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
	services.deleteUser = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/deleteUser.do',
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
	services.updateUserById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/updateUserById.do',
			data : data,
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
								    	
										
						/*// 获取角色列表
						function getAllRoleList() {
							services.getAllRoleList({}).success(
									function(data) {
									
										staffInfo.roles = data;
									});
						}*/
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
					
					var useFormData = JSON.stringify(staffInfo.users);
					services.updateUserById({
						users :useFormData
					}).success(function(data) {
						
						alert("修改成功！");
						$location.path('userList/');
					});
				};
						
				// 查看ID，并记入sessionStorage
				staffInfo.getUserId = function(userId) {
					sessionStorage.setItem('userId', userId);
					
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
								
								//批量删除用户信息
								/*function check() {

							        var msg = "您真的确定要删除吗？";   
							        if (confirm(msg)==true){   
							            var allcheckbox = "";
							            var becheckbox = "";
							            $("input[name=atask]").each(function(){ //遍历table里的全部checkbox
							                allcheckbox += $(this).val() + ","; //获取所有checkbox的值
							                if($(this).attr("checked")) //如果被选中
							                    becheckbox += $(this).val() + ","; //获取被选中的值
							            });

							            if(becheckbox.length > 0) //如果获取到
							                becheckbox = becheckbox.substring(0, becheckbox.length - 1); //把最后一个逗号去掉
							                window.location = "astask_batch_delete.action?checkTnum="+becheckbox;
							        }else{   
							        return false;   
							        }   
							}*/
												
						
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
						// 根据页数获取用户列表
							function getUserListByPage(page) {
								services.getUserListByPage({
									page : page,
									searchKey : searchKey
								}).success(function(data) {
									staffInfo.users = data.list;
								
							});
							}
									
									
									
									/*							
							staffInfo.addStaff = function(e) {
								preventDefault(e);
								services.getAllRoleList().success(
										function(data) {
											user.roles = data;
										});
								user.addinguser = "";
								$(".overlayer").fadeIn(200);
								$(".tip").fadeIn(200);
								$("#addUser-form").slideDown(200);
								$("#editUser-form").hide();
								user.addinguser = {
									user_sex : 0,
									role : null
								};

							};
									 */
									/*function checkradio()
									{
										var parms=document.getElementsByName("radio1");
										//获取所有的单选框
										var i;
										for( i=0;i<parms.length;i++)              
											//遍历单选框
										{
											if(parms[i].checked)                     
												//如果选择了此单选框
												alert("您选择了"+ parms[i].value);     
											//提示用户的选择
										}
									}*/
									
									function getAllStaff(){
										services.getStaffInfo().success(function(data){
											console.log(data.Result)
											staffInfo.user = data.Result;
										})
									}
									//改动过
									function initPage() {
										console.log("初始化成功staffInfoController！")
										if ($location.path().indexOf('/staffBaseInfo') == 0) {
											getAllStaff();
										}else if($location.path().indexOf('/getProjectInfo') == 0){
											services.getProjectInfo().success(function(data){
												indexpro.project = data.result;
												console.log(JSON.stringify(indexpro.project))
											})
									}
									}
									initPage();
						} ]);
						
							
							
							
								 
									
									
												
										
									
									
									
									
									
											
													
														
													
														
													
														
														
								
								
								

					
						

