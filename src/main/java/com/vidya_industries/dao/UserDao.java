package com.vidya_industries.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidya_industries.Entity.Role;
import com.vidya_industries.Entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Long>{

	Optional<UserEntity> findByEmail(String email);
	
	boolean existsByEmail(String Email);

	List<UserEntity> findByRole(Role role);

}
