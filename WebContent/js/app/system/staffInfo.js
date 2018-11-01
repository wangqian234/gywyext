var app = angular
		.module(
				'staffInfoApp',
				[ 'ngRoute' ],
				function($httpProvider) {// ngRoute引入路由依赖
					$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
					$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

					// Override $http service's default transformRequest
					$httpProvider.defaults.transformRequest = [ function(data) {
						/**
						 * The workhorse; converts an object to
						 * x-www-form-urlencoded serialization.
						 * 
						 * @param {Object}
						 *            obj
						 * @return {String}
						 */
						var param = function(obj) {
							var query = '';
							var name, value, fullSubName, subName, subValue, innerObj, i;

							for (name in obj) {
								value = obj[name];

								if (value instanceof Array) {
									for (i = 0; i < value.length; ++i) {
										subValue = value[i];
										fullSubName = name + '[' + i + ']';
										innerObj = {};
										innerObj[fullSubName] = subValue;
										query += param(innerObj) + '&';
									}
								} else if (value instanceof Object) {
									for (subName in value) {
										subValue = value[subName];
										fullSubName = name + '[' + subName
												+ ']';
										innerObj = {};
										innerObj[fullSubName] = subValue;
										query += param(innerObj) + '&';
									}
								} else if (value !== undefined
										&& value !== null) {
									query += encodeURIComponent(name) + '='
											+ encodeURIComponent(value) + '&';
								}
							}

							return query.length ? query.substr(0,
									query.length - 1) : query;
						};

						return angular.isObject(data)
								&& String(data) !== '[object File]' ? param(data)
								: data;
					} ];
				});
//获取权限列表
var permissionList;
angular.element(document).ready(function() {
	console.log("获取权限列表！");
	$.get('/gywyext/login/getUserPermission.do', function(data) {
		permissionList = data; //
		/*angular.bootstrap($("#user"), [ 'user' ]); // 手动加载angular模块
*/	});
});

app.directive('hasPermission', function($timeout) {
	return {
		restrict : 'ECMA',
		link : function(scope, element, attr) {
			setTimeout(function(){
				var key = attr.hasPermission.trim(); // 获取页面上的权限值
				var keys = permissionList;
				/*alert(keys);*/
				var regStr = "\\s" + key + "\\s";
				var reg = new RegExp(regStr);
				if (keys.search(reg) < 0) {
					element.css("display", "none");
				}
			},0)
		}
	};
});
app.run([ '$rootScope', '$location', function($rootScope, $location) {
	$rootScope.$on('$routeChangeSuccess', function(evt, next, previous) {
		console.log('路由跳转成功');
		$rootScope.$broadcast('reGetData');
	});
} ]);

// 路由配置
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/staffBaseInfo', {
		templateUrl : '/gywyext/jsp/system/staffInfo/staffBaseInfo.html',
		controller : 'staffInfoController'
	}).when('/staffAdd', {
		templateUrl : '/gywyext/jsp/system/staffInfo/staffAdd.html',
		controller : 'staffInfoController'
	}).when('/userList', {
		templateUrl : '/gywyext/jsp/system/staffInfo/userList.html',
		controller : 'staffInfoController'
	}).when('/userUpdate', {
		templateUrl : '/gywyext/jsp/system/staffInfo/userUpdate.html',
		controller : 'staffInfoController'
	}).when('/roleUpdate', {
		templateUrl : '/gywyext/jsp/system/staffInfo/roleUpdate.html',
		controller : 'staffInfoController'
	}).when('/roleAdd', {
		templateUrl : '/gywyext/jsp/system/staffInfo/roleAdd.html',
		controller : 'staffInfoController'
	}).when('/roleDetail', {
		templateUrl : '/gywyext/jsp/system/staffInfo/roleDetail.html',
		controller : 'staffInfoController'
	}).when('/roleList', {
		templateUrl : '/gywyext/jsp/system/staffInfo/roleList.html',
		controller : 'staffInfoController'
	})
}]);

