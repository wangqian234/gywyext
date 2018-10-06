
</section>

<!-- AngularJS dependences -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/angular/angular.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/angular/angular-route.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/angular/angular-file-upload.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/pageTurn.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/lib/echarts.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/chartModel.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/app/equipment/ezuikit.js"></script>
        <script>
        $(document).ready(function () {
    		$("#sideNav").click(function(){
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

        $(window).bind("load resize", function () {
            if ($(this).width() < 768) {
                $('div.sidebar-collapse').addClass('collapse')
            } else {
                $('div.sidebar-collapse').removeClass('collapse')
            }
        });
        </script>
