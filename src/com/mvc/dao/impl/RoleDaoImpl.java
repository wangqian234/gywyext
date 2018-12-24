package com.mvc.dao.impl;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.mvc.dao.RoleDao;
import com.mvc.entityReport.Role;
import com.mvc.entityReport.User;
import com.mvc.repository.UserRepository;

/**
 * 角色，职位
 * 
 * @author wanghuimin
 * @date 2016年9月9日
 */
@Repository("roleDaoImpl")
public class RoleDaoImpl implements RoleDao {
	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;
//  查询用户信息总条数
			@SuppressWarnings("unchecked")
			public Integer countTotal() {
				EntityManager em = emf.createEntityManager();
				String countSql = " select count(role_id) from Role where role_isdeleted=0 ";
				Query query = em.createNativeQuery(countSql);
				List<Object> totalRow = query.getResultList();
				em.close();
				return Integer.parseInt(totalRow.get(0).toString());
			}

			// 根据页数筛选全部用户信息列表
			@SuppressWarnings("unchecked")
			@Override
			public List<Role> findRoleAllByPage(Integer offset, Integer end) {
			
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from Role where role_isdeleted=0";
				selectSql += " order by role_id desc limit :offset, :end";
				Query query = em.createNativeQuery(selectSql, Role.class);
				query.setParameter("offset", offset);
				query.setParameter("end", end);
				List<Role> list = query.getResultList();
				em.close();
				System.out.println(list);
				return list;
			}

}
