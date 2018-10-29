<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8" />
<title>e巡通大数据统计分析系统</title>
    <!-- Bootstrap Styles-->
    <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" />
    <!-- FontAwesome Styles-->
    <link href="${pageContext.request.contextPath}/css/font-awesome.css" rel="stylesheet" />
    <!-- Custom Styles-->
    <link href="${pageContext.request.contextPath}/css/custom-styles.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/mystyle.css" rel="stylesheet" />
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/jquery.json-2.2.min.js"></script>
</head>

<body>
        <nav class="navbar navbar-default top-navbar" role="navigation">
		<div class="navbar-header">
			<a class="navbar-brand" href="${pageContext.request.contextPath}/">
			<img style="height:25px;display:inline-block;margin-top:-2px;margin-right: 5px;" src="${pageContext.request.contextPath}/images/gylogo.png"><strong>公元e巡通</strong></a>
		</div>
			<div>
			<ul id="menuUl">
			<li class="active"><a class="topspan active" href="#/equipBaseInfo"><h4 class="fa fa-dashboard">设备基本信息</h4></a></li>
			<li><a class="topspan" href="equipRealInfo/dataV/index.jsp#/dataV"><h4 class="fa fa-tasks">可视化大屏展示</h4></a></li>
			<li><a class="topspan" href="equipAlarmInfo/index.jsp#/equipAlarmInfo"><h4 class="fa fa-bell">设备报警信息</h4></a></li>
			</ul>
<!-- 				<a class="topspan" href="#/equipBaseInfo"><h4 class="fa fa-dashboard">设备基本信息</h4></a>
				<a class="topspan" href="equipRealInfo/dataV/index.jsp#/dataV"><h4 class="fa fa-tasks">可视化大屏展示</h4></a>
				<a class="topspan" href="equipAlarmInfo/index.jsp#/equipAlarmInfo"><h4 class="fa fa-bell">设备报警信息</h4></a> -->
			</div>
        </nav>
	<section class="containner">
	<div class="overlayer"></div>
	
	        <script>
        $(document).ready(function () {
    		$("#sideNav").click(function(){
    			if($(this).hasClass('closed')){
    				$('.navbar-side').animate({left: '0px'});
    				$(this).removeClass('closed');
    				$('#page-wrapper').animate({'margin-left' : '260px'});
    				
    			}
    			else{
    			    $(this).addClass('closed');
    				$('.navbar-side').animate({left: '-260px'});
    				$('#page-wrapper').animate({'margin-left' : '0px'}); 
    			}
    		});
        });

        $(window).bind("load resize", function () {
            if ($(this).width() < 768) {
                $('div.sidebar-collapse').addClass('collapse')
            } else {
                $('div.sidebar-collapse').removeClass('collapse')
            }
        });
		$("#menuUl>li").click(function() {
			$(this).addClass("active").siblings().removeClass();
		})
        </script>
