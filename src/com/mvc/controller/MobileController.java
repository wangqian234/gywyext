package com.mvc.controller;

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

import com.alibaba.fastjson.JSON;
import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipOper;
import com.mvc.entityReport.Equipment;
import com.mvc.service.MobileService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/moblieAdd")
public class MobileController {
	
	@Autowired
	MobileService mobileService;

	//手动录入参数运行信息
	@RequestMapping(value = "/addOpeartion.do")
	public @ResponseBody String addOpeartion(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		AlarmLog alarmLog = new AlarmLog();
		EquipOper equipOper = new EquipOper();
		try{
			if (request.getParameter("equip_para_id") != null) {
				equipOper.setEquip_para_id((Integer.parseInt(request.getParameter("equip_para_id"))));
			}	
			if (request.getParameter("equip_operation_info") != null) {
				equipOper.setEquip_oper_info(request.getParameter("equip_operation_info"));
			}
			Date equip_oper_time = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = sdf.format(equip_oper_time);
			equipOper.setEquip_oper_time(str);
			EquipOper equipOper1 = mobileService.addOpeartion(equipOper);
			return JSON.toJSONString("ok");
		}catch(Exception e){
			return JSON.toJSONString(e);
		}
	}
	
	//手动录入报警信息
	@RequestMapping(value = "/addAlarm.do")
	public @ResponseBody String addAlarm(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		AlarmLog alarmLog = new AlarmLog();
		Equipment equipment = new Equipment();
		try{
			if (request.getParameter("equip_id") != null) {
			    equipment.setEquip_id(Integer.parseInt(request.getParameter("equip_id")));
			    alarmLog.setEquipment(equipment);
			}	
			if (request.getParameter("alarmLog_info") != null) {
			    alarmLog.setAlarm_log_info(request.getParameter("alarmLog_info"));
			}
			if (request.getParameter("alarmLog_memo") != null) {
			    alarmLog.setAlarm_log_memo(request.getParameter("alarmLog_memo"));
			}
			Date alarm_log_date = new Date();
			alarmLog.setAlarm_log_date(alarm_log_date);
			alarmLog.setAlarm_log_ischecked(0);
			AlarmLog alarmLog1 = mobileService.addAlarm(alarmLog);
			return JSON.toJSONString("ok");
		}catch(Exception e){
			return JSON.toJSONString(e);
		}
	}
		
}















