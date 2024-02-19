package com.swe.sartoria.service.impl;

import com.swe.sartoria.dto.RoleDTO;
import com.swe.sartoria.dto.UserDTO;
import com.swe.sartoria.dto.UserResponse;
import com.swe.sartoria.model.Role;
import com.swe.sartoria.model.UserEntity;
import com.swe.sartoria.repository.RoleRepository;
import com.swe.sartoria.repository.UserRepository;
import com.swe.sartoria.service.CostumerService;
import com.swe.sartoria.service.OrderService;
import com.swe.sartoria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private OrderService orderService;
    private CostumerService costumerService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, OrderService orderService, CostumerService costumerService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.orderService = orderService;
        this.costumerService = costumerService;
        this.passwordEncoder = passwordEncoder;
    }

    // addUser method to be used by admin: the clients
    // can register themselves using the register method in the AuthController
    @Override
    public UserDTO addUser(UserDTO userDTO) {
        UserEntity newUser = mapToEntity(userDTO);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser =  userRepository.save(newUser);
        return mapToDTO(newUser);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        return mapToDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return mapToDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        user = mapToEntity(userDTO);
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    @Override
    public UserResponse getAllUsers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<UserEntity> userPage = userRepository.findAll(pageable);
        List<UserEntity> usersList = userPage.getContent();
        List<UserDTO> content = new ArrayList<>();
        for (UserEntity user : usersList) {
            content.add(mapToDTO(user));
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setTotalElements(userPage.getTotalElements());
        userResponse.setTotalPages(userPage.getTotalPages());
        userResponse.setPageNo(userPage.getNumber());
        userResponse.setPageSize(userPage.getSize());
        userResponse.setLast(userPage.isLast());
        return userResponse;
    }

    @Override
    public UserResponse searchUser(String username) {
        List<UserEntity> usersList = userRepository.findByUsernameContaining(username);
        List<UserDTO> content = new ArrayList<>();
        for (UserEntity user : usersList) {
            content.add(mapToDTO(user));
        }
        UserResponse userResponse = new UserResponse();
        userResponse.setContent(content);
        userResponse.setTotalElements(content.size());
        userResponse.setTotalPages(1);
        userResponse.setPageNo(0);
        userResponse.setPageSize(content.size());
        userResponse.setLast(true);
        return userResponse;
    }

    @Override
    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    @Override
    public UserEntity mapToEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        List<Role> roles = new ArrayList<>();
        for (RoleDTO role : userDTO.getRoles()) {
            roles.add(roleRepository.findByName(role.getName()).get());
        }
        user.setRoles(roles);

        return user;
    }

    @Override
    public UserDTO mapToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        List<RoleDTO> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            RoleDTO roleDTO  = new RoleDTO();
            roleDTO.setName(role.getName());
            roles.add(roleDTO);
            roles.add(roleDTO);
        }

        userDTO.setRoles(roles);

        return userDTO;
    }

}
