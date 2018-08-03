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
import com.base.constants.SessionKeyConstants;
import com.mvc.entityReport.EquipManu;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipType;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.User;
import com.mvc.entityReport.Project;

import com.utils.Pager;
import com.mvc.service.EquipmentService;

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
	
	//根据限制条件type、state筛选设备信息
		@RequestMapping("/getEquipmentListByTS.do")
		public @ResponseBody String getEquipmentByPrarm(HttpServletRequest request, HttpSession session) {
			String eqType = null;
			String eqState = null;
			JSONObject jsonObject = new JSONObject();
			if(request.getParameter("eqType") != null){
				eqType = JSONObject.fromObject(request.getParameter("eqType")).getString("equip_type");
			}
			if(request.getParameter("eqState") != null){
				eqState = JSONObject.fromObject(request.getParameter("eqState")).getString("equip_state");
			}
			Integer totalRow = equipmentService.countTotal(eqType,eqState);
			Pager pager = new Pager();
			pager.setPage(Integer.valueOf(request.getParameter("page")));
			if(totalRow != 0){
				pager.setTotalRow(Integer.parseInt(totalRow.toString()));
			}
			List<Equipment> list = equipmentService.findEquipmentByPage(eqType,eqState,pager.getOffset(), pager.getLimit());
			jsonObject.put("totalPage", pager.getTotalPage());
			jsonObject.put("list", list);
			return jsonObject.toString();
		}
		
		//根据页数筛选设备信息列表
		@RequestMapping(value = "/getEquipmentListByPage.do")
		public @ResponseBody String getEquipmentsByPrarm(HttpServletRequest request, HttpSession session) {
			JSONObject jsonObject = new JSONObject();
			String searchKey = request.getParameter("searchKey");
			Integer totalRow = equipmentService.countEqTotal(searchKey);
			Pager pager = new Pager();
			pager.setPage(Integer.valueOf(request.getParameter("page")));
			pager.setTotalRow(Integer.parseInt(totalRow.toString()));
			List<Equipment> list = equipmentService.selectEquipmentByPage(searchKey, pager.getOffset(), pager.getLimit());
			jsonObject.put("list", list);
			jsonObject.put("totalPage", pager.getTotalPage());
			System.out.println("totalPage:" + pager.getTotalPage());
			return jsonObject.toString();
		}		
		//添加设备信息
		@RequestMapping(value = "/addEquipment.do")
		public @ResponseBody String addEquipment(HttpServletRequest request, HttpSession session) throws ParseException {
			JSONObject jsonObject = new JSONObject();
			jsonObject = JSONObject.fromObject(request.getParameter("equipment"));
			Equipment equipment = new Equipment();
			if (jsonObject.containsKey("equip_no")) {
			equipment.setEquip_no(jsonObject.getString("equip_no"));
			}	
			if (jsonObject.containsKey("equip_name")) {
				equipment.setEquip_name(jsonObject.getString("equip_name"));
				}
//			if (jsonObject.containsKey("equip_pic")) {
//				equipment.setEquip_pic(jsonObject.getString("equip_pic"));}
//			if (jsonObject.containsKey("equip_qrcode")) {
//				equipment.setEquip_qrcode(jsonObject.getString("equip_qrcode"));}
			if (jsonObject.containsKey("equip_spec")) {
				equipment.setEquip_spec(jsonObject.getString("equip_spec"));
			}
			if (jsonObject.containsKey("equip_type")) {
				EquipType et = new EquipType();
				et.setEquip_type_id(Integer.valueOf(jsonObject.getString("equip_type")));
				equipment.setEquip_type(et);	
			}
			if (jsonObject.containsKey("equip_manu")) {
				EquipManu em = new EquipManu();
				em.setEquip_manu_id(Integer.valueOf(jsonObject.getString("equip_manu")));
				equipment.setEquip_manu(em);	
			}
			if (jsonObject.containsKey("equip_pdate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(jsonObject.getString("equip_pdate"));
				equipment.setEquip_pdate(date);
			}
			if (jsonObject.containsKey("equip_bdate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(jsonObject.getString("equip_bdate"));
				equipment.setEquip_bdate(date);
			}
			if (jsonObject.containsKey("equip_idate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(jsonObject.getString("equip_idate"));
				equipment.setEquip_idate(date);
			}
			if (jsonObject.containsKey("equip_udate")) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = sdf.parse(jsonObject.getString("equip_udate"));
				equipment.setEquip_udate(date);
			}
			if (jsonObject.containsKey("equip_ifee")) {
				equipment.setEquip_ifee(Float.parseFloat(jsonObject.getString("equip_ifee")));
			}
			if (jsonObject.containsKey("equip_bfee")) {
				equipment.setEquip_bfee(Float.parseFloat(jsonObject.getString("equip_bfee")));
			}
			if (jsonObject.containsKey("equip_state")) {
				equipment.setEquip_state(Integer.parseInt(jsonObject.getString("equip_state")));
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
			if (jsonObject.containsKey("user")) {
				User eu = new User();
				eu.setUser_id(Integer.valueOf(jsonObject.getString("user")));
				equipment.setUser(eu);	
			}
			if (jsonObject.containsKey("equip_room")) {
				EquipRoom er = new EquipRoom();
				er.setEquip_room_id(Integer.valueOf(jsonObject.getString("equip_room")));
				equipment.setEquip_room(er);	
			}
			
			if (jsonObject.containsKey("equip_memo")) {
				equipment.setEquip_memo(jsonObject.getString("equip_memo"));
			}
			equipment.setEquip_isdeleted(0);
			boolean result;
			if (jsonObject.containsKey("equip_id")) {
				equipment.setEquip_id(Integer.valueOf(jsonObject.getString("equip_id")));
				result = equipmentService.save(equipment);// 添加信息
			} else {
				result = equipmentService.save(equipment); 
			}
			return JSON.toJSONString(result);
		}	
        
		//修改设备信息
		@RequestMapping("/updateEquipmentById.do")
		public @ResponseBody Integer updateEquipmentById(HttpServletRequest request, HttpSession session) throws ParseException {
			User user = (User) session.getAttribute(SessionKeyConstants.LOGIN);
			JSONObject jsonObject = JSONObject.fromObject(request.getParameter("equipment"));
			Integer equip_id = null;
			if (jsonObject.containsKey("equip_id")) {
				equip_id = Integer.parseInt(jsonObject.getString("equip_id"));
			}
			Boolean flag = equipmentService.updateEquipmentBase(equip_id, jsonObject, user);
			if (flag == true)
				return 1;
			else
				return 0;
		}
		
		//根据id获取信息
		@RequestMapping("/selectEquipmentById.do")
		public @ResponseBody String selectEquipmentById(HttpServletRequest request, HttpSession session) {
			int equip_id = Integer.parseInt(request.getParameter("equip_id"));
			session.setAttribute("equip_id", equip_id);
			Equipment equipment = equipmentService.selectEquipmentById(equip_id);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("equipment", equipment);
			return jsonObject.toString();
		}
		
/*		//根据页数筛选安装地点信息列表
				@RequestMapping(value = "/getEquipRoomListByPage.do")
				public @ResponseBody String getEquipRoomsByPrarm(HttpServletRequest request, HttpSession session) {
					JSONObject jsonObject = new JSONObject();
					String searchKey = request.getParameter("searchKey");
					Integer totalRow = equipmentService.countRoomTotal(searchKey);
					Pager pager = new Pager();
					pager.setPage(Integer.valueOf(request.getParameter("page")));
					pager.setTotalRow(Integer.parseInt(totalRow.toString()));
					List<EquipRoom> list = equipmentService.selectEquipRoomByPage(searchKey, pager.getOffset(), pager.getLimit());
					jsonObject.put("list", list);
					jsonObject.put("totalPage", pager.getTotalPage());
					System.out.println("totalPage:" + pager.getTotalPage());
					return jsonObject.toString();
				}
		

		//根据页数筛选项目信息列表
				@RequestMapping(value = "/getProjectListByPage.do")
				public @ResponseBody String getProjectsByPrarm(HttpServletRequest request, HttpSession session) {
					JSONObject jsonObject = new JSONObject();
					String searchKey = request.getParameter("searchKey");
					Integer totalRow = equipmentService.countProjTotal(searchKey);
					Pager pager = new Pager();
					pager.setPage(Integer.valueOf(request.getParameter("page")));
					pager.setTotalRow(Integer.parseInt(totalRow.toString()));
					List<Project> list = equipmentService.selectProjectByPage(searchKey, pager.getOffset(), pager.getLimit());
					jsonObject.put("list", list);
					jsonObject.put("totalPage", pager.getTotalPage());
					System.out.println("totalPage:" + pager.getTotalPage());
					return jsonObject.toString();
				}		*/
				
				
				
				
				
		@RequestMapping("/selectBaseInfoByProj.do")
		public @ResponseBody String selectBaseInfoByProj(HttpServletRequest request, HttpSession session) {
			Pager pager = new Pager();
			String proj_id = null;
			String areaInfo;
			List<EquipRoom> room = new ArrayList<EquipRoom>();
			if(request.getParameter("page") != null){
				pager.setPage(Integer.valueOf(request.getParameter("page")));
			};
			if(request.getParameter("proj_id") != null){
				proj_id = request.getParameter("proj_id");
			};
			
			room = equipmentService.selectEquipRoomByPage(proj_id);
			List<Equipment> list = equipmentService.selectEquipByRoom(room, pager.getOffset(), pager.getLimit());
			
			
			if(request.getParameter("state") != null){
				switch(request.getParameter("state")){
				case "0":;
				case "1":;
				}
				
			};

/*			List<Equipment> list = equipmentService.selectEquipmentByPage(proj_id, pager.getOffset(), pager.getLimit());*/
			
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("equipment", list);
			jsonObject.put("room", room);
			return jsonObject.toString();
		}






}
		
















