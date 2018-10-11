var app = angular
		.module(
				'index',
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
	$routeProvider.when('/', {
		templateUrl : '/gywyext/jsp/index/init.html',
		controller : 'indexController'
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
	services.selectIndexData=function(data){
		return $http({
			method : 'post',
			url : baseUrl + 'index/selectIndexData.do',
			data : data
		});
	};
	return services;
} ]);

app.controller('indexController', [ '$scope', 'services', '$location',
		"$timeout", '$interval',
		function($scope, services, $location, $timeout, $interval) {
			var index = $scope;
			index.proId;
			index.alarmNum=0;
			index.mainNum=0;
			index.unhealthNum=0;
			// 获取左侧蓝菜单
			index.getLeftData = function() {
				services.getInitLeft({}).success(function(data) {
					console.log("zzzz" + JSON.stringify(data));
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
					index.leftData = dest;
					var leftData = JSON.stringify(dest)
					sessionStorage.setItem('leftData', leftData);

					setTimeout(function() {
						var tt = $(".dd");
						console.log(tt[0]);
						tt[0].click();
					}, 10);

				});
			}
			// 点击项目触发事件
			index.selectBaseInfoByProj = function(str, $event) {

				index.proId = str;
				services.selectIndexData({
					"proId" : str
				}).success(function(data) {
					index.alarmNum=data.alarmNum;
					index.mainNum=data.mainNum;
					index.unhealthNum=data.unhealthNum;
				});
				$(".nav-second-level li").removeClass("liActive");
				var oObj = window.event.srcElement;
				var oTr = oObj.parentNode;
				oTr.className = "liActive";
			}

			// 初始化页面信息
			function initData() {
				console.log("初始化页面index！");
				if ($location.path().indexOf('/init') == 0) {

					index.type = "init";

					index.getLeftData();

				} else if ($location.path().indexOf('/equipPre') == 0) {

					index.type = "equipPre";
					index.getLeftData();
				} else {
					index.getLeftData();
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