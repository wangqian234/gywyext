var app = angular
		.module(
				'equipMainInfoApp',
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
	$routeProvider.when('/equipMainInfo', {
		templateUrl : '/gywyext/jsp/equip/equipMainInfo/equipMainInfo.html',
		controller : 'EquipMainController'
	})
} ]);

app.constant('baseUrl', '/gywyext/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	// 获取左侧菜单栏
	services.getInitLeft = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'index/getInitLeft.do',
			data : data
		});
	};
	// 根据页数获取设备信息
	services.getEquipMainListByIR = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'equipMain/getEquipMainListByIR.do',
			data : data
		});
	}
	return services;
} ]);
app.controller(
				'EquipMainController',
				[
						'$scope',
						'services',
						'$location',						
						function($scope, services, $location) {
							var equipmain = $scope;				
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

							// 列表换页
							function getEquipMainListByIR(page) {
								equip_id = equipmain.equipid;
								equip_main_result = equipmain.result;
								var proj_id = sessionStorage.getItem('proj_id');
								services.getEquipMainListByIR({
									page : page,									
									equip_id : equip_id,
									equip_main_result : equip_main_result,
									proj_id : proj_id
								}).success(function(data) {
									equipmain.equipMains = data.list;
								});
							}
							
							//获取维保信息
							equipmain.getEquipMainList = function(proj_id,name) {
								sessionStorage.setItem("proj_id", proj_id); // 新建中的设备获取用到
								equipmain.project_name = name   
								equipmain.equipid = "0";    //改变proj时筛选条件初始化
								equipmain.result = "2";  
								equip_id = equipmain.equipid;
								equip_main_result = equipmain.result;
								services.getEquipMainListByIR({
									page : 1,
									equip_id : equip_id,
									equip_main_result : equip_main_result,
									proj_id : proj_id
								}).success(function(data) {
									equipmain.equipMains = data.list;									
									equipmain.equipments = data.equip;
									pageTurn(data.totalPage,1,getEquipMainListByIR);
								});
							}
							
							// 根据room，state，searchkey筛选设备信息
							equipmain.selectEquipMainByIR = function() {
								equip_id = equipmain.equipid;
								equip_main_result = equipmain.result;
								var proj_id = sessionStorage.getItem('proj_id');
								services.getEquipMainListByIR({
									page : 1,
									equip_id : equip_id,
									equip_main_result : equip_main_result,
									proj_id : proj_id								
								}).success(function(data) {
									equipmain.equipMains = data.list;
									pageTurn(data.totalPage,1,getEquipMainListByIR);
								});
							};
							
							
							// 初始化
							function initPage() {
								console.log("初始化成功equipMainController！");
                                if ($location.path().indexOf('/equipMainInfo') == 0) {					//初始页面				
									services.getInitLeft({}).success(
													function(data) {
														var arr = data.leftResult;
														var map = {}, dest = [];
														for (var i = 0; i < arr.length; i++) {
															var ai = arr[i];
															if (!map[ai.comp_id]) {
																dest.push({
																			comp_id : ai.comp_id,
																			comp_name : ai.comp_name,
																			data : [ ai ]
																		});
																map[ai.comp_id] = ai;
															} else {
																for (var j = 0; j < dest.length; j++) {
																	var dj = dest[j];
																	if (dj.comp_id == ai.comp_id) {
																		dj.data.push(ai);
																		break;
																	}
																}
															}
														}
														equipmain.leftData = dest;
														var leftData = JSON.stringify(dest)
														sessionStorage.setItem('leftData',leftData);
														setTimeout(function() {
															var tt = $(".leftSecond");
															console.log(tt[0]);
															tt[0].click();
														}, 10);
													});

																
								}
								}								
								
							initPage();
						} ]);

function formatDateTime(inputTime) {  
	    var date = new Date(inputTime);
	    var y = date.getFullYear();  
	    var m = date.getMonth() + 1;  
	    m = m < 10 ? ('0' + m) : m;  
	    var d = date.getDate();  
	    d = d < 10 ? ('0' + d) : d;  
	    var h = date.getHours();
	    h = h < 10 ? ('0' + h) : h;
	    var minute = date.getMinutes();
	    minute = minute < 10 ? ('0' + minute) : minute;   
	    return y + '-' + m + '-' + d+' '+h+':'+minute;  
	};


//时间戳转换
app.filter('timer', function() {
	return function(input) {
		var type = "";
		if (input != null) {
			/*type = new Date(input).toLocaleDateString().replace(/\//g, '-');*/
			type = formatDateTime(input);
		}
		return type;
	}
});

//result 0、1转换
app.filter('findresult', function() {
	return function(input) {
		if (input == 0) {
			var output = "维保正常";
			return output;
		}
		if(input == 1) {
			var output = "维保失败";
			return output;
		}
	}
});