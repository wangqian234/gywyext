<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/jsp/top.jsp" />
<section id="page-wrapper" ng-app="indexProject" style="margin-left: 260px;padding-top:100px" class="main">
<div ng-view></div>
</section>
<jsp:include page="/jsp/project/left.jsp" />
<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/app/project/index.js"></script>
</body>
</html>