<%-- <nav class="navbar-default navbar-side" role="navigation">
	<div id="sideNav"><i class="fa fa-caret-right"></i></div> 
		<div class="btn-group">
			<ul>
				<li><a	href="${pageContext.request.contextPath}/jsp/system/index.jsp" >用户管理</a>
					<ul>
						
					    <li><a href="${pageContext.request.contextPath}/jsp/system/staffInfo/index.jsp#/staffBaseInfo">全部用户信息</a></li> 
	  					<li><a href="${pageContext.request.contextPath}/jsp/system/staffInfo/index.jsp#/staffAdd">新建用户信息</a></li>
						<li><a href="${pageContext.request.contextPath}/jsp/system/staffInfo/index.jsp#/userList">查询用户信息</a></li>
					 </ul>
			 	</li>
			</ul>
	  	</div>
</nav>
 --%>
 
        <nav class="navbar-default navbar-side" role="navigation">
		<div id="sideNav"><i class="fa fa-caret-right"></i></div>
            <div class="sidebar-collapse">
               <ul class="nav" id="main-menu">
                     <li ng-repeat="ld in leftData">
                        <a style="font-weight:600" value="{{ld.comp_id}}"><i class="fa fa-sitemap"></i> 用户管理<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level">
                            <li><a class="leftSecond clickin" href="${pageContext.request.contextPath}/jsp/system/staffInfo/index.jsp#/userList">全部用户信息</a></li> 
	  						<li id="uAdd" class="uAdd" style="display: none"><a class="leftSecond" href="${pageContext.request.contextPath}/jsp/system/staffInfo/index.jsp#/staffAdd">新建用户信息</a></li>
                          	<li id="rAdd" class="rAdd" style="display: none"><a class="leftSecond" href="#">新建角色权限</a></li>
                       	</ul>
                    </li>
                 </ul>
            </div>
        </nav>

 
    <script>
    $("#main-menu").on("click",function(e){
    	if(e.target.nodeName == "A"){
    		$(this).find("a").removeClass("clickin");
    		$(e.target).addClass("clickin");
    	}
    })
    
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
    
	 $(document)
		.ready(
				function() {
					//根据权限显示左侧栏相关条目
					$
							.get(
									"/gywyext/login/getSystemLeftbarPermission.do",
									function(data) {
										console.log("左侧栏权限：" + data);
										var leftbarPermission = data
												.substring(1,
														data.length - 2)
												.split(" ");
										for (var i = 0, len = leftbarPermission.length; i < len; i++) {
											if (leftbarPermission[i].trim()) {
												var $temp = $('.'
														+ leftbarPermission[i].trim());
												if ($temp) {
													$temp.css('display',
															'block');
												}
											}

										}
									});
					//点击li时将当前页面的信息存入sessionStorage
					var $li = $('.leftmenu li');
					$li.click(function() {
						sessionStorage.setItem("currentPage", $(this).attr(
								'id'));
					});
				}); 
    </script>
    <script type="text/javascript" src="/gywyext/js/lib/jquery.metisMenu.js"></script>