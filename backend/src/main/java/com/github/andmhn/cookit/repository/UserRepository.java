package com.github.andmhn.cookit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.andmhn.cookit.entity.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{
	
}
