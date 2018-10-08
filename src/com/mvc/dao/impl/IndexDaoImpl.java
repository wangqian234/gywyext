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

	public List<Company> getInitLeft1() {
		EntityManager em = emf.createEntityManager();
		String selectSql = "select * from company where comp_isdeleted = 0";

		Query query = em.createNativeQuery(selectSql, Company.class);
		List<Company> list = query.getResultList();
		em.close();
		return list;
	}
	
	public List<Project> getInitLeft2() {
		EntityManager em = emf.createEntityManager();
		String selectSql = "select * from project where proj_isdeleted = 0";

		Query query = em.createNativeQuery(selectSql,Project.class);
		List<Project> list = query.getResultList();
		em.close();
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Project> getCompProj(String searchKey) {
		List<Project> list;
		EntityManager em = emf.createEntityManager();
		try {
			String selectSql = " select * from project where proj_isdeleted = 0 and comp_id = '" + searchKey + "'";
			Query query = em.createNativeQuery(selectSql, Project.class);
			list = query.getResultList();
		} finally {
			em.close();
		}
		return list;
	}
	//删除公司信息
		public Boolean updateState(Integer comp_id) {
			EntityManager em = emf.createEntityManager();
			em.getTransaction().begin();
			try {
				String selectSql = " update company set comp_isdeleted =:comp_isdeleted where comp_id =:comp_id ";
				Query query = em.createNativeQuery(selectSql);
				query.setParameter("comp_id", comp_id);
				query.setParameter("comp_isdeleted", 1);
				query.executeUpdate();
				em.flush();
				em.getTransaction().commit();
			} finally {
				em.close();
			}
			return true;

		}
	
		//删除项目信息
				public Boolean updateStated(Integer proj_id) {
					EntityManager em = emf.createEntityManager();
					em.getTransaction().begin();
					try {
						String selectSql = " update project set proj_isdeleted =:proj_isdeleted where proj_id =:proj_id ";
						Query query = em.createNativeQuery(selectSql);
						query.setParameter("proj_id", proj_id);
						query.setParameter("proj_isdeleted", 1);
						query.executeUpdate();
						em.flush();
						em.getTransaction().commit();
					} finally {
						em.close();
					}
					return true;
				}

		//  查询公司信息总条数
			@SuppressWarnings("unchecked")
			public Integer CompCountTotal(String searchKey) {
				EntityManager em = emf.createEntityManager();
				String countSql = " select count(comp_id) from Company where comp_isdeleted=0 ";
				if (null != searchKey) {
					countSql += "   and (comp_name like '%" + searchKey + "%' or comp_addr like '%" + searchKey + "%')";
				}
				Query query = em.createNativeQuery(countSql);
				List<Object> totalRow = query.getResultList();
				em.close();
				return Integer.parseInt(totalRow.get(0).toString());
			}
			
			// 根据页数筛选全部公司信息列表
			@SuppressWarnings("unchecked")
			@Override
			public List<Company> findCompanyByPage(String searchKey, Integer offset, Integer end) {
				EntityManager em = emf.createEntityManager();
				String selectSql = "select * from Company where comp_isdeleted=0";
			// 判断查找关键字是否为空
				if (null != searchKey) {
					selectSql += " and ( comp_name like '%" + searchKey + "%' or comp_addr like '%" + searchKey + "%')";
				}
				selectSql += " order by comp_id desc limit :offset, :end";
				Query query = em.createNativeQuery(selectSql, Company.class);
				query.setParameter("offset", offset);
				query.setParameter("end", end);
				List<Company> list = query.getResultList();
				em.close();
				System.out.println(list);
				return list;
			}
			
		//  查询项目信息总条数
					@SuppressWarnings("unchecked")
					public Integer ProjCountTotal(String searchKey) {
						EntityManager em = emf.createEntityManager();
						String countSql = " select count(proj_id) from Project where proj_isdeleted=0 ";
						Query query = em.createNativeQuery(countSql);
						List<Object> totalRow = query.getResultList();
						em.close();
						return Integer.parseInt(totalRow.get(0).toString());
					   }
					// 根据页数筛选全部项目信息列表
					@SuppressWarnings("unchecked")
					@Override
					public List<Project> findProjectByPage(String searchKey, Integer offset, Integer end) {
						EntityManager em = emf.createEntityManager();
						String selectSql = "select * from Project where proj_isdeleted=0";
						selectSql += " order by proj_id desc limit :offset, :end";
						Query query = em.createNativeQuery(selectSql, Project.class);
						query.setParameter("offset", offset);
						query.setParameter("end", end);
						List<Project> list = query.getResultList();
						em.close();
						System.out.println(list);
						return list;
					}
}
					
