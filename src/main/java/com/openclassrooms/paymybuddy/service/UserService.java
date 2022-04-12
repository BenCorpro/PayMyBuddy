package com.openclassrooms.paymybuddy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.openclassrooms.paymybuddy.dto.RegisterDTO;
import com.openclassrooms.paymybuddy.dto.UserDTO;
import com.openclassrooms.paymybuddy.exceptions.UserAccountException;
import com.openclassrooms.paymybuddy.exceptions.UserConnectionException;
import com.openclassrooms.paymybuddy.model.User;

public interface UserService {

	Page<User> getUsers(int page);

	Optional<User> getUserById(Integer id);

	Page<UserDTO> getUsersDTOByEmail(String email, int page);

	Page<User> getUsersByEmail(String email, int page);

	Optional<User> getUserByEmail(String email);

	boolean existsByEmail(String email);

	User saveUser(User user);

	User newUser(RegisterDTO newUserDto) throws UserAccountException;

	User updateUser(User user);

	void deleteUserById(Integer id);

	void deleteUserByEmail(String email);

	void addBankAccount(User user, String accountNumber);

	boolean addFriend(User user, User newFriend) throws UserConnectionException;

	List<User> getConnections(int userId);

}