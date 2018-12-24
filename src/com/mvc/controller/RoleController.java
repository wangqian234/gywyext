package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.base.enums.IsDelete;
import com.mvc.entityReport.Role;

import com.mvc.service.RoleService;
import com.utils.Pager;

import net.sf.json.JSONObject;
@Controller
@RequestMapping("/role")
public class RoleController {
	@Autowired
	RoleService roleService;
	
	/**
	 * 根据页数筛选角色列表
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getRoleListByPage.do")
	public @ResponseBody String getRolesByPrarm(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		
		Integer totalRow = roleService.countTotal();
		Pager pager = new Pager();
		pager.setPage(Integer.valueOf(request.getParameter("page")));
		pager.setTotalRow(Integer.parseInt(totalRow.toString()));
		List<Role> list = roleService.findRoleAllByPage( pager.getOffset(), pager.getLimit());
		
		jsonObject.put("list", list);
		jsonObject.put("totalPage", pager.getTotalPage());
		System.out.println("totalPage:" + pager.getTotalPage());
		return jsonObject.toString();
	}
	/**
	 * 添加，修改角色
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/addRole.do")
	public @ResponseBody String addRole(HttpServletRequest request, HttpSession session) {
		Role role = new Role();
		role.setRole_name(request.getParameter("role_name"));
		role.setRole_isdeleted(IsDelete.NO.value);
		role.setRole_permission(request.getParameter("role_permission"));
		boolean result;
		if (request.getParameter("role_id") != null) {
			role.setRole_id(Integer.valueOf(request.getParameter("role_id")));
			result = roleService.save(role);// 修改角色信息
		} else {
			result = roleService.save(role);// 添加角色信息
		}
		return JSON.toJSONString(result);
	}

}
