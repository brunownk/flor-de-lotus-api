package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository  // This tells Spring that this interface is a repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository provides CRUD operations and more
}
