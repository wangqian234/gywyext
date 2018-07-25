</section>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular/angular.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/angular/angular-route.min.js"></script>
<%--     <!-- JS Scripts-->
    <!-- jQuery Js -->
    <script src="${pageContext.request.contextPath}/js/jquery-1.10.2.js"></script>
    <!-- Bootstrap Js -->
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
	 
    <!-- Metis Menu Js -->
    <script src="${pageContext.request.contextPath}/js/jquery.metisMenu.js"></script> --%>
	
        <script>
        $(document).ready(function () {
        	alert("我进来了1")
    		$("#sideNav").click(function(){
    			alert("我进来了2")
    			if($(this).hasClass('closed')){
    				$('.navbar-side').animate({left: '0px'});
    				$(this).removeClass('closed');
    				$('#page-wrapper').animate({'margin-left' : '260px'});
    				
    			}
    			else{
    			    $(this).addClass('closed');
    				$('.navbar-side').animate({left: '-260px'});
    				$('#page-wrapper').animate({'margin-left' : '0px'}); 
    			}
    		});
        });
        </script>
