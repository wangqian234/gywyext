package com.mvc.dao.impl;

import java.util.Date;
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
import com.mvc.entityReport.EquipOper;
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
		public List<Equipment> selectEquipmentByRS(List<Integer> roomId ,String eqRoom, String eqState,String searchKey ,Integer offset, Integer end) {
			EntityManager em = emf.createEntityManager();			
			String selectSql = " select * from equipment where equip_isdeleted=0 " ;								
			if (null != searchKey) {				
				if (eqState.equals("0") && eqRoom.equals("0")) {
					selectSql += " and ( ";
					for(int i=0;i<roomId.size()-1;i++){
						selectSql += " equip_room = '" +roomId.get(i) + "' or ";
					}
					selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%') ";
				}
				if(eqState.equals("0") && !eqRoom.equals("0")){
					selectSql += " and equip_room = " + eqRoom + " and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";
				}
				if(eqState.equals("1") && !eqRoom.equals("0")){
					selectSql += " and (equip_state = 10 or equip_state = 9 or equip_state = 8 or equip_state = 7)" + " and equip_room = " + eqRoom +
							" and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";
				}
				if(eqState.equals("1") && eqRoom.equals("0")){
					selectSql += " and (equip_state = 10 or equip_state = 9 or equip_state = 8 or equip_state = 7)" +
							" and ( ";
					for(int i=0;i<roomId.size()-1;i++){
						selectSql += " equip_room = '" +roomId.get(i) + "' or ";
					}
					selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%') ";				
				}				
				if(eqState.equals("2") && !eqRoom.equals("0")){
					selectSql += " and (equip_state = 3 or equip_state = 4 or equip_state = 5 or equip_state = 6)" + " and equip_room = " + eqRoom +
							" and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";
				}
				if(eqState.equals("2") && eqRoom.equals("0")){
					selectSql += " and (equip_state = 3 or equip_state = 4 or equip_state = 5 or equip_state = 6)" + 
							" and ( ";
					for(int i=0;i<roomId.size()-1;i++){
						selectSql += " equip_room = '" +roomId.get(i) + "' or ";
					}
					selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%') ";
				}		
				 
				if(eqState.equals("3") && !eqRoom.equals("0")){
					selectSql += " and (equip_state = 1 or equip_state = 2)" + "and equip_room = " + eqRoom +
							" and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";	
				}
				if(eqState.equals("3") && eqRoom.equals("0")){
					selectSql += " and (equip_state = 1 or equip_state = 2)" + 
							" and ( ";
					for(int i=0;i<roomId.size()-1;i++){
						selectSql += " equip_room = '" +roomId.get(i) + "' or ";
					}
					selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%') ";
				}	
				if(eqState.equals("4") && !eqRoom.equals("0")){
					selectSql += " and equip_state = 0" + " and equip_room = " + eqRoom +
							" and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";
				}
				if(eqState.equals("4") && eqRoom.equals("0")){
					selectSql += " and equip_state = 0" +
							" and ( ";
					for(int i=0;i<roomId.size()-1;i++){
						selectSql += " equip_room = '" +roomId.get(i) + "' or ";
					}
					selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%') ";
				}
				}else if(null == searchKey){
					if (eqState.equals("0") && eqRoom.equals("0")) {
						selectSql += " and ( ";
								for(int i=0;i<roomId.size()-1;i++){
									selectSql += " equip_room = '" +roomId.get(i) + "' or ";
								}
								selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
					}
					if(eqState.equals("0") && !eqRoom.equals("0")){
						selectSql += " and equip_room = " + eqRoom;
					}
					if(eqState.equals("1") && !eqRoom.equals("0")){
						selectSql += " and (equip_state = 10 or equip_state = 9 or equip_state = 8 or equip_state = 7)" + " and equip_room = " + eqRoom;
					}
					if(eqState.equals("1") && eqRoom.equals("0")){
						selectSql += " and (equip_state = 10 or equip_state = 9 or equip_state = 8 or equip_state = 7)" +
								" and ( ";
						for(int i=0;i<roomId.size()-1;i++){
							selectSql += " equip_room = '" +roomId.get(i) + "' or ";
						}
						selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
					}				
					if(eqState.equals("2") && !eqRoom.equals("0")){
						selectSql += " and (equip_state = 3 or equip_state = 4 or equip_state = 5 or equip_state = 6)" + " and equip_room = " + eqRoom;
					}
					if(eqState.equals("2") && eqRoom.equals("0")){
						selectSql += " and (equip_state = 3 or equip_state = 4 or equip_state = 5 or equip_state = 6)" + 
								" and ( ";
						for(int i=0;i<roomId.size()-1;i++){
							selectSql += " equip_room = '" +roomId.get(i) + "' or ";
						}
						selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
					}					
					if(eqState.equals("3") && !eqRoom.equals("0")){
						selectSql += " and (equip_state = 1 or equip_state = 2)" + "and equip_room = " + eqRoom;
					}
					if(eqState.equals("3") && eqRoom.equals("0")){
						selectSql += " and (equip_state = 1 or equip_state = 2)" + 
								" and ( ";
						for(int i=0;i<roomId.size()-1;i++){
							selectSql += " equip_room = '" +roomId.get(i) + "' or ";
						}
						selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
					}	
					if(eqState.equals("4") && !eqRoom.equals("0")){
						selectSql += " and equip_state = 0" + " and equip_room = " + eqRoom;
					}
					if(eqState.equals("4") && eqRoom.equals("0")){
						selectSql += " and equip_state = 0" +
								" and ( ";
						for(int i=0;i<roomId.size()-1;i++){
							selectSql += " equip_room = '" +roomId.get(i) + "' or ";
						}
						selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
					}
				}
			selectSql += " order by equip_id desc limit :offset, :end";			
			Query query = em.createNativeQuery(selectSql, Equipment.class);			
			query.setParameter("offset", offset);
			query.setParameter("end", end);			
			List<Equipment> list = query.getResultList();
			em.close();			
			return list;
		}
	    
		//  查询设备信息总条数(翻页功能)
		@SuppressWarnings("unchecked")
		public Integer countEqTotal(List<Integer> roomId,String eqRoom,String eqState,String searchKey) {
			EntityManager em = emf.createEntityManager();
			String countSql = " select count(equip_id) from equipment where equip_isdeleted=0 ";
			if (null != searchKey) {				
			if (eqState.equals("0") && eqRoom.equals("0")) {
				countSql += " and ( ";
				for(int i=0;i<roomId.size()-1;i++){
					countSql += " equip_room = '" +roomId.get(i) + "' or ";
				}
				countSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%') ";
			}
			if(eqState.equals("0") && !eqRoom.equals("0")){
				countSql += " and equip_room = " + eqRoom + " and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";
			}
			if(eqState.equals("1") && !eqRoom.equals("0")){
				countSql += " and (equip_state = 10 or equip_state = 9 or equip_state = 8 or equip_state = 7)" + " and equip_room = " + eqRoom +
						" and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";
			}
			if(eqState.equals("1") && eqRoom.equals("0")){
				countSql += " and (equip_state = 10 or equip_state = 9 or equip_state = 8 or equip_state = 7)" +
						" and ( ";
				for(int i=0;i<roomId.size()-1;i++){
					countSql += " equip_room = '" +roomId.get(i) + "' or ";
				}
				countSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%') ";				
			}				
			if(eqState.equals("2") && !eqRoom.equals("0")){
				countSql += " and (equip_state = 3 or equip_state = 4 or equip_state = 5 or equip_state = 6)" + " and equip_room = " + eqRoom +
						" and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";
			}
			if(eqState.equals("2") && eqRoom.equals("0")){
				countSql += " and (equip_state = 3 or equip_state = 4 or equip_state = 5 or equip_state = 6)" + 
						" and ( ";
				for(int i=0;i<roomId.size()-1;i++){
					countSql += " equip_room = '" +roomId.get(i) + "' or ";
				}
				countSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%') ";
			}		
			 
			if(eqState.equals("3") && !eqRoom.equals("0")){
				countSql += " and (equip_state = 1 or equip_state = 2)" + "and equip_room = " + eqRoom +
						" and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";	
			}
			if(eqState.equals("3") && eqRoom.equals("0")){
				countSql += " and (equip_state = 1 or equip_state = 2)" + 
						" and ( ";
				for(int i=0;i<roomId.size()-1;i++){
					countSql += " equip_room = '" +roomId.get(i) + "' or ";
				}
				countSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%') ";
			}	
			if(eqState.equals("4") && !eqRoom.equals("0")){
				countSql += " and equip_state = 0" + " and equip_room = " + eqRoom +
						" and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%')";
			}
			if(eqState.equals("4") && eqRoom.equals("0")){
				countSql += " and equip_state = 0" +
						" and ( ";
				for(int i=0;i<roomId.size()-1;i++){
					countSql += " equip_room = '" +roomId.get(i) + "' or ";
				}
				countSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) and ( equip_name like '%" + searchKey + "%' or equip_no like '%" + searchKey + "%') ";
			}
			}else if(null == searchKey){
				if (eqState.equals("0") && eqRoom.equals("0")) {
					countSql += " and ( ";
							for(int i=0;i<roomId.size()-1;i++){
								countSql += " equip_room = '" +roomId.get(i) + "' or ";
							}
							countSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
				}
				if(eqState.equals("0") && !eqRoom.equals("0")){
					countSql += " and equip_room = " + eqRoom;
				}
				if(eqState.equals("1") && !eqRoom.equals("0")){
					countSql += " and (equip_state = 10 or equip_state = 9 or equip_state = 8 or equip_state = 7)" + " and equip_room = " + eqRoom;
				}
				if(eqState.equals("1") && eqRoom.equals("0")){
					countSql += " and (equip_state = 10 or equip_state = 9 or equip_state = 8 or equip_state = 7)" +
							" and ( ";
					for(int i=0;i<roomId.size()-1;i++){
						countSql += " equip_room = '" +roomId.get(i) + "' or ";
					}
					countSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
				}				
				if(eqState.equals("2") && !eqRoom.equals("0")){
					countSql += " and (equip_state = 3 or equip_state = 4 or equip_state = 5 or equip_state = 6)" + " and equip_room = " + eqRoom;
				}
				if(eqState.equals("2") && eqRoom.equals("0")){
					countSql += " and (equip_state = 3 or equip_state = 4 or equip_state = 5 or equip_state = 6)" + 
							" and ( ";
					for(int i=0;i<roomId.size()-1;i++){
						countSql += " equip_room = '" +roomId.get(i) + "' or ";
					}
					countSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
				}					
				if(eqState.equals("3") && !eqRoom.equals("0")){
					countSql += " and (equip_state = 1 or equip_state = 2)" + "and equip_room = " + eqRoom;
				}
				if(eqState.equals("3") && eqRoom.equals("0")){
					countSql += " and (equip_state = 1 or equip_state = 2)" + 
							" and ( ";
					for(int i=0;i<roomId.size()-1;i++){
						countSql += " equip_room = '" +roomId.get(i) + "' or ";
					}
					countSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
				}	
				if(eqState.equals("4") && !eqRoom.equals("0")){
					countSql += " and equip_state = 0" + " and equip_room = " + eqRoom;
				}
				if(eqState.equals("4") && eqRoom.equals("0")){
					countSql += " and equip_state = 0" +
							" and ( ";
					for(int i=0;i<roomId.size()-1;i++){
						countSql += " equip_room = '" +roomId.get(i) + "' or ";
					}
					countSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
				}
			}			
			Query query = em.createNativeQuery(countSql);
			List<Object> totalRow = query.getResultList();
			em.close();
			return Integer.parseInt(totalRow.get(0).toString());
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
					
			//根据安装位置信息获取设备信息
			@Override
			public List<Equipment> selectEquipByRoom(List<Integer> roomId, int offset, int end) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equipment where equip_isdeleted=0 and ( ";
				for(int i=0;i<roomId.size()-1;i++){
					selectSql += " equip_room = '" +roomId.get(i) + "' or ";
				}
				selectSql += " equip_room = '" +roomId.get(roomId.size()-1) + "' ) ";
				selectSql += " order by equip_id desc limit :offset, :end";
				Query query = em.createNativeQuery(selectSql, Equipment.class);
				query.setParameter("offset", offset);
				query.setParameter("end", end);
				List<Equipment> list = query.getResultList();
				em.close();
				System.out.println(list);
				return list;
			}

			//根据id获取特征参数信息
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


			@SuppressWarnings("unchecked")
			@Override
			public List<Object> selectEquipmentByN(String proj_id, String timenow) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select equip_id,equip_name,equip_memo from project right join equip_room on equip_room.proj_id = project.proj_id right join equipment on equipment.equip_room=equip_room.equip_room_id where project.proj_id = :proj_id and equipment.equip_ndate < :timenow"; 
				Query query = em.createNativeQuery(selectSql);
				query.setParameter("proj_id", proj_id);
				query.setParameter("timenow", timenow);
				List<Object> list = query.getResultList();
				em.close();
				return list;
			}

			@SuppressWarnings("unchecked")
			@Override
			public List<Object> selectEquipmentByS(String proj_id) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select equip_id,equip_name,equip_memo from project right join equip_room on equip_room.proj_id = project.proj_id right join equipment on equipment.equip_room=equip_room.equip_room_id where project.proj_id = :proj_id and (equipment.equip_state = 0 or equipment.equip_state = 1 or equipment.equip_state = 2)"; 
				Query query = em.createNativeQuery(selectSql);
				query.setParameter("proj_id", proj_id);
				List<Object> list = query.getResultList();
				em.close();
				return list;
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public Integer getNdateNum(String proj_id, String timenow) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select count(*) from project right join equip_room on equip_room.proj_id = project.proj_id right join equipment on equipment.equip_room=equip_room.equip_room_id where project.proj_id = :proj_id and equipment.equip_ndate < :timenow"; 
				Query query = em.createNativeQuery(selectSql);
				query.setParameter("proj_id", proj_id);
				query.setParameter("timenow", timenow);
				List<Object> list = query.getResultList();
				em.close();
				return Integer.parseInt(list.get(0).toString());
			}

			@SuppressWarnings("unchecked")
			@Override
			public Integer getStateNum(String proj_id) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select count(*) from project right join equip_room on equip_room.proj_id = project.proj_id right join equipment on equipment.equip_room=equip_room.equip_room_id where project.proj_id = :proj_id and (equipment.equip_state = 0 or equipment.equip_state = 1 or equipment.equip_state = 2)"; 
				Query query = em.createNativeQuery(selectSql);
				query.setParameter("proj_id", proj_id);
				List<Object> list = query.getResultList();
				em.close();
				return Integer.parseInt(list.get(0).toString());
			}


			@Override
			public Integer getEquipMainNumByProId(Integer proId,Date updateDate) {
				// TODO Auto-generated method stub
				EntityManager em = emf.createEntityManager();
				String selectSql = "select count(*) from equipment where equip_isdeleted='0' and equip_ndate<:updateDate and equip_room in (select equip_room_id from equip_room where proj_id =:proj_id)"; 
				Query query = em.createNativeQuery(selectSql);
				query.setParameter("updateDate", updateDate);
				query.setParameter("proj_id", proId);
				List<Object> totalRow = query.getResultList();
				em.close();
				return Integer.parseInt(totalRow.get(0).toString());
			}

			@Override
			public Integer getEquipUnhealthNumByProId(Integer proId) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select count(*) from equipment where equip_isdeleted='0' and equip_state in (0,1,2) and equip_room in (select equip_room_id from equip_room where proj_id =:proj_id)"; 
				Query query = em.createNativeQuery(selectSql);
				query.setParameter("proj_id", proId);
				List<Object> totalRow = query.getResultList();
				em.close();
				return Integer.parseInt(totalRow.get(0).toString());

			}

			@Override
			public List<Equipment> selectIndexMainEquipList(Integer proId, Integer offset, Integer limit,Date updateDate) {
				// TODO Auto-generated method stub
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equipment where equip_isdeleted='0' and equip_ndate>now() and equip_ndate<:updateDate and equip_room in (select equip_room_id from equip_room where proj_id =:proj_id) order by equip_id desc limit :offset, :end"; 
				Query query = em.createNativeQuery(selectSql,Equipment.class);
				query.setParameter("updateDate", updateDate);
				query.setParameter("proj_id", proId);
				query.setParameter("offset", offset);
				query.setParameter("end", limit);
				List<Equipment> list = query.getResultList();
				em.close();
				return list;
			}

			@Override
			public List<Equipment> selectIndexUnhealthEquip(Integer proId, Integer offset, Integer limit) {
				// TODO Auto-generated method stub
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from equipment where equip_isdeleted='0' and equip_state in (0,1,2) and equip_room in (select equip_room_id from equip_room where proj_id =:proj_id) order by equip_id desc limit :offset, :end"; 
				Query query = em.createNativeQuery(selectSql,Equipment.class);
				query.setParameter("proj_id", proId);
				query.setParameter("offset", offset);
				query.setParameter("end", limit);
				List<Equipment> list = query.getResultList();
				em.close();
				return list;
			}

}
