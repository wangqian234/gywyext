package com.mvc.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.MacSpi;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.EquipOper;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Equipment;
import com.mvc.service.EquipmentService;
import com.mvc.service.IndexService;
import com.mvc.service.MobileService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/moblieAdd")
public class MobileController {
	
	@Autowired
	MobileService mobileService;
	@Autowired
	EquipmentService equipmentService;
	@Autowired
	IndexService indexService;

	//手动录入参数运行信息
	@RequestMapping(value = "/addOpeartion.do")
	public @ResponseBody String addOpeartion(HttpServletRequest request, HttpSession session) {
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
			mobileService.addOpeartion(equipOper);
			return JSON.toJSONString("ok");
		}catch(Exception e){
			return JSON.toJSONString(e);
		}
	}
	
	//手动录入报警信息
	@RequestMapping(value = "/addAlarm.do")
	public @ResponseBody String addAlarm(HttpServletRequest request, HttpSession session) {
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
			mobileService.addAlarm(alarmLog);
			return JSON.toJSONString("ok");
		}catch(Exception e){
			return JSON.toJSONString(e);
		}
	}
	
	//获取维保信息
	@RequestMapping(value = "/getmaintenance.do")
	public @ResponseBody String getmaintenance(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		List<EquipMain> equipMains = new ArrayList<EquipMain>();
		try{
			equipMains = mobileService.getmaintenance();
			jsonObject.put("result",equipMains);
		}catch(Exception e){
			return JSON.toJSONString(e);
		}
		return jsonObject.toString();
	}
	
	//根据设备ID获取维保信息
	@RequestMapping(value = "/getMaintenanceById.do")
	public @ResponseBody String getMaintenanceById(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String equip_id = null;
		if (request.getParameter("equip_id") != null) {
		    equip_id = request.getParameter("equip_id");
		}
		List<EquipMain> equipMains = new ArrayList<EquipMain>();
		try{
			equipMains = mobileService.getMaintenanceById(equip_id);
			jsonObject.put("result",equipMains);
		}catch(Exception e){
			return JSON.toJSONString(e);
		}
		return jsonObject.toString();
	}
	
	//根据设备ID获取设备参数信息
	@RequestMapping(value = "/getParaById.do")
	public @ResponseBody String getParaById(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String equip_id = null;
		if (request.getParameter("equip_id") != null) {
		    equip_id = request.getParameter("equip_id");
		}
		List<EquipPara> equipParas = new ArrayList<EquipPara>();
		try{
			equipParas = mobileService.getParaById(equip_id);
			jsonObject.put("result",equipParas);
		}catch(Exception e){
			return JSON.toJSONString(e);
		}
		return jsonObject.toString();
	}
	
	//根据项目编号筛选设备信息
	@RequestMapping("/selectBaseInfoByProj.do")
	public @ResponseBody String selectBaseInfoByProj(HttpServletRequest request, HttpSession session) {
		String proj_id = null;
		List<EquipRoom> room = new ArrayList<EquipRoom>();
		List<Equipment> list = new ArrayList<Equipment>();
		JSONObject jsonObject = new JSONObject();
		if(request.getParameter("proj_id") != null){
			proj_id = request.getParameter("proj_id");
			room = mobileService.getRoomByProId(proj_id);
			list = mobileService.selectEquipByRoomMobile(room);
			jsonObject.put("equipment", list);
//			for(int i=0;i<room.size();i++){
//				room.get(i).setProject(null);
//			}
			jsonObject.put("room", room);

		}
		return jsonObject.toString();
	}
	
	//根据维保时间查找设备
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectEquipmentByN.do")
	public @ResponseBody String selectEquipmentByN(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String proj_id = null;
		if(request.getParameter("proj_id") != null){
			proj_id = request.getParameter("proj_id");
		};			
		List<Map> list = mobileService.selectEquipmentByN(proj_id);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	//根据健康状态查找设备
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectEquipmentByS.do")
	public @ResponseBody String selectEquipmentByS(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String proj_id = null;
		if(request.getParameter("proj_id") != null){
			proj_id = request.getParameter("proj_id");
		};			
		List<Map> list = mobileService.selectEquipmentByS(proj_id);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	//根据项目查找报警
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/selectAlarmByA.do")
	public @ResponseBody String selectAlarmByA(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String proj_id = null;
		if(request.getParameter("proj_id") != null){
			proj_id = request.getParameter("proj_id");
		};			
		List<Map> list = mobileService.selectAlarmByA(proj_id);
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	
	/**
	 * 获取左侧菜单栏
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getInitLeft.do")
	public @ResponseBody String getInitLeft(HttpServletRequest request, HttpSession session) throws ParseException {
		JSONObject jsonObject = new JSONObject();

		List<Map> leftResult = indexService.getInitLeft();
		List<Map> listR = new ArrayList<Map>();
		for(int i=0;i<leftResult.size();i++){
			Map<String, String> map = new HashMap<String, String>();
			if(leftResult.get(i) != null){
				System.out.println(leftResult.get(i).get("proj_id"));
				String proj_id = (String) leftResult.get(i).get("proj_id");
				List<Integer> list = mobileService.selectAllNum(proj_id);
				
				map.put("alart", list.get(0).toString());
				map.put("ndate", list.get(1).toString());
				map.put("state", list.get(2).toString());
				map.put("proj_id", leftResult.get(i).get("proj_id").toString());
				map.put("proj_name", leftResult.get(i).get("proj_id").toString());
				map.put("comp_name", leftResult.get(i).get("proj_id").toString());
				map.put("comp_id", leftResult.get(i).get("proj_id").toString());
				listR.add(map);
			}
		}
		jsonObject.put("left", listR);
		return jsonObject.toString();
	}

}















