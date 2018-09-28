package com.mvc.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.MobileDao;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.Equipment;

@Repository("mobileDaoImpl")
public class MobileDaoImpl implements MobileDao {
	
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@Override
	public List<Equipment> selectEquipByRoomMobile(List<Integer> list) {
		EntityManager em = emf.createEntityManager();
		String selectSql = "select * from equipment where equip_isdeleted=0 and ( ";
		for(int i=0;i<list.size()-1;i++){
			selectSql += " equip_room = '" +list.get(i) + "' or ";
		}
		selectSql += " equip_room = '" +list.get(list.size()-1) + "' ) ";
		Query query = em.createNativeQuery(selectSql);
		List<Equipment> list1 = query.getResultList();
		em.close();
		return list1;
	}
	
}
