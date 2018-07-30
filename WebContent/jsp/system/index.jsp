<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/jsp/top.jsp" />
<section id="page-wrapper" ng-app="indexSystem" class="main">
<div style="margin-top:60px;">首页信息的展示</div>
</section>
<jsp:include page="/jsp/system/left.jsp" />
<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/app/system/index.js"></script>
</body>
</html>