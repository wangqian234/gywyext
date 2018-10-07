package com.mvc.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.mvc.dao.EquipRoomDao;
import com.mvc.dao.EquipmentDao;
import com.mvc.entityReport.Equipment;
import com.mvc.entityReport.EquipRoom;
import com.mvc.entityReport.EquipMain;
import com.mvc.entityReport.EquipPara;

@Repository("equipRoomDaoImpl")
public class EquipRoomDaoImpl implements EquipRoomDao {

	@Autowired
	@Qualifier("entityManagerFactory")
	EntityManagerFactory emf;

	

}
