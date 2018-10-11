<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<title>e巡通大数据统计分析系统</title>
<!-- Bootstrap Styles-->
<link href="${pageContext.request.contextPath}/css/bootstrap.css"
	rel="stylesheet" />
<!-- FontAwesome Styles-->
<link href="${pageContext.request.contextPath}/css/font-awesome.css"
	rel="stylesheet" />
<!-- Custom Styles-->
<link href="${pageContext.request.contextPath}/css/custom-styles.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/mystyle.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/style.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/vmcss.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/lib/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/lib/jquery.json-2.2.min.js"></script>
<style>
.active {
	color: rgba(124, 189, 187, 1) !important;
	background-color: rgb(234, 234, 234) !important;
	/* color: #333 !important */
}

.active a {
	color: black !important;
}

.active a h4 {
	color: black !important;
}

.menuUl {
	width: 87% !important;
	float: left !important;
	list-style: none !important;
	text-align: center !important;
	margin-bottom: 0px !important;
	padding-left: 0px !important;
}

.menuUl li {
	width: 12% !important;
	float: left !important;
	text-align: center !important;
}

.tr-active {
	background-color: "#EAEAEA" !important;
	color: rgba(124, 189, 187, 1) !important;
}

.titleImgRight {
	width: 18%;
	margin-right: 8%;
}

.titleImgLeft {
	width: 11%;
	margin-right: 4%;
}

.indexMenuUL {
	list-style: none;
	width: 100%;
	float: left;
	height: 10%;
	background-color: white;
	padding-left: 0;
}

.indexMenuUL li {
	float: left;
	width: 25%;
	text-align: center;
	height: 80px;
	line-height: 80px;
	font-size: 25px;
	border-right: 1px solid #A9A9A9;
}

.col-md-4 {
	width: 33.3%;
}
</style>

</head>

