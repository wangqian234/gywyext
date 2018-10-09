package com.mvc.dao;
import java.util.List;
import com.mvc.entityReport.Company;
import com.mvc.entityReport.Project;





public interface IndexDao {

	List<Company> getInitLeft1();
	
	List<Project> getInitLeft2();
	//根据公司id查找公司的项目
	List<Project>  getCompProj(String searchKey);
	//删除公司信息
	Boolean updateState(Integer comp_id);
	//删除项目信息
	Boolean updateStated(Integer proj_id);
	// 根据页数筛选全部公司信息列表
	List<Company> findCompanyByPage(String searchKey, Integer offset, Integer end);
	// 查询公司信息总条数
	Integer CompCountTotal(String searchKey);
	// 根据页数筛选全部项目信息列表
	List<Project> getProjectListByPage(String searchKey, Integer offset, Integer end);
	// 查询项目信息总条数
	Integer ProjCountTotal(String searchKey);
}
