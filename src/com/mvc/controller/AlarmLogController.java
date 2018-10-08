package com.mvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mvc.service.EquipmentService;

@Controller
@RequestMapping("/alarmLog")
public class AlarmLogController {

	@Autowired
	EquipmentService equipmentService;

	
}
