package com.mvc.dao.impl;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.EquipRealInfoDao;
import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.EquipOper;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.Project;

@Repository("equipRealInfoDaoImpl")
public class EquipRealInfoDaoImpl implements EquipRealInfoDao {
	
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
	
//  查询设备信息总条数
		@SuppressWarnings("unchecked")
		public Integer countEqTotal(String searchKey) {
			EntityManager em = emf.createEntityManager();
			String countSql = " select count(equip_id) from equipment tr where equip_isdeleted=0 ";
			if (null != searchKey) {
				countSql += "   and (equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";
			}
			Query query = em.createNativeQuery(countSql);
			List<Object> totalRow = query.getResultList();
			em.close();
			return Integer.parseInt(totalRow.get(0).toString());
		}
		// 根据页数显示全部设备信息列表
		@SuppressWarnings("unchecked")
		@Override
		public List<Equipment> selectEquipmentByPage(String searchKey, Integer offset, Integer end) {
			EntityManager em = emf.createEntityManager();
			String selectSql = "select * from equipment where equip_isdeleted=0";
		// 判断查找关键字是否为空
			if (null != searchKey) {
				selectSql += " and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";
			}
			selectSql += " order by equip_id asc limit :offset, :end";
			Query query = em.createNativeQuery(selectSql, Equipment.class);
			query.setParameter("offset", offset);
			query.setParameter("end", end);
			List<Equipment> list = query.getResultList();
			em.close();
			return list;
		}
		
		//根据设备id查找设备特征参数
		@SuppressWarnings("unchecked")
		@Override
		public List<EquipPara> getEquipPara(String searchKey) {
			List<EquipPara> list = null;
			EntityManager em = emf.createEntityManager();
			try {
				String selectSql = " select * from equip_para where equip_para_isdeleted = 0 and equip_id = '" + searchKey + "'";
				Query query = em.createNativeQuery(selectSql, EquipPara.class);
				list = query.getResultList();
			} finally {
				em.close();
			}
			return list;
		}
		//根据设备参数名字查找设备特征参数信息
		@SuppressWarnings("unchecked")
		@Override
		public List<EquipPara> getEquipParaByName(String searchKey) {
			List<EquipPara> list = new ArrayList<EquipPara>();
			EntityManager em = emf.createEntityManager();
			try {
				String selectSql = " select * from equip_para where equip_para_isdeleted = 0 and equip_para_name = '" + searchKey + "'";
				Query query = em.createNativeQuery(selectSql, EquipPara.class);
				list = query.getResultList();
			} finally {
				em.close();
			}
			return list;
		}
		//根据设备参数id查询设备参数实时数据
		@SuppressWarnings("unchecked")
		@Override
		public List<EquipOper> getEquipRealData(String searchKey,String startDate) {
			List<EquipOper> list = null;
			EntityManager em = emf.createEntityManager();
			try {
				String selectSql = " select * from equip_oper where  equip_oper_time > '" + startDate + "' and  "
						+ " equip_para_id = '" + searchKey + "'";
				Query query = em.createNativeQuery(selectSql, EquipOper.class);
				list = query.getResultList();
			} finally {
				em.close();
			}
			return list;
		}
		
		//获取设备报警信息
		@SuppressWarnings("unchecked")
		@Override
		public List<AlarmLog> getWaringNews(String searchKey) {
			List<AlarmLog> list =null;
			EntityManager em = emf.createEntityManager();
			System.out.println(searchKey);
			String selectSql = " select * from alarm_log";
			if(null==searchKey) {
				selectSql +=" where  alarm_log_ischecked = 0";
			}
			else {
				selectSql +=" where equipment = '" + searchKey + "'";
			}
			Query query = em.createNativeQuery(selectSql, AlarmLog.class);
			list = query.getResultList();
			em.close();
			return list;
		}
		//获取设备报警信息
		@SuppressWarnings("unchecked")
		@Override
		public List<AlarmLog> getWaringNewsWithOut(String searchKey) {
			List<AlarmLog> list =null;
			EntityManager em = emf.createEntityManager();
			System.out.println(searchKey);
			String selectSql = " select * from alarm_log order by alarm_log_date DESC limit 0 , 50";
			Query query = em.createNativeQuery(selectSql, AlarmLog.class);
			list = query.getResultList();
			em.close();
			return list;
		}
		//根据项目名称和设备名称获取告警信息
		/*@SuppressWarnings("unchecked")
		@Override
		public List<AlarmLog> getEquipAlarmByProAndEquip(String proName, String equipName) {
			List<AlarmLog> list =null;
			EntityManager em = emf.createEntityManager();
			String selectSql = " select alarm_log.* from project left join equip_room "
			+"on equip_room.proj_id=project.proj_id left join equipment"
			+"on equipment.equip_room=equip_room.equip_room_id left join alarm_log"
			+"on alarm_log.equipment=equipment.equip_id "
			+"where project.proj_name='"+proName+"' and equipment.equip_name='"+equipName+"'";
			Query query = em.createNativeQuery(selectSql, AlarmLog.class);
			list = query.getResultList();
			em.close();
			System.out.println(list);
			return list;
		}*/
		//根据起始时间 向后查100条
		@SuppressWarnings("unchecked")
		@Override
		public List<EquipOper> getEquipRealDataByTime(String equip_para_id, String startDate) {
			List<EquipOper> list = null;
			EntityManager em = emf.createEntityManager();
			try {
				String selectSql = " select * from equip_oper where  equip_oper_time > '" + startDate + "' and  "
						+ " equip_para_id = '" + equip_para_id + "' limit 0,100 ";
				Query query = em.createNativeQuery(selectSql, EquipOper.class);
				list = query.getResultList();
			} finally {
				em.close();
			}
			return list;
		}
		//根据项目获取所属设备信息
		@SuppressWarnings("unchecked")
		@Override
		public List<Equipment> getEquipmentListByProject(String searchKey) {
			List<Equipment> list = null;
			EntityManager em = emf.createEntityManager();
			try {
				String selectSql = "select * from equipment where equip_room=any(select equip_room_id from equip_room where proj_id=(select proj_id from project where proj_name='" + searchKey + "') )";
				//String selectSql = "select equipment.*from project left join equip_room on equip_room.proj_id=project.proj_id left join equipment on equipment.equip_room=equip_room.equip_room_id where project.proj_name='"+searchKey+"'";
				Query query = em.createNativeQuery(selectSql, Equipment.class);
				list = query.getResultList();
			} finally {
				em.close();
			}
			return list;
		}
		// 根据公司id获取所属项目信息
		@SuppressWarnings("unchecked")
		@Override
		public List<Project> selectProjectByCompId(String searchKey) {
			List<Project> list = null;
			EntityManager em = emf.createEntityManager();
			try {
				String selectSql = "select *from project where comp_id='"+ searchKey +"'";
				Query query = em.createNativeQuery(selectSql, Project.class);
				list = query.getResultList();
			} finally {
				em.close();
			}
			return list;
		}
}
