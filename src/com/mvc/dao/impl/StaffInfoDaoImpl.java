package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.StaffInfoDao;
import com.mvc.entityReport.User;

@Repository("staffInfoDaoImpl")
public class StaffInfoDaoImpl implements StaffInfoDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getStaffInfo() {
		EntityManager em = emf.createEntityManager();
		String selectSql = "select * from user";

		Query query = em.createNativeQuery(selectSql, User.class);
		List<User> list = query.getResultList();
		em.close();
		return list;
	}

}
