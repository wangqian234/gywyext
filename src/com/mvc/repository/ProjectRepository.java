package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.Project;


public interface ProjectRepository extends JpaRepository<Project, Integer>{
	
	//根据ID获取设备信息
	@Query("select tr from Project tr where proj_id=:proj_id ")
	public Project selectProjectById(@Param("proj_id") Integer proj_id);
}
