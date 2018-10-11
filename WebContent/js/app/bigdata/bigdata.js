var app = angular
		.module(
				'bigData',
				[ 'ngRoute' ],
				function($httpProvider) {// ngRoute引入路由依赖
					$httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
					$httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

					// Override $http service's default transformRequest
					$httpProvider.defaults.transformRequest = [ function(data) {
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

// 路由配置
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/init', {
		templateUrl : '/gywyext/jsp/bigdata/init.html',
		controller : 'bigdataController'
	}).when('/equipFail', {
		templateUrl : '/gywyext/jsp/bigdata/equipFail.html',
		controller : 'bigdataController'
	}).when('/equipState', {
		templateUrl : '/gywyext/jsp/bigdata/equipState.html',
		controller : 'bigdataController'
	}).when('/equipPre', {
		templateUrl : '/gywyext/jsp/bigdata/equipPre.html',
		controller : 'bigdataController'
	})
} ]);

app.constant('baseUrl', '/gywyext/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	services.addCompany = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/addCompany.do',
			data : data,
		});
	};
	// 获取左侧菜单栏
	services.getInitLeft = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/getInitLeft.do',
			data : data
		});
	};
	services.selectRoomList = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipRoom/selectEquipRoomList.do',
			data : data
		});
	};
	services.selectEquipListByRoomId = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'bigData/selectEquipListByRoomId.do',
			data : data
		});
	};
	services.getEquipRadarById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'bigData/getEquipRadarById.do',
			data : data
		});
	};
	services.getEquipPreById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'bigData/getEquipPreById.do',
			data : data
		});
	};
	services.getRoomEquipAnalysisByRoomId = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'bigData/getRoomEquipAnalysisByRoomId.do',
			data : data
		});
	};
	return services;
} ]);

