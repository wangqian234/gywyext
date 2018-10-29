package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.EquipMainDao;
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

}
