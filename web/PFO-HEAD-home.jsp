<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>

            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>

                        <strong><i class="fa fa-dashboard"></i> Dashboard</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>

                    </h1>

                </section>

                <!-- Main content -->
                <section class="content">
                    <!-- Info boxes -->
                    <div class="row">


                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="info-box">
                                <span class="info-box-icon bg-aqua"><i class="ion ion-ios-gear-outline"></i></span>

                                <div class="info-box-content">
                                    <span class="info-box-text">Delayed Requests</span>
                                    <span class="info-box-number">90<small>%</small></span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>
                            <!-- /.info-box -->
                        </div>
                        <!-- /.col -->
                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="info-box">
                                <span class="info-box-icon bg-red"><i class="fa fa-google-plus"></i></span>

                                <div class="info-box-content">
                                    <span class="info-box-text">Past Due Accounts</span>
                                    <span class="info-box-number">41,410</span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>
                            <!-- /.info-box -->
                        </div>
                        <!-- /.col -->

                        <!-- fix for small devices only -->
                        <div class="clearfix visible-sm-block"></div>

                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="info-box">
                                <span class="info-box-icon bg-green"><i class="ion ion-ios-cart-outline"></i></span>

                                <div class="info-box-content">
                                    <span class="info-box-text">Postponed Plans</span>
                                    <span class="info-box-number"><%out.print(postponedPlans.size());%></span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>
                            <!-- /.info-box -->
                        </div>
                        <!-- /.col -->
                        <!-- /.col -->
                    </div>
                    <div class="row">
                        <div class="col-md-2 col-sm-6 col-xs-12">
                        </div>
                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="info-box">
                                <span class="info-box-icon bg-yellow"><i class="ion ion-ios-people-outline"></i></span>

                                <div class="info-box-content">
                                    <span class="info-box-text">APCP BUDGET</span>
                                    <span class="info-box-number"><%out.print(currency.format(apcpBudget.getBudget()));%></span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>
                            <!-- /.info-box -->
                        </div>
                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="info-box">
                                <span class="info-box-icon bg-yellow"><i class="ion ion-ios-people-outline"></i></span>

                                <div class="info-box-content">
                                    <span class="info-box-text">CAPDEV BUDGET</span>
                                    <span class="info-box-number"><%out.print(currency.format(capdevBudget.getBudget()));%></span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>
                            <!-- /.info-box -->
                        </div>
                        <div class="col-md-2 col-sm-6 col-xs-12">
                        </div>
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-md-6">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title"><a href="PFO-HEAD-view-new-accessing-conduits.jsp">Pending Conduit Access</a></h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table class="table table-bordered table-striped modTable">
                                        <thead>
                                            <tr>
                                                <th>ARBO</th>
                                                <th>Address</th>
                                                <th>Date Requested</th>
                                                <th>Amount</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for(APCPRequest req : newAccessingRequests){
                                                    ARBO arbo = arboDAO.getARBOByID(req.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="SelectARBORequest?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(arbo.getFullAddress());%></td>
                                                <td><%out.print(req.getDateRequested());%></td>
                                                <td><%out.print(currency.format(req.getLoanAmount()));%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ARBO</th>
                                                <th>Address</th>
                                                <th>Date Requested</th>
                                                <th>Amount</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>    
                        </div>
                        <div class="col-md-6">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title"><a href="view-capdev-status.jsp">Pending CAPDEV Plans</a></h3>
                                </div>
                                <div class="box-body">
                                    <table class="table table-bordered table-striped modTable">
                                        <thead>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>ARBO</th>
                                                <th>Plan Date</th>
                                                <th>Amount</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                            for(CAPDEVPlan plan : pendingPlans){
                                                APCPRequest r = apcpRequestDAO.getRequestByID(plan.getRequestID());
                                                ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="ViewCAPDEVProposal?planID=<%out.print(plan.getPlanID());%>"></a><%out.print(plan.getPlanDTN());%></td>
                                                <td><a target="_blank" rel="noopener noreferrer" href="ViewARBO?id=<%out.print(arbo.getArboID());%>"></a><%out.print(arbo.getArboName());%></td>
                                                <td><%out.print(plan.getPlanDate());%></td>
                                                <td><%out.print(currency.format(plan.getBudget()));%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>ARBO</th>
                                                <th>Plan Date</th>
                                                <th>Amount</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>    
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Agrarian Production Credit Program (APCP)</h3>

                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">


                                    <div class="row">
                                        <div class="col-xs-12">

                                        </div>
                                    </div>

                                    <div class="row">
                                        <div class="col-md-8 text-center">

                                            <canvas id="apcpChart"></canvas>

                                        </div>
                                        <!-- /.col -->

                                        <div class="col-md-4">
                                            <p class="text-center">
                                                <strong>APCP: Actual vs. Targets</strong>
                                            </p>

                                            <div class="progress-group">
                                                <span class="progress-text">ARBOs SERVED</span>
                                                <span class="progress-number"><%out.print(apcpRequestDAO.getDistinctARBOCountWithReleased(provincialRequests));%>/<strong><%out.print(apcpRequestDAO.getDistinctARBOCountTarget(provincialRequests));%></strong></span> 

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-aqua" style="width: <%out.print(apcpRequestDAO.getPercentage(apcpRequestDAO.getDistinctARBOCountWithReleased(provincialRequests),apcpRequestDAO.getDistinctARBOCountTarget(provincialRequests)) + "%");%>"></div>
                                                </div>
                                            </div>

                                            <div class="progress-group">
                                                <span class="progress-text">ARBs SERVED</span>
                                                <span class="progress-number"><%out.print(apcpRequestDAO.getDistinctRecipientCountWithReleased(provincialRequests));%>/<strong><%out.print(apcpRequestDAO.getDistinctRecipientCountTarget(provincialRequests));%></strong></span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-red" style="width: <%out.print(apcpRequestDAO.getPercentage(apcpRequestDAO.getDistinctRecipientCountWithReleased(provincialRequests),apcpRequestDAO.getDistinctRecipientCountTarget(provincialRequests)) + "%");%>"></div>
                                                </div>
                                            </div>

                                            <div class="progress-group">
                                                <span class="progress-text">Budget Released</span>
                                                <span class="progress-number"><%out.print(currency.format(apcpRequestDAO.getYearlySumOfReleasesByRequest(releasedRequests,year)));%>/<strong><%out.print(currency.format(apcpRequestDAO.getYearlyTotalApprovedAmount(provincialRequests,year)));%></strong></span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-green" style="width: <%out.print(apcpRequestDAO.getPercentage(apcpRequestDAO.getYearlySumOfReleasesByRequest(provincialRequests,year),apcpRequestDAO.getYearlyTotalApprovedAmount(provincialRequests,year)) + "%");%>"></div>
                                                </div>
                                            </div>

                                            <div class="progress-group">
                                                <span class="progress-text">Budget Allocated</span>
                                                <span class="progress-number">250/<strong><%out.print(apcpBudget.getBudget());%></strong></span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-yellow" style="width: 80%"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /.row -->
                                    <!--
                                             <div class="col-md-2">
                                             <p class="text-center">
                                                <strong>Key Findings</strong>
                                            </p>
                                            <div class="row">
                                                <div class="box no-border">
                                                    <div class="box-body">
                                                        <p>Hello World</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="box no-border">
                                                    <div class="box-body">
                                                        <p>Hello World</p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="box no-border">
                                                    <div class="box-body">
                                                        <p>Hello World</p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    -->
                                    <hr>
                                    <div class="row">
                                        <center>
                                            <%
                                                        for(APCPRequest req : allRequestStatus){ // THIS IS A STATUS
                                                        if(req.getRequestStatus() >= 0 && req.getRequestStatus() <= 4){
                                            %>
                                            <button class="btn btn-primary"><%out.print(req.getRequestStatusDesc());%> : <%out.print(apcpRequestDAO.getOnTrackRequestsPerStatus(provincialRequests,req.getRequestStatus()));%> | <strong><%out.print(apcpRequestDAO.getDelayedRequestsPerStatus(provincialRequests,req.getRequestStatus()));%></strong>  </button>
                                            <%}%>
                                            <%}%>
                                            <div class="row">
                                                <h6>Status: On-track | <strong>Delayed</strong></h6>
                                            </div>
                                        </center>
                                    </div>
                                </div>
                                <!-- ./box-body -->
                                <div class="box-footer">
                                    <div class="row">
                                        <div class="col-sm-3 col-xs-6" id="totalARBOsAPCP">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 17%</span>
                                                <h5 class="description-header"><%out.print(apcpRequestDAO.getDistinctARBOCountWithReleased(provincialRequests));%></h5>
                                                <span class="description-text">TOTAL ARBOs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalARBsAPCP">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                <h5 class="description-header"><%out.print(apcpRequestDAO.getDistinctRecipientCountWithReleased(provincialRequests));%></h5>
                                                <span class="description-text">TOTAL ARBs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalReleasedAmountAPCP">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 20%</span>
                                                <h5 class="description-header"><%out.print(currency.format(apcpRequestDAO.getYearlySumOfReleasesByRequestId(provincialRequests,year)));%></h5>
                                                <span class="description-text">TOTAL RELEASED AMOUNT (<%out.print(year);%>)</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalPastDueAmountAPCP">
                                            <div class="description-block">
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> 18%</span>
                                                <h5 class="description-header"><%out.print(currency.format(apcpRequestDAO.getYearlyTotalPastDueAmount(provincialRequests)));%></h5>
                                                <span class="description-text">TOTAL PAST DUE (<%out.print(year);%>)</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-2"></div>
                                        <div class="col-sm-4 col-xs-6" id="cumulativeReleasedAmountAPCP">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 17%</span>
                                                <h5 class="description-header"><%out.print(currency.format(apcpRequestDAO.getSumOfAccumulatedReleasesByRequestId(provincialRequests)));%></h5>
                                                <span class="description-text">CUMULATIVE RELEASED AMOUNT</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-4 col-xs-6" id="cumulativePastDueAmountAPCP">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                <h5 class="description-header"><%out.print(currency.format(apcpRequestDAO.getTotalPastDueAmount(provincialRequests)));%></h5>
                                                <span class="description-text">CUMULATIVE PAST DUE AMOUNT</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-2"></div>
                                    </div>
                                    <!-- /.row -->
                                </div>
                                <!-- /.box-footer -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Capacity Development (CAPDEV)</h3>

                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-md-8 text-center">
                                            <canvas id="capdevChart"></canvas>
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-md-4">
                                            <p class="text-center">
                                                <strong>CAPDEV: Actuals vs. Targets</strong>
                                            </p>

                                            <div class="progress-group">
                                                <span class="progress-text">ARBOs Served</span>
                                                <span class="progress-number"><b>160</b>/200</span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-aqua" style="width: 80%"></div>
                                                </div>
                                            </div>

                                            <div class="progress-group">
                                                <span class="progress-text">ARBs Served</span>
                                                <span class="progress-number"><b>310</b>/400</span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-red" style="width: 80%"></div>
                                                </div>
                                            </div>

                                            <div class="progress-group">
                                                <span class="progress-text">Activities Implemented</span>
                                                <span class="progress-number"><b>480</b>/800</span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-green" style="width: 80%"></div>
                                                </div>
                                            </div>

                                            <div class="progress-group">
                                                <span class="progress-text">APCP Budget</span>
                                                <span class="progress-number"><b>250</b>/500</span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-yellow" style="width: 80%"></div>
                                                </div>
                                            </div>
                                        </div>

                                        <!-- /.col -->
                                    </div>
                                    <!-- /.row -->
                                    <hr>
                                    <div class="row">
                                        <center>
                                            <%
                                                        for(APCPRequest req : allRequestStatus){ // THIS IS A STATUS
                                                        if(req.getRequestStatus() >= 0 && req.getRequestStatus() <= 4){
                                            %>
                                            <button class="btn btn-primary"><%out.print(req.getRequestStatusDesc());%> : <%out.print(apcpRequestDAO.getOnTrackRequestsPerStatus(provincialRequests,req.getRequestStatus()));%> | <strong><%out.print(apcpRequestDAO.getDelayedRequestsPerStatus(provincialRequests,req.getRequestStatus()));%></strong>  </button>
                                            <%}%>
                                            <%}%>
                                            <div class="row">
                                                <h6>Status: On-track | <strong>Delayed</strong></h6>
                                            </div>
                                        </center>
                                    </div>
                                </div>
                                <!-- ./box-body -->
                                <div class="box-footer">
                                    <div class="row">
                                        <div class="col-sm-3 col-xs-6">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 17%</span>
                                                <h5 class="description-header"><%out.print(capdevDAO.getDistinctARBOCountWithImplemented(implementedPlans,year));%></h5>
                                                <span class="description-text">TOTAL ARBOs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                <h5 class="description-header"><%out.print(capdevDAO.getDistinctParticipantCountWithImplemented(implementedPlans, year));%></h5>
                                                <span class="description-text">TOTAL ARBs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 20%</span>
                                                <h5 class="description-header"><%out.print(implementedPlans.size());%></h5>
                                                <span class="description-text">TOTAL ACTIVITIES IMPLEMENTED</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6">
                                            <div class="description-block">
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> 18%</span>
                                                <h5 class="description-header"><%out.print(postponedPlans.size());%></h5>
                                                <span class="description-text">TOTAL POSTPONED ACTIVITIES</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-3">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                <h5 class="description-header"><%out.print(currency.format(capdevDAO.getCumulativeBudgetSpent(allPlans)));%></h5>
                                                <span class="description-text">IMPLEMENTED ACTIVITY BUDGET (<%out.print(year);%>)</span>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 col-xs-6">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 17%</span>
                                                <h5 class="description-header"><%out.print(currency.format(capdevDAO.getCumulativeApprovedAmount(allPlans)));%></h5>
                                                <span class="description-text">POSTPONED ACTIVITY BUDGET (<%=year%>)</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                <h5 class="description-header"><%out.print(currency.format(capdevDAO.getCumulativeBudgetSpent(allPlans)));%></h5>
                                                <span class="description-text">CUMULATIVE IMPLEMENTED BUDGET</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                <h5 class="description-header"><%out.print(currency.format(capdevDAO.getCumulativeBudgetSpent(allPlans)));%></h5>
                                                <span class="description-text">CUMULATIVE POSTPONED BUDGET</span>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /.row -->
                                </div>
                                <!-- /.box-footer -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>

                </section>
                <!-- /.content -->
            </div>


        </div>
        <!-- ./wrapper -->

        <%@include file="jspf/footer.jspf" %>
        <script type="text/javascript">

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

            $(document).ready(function () {
                var ctxAPCP = document.getElementById('apcpChart');
            <%
               Chart chart = new Chart();
               String total = "";
            %>

                $('#totalARBOsAPCP').on('click', function({
                chart.destroy();
                json = chart.getLine
                        ));
            });
        </script>
    </body>
</html>