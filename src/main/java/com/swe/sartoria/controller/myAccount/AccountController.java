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
import com.swe.sartoria.service.DAO_pure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Collections;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private JWTGenerator jwtGenerator;
    private PasswordEncoder passwordEncoder;

    private AccountRepository AccountRepository;
    private AuthenticationManager authenticationManager;

    private DAO_pure dao;
    private AccountService accountService;

    private UserRepository userRepository;
    private RoleRepository roleRepository;



    @Autowired
    public AccountController( JWTGenerator jwtGenerator, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                              AccountRepository AccountRepository,
                              DAO_pure dao_pure,
                              AccountService accountService,
                              RoleRepository roleRepository,
                              UserRepository userRepository)
    {
        this.jwtGenerator = jwtGenerator;
        this.passwordEncoder = passwordEncoder;
        this.AccountRepository = AccountRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
        this.accountService = accountService;
        this.userRepository = userRepository;
        this.dao = dao_pure;
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
        Role roles = roleRepository.findByName("USER").get();
        newUser.setRoles(Collections.singletonList(roles));

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
        return new ResponseEntity<>(dao.getOrdesByCostId(myAccount.getCostumer().getId()), HttpStatus.OK);
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

    @GetMapping("/proile/myFidelityPoints")
    public ResponseEntity<Integer> getMyPoints(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserEntity user = userRepository.findByUsername(username).get();
        Account myAccount = AccountRepository.findByUser(user);
        return new ResponseEntity<>(myAccount.getFidelityPoints(), HttpStatus.OK);
    }

}
