package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import com.mvc.entityReport.Project;
public interface ProjectRepository extends JpaRepository<Project, Integer>{
	
	//根据ID获取项目信息
	@Query("select p from Project p where proj_id=:proj_id ")
	public Project selectProjectById(@Param("proj_id") Integer proj_id);
	
	@Query("select p from Project  p where proj_isdeleted=0")
	public List<Project> getProjectInfo();
	
	
}



