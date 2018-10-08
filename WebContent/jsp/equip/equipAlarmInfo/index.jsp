<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/jsp/equip/equipAlarmInfo/top.jsp" />
 <section ng-app="equipAlarmInfoApp">
 <div ng-view></div> 
</section> 
<%-- <jsp:include page="/jsp/equip/left.jsp" /> --%>
<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/app/equipment/equipmentAlarmInfo.js"></script>
<script src="${pageContext.request.contextPath}/js/lib/My97DatePicker/WdatePicker.js"></script>


<div id="page-wrapper" style="margin-left: 0px;">
<div id="page-inner">
<br>
<br>
<br>
    </div>
 </div>
</html>