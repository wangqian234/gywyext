package com.mvc.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.repository.EquipmentRepository;
import com.mvc.repository.EquipRoomRepository;
import com.mvc.repository.EquipTypeRepository;
import com.mvc.repository.EquipManuRepository;
import com.mvc.repository.EquipParaRepository;
import com.mvc.repository.UserRepository;
import com.mvc.repository.ProjectRepository;
import com.mvc.dao.AlarmLogDao;
import com.mvc.dao.EquipMainDao;
import com.mvc.dao.EquipmentDao;
import com.mvc.entityReport.User;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipType;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.Files;
import com.mvc.service.EquipmentService;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.EquipMain;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("equipmentServiceImpl")
public class EquipmentServiceImpl implements EquipmentService {
	@Autowired
	EquipmentRepository equipmentRepository;
	@Autowired
	EquipRoomRepository equipRoomRepository;
	@Autowired
	EquipTypeRepository equipTypeRepository;
	@Autowired
	EquipManuRepository equipManuRepository;
	@Autowired
	EquipParaRepository equipParaRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	EquipmentDao equipmentDao;

	@Autowired
	EquipMainDao equipMainDao;
	@Autowired
	AlarmLogDao alarmLogDao;

	// 根据id删除设备信息
	@Override
	public boolean deleteIsdelete(Integer equip_id) {
		return equipmentDao.updateState(equip_id);
	}

	// 根据room，state筛选信息
	@Override
	public List<Equipment> selectEquipmentByRS(String eqRoom, String eqState, Integer offset, Integer end) {
		return equipmentDao.selectEquipmentByRS(eqRoom, eqState, offset, end);
	}

	// 查询设备总条数
	@Override
	public Integer countEqTotal(String searchKey) {
		return equipmentDao.countEqTotal(searchKey);
	}

	// 根据页数筛选全部设备信息列表
	@Override
	public List<Equipment> selectEquipmentByPage(String searchKey, Integer offset, Integer end) {
		return equipmentDao.selectEquipmentByPage(searchKey, offset, end);
	}

	// 添加信息
	public Equipment save(Equipment equipment) {
		Equipment result = equipmentRepository.saveAndFlush(equipment);
		return result;
	}

