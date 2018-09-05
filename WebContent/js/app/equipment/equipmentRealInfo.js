var app = angular
		.module(
				'equipRealInfoApp',
				[ 'ngRoute', ],
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
	$routeProvider.when('/equipRealInfo', {
		templateUrl : '/gywyext/jsp/equip/equipRealInfo/equipRealInfo.html',//md
		controller : 'equipRealInfoController'
	}).when('/echartsShow', {
		templateUrl : '/gywyext/jsp/equip/equipRealInfo/echartsShow.html',
		controller : 'equipRealInfoController'
	})
} ]);

app.constant('baseUrl', '/gywyext/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	
	var services = {};
	// 根据页数获取设备信息
	services.getEquipmentListByPage = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/getEquipmentListByPage.do',
			data : data
		});
	};
	//根据设备id查找设备特征参数
	services.getEquipPara = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/getEquipPara.do',
			data : data
		});
	};
	// 根据id获取设备信息
	services.selectEquipmentById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/selectEquipmentById.do',
			data : data
		});
	};
	//根据设备参数id获取实时数据
	services.getEquipRealData = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRealInfo/getEquipRealData.do',
			data : data
		});
	}
	
	return services;
} ]);
app
		.controller(
				'equipRealInfoController',
				[
						'$scope',
						'services',
						'$location',
						/*'FileUploader',*/
						function($scope, services, $location/*, FileUploader*/) {
							var equipment = $scope;
							var equip_room = $scope;
							var equip_type = $scope;
							var equip_para = $scope;
							
							//md
							// 根据页数获取设备列表
							function getEquipmentListByPage(page) {
								services.getEquipmentListByPage({
									page : page,
									searchKey : searchKey
								}).success(function(data) {
									equipment.equipments = data.list;
								});
							}
							equipment.count = [1];					
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
							// 读取设备监控参数信息
							equipment.getequipmentId = function(equipmentId){
								sessionStorage.setItem('equipmentId',equipmentId);
							}
							function getEquipPara() {
								var equip_id = sessionStorage.getItem('equipmentId');
								services.getEquipPara({
									page : 1,
									searchKey : equip_id
								}).success(function(data) {
									equipment.equipara = data.result;
								});
							};
							//读取设备参数实时数据
							function getRealData(equipParaId,start){
								var equip_para_id = equipParaId;
								services.getEquipRealData({
									searchKey : equip_para_id,
									start : start,
								}).success(function(data) {
									equipment.equiparadata = data.data;
									console.log("1");
								});
							};
							//分离数据
							function divide(d,l){
								var xdata = [];
								var ydata = [];
								for(var i = 0;i<l;i++){
									if((i+1) == l)
									var x = d[i].equip_oper_time;
									var y = d[i].equip_oper_info;
									xdata.push(x);
									ydata.push(y);
								}
								/*try3(xdata,ydata);*/
								};
							//根据参数id，查询实时数据
							equipment.getEquipRealData = function(equipParaId){
								var start = 0;
								var data = [];
								var xdata = [];
								var ydata = [];
								var l = 9;
								var p = new Promise(function (resolve, reject) {
									     console.log('start new Promise...');
									     getRealData(equipParaId,start)
									     /*resolve(getRealData(equipParaId,start));*/
									 });
								/*var equip_para_id = equipParaId;*/
								p.then(function(){
									console.log("2");
									/*console.log(start);*/
									start = equipment.equiparadata[9].equip_oper_id;
									/*console.log(start);*/
								});
								/*p.then(getRealData(equipParaId,equipment.start)).
								then(divide(equipment.equiparadata,equipment.equiparadata.length));
								getRealData(equipParaId,start);
								data = equipment.equiparadata;
								l = equipment.equiparadata.length
								start = equipment.equiparadata[l-1].equip_oper_id*/
								/*for(var i = 0;i<l;i++){
									if((i+1) == l)
									var x = data[i].equip_oper_time;
									var y = data[i].equip_oper_info;
									xdata.push(x);
									ydata.push(y);
								}*/
								/*try3(xdata,ydata);*/
								/*setInterval(function (){
									getRealData(equipParaId,equipment.start);
									start = getRealData(equipParaId,equipment.start);
									console.log(equipment.start);
								},1000);*/
								/*setInterval(function (){
									getRealData(equipParaId,start);
									data = equipment.equiparadata;
									console.log(data);
									start = equipment.equiparadata[l].equip_oper_id;
									for(var i = 0;i<10;i++){
										if((i+1) == 10)
											start = data[i].equip_oper_id;
										var x = data[i].equip_oper_time;
										var y = data[i].equip_oper_info;
										var z = data[i+1].equip_oper_time;
										if(x != z){
											xdata.push(x);
											ydata.push(y);
											}
									};
									console.log(xdata);
									try3(xdata,ydata);
								    },1000)*/
							};
							/*function try1(xdata,ydata){
								
								echart.clear();
								console.log(ydata[1]);
								
								var option = {
									    xAxis: {
									        type: 'category',
									        data: xdata
									    },
									    yAxis: {
									        type: 'value',
									    },
									    series: [{
									        data: ydata,
									        type: 'line',
									        smooth: true
									    }]
									};
								console.log(xdata[2]);
								echart.setOption(option);
							} */
							
							// 初始化
							function initPage() {
								console.log("初始化成功equipmentController！");
								if ($location.path().indexOf('/equipRealInfo') == 0) {
									
									searchKey = null;
									services.getEquipmentListByPage({
										page : 1,
										searchKey : searchKey
									}).success(function(data) {
										equipment.equipments = data.list;
										pageTurn(
												data.totalPage,
												1,
												getEquipmentListByPage);
										});	
								}
								else if ($location.path().indexOf('/echartsShow') == 0){
									getEquipPara()
								}
							}
							initPage();
						} ]);

                          //Echarts图表展示
                          function echarts(data) {
                        	  
                          }


