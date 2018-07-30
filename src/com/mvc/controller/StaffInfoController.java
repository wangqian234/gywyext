package com.mvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
}
