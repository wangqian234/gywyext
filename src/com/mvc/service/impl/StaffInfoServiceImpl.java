package com.mvc.service.impl;

import java.text.ParseException;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvc.dao.StaffInfoDao;
import com.mvc.entityReport.EquipType;
import com.mvc.entityReport.Role;
import com.mvc.entityReport.User;
import com.mvc.repository.StaffInfoRepository;
import com.mvc.service.StaffInfoService;
import com.utils.MD5;
import com.utils.StringUtil;

import net.sf.json.JSONObject;

@Service("staffInfoServiceImpl")
public class  StaffInfoServiceImpl implements StaffInfoService {
	
	@Autowired
	StaffInfoRepository staffInfoRepository;
	
	@Autowired
	StaffInfoDao staffInfoDao;
	@Override
	public List<User> getStaffInfo() {
		return staffInfoDao.getStaffInfo();
	}

	/**
	 * 添加用户信息
	 */
	public boolean save(User user) {
		User result = staffInfoRepository.saveAndFlush(user);
		if (result.getUser_id() != null)
			return true;
		else
			return false;
	}
	

	// 筛选角色列表
		@Override
		public List<Role> getAllRoleList() {
		return staffInfoDao.getAllRoleList();
		}
	// 根据userAcct查询用户账号是否存在,返回1存在，返回0不存在
		//public Long isExist(String userAcct) {
		//	Long result = staffInfoRepository.countByUserAcct(userAcct);
		//	return result;
		//}
	
	// 根据页数筛选全部用户信息列表
		
		public List<User> findUserByPage(String searchKey, Integer offset, Integer end) {
			return  staffInfoDao.findUserByPage(searchKey, offset, end);
		}
	// 查询总条数
			@Override
			public Integer countTotal(String searchKey) {
				return staffInfoDao.countTotal(searchKey);
			}
		
			// 根据id删除
			@Override
			public boolean deleteIsdelete(Integer user_id) {
				return staffInfoDao.updateState(user_id );
			}
			// 根据id删除角色
			@Override
			public boolean deleteIsdeleteRole(Integer role_id) {
				return staffInfoDao.updateStateRole(role_id);
			}
			// 根据ID获取用户信息
						@Override
						public User selectUserById(Integer user_id) {
							return staffInfoRepository.selectUserById(user_id);
						}
						// 根据ID获取角色信息
						@Override
						public	Role selectRoleById(Integer role_id) {
							return staffInfoRepository.selectRoleById(role_id);
						}
			// 修改用户基本信息
						@Override
						public Boolean updateUserBase(Integer user_id, JSONObject jsonObject, User user) throws ParseException {
							User user1 = staffInfoRepository.selectUserById(user_id);
							if (user1 != null) {
							user1.setUser_acct(jsonObject.getString("user_acct"));
							user1.setUser_name(jsonObject.getString("user_name"));
							user1.setUser_pwd(MD5.encodeByMD5(jsonObject.getString("user_pwd")));
							if (jsonObject.containsKey("user_tel")) {
							user1.setUser_tel(jsonObject.getString("user_tel"));}
							if (jsonObject.containsKey("user_email")) {
							user1.setUser_email(jsonObject.getString("user_email"));}
							if (jsonObject.containsKey("role_id")) {
								Role role = new Role();
								role.setRole_id(Integer.valueOf(jsonObject.getString("role_id")));
								user1.setRole(role);	
							}
						}
	
							user1 = staffInfoRepository.saveAndFlush(user1);		
							if (user1.getUser_id() != null)
								return true;
							else
								return false;
							}
						}
