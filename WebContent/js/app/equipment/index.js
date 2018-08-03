var app = angular
		.module(
				'equipment',
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


//路由配置
app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/equipBaseInfo', {
		templateUrl : '/gywyext/jsp/equip/equipInfo/equipBaseInfo.html',
		controller : 'equipmentController'
	})

} ]);


app.constant('baseUrl', '/gywyext/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	services.getInitLeft = function() {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/getStaffInfo.do',
		});
	};
	services.getStaffInfo = function() {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/getStaffInfo.do',
		});
	};
	return services;
} ]);

app.controller('indexController', [
		'$scope',
		'services',
		'$location',
		function($scope, services, $location) {
			var index = $scope;
			
			// 初始化页面信息
			function initData() {
				console.log("初始化页面indexController！");
				services.getStaffInfo().success(function(data){
					console.log(data.Result)
					index.user = data.Result;
				})

			}
			initData();
		} ]);