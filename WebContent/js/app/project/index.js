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
	}).when('/getProjectInfo', {
		templateUrl : '/gywyext/jsp/project/getProjectInfo.html',
		controller : 'indexProController'
	}).when('/companyUpdate', {
		templateUrl : '/gywyext/jsp/project/companyUpdate.html',
		controller : 'indexProController'
	}).when('/projectUpdate', {
		templateUrl : '/gywyext/jsp/project/projectUpdate.html',
		controller : 'indexProController'
	}).when('/companyDetail', {
		templateUrl : '/gywyext/jsp/project/companyDetail.html',
		controller : 'indexProController'
	}).when('/companyList', {
		templateUrl : '/gywyext/jsp/project/companyList.html',
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
	services.addProject = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/addProject.do',
			data : data,
		});
	};
	// 删除公司信息
	services.deleteCompanyInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/deleteCompanyInfo.do',
			data : data
		});
	};
	// 删除项目信息
	services.deleteProjectInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/deleteProjectInfo.do',
			data : data
		});
	};
	services.getProjectInfo = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/getProjectInfo.do',
			data : data,
		});
	};
	//根据公司id查找公司的项目
	services.getCompProj = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/getCompProj.do',
			data : data
		});
	};
	
	// 根据id获取公司信息
	services.selectCompanyById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/selectCompanyById.do',
			data : data
		});
	};
	
	// 修改公司信息
	services.updateCompanyById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/updateCompanyById.do',
			data : data
		});
	};
	//根据公司名称查询公司信息
	services.selectcompanyByName = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemStaff/selectcompanyByName.do',
			data : data
		});
	};
	// 根据id获取项目信息
	services.selectProjectById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/selectProjectById.do',
			data : data
		});
	};

	services.getCompanyListByPage = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/getCompanyListByPage.do',
			data : data
		});
	};
	services.getProjectListByPage = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/getProjectListByPage.do',
			data : data
		});
	};
	// 修改项目信息
	services.updateProjectById = function(data) {
		return $http({
			method : 'post',
			url : baseUrl + 'systemProject/updateProjectById.do',
			data : data
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
			var companys;
			var projects;
			var companyName;
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
				indexpro.proj = {
						projname : [],
						projaddr : [],
						projnum : [],
						projmemo : []
					}
					$("input[name='projname']").each(function() {
						indexpro.proj.projname.push($(this).val());
					})
					$("input[name='projaddr']").each(function() {
						indexpro.proj.projaddr.push($(this).val());
					})
					$("input[name='projnum']").each(function() {
						indexpro.proj.projnum.push($(this).val());
					})
					$("input[name='projmemo']").each(function() {
						indexpro.proj.projmemo.push($(this).val());
					})
				var companyInfo = JSON.stringify(indexpro.company);
				var companyproj = JSON.stringify(indexpro.proj);
				alert("123")
				if(indexpro.company.comp_name == "" || indexpro.company.comp_name == undefined){
					$(".companyname").show();
					return ;
				} else {
					$(".companyname").hide();
				}
				services.addCompany({
					company : companyInfo,
					companyproj : companyproj
				}).success(function(data) {
					alert("添加成功！")
					$location.path('companyList/');
				});
			}
				
					/*$(".overlayer").show();
					$(".motai").show();
					$timeout(function(){
						$(".overlayer").hide();
						$(".motai").hide();
						$location.path('getCompanyInfo/');
			    	}, 3000);*/
					// $(".panel-footer").click(function(){
					// $(".overlayer").hide();
					// $(".motai").hide();
					// $location.path('getCompanyInfo/');
					//					})
				/*var timeout_upd = $interval(function(){
					indexpro.count--;
					$(".countnum").innerHTML = indexpro.count;
					if(indexpro.count<1){
						$interval.cancel(timeout_upd);
					}
				},1000)
				});
			}*/
			
			
			
			
			
			indexpro.addProject = function(){
				var projectInfo = JSON.stringify(indexpro.project);
				alert(projectInfo)
				services.addProject({
					project : projectInfo
				}).success(function(data) {
					alert("添加成功！")
					$location.path('getProjectInfo/');
				});
			}
			
			
			
			
			
			// 根据输入筛选公司信息
			indexpro.selectcompanyByName = function() {
				searchKey = indexpro.companyName;
				services.getCompanyListByPage({
					page : 1,
					searchKey : searchKey
				}).success(function(data) {
					indexpro.companys = data.list;
					pageTurn(data.totalPage, 1, getCompanyListByPage)
				});
			};
	
			// 查看ID，并记入sessionStorage
			indexpro.getCompanyId = function(companyId) {
				sessionStorage.setItem('companyId',companyId);
				};
			// 读取公司信息
			indexpro.selectCompanyById = function(companyId) {
				var comp_id = sessionStorage.getItem('companyId');
				services.selectCompanyById({
					comp_id : companyId
				}).success(function(data) {
					indexpro.companys = data.company;
				});
			};
			// 修改公司信息
			indexpro.updateCompany = function() {
				var CoFormData = JSON.stringify(indexpro.companys);
				if (confirm("是否修改此公司信息？") == true) {
					services.updateCompanyById ({
						companys : CoFormData,
					}).success(function(data) {
						alert("修改成功！")
						$location.path('companyList/');
					});
				}
			}
				
			//查看公司详细信息
			indexpro.getCompanyDetail = function(company){
				var companyDetail = JSON.stringify(company);
				sessionStorage.setItem('companyDetail',companyDetail);
				$location.path("/companyDetail");
			}
			
			// 删除公司信息
			indexpro.deleteCompanyInfo = function(comp_id) {
				if (confirm("是否删除该公司信息？") == true) {
					services.deleteCompanyInfo({
						companyId : comp_id
					}).success(function(data) {
						
						indexpro.result = data;
						alert("成功删除公司信息！");
						$location.path('companyList/');
					});
				}
			}
			
			// 查看项目ID，并记入sessionStorage
			indexpro.getProjectId = function(projectId) {
				sessionStorage.setItem('projectId',projectId);				
			};
						
			// 读取项目信息
			indexpro.selectProjectById = function(projectId) {
				
				var proj_id = sessionStorage.getItem('projectId');
				services.selectProjectById({
					proj_id : projectId
				}).success(function(data) {
					indexpro.projects = data.project;
					alert(indexpro.projects);
				});
			};
				
			
			// 修改项目信息
			indexpro.updateProject = function() {				
				var PrFormData = JSON.stringify(indexpro.projects);
				alert(PrFormData);
				if (confirm("是否修改此项目信息？") == true) {
					services.updateProjectById ({
						projects : PrFormData,
					}).success(function(data) {
						alert("修改成功！")
						$location.path('getProjectInfo/');
						alert(JSON.stringify(PrFormData));
					});
				}
			}			
			// 删除项目信息
			indexpro.deleteProjectInfo = function(proj_id) {
				if (confirm("是否删除该项目信息？") == true) {
					services.deleteProjectInfo({
						projectId : proj_id
					}).success(function(data) {

						indexpro.result = data;
							alert("成功删除项目信息！");
							$location.path('getProjectInfo/');
					});
				}
			}
			// 根据页数获取公司列表
			function getCompanyListByPage(page) {
				services.getCompanyListByPage({
					page : page,
					searchKey : searchKey
				}).success(function(data) {
					indexpro.companys = data.list;
				
			});
			}
			// 根据页数获取项目列表
			function getProjectListByPage(page) {
				services.getProjectListByPage({
					page : page,
					searchKey : searchKey
				}).success(function(data) {
					indexpro.projects = data.list;
				
			});
			}
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
			
			indexpro.selectProjectByCompany = function() {
				searchKey = indexpro.compname;
				services.getProjectListByPage({
					page : 1,
					searchKey : searchKey								
				}).success(function(data) {
					indexpro.projects = data.list;
					pageTurn(data.totalPage,1,getProjectListByPage);
				});
			};
			
			// 初始化页面信息
			function initData() {
				console.log("初始化页面indexProController！");
				if ($location.path().indexOf('/addProject') == 0){
					services.getCompanyInfo().success(function(data){
						indexpro.companys = data.result;
					})
	
				} else if ($location.path().indexOf('/getCompanyInfo') == 0){					
					searchKey = null;
					services.getCompanyListByPage({
						page : 1,
						searchKey : searchKey
					}).success(function(data) {
						indexpro.companys = data.list;
						console.log(JSON.stringify(data.list))
						pageTurn(
								data.totalPage,
								1,
								getCompanyListByPage);
					})
				} else if ($location.path().indexOf('/getProjectInfo') == 0){
					searchKey = indexpro.companyName;
					/*services.getProjectInfo().success(function(data){
						indexpro.project = data.result;
						console.log(JSON.stringify(indexpro.project))
					})*/
					services.getCompanyInfo().success(function(data){
						indexpro.companys = data.result;
					})
					/*indexpro.compname = "0"
					var searchKey = JSON.stringify(indexpro.compname)*/
					searchKey = null;
					services.getProjectListByPage({
						page : 1,
						searchKey : searchKey
					}).success(function(data) {
						indexpro.Projects = data.list;
						console.log(JSON.stringify(data.list))
						pageTurn(
								data.totalPage,
								1,
								getProjectListByPage);
						indexpro.projects = data.list;
						console.log(JSON.stringify(indexpro.projects))
						pageTurn(data.totalPage,1,getProjectListByPage);
					})
				}else if ($location.path().indexOf('/companyUpdate') == 0) {
					var comp_id = sessionStorage.getItem("companyId");
					services.selectCompanyById({
						comp_id : comp_id
					}).success(function(data) {
						indexpro.companys = data.company;
						console.log(JSON.stringify(indexpro.companys))
					}) 
				}else if ($location.path().indexOf('/projectUpdate') == 0) {
					services.getCompanyInfo().success(function(data){
						indexpro.companys = data.result;						
					})
					var proj_id = sessionStorage.getItem("projectId");
					services.selectProjectById({
						proj_id : proj_id
					}).success(function(data) {
						indexpro.projects = data.project;
						indexpro.projects.company = data.project.company.comp_id;
					}) ;
				}else if($location.path().indexOf('/companyList') == 0) {
					searchKey = null;
					services.getCompanyListByPage({
						page : 1,
						searchKey : searchKey
					}).success(function(data) {
						indexpro.companys = data.list;
						console.log(JSON.stringify(indexpro.companys))
						pageTurn(
								data.totalPage,
								1,
								getCompanyListByPage);
					})
				}else if($location.path().indexOf('/companyDetail') == 0){
					indexpro.companyDetail = JSON.parse(sessionStorage.getItem('companyDetail'));
					
					services.getCompProj({
						comp_id : indexpro.companyDetail.comp_id
					}).success(function(data){
						if(data.error){
							alert(data.error);
							history.go(-1);
							return;
						}
						indexpro.projectInfo = data.result;
						console.log(indexpro.projectInfo);
					})
				}
			}
			initData();
		} ]);
					
						
				
					
						
						
				