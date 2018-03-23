
<%-- 
    Document   : field-officer-arbo-list
    Created on : Jan 29, 2018, 4:08:13 PM
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
            <%if((Integer)session.getAttribute("userType") == 3){ // PFO%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%}else if((Integer)session.getAttribute("userType") == 4){ // RFO%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%}else if((Integer)session.getAttribute("userType") == 5){ // RFO%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}else if((Integer)session.getAttribute("userType") == 2){ // RFO%>
            <%@include file="jspf/point-person-sidebar.jspf"%>
            <%}%>

            <%
                APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
                AddressDAO addressDAO = new AddressDAO();
                UserDAO uDAO = new UserDAO();
                ARBODAO arboDAO = new ARBODAO();
                ARBDAO arbDAO = new ARBDAO();
                
                ReportsDAO rDAO = new ReportsDAO();
                ARB arb = new ARB();
                
                int arbID = 0;
                Evaluation e = (Evaluation)request.getAttribute("evaluation");
                
                if((Integer)request.getAttribute("arbID") != null){
                    arbID = (Integer)request.getAttribute("arbID");
                    arb = arbDAO.getARBByID(arbID);
                }
                
                ArrayList<Evaluation> filteredEvaluations = rDAO.getAllFilteredReleasesByRequests(arbID,1,e.getEvaluationStartDate(),e.getEvaluationEndDate());
                
                
                
            %>

            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        REPORT<small> OVERALL RATING: <%out.print(arb.getFullName());%></small>
                    </h1>
                </section>

                <!-- Main content -->
                <section class="invoice">
                    <!-- title row -->
                    <div class="row">
                        <div class="col-xs-12">
                            <h2 class="page-header">
                                <i class="fa fa-globe"></i> OVERALL RATING: <%out.print(arb.getFullName());%>
                                <div class="pull-right">
                                    <small><%out.print(f.format(e.getEvaluationStartDate()));%>-<%out.print(f.format(e.getEvaluationEndDate()));%></small>
                                </div>
                            </h2>
                        </div>
                    </div>
                    <div class="row invoice-info">
                        <div class="text-center">
                            <canvas id="lineCAPDEV" style="height:500px"></canvas>
                        </div>
                    </div>
                    <div class="row invoice-info">
                        <div class="col-xs-12">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Evaluation DTN</th>
                                        <th>Rating</th>
                                        <th>Evaluation Date</th>
                                        <th>Evaluation Start & End</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%

                                        for (Evaluation evalArb : filteredEvaluations) {
                                    %>
                                    <tr>
                                        <td><%out.print(evalArb.getEvaluationDTN());%></td>
                                        <td><%out.print(evalArb.getRating());%></td>
                                        <td><%out.print(evalArb.getEvaluationDate());%></td>
                                        <td><%out.print(evalArb.getEvaluationStartDate() + "-" + evalArb.getEvaluationEndDate());%></td>
                                    </tr>
                                    <%}%>

                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th>Evaluation DTN</th>
                                        <th>Rating</th>
                                        <th>Evaluation Date</th>
                                        <th>Evaluation Start & End</th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="row no-print">
                        <div class="col-xs-12">
                            <a href="#" id="print" onclick="myFunction()" class="btn btn-default pull-right"><i class="fa fa-print"></i> Print</a>
                        </div>
                    </div>
                </section>
                <!-- /.content -->
                <div class="clearfix"></div>
            </div>
            <!-- /.content-wrapper -->
        </div>
        <%@include file="jspf/footer.jspf" %>

        <script>
            $(function () {

                var ctx = $('#lineARB').get(0).getContext('2d');

            <%Chart chart = new Chart();%>
            <%String json = chart.getARBRating(filteredEvaluations);%>
                new Chart(ctx, <%out.print(json);%>);
            });

            function myFunction() {
                window.print();
            }
        </script>

    </body>
</html>