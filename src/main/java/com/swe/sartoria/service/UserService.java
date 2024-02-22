package com.swe.sartoria.service;

import com.swe.sartoria.dto.RoleDTO;
import com.swe.sartoria.dto.UserDTO;
import com.swe.sartoria.dto.UserResponse;
import com.swe.sartoria.model.Role;
import com.swe.sartoria.model.UserEntity;
import com.swe.sartoria.repository.RoleRepository;
import com.swe.sartoria.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService{
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private DAO dao;
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, DAO dao, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    // addUser method to be used by admin: the clients
    // can register themselves using the register method in the AuthController
    public UserDTO addUser(UserDTO userDTO) {
        UserEntity newUser = mapToEntity(userDTO);
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser =  userRepository.save(newUser);
        return mapToDTO(newUser);
    }

    public UserDTO getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            return null;
        }
        return mapToDTO(user);
    }

    public UserDTO getUserById(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return mapToDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO userDTO) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        user = mapToEntity(userDTO);
        user = userRepository.save(user);
        return mapToDTO(user);
    }

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

    public void deleteUser(Long id) {
        UserEntity user = userRepository.findById(id).orElse(null);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserEntity mapToEntity(UserDTO userDTO) {
        UserEntity user = new UserEntity();

        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setId(userDTO.getId());
        List<Role> roles = new ArrayList<>();
        for (RoleDTO role : userDTO.getRoles()) {
            roles.add(roleRepository.findByName(role.getName()).get());
        }
        user.setRoles(roles);

        return user;
    }

    public UserDTO mapToDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setId(user.getId());
        List<RoleDTO> roles = new ArrayList<>();
        for (Role role : user.getRoles()) {
            RoleDTO roleDTO  = new RoleDTO();
            roleDTO.setName(role.getName());
            roleDTO.setId(role.getId());
            roles.add(roleDTO);
        }

        userDTO.setRoles(roles);

        return userDTO;
    }

}