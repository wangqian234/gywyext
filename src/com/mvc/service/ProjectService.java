package com.mvc.service;

import java.util.List;

import com.mvc.entityReport.Company;
import com.mvc.entityReport.Project;

public interface ProjectService {

	boolean addCompany(Company company, Project project);

	List<Company> getCompanyInfo();

	boolean addProject(Project project);

}
