package com.mvc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.IndexDao;
import com.mvc.entityReport.Company;
import com.mvc.entityReport.Project;
import com.mvc.service.IndexService;

@Service("indexServiceImpl")
public class IndexServiceImpl implements IndexService {
	@Autowired
	IndexDao indexDao;
	@Override
	public List<Map> getInitLeft() {
		List<Company> listSource1 = indexDao.getInitLeft1();
		List<Project> listSource2 = indexDao.getInitLeft2();
		
		List<Map> listMap = new ArrayList<Map>();
		
		for(int i = 0 ; i < listSource1.size() ; i++){
			for(int j = 0 ; j < listSource2.size() ; j++){
				if(listSource1.get(i).getComp_id() == listSource2.get(j).getCompany().getComp_id()){
					Map<String, String> map = new HashMap<String, String>();
					map.put("comp_id", listSource1.get(i).getComp_id().toString());
					map.put("comp_name", listSource1.get(i).getComp_name());
					map.put("proj_id", listSource2.get(j).getProj_id().toString());
					map.put("proj_name", listSource2.get(j).getProj_name());
					listMap.add(map);
				}
			}
		}
		
		return listMap;
	}

}
