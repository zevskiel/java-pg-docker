package com.chandra.javadocker.userapp.service;

import com.chandra.javadocker.userapp.dao.User;
import io.jsonwebtoken.Claims;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String getPhoneNumberFromToken(String token) throws Exception;

    //retrieve expiration date from jwt token
    Date getExpirationDateFromToken(String token);

    <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver);

    //generate token for user
    String generateToken(User user) throws Exception;

    //validate token
    User validateToken(String token) throws Exception;
}
