package com.scientificcenter.service.impl;

import com.scientificcenter.model.enums.Role;
import com.scientificcenter.model.users.User;
import com.scientificcenter.repository.jpa.UserRepository;
import com.scientificcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllAuthors() {
        return this.userRepository.getAllUsersByRolesContains(Role.AUTHOR);
    }

    @Override
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User findUserById(Long id) {
        return this.userRepository.getOne(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return this.userRepository.findUserByEmailContains(username);
    }

    @Override
    public List<User> findUsersWhichAreCorrespondingForArea(Long id) {
        return this.userRepository.findUsersWhichAreCorrespondingForArea(id);
    }
}
