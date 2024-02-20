package com.swe.sartoria.controller.accounts;


import com.swe.sartoria.dto.AuthResponseDTO;
import com.swe.sartoria.dto.LoginDTO;
import com.swe.sartoria.dto.RegisterDTO;
import com.swe.sartoria.model.Account;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Role;
import com.swe.sartoria.model.UserEntity;
import com.swe.sartoria.repository.*;
import com.swe.sartoria.security.JWTGenerator;
import com.swe.sartoria.service.CostumerService;
import com.swe.sartoria.service.OrderService;
import com.swe.sartoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private  UserService userService;
    private JWTGenerator jwtGenerator;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    private OrderService orderService;
    private RoleRepository roleRepository;
    private CostumerRepository costumerRepository;
    private AccountRepository AccountRepository;
    private AuthenticationManager authenticationManager;
    private final OrderRepository orderRepository;


    @Autowired
    public AccountController(UserService userService, JWTGenerator jwtGenerator, PasswordEncoder passwordEncoder, AccountRepository AccountRepository, RoleRepository roleRepository,
                             UserRepository userRepository, CostumerRepository costumerRepository, OrderService orderService, AuthenticationManager authenticationManager,
                             OrderRepository orderRepository)
    {
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.costumerRepository = costumerRepository;
        this.AccountRepository = AccountRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
        System.out.println("register controller hit");

        if (userRepository.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode((registerDto.getPassword())));

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        user = userRepository.save(user);

        Costumer costumer = new Costumer();
        costumer.setEmail(registerDto.getEmail());
        costumer.setName(registerDto.getName());
        costumer.setSurname(registerDto.getSurname());
        costumer.setPhone(registerDto.getPhone());

        costumer = costumerRepository.save(costumer);

        Account account = new Account();
        account.setCostumer(costumer);
        account.setUser(user);

        account = AccountRepository.save(account);

        return new ResponseEntity<>("Account registered success!", HttpStatus.OK);
    }

    @GetMapping("/myOrders")
    public ResponseEntity<?> getMyOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userRepository.findByUsername(username).get();
        Account myAccount = AccountRepository.findByUser(user);
        return new ResponseEntity<>(orderRepository.findByCostumerId(myAccount.getCostumer().getId()), HttpStatus.OK);
    }

}
