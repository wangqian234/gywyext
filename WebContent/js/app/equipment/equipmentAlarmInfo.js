var app = angular
		.module(
				'equipAlarmInfoApp',
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
	$routeProvider.when('/equipAlarmInfo', {
		templateUrl : '/gywyext/jsp/equip/equipAlarmInfo/equipAlarmInfo.html',
		controller : 'AlarmLogController'
	})
} ]);

app.constant('baseUrl', '/gywyext/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	// 根据页数获取设备信息
	services.getAlarmListByPage = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'alarmLog/getAlarmListByPage.do',
			data : data
		});
	}
	return services;
} ]);
app.controller(
				'AlarmLogController',
				[
						'$scope',
						'services',
						'$location',						
						function($scope, services, $location) {
							var alarmlog = $scope;				
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

							function getAlarmListByPage(page) {
								services.getAlarmListByPage({
									page : page,
									searchKey : searchKey
								}).success(function(data) {
									alarmlog.alarmlogs = data.list;									
								});
							}
							
							// 初始化
							function initPage() {
								console.log("初始化成功equipAlarmController！");
								if ($location.path().indexOf('/equipAlarmInfo') == 0) {									
									searchKey = null;	
									   services.getAlarmListByPage({
											page : 1,
											searchKey : searchKey
										}).success(function(data) {									
											alarmlog.alarmlogs = data.list;
											pageTurn(data.totalPage, 1, getAlarmListByPage);		
										});
									}
								}								
								
							initPage();
						} ]);
//时间戳转换
app.filter('timer', function() {
	return function(input) {
		var type = "";
		if (input != null) {
			type = new Date(input).toLocaleDateString().replace(/\//g, '-');
		}
		return type;
	}
});
