package com.mvc.controller;

import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 路由跳转相关
 * 
 * @author
 * @date 2018年7月28日
 */
@Controller
@RequestMapping("/routeController")
public class RouteController extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	//进入系统首页
    @RequestMapping("/toSystemIndex.do")
 	public String systemIndex() {
 		return "system/index";
 	}
    
	//进入用户管理首页
    @RequestMapping("/toStaffBaseInfoPage.do")
 	public String toLoginPage() {
 		return "system/staffInfo/index";
 	}
}
