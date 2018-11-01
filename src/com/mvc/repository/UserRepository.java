package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.User;

public interface UserRepository extends JpaRepository<User, Integer> {	

	//根据ID获取用户信息
	@Query("select eu from User eu where user_id=:user_id ")
	public User selectUserById(@Param("user_id") Integer user_id);
	
	//获取设备分类信息
	@Query("select eu from User eu where user_isdeleted=0")
	List<User> getUserInfo();

	// 根据userNum查询用户账号是否存在,返回1存在，返回0不存在
		@Query("select count(id) from User u where user_acct = :user_acct and user_isdeleted=0")
		public Long countByUserAcct(@Param("user_acct") String user_acct);

		// 根据userNum查询用户信息
		@Query("select u from User u where user_acct = :user_acct and user_isdeleted=0")
		public User findByUserAcct(@Param("user_acct") String user_acct);



}