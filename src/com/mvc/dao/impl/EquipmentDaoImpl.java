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
import com.mvc.entityReport.EquipPara;

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
			query.setParameter("equip_isdeleted", 1);
			query.executeUpdate();
			em.flush();
			em.getTransaction().commit();
		} finally {
			em.close();
		}
		return true;

	}

	//根据room，state筛选信息
		@SuppressWarnings("unchecked")
		@Override
		public List<Equipment> selectEquipmentByRS(String eqRoom, String eqState, Integer offset, Integer end) {
			EntityManager em = emf.createEntityManager();
			String selectSql = " select * from equipment where equip_isdeleted=0 ";
			if((eqState != null && !eqState.equals("")) && (eqRoom != null && !eqRoom.equals(""))){
				selectSql += " and equip_state = " + eqState + " and equip_room = " + eqRoom;
			}
			if((eqState != null && !eqState.equals("")) && (eqRoom == null || eqRoom.equals(""))){
				selectSql += " and equip_state = " + eqState;
			}
			if((eqRoom != null && !eqRoom.equals("")) && (eqState == null || eqState.equals(""))){
				selectSql += " and equip_room = " + eqRoom;
			}
			if((eqRoom == null || eqRoom.equals("")) && (eqState == null || eqState.equals(""))){
				selectSql += " ";
			}
			selectSql += " order by equip_id desc limit :offset, :end";
			Query query = em.createNativeQuery(selectSql, Equipment.class);
			query.setParameter("offset", offset);
			query.setParameter("end", end);
			List<Equipment> list = query.getResultList();
			em.close();
			return list;
		}
	    
		//  查询设备信息总条数
		@SuppressWarnings("unchecked")
		public Integer countEqTotal(String searchKey) {
			EntityManager em = emf.createEntityManager();
			String countSql = " select count(equip_id) from equipment where equip_isdeleted=0 ";
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
			return list;
		}

			// 根据页数筛选全部设备安装位置信息列表
			@SuppressWarnings("unchecked")
			@Override
			public List<EquipRoom> selectEquipRoomByProj(String searchKey) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equip_room where equip_room_isdeleted=0 and proj_id = '" + searchKey + "'";
				Query query = em.createNativeQuery(selectSql, EquipRoom.class);
				List<EquipRoom> list = query.getResultList();
				em.close();
				return list;
			}
					
			//根据安装位置筛选设备
			@Override
			public List<Equipment> selectEquipByRoom(List<Integer> roomId, int offset, int end) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equipment where equip_isdeleted=0 and ( ";
				for(int i=0;i<roomId.size()-1;i++){
					selectSql += " equip_room = '" +roomId.get(i) + "' or ";
				}
				selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
				selectSql += " limit :offset, :end";
				Query query = em.createNativeQuery(selectSql, Equipment.class);
				query.setParameter("offset", offset);
				query.setParameter("end", end);
				List<Equipment> list = query.getResultList();
				em.close();
				System.out.println(list);
				return list;
			}
                                                 /*设备维保信息表内容*/
		/*//  查询信息总条数
			@SuppressWarnings("unchecked")
			public Integer countEmTotal(String searchKey) {
				EntityManager em = emf.createEntityManager();
				String countSql = " select count(equip_main_id) from equip_main";
				if (null != searchKey) {
					countSql +=" equip_main_info like '%" + searchKey + "%' ";
				}
				Query query = em.createNativeQuery(countSql);
				List<Object> totalRow = query.getResultList();
				em.close();
				return Integer.parseInt(totalRow.get(0).toString());
			}*/
			/*// 根据页数显示全部信息列表
			@SuppressWarnings("unchecked")
			@Override
			public List<EquipMain> selectEquipMainByPage(String searchKey, Integer offset, Integer end) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equip_main ";
			// 判断查找关键字是否为空
				if (null != searchKey) {
					selectSql += "  equip_main_info like '%" + searchKey + "%' ";
				}
				selectSql += " order by equip_main_info desc limit :offset, :end";
				Query query = em.createNativeQuery(selectSql, EquipMain.class);
				query.setParameter("offset", offset);
				query.setParameter("end", end);
				List<EquipMain> list = query.getResultList();
				em.close();
				System.out.println(list);
				return list;
			}*/

			@SuppressWarnings("unchecked")
			@Override
			public List<EquipPara> getEquipPara(String searchKey) {
				List<EquipPara> list;
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

			
			//zq
			@Override
			public List<Equipment> findEquipListByRoomId(Integer roomId, Integer offset, Integer limit) {
				// TODO Auto-generated method stub
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equipment where equip_room=:roomId order by equip_id desc limit :offset, :end"; 
				Query query = em.createNativeQuery(selectSql, Equipment.class);
				query.setParameter("roomId", roomId);
				query.setParameter("offset", offset);
				query.setParameter("end", limit);
				List<Equipment> list = query.getResultList();
				em.close();
				return list;
			}

			@Override
			public Integer selectEquipNumByRoomId(Integer roomId) {
				// TODO Auto-generated method stub
				EntityManager em = emf.createEntityManager();
				String selectSql = "select count(*) from equipment where equip_room=:roomId"; 
				Query query = em.createNativeQuery(selectSql);
				query.setParameter("roomId", roomId);
				List<Object> totalRow = query.getResultList();
				em.close();
				return Integer.parseInt(totalRow.get(0).toString());
			}

			@Override
			public List<Equipment> selectAllEquipByRoomId(Integer roomId) {
				// TODO Auto-generated method stub
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equipment where equip_room=:roomId order by equip_id"; 
				Query query = em.createNativeQuery(selectSql, Equipment.class);
				query.setParameter("roomId", roomId);
				List<Equipment> list = query.getResultList();
				em.close();
				return list;
			}

			@Override
			public Equipment selectEquipmentById(Integer equipId) {
				// TODO Auto-generated method stub
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equipment where equip_id=:equipId"; 
				Query query = em.createNativeQuery(selectSql, Equipment.class);
				query.setParameter("equipId", equipId);
				List<Equipment> list = query.getResultList();
				em.close();
				Equipment e=new Equipment();
				if(list.size()==0){
					e=null;
				}else{
					e=list.get(0);
				}
				return e;
			}

}
