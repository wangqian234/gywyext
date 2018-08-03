<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/jsp/equip/top.jsp" />
<section id="page-wrapper" class="main" style="margin-left: 260px;padding-top:100px" >
<div ng-view></div>
</section>
<jsp:include page="/jsp/equip/left.jsp" />
<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/app/equipment/equipment.js"></script>
</body>
</html>