package com.mvc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipType;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.Files;
import com.mvc.entityReport.EquipMain;
import com.utils.Pager;
import com.mvc.service.EquipMainService;
import com.mvc.service.EquipmentService;
import com.mvc.service.AlarmLogService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/equipMain")
public class EquipMainController {

	@Autowired
	EquipMainService equipMainService;
	@Autowired
	EquipmentService equipmentService;
	@Autowired
	AlarmLogService alarmLogService;
	
	@RequestMapping(value = "/getEquipMainListByIR.do")
	public @ResponseBody String getEquipMainsByNR(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String eid = request.getParameter("equip_id");
		String emr = request.getParameter("equip_main_result");
		String proj_id = request.getParameter("proj_id");
		List<EquipRoom> room = new ArrayList<EquipRoom>();
		List<Equipment> equip = new ArrayList<Equipment>();
		room = equipmentService.selectEquipRoomByProj(proj_id);
		equip = equipMainService.selectEquipByRoom(room);		
		Integer totalRow = equipMainService.countEmTotal(eid,emr,proj_id);
		Pager pager = new Pager();
		if(request.getParameter("page") != null){
			pager.setPage(Integer.valueOf(request.getParameter("page")));
		}
		if(totalRow != 0){
			pager.setTotalRow(Integer.parseInt(totalRow.toString()));
		}
		List<EquipMain> list = equipMainService.selectEquipMainListByIR(eid,emr,proj_id,pager.getOffset(), pager.getLimit());
		jsonObject.put("list", list);
		jsonObject.put("equip", equip);
		jsonObject.put("totalPage", pager.getTotalPage());
		return jsonObject.toString();
	}
	 
}
