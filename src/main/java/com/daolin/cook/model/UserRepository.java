package com.daolin.cook.model;

import org.apache.catalina.mbeans.UserMBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);
    List<User> findAll();
    void deleteByName(String name);
}
