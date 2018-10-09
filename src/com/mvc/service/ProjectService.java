package com.mvc.service;

import java.text.ParseException;
import java.util.List;

import com.mvc.entityReport.Company;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.Project;
import com.mvc.entityReport.User;

import net.sf.json.JSONObject;

public interface ProjectService {
	//添加公司信息
	/*boolean addCompany(Company company, Project project);*/
	List<Company> getCompanyInfo();
	Company save(Company company);
	//根据公司id查找公司的项目
		List<Project> getCompProj(String searchKey);
		void saveProjs(List<Project> projects);
	//添加项目信息
	boolean addProject(Project project);
	List<Project> getProjectInfo();


	//根据id获取公司信息
	Company selectCompanyById(Integer comp_id);
	
	// 修改公司基本信息
	boolean updateCompanyBase(Integer comp_id, JSONObject jsonObject, User user) throws ParseException;
	
	// 修改项目基本信息
	boolean  updateProjectBase(Integer proj_id, JSONObject jsonObject, User user) throws ParseException;
	
	

	// 根据id删除公司
		boolean deleteIsdelete(Integer comp_id);
		
	// 根据id删除项目
	boolean deleteIsdeleted(Integer proj_id);
		
	//根据id获取项目信息
		Project selectProjectById(Integer proj_id);
		
	// 查询公司总条数
	Integer CompCountTotal(String searchKey);
	
	// 根据页数筛选全部公司信息列表
	List<Company> findCompanyByPage(String searchKey, Integer offset, Integer end);
		
	// 查询项目总条数
	Integer ProjCountTotal(String searchKey);
		
	// 根据页数筛选全部项目信息列表
	List<Project>  findProjectByPage  (String searchKey, Integer offset, Integer end);

	}

	





	
	



