package com.mvc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.mvc.entityReport.EquipRoom;
import com.mvc.service.EquipRoomService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/equipRoom")
public class EquipRoomController {
	
	@Autowired
	EquipRoomService equipRoomService;

	
	@RequestMapping(value = "/selectEquipRoomList.do")
	public @ResponseBody String getTasks(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String proId=request.getParameter("proId");
		List<EquipRoom> list = equipRoomService.selectEquipRoomListByproId(Integer.valueOf(proId));
		jsonObject.put("list", list);
		
		return jsonObject.toString();
	}

	
}















