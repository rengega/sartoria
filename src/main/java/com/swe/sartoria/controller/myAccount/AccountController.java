package com.swe.sartoria.controller.myAccount;


import com.swe.sartoria.dto.AuthResponseDTO;
import com.swe.sartoria.dto.CostumerDTO;
import com.swe.sartoria.dto.LoginDTO;
import com.swe.sartoria.dto.RegisterDTO;
import com.swe.sartoria.model.Account;
import com.swe.sartoria.model.Costumer;
import com.swe.sartoria.model.Role;
import com.swe.sartoria.model.UserEntity;
import com.swe.sartoria.repository.*;
import com.swe.sartoria.security.JWTGenerator;
import com.swe.sartoria.service.AccountService;
import com.swe.sartoria.service.DAO;
import com.swe.sartoria.service.DAO_pure;
import com.swe.sartoria.service.UserService;
import org.apache.catalina.User;
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
    private UserService userService;
    private JWTGenerator jwtGenerator;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    private DAO dao;
    private RoleRepository roleRepository;
    private CostumerRepository costumerRepository;
    private AccountRepository AccountRepository;
    private AuthenticationManager authenticationManager;
    private final OrderRepository orderRepository;

    private DAO_pure dao_pure;
    private AccountService accountService;


    @Autowired
    public AccountController(UserService userService, JWTGenerator jwtGenerator, PasswordEncoder passwordEncoder, AccountRepository AccountRepository, RoleRepository roleRepository,
                             UserRepository userRepository, CostumerRepository costumerRepository, DAO dao, AuthenticationManager authenticationManager,
                             OrderRepository orderRepository,
                             DAO_pure dao_pure, AccountService accountService)
    {
        this.userService = userService;
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.dao = dao;
        this.costumerRepository = costumerRepository;
        this.AccountRepository = AccountRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.orderRepository = orderRepository;

        this.accountService = accountService;
        this.dao_pure = dao_pure;
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

        if (accountService.existsByUsername(registerDto.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        accountService.addAccount(registerDTOtoAccount(registerDto));

        return new ResponseEntity<>("Account registered success!", HttpStatus.OK);
    }

    private Account registerDTOtoAccount(RegisterDTO dto){
        Account newAccount = new Account();
        Costumer newCostumer = new Costumer();
        UserEntity newUser = new UserEntity();

        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode((dto.getPassword())));
        newAccount.setUser(newUser);

        newCostumer.setName(dto.getName());
        newCostumer.setSurname(dto.getSurname());
        newCostumer.setEmail(dto.getEmail());
        newCostumer.setPhone(dto.getPhone());

        newAccount.setCostumer(newCostumer);
        return newAccount;
    }

    @GetMapping("/myOrders")
    public ResponseEntity<?> getMyOrders() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userRepository.findByUsername(username).get();
        Account myAccount = AccountRepository.findByUser(user);
        return new ResponseEntity<>(orderRepository.findByCostumerId(myAccount.getCostumer().getId()), HttpStatus.OK);
    }

    @PutMapping("/profile/edit")
    public ResponseEntity<String> updateProfile(@RequestBody CostumerDTO costumerUpdate){
        Costumer update = new Costumer();
        update.setId(costumerUpdate.getId());
        update.setName(costumerUpdate.getName());
        update.setSurname(costumerUpdate.getSurname());
        update.setPhone(costumerUpdate.getPhone());
        update.setEmail(costumerUpdate.getEmail());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userRepository.findByUsername(username).get();
        Account myAccount = AccountRepository.findByUser(user);
        myAccount.setCostumer(update);
        accountService.updateAccount(myAccount, myAccount.getId());

        return new ResponseEntity<>("Profile Edited!", HttpStatus.OK);

    }

}
