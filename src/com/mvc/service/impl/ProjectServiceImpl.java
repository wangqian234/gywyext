package com.mvc.service.impl;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mvc.dao.IndexDao;
import com.mvc.entityReport.Company;
import com.mvc.entityReport.Project;
import com.mvc.entityReport.User;
import com.mvc.repository.CompanyRepository;
import com.mvc.repository.ProjectRepository;
import com.mvc.service.ProjectService;
import net.sf.json.JSONObject;

@Service("projectServiceImpl")
public  class ProjectServiceImpl implements ProjectService {
	@Autowired
	IndexDao indexDao;
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
	 


	
	@Override
	public List<Project> getProjectInfo() {
		// TODO Auto-generated method stub
		return projectRepository.getProjectInfo();
	}
	// 根据ID获取公司信息
	@Override
	public Company selectCompanyById(Integer comp_id) {
		return companyRepository.selectCompanyById(comp_id);
	}
	// 修改公司基本信息
	@Override
	public boolean updateCompanyBase(Integer comp_id, JSONObject jsonObject, User user) throws ParseException {
		Company company = companyRepository.selectCompanyById(comp_id);
		
		if (company != null) {
			if (jsonObject.containsKey("comp_name")) {
				company.setComp_name(jsonObject.getString("comp_name"));
			}	
			if (jsonObject.containsKey("comp_addr")) {
				company.setComp_addr(jsonObject.getString("comp_addr"));
			}
			if (jsonObject.containsKey("comp_num")) {
				company.setComp_num(jsonObject.getInt("comp_num"));
			}	
			if (jsonObject.containsKey("comp_memo")) {
				company.setComp_memo(jsonObject.getString("comp_memo"));
			}		
		}
		company = companyRepository.saveAndFlush(company);
		if (company.getComp_id() != null)
			return true;
		else
			return false;
	}
	// 修改项目基本信息
		@Override
		public boolean updateProjectBase(Integer proj_id, JSONObject jsonObject, User user) throws ParseException {
			Project project = projectRepository.selectProjectById(proj_id);
			
			if (project != null) {
				if (jsonObject.containsKey("proj_name")) {
					project.setProj_name(jsonObject.getString("proj_name"));
				}	
				if (jsonObject.containsKey("proj_addr")) {
					project.setProj_addr(jsonObject.getString("proj_addr"));
				}
				if (jsonObject.containsKey("proj_num")) {
					project.setProj_num(jsonObject.getInt("proj_num"));
				}	
				if (jsonObject.containsKey("proj_memo")) {
					project.setProj_memo(jsonObject.getString("proj_memo"));
				}
				if (jsonObject.containsKey("company")) {
					Company co = new Company();
					co.setComp_id(Integer.valueOf(jsonObject.getString("company")));
					project.setCompany(co);	
				}
			}
			project = projectRepository.saveAndFlush(project);
			if (project.getProj_id() != null)
				return true;
			else
				return false;
		}
		
		//根据公司id查找公司的项目
		@Override
		public List<Project> getCompProj(String searchKey) {
			List<Project> list = indexDao.getCompProj(searchKey);
			for(int i=0;i<list.size();i++){
				list.get(i).setCompany(null);
			}
			return indexDao.getCompProj(searchKey);
		}
		
		@Override
		public void saveProjs(List<Project> compProjs) {
			for(int i=0;i<compProjs.size();i++){
				projectRepository.saveAndFlush(compProjs.get(i));
			}
		}
		
		// 根据id删除公司信息
		@Override
		public boolean deleteIsdelete(Integer comp_id) {
			return	indexDao.updateState(comp_id);
		}
		
		// 根据ID获取公司信息
		@Override
		public Project selectProjectById(Integer proj_id) {
			return projectRepository.selectProjectById(proj_id);
		}
		
		// 根据id删除项目信息
		@Override
		public boolean deleteIsdeleted(Integer proj_id) {
			return	indexDao.updateStated(proj_id);
		}
		
		// 根据页数筛选全部公司信息列表
		public List<Company> findCompanyByPage(String searchKey, Integer offset, Integer end) {
			return  indexDao.findCompanyByPage(searchKey, offset, end);
		}
		
		// 查询公司总条数
		@Override
		public Integer CompCountTotal(String searchKey) {
			return indexDao.CompCountTotal(searchKey);
		}
		
		// 根据页数筛选全部项目信息列表
		public List<Project> findProjectByPage(String searchKey, Integer offset, Integer end) {
			return  indexDao.findProjectByPage(searchKey, offset, end);
		}
		
		// 查询项目总条数
		@Override
		public Integer ProjCountTotal(String searchKey) {
			return indexDao.ProjCountTotal(searchKey);
		}
		

	/*@Override
	public List<Project> getProjectInfo(Integer parameter) {
		List<Project> project = new ArrayList<Project>();
		try{
			project = projectRepository.getProjectInfo(parameter);
		} catch (Exception e){
			
		} finally {
			return project;
		}
	}
*/
}
					
		
		
		
				
		
		
      
			
			
			
			
			
			
			
			
