<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/jsp/index/top.jsp" />
 <section ng-app="index">
 <div ng-view></div> 
</section> 
<%-- <jsp:include page="/jsp/equip/left.jsp" /> --%>
<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/app/index/index.js"></script>
<script src="${pageContext.request.contextPath}/js/lib/My97DatePicker/WdatePicker.js"></script>


 </div>


<script type="text/javascript">





</script>
</html>