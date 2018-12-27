package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.EquipMainDao;
import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.Equipment;

@Repository("equipMainDaoImpl")
public class EquipMainDaoImpl implements EquipMainDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@Override
	public Integer countEquipFailNumById(Integer equipId) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		String selectSql = "select count(*) from equip_main where equip_id=:equip_id";
		Query query = em.createNativeQuery(selectSql);
		query.setParameter("equip_id", equipId);
		List<Object> totalRow = query.getResultList();
		em.close();
		return Integer.parseInt(totalRow.get(0).toString());
	}

	@Override
	public List<EquipMain> selectListByEquipId(Integer equipmentId) {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		String selectSql = "select * from equip_main where equip_id=:equipmentId order by equip_id desc "; 
		Query query = em.createNativeQuery(selectSql, EquipMain.class);
		query.setParameter("equipmentId", equipmentId);
		List<EquipMain> list = query.getResultList();
		em.close();
		return list;
	}

	//nanjian维保
	@Override
	public List<Equipment> selectEquipByRoom(List<Integer> roomId) {
		EntityManager em = emf.createEntityManager();
		String selectSql = "select * from equipment where equip_isdeleted=0 and ( ";
		for(int i=0;i<roomId.size()-1;i++){
			selectSql += " equip_room = '" +roomId.get(i) + "' or ";
		}
		selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
		Query query = em.createNativeQuery(selectSql, Equipment.class);
		List<Equipment> list = query.getResultList();
		em.close();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer countEmTotal(String eid,String emr,String proj_id) {
		EntityManager em = emf.createEntityManager();
		String countSql = " select count(equip_main_id) from project right join equip_room on equip_room.proj_id = project.proj_id right join equipment on equipment.equip_room=equip_room.equip_room_id right join equip_main on equipment.equip_id=equip_main.equip_id where project.proj_id = " + proj_id;
		if (!eid.equals("0") && emr.equals("2")) {
			countSql += " and equip_main.equip_id = " + eid ;
		}
		if (eid.equals("0") && !emr.equals("2")) {
			countSql += " and equip_main.equip_main_result = " + emr ;
		}
		if (!eid.equals("0") && !emr.equals("2")) {
			countSql += " and equip_main.equip_id = " + eid + " and equip_main.equip_main_result = " + emr ;
		}	
		Query query = em.createNativeQuery(countSql);
		List<Object> totalRow = query.getResultList();
		em.close();
		return Integer.parseInt(totalRow.get(0).toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EquipMain> selectEquipMainListByIR(String eid, String emr, String proj_id ,Integer offset, Integer end){
		EntityManager em = emf.createEntityManager();
		String selectSql = "select equip_main.equip_main_id,equip_main.equip_main_date,equip_main.equip_main_info,equip_main.equip_main_memo,equip_main.equip_main_result,equip_main.equip_main_time,equip_main.equip_main_worker,equipment.equip_id from project right join equip_room on equip_room.proj_id = project.proj_id right join equipment on equipment.equip_room=equip_room.equip_room_id right join equip_main on equipment.equip_id=equip_main.equip_id where project.proj_id =" + proj_id;
		if (!eid.equals("0") && emr.equals("2")) {
			selectSql += " and equip_main.equip_id = " + eid ;
		}
		if (eid.equals("0") && !emr.equals("2")) {
			selectSql += " and equip_main.equip_main_result = " + emr ;
		}
		if (!eid.equals("0") && !emr.equals("2")) {
			selectSql += " and equip_main.equip_id = " + eid + " and equip_main.equip_main_result = " + emr ;
		}
		selectSql += " order by equip_main_date desc limit :offset, :end";
		Query query = em.createNativeQuery(selectSql, EquipMain.class);
		query.setParameter("offset", offset);
		query.setParameter("end", end);
		List<EquipMain> list = query.getResultList();
		em.close();
		return list;
	}













}
