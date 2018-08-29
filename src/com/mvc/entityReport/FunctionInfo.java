package com.mvc.entityReport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="function_info")
public class FunctionInfo {

	private Integer function_id;//功能号id，主键
	private String function_num;//功能号编码
	private String function_remark;//功能号备注
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "function_id",unique=true,nullable=false, length = 11)
	public Integer getFunction_id() {
		return function_id;
	}
	public void setFunction_id(Integer function_id) {
		this.function_id = function_id;
	}
	public String getFunction_num() {
		return function_num;
	}
	public void setFunction_num(String function_num) {
		this.function_num = function_num;
	}
	public String getFunction_remark() {
		return function_remark;
	}
	public void setFunction_remark(String function_remark) {
		this.function_remark = function_remark;
	}

}
