package com.mvc.controller;

import java.text.ParseException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.base.constants.SessionKeyConstants;
import com.mvc.entityReport.Company;
import com.mvc.entityReport.Project;
import com.mvc.entityReport.User;
import com.mvc.service.ProjectService;
import com.utils.Pager;
import com.utils.StringUtil;
import net.sf.json.JSONObject;





@Controller
@RequestMapping("/systemProject")
public class ProjectController {
	
	@Autowired
	ProjectService projectService;

	//	增加公司信息
	@RequestMapping("/addCompany.do")
	public @ResponseBody String addCompany(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Company company = new Company();
		Project project = new Project();
		
		JSONObject jsonCom= JSONObject.fromObject(request.getParameter("company"));
		if (jsonCom.containsKey("comp_name")){
			if (StringUtil.strIsNotEmpty(jsonCom.getString("comp_name"))){
				company.setComp_name(jsonCom.getString("comp_name"));
			}
		}
		if (jsonCom.containsKey("comp_addr")){
			if (StringUtil.strIsNotEmpty(jsonCom.getString("comp_addr"))){
				company.setComp_addr(jsonCom.getString("comp_addr"));
			}
		}
		if (jsonCom.containsKey("comp_num")){
			if (StringUtil.strIsNotEmpty(jsonCom.getString("comp_num"))){
				company.setComp_num(Integer.parseInt(jsonCom.getString("comp_num")));
			}	
		}
		if (jsonCom.containsKey("comp_memo")){
			if (StringUtil.strIsNotEmpty(jsonCom.getString("comp_memo"))){
				company.setComp_memo(jsonCom.getString("comp_memo"));
			}
		}
		company.setComp_isdeleted(0);
		
		JSONObject jsonPro= JSONObject.fromObject(request.getParameter("project"));
		if (jsonPro.containsKey("proj_name")){
			if (StringUtil.strIsNotEmpty(jsonPro.getString("proj_name"))){
				project.setProj_name(jsonPro.getString("proj_name"));
			}
		}
		if (jsonPro.containsKey("proj_addr")){
			if (StringUtil.strIsNotEmpty(jsonPro.getString("proj_addr"))){
				project.setProj_addr(jsonPro.getString("proj_addr"));
			}
		}
		if (jsonPro.containsKey("proj_num")){
			if (StringUtil.strIsNotEmpty(jsonPro.getString("proj_num"))){
				project.setProj_num(Integer.parseInt(jsonPro.getString("proj_num")));
			}	
		}
		if (jsonPro.containsKey("proj_memo")){
			if (StringUtil.strIsNotEmpty(jsonPro.getString("proj_memo"))){
				project.setProj_memo(jsonPro.getString("proj_memo"));
			}
		}
		project.setProj_isdeleted(0);
		
		boolean result = projectService.addCompany(company,project);
		
		
		jsonObject.put("Result", result);
		return jsonObject.toString();
	}
	/**
	 * 根据页数筛选公司信息列表
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getCompanyListByPage.do")
	public @ResponseBody String getCompanysByPrarm(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String searchKey = request.getParameter("searchKey");
		Integer totalRow = projectService.CompCountTotal(searchKey);
		Pager pager = new Pager();
		pager.setPage(Integer.valueOf(request.getParameter("page")));
		pager.setTotalRow(Integer.parseInt(totalRow.toString()));
		List<Company> list = projectService.findCompanyByPage(searchKey, pager.getOffset(), pager.getLimit());
		jsonObject.put("list", list);
		jsonObject.put("totalPage", pager.getTotalPage());
		System.out.println("totalPage:" + pager.getTotalPage());
		return jsonObject.toString();
	}
		
	/**
	 * 根据页数筛选项目信息列表
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getProjectListByPage.do")
	public @ResponseBody String getProjectsByPrarm(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String searchKey = request.getParameter("searchKey");
		Integer totalRow = projectService.ProjCountTotal(searchKey);
		Pager pager = new Pager();
		pager.setPage(Integer.valueOf(request.getParameter("page")));
		pager.setTotalRow(Integer.parseInt(totalRow.toString()));
		List<Project> list = projectService.findProjectByPage(searchKey, pager.getOffset(), pager.getLimit());
		jsonObject.put("list", list);
		jsonObject.put("totalPage", pager.getTotalPage());
		System.out.println("totalPage:" + pager.getTotalPage());
		return jsonObject.toString();
	}
		
	
	//	增加项目信息
	@RequestMapping("/addProject.do")
	public @ResponseBody String addProject(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		Project project = new Project();
		Company company = new Company();
		JSONObject jsonPro= JSONObject.fromObject(request.getParameter("project"));
		if (jsonPro.containsKey("proj_name")){
			if (StringUtil.strIsNotEmpty(jsonPro.getString("proj_name"))){
				project.setProj_name(jsonPro.getString("proj_name"));
			}
		}
		if (jsonPro.containsKey("proj_addr")){
			if (StringUtil.strIsNotEmpty(jsonPro.getString("proj_addr"))){
				project.setProj_addr(jsonPro.getString("proj_addr"));
			}
		}
		if (jsonPro.containsKey("proj_num")){
			if (StringUtil.strIsNotEmpty(jsonPro.getString("proj_num"))){
				project.setProj_num(Integer.parseInt(jsonPro.getString("proj_num")));
			}	
		}
		if (jsonPro.containsKey("proj_memo")){
			if (StringUtil.strIsNotEmpty(jsonPro.getString("proj_memo"))){
				project.setProj_memo(jsonPro.getString("proj_memo"));
			}
		}
		if (jsonPro.containsKey("company")){
			if (StringUtil.strIsNotEmpty(jsonPro.getString("company"))){
				company.setComp_id(Integer.parseInt(jsonPro.getString("company")));
				project.setCompany(company);
			}
		}
		project.setProj_isdeleted(0);
		
		boolean result = projectService.addProject(project);
		
		
		jsonObject.put("Result", result);
		return jsonObject.toString();
	}
		
	
	//浏览公司信息
	@RequestMapping("/getCompanyInfo.do")
	public @ResponseBody String getCompanyInfo(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		
		List<Company> result = projectService.getCompanyInfo();
		
		jsonObject.put("result", result);
		return jsonObject.toString();
	}

	//浏览项目信息
		@RequestMapping("/getProjectInfo.do")
		public @ResponseBody String getProjectInfo(HttpServletRequest request) {
			JSONObject jsonObject = new JSONObject();
			
			List<Project> result = projectService.getProjectInfo();
			
			jsonObject.put("result", result);
			return jsonObject.toString();
		}
		//根据id获取公司信息
				@RequestMapping("/selectCompanyById.do")
				public @ResponseBody String selectCompanyById(HttpServletRequest request, HttpSession session) {
					int comp_id = Integer.parseInt(request.getParameter("comp_id"));
					session.setAttribute("comp_id", comp_id);
					Company company = projectService.selectCompanyById(comp_id);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("company", company);
					return jsonObject.toString();
				}
			//修改公司信息
			@RequestMapping("/updateCompanyById.do")
			public @ResponseBody Integer updateCompanyById(HttpServletRequest request, HttpSession session) throws ParseException {
				User user = (User) session.getAttribute(SessionKeyConstants.LOGIN);
				JSONObject jSONObject = JSONObject.fromObject(request.getParameter("companys"));
				Integer comp_id = null;
				if (jSONObject.containsKey("comp_id")) {
					comp_id = Integer.parseInt(jSONObject.getString("comp_id"));
				}
				Boolean flag = projectService.updateCompanyBase(comp_id, jSONObject, user);
				if (flag == true)
					return 1;
				else
					return 0;
			}
		
			//修改项目信息
			@RequestMapping("/updateProjectById.do")
			public @ResponseBody Integer updateProjectById(HttpServletRequest request, HttpSession session) throws ParseException {
				User user = (User) session.getAttribute(SessionKeyConstants.LOGIN);
				JSONObject jSONObject = JSONObject.fromObject(request.getParameter("project"));
				Integer proj_id = null;
				if (jSONObject.containsKey("proj_id")) {
					proj_id = Integer.parseInt(jSONObject.getString("proj_id"));
				}
				Boolean flag = projectService.updateProjectBase(proj_id, jSONObject, user);
				if (flag == true)
					return 1;
				else
					return 0;
			}
				
				
				//根据公司id查找公司的项目
				@RequestMapping(value = "/getCompProj.do")
				public @ResponseBody String getCompProj(HttpServletRequest request, HttpSession session){
					JSONObject jsonObject = new JSONObject();
					try{
						String searchKey = request.getParameter("comp_id");
						List<Project> result = projectService.getCompProj(searchKey);
						jsonObject.put("result", result);
					} catch (Exception e){
						jsonObject.put("error", "暂未找到相关数据");
					}
					return jsonObject.toString();
				}
				//删除公司信息
				@RequestMapping(value = "/deleteCompanyInfo.do")
				public @ResponseBody String deleteCompanyInfo(HttpServletRequest request, HttpSession session) {
					Integer compid = Integer.valueOf(request.getParameter("companyId"));	
					boolean result = projectService.deleteIsdelete(compid);
					return JSON.toJSONString(result);
				}
				//根据id获取项目信息
				@RequestMapping("/selectProjectById.do")
				public @ResponseBody String selectProjectById(HttpServletRequest request, HttpSession session) {
					int proj_id = Integer.parseInt(request.getParameter("proj_id"));
					session.setAttribute("proj_id", proj_id);
					Project project = projectService.selectProjectById(proj_id);
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("project", project);					
					return jsonObject.toString();
				}
				//删除项目信息
				@RequestMapping(value = "/deleteProjectInfo.do")
				public @ResponseBody String deleteProjectInfo(HttpServletRequest request, HttpSession session) {
					Integer projid = Integer.valueOf(request.getParameter("projectId"));	
					boolean result = projectService.deleteIsdeleted(projid);
					return JSON.toJSONString(result);
				}

/*	//根据公司信息查找项目信息
	@RequestMapping("/getProjectInfo.do")
	public @ResponseBody String getProjectInfo(HttpServletRequest request) {

		JSONObject jsonObject = new JSONObject();
		List<Project> project = new ArrayList<Project>();
		
		if (request.getParameter("company_id") != null){
			project = projectService.getProjectInfo(Integer.parseInt(request.getParameter("company_id")));
		}
		
		jsonObject.put("result", project);
		return jsonObject.toString();
	}*/

}
