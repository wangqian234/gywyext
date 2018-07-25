package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.IndexDao;
import com.mvc.entityReport.Company;
import com.mvc.entityReport.Project;

@Repository("indexDaoImpl")
public class IndexDaoImpl implements IndexDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> getInitLeft1() {
		EntityManager em = emf.createEntityManager();
		String selectSql = "select * from company";

		Query query = em.createNativeQuery(selectSql, Company.class);
		List<Company> list = query.getResultList();
		em.close();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getInitLeft2() {
		EntityManager em = emf.createEntityManager();
		String selectSql = "select * from project";

		Query query = em.createNativeQuery(selectSql,Project.class);
		List<Project> list = query.getResultList();
		em.close();
		return list;
	}
}
