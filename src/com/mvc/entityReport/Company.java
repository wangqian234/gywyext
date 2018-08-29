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
@Table(name = "company")
public class Company {
	private Integer comp_id;//公司编号，主键
	private String comp_name;//公司名称
	private String comp_rank;// 公司级别，1：优质，2：一般，3：待开发
	private String comp_addr;// 公司地址
	private Integer comp_num;//公司员工数量
	private User user;//负责人编号，外键
	private String comp_memo;//备注
	private Integer comp_isdeleted;//公司状态，0：存在，1：已删除
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "comp_id",length = 11)
	public Integer getComp_id() {
		return comp_id;
	}
	public void setComp_id(Integer comp_id) {
		this.comp_id = comp_id;
	}
	@Column(name = "comp_name",length = 64)
	public String getComp_name() {
		return comp_name;
	}
	public void setComp_name(String comp_name) {
		this.comp_name = comp_name;
	}
	@Column(name = "comp_rank",length = 32)
	public String getComp_rank() {
		return comp_rank;
	}
	public void setComp_rank(String comp_rank) {
		this.comp_rank = comp_rank;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = "comp_num",length = 11)
	public Integer getComp_num() {
		return comp_num;
	}
	public void setComp_num(Integer comp_num) {
		this.comp_num = comp_num;
	}
	@Column(name = "comp_addr",length = 255)
	public String getComp_addr() {
		return comp_addr;
	}
	public void setComp_addr(String comp_addr) {
		this.comp_addr = comp_addr;
	}
	@Column(name = "comp_memo",length = 255)
	public String getComp_memo() {
		return comp_memo;
	}
	public void setComp_memo(String comp_memo) {
		this.comp_memo = comp_memo;
	}
	@Column(name = "comp_isdeleted",length = 1,columnDefinition = "INT not null default 0")
	public Integer getComp_isdeleted() {
		return comp_isdeleted;
	}
	public void setComp_isdeleted(Integer comp_isdeleted) {
		this.comp_isdeleted = comp_isdeleted;
	}
}
