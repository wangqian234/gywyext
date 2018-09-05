<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/jsp/equip/top.jsp" />
<section ng-app="equipRealInfoApp">
<div ng-view></div>
</section>
<jsp:include page="/jsp/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/app/equipment/equipmentRealInfo.js"></script>
<script src="${pageContext.request.contextPath}/js/lib/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/echarts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/chart.js"></script>

<!--md-->

<%-- <script src="${pageContext.request.contextPath}/js/app/equipment/equipmentRealInfo.js"></script> --%>
</body>
</html>