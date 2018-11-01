        <nav class="navbar-default navbar-side" role="navigation">
		<!-- <div id="sideNav"><i class="fa fa-caret-right"></i></div> -->
            <div class="sidebar-collapse">
              <ul class="nav" id="main-menu">
                <li>
                    <a class="clickin" href="${pageContext.request.contextPath}/jsp/project/index.jsp#/companyList">
                    	<i class="fa fa-sitemap"></i>浏览公司信息
                    </a>
                    </li>
                    <li>
                     <a href="${pageContext.request.contextPath}/jsp/project/index.jsp#/getProjectInfo">
                    	<i class="fa fa-sitemap"></i>浏览项目信息
                    </a>
                    </li>
                    <li id="cAdd" class="cAdd" style="display: none">   
                    <a href="${pageContext.request.contextPath}/jsp/project/index.jsp#/addCompany">
                    	<i class="fa fa-sitemap"></i>添加公司信息
                    </a>
                    </li>
                    <li id="pAdd" class="pAdd" style="display: none">
                    <a href="${pageContext.request.contextPath}/jsp/project/index.jsp#/addProject">
                    	<i class="fa fa-sitemap"></i>添加项目信息
                    </a>                                    
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
	        
	        			 $(document)
	.ready(
			function() {
				//根据权限显示左侧栏相关条目
				$
						.get(
								"/gywyext/login/getProjLeftbarPermission.do",
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