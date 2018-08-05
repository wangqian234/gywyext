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

import com.mvc.entityReport.User;

import com.mvc.service.StaffInfoService;
import com.utils.Pager;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/systemStaff")
public class StaffInfoController {
	
	@Autowired
	StaffInfoService staffInfoService;

	//	获取员工信息
	@RequestMapping("/getStaffInfo.do")
	public @ResponseBody String getStaffInfo(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();

		List<User> result = staffInfoService.getStaffInfo();
		jsonObject.put("Result", result);
		return jsonObject.toString();
	}
	/**
	 * 删除旅游信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteUser.do")
	public @ResponseBody String deleteUser(HttpServletRequest request, HttpSession session) {
		Integer userId = Integer.valueOf(request.getParameter("user_id"));
		boolean result = staffInfoService.deleteIsdelete(userId);
		//boolean result = travelService.deleteIsdelete(travelid);
		return JSON.toJSONString(result);
	}	



/**
 * 添加,修改用户信息
 * 
 * @param request
 * @param session
 * @return
 * @throws ParseException 
 */
@RequestMapping(value = "/addStaff.do")
public @ResponseBody String addStaff(HttpServletRequest request, HttpSession session) throws ParseException {
	JSONObject jsonObject = new JSONObject();
	jsonObject = JSONObject.fromObject(request.getParameter("staff"));
	User user = new User();
	user.setUser_acct(jsonObject.getString("user_acct"));
	if (jsonObject.containsKey("user_name")) {
	user.setUser_name(jsonObject.getString("user_name"));}
	if (jsonObject.containsKey("user_tel")) {
	user.setUser_tel(jsonObject.getString("user_tel"));}
	if (jsonObject.containsKey("user_email")) {
	user.setUser_email(jsonObject.getString("user_email"));}
	user.setUser_isdeleted(0);
	boolean result;
	if (jsonObject.containsKey("user_id")) {
		user.setUser_id(Integer.valueOf(jsonObject.getString("User_id")));
		result = staffInfoService.save(user);// 修改信息
	} else {
		result = staffInfoService.save(user);// 添加信息
	}
	return JSON.toJSONString(result);
}

/**
 * 筛选角色列表
 * 
 * @param request
 * @param session
 * @return
 */
//@RequestMapping(value = "/getAllRoleList.do")
//public @ResponseBody String getAllStores(HttpServletRequest request, HttpSession session) {
	//List<User> result = staffInfoService.findRoleAlls();
	//return JSON.toJSONString(result);
/**
 * 根据页数筛选旅游信息列表
 * 
 * @param request
 * @param session
 * @return
 */
@RequestMapping(value = "/getUserListByPage.do")
public @ResponseBody String getUsersByPrarm(HttpServletRequest request, HttpSession session) {
	JSONObject jsonObject = new JSONObject();
	String searchKey = request.getParameter("searchKey");
	Integer totalRow = staffInfoService.countTotal(searchKey);
	Pager pager = new Pager();
	pager.setPage(Integer.valueOf(request.getParameter("page")));
	pager.setTotalRow(Integer.parseInt(totalRow.toString()));
	List<User> list = staffInfoService.findUserByPage(searchKey, pager.getOffset(), pager.getLimit());
	jsonObject.put("list", list);
	jsonObject.put("totalPage", pager.getTotalPage());
	System.out.println("totalPage:" + pager.getTotalPage());
	return jsonObject.toString();
}


}
	

	