
        <nav class="navbar-default navbar-side" role="navigation">
		<div id="sideNav"><i class="fa fa-caret-right"></i></div>
            <div class="sidebar-collapse">
<%--                  <ul class="nav" id="main-menu">
                    <li>
                    <a href="${pageContext.request.contextPath}/jsp/equip/index.jsp#/equipBaseInfo"><i class="fa fa-sitemap"></i>设备信息<span class="fa arrow"></span></a>
					<a href="${pageContext.request.contextPath}/jsp/equip/index.jsp#/equipTest"><i class="fa fa-sitemap"></i>测试<span class="fa arrow"></span></a>
                    </li>
                </ul>  --%>
                
               <ul class="nav" id="main-menu" ng-controller="equipmentController">
                     <li ng-repeat="ld in leftData">
                        <a style="font-weight:600" value="{{ld.comp_id}}"><i class="fa fa-sitemap"></i> {{ld.comp_name}}<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level" ng-repeat="ldd in ld.data">
                            <li>
                                <a class="leftSecond" href="" ng-click="selectBaseInfoByProj(ldd.proj_id)">{{ldd.proj_name}}</a>
                            </li>
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
        </script>
