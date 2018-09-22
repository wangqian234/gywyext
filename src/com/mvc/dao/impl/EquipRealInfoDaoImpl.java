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
import com.mvc.entityReport.EquipOper;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.Equipment;

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
			/*System.out.println(list);*/
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
			/*System.out.println("数据流建立成功");*/
			List<EquipOper> list = null;
			EntityManager em = emf.createEntityManager();
			try {
				String selectSql = " select * from equip_oper where  equip_oper_time > '" + startDate + "' and  "
						+ " equip_para_id = '" + searchKey + "'";
				/*String selectSql = " select * from equip_oper where equip_para_id = '" + searchKey + "'";*/
				Query query = em.createNativeQuery(selectSql, EquipOper.class);
				/*query.setParameter("start", start);*/
				/*System.out.println(startDate);*/
				list = query.getResultList();
			} finally {
				em.close();
			}
			return list;
		}

}
