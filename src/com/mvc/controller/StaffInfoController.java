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
import com.mvc.entityReport.Role;
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
	 * 删除用户信息
	 * 
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteUser.do")
	public @ResponseBody String deleteUser(HttpServletRequest request, HttpSession session) {
		
		/*Integer wd=222;
		System.out.println("wd:"+ wd);*/
		Integer userid= Integer.valueOf(request.getParameter("userId"));		
		boolean result = staffInfoService.deleteIsdelete(userid);		
		//boolean result = travelService.deleteIsdelete(travelid);
		return JSON.toJSONString(result);
	}	
/*	
 * 批量删除用户信息
 */
	/*private String checkTnum;
    public String getCheckTnum() {
        return checkTnum;
    }

    public void setCheckTnum(String checkTnum) {
        this.checkTnum = checkTnum;
    }*/


/**
 * 添加用户信息
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
	if (jsonObject.containsKey("user_acct")) {
	user.setUser_acct(jsonObject.getString("user_acct"));}
	if (jsonObject.containsKey("user_name")) {
	user.setUser_name(jsonObject.getString("user_name"));}
	if (jsonObject.containsKey("user_pwd")) {
	user.setUser_pwd(jsonObject.getString("user_pwd"));}
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
@RequestMapping(value = "/getAllRoleList.do")
public @ResponseBody String getAllStores(HttpServletRequest request, HttpSession session) {
	List<Role> result = staffInfoService.findRoleAlls();
	return JSON.toJSONString(result);
}
/**
 * 根据页数筛选用户信息列表
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
/**
 * 根据ID修改用户信息
 * 
 * @param request
 * @param session
 * @return 成功返回1，失败返回0
 * @throws ParseException 
 */
@RequestMapping("/updateUserById.do")
public @ResponseBody Integer updateUserById(HttpServletRequest request, HttpSession session) throws ParseException {
	User user = (User) session.getAttribute(SessionKeyConstants.LOGIN);
	JSONObject jsonObject = JSONObject.fromObject(request.getParameter("users"));
	Integer user_id = null;
	if (jsonObject.containsKey("user_id")) {
		user_id = Integer.parseInt(jsonObject.getString("user_id"));
	}
	Boolean flag = staffInfoService.updateUserBase(user_id, jsonObject, user);
	if (flag == true)
		return 1;
	else
		return 0;
}

/**
 * 根据ID获取用户信息
 * 
 * @param request
 * @param session
 * @return Travel对象
 */
@RequestMapping("/selectUserById.do")
public @ResponseBody String selectUserById(HttpServletRequest request, HttpSession session) {
	int	user_id = Integer.parseInt(request.getParameter("user_id"));
	session.setAttribute("user_id", user_id);
	User user = staffInfoService.selectUserById(user_id);
	JSONObject jsonObject = new JSONObject();
	jsonObject.put("user", user);
	return jsonObject.toString();
}

}
	

	