	// 修改设备基本信息
	@Override
	public boolean updateEquipmentBase(Integer equip_id, JSONObject jsonObject, User user) throws ParseException {
		Equipment equipment = equipmentRepository.selectEquipmentById(equip_id);
		/*
		 * JSONObject jsonPara = new JSONObject(); List<EquipPara> equipParas =
		 * new ArrayList<EquipPara>();
		 */
		if (equipment != null) {
			if (jsonObject.containsKey("equip_no")) {
				equipment.setEquip_no(jsonObject.getString("equip_no"));
			}
			if (jsonObject.containsKey("equip_name")) {
				equipment.setEquip_name(jsonObject.getString("equip_name"));
			}
			if (jsonObject.containsKey("equip_num")) {
				equipment.setEquip_num(jsonObject.getString("equip_num"));
			}
			/*
			 * if (jsonObject.containsKey("file_id")) { Files file = new
			 * Files();
			 * file.setFile_id(Integer.parseInt(jsonObject.getString("file_id"))
			 * ); equipment.setFile_id(file); }
			 */
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
		}

		equipment = equipmentRepository.saveAndFlush(equipment);

		/*
		 * JSONArray objName = (JSONArray) jsonPara.get("paraname"); JSONArray
		 * objValue = (JSONArray) jsonPara.get("paravalue"); JSONArray objRe =
		 * (JSONArray) jsonPara.get("parare"); JSONArray objUnit = (JSONArray)
		 * jsonPara.get("paraunit"); Object[] paraName = objName.toArray();
		 * Object[] paraValue = objValue.toArray(); Object[] paraRe =
		 * objRe.toArray(); Object[] paraUnit = objUnit.toArray(); for(int
		 * i=0;i<paraValue.length;i++){ EquipPara ep = new EquipPara();
		 * ep.setEquip_para_name(paraName[i].toString());
		 * ep.setEquip_para_rate(Float.parseFloat(paraValue[i].toString()));;
		 * ep.setEquip_para_memo(paraRe[i].toString());
		 * ep.setEquip_para_unit(paraUnit[i].toString());
		 * ep.setEquip_para_isdeleted(0); ep.setEquipment(equipment);
		 * equipParas.add(ep); } for(int i=0;i<equipParas.size();i++){
		 * equipParaRepository.saveAndFlush(equipParas.get(i)); }
		 */
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

	// 根据页数筛选全部设备信息列表
	@Override
	public List<EquipRoom> selectEquipRoomByProj(String searchKey) {
		return equipmentDao.selectEquipRoomByProj(searchKey);
	}

	// 根据安装位置筛选设备信息
	@Override
	public List<Equipment> selectEquipByRoom(List<EquipRoom> room, int offset, int end) {
		List<Integer> roomId = new ArrayList();
		for (int k = 0; k < room.size(); k++) {
			System.out.println(room.get(k).getEquip_room_id());
			roomId.add(room.get(k).getEquip_room_id());
		}
		List<Equipment> list = equipmentDao.selectEquipByRoom(roomId, offset, end);

		return list;
	}

	// 添加设备安装位置信息
	public boolean save(EquipRoom equip_room) {
		EquipRoom result = equipRoomRepository.saveAndFlush(equip_room);
		if (result.getEquip_room_id() != null)
			return true;
		else
			return false;
	}

	/*
	 * //添加设备分类信息 public boolean save(EquipType equip_type) { EquipType result =
	 * equipTypeRepository.saveAndFlush(equip_type); if
	 * (result.getEquip_type_id() != null) return true; else return false; }
	 */

	/*
	 * //添加设备制造商信息 public boolean save(EquipManu equip_manu) { EquipManu result
	 * = equipManuRepository.saveAndFlush(equip_manu); if
	 * (result.getEquip_manu_id() != null) return true; else return false; }
	 */

	// 获取设备分类信息
	@Override
	public List<EquipType> getEquipTypeInfo() {
		return equipTypeRepository.getEquipTypeInfo();
	}

	// 获取设备分类信息
	@Override
	public List<User> getUserInfo() {
		return userRepository.getUserInfo();
	}
	/*
	 * //获取设备制造商信息
	 * 
	 * @Override public List<EquipManu> getEquipManuInfo() { return
	 * equipManuRepository.getEquipManuInfo(); }
	 */

	/*
	 * //添加设备特征参数信息 public boolean save(EquipPara equip_para) { EquipPara result
	 * = equipParaRepository.saveAndFlush(equip_para); if
	 * (result.getEquip_para_id() != null) return true; else return false; }
	 */

	/*
	 * // 根据ID获取用户信息
	 * 
	 * @Override public User selectUserById(Integer user_id) { return
	 * userRepository.selectUserById(user_id); }
	 * 
	 * // 根据ID获取设备特征信息
	 * 
	 * @Override public EquipPara selectEquipParaById(Integer equip_para_id) {
	 * return equipParaRepository.selectEquipParaById(equip_para_id); }
	 */

	/*
	 * // 查询设备维保信息总条数
	 * 
	 * @Override public Integer countEmTotal(String searchKey) { return
	 * equipmentDao.countEmTotal(searchKey); }
	 * 
	 * // 根据页数筛选全部维保信息列表
	 * 
	 * @Override public List<EquipMain> selectEquipMainByPage(String searchKey,
	 * Integer offset, Integer end) { return
	 * equipmentDao.selectEquipMainByPage(searchKey, offset, end); }
	 */

	// 根据设备id查找设备特征参数
	@Override
	public List<EquipPara> getEquipPara(String searchKey) {
		List<EquipPara> list = equipmentDao.getEquipPara(searchKey);
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setEquipment(null);
		}
		return equipmentDao.getEquipPara(searchKey);
	}

	// 添加设备特征信息
	@Override
	public void saveParas(List<EquipPara> equipParas) {
		for (int i = 0; i < equipParas.size(); i++) {
			equipParaRepository.saveAndFlush(equipParas.get(i));
		}
	}

	

}
