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
@Table(name = "project")
public class Project {
	private Integer proj_id;//项目编号，主键
	private Company company;//公司编号，外键
	private String proj_name;// 项目名称	
	private String proj_rank;// 项目级别
	private String proj_addr;//项目地址
	private Integer proj_num;//业主数量
	private User user;//负责人编号，外键
	private String proj_memo;//备注
	private Integer proj_isdeleted;//项目状态，0：存在，1：已删除
	private String proj_gate;//网关传来项目编号,清远凤城郦都：01020304;展会演示项目：00000001

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "proj_id",length = 11)
	public Integer getProj_id() {
		return proj_id;
	}
	public void setProj_id(Integer proj_id) {
		this.proj_id = proj_id;
	}
	
	@ManyToOne
	@JoinColumn(name="comp_id")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	@Column(name = "proj_name",length = 64)
	public String getProj_name() {
		return proj_name;
	}
	public void setProj_name(String proj_name) {
		this.proj_name = proj_name;
	}
	@Column(name = "proj_rank",length = 32)
	public String getProj_rank() {
		return proj_rank;
	}
	public void setProj_rank(String proj_rank) {
		this.proj_rank = proj_rank;
	}
	@Column(name = "proj_addr",length = 255)
	public String getProj_addr() {
		return proj_addr;
	}
	public void setProj_addr(String proj_addr) {
		this.proj_addr = proj_addr;
	}
	@Column(name = "proj_num",length = 11)
	public Integer getProj_num() {
		return proj_num;
	}
	public void setProj_num(Integer proj_num) {
		this.proj_num = proj_num;
	}
	@ManyToOne
	@JoinColumn(name="user_id")
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = "proj_memo",length = 255)
	public String getProj_memo() {
		return proj_memo;
	}
	public void setProj_memo(String proj_memo) {
		this.proj_memo = proj_memo;
	}
	@Column(name = "proj_isdeleted",length = 1,columnDefinition = "INT not null default 0")
	public Integer getProj_isdeleted() {
		return proj_isdeleted;
	}
	public void setProj_isdeleted(Integer proj_isdeleted) {
		this.proj_isdeleted = proj_isdeleted;
	}
	
	@Column(name = "proj_gate",length = 64)
	public String getProj_gate() {
		return proj_gate;
	}
	public void setProj_gate(String proj_gate) {
		this.proj_gate = proj_gate;
	}
	
}


