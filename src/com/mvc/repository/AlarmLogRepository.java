package com.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mvc.entityReport.AlarmLog;
import com.mvc.entityReport.Company;

public interface AlarmLogRepository extends JpaRepository<AlarmLog, Integer>{

	AlarmLog saveAndFlush(AlarmLog alarmLog);

}
