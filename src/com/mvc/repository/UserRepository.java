package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.User;

public interface UserRepository extends JpaRepository<User, Integer> {	

	//根据ID获取用户信息
	@Query("select et from User et where user_id=:user_id ")
	public User selectUserById(@Param("user_id") Integer user_id);

}