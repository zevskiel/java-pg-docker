package com.chandra.javadocker.userapp.service;

import com.chandra.javadocker.userapp.dao.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void saveUser(User user) throws Exception;

    User getUserByPhoneNumberAndPassword(String phoneNumber, String password) throws Exception;
    User getUserByPhoneNumber(String phoneNumber) throws Exception;

    Boolean validateUserData(String name, String phone, String password) throws Exception;

    User updateUserName(String oldName, String name) throws Exception;
}
