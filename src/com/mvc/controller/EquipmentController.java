package com.mvc.controller;

import java.io.File;
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
import com.base.constants.SessionKeyConstants;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipType;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.Files;
import com.mvc.entityReport.User;
import com.mvc.entityReport.Project;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.EquipState;
import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipOper;
import com.utils.Pager;
import com.mvc.service.EquipmentService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/equipEquipment")
public class EquipmentController {
	
	@Autowired
	EquipmentService equipmentService;

	//删除设备信息
	@RequestMapping(value = "/deleteEquipmentInfo.do")
	public @ResponseBody String deleteEquipmentInfo(HttpServletRequest request, HttpSession session) {
		Integer equipmentid = Integer.valueOf(request.getParameter("equipmentId"));	
		boolean result = equipmentService.deleteIsdelete(equipmentid);
		return JSON.toJSONString(result);
	}

	   //根据room，state，searchkey筛选信息
		@RequestMapping(value = "/getEquipmentListByRS.do")
		public @ResponseBody String getEquipmentsByRS(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			String eqRoom = request.getParameter("eqRoom");
			String eqState = request.getParameter("eqState");
			String searchKey = request.getParameter("searchKey");
			String proj_id = null;
			if(request.getParameter("proj_id") != null){
				proj_id = request.getParameter("proj_id");
			};
			List<EquipRoom> room = new ArrayList<EquipRoom>();
			room = equipmentService.selectEquipRoomByProj(proj_id);				
			Integer totalRow = equipmentService.countEqTotal(room,eqRoom,eqState,searchKey);
			Pager pager = new Pager();
			if(request.getParameter("page") != null){
				pager.setPage(Integer.valueOf(request.getParameter("page")));
			}
			if(totalRow != 0){
				pager.setTotalRow(Integer.parseInt(totalRow.toString()));
			}
			List<Equipment> list = equipmentService.selectEquipmentByRS(room,eqRoom,eqState,searchKey,pager.getOffset(), pager.getLimit());
			jsonObject.put("list", list);
			jsonObject.put("room", room);
			jsonObject.put("totalPage", pager.getTotalPage());
			return jsonObject.toString();
		}

