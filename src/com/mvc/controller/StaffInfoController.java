package com.mvc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import com.mvc.entityReport.User;
import com.mvc.service.IndexService;
import com.mvc.service.StaffInfoService;

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


	
	//if (jsonObject.containsKey("user_acct")) {
	//	travel.setTravel_cprice(Float.parseFloat(jsonObject.getString("user_acct")));
	//}
//	if (jsonObject.containsKey("user_acct")) {
	//	travel.setTravel_insurance(Float.parseFloat(jsonObject.getString("user_acct")));
//	}
//	if (jsonObject.containsKey("user_name")) {
//		DecimalFormat df = new DecimalFormat("#.00");
//		String str = df.format(Float.parseFloat(jsonObject.getString("travel_discount")));
//		travel.setTravel_discount(Float.parseFloat(str));
	//	travel.setTravel_discount(Float.parseFloat(jsonObject.getString("travel_discount")));
	//}
	
	//}
	//user.set_Isdeleted(0);
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
@RequestMapping("/isUserAcctExist.do")
public @ResponseBody Long checkUserName(HttpServletRequest request, HttpSession session, ModelMap map) {
	String userAcct = request.getParameter("userAcct");
	Long result = staffInfoService.isExist(userAcct);
	return result;
}

}
	

	