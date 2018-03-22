<%-- 
    Document   : report-accumulated-release
    Created on : Mar 22, 2018, 2:53:51 PM
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
                
                requests = rDAO.getAccumulatedReleasesByRequests(requestsParam,e.getEvaluationStartDate(),e.getEvaluationEndDate());
            %>

            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        REPORT<small>TOTAL RELEASED ACCUMULATED AMOUNT</small>
                    </h1>
                </section>

                <!-- Main content -->
                <section class="invoice">
                    <!-- title row -->
                    <div class="row">
                        <div class="col-xs-12">
                            <h2 class="page-header">
                                <i class="fa fa-globe"></i> TOTAL RELEASED ACCUMULATED AMOUNT: <%=place%>
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
                                        <th>ARBO Name</th>
                                        <th>No. of ARBs</th>
                                        <th>Total Approved Amount</th>
                                        <th>Accumulated Releases</th>
                                        <th><%=year%> Releases</th>
                                        <th>Date of Last Release</th>
                                        <th>O/S Balance</th>
                                        <th>Past Due Amount</th>
                                        <th>Past Due Reason</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        for(APCPRequest req : requests){
                                            ARBO arbo = arboDAO.getARBOByID(req.getArboID());
                                    %>
                                    <tr>
                                        <td><%=arbo.getArboName()%></td>
                                        <td><%=arboDAO.getARBCount(arbo.getArboID())%></td>
                                        <td><%out.print("N/A");%></td>
                                        <td><%=req.getTotalReleaseAmount()%></td>
                                        <td><%out.print("N/A");%></td>

                                        <%if(!req.getReleases().isEmpty()){%>
                                        <td><%=f.format(req.getDateLastReleased())%></td>
                                        <%}else{%>
                                        <td><%out.print("N/A");%></td>
                                        <%}%>

                                        <td><%=req.getAccumulatedOSBalance()%></td>
                                        <td><%=currency.format(req.getTotalPDAAmount())%></td>
                                        <td><%out.print("N/A");%></td>
                                    </tr>
                                    <%
                                                                                                                       
                                        }
                                    %>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th>ARBO Name</th>
                                        <th>No. of ARBs</th>
                                        <th>Total Approved Amount</th>
                                        <th>Accumulated Releases</th>
                                        <th><%=year%> Releases</th>
                                        <th>Date of Last Release</th>
                                        <th>O/S Balance</th>
                                        <th>Past Due Amount</th>
                                        <th>Past Due Reason</th>
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