<body>
	<nav class="navbar navbar-default top-navbar" role="navigation"
		style="padding-left: 0px;">
		<div class="navbar-header">
			<a class="navbar-brand" href="#"><i
				class="fa fa-gear"></i> <strong>公元物业e巡通</strong></a>
		</div>
		<div class="menu">
			<ul id="menuUl" class="menuUl">
				<li ><a class="topspan"
					href="/gywyext/jsp/project/index.jsp"><h4 class="fa fa-tasks">项目管理</h4></a></li>
				<li><a class="topspan" href="/gywyext/jsp/equip/index.jsp#/equipBaseInfo"><h4
							class="fa fa-bell">设备管理</h4></a></li>
				<li><a class="topspan" href="/gywyext/jsp/bigdata/index.jsp#/equipFail"><h4
							class="fa fa-bell">大数据分析</h4></a></li>
				<li><a class="topspan" href="/gywyext/jsp/system/index.jsp"><h4
							class="fa fa-bell">系统管理</h4></a></li>
			</ul>
			<!-- <a class="topspan" href="#/equipBaseInfo"><h4
					class="fa fa-dashboard">能耗分析</h4></a>  -->

		</div>
		<!-- 		<ul class="nav navbar-top-links navbar-right">
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#" aria-expanded="false"> <i
					class="fa fa-envelope fa-fw"></i> <i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-messages">
					<li><a href="#">
							<div>
								<strong>John Doe</strong> <span class="pull-right text-muted">
									<em>Today</em>
								</span>
							</div>
							<div>Lorem Ipsum has been the industry's standard dummy
								text ever since the 1500s...</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<strong>John Smith</strong> <span class="pull-right text-muted">
									<em>Yesterday</em>
								</span>
							</div>
							<div>Lorem Ipsum has been the industry's standard dummy
								text ever since an kwilnw...</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<strong>John Smith</strong> <span class="pull-right text-muted">
									<em>Yesterday</em>
								</span>
							</div>
							<div>Lorem Ipsum has been the industry's standard dummy
								text ever since the...</div>
					</a></li>
					<li class="divider"></li>
					<li><a class="text-center" href="#"> <strong>Read
								All Messages</strong> <i class="fa fa-angle-right"></i>
					</a></li>
				</ul> /.dropdown-messages</li>
			/.dropdown
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#" aria-expanded="false"> <i
					class="fa fa-tasks fa-fw"></i> <i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-tasks">
					<li><a href="#">
							<div>
								<p>
									<strong>Task 1</strong> <span class="pull-right text-muted">60%
										Complete</span>
								</p>
								<div class="progress progress-striped active">
									<div class="progress-bar progress-bar-success"
										role="progressbar" aria-valuenow="60" aria-valuemin="0"
										aria-valuemax="100" style="width: 60%">
										<span class="sr-only">60% Complete (success)</span>
									</div>
								</div>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<p>
									<strong>Task 2</strong> <span class="pull-right text-muted">28%
										Complete</span>
								</p>
								<div class="progress progress-striped active">
									<div class="progress-bar progress-bar-info" role="progressbar"
										aria-valuenow="28" aria-valuemin="0" aria-valuemax="100"
										style="width: 28%">
										<span class="sr-only">28% Complete</span>
									</div>
								</div>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<p>
									<strong>Task 3</strong> <span class="pull-right text-muted">60%
										Complete</span>
								</p>
								<div class="progress progress-striped active">
									<div class="progress-bar progress-bar-warning"
										role="progressbar" aria-valuenow="60" aria-valuemin="0"
										aria-valuemax="100" style="width: 60%">
										<span class="sr-only">60% Complete (warning)</span>
									</div>
								</div>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<p>
									<strong>Task 4</strong> <span class="pull-right text-muted">85%
										Complete</span>
								</p>
								<div class="progress progress-striped active">
									<div class="progress-bar progress-bar-danger"
										role="progressbar" aria-valuenow="85" aria-valuemin="0"
										aria-valuemax="100" style="width: 85%">
										<span class="sr-only">85% Complete (danger)</span>
									</div>
								</div>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a class="text-center" href="#"> <strong>See
								All Tasks</strong> <i class="fa fa-angle-right"></i>
					</a></li>
				</ul> /.dropdown-tasks</li>
			/.dropdown
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#" aria-expanded="false"> <i
					class="fa fa-bell fa-fw"></i> <i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-alerts">
					<li><a href="#">
							<div>
								<i class="fa fa-comment fa-fw"></i> New Comment <span
									class="pull-right text-muted small">4 min</span>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<i class="fa fa-twitter fa-fw"></i> 3 New Followers <span
									class="pull-right text-muted small">12 min</span>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<i class="fa fa-envelope fa-fw"></i> Message Sent <span
									class="pull-right text-muted small">4 min</span>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<i class="fa fa-tasks fa-fw"></i> New Task <span
									class="pull-right text-muted small">4 min</span>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a href="#">
							<div>
								<i class="fa fa-upload fa-fw"></i> Server Rebooted <span
									class="pull-right text-muted small">4 min</span>
							</div>
					</a></li>
					<li class="divider"></li>
					<li><a class="text-center" href="#"> <strong>See
								All Alerts</strong> <i class="fa fa-angle-right"></i>
					</a></li>
				</ul> /.dropdown-alerts</li>
			/.dropdown
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#" aria-expanded="false"> <i
					class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
			</a>
				<ul class="dropdown-menu dropdown-user">
					<li><a href="#"><i class="fa fa-user fa-fw"></i> User
							Profile</a></li>
					<li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
					</li>
					<li class="divider"></li>
					<li><a href="#"><i class="fa fa-sign-out fa-fw"></i>
							Logout</a></li>
				</ul> /.dropdown-user</li>
			/.dropdown
		</ul> -->
	</nav>
	<section class="containner">
		<div class="overlayer"></div>

		<script>
			$(document).ready(function() {
				$("#sideNav").click(function() {
					if ($(this).hasClass('closed')) {
						$('.navbar-side').animate({
							left : '0px'
						});
						$(this).removeClass('closed');
						$('#page-wrapper').animate({
							'margin-left' : '260px'
						});

					} else {
						$(this).addClass('closed');
						$('.navbar-side').animate({
							left : '-260px'
						});
						$('#page-wrapper').animate({
							'margin-left' : '0px'
						});
					}
				});
			});

			$(window).bind("load resize", function() {
				if ($(this).width() < 768) {
					$('div.sidebar-collapse').addClass('collapse')
				} else {
					$('div.sidebar-collapse').removeClass('collapse')
				}
			});

			$("#menuUl li").click(function() {
				$(this).addClass("active").siblings().removeClass();
			})
		</script>