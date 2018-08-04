<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/jsp/equip/top.jsp" />
<section>
<div ng-view></div>
</section>
<%-- <jsp:include page="/jsp/equip/left.jsp" /> --%>
<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/app/equipment/equipment.js"></script>
</body>
</html>