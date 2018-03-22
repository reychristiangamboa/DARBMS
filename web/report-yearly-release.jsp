
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
            <%}%>

            <%
                APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
                AddressDAO addressDAO = new AddressDAO();
                UserDAO uDAO = new UserDAO();
                ARBODAO arboDAO = new ARBODAO();
                
                ReportsDAO rDAO = new ReportsDAO();
                
                ArrayList<APCPRequest> requestsParam = new ArrayList();
                ArrayList<APCPRequest> requests = new ArrayList();
                String place = "rwerwer";
                Evaluation e = (Evaluation)request.getAttribute("evaluation");
                
                if((Integer)session.getAttribute("userType") == 3){ // PFO
                    Province pro = addressDAO.getProvOffice((Integer)session.getAttribute("provOfficeCode"));
                    place = pro.getProvDesc();
                    requestsParam = apcpRequestDAO.getAllProvincialRequests((Integer)session.getAttribute("provOfficeCode"));
                }else if((Integer)session.getAttribute("userType") == 4){ // RFO
                    place = addressDAO.getRegDesc((Integer)session.getAttribute("regOfficeCode"));
                    requestsParam = apcpRequestDAO.getAllRegionalRequests((Integer) session.getAttribute("regOfficeCode"));;
                }
                
                requests = rDAO.getAllFilteredReleasesByRequests(requestsParam,e.getEvaluationStartDate(),e.getEvaluationEndDate());
                System.out.print(requests.size());
                
                
            %>

            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        REPORT<small>TOTAL <%out.print(year);%> RELEASED AMOUNT</small>
                    </h1>
                </section>

                <!-- Main content -->
                <section class="invoice">
                    <!-- title row -->
                    <div class="row">
                        <div class="col-xs-12">
                            <h2 class="page-header">
                                <i class="fa fa-globe"></i> TOTAL <%out.print(year);%> RELEASED AMOUNT: <%=place%>
                                <div class="pull-right">
                                    <small><%out.print(f.format(e.getEvaluationStartDate()));%>-<%out.print(f.format(e.getEvaluationEndDate()));%></small>
                                </div>
                            </h2>
                        </div>
                    </div>
                    <div class="row invoice-info">
                        <div class="text-center">
                            <canvas id="barTotalYear" style="height:500px"></canvas>
                        </div>
                    </div>
                    <div class="row invoice-info">
                        <div class="col-xs-12">
                            <table class="table table-bordered table-striped">
                                <thead>
                                    <tr>
                                        <th>Loan Tracking No.</th>
                                        <th>ARBO Name</th>
                                        <th>Release Amount</th>
                                        <th>Release Date</th>
                                        <th>Released By</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for(APCPRequest req : requests){
                                            for(APCPRelease rel : req.getReleases()){
                                                ARBO arbo = arboDAO.getARBOByID(req.getArboID());
                                                User u = uDAO.searchUser(rel.getReleasedBy());
                                    %>
                                    <tr>
                                        <td><%=req.getLoanTrackingNo()%></td>
                                        <td><%=arbo.getArboName()%></td>
                                        <td><%=currency.format(rel.getReleaseAmount())%></td>
                                        <td><%=rel.getReleaseDate()%></td>
                                        <td><%=u.getFullName()%></td>
                                    </tr>
                                    <%
                                            }                                                                            
                                        }
                                    %>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th>Loan Tracking No.</th>
                                        <th>ARBO Name</th>
                                        <th>Release Amount</th>
                                        <th>Release Date</th>
                                        <th>Released By</th>
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

                var ctx = $('#barTotalYear').get(0).getContext('2d');

            <%Chart chart = new Chart();%>
            <%String json = chart.getTotalYearBarChart(requests);%>
                new Chart(ctx, <%out.print(json);%>);
            });

            function myFunction() {
                window.print();
            }
        </script>

    </body>
</html>