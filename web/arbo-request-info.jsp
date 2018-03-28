<%-- 
    Document   : arbo-request-info
    Created on : Mar 24, 2018, 9:00:38 AM
    Author     : Rey Christian
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>

    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            
            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%if ((Integer) session.getAttribute("userType") == 2) {%>
            <%@include file="jspf/point-person-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>
            <%
                APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
                ARBDAO arbDAO = new ARBDAO();
                ARBODAO arboDAO = new ARBODAO();
                APCPRequest r = apcpRequestDAO.getRequestByID((Integer)request.getAttribute("requestID"));
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
            %>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">

                </section>
     
                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">ARBO Information</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <div class="box-body"> 

                                    <div class="nav-tabs-custom">
                                        <!-- Tabs within a box -->
                                        <ul class="nav nav-tabs pull-left">
                                            <li class="active"><a href="#request" data-toggle="tab">Request Information</a></li>
                                            <li><a href="#info" data-toggle="tab">ARBO Profile</a></li>
                                            <li><a href="#history" data-toggle="tab">CAPDEV History</a></li>
                                        </ul>

                                        <%@include file="jspf/arboInfo.jspf"%>
                                    </div>
                                    <hr>        
                                </div>
                                <!-- /.box-body -->

                            </div>
                  
                        </div>
                        <!-- /.col -->
                    </div>


                </section>
                <!-- /.content -->

            </div>
            <!-- /.content-wrapper -->
        </div>
        <%@include file="jspf/footer.jspf" %>
        
            <script>
            var ctx = $('#barCanvas').get(0).getContext('2d');
            <%
                    Chart bar = new Chart();
                    String json = bar.getBarChartEducation(arbList);
            %>
            new Chart(ctx, <%out.print(json);%>);

            var ctx3 = $('#pieCanvas').get(0).getContext('2d');
            <%
                    Chart pie = new Chart();
                    String json3 = pie.getPieChartGender(arbList);
            %>
            new Chart(ctx3, <%out.print(json3);%>);

        </script>
        
        <script>
        </script>

    </body>
</html>