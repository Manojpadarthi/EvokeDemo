package com.evoke.demo.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.evoke.demo.employee.UserEntity;

@Repository
//@CrossOrigin("http://localhost:4200")
public interface UserRepository extends JpaRepository<UserEntity,Integer>{

	UserEntity findByUserName(String username);

}
