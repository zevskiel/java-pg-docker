package com.chandra.javadocker.userapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumberAndPassword(String phoneNumber, String password);
    User findByPhoneNumber(String phoneNumber);
    Optional<User> findByName(String name);
}
