package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entityReport.Company;
import com.mvc.entityReport.Project;
import com.mvc.service.ProjectService;
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
	
	//查找公司信息
	@RequestMapping("/getCompanyInfo.do")
	public @ResponseBody String getCompanyInfo(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
		
		List<Company> result = projectService.getCompanyInfo();
		
		jsonObject.put("result", result);
		return jsonObject.toString();
	}
	
}
