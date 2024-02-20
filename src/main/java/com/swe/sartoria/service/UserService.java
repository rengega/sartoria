package com.swe.sartoria.service;

import com.swe.sartoria.dto.UserDTO;
import com.swe.sartoria.dto.UserResponse;
import com.swe.sartoria.model.UserEntity;

public interface UserService {
        UserDTO addUser(UserDTO userDTO);
        UserResponse getAllUsers(int pageNo, int pageSize);
        UserDTO getUserById(Long id);

        UserDTO getUserByUsername(String username);
        UserResponse searchUser(String username);
        UserDTO updateUser(Long id, UserDTO userDTO);

        void deleteUser(Long id);

        UserDTO mapToDTO(UserEntity userEntity);
        UserEntity mapToEntity(UserDTO userDTO);

        // TODO: implement method to check if user exists to use in controller
}
