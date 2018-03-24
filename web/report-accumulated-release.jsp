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
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <%}else if((Integer)session.getAttribute("userType") == 5){ // RFO%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>

            <%
                APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
                AddressDAO addressDAO = new AddressDAO();
                UserDAO uDAO = new UserDAO();
                ARBODAO arboDAO = new ARBODAO();
                
                ReportsDAO rDAO = new ReportsDAO();
                
                ArrayList<ARBO> arboListParam = new ArrayList();
                ArrayList<APCPRequest> requests = new ArrayList();
                String place = "rwerwer";
                Evaluation e = (Evaluation)request.getAttribute("evaluation");
                
                if((Integer)session.getAttribute("userType") == 3){ // PFO
                    Province pro = addressDAO.getProvOffice((Integer)session.getAttribute("provOfficeCode"));
                    place = pro.getProvDesc();
                    arboListParam = arboDAO.getAllARBOsByProvince((Integer) session.getAttribute("provOfficeCode"));
                }else if((Integer)session.getAttribute("userType") == 4 || (Integer)session.getAttribute("userType") == 5 && (Integer)session.getAttribute("regOfficeCode") != 13){ // RFO
                    place = addressDAO.getRegDesc((Integer)session.getAttribute("regOfficeCode"));
                    arboListParam = arboDAO.getAllARBOsByRegion((Integer) session.getAttribute("regOfficeCode"));
                }else if((Integer)session.getAttribute("userType") == 5 && (Integer)session.getAttribute("regOfficeCode") == 13){ // RFO
                    place = "NATIONAL";
                    arboListParam = arboDAO.getAllARBOs();
                }
                
                requests = rDAO.getAllFilteredAccumulatedARBORequests(arboListParam,e.getEvaluationStartDate(),e.getEvaluationEndDate());
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
                            <canvas id="barTotalRequestedAmount" style="height:500px"></canvas>
                        </div>
                    </div>

                    <div class="row invoice-info">
                        <div class="text-center">
                            <canvas id="barTotalReleaseAmount" style="height:500px"></canvas>
                        </div>
                    </div>
                    <div class="row invoice-info">
                        <div class="col-xs-12">
                            <table class="table table-bordered table-striped export">
                                <thead>
                                    <tr>
                                        <th>Region</th>
                                        <th>Province</th>
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
                                        <td><%=arbo.getArboRegionDesc()%></td>
                                        <td><%=arbo.getArboProvinceDesc()%></td>
                                        <td><%=arbo.getArboName()%></td>
                                        <td><%=arboDAO.getARBCount(arbo.getArboID())%></td>
                                        <td><%=currency.format(req.getLoanAmount())%></td>
                                        <td><%=currency.format(req.getTotalReleasedAmount())%></td>
                                        <td><%=currency.format(req.getYearlyReleasedAmount())%></td>
                                        <td><%if(req.getDateLastRelease()!= null) out.print(req.getDateLastRelease());%></td>
                                        <td><%=currency.format(req.getTotalOSBalance())%></td>
                                        <td><%=currency.format(req.getTotalPastDueAmount())%></td>
                                        <td><%=req.printAllPastDueReasons()%></td>
                                    </tr>
                                    <%}%>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th>Region</th>
                                        <th>Province</th>
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

            var ctx = $('#barTotalRequestedAmount').get(0).getContext('2d');
            var ctx2 = $('#barTotalReleaseAmount').get(0).getContext('2d');

            <%Chart chart = new Chart();%>
            <%String json = chart.getTotalRequestedAmountBarChart(requests);%>
                new Chart(ctx, <%out.print(json);%>);
                
                
            <%Chart chart2 = new Chart();%>
            <%String json2 = chart2.getTotalReleasedAmountBarChart(requests);%>
                new Chart(ctx2, <%out.print(json2);%>);
            });
            
            function myFunction() {
                window.print();
            }
        </script>

    </body>
</html>