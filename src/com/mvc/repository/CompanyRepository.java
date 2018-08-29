package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mvc.entityReport.Company;


public interface CompanyRepository extends JpaRepository<Company, Integer>{

	@Query("select c from Company c where comp_isdeleted=0")
	public List<Company> getCompanyInfo();

}
