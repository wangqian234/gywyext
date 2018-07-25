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

app.constant('baseUrl', '/gywyext/');
app.factory('services', [ '$http', 'baseUrl', function($http, baseUrl) {
	var services = {};
	services.getInitLeft = function() {
		return $http({
			method : 'post',
			url : baseUrl + 'index/getInitLeft.do',
		});
	};
	return services;
} ]);

app.controller('IndexController', [
		'$scope',
		'services',
		'$location',
		function($scope, services, $location) {
			var index = $scope;
			
			// 初始化页面信息
			function initData() {
				console.log("初始化页面！");
				 services.getInitLeft().success(function(data) {
					var arr = data.leftResult;
					console.log(data.leftResult)
					 
					var map = {},dest = [];
					for(var i = 0; i < arr.length; i++){
					    var ai = arr[i];
					    if(!map[ai.comp_id]){
					        dest.push({
					        	comp_id: ai.comp_id,
					            comp_name: ai.comp_name,
					            data: [ai]
					        });
					        map[ai.comp_id] = ai;
					    }else{
					        for(var j = 0; j < dest.length; j++){
					            var dj = dest[j];
					            if(dj.comp_id == ai.comp_id){
					                dj.data.push(ai);
					                break;
					            }
					        }
					    }
					}
					console.log(dest)
					index.leftData = dest;
				 });
			}
			initData();
		} ]);