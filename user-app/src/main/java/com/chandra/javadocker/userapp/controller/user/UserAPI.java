package com.chandra.javadocker.userapp.controller.user;

import com.chandra.javadocker.userapp.dao.User;
import com.chandra.javadocker.userapp.service.JwtService;
import com.chandra.javadocker.userapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserAPI {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Operation(summary = "register new user")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request){
        try{
            Boolean result = userService.validateUserData(request.getName(), request.getPhoneNumber(), request.getPassword());
            if(result) {
                User user = new User()
                        .setPhoneNumber(request.getPhoneNumber())
                        .setName(request.getName())
                        .setPassword(request.getPassword());

                userService.saveUser(user);
                return new ResponseEntity<>("Success create user", HttpStatus.CREATED);
            } else {
                throw new Exception("Invalid user data");
            }
        } catch (DataIntegrityViolationException d){
            return new ResponseEntity<>("user already exists", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "user login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        try {
            if(request.getPhoneNumber() == null || request.getPhoneNumber().isEmpty()
                    || request.getPassword() == null || request.getPassword().isEmpty()) {
                throw new Exception("Name or Password is Empty");
            }
            User user = userService.getUserByPhoneNumberAndPassword(request.getPhoneNumber(), request.getPassword());
            if(user == null || user.getId() == null){
                throw new Exception("Name or Password is Invalid");
            }
            return new ResponseEntity<>(jwtService.generateToken(user), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Get user name", description = "Need to authorize using JWT token from login without Bearer keyword")
    @SecurityRequirement(name = "JWT Bearer Authentication")
    @GetMapping("/name")
    public ResponseEntity<?> getName() {
        try {
            String name = SecurityContextHolder.getContext().getAuthentication().getName();
            return new ResponseEntity<>(name, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "Update user name", description = "Need to authorize using JWT token from login without Bearer keyword")
    @SecurityRequirement(name = "JWT Bearer Authentication")
    @PostMapping("/update/name")
    public ResponseEntity<?> getName(@RequestBody UserUpdateNameRequest request) {
        try {
            String oldName = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.updateUserName(oldName, request.getName());
            return new ResponseEntity<>("Update Success", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
