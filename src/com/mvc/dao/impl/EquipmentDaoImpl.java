package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.EquipmentDao;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Project;

@Repository("equipmentDaoImpl")
public class EquipmentDaoImpl implements EquipmentDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	//删除设备信息
	public Boolean updateState(Integer equip_id) {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		try {
			String selectSql = " update equipment set equip_isdeleted =:equip_isdeleted where equip_id =:equip_id ";
			Query query = em.createNativeQuery(selectSql);
			query.setParameter("equip_id", equip_id);
			query.setParameter("equip_isdeleted", IsDelete.YES.value);
			query.executeUpdate();
			em.flush();
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		return true;

	}
/*	//根据限制条件筛选信息
		@SuppressWarnings({ "unchecked" })
		@Override
		public Integer countTotal(String eqType, String eqState) {
			// TODO 自动生成的方法存根
			EntityManager em = emf.createEntityManager();
			String countSql = " select count(equip_id) from equipment tr where equip_isdeleted=0 " ;
			if((eqState != null && !eqState.equals("")) && (eqType != null && !eqType.equals(""))){
				countSql += " and equip_state = " + eqState + " and equip_type = " + eqType;
			}
			if((eqState != null && !eqState.equals("")) && (eqType == null || eqType.equals(""))){
				countSql += " and equip_state = " + eqState;
			}
			if((eqType != null && !eqType.equals("")) && (eqState == null || eqState.equals(""))){
				countSql += " and equip_type = " + eqType;
			}
			Query query = em.createNativeQuery(countSql);
			List<Object> totalRow = query.getResultList();
			em.close();
			return Integer.parseInt(totalRow.get(0).toString());
		}
		@SuppressWarnings("unchecked")
		@Override
		public List<Equipment> findEquipmentByPage(String eqType, String eqState, Integer offset, Integer limit) {
			// TODO 自动生成的方法存根
			EntityManager em = emf.createEntityManager();
			String selectSql = " select * from equipment where equip_isdeleted=0 ";
			if((eqState != null && !eqState.equals("")) && (eqType != null && !eqType.equals(""))){
				selectSql += " and equip_state = " + eqState + " and equip_type = " + eqType;
			}
			if((eqState != null && !eqState.equals("")) && (eqType == null || eqType.equals(""))){
				selectSql += " and equip_state = " + eqState;
			}
			if((eqType != null && !eqType.equals("")) && (eqState == null || eqState.equals(""))){
				selectSql += " and equip_type = " + eqType;
			}
			selectSql += " order by equip_type limit :offset , :end ";
			Query query = em.createNativeQuery(selectSql, Equipment.class);
			query.setParameter("offset", offset);
			query.setParameter("end", limit);
			List<Equipment> list = query.getResultList();
			em.close();
			return list;
		}*/
	    
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
			selectSql += " order by equip_id desc limit :offset, :end";
			Query query = em.createNativeQuery(selectSql, Equipment.class);
			query.setParameter("offset", offset);
			query.setParameter("end", end);
			List<Equipment> list = query.getResultList();
			em.close();
			System.out.println(list);
			return list;
		}
	        

			// 根据页数筛选全部设备安装位置信息列表
			@SuppressWarnings("unchecked")
			@Override
			public List<EquipRoom> selectEquipRoomByPage(String searchKey) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equip_room where equip_room_isdeleted=0 and proj_id = '" + searchKey + "'";
				Query query = em.createNativeQuery(selectSql, EquipRoom.class);
				List<EquipRoom> list = query.getResultList();
				em.close();
				System.out.println(list);
				return list;
			}

		   //  查询设备信息总条数
			@SuppressWarnings("unchecked")
			public Integer countProjTotal(String searchKey) {
				EntityManager em = emf.createEntityManager();
				String countSql = " select count(proj_id) from project tr where proj_isdeleted=0 ";
				if (null != searchKey) {
					countSql += " proj_name like '%" + searchKey + "%' ";
				}
				Query query = em.createNativeQuery(countSql);
				List<Object> totalRow = query.getResultList();
				em.close();
				return Integer.parseInt(totalRow.get(0).toString());
			}
			
			
			// 根据页数筛选全部项目信息列表
			@SuppressWarnings("unchecked")
			@Override
			public List<Project> selectProjectByPage(String searchKey, Integer offset, Integer end) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from project where proj_isdeleted=0";
			// 判断查找关键字是否为空
				if (null != searchKey) {
					selectSql +=  " proj_name like '%" + searchKey + "%' ";
				}
				selectSql += " order by proj_id desc limit :offset, :end";
				Query query = em.createNativeQuery(selectSql, Project.class);
				query.setParameter("offset", offset);
				query.setParameter("end", end);
				List<Project> list = query.getResultList();
				em.close();
				System.out.println(list);
				return list;
			}
					
			//根据安装位置筛选设备
			@Override
			public List<Equipment> selectEquipByRoom(List<Integer> roomId, int offset, int end) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equipment where equip_isdeleted=0 and ";
				for(int i=0;i<roomId.size()-1;i++){
					selectSql += " equip_room = '" +roomId.get(i) + "' or ";
				}
				selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ";
				selectSql += " limit :offset, :end";
				Query query = em.createNativeQuery(selectSql, Equipment.class);
				query.setParameter("offset", offset);
				query.setParameter("end", end);
				List<Equipment> list = query.getResultList();
				em.close();
				System.out.println(list);
				return list;
			}

}
