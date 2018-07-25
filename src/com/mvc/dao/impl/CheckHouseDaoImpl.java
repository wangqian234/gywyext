/**
 * 
 */
package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.CheckHouseDao;
import com.utils.StringUtil;

/**
 * 领班查房效率相关的dao层实现
 * 
 * @author zjn
 * @date 2017年1月17日
 */
@Repository("checkHouseDaoImpl")
public class CheckHouseDaoImpl implements CheckHouseDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	// 获取领班每天查房用时列表
	@Override
	public List<Object> getCheckHouseTime(String startTime, String endTime) {
		EntityManager em = emf.createEntityManager();
		StringBuilder selectSql = new StringBuilder();
		startTime = StringUtil.dayFirstTime(startTime);
		endTime = StringUtil.dayLastTime(endTime);

		selectSql.append(
				"select si.Staff_no staff_no,si.Staff_name staff_name,coalesce(sum(check_time),0) check_time ,coalesce(sum(work_time),0) work_time");
		selectSql.append(",clean_room_workload,checkout_room_workload,overnight_room_workload ");
		selectSql.append(" from work_record wr ");
		selectSql.append(" left join  staff_info si on wr.staff_id=si.Staff_id");
		selectSql.append(" left join staff_dep_rela  sdr on wr.staff_id=sdr.Staff_id");
		selectSql.append(
				" where wr.close_time between '" + startTime + "' and '" + endTime + "' and sdr.Staff_role='领班'");
		selectSql.append(" group by wr.staff_id ");

		Query query = em.createNativeQuery(selectSql.toString());
		@SuppressWarnings("unchecked")
		List<Object> list = query.getResultList();
		em.close();
		return list;
	}
}
