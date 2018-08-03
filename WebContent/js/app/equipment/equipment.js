var app = angular
		.module(
				'equipmentApp',
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
	$routeProvider.when('/equipBaseInfo', {
		templateUrl : '/gywyext/jsp/equip/equipInfo/equipBaseInfo.html',
		controller : 'equipmentController'
	}).when('/equipAdd', {
		templateUrl : '/gywyext/jsp/equip/equipInfo/equipAdd.html',
		controller : 'equipmentController'
	}).when('/equipDetail', {
				templateUrl : '/gywyext/jsp/equip/equipInfo/equipDetail.html',
				controller : 'equipmentController'
	}).when('/equipUpdate', {
		templateUrl : '/gywyext/jsp/equip/equipInfo/equipUpdate.html',
		controller : 'equipmentController'
    })
} ]);

app.constant('baseUrl', '/gywyext/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	//显示
	var services = {};
	
	services.getEquipmentInfo = function() {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipmentInfo.do',
		});
	};
	//删除
	services.deleteEquipmentInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/deleteEquipmentInfo.do',
		    data:data
		});
	};
	//限制条件筛选
	services.getEquipmentListByTS = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipmentListByTS.do',
			data : data
		});
	};
	//根据页数筛选信息
	services.getEquipmentListByPage = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipmentListByPage.do',
			data : data
		});
	};
	//添加设备信息
	services.addEquipment = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/addEquipment.do',
			data : data
		});
	};
	//修改设备信息
	services.updateEquipment = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/updateEquipmentById.do',
			data : data
		});
	};
	//根据id获取信息
	services.selectEquipmentById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/selectEquipmentById.do',
			data : data
		});
	};	
	//根据页数获取设备信息
	services.getEquipmentListByPage = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipEquipment/getEquipmentListByPage.do',
		    data:data
		});
	};

	return services;
} ]);
app
		.controller(
				'equipmentController',
				[
						'$scope',
						'services',
						'$location',
						function($scope, services, $location) {
							var equipment = $scope;
							
							// 根据页数获取用户列表
							function getEquipmentListByPage(page) {
								services.getEquipmentListByPage({
									page : page,
									searchKey : searchKey
								}).success(function(data) {
									equipment.equipments = data.list;	
								});
							}
							
											
							
							
							// 换页
							function pageTurn(totalPage, page, Func) {
								$(".tcdPageCode").empty();
								var pa=1
								var $pages = $(".tcdPageCode");
								if ($pages.length != 0) {
									$(".tcdPageCode").createPage({
										pageCount : totalPage,
										current : page,
										backFn : function(p) {
											pa=p;
											Func(p);
										}
									});
								}
							}
	
							// 删除设备信息
							equipment.deleteEquipmentInfo = function(equip_id) {
								if (confirm("是否删除该旅游信息？") == true) {
									services.deleteEquipmentInfo({
										equipmentId : equip_id
									}).success(function(data) {
										
										equipment.result = data;
										if (data == "true") {
											console.log("删除旅游信息成功！");
											$location.path('equipBaseInfo/');
										} else {
											console.log("删除失败！");
										}
										
									});
								}
							}
							//限制条件筛选
							// 根据页数获取设备信息
							function getEquipmentListByTS(page) {
								var eqLimit = null;
								var eqSLimit = null;
								if (JSON.stringify(equipment.EQTLimit) != null
										&& JSON.stringify(equipment.EQTLimit) != "") {
									eqLimit = JSON.stringify(equipment.EQTLimit);
								}
								if (JSON.stringify(equipment.EQSLimit) != null
										&& JSON.stringify(equipment.EQSLimit) != "") {
									EQSLimit = JSON.stringify(equipment.EQSLimit);
								}
								services.getEquipmentListByTS({
									page : page,
									equipmentType : eqLimit,
									equipmentState : eqSLimit
								}).success(function(data) {
									equipment.equipments = data.list;
								});
							}

							// state、Type限制
							equipment.getELByTS = function(page) {
								var eqLimit = null;
								var eqSLimit = null;
								if (JSON.stringify(equipment.EQTLimit) != null
										&& JSON.stringify(equipment.EQTLimit) != "") {
									eqLimit = JSON.stringify(equipment.EQTLimit);
								}
								if (JSON.stringify(equipment.EQSLimit) != null
										&& JSON.stringify(equipment.EQSLimit) != "") {
									eqSLimit = JSON.stringify(equipment.EQSLimit);
								}
								services.getEquipmentListByTS({
									page : page,
									equipmentType : eqLimit,
									equipmentState : eqSLimit
								}).success(function(data) {
									$scope.equipment = data.list;
									pageTurn(data.totalPage, page, getEquipmentListByTS)
								});
							}
			
							// 添加设备信息
							equipment.addEquipment = function() {
								
								var equipmentFormData = JSON.stringify(equipment.equipmentInfo);
								console.log(equipmentFormData);
								services.addEquipment({
									equipment : equipmentFormData
								}).success(function(equipment) {
									
									$location.path('equipBaseInfo/');
							});
							}
							
							//读取设备信息
							equipment.selectEquipmentById=function(equipmentId) {
								console.log(equipmentId);
								var equip_id = sessionStorage.getItem('equipmentId');
								services.selectEquipmentById({
											equip_id : equipmentId
										})
										.success(
												function(data) {
													equipment.equipment = data.equipment;
													/*if (data.equipment.equip_pdate) {
														equipment.equipment.equip_pdate = changeDateType(data.equipment.equip_pdate);
													}*/
/*
													if(data.equipment.travel_discount){
														equipment.equipmentInfo.travel_discount = changeFloat(data.travel.travel_discount);*/
													});										
							};
							
							// 修改设备信息
							equipment.updateEquipment = function() {
								
								var EqFormData = JSON.stringify(equipment.equipmentInfo);
								services.updateEquipmentById({
									equipment : EqFormData
								}).success(function(data) {
									alert("修改成功！");
									$location.path('equipBaseInfo/');
								});
							};
							// 查看ID，并记入sessionStorage
							equipment.getEquipmentId = function(equipmentId) {
								sessionStorage.setItem('equipmentId', equipmentId);
							};		
						 
						
						//初始化
						function initPage() {
							console.log("初始化成功equipmentController！")
							
							if ($location.path().indexOf('/equipBaseInfo') == 0) {	
									searchKey = null;
									services.getEquipmentListByPage({
										page : 1,
										searchKey : searchKey
									}).success(function(data) {
										equipment.equipments = data.list;
										pageTurn(data.totalPage, 1, getEquipmentListByPage);
										
									});
								}
							else if($location.path().indexOf('/equipUpdate') == 0){
								var equip_id = sessionStorage.getItem("equipmentId");
								services.selectEquipmentById({
											equip_id : equip_id
										})
										.success(
												function(data) {
													equipment.equipmentInfo = data.equipment;
													/*if (data.equipment.equip_pdate) {
														equipment.equipment.equip_pdate = changeDateType(data.equipment.equip_pdate);
													}	*/
												}
												);
							};
						}
						initPage();
						} ]);
/*//2017-8-30wdh更改时间的样式
function changeDateType(date) {
	console.log("传进来的时间" + date);
	if (date != "") {
		var DateTime = new Date(date.time)
				.toLocaleDateString().replace(
						/\//g, '-');
	} else {
		var DateTime = "";
	}
	console.log("转化后的时间" + DateTime);
	return DateTime;
}*/
