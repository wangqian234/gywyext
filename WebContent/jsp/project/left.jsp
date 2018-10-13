        <nav class="navbar-default navbar-side" role="navigation">
		<div id="sideNav"><i class="fa fa-caret-right"></i></div>
            <div class="sidebar-collapse">
              <ul class="nav" id="main-menu">
                <li>
                    <a href="${pageContext.request.contextPath}/jsp/project/index.jsp#/companyList">
                    	<i class="fa fa-sitemap"></i>浏览公司信息<span class="fa arrow"></span>
                    </a>
                     <a href="${pageContext.request.contextPath}/jsp/project/index.jsp#/getProjectInfo">
                    	<i class="fa fa-sitemap"></i>浏览项目信息<span class="fa arrow"></span>
                    </a>   
                    <a href="${pageContext.request.contextPath}/jsp/project/index.jsp#/addCompany">
                    	<i class="fa fa-sitemap"></i>添加公司信息<span class="fa arrow"></span>
                    </a>
                    <a href="${pageContext.request.contextPath}/jsp/project/index.jsp#/addProject">
                    	<i class="fa fa-sitemap"></i>添加项目信息<span class="fa arrow"></span>
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
        </script>