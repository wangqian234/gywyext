package com.mvc.entityReport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="gateway_info")
public class GatewayInfo {

	private Integer gateway_id;//网关id，主键
	private String gateway_name;//网关名称
	private String gateway_ip;//网关套接字
	private Project project;//项目id

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "gateway_id",unique=true,nullable=false, length = 11)
	public Integer getGateway_id() {
		return gateway_id;
	}
	public void setGateway_id(Integer gateway_id) {
		this.gateway_id = gateway_id;
	}
	public String getGateway_name() {
		return gateway_name;
	}
	public void setGateway_name(String gateway_name) {
		this.gateway_name = gateway_name;
	}
	public String getGateway_ip() {
		return gateway_ip;
	}
	public void setGateway_ip(String gateway_ip) {
		this.gateway_ip = gateway_ip;
	}
	
	@ManyToOne
	@JoinColumn(name="proj_id")
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}






}
