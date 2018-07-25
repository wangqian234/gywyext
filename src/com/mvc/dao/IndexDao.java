package com.mvc.dao;

import java.util.List;

import com.mvc.entityReport.Company;
import com.mvc.entityReport.Project;

public interface IndexDao {

	List<Company> getInitLeft1();
	
	List<Project> getInitLeft2();

}
