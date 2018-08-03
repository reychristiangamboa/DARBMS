<%-- 
    Document   : capdev-dashboard
    Created on : May 16, 2018, 7:13:25 PM
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

            <%@include file="jspf/field-officer-navbar.jspf"%>

            <%if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <% } else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <% } else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%}%>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <%if((Integer)session.getAttribute("userType") == 5){%>
                        <strong><i class="fa fa-dashboard"></i> Dashboard: Central</strong>
                        <%}else if((Integer)session.getAttribute("userType") == 4){%>
                        <strong><i class="fa fa-dashboard"></i> Dashboard: <%out.print((String)session.getAttribute("regOfficeDesc"));%></strong>
                        <%}else if((Integer)session.getAttribute("userType") == 3){%>
                        <strong><i class="fa fa-dashboard"></i> Dashboard: <%out.print((String)session.getAttribute("provOfficeDesc"));%></strong>
                        <%}%>
                    </h1>
                </section>

                <%
                ARBODAO arboDAO = new ARBODAO();
                ARBDAO arbDAO = new ARBDAO();
                AddressDAO addressDAO = new AddressDAO();
                CAPDEVDAO capdevDAO = new CAPDEVDAO();
                APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
                UserDAO uDAO = new UserDAO();
                
                int type = 1; // default type
                
                ArrayList<Region> regionList = addressDAO.getAllRegions();
                ArrayList<Province> provOfficeList = new ArrayList();
                
                ArrayList<Region> regions = new ArrayList(); // printing purposes
                ArrayList<Province> provOffices = new ArrayList(); // printing purposes
                
                if(request.getAttribute("regions") != null){
                    regions = (ArrayList<Region>)request.getAttribute("regions");
                }
                if(request.getAttribute("provOffices") != null){
                    provOffices = (ArrayList<Province>)request.getAttribute("provOffices");
                }
                
                ArrayList<ARBO> allArboList = new ArrayList();
                ArrayList<CAPDEVPlan> allPlans = new ArrayList();
                if((Integer)session.getAttribute("userType") == 5){ // CENTRAL
                    allArboList = arboDAO.getAllARBOs();
                    allPlans = capdevDAO.getAllCAPDEVPlan();
                }else if((Integer)session.getAttribute("userType") == 4){ // REGIONAL
                    allArboList = arboDAO.getAllARBOsByRegion((Integer)session.getAttribute("regOfficeCode"));    
                    provOfficeList = addressDAO.getAllProvOfficesRegion((Integer)session.getAttribute("regOfficeCode"));
                    allPlans = capdevDAO.getAllRegionalCAPDEVPlan((Integer)session.getAttribute("regOfficeCode"));
                }else if((Integer)session.getAttribute("userType") == 3){ // PROVINCIAL
                    allArboList = arboDAO.getAllARBOsByProvince((Integer)session.getAttribute("provOfficeCode")); 
                    allPlans = capdevDAO.getAllProvincialCAPDEVPlan((Integer)session.getAttribute("provOfficeCode"));
                }
                
            if(request.getAttribute("type") != null){ // filtered type
                type = (Integer)request.getAttribute("type");
            }
                
            if(request.getAttribute("filtered") != null){ // filtered ARBOs
                allArboList = (ArrayList<ARBO>)request.getAttribute("filtered");
            }
            
                %>

                <!-- Main content -->
                <section class="content">

                    <section class="content-header">
                        <h1>
                            <i class="fa fa-clipboard"></i> Agrarian Reform Beneficiary Organization (ARBO) Reports
                        </h1>
                    </section>

                    <section class="invoice no-print">
                        <div class="row">
                            <div class="col-xs-12">
                                <h2 class="page-header">
                                    <i class="fa fa-search"></i> Filter By
                                </h2>
                            </div>
                        </div>
                        <%if((Integer)session.getAttribute("userType") == 5){%> 
                        <div class="row">
                            <div class="col-xs-12">
                                <form id="drillDownGenderForm">
                                    <div class="row no-print">
                                        <div class="col-xs-12">
                                            <input type="radio" id="drillDownGender" name="filterBy" value="All" checked onclick="document.getElementById('regions').disabled = true;document.getElementById('provinces').disabled = true;">
                                            <label for="actName">Select All</label>
                                            &nbsp;&nbsp;
                                            <input type="radio" id="drillDownGender" name="filterBy" value="regions" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = true;">
                                            <label for="actName">Regions</label>
                                            &nbsp;&nbsp;
                                            <input type="radio" id="drillDownGender" name="filterBy" value="provinces" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = false;">
                                            <label for="actName">Provinces</label>
                                        </div>
                                    </div>
                                    <div class="row no-print">
                                        <div class="col-xs-3">
                                            <div class="form-group">
                                                <label for="actName">Type</label>
                                                <select name="type" id="type" class="form-control select2">
                                                    <option value="1">Average Days Unsettled</option>
                                                    <option value="2">Average Participation Rate</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-xs-3">
                                            <div class="form-group">
                                                <label for="actName">Regions</label>
                                                <select name="regions[]" id="regions" onchange="chg2()" class="form-control select2" multiple="multiple" disabled>
                                                    <%for(Region r : regionList){%>
                                                    <option value="<%=r.getRegCode()%>"><%out.print(r.getRegDesc());%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-xs-3">
                                            <div class="form-group">
                                                <label for="actName">Provinces</label>
                                                <select name="provinces[]" id="provinces" class="form-control select2" multiple="multiple" disabled>

                                                </select>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row no-print">
                                        <button type="submit" onclick="form.action = 'FilterARBODashboard'" class="btn btn-success pull-right"><i class="fa fa-filter margin-r-5"></i> Filter</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <%}else if((Integer)session.getAttribute("userType") == 4){%>
                        <div class="row">
                            <div class="col-xs-12">
                                <form id="drillDownGenderForm">
                                    <div class="row no-print">
                                        <div class="col-xs-12">
                                            <input type="radio" id="drillDownGender" name="filterBy" value="All" checked onclick="document.getElementById('regions').disabled = true;document.getElementById('provinces').disabled = true;">
                                            <label for="actName">Select All</label>
                                            &nbsp;&nbsp;
                                            <input type="radio" id="drillDownGender" name="filterBy" value="provinces" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = false;">
                                            <label for="actName">Provinces</label>
                                        </div>
                                    </div>
                                    <div class="row no-print">
                                        <div class="col-xs-4">
                                            <div class="form-group">
                                                <label for="actName">Type</label>
                                                <select name="type" id="type" class="form-control select2">
                                                    <option value="1">Average Days Unsettled</option>
                                                    <option value="2">Average Participation Rate</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-xs-5">
                                            <div class="form-group">
                                                <label for="actName">Provinces</label>
                                                <select name="provinces[]" id="provinces" class="form-control select2" multiple="multiple" disabled>
                                                    <%for(Province p : provOfficeList){%>
                                                    <option value="<%out.print(p.getProvCode());%>"><%out.print(p.getProvDesc());%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="row no-print">
                                        <button type="submit" onclick="form.action = 'FilterARBODashboard'" class="btn btn-success pull-right"><i class="fa fa-filter margin-r-5"></i> Filter</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <%}else if((Integer)session.getAttribute("userType") == 4){%>
                        <div class="row">
                            <div class="col-xs-12">
                                <form id="drillDownGenderForm">
                                    <input type="hidden" name="filterBy" value="All">
                                    <div class="row no-print">
                                        <div class="col-xs-4">
                                            <div class="form-group">
                                                <label for="actName">Type</label>
                                                <select name="type" id="type" class="form-control select2">
                                                    <option value="1">Average Days Unsettled</option>
                                                    <option value="2">Average Participation Rate</option>
                                                </select>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="row no-print">
                                        <button type="submit" onclick="form.action = 'FilterARBODashboard'" class="btn btn-success pull-right"><i class="fa fa-filter margin-r-5"></i> Filter</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <%}%>
                    </section>

                    <section class="invoice">
                        <!-- title row -->
                        <div class="row invoice-info">
                            <div class="row">
                                <div class="col-xs-12">
                                    <div class="box-body">
                                        <canvas id="chartCanvas"></canvas>
                                        <div class="row text-center no-print">
                                            <a class="btn btn-submit" data-toggle="modal" data-target="#modalPie">View More</a>
                                        </div>
                                        <div class="modal fade" id="modalPie">
                                            <div class="modal-dialog modal-lg">
                                                <%if(type == 1){ // AVE. DAYS UNSETTLED%>
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span></button>
                                                        <h4 class="modal-title">ARBO: Credit Standing</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="row">
                                                            <div class="col-xs-12">
                                                                <table class="table table-striped table-bordered export">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>ARBO</th>
                                                                            <th>Average Days Unsettled</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <%
                                                                            for(ARBO arbo : allArboList){
                                                                                arbo.setRequestList(apcpRequestDAO.getAllARBORequests(arbo.getArboID()));
                                                                        %>
                                                                        <tr>
                                                                            <td><a target="_blank" rel="noopener noreferrer" href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                                            <td><%out.print(df.format(apcpRequestDAO.getAverageDaysUnsettled(arbo.getRequestList())));%></td>
                                                                        </tr>
                                                                        <%}%>
                                                                    </tbody>
                                                                    <tfoot>
                                                                        <tr>
                                                                            <th>ARBO</th>
                                                                            <th>Average Days Unsettled</th>
                                                                        </tr>
                                                                    </tfoot>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                    </div>
                                                </div>
                                                <%}else if(type == 2){%>
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span></button>
                                                        <h4 class="modal-title">ARBO: Participation Rate</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="row">
                                                            <div class="col-xs-12">
                                                                <table class="table table-striped table-bordered export">
                                                                    <thead>
                                                                        <tr>
                                                                            <th>ARBO</th>
                                                                            <th>Average Participation Rate</th>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>
                                                                        <%
                                                                            for(ARBO arbo : allArboList){
                                                                                arbo.setArbList(arbDAO.getAllARBsARBO(arbo.getArboID()));
                                                                        %>
                                                                        <tr>
                                                                            <td><a target="_blank" rel="noopener noreferrer" href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                                            <td><%out.print(df.format(capdevDAO.getMeanAverageAttendanceRateARBO(arbo.getArbList())));%></td>
                                                                        </tr>
                                                                        <%}%>
                                                                    </tbody>
                                                                    <tfoot>
                                                                        <tr>
                                                                            <th>ARBO</th>
                                                                            <th>Average Participation Rate</th>
                                                                        </tr>
                                                                    </tfoot>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                    </div>
                                                </div>
                                                <%}%>
                                            </div>
                                            <!-- /.modal-dialog -->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-xs-12">

                                    <%if(type == 1){ // AVE. DAYS UNSETTLED%>
                                    <table class="table table-striped table-bordered export">
                                        <thead>
                                            <tr>
                                                <th>ARBO</th>
                                                <th>Average Days Unsettled</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for(ARBO arbo : allArboList){
                                                    arbo.setRequestList(apcpRequestDAO.getAllARBORequests(arbo.getArboID()));
                                            %>
                                            <tr>
                                                <td><a target="_blank" rel="noopener noreferrer" href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(df.format(apcpRequestDAO.getAverageDaysUnsettled(arbo.getRequestList())));%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ARBO</th>
                                                <th>Average Days Unsettled</th>
                                            </tr>
                                        </tfoot>
                                    </table>

                                    <%}else if(type == 2){%>
                                    <table class="table table-striped table-bordered export">
                                        <thead>
                                            <tr>
                                                <th>ARBO</th>
                                                <th>Average Participation Rate</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for(ARBO arbo : allArboList){
                                                    arbo.setArbList(arbDAO.getAllARBsARBO(arbo.getArboID()));
                                            %>
                                            <tr>
                                                <td><a target="_blank" rel="noopener noreferrer" href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(df.format(capdevDAO.getMeanAverageAttendanceRateARBO(arbo.getArbList())));%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ARBO</th>
                                                <th>Average Participation Rate</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                    <%}%>

                                </div>
                            </div>
                        </div>
                        <div class="row no-print">
                            <div class="col-xs-12">
                                <button type="button" onclick="window.print()" class="btn btn-default pull-right"><i class="fa fa-print"></i> Print</button>
                            </div>
                        </div>
                    </section>



                </section>

            </div>
            <!-- /.row -->


            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>
        <script type="text/javascript">

            function chg2() {
                var values = $('#regions').val();

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('provinces').innerHTML = xhttp.responseText;
                    }
                };

                var url = "RegionalProvincesFilterRefresh?";
                for (i = 0; i < values.length; i++) {
                    if (i === 0) {
                        url += "valajax=" + values[i];
                    } else {
                        url += "&valajax=" + values[i];
                    }
                }


                xhttp.open("GET", url, true);
                xhttp.send();
            }

            $(function () {
                $('#dr-totalYearReleaseReport').daterangepicker(
                        {
                            minDate: moment().startOf('year'),
                            maxDate: moment().endOf('year'),
                            ranges: {
                                'This Month': [moment().startOf('month'), moment().endOf('month')],
                                'This Quarter': [moment().startOf('quarter'), moment().endOf('quarter')],
                                'This Year': [moment().startOf('year'), moment().endOf('year')]
                            }

                        },
                        function (start, end) {
                            $('#dr-totalYearReleaseReport span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                            $('#start-totalYearReleaseReport').val(start.format('YYYY-MM-DD'));
                            $('#end-totalYearReleaseReport').val(end.format('YYYY-MM-DD'));
                        }
                );

                $('#dr-totalAccumulatedReleaseReport').daterangepicker(
                        {
                            minDate: moment().startOf('year'),
                            maxDate: moment().endOf('year'),
                            ranges: {
                                'This Month': [moment().startOf('month'), moment().endOf('month')],
                                'This Quarter': [moment().startOf('quarter'), moment().endOf('quarter')],
                                'This Year': [moment().startOf('year'), moment().endOf('year')]
                            }

                        },
                        function (start, end) {
                            $('#dr-totalAccumulatedReleaseReport span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                            $('#start-totalAccumulatedReleaseReport').val(start.format('YYYY-MM-DD'));
                            $('#end-totalAccumulatedReleaseReport').val(end.format('YYYY-MM-DD'));
                        }
                );


            });

            $(function () {
                var ctx = $('#chartCanvas').get(0).getContext('2d');
            <%
                Chart chart = new Chart();
                String json = "";
                
                if(type == 1){ // PLANS
                    json = chart.getStackedBarChartARBODaysUnsettled(regions,provOffices,allArboList);
                }else if(type == 2){ // APPROVAL RATE
                    json = chart.getStackedBarChartARBOParticipationRate(regions,provOffices,allArboList);
                }
                
            %>
                new Chart(ctx, <%out.print(json);%>);
            });
        </script>
    </body>
</html>

