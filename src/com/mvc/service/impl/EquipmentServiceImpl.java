package com.mvc.service.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.repository.EquipmentRepository;
import com.mvc.dao.EquipmentDao;
import com.mvc.entityReport.User;
import com.mvc.entityReport.EquipManu;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipType;
import com.mvc.entityReport.Equipment;
import com.mvc.service.EquipmentService;
import com.mvc.entityReport.Project;

import net.sf.json.JSONObject;

@Service("equipmentServiceImpl")
public class EquipmentServiceImpl implements EquipmentService {
	@Autowired
	EquipmentRepository equipmentRepository;
	@Autowired
	EquipmentDao equipmentDao;

	// 根据id删除设备信息
	@Override
	public boolean deleteIsdelete(Integer equip_id) {
		return equipmentDao.updateState(equip_id);
	}
	/*//根据限制条件筛选信息 
	@Override
	public List<Equipment> findEquipmentByPage(String eqType, String eqState, Integer offset, Integer limit) {
		// TODO 自动生成的方法存根
		return equipmentDao.findEquipmentByPage(eqType,  eqState,  offset,  limit);
	}
	@Override
	public Integer countTotal(String eqType, String eqState) {
		// TODO 自动生成的方法存根
		return equipmentDao.countTotal( eqType,  eqState);
	}	*/
	
	// 查询设备总条数
	@Override
	public Integer countEqTotal(String searchKey) {
		return equipmentDao.countEqTotal(searchKey);
	}
	
	// 根据页数筛选全部设备信息列表
		@Override
		public List<EquipRoom> selectEquipRoomByPage(String searchKey) {
			return equipmentDao.selectEquipRoomByPage(searchKey);
		}	
		
    // 查询项目总条数
		@Override
		public Integer countProjTotal(String searchKey) {
			return equipmentDao.countProjTotal(searchKey);
		}
	// 根据页数筛选全部项目信息列表
		@Override
		public List<Project> selectProjectByPage(String searchKey, Integer offset, Integer end) {
			return equipmentDao.selectProjectByPage(searchKey, offset, end);
		}	
		
		
	// 根据页数筛选全部设备信息列表
	@Override
	public List<Equipment> selectEquipmentByPage(String searchKey, Integer offset, Integer end) {
		return equipmentDao.selectEquipmentByPage(searchKey, offset, end);
	}
	
	//添加信息
	public boolean save(Equipment equipment) {
		Equipment result = equipmentRepository.saveAndFlush(equipment);
		if (result.getEquip_id() != null)
			return true;
		else
			return false;
	}
	// 修改设备基本信息
				@Override
				public boolean updateEquipmentBase(Integer equip_id, JSONObject jsonObject, User user) throws ParseException {
					Equipment equipment = equipmentRepository.selectEquipmentById(equip_id);
					if (equipment != null) {
						if (jsonObject.containsKey("equip_no")) {
							equipment.setEquip_no(jsonObject.getString("equip_no"));
							}	
							if (jsonObject.containsKey("equip_name")) {
								equipment.setEquip_name(jsonObject.getString("equip_name"));
								}
//							if (jsonObject.containsKey("equip_pic")) {
//								equipment.setEquip_pic(jsonObject.getString("equip_pic"));}
//							if (jsonObject.containsKey("equip_qrcode")) {
//								equipment.setEquip_qrcode(jsonObject.getString("equip_qrcode"));}
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
					}
					equipment = equipmentRepository.saveAndFlush(equipment);
					if (equipment.getEquip_id() != null)
						return true;
					else
						return false;
					}
				// 根据ID获取设备信息(用于设备信息的修改）
				@Override
				public Equipment selectEquipmentById(Integer equip_id) {
					return equipmentRepository.selectEquipmentById(equip_id);
				}

				//根据安装位置筛选设备信息
				@Override
				public List<Equipment> selectEquipByRoom(List<EquipRoom> room, int offset, int end) {
					List<Integer> roomId = new ArrayList();;
					for(int k=0;k<room.size();k++){
						System.out.println(room.get(k).getEquip_room_id());
						roomId.add(room.get(k).getEquip_room_id());
					}
					List<Equipment> list = equipmentDao.selectEquipByRoom(roomId,offset, end);
					
					return list;
				}


}
