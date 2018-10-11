package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mvc.entityReport.Company;
import com.mvc.entityReport.EquipPara;
import com.mvc.entityReport.Project;


public interface CompanyRepository extends JpaRepository<Company, Integer>{

	@Query("select c from Company c where comp_isdeleted=0")
	public List<Company> getCompanyInfo();
	//根据ID获取公司信息
		@Query("select tr from Company tr where comp_id=:comp_id ")
		public Company selectCompanyById(@Param("comp_id") Integer comp_id);
		
}
