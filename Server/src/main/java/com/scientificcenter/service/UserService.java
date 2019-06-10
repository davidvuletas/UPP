package com.scientificcenter.service;

import com.scientificcenter.model.users.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

//    boolean isUserExists(String email, String password);
    List<User> getAllAuthors();

    List<User> getAllUsers();

    User findUserById(Long id);

    User findUserByUsername(String username);

    List<User> findUsersWhichAreCorrespondingForArea(Long id);


}
