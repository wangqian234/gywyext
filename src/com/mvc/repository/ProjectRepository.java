package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvc.entityReport.Project;


public interface ProjectRepository extends JpaRepository<Project, Integer>{
	

}