app.constant('baseUrl', '/gywyext/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	services.getStaffInfo = function() {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/getStaffInfo.do',
		});
	};
	services.addStaff = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/addStaff.do',
			data : data
		});
	};
	services.addRole = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'role/addRole.do',
			data : data
		});
	};
	services.deleteUser = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/deleteUser.do',
			data : data
		});
	};
	services.deleteRole = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/deleteRole.do',
			data : data
		});
	};
	
	services.getUserListByPage = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/getUserListByPage.do',
			data : data
		});
	};
	services.selectuserByName = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/selectuserByName.do',
			data : data
		});
	};
	services.selectUserById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/selectUserById.do',
			data : data
		});
	};
	services.selectRoleById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/selectRoleById.do',
			data : data
		});
	};
	services.updateUserById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/updateUserById.do',
			data : data,
		});
	};
	/*services.updateRoleById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/updateRoleById.do',
			data : data,
		});
	};*/
	services.getRoleListByPage = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'role/getRoleListByPage.do',
			data : data
		});
	};
	services.getAllRoleList = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/getAllRoleList.do',
			data : data
		});
	};
	
	return services;
} ]);
app
		.controller(
				'staffInfoController',
				[
						'$scope',
						'services',
						'$location',
						function($scope, services, $location) {
							var staffInfo = $scope;
							var roles;
							
							staffInfo.staff = {
									user_acct:"",
									user_name:"",
									user_email:'',
									user_tel:"",
									user_sex:'' ,
									role_id:" ", 
									user_pwd:"",/*设计编辑功能的时候可以输入进去123deng，新增""*/
									role:""	
							};
/*							staffInfo.addStaff = function(){
								alert(JSON.stringify(staffInfo.staff))//alert:网页打开时弹窗提示
							}
							*/
							
							// 换页
							function pageTurn(totalPage, page, Func) {
								$(".tcdPageCode").empty();
								var pa = 1
								var $pages = $(".tcdPageCode");
								if ($pages.length != 0) {
									$(".tcdPageCode").createPage({
										pageCount : totalPage,
										current : page,
										backFn : function(p) {
											pa = p;
											Func(p);
										}
									});
								}
							}
						
				// 添加用户信息
			staffInfo.addStaff = function() {
					/*alert(JSON.stringify(staffInfo.staff))
					 if(!((/^0\d{2,3}-?\d{7,8}$/.test(staffInfo.staff.user_tel))||(/^1(3|4|5|7|8)\d{9}$/.test(staffInfo.staff.user_tel)))){
						 alert("电话格式有误，请重填!");
						 return;*/
					
					var user = JSON.stringify(staffInfo.staff);
					if(staffInfo.staff.user_name == "" || staffInfo.staff.user_name == undefined){
						$(".username").show();
						return ;
					} else {
						$(".username").hide();
					};
					var staffFormData = JSON
					.stringify(staffInfo.staff);
					/*alert(staffFormData)*/
					services.addStaff({
						staff : staffFormData
					}).success(function(data) {
						alert("添加成功！")
						$location.path('userList/');
						return;
					});
		};	
		// 添加角色信息
		staffInfo.addingRole = {
				role_name : ""
		};
		staffInfo.addRole = function() {
				
				var AddUser = JSON.stringify(staffInfo.addinguser);
				var rolePermission = JSON
				.stringify(staffInfo.selected);
				console.log("权限"+ rolePermission);
				if(staffInfo.addingRole && staffInfo.addingRole.role_name == "" || staffInfo.addingRole.role_name == undefined){
					$(".rolename").show();
					return ;
				} else {
					$(".rolename").hide();
				};
			services.addRole({
				role_name : staffInfo.addingRole.role_name,
				role_permission : rolePermission
				}).success(function(data) {
					alert("添加成功！")
					$location.path('roleList/');
					return;
				});
  };	
	
						// 功能模块权限字段名根据页数
						var perName = [ "index_per", "system_per" ,"systemleft_per","projleft_per","proj_per","equipment_per"];
						// 初始化权限数据容器
						staffInfo.selected = {};
						function initCheckBoxData() {
							$("input:checkbox[name='selectAllChkBx']")
									.attr("checked", false);
							
							
							for (var i = 0; i < 8; i++) {
								staffInfo.selected[perName[i]] = new Array();
								for (var j = 0; j < 8; j++)
									staffInfo.selected[perName[i]][j] = 0;
							}
							console.log(staffInfo.selected);
						}
						// 根据用户选择更新权限数据容器
						var updateSelected = function(action, clazz, name) {
							if (action == 'add') {
								staffInfo.selected[clazz][name] = 1;
							}
							if (action == 'remove') {
								staffInfo.selected[clazz][name] = 0;
							}
						}
						staffInfo.selectAll = function($event, subPerName) {
							if ($event.target.checked == true) {
								for (var i = 0; i < 10; i++)
									staffInfo.selected[subPerName][i] = 1;
							} else {
								for (var i = 0; i < 10; i++)
									staffInfo.selected[subPerName][i] = 0;
							}

						}
						// 根据用户选择更新权限数据容器
						staffInfo.updateSelection = function(e, clazz, name) {
							var checkbox = e.target;
							var action = (checkbox.checked ? 'add'
									: 'remove');
							updateSelected(action, clazz, name);
						}
						// 控件内容初始化
						staffInfo.isSelected = function(clazz, name) {
							console.log(clazz);
							console.log(name);
							var t = staffInfo.selected[clazz][name];
							return t;
						}
							    	
								    	
										
					
			// 读取用户信息
		staffInfo.selectUserById=function(userId) {
		console.log(userId);
		var user_id = sessionStorage.getItem('userId');
		services.selectUserById({
				user_id : userId
				})
				.success(
						function(data) {
							staffInfo.users = data.user;
							staffInfo.staff = data.user;
							
						});
		};
		
		// 修改用户信息		
		staffInfo.updateUser = function() {
			var UseFormData = JSON.stringify(staffInfo.users);
			if (confirm("是否修改用户信息？") == true) {
				services.updateUserById ({
					users :UseFormData,
				}).success(function(data) {
					alert("修改成功！")
					$location.path('userList/');
				});
			}
		}
		// 读取角色信息
		staffInfo.selectRoleById=function(obj) {
		console.log(roleId);
		var role_id = sessionStorage.getItem('roleId');
		initCheckBoxData();
		services.selectRoleById({
				role_id : roleId
				})
				.success(
						function(data) {
							staffInfo.editRole = data.role;
							var obj = $
							.parseJSON(data.role.role_permission);
							staffInfo.selected = $
									.parseJSON(data.role.role_permission);
							
						});
		};	
		// 修改角色
		
				staffInfo.updateRole =function() {
							var EditRole = JSON
									.stringify(staffInfo.editRole);
							var EditRolePermission = JSON
									.stringify(staffInfo.selected);
							console.log(EditRolePermission);
							services
									.addRole(
											{
												role_name : staffInfo.editRole.role_name,
												role_id : staffInfo.editRole.role_id,
												role_permission : EditRolePermission
											})
									.success(
											function(data) {
												alert("修改成功！");
												$location.path('roleList/');
											});
				}
						
				// 查看用户ID，并记入sessionStorage
				staffInfo.getUserId = function(userId) {
					sessionStorage.setItem('userId', userId);
				};
				// 查看角色ID，并记入sessionStorage
				staffInfo.getRoleId = function(roleId) {
					sessionStorage.setItem('roleId', roleId);
				};
				// 根据输入筛选信息
				staffInfo.selectuserByName = function() {
					searchKey = staffInfo.userName;
					services.getUserListByPage({
						page : 1,
						searchKey : searchKey
					}).success(function(data) {
						staffInfo.users = data.list;
						initData();
						//console.log(staffInfo.users)
						//pageTurn(data.totalPage, 1, getUserListByPage)
					});
				};
				// 根据页数获取角色列表
				function getRoleListByPage(page) {
					services.getRoleListByPage({
						page : page,
						}).success(function(data) {
						staffInfo.roles = data.list;
					
				});
				}
				
			//初始化页面信息
			function initData() {
				console.log("初始化页面信息");
				if ($location.path().indexOf('/userList') == 0) {
					searchKey = staffInfo.userName;
					services.getUserListByPage({
						page : 1,
						searchKey : searchKey
					}).success(function(data) {
						staffInfo.users = data.list;
						console.log(JSON.stringify( staffInfo.users))
						pageTurn(
								data.totalPage, 
								1, 
								getUserListByPage);
					});
				}
			
				else if ($location.path().indexOf('/staffBaseInfo') == 0) {
				searchKey = null;
					services.getUserListByPage({
						page : 1,
						searchKey : searchKey
					}).success(function(data) {
						staffInfo.user = data.list;
						pageTurn(
								data.totalPage,
								1,
								getUserListByPage);
					});
				}
				 else if ($location.path().indexOf('/staffAdd') == 0) {
					 services.getAllRoleList({
						 
					 }).success(function(data){
						 staffInfo.roles = data;
						 console.log(JSON.stringify( staffInfo.roles))
						 
						})
				 }
				 else if ($location.path().indexOf('/roleList') == 0) {
					 
						services.getRoleListByPage({
							page : 1,
						}).success(function(data) {
							staffInfo.roles = data.list;
							console.log(JSON.stringify( staffInfo.roles))
							pageTurn(
									data.totalPage, 
									1, 
									getRoleListByPage);
						});
					}
				 else if ($location.path().indexOf('/roleDetail') == 0) {
						// 根据ID获取信息
						var role_id = sessionStorage.getItem('roleId');
						services.selectRoleById({
							role_id: role_id
								})
								.success(											
										function(data) {
											staffInfo.roleDetail = data.role;
											staffInfo.selected = $
													.parseJSON(data.role.role_permission);
								});
						 			}
				 else if ($location.path().indexOf('/roleUpdate') == 0) {
				// 根据ID获取信息
				var role_id = sessionStorage.getItem('roleId');
				services.selectRoleById({
						role_id: role_id
						})
						.success(											
								function(data) {
									staffInfo.editRole = data.role;
									staffInfo.selected = $
											.parseJSON(data.role.role_permission);
						});
				 			}
				 else if ($location.path().indexOf('/userUpdate') == 0) {
					 services.getAllRoleList().success(function(data){
						 staffInfo.roles = data;						
						})
						
				// 根据ID获取信息
				var user_id = sessionStorage.getItem('userId');
				services.selectUserById({
						user_id: user_id
						})
						.success(											
								function(data) {
									staffInfo.users = data.user;//"user"是controller里get的到的"user","users"对应html中的"users.user_xxx"
									staffInfo.users.role = data.user.role.role_id;
						});
				 			}
				
							}
						initData();
						//获取信息完
						initCheckBoxData();
								
						
												
						
				// 删除单个用户信息
										
				staffInfo.deleteUser = function(user_id) {								
					$(".overlayer").fadeIn(200);
					$("#tipDelUser").fadeIn(200);								
					sessionStorage.setItem("userId",user_id);
					
				}
				$(".tiptop a").click(function() {
					$("#tipDelUser").fadeOut(100);
					$(".overlayer").fadeOut(200);
					});
				
				$("#sureDelUser").click(function(){
					$("#tipDelUser").fadeOut(100);
					$(".overlayer").fadeOut(200);
					//进入后台
					var user_id=sessionStorage.getItem("userId");
					services.deleteUser({
						userId : user_id
					}).success(function(data) {
						initData();
						
					});
					});
				$("#cancelDelUser").click(function(){
					$("#tipDelUser").fadeOut(100);
					$(".overlayer").fadeOut(200);
					});
				// 删除角色信息
				
				staffInfo.deleteRole = function(role_id) {								
					$(".overlayer").fadeIn(200);
					$("#tipDelUser").fadeIn(200);								
					sessionStorage.setItem("roleId",role_id);
					
				}
				$(".tiptop a").click(function() {
					$("#tipDelUser").fadeOut(100);
					$(".overlayer").fadeOut(200);
					});
				
				$("#sureDelUser").click(function(){
					$("#tipDelUser").fadeOut(100);
					$(".overlayer").fadeOut(200);
					//进入后台
					var role_id=sessionStorage.getItem("roleId");
					services.deleteRole({
						roleId : role_id
					}).success(function(data) {
						initData();
						
					});
					});
				$("#cancelDelUser").click(function(){
					$("#tipDelUser").fadeOut(100);
					$(".overlayer").fadeOut(200);
					});
						// 根据页数获取用户列表
							function getUserListByPage(page) {
								services.getUserListByPage({
									page : page,
									searchKey : searchKey
								}).success(function(data) {
									staffInfo.users = data.list;
								
							});
							}
									
									
									
							
									
									function getAllStaff(){
										services.getStaffInfo().success(function(data){
											console.log(data.Result)
											staffInfo.user = data.Result;
										})
									}
					
						
						
							 function initPage() {
										console.log("初始化成功staffInfoController！")
										if ($location.path().indexOf('/staffBaseInfo') == 0) {
											getAllStaff();
										}else if($location.path().indexOf('/getProjectInfo') == 0){
											services.getProjectInfo().success(function(data){
												indexpro.project = data.result;
												console.log(JSON.stringify(indexpro.project))
											});
										}
										}
									
					} ]);	
						            
						
							
							
							
								 
									
									
												
										
									
									
									
									
									
											
													
														
													
														
													
														
														
								
								
								

					
						