		//添加设备信息
		@RequestMapping(value = "/addEquipment.do")
		public @ResponseBody String addEquipment(HttpServletRequest request, HttpSession session) throws ParseException {
			JSONObject jsonObject = new JSONObject();
			JSONObject jsonPara = new JSONObject();
			List<EquipPara> equipParas = new ArrayList<EquipPara>();			
			jsonObject = JSONObject.fromObject(request.getParameter("equipment"));
			Equipment equipment = new Equipment();
			EquipState equipState = new EquipState();
			equipState.setEquip_state_flag(0);
			equipment.setEquip_state(10);
			if (jsonObject.containsKey("equip_no")) {
			    equipment.setEquip_no(jsonObject.getString("equip_no"));
			}	
			if (jsonObject.containsKey("equip_name")) {
			    equipment.setEquip_name(jsonObject.getString("equip_name"));
			}
			if (jsonObject.containsKey("equip_num")) {
				equipment.setEquip_num(jsonObject.getString("equip_num"));
			}		
			if (jsonObject.containsKey("file_id")) {
				Files file = new Files();
				file.setFile_id(Integer.parseInt(jsonObject.getString("file_id")));
				equipment.setFile_id(file);
			}
			if (jsonObject.containsKey("equip_type")) {
				EquipType et = new EquipType();
				et.setEquip_type_id(Integer.valueOf(jsonObject.getString("equip_type")));
				equipment.setEquip_type(et);	
			}
			if (jsonObject.containsKey("equip_manu")) {
				equipment.setEquip_manu(jsonObject.getString("equip_manu"));
				}
			if (jsonObject.containsKey("equip_tel")) {
			    equipment.setEquip_tel(jsonObject.getString("equip_tel"));
			}
			if (jsonObject.containsKey("equip_pdate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(jsonObject.getString("equip_pdate"));
				equipment.setEquip_pdate(date);
			}
			if (jsonObject.containsKey("equip_udate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(jsonObject.getString("equip_udate"));
				equipment.setEquip_udate(date);
			}
			if (jsonObject.containsKey("equip_bfee")) {
				equipment.setEquip_bfee(Float.parseFloat(jsonObject.getString("equip_bfee")));
			}
			if (jsonObject.containsKey("equip_snum")) {
				equipment.setEquip_snum(Integer.parseInt(jsonObject.getString("equip_snum")));
				}
			if (jsonObject.containsKey("equip_mdate")) {
				equipment.setEquip_mdate(Integer.parseInt(jsonObject.getString("equip_mdate")));
				}
			if (jsonObject.containsKey("equip_ndate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(jsonObject.getString("equip_ndate"));
				equipment.setEquip_ndate(date);
			}
			if (jsonObject.containsKey("equip_atime")) {
				equipment.setEquip_atime(Integer.parseInt(jsonObject.getString("equip_atime")));
				}
			if (jsonObject.containsKey("equip_life")) {
				equipment.setEquip_life(Integer.parseInt(jsonObject.getString("equip_life")));
				}	

			if (jsonObject.containsKey("equip_room")) {
				EquipRoom er = new EquipRoom();
				er.setEquip_room_id(Integer.valueOf(jsonObject.getString("equip_room")));
				equipment.setEquip_room(er);	
			}
			if (jsonObject.containsKey("user")) {
				User eu = new User();
				eu.setUser_id(Integer.valueOf(jsonObject.getString("user")));
				equipment.setUser(eu);	
			}	
			if (jsonObject.containsKey("equip_memo")) {
				equipment.setEquip_memo(jsonObject.getString("equip_memo"));
			}
			if (jsonObject.containsKey("equip_type")) {
				EquipType et = new EquipType();
				et.setEquip_type_id(Integer.valueOf(jsonObject.getString("equip_type")));
				equipment.setEquip_type(et);	
			}
			equipment.setEquip_isdeleted(0);			
			Equipment result;
			if (jsonObject.containsKey("equip_id")) {
				equipment.setEquip_id(Integer.valueOf(jsonObject.getString("equip_id")));
				equipState.setEquip_state_id(Integer.valueOf(jsonObject.getString("equip_id")));
				result = equipmentService.save(equipment);				
				jsonPara = JSONObject.fromObject(request.getParameter("equipmentpara"));
				JSONArray objName = (JSONArray) jsonPara.get("paraname");
				JSONArray objValue = (JSONArray) jsonPara.get("paravalue");
				JSONArray objRe = (JSONArray) jsonPara.get("parare");
				JSONArray objUnit = (JSONArray) jsonPara.get("paraunit");
				Object[] paraName = objName.toArray();
				Object[] paraValue = objValue.toArray();
				Object[] paraRe = objRe.toArray();
				Object[] paraUnit = objUnit.toArray();
				for(int i=0;i<paraValue.length;i++){
					EquipPara ep = new EquipPara();
					ep.setEquip_para_name(paraName[i].toString());					
					ep.setEquip_para_rate(Float.parseFloat(paraValue[i].toString()));;
					ep.setEquip_para_memo(paraRe[i].toString());
					ep.setEquip_para_unit(paraUnit[i].toString());
					ep.setEquip_para_isdeleted(0);
					ep.setEquipment(equipment);
					equipParas.add(ep);					
				}
				equipmentService.saveState(equipState);
				equipmentService.saveParas(equipParas);
			} else {
				result = equipmentService.save(equipment);		
				jsonPara = JSONObject.fromObject(request.getParameter("equipmentpara"));
				JSONArray objName = (JSONArray) jsonPara.get("paraname");
				JSONArray objValue = (JSONArray) jsonPara.get("paravalue");
				JSONArray objRe = (JSONArray) jsonPara.get("parare");
				JSONArray objUnit = (JSONArray) jsonPara.get("paraunit");
				Object[] paraName = objName.toArray();
				Object[] paraValue = objValue.toArray();
				Object[] paraRe = objRe.toArray();
				Object[] paraUnit = objUnit.toArray();
				for(int i=0;i<paraValue.length;i++){
					EquipPara ep = new EquipPara();
					ep.setEquip_para_name(paraName[i].toString());
					ep.setEquip_para_rate(Float.parseFloat(paraValue[i].toString()));;
					ep.setEquip_para_memo(paraRe[i].toString());
					ep.setEquip_para_unit(paraUnit[i].toString());
					ep.setEquip_para_isdeleted(0);
					ep.setEquipment(equipment);
					equipParas.add(ep);
				}
				equipmentService.saveParas(equipParas);
				equipmentService.saveState(equipState);
			}			
			return JSON.toJSONString(result);
		}	
		
		//修改设备信息
		@RequestMapping("/updateEquipmentById.do")
		public @ResponseBody Integer updateEquipmentById(HttpServletRequest request, HttpSession session) throws ParseException {

/*			JSONObject jsonPara = new JSONObject();
			List<EquipPara> equipParas = new ArrayList<EquipPara>();*/
			JSONObject jsonObject = JSONObject.fromObject(request.getParameter("equipment"));
			Integer equip_id = null;
			if (jsonObject.containsKey("equip_id")) {
				equip_id = Integer.parseInt(jsonObject.getString("equip_id"));
/*				jsonPara = JSONObject.fromObject(request.getParameter("equipmentpara"));
				JSONArray objName = (JSONArray) jsonPara.get("paraname");
				JSONArray objValue = (JSONArray) jsonPara.get("paravalue");
				JSONArray objRe = (JSONArray) jsonPara.get("parare");
				JSONArray objUnit = (JSONArray) jsonPara.get("paraunit");
				Object[] paraName = objName.toArray();
				Object[] paraValue = objValue.toArray();
				Object[] paraRe = objRe.toArray();
				Object[] paraUnit = objUnit.toArray();
				for(int i=0;i<paraValue.length;i++){
					EquipPara ep = new EquipPara();
					ep.setEquip_para_name(paraName[i].toString());					
					ep.setEquip_para_rate(Float.parseFloat(paraValue[i].toString()));;
					ep.setEquip_para_memo(paraRe[i].toString());
					ep.setEquip_para_unit(paraUnit[i].toString());
					ep.setEquip_para_isdeleted(0);
					ep.setEquipment(equipment);
					equipParas.add(ep);
				}
				equipmentService.saveParas(equipParas);*/
			}
			Boolean flag = equipmentService.updateEquipmentBase(equip_id, jsonObject);
			if (flag == true)
				return 1;
			else
				return 0;
		}
		
		//根据设备id查找设备特征参数
		@RequestMapping(value = "/getEquipPara.do")
		public @ResponseBody String getEquipPara(HttpServletRequest request, HttpSession session){
			JSONObject jsonObject = new JSONObject();
			try{				
				String searchKey = request.getParameter("equip_id");
				List<EquipPara> result = equipmentService.getEquipPara(searchKey);
				jsonObject.put("result", result);
			} catch (Exception e){
				jsonObject.put("error", "暂未找到相关数据");
			}
			return jsonObject.toString();
		}
		
	    //根据设备参数id查找设备特征参数实时数据
	    @RequestMapping(value = "/getEquipRealData.do")
 		public @ResponseBody String getEquipRealData(HttpServletRequest request, HttpSession session){
			JSONObject jsonObject = new JSONObject();
			try{
				String searchKey = request.getParameter("searchKey");
				String startDate = request.getParameter("startDate");
				List<EquipOper> data = equipmentService.getEquipRealData(searchKey,startDate);
				jsonObject.put("data", data);
			} catch (Exception ep){
				jsonObject.put("error", "暂未找到相关数据");
			}
			return jsonObject.toString();
		}
		
 		//根据id获取设备信息(用于设备修改)
		@RequestMapping("/selectEquipmentById.do")
		public @ResponseBody String selectEquipmentById(HttpServletRequest request, HttpSession session) {
			int equip_id = Integer.parseInt(request.getParameter("equip_id"));
			session.setAttribute("equip_id", equip_id);
			Equipment equipment = equipmentService.selectEquipmentById(equip_id);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("equipment", equipment);
			return jsonObject.toString();
		}
		
		//根据项目获取安装位置信息
		@RequestMapping("/selectEquipRoomByProj.do")
		public @ResponseBody String selectEquipRoomByProj(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			String searchKey = request.getParameter("proj_id");
			List<EquipRoom> equip_room = equipmentService.selectEquipRoomByProj(searchKey);			
			jsonObject.put("equip_room", equip_room);
			return jsonObject.toString();
		}

		//查找安装分类信息
		@RequestMapping("/getEquipTypeInfo.do")
		public @ResponseBody String getEquipTypeInfo(HttpServletRequest request) {
			JSONObject jsonObject = new JSONObject();		
			List<EquipType> result = equipmentService.getEquipTypeInfo();		
			jsonObject.put("result", result);
			return jsonObject.toString();
		}

		//查找用户信息
		@RequestMapping("/getUserInfo.do")
		public @ResponseBody String getUserInfo(HttpServletRequest request) {
			JSONObject jsonObject = new JSONObject();		
			List<User> result = equipmentService.getUserInfo();		
			jsonObject.put("result", result);
			return jsonObject.toString();
		}
		
		//添加设备区域
		@RequestMapping(value = "/addEquipRoom.do")
		public @ResponseBody String addEquipRoom(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			EquipRoom equiproom = new EquipRoom();
			jsonObject= JSONObject.fromObject(request.getParameter("equiproom"));

				    Project pro = new Project();
					pro.setProj_id(Integer.valueOf(request.getParameter("proj_id")));
					equiproom.setProject(pro);
					
				if (jsonObject.containsKey("equip_room_name")) {
					equiproom.setEquip_room_name(jsonObject.getString("equip_room_name"));
				}
				if (jsonObject.containsKey("equip_room_memo")) {
					equiproom.setEquip_room_memo(jsonObject.getString("equip_room_memo"));
				}				
				equiproom.setEquip_room_isdeleted(0);			
				boolean result;
				if (jsonObject.containsKey("equip_room_id")) {
					equiproom.setEquip_room_id(Integer.valueOf(jsonObject.getString("equip_room_id")));
					result = equipmentService.saveRoom(equiproom);// 添加信息
				} else {
					result = equipmentService.saveRoom(equiproom); 
				}
				return JSON.toJSONString(result);		
		}
		//删除区域信息
		@RequestMapping(value = "/deleteEquipRoomInfo.do")
		public @ResponseBody String deleteEquipRoomInfo(HttpServletRequest request, HttpSession session) {
			Integer equip_room_id = Integer.valueOf(request.getParameter("equiproomId"));	
			boolean result = equipmentService.deleteRoom(equip_room_id);
			return JSON.toJSONString(result);
		}
}















