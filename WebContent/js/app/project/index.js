var app = angular
		.module(
				'indexProject',
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
	$routeProvider.when('/addCompany', {
		templateUrl : '/gywyext/jsp/project/addCompany.html',
		controller : 'indexProController'
	}).when('/addProject', {
		templateUrl : '/gywyext/jsp/project/addProject.html',
		controller : 'indexProController'
	}).when('/getCompanyInfo', {
		templateUrl : '/gywyext/jsp/project/getCompanyInfo.html',
		controller : 'indexProController'
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
	services.getCompanyInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/getCompanyInfo.do',
			data : data,
		});
	};
	return services;
} ]);

app.controller('indexProController', [
		'$scope',
		'services',
		'$location',
		"$timeout",
		'$interval',
		function($scope, services, $location, $timeout, $interval) {
			var indexpro = $scope;
			
			indexpro.company = {
					comp_name : "",
					comp_addr : "",
					comp_num : "",
					comp_memo : ""
			};
			indexpro.project = {
					proj_name : "",
					proj_addr : "",
					proj_num : "",
					proj_memo : "",
					company:""
			};
			indexpro.count = 3;
			
			indexpro.addCompany = function(){
				var companyInfo = JSON.stringify(indexpro.company);
				var projectInfo = JSON.stringify(indexpro.project);
				if(indexpro.company.comp_name == "" || indexpro.company.comp_name == undefined){
					alert("w jinlail ")
					$(".companyname").show();
					return ;
				} else {
					$(".companyname").hide();
				}
				services.addCompany({
					company : companyInfo,
					project : projectInfo
				}).success(function(data) {
					$(".overlayer").show();
					$(".motai").show();
					$timeout(function(){
						$(".overlayer").hide();
						$(".motai").hide();
						$location.path('getCompanyInfo/');
			    	}, 3000);
					// $(".panel-footer").click(function(){
					// $(".overlayer").hide();
					// $(".motai").hide();
					// $location.path('getCompanyInfo/');
					//					})
				var timeout_upd = $interval(function(){
					indexpro.count--;
					$(".countnum").innerHTML = indexpro.count;
					if(indexpro.count<1){
						$interval.cancel(timeout_upd);
					}
				},1000)
				});
			}
			
			indexpro.addProject = function(){
				var projectInfo = JSON.stringify(indexpro.project);
				alert(projectInfo)
			}
			
/*			function addProject(){
				$(".add-project").click(function(){
					alert("我进来了")
				})
			}*/
			
			// 初始化页面信息
			function initData() {
				console.log("初始化页面indexProController！");
				if ($location.path().indexOf('/addProject') == 0){
					services.getCompanyInfo().success(function(data){
						indexpro.company = data.result;
						console.log(JSON.stringify(indexpro.company))
					})
				} else if ($location.path().indexOf('/getCompanyInfo') == 0){
					services.getCompanyInfo().success(function(data){
						indexpro.company = data.result;
						console.log(JSON.stringify(indexpro.company))
					})
				}
			}
			initData();
		} ]);