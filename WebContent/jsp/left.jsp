
        <nav class="navbar-default navbar-side" role="navigation" ng-app="index" ng-controller="IndexController">
		<div id="sideNav"><i class="fa fa-caret-right"></i></div>
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">

                    <li ng-repeat="ld in leftData">
                        <a value="{{ld.comp_id}}"><i class="fa fa-sitemap"></i> {{ld.comp_name}}<span class="fa arrow"></span></a>
                        <ul class="nav nav-second-level" ng-repeat="ldd in ld.data">
                            <li>
                                <a href="#">{{ldd.proj_name}}</a>
                                  <ul class="nav nav-third-level">
                                    <li>
                                        <a href="#">设备基本信息管理</a>
                                    </li>
                                    <li>
                                        <a href="#">设备维保信息管理</a>
                                    </li>
                                    <li>
                                        <a href="#">设备运行状态管理</a>
                                    </li>

                                </ul>
                            </li>
                        </ul>
                    </li>

                </ul>

            </div>

        </nav>


