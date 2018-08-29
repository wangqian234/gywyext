package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.entityReport.Company;
import com.mvc.entityReport.Project;
import com.mvc.repository.CompanyRepository;
import com.mvc.repository.ProjectRepository;
import com.mvc.service.ProjectService;



@Service("projectServiceImpl")
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	CompanyRepository companyRepository;

	@SuppressWarnings("unused")
	@Override
	public boolean addCompany(Company company, Project project) {
		Company com = companyRepository.saveAndFlush(company);
		if(project.getProj_name() == null){return true;};
		project.setCompany(com);
		Project pro = projectRepository.saveAndFlush(project);
		return true;
	}

	@Override
	public List<Company> getCompanyInfo() {
		return companyRepository.getCompanyInfo();
	}

	@Override
	public boolean addProject(Project project) {
		try{
			projectRepository.saveAndFlush(project);
			return true;
		} catch (Exception e){
			return false;
		}
	}

}