app
		.controller(
				'bigdataController',
				[
						'$scope',
						'services',
						'$location',
						"$timeout",
						'$interval',
						function($scope, services, $location, $timeout,
								$interval) {
							var bigData = $scope;
							bigData.limit = {
								projectId : "",
								EquipRoomId : ""
							};
							bigData.preDate;
							bigData.warning = "";
							bigData.proId;// 项目id全局变量
							bigData.roomId;// 位置ID全局变量
							bigData.chosedIndex;// 控制选择那个位置显示
							bigData.type = "";

							// 换页函数
							function pageTurn(totalPage, page, Func) {
								$(".tcdPageCode").empty();
								var $pages = $(".tcdPageCode");
								if ($pages.length != 0) {
									$(".tcdPageCode").createPage({
										pageCount : totalPage,
										current : page,
										backFn : function(p) {
											Func(p);
										}
									});
								}
							}

							// zq点击设备位置查询设备基本信息列表
							bigData.selectEquipList = function(f, index) {
								bigData.chosedIndex = index;
								bigData.roomId = f.equip_room_id;
								if (bigData.type == "equipFail") {

									services
											.getRoomEquipAnalysisByRoomId({
												page : 1,
												roomId : f.equip_room_id
											})
											.success(
													function(data) {
														bigData.equiplist = data.list;
														pageTurn(
																data.totalPage,
																1,
																selectEquipList);
														var pieContent = [];
														let
														o = new Object();
														o.name = "故障数量";
														o.type = "pie";
														o.radius = "55%";
														o.center = [ '50%',
																'60%' ];
														o.data = data.analysis;
														pieContent.push(o);
														var chartObject = new Object();
														/*
														 * chartObject.title =
														 * "设备故障种类分析";
														 */
														chartObject.domElement = document
																.getElementById("chart");
														chartObject.dataContent = pieContent;
														var failChart = drawPieChart(chartObject);

													});
								} else if (bigData.type == "equipState") {
									services.selectEquipListByRoomId({
										page : 1,
										roomId : f.equip_room_id
									}).success(
											function(data) {
												bigData.equiplist = data.list;
												pageTurn(data.totalPage, 1,
														selectEquipList);
											});
								} else if (bigData.type == "equipPre") {
									services.selectEquipListByRoomId({
										page : 1,
										roomId : f.equip_room_id
									}).success(
											function(data) {
												bigData.equiplist = data.list;
												pageTurn(data.totalPage, 1,
														selectEquipList);
											});
								}
							}

							// zq用于基本信息换页查询
							function selectEquipList(page) {
								services.selectEquipListByRoomId({
									page : page,
									roomId : bigData.roomId
								}).success(function(data) {
									bigData.equiplist = data.list;

								});
							}
							// 点击项目获取项目设备安装位置列表，并默认显示第一个位置的设备
							bigData.selectRoomList = function() {
								bigData.tableIndex = 0;
								bigData.tip="提示：请从左侧设备列表选择设备进行分析！";
								services
										.selectRoomList({
											"proId" : bigData.proId
										})
										.success(
												function(data) {
													bigData.roomId = data.list[0].equip_room_id;
													bigData.roomList = data.list;
													// 给ul的第一个li显示样式
													bigData.chosedIndex = 0;
													var o = {
														"equip_room_id" : bigData.roomId
													};
													bigData.selectEquipList(o,
															0);

												});
							}
							// 点击项目触发事件
							bigData.selectBaseInfoByProj = function(str, $event) {
								bigData.proId = str;
								bigData.selectRoomList();
								$(".nav-second-level li").removeClass(
										"liActive");
								var oObj = window.event.srcElement;
								var oTr = oObj.parentNode;
								oTr.className = "liActive";
							}

							// zq点击表格的每一行变色
							bigData.change = function(obj, index, e) {

								bigData.tableIndex = 100;
								var oObj = window.event.srcElement;
								if (oObj.tagName.toLowerCase() == "td") {
									var oTr = oObj.parentNode;
									for (var i = 1; i < document.all.infoList.rows.length; i++) {
										document.all.infoList.rows[i].style.backgroundColor = "";
										document.all.infoList.rows[i].tag = false;
									}
									oTr.style.backgroundColor = "#EAEAEA";
									oTr.tag = true;
								}

								if (bigData.type == "equipFail") {

								} else if (bigData.type == "equipState") {
									services
											.getEquipRadarById({
												equipmentId : obj.equip_id
											})
											.success(
													function(data) {
														var radarResult = data.result;
														var typeArray = data.typeArray;
														var dataContent = [];
														var o = {};
														o.name = "设备健康状态分析";
														o.type = "radar";
														var dataNum = [];
														var num = {};
														num.name = "设备健康状态分析";
														num.value = radarResult;
														dataNum.push(num);
														o.data = dataNum;

														dataContent.push(o);
														var chartObject1 = {};
														chartObject1.domElement = document
																.getElementById('radarChart');
														chartObject1.title = "";
														chartObject1.model = typeArray;
														chartObject1.dataContent = dataContent;
														var radarChart = drawRadarChart(chartObject1);
														bigData.warning=data.warning;
													});
								} else if (bigData.type == "equipPre") {
									bigData.tip="提示：请从左侧设备列表选择设备进行分析！";
									services
											.getEquipPreById({
												equipmentId : obj.equip_id
											})
											.success(
													function(data) {

														bigData.preDate = data.result;

														if (data.result[0] == null
																|| data.result[0] == "") {
															bigData.warning = "该设备的初次安装使用时间为"+bigData
															.formatDate(bigData.preDate[2].time)+"，经分析预测设备下次维修时间为"
																	+ bigData
																			.formatDate(bigData.preDate[1].time)
																	+ ",请在预测维修时间之前进行检修，以防发生突发故障！";
														} else {
															bigData.warning = "该设备的初次安装使用时间为"+bigData
															.formatDate(bigData.preDate[2].time)+"，上次维保时间为"
																	+ bigData
																			.formatDate(bigData.preDate[0].time)
																	+ ",经分析预测设备下次维修时间为"
																	+ bigData
																			.formatDate(bigData.preDate[1].time)
																	+ ",请在预测维修时间之前进行检修，以防发生突发故障！";
														}
														;

														var barObject = new Object();
														var dataContent = [];
														var o = new Object();
														if(data.data==null||data.data==""||data.data==undefined){
															bigData.tip="";
															$("#barChart").html("");
															return;
														}else{
															o.data = data.data;
															o.type = "bar";
															dataContent.push(o);
															barObject.x_Axis = data.xAxis;
															barObject.y_Axis = "次";
															barObject.dataContent = dataContent;
															barObject.domElement = document
																	.getElementById('barChart');

															var radarChart = drawBarChart(barObject);
														}
														
													});
								}
								;
							}
							bigData.formatDate = function(input) {
								var type = new Date(input).toLocaleDateString()
										.replace(/\//g, '-');
								return type;
							}
							// 获取左侧蓝菜单
							bigData.getLeftData = function() {
								services
										.getInitLeft({})
										.success(
												function(data) {
													console
															.log("zzzz"
																	+ JSON
																			.stringify(data));
													var arr = data.leftResult;
													var map = {}, dest = [];
													for (var i = 0; i < arr.length; i++) {
														var ai = arr[i];
														if (!map[ai.comp_id]) {
															dest
																	.push({
																		comp_id : ai.comp_id,
																		comp_name : ai.comp_name,
																		data : [ ai ]
																	});
															map[ai.comp_id] = ai;
														} else {
															for (var j = 0; j < dest.length; j++) {
																var dj = dest[j];
																if (dj.comp_id == ai.comp_id) {
																	dj.data
																			.push(ai);
																	break;
																}
															}
														}
													}
													bigData.leftData = dest;
													var leftData = JSON
															.stringify(dest)
													sessionStorage.setItem(
															'leftData',
															leftData);
													bigData.tableIndex = 0;
													setTimeout(
															function() {
																var tt = $(".dd");
																console.log(tt[0]);
																tt[0].click();
															}, 10);

												});
							}

							// 初始化页面信息
							function initData() {
								console.log("初始化页面bigData！");
								if ($location.path().indexOf('/init') == 0) {
									bigData.type = "init";

									bigData.getLeftData();

								} else if ($location.path().indexOf(
										'/equipFail') == 0) {

									bigData.type = "equipFail";
									bigData.getLeftData();
									

								
								} else if ($location.path().indexOf(
										'/equipState') == 0) {

									bigData.type = "equipState";
									bigData.getLeftData();

								} else if ($location.path()
										.indexOf('/equipPre') == 0) {

									bigData.type = "equipPre";
									bigData.getLeftData();
								}
							}
							initData();
						} ]);

// 时间的格式化的判断
app.filter('dateType', function() {
	return function(input) {
		var type = "";
		if (input) {
			type = new Date(input).toLocaleDateString().replace(/\//g, '-');
		}

		return type;
	}
});
// 时间的格式化的判断
app.filter('DateTimeFormat', function() {
	return function(input) {
		var type = "";
		if (input) {
			type = input.substr(0, 10);
		} else {
			type = "无";
		}

		return type;
	}
});

// 时间的格式化的判断
app.filter('EquipState', function() {
	return function(input) {
		var type = "";
		if (input == '0') {
			type = "正常";
		} else if (input == '1') {
			type = "需要维修";
		} else if (input == '2') {
			type = "需要更换";
		} else {
			type = "正常";
		}

		return type;
	}
});