package com.mvc.controller;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.base.constants.SessionKeyConstants;
import com.utils.StringUtil;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipType;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.Files;
import com.mvc.entityReport.User;
import com.mvc.entityReport.Project;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipMain;

import com.utils.Pager;
import com.mvc.service.AlarmLogService;
import com.mvc.service.BigDataService;
import com.mvc.service.EquipmentService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/bigData")
public class BigDataController {

	@Autowired
	EquipmentService equipmentService;

	@Autowired
	BigDataService bigDataService;
	
	@Autowired
	AlarmLogService alarmLogService;

	// zq
	// 根据位置id查找设备列表
	@RequestMapping(value = "/selectEquipListByRoomId.do")
	public @ResponseBody String selectEquipListByRoomId(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String roomId = request.getParameter("roomId");
		List<Equipment> list = null;
		Pager pager = new Pager();
		if (StringUtil.strIsNotEmpty(roomId)) {
			Integer totalRow = bigDataService.selectEquipNumByRoomId(Integer.valueOf(roomId));
			pager.setPage(Integer.parseInt(request.getParameter("page")));// 指定页码
			pager.setTotalRow(Integer.parseInt(totalRow.toString()));
			list = bigDataService.findEquipListByRoomId(Integer.valueOf(roomId), pager.getOffset(), pager.getLimit());
			/*JSONArray result = bigDataService.failureAnalysis(Integer.valueOf(roomId));*/
			jsonObject.put("list", list);
			jsonObject.put("totalPage", pager.getTotalPage());
			/*jsonObject.put("analysis", result);*/
		} else {
			jsonObject.put("list", null);
		}

		return jsonObject.toString();
	}

	// zq
	// 根据位置id查找设备列表
	@RequestMapping(value = "/getEquipRadarById.do")
	public @ResponseBody String getEquipRadarById(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject = new JSONObject();
		String equipmentId = request.getParameter("equipmentId");
		JSONArray result = bigDataService.getEquipRadarById(Integer.valueOf(equipmentId));
		JSONArray arr = new JSONArray();
		JSONObject o1 = new JSONObject();
		o1.put("name", "可靠性");
		o1.put("max", 1);
		JSONObject o2 = new JSONObject();
		o2.put("name", "健康指数");
		o2.put("max", 1);
		JSONObject o3 = new JSONObject();
		o3.put("name", "剩余寿命");
		o3.put("max", 1);
		String warning="";
		if(Float.valueOf(result.get(1).toString())>0.8){
			warning="设备运行良好，不需要检修!";
		}else if(Float.valueOf(result.get(1).toString())>0.6&&Float.valueOf(result.get(1).toString())<0.8){
			warning="设备工作正常，但存在隐患，建议检修...";
		}else{
			warning="设备运行不稳定，请及时安排人进行检修!";
		}
		arr.add(o1);
		arr.add(o2);
		arr.add(o3);
		jsonObject.put("result", result);
		jsonObject.put("typeArray", arr);
		jsonObject.put("warning", warning);
		return jsonObject.toString();
	}
	
	//zq设备故障分析
	// zq
		// 根据位置id查找设备列表
		@RequestMapping(value = "/getRoomEquipAnalysisByRoomId.do")
		public @ResponseBody String getRoomEquipAnalysisByRoomId(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			String roomId = request.getParameter("roomId");
			List<Equipment> list = null;
			Pager pager = new Pager();
			if (StringUtil.strIsNotEmpty(roomId)) {
				Integer totalRow = bigDataService.selectEquipNumByRoomId(Integer.valueOf(roomId));
				pager.setPage(Integer.parseInt(request.getParameter("page")));// 指定页码
				pager.setTotalRow(Integer.parseInt(totalRow.toString()));
				list = bigDataService.findEquipListByRoomId(Integer.valueOf(roomId), pager.getOffset(), pager.getLimit());
				JSONArray result = bigDataService.failureAnalysis(Integer.valueOf(roomId));
				jsonObject.put("list", list);
				jsonObject.put("totalPage", pager.getTotalPage());
				jsonObject.put("analysis", result);
			} else {
				jsonObject.put("list", null);
			}

			return jsonObject.toString();
		}

	
	// zq
		// 根据位置id查找设备列表
		@RequestMapping(value = "/getEquipPreById.do")
		public @ResponseBody String getEquipPreById(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			String equipmentId = request.getParameter("equipmentId");
			JSONArray mainDate = bigDataService.getEquipPreById(Integer.valueOf(equipmentId));
			JSONObject o=bigDataService.getEquipFailCountById(Integer.valueOf(equipmentId));
			List<AlarmLog> list = alarmLogService.getAlarmListByEquipId(equipmentId);
			
			jsonObject.put("result", mainDate);
			jsonObject.put("xAxis", o.get("xAxis"));
			jsonObject.put("data", o.get("data"));
			jsonObject.put("list", list);
			return jsonObject.toString();
		}
		
		@RequestMapping(value = "/getEquipFailById.do")
		public @ResponseBody String getEquipFailById(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			String equipmentId = request.getParameter("equipmentId");
			JSONArray result = bigDataService.getEquipFailById(equipmentId);
			jsonObject.put("analysis", result);
			return jsonObject.toString();
		}

}
