package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.base.enums.IsDelete;
import com.mvc.dao.StaffInfoDao;
import com.mvc.entityReport.Role;
import com.mvc.entityReport.User;

@Repository("staffInfoDaoImpl")
public  class StaffInfoDaoImpl implements StaffInfoDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getStaffInfo() {
		EntityManager em = emf.createEntityManager();
		String selectSql = "select * from user";
		/*String selectSql = "select u from User  u where user_isdeleted=0";*/
		Query query = em.createNativeQuery(selectSql, User.class);
		List<User> list = query.getResultList();
		em.close();
		return list;
	}
	
	
		//  查询用户信息总条数
			@SuppressWarnings("unchecked")
			public Integer countTotal(String searchKey) {
				EntityManager em = emf.createEntityManager();
				String countSql = " select count(user_id) from User where user_isdeleted=0 ";
				if (null != searchKey) {
					countSql += "   and (user_name like '%" + searchKey + "%' or user_acct like '%" + searchKey + "%')";
				}
				Query query = em.createNativeQuery(countSql);
				List<Object> totalRow = query.getResultList();
				em.close();
				return Integer.parseInt(totalRow.get(0).toString());
			}

			// 根据页数筛选全部用户信息列表
			@SuppressWarnings("unchecked")
			@Override
			public List<User> findUserByPage(String searchKey, Integer offset, Integer end) {
			
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from User where user_isdeleted=0";
			// 判断查找关键字是否为空
				if (null != searchKey) {
					selectSql += " and ( user_name like '%" + searchKey + "%' or user_acct like '%" + searchKey + "%')";
				}
				selectSql += " order by user_id desc limit :offset, :end";
				Query query = em.createNativeQuery(selectSql, User.class);
				query.setParameter("offset", offset);
				query.setParameter("end", end);
				List<User> list = query.getResultList();
				em.close();
				System.out.println(list);
				return list;
			}
			
			/**
			 * 删除用户信息
			 */
			public Boolean updateState(Integer user_id) {
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				try {
					String selectSql = " update user set user_isdeleted =:user_isdeleted where user_id=:user_id";
					Query query = em.createNativeQuery(selectSql);
					query.setParameter("user_id", user_id);
					query.setParameter("user_isdeleted", IsDelete.YES.value);
					query.executeUpdate();
					em.flush();
					em.getTransaction().commit();
				} finally {
					em.close();
				}
				return true;

			}
			/**
			 * 删除角色信息
			 */
			public Boolean updateStateRole(Integer role_id) {
				EntityManager em = emf.createEntityManager();
				em.getTransaction().begin();
				try {
					String selectSql = " update role set role_isdeleted =:role_isdeleted where role_id=:role_id";
					Query query = em.createNativeQuery(selectSql);
					query.setParameter("role_id", role_id);
					query.setParameter("role_isdeleted", IsDelete.YES.value);
					query.executeUpdate();
					em.flush();
					em.getTransaction().commit();
				} finally {
					em.close();
				}
				return true;

			}


			@Override
			public List<Role> getAllRoleList() {
				EntityManager em = emf.createEntityManager();
				String countSql = " select * from Role where role_isdeleted = 0 ";
				Query query = em.createNativeQuery(countSql, Role.class);
				List<Role> role = query.getResultList();
				em.close();
				return role;
			}

	
		

		
}
