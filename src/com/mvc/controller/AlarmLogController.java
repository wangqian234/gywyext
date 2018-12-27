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

import com.alibaba.fastjson.JSON;
import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Equipment;
import com.utils.Pager;
import com.mvc.service.AlarmLogService;
import com.mvc.service.EquipmentService;
import com.mvc.service.EquipMainService;
import com.mvc.service.MobileService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/alarmLog")
public class AlarmLogController {

	@Autowired
	AlarmLogService alarmLogService;
	@Autowired
	EquipmentService equipmentService;
	@Autowired
	EquipMainService equipMainService;
	@Autowired
	MobileService mobileService;
	
	//获取报警信息
	 @RequestMapping(value = "/getAlarmListByPage.do")
		public @ResponseBody String getAlarmsByPrarm(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			String proj_id = request.getParameter("proj_id");
			String searchKey = request.getParameter("searchKey");
			Integer totalRow = alarmLogService.countAlarmTotal(proj_id,searchKey);
			Pager pager = new Pager();
			pager.setPage(Integer.valueOf(request.getParameter("page")));
			pager.setTotalRow(Integer.parseInt(totalRow.toString()));
			List<AlarmLog> list = alarmLogService.getAlarmListByPage(proj_id ,searchKey, pager.getOffset(), pager.getLimit());
			jsonObject.put("list", list);
			jsonObject.put("totalPage", pager.getTotalPage());
			return jsonObject.toString();
		}
	 
	//手动录入报警信息
		@RequestMapping(value = "/addAlarm.do")
		public @ResponseBody String addAlarm(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			AlarmLog alarmlog = new AlarmLog();
			jsonObject= JSONObject.fromObject(request.getParameter("alarmlog"));
				if (jsonObject.containsKey("equip_id")) {
				    Equipment equipment = new Equipment();
					equipment.setEquip_id(Integer.valueOf(jsonObject.getString("equip_id")));
				    alarmlog.setEquipment(equipment);
				}	
				if (jsonObject.containsKey("alarm_log_info")) {
				    alarmlog.setAlarm_log_info(jsonObject.getString("alarm_log_info"));
				}
				if (jsonObject.containsKey("alarm_log_memo")) {
				    alarmlog.setAlarm_log_memo(jsonObject.getString("alarm_log_memo"));
				}				
				Date alarm_log_date = new Date();
				alarmlog.setAlarm_log_date(alarm_log_date);
				alarmlog.setAlarm_log_ischecked(0);			
				boolean result;
				if (jsonObject.containsKey("alarm_log_id")) {
					alarmlog.setAlarm_log_id(Integer.valueOf(jsonObject.getString("alarm_log_id")));
					result = alarmLogService.save(alarmlog);// 添加信息
				} else {
					result = alarmLogService.save(alarmlog); 
				}
				return JSON.toJSONString(result);		
		}
	 
		//根据project查找设备
		@RequestMapping(value = "/selectEquipByProj.do")
		public @ResponseBody String selectEquipByProj(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			String proj_id = request.getParameter("proj_id");
			List<EquipRoom> room = new ArrayList<EquipRoom>();
			List<Equipment> equip = new ArrayList<Equipment>();
			room = equipmentService.selectEquipRoomByProj(proj_id);
			equip = equipMainService.selectEquipByRoom(room);
			jsonObject.put("equip", equip);
			return jsonObject.toString();
		}
		
		
	 
	@RequestMapping(value = "/getProEquipAnalysis.do")
	public @ResponseBody String getProEquipAnalysis(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String proj_id = request.getParameter("proj_id");
		JSONArray result = alarmLogService.getProEquipAnalysis(proj_id);

		jsonObject.put("analysis", result);
		return jsonObject.toString();
	}
	
	 
}
