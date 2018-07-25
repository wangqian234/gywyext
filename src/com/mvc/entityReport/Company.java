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
	private String cmop_addr;// 公司地址
	private Integer cmop_num;//公司员工数量
	private User user;//负责人编号，外键
	private String cmop_memo;//备注
	private Integer cmop_isdeleted;//公司状态，0：存在，1：已删除
	
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
	@Column(name = "cmop_addr",length = 255)
	public String getCmop_addr() {
		return cmop_addr;
	}
	public void setCmop_addr(String cmop_addr) {
		this.cmop_addr = cmop_addr;
	}
	@Column(name = "cmop_num",length = 11)
	public Integer getCmop_num() {
		return cmop_num;
	}
	public void setCmop_num(Integer cmop_num) {
		this.cmop_num = cmop_num;
	}
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Column(name = "cmop_memo",length = 255)
	public String getCmop_memo() {
		return cmop_memo;
	}
	public void setCmop_memo(String cmop_memo) {
		this.cmop_memo = cmop_memo;
	}
	@Column(name = "cmop_isdeleted",length = 1)
	public Integer getCmop_isdeleted() {
		return cmop_isdeleted;
	}
	public void setCmop_isdeleted(Integer cmop_isdeleted) {
		this.cmop_isdeleted = cmop_isdeleted;
	}
}
