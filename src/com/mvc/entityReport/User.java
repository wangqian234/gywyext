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
@Table(name = "user")
public class User {
	private Integer user_id;//用户编号,主键
	private String user_name;//用户姓名
	private String user_acct;// 用户账号
	private String user_pwd;// 用户密码
	private Role role;// 用户角色编号,外键
	private String user_tel;// 用户手机号码
	private String user_email;// 用户邮箱
	private Integer user_isdeleted;// 用户状态，0存在，1已删除
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id",length = 11)
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	@Column(name = "user_name",length = 32)
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	@Column(name = "user_acct",length = 32)
	public String getUser_acct() {
		return user_acct;
	}
	public void setUser_acct(String user_acct) {
		this.user_acct = user_acct;
	}
	@Column(name = "user_pwd",length = 64)
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	@ManyToOne
	@JoinColumn(name="role_id")
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@Column(name = "user_tel",length = 11)
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	@Column(name = "user_email",length = 64)
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	@Column(name = "user_isdeleted",length = 1,columnDefinition = "INT not null default 0")
	public Integer getUser_isdeleted() {
		return user_isdeleted;
	}
	public void setUser_isdeleted(Integer user_isdeleted) {
		this.user_isdeleted = user_isdeleted;
	}
	
	
}
