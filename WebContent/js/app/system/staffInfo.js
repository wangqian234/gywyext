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
	}).when('/staffList', {
		templateUrl : '/gywyext/jsp/system/staffInfo/staffList.html',
		controller : 'staffInfoController'
	})
} ]);

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
/*	services.getAllRoleList = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/getAllRoleList.do',
			data : data
		});
	};
	*/
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
							
							staffInfo.staff = {
									user_name:"",
									user_email:'',
									user_tel:"",
									user_age:'' ,
									role_id:" ", /*设计编辑功能的时候可以输入进去123deng，新增""*/
										
							}
/*							staffInfo.addStaff = function(){
								alert(JSON.stringify(staffInfo.staff))//alert:网页打开时弹窗提示
							}*/
							
							// 添加用户信息
						staffInfo.addStaff = function() {
								alert(JSON.stringify(staffInfo.staff))
								var staffFormData = JSON
										.stringify(staffInfo.staff);
								services.addStaff({
									staff : staffFormData
								}).success(function(data) {
									alert("新建成功！");
								});
								user.addinguser = "";
								$(".overlayer").fadeIn(200);
								$(".tip").fadeIn(200);
								$("#addUser-form").slideDown(200);
								user.addinguser = {
									user_sex : 0,
									role : null
								};
							};
						
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
							function checkradio()
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
								}

							}

							initPage();
						} ]);

