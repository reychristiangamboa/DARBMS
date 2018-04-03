<%-- 
    Document   : provincial-field-officer-linksfarm-select-project-sites
    Created on : Mar 23, 2018, 6:27:16 PM
    Author     : Rey Christian
--%>


<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>
        <style>
            .rate{
                color:black;
                cursor:pointer;
                width: 80px;
                margin: 0 auto;
            }
            .rate:hover{
                color:red;
            }
            .checked {
                color: orange;
            }
            .bs-example{
                margin: 20px;
            }
            @media screen and (min-width: 768px) {
                .modal-dialog {
                    width: 700px; /* New width for default modal */
                }
                .modal-sm {
                    width: 350px; /* New width for small modal */
                }
            }
            @media screen and (min-width: 992px) {
                .modal-lg {
                    width: 950px; /* New width for large modal */
                }
            }
            @media screen and (min-width: 1080px) {
                .modal-lger {
                    width: 1080px; /* New width for large modal */
                }
            }

        </style>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%int userType = (Integer) session.getAttribute("userType");%>
            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%if((Integer)session.getAttribute("userType")==3){%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%}else if((Integer)session.getAttribute("userType")==5){%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>

            <%
            AddressDAO addressDAO = new AddressDAO();
            ARBDAO arbDAO = new ARBDAO();
            ARBODAO arboDAO = new ARBODAO();
            
            ArrayList<CityMun> cityMunListSites = new ArrayList();
            ArrayList<CityMun> cityMunListNonSites = new ArrayList();
            
            if(userType == 3){
                ArrayList<ARBO> arboListProvince = arboDAO.getAllARBOsByProvince((Integer) session.getAttribute("provOfficeCode"));
                cityMunListSites = addressDAO.getAllProjectSites(arboListProvince.get(0).getArboProvince());
                cityMunListNonSites = addressDAO.getAllNonProjectSites(arboListProvince.get(0).getArboProvince());
            }else if(userType == 5){
                cityMunListSites = addressDAO.getAllProjectSitesC();
                cityMunListNonSites = addressDAO.getAllNonProjectSitesC();
            }
            
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        User Profile
                    </h1>
                </section>

                <!-- Main content -->
                <section class="content">

                    <%if(request.getAttribute("success") != null){%>
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-check"></i> <%out.print((String)request.getAttribute("success"));%></h4>
                    </div>
                    <%}else if(request.getAttribute("errMessage") != null){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String)request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>

                    <div class="row">
                        <div class="col-md-12">
                            <!-- /.col -->
                            <%if((Integer)session.getAttribute("userType") == 3){%>
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Project Site</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="box-body">

                                    <div class="box-body">
                                        <%
                                            
                                            for (CityMun cm : cityMunListSites) {
                                                ArrayList<ARB> arbListCityMun = arbDAO.getAllARBsByCityMun(cm.getCityMunCode());
                                                
                                        %>
                                        <div class="active tab-pane" >

                                            <div class="col-lg-2 col-xs-6">
                                                <!-- small box -->
                                                <a href="ViewExistingSite?id=<%out.print(cm.getCityMunCode());%>" class="btn-link">
                                                    <div class="small-box bg-yellow">
                                                        <div class="inner">
                                                            <h3><%out.print(arbListCityMun.size());%></h3>

                                                            <p><%=cm.getCityMunDesc()%></p>
                                                        </div>
                                                    </div>
                                                </a>
                                            </div>
                                        </div>
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                            <%}%>
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Non Project Site</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="box-body">

                                    <div class="box-body">
                                        <%
                                            for (CityMun cm : cityMunListNonSites) {
                                                ArrayList<ARB> arbListCityMun = arbDAO.getAllARBsByCityMun(cm.getCityMunCode());
                                        %>
                                        <div class="active tab-pane" >
                                            <div class="col-lg-2 col-xs-6" data-toggle="modal" data-target="#modal-default<%out.print(cm.getCityMunCode());%>">
                                                <!-- small box -->
                                                <a href="ViewNonExistingSite?id=<%out.print(cm.getCityMunCode());%>" class="btn-link">
                                                    <div class="small-box bg-yellow">
                                                        <div class="inner">
                                                            <h3><%out.print(arbListCityMun.size());%></h3>

                                                            <p><%=cm.getCityMunDesc()%></p>
                                                        </div>
                                                    </div>
                                                </a>
                                            </div>

                                        </div>
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>

                </section>

            </div>
            <!-- /.row -->


            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>
        <script type="text/javascript">
        </script>
    </body>
</html>
