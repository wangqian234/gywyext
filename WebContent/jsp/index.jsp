<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="/jsp/top.jsp" />
<section id="page-wrapper" style="margin-left: 260px;" class="main">

这是一个首页
		<div id="sideNav"><i class="fa fa-caret-right"></i></div>
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">

                    <li ng-repeat="ld in leftData">
                        <a value="{{ld.comp_id}}"><i class="fa fa-sitemap"></i> {{ld.comp_name}}<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level" ng-repeat="ldd in ld.data">
                            <li>
                                <a href="#">{{ldd.proj_name}}</a>
                                  <ul class="nav nav-third-level">
                                    <li>
                                        <a href="#">第二个功能</a>
                                    </li>
                                    <li>
                                        <a href="#">第一个功能</a>
                                    </li>
                                    <li>
                                        <a href="#">第san个功能</a>
                                    </li>

                                </ul>
                            </li>
                        </ul>
                    </li>

                </ul>

            </div>
</section>
<jsp:include page="/jsp/left.jsp" />
<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/app/index.js"></script>
</body>
</html>