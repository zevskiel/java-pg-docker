package com.chandra.javadocker.userapp.service.impl;

import com.chandra.javadocker.userapp.dao.User;
import com.chandra.javadocker.userapp.dao.UserRepository;
import com.chandra.javadocker.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {
    private static final byte[] salt = "sawitpro".getBytes(StandardCharsets.UTF_8);
    private static final int HASH_BYTES = 256;
    private static final int PBKDF2_ITERATIONS = 1000;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user) throws Exception {
        //hash password
        KeySpec spec = new PBEKeySpec(user.getPassword().toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTES);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();

        //store user
        user.setPassword(toHex(hash));
        userRepository.save(user);
    }

    @Override
    public Boolean validateUserData(String name, String phone, String password) throws Exception {
        //validate phone number
        String prefix = phone == null ? "" : phone.substring(0,2);
        if(phone == null || phone.length() > 13 || phone.length() < 10 || !prefix.equalsIgnoreCase("08")) {
            return false;
        }

        //validate name
        if(!validateName(name)) {
            return false;
        }

        //validate password
        return password != null && password.matches("^(?=.*?[A-Z])(?=.*?[0-9]).{6,16}$");
    }

    @Override
    public User getUserByPhoneNumberAndPassword(String phoneNumber, String password) throws Exception {
        User user = userRepository.findByPhoneNumberAndPassword(phoneNumber, password);
        if(user == null){
            throw new Exception("Invalid phone number or password");
        }
        return user;
    }

    @Override
    public User getUserByPhoneNumber(String phoneNumber) throws Exception {
        User user = userRepository.findByPhoneNumber(phoneNumber);
        if(user == null){
            throw new Exception("Invalid phone number");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + name));

        return new org.springframework.security.core.userdetails.User(
                user.getName(), user.getPassword(),new ArrayList<>()
        );
    }

    @Override
    public User updateUserName(String oldName, String name) throws Exception {
        if(!validateName(name)) {
            throw new Exception("Invalid name");
        }
        User user = userRepository.findByName(oldName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + name));

        user.setName(name);
        userRepository.save(user);
        return user;
    }

    private static Boolean validateName (String name) {
        if(name == null || name.isEmpty() || name.length() > 60) {
            return false;
        }
        return true;
    }

    private static String toHex(byte[] array)
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
}
