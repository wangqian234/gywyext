<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<jsp:include page="/jsp/top.jsp" />
<section id="page-wrapper" ng-app="equipmentApp" style="margin-left: 260px;padding-top:100px" class="main">
<div ng-view></div>
</section> 
<jsp:include page="/jsp/equip/left.jsp" />
<jsp:include page="/jsp/footer.jsp" />
<script src="/gywyext/js/app/equipment/equipment.js"></script>
</body>
</html>