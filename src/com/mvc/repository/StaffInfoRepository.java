package com.mvc.repository;



/*import java.util.List;*/

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.Role;
/*import com.mvc.entityReport.Role;*/
import com.mvc.entityReport.User;

public interface StaffInfoRepository extends JpaRepository<User, Integer>{

	// 根据userNum查询用户账号是否存在,返回1存在，返回0不存在
	
	//@Query("select count(id) from User u where user_acct = :user_acct and user_isdelete=0")
	//	public Long countByUserAcct(@Param("user_acct") String user_acct);
	
	//根据ID获取用户信息
	@Query("select tr from  User tr where user_id=:user_id ")
	User selectUserById(@Param("user_id") Integer user_id);
	//根据ID获取角色信息
		@Query("select tr from  Role tr where role_id=:role_id ")
		Role selectRoleById(@Param("role_id") Integer role_id);
	
			
			
}
