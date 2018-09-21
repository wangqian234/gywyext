<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<jsp:include page="/jsp/equip/top.jsp" />
 <section ng-app="equipmentApp">
 <div ng-view></div> 
</section> 
<%-- <jsp:include page="/jsp/equip/left.jsp" /> --%>
<jsp:include page="/jsp/footer.jsp" />
<script src="${pageContext.request.contextPath}/js/app/equipment/equipment.js"></script>
<script src="${pageContext.request.contextPath}/js/lib/My97DatePicker/WdatePicker.js"></script>


<div id="page-wrapper" style="margin-left: 0px;">
<div id="page-inner">
<br>
<br>
<br>
    </div>
 </div>
          <!-- <nav class="navbar-default navbar-side" role="navigation">
		<div id="sideNav"><i class="fa fa-caret-right"></i></div>
             <div class="sidebar-collapse">
                
               <ul class="nav" id="main-menu">
                     <li ng-repeat="ld in leftData">
                        <a style="font-weight:600" value="{{ld.comp_id}}"><i class="fa fa-sitemap"></i> {{ld.comp_name}}<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level" ng-repeat="ldd in ld.data">
                            <li>
                                <a class="leftSecond" href="" ng-click="selectBaseInfoByProj(ldd.proj_id)">{{ldd.proj_name}}</a>
                            </li>
                        </ul>
                    </li>
                 </ul>                
            </div>
        </nav>  -->
        
     
<!--           <script>
        $("#main-menu").on("click",function(e){
        	if(e.target.nodeName == "A"){
        		$(this).find("a").removeClass("clickin");
        		$(e.target).addClass("clickin");
        	}
        })
        
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


        </script> -->  
</html>