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
                                <span class="info-box-icon bg-yellow"><i class="fa fa-warning"></i></span>

                                <div class="info-box-content">
                                    <span class="info-box-text">Delayed Requests</span>
                                    <span class="info-box-number"><%out.print(apcpRequestDAO.getPercentage(apcpRequestDAO.getDelayedRequests(provincialRequests),provincialRequests.size()));%><small>%</small></span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>
                            <!-- /.info-box -->
                        </div>
                        <!-- /.col -->
                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="info-box">
                                <span class="info-box-icon bg-red"><i class="fa fa-hourglass-end"></i></span>

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
                                <span class="info-box-icon bg-orange"><i class="fa fa-pause"></i></span>

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
                                <%if (apcpBudget.getBudget() <= 100000){%>
                                <span class="info-box-icon bg-red"><i class="fa fa-money"></i></span>
                                    <%}else{%>
                                <span class="info-box-icon bg-green"><i class="fa fa-money"></i></span>
                                    <%}%>
                                <div class="info-box-content">
                                    <span class="info-box-text">APCP BUDGET</span>
                                    <span class="info-box-number"><%out.print(currency.format(apcpBudget.getBudget()));%></span>
                                </div>
                            </div>
                            <!-- /.info-box -->
                        </div>
                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <div class="info-box">
                                <%if (capdevBudget.getBudget() <= 100000){%>
                                <span class="info-box-icon bg-red"><i class="fa fa-database"></i></span>
                                    <%}else{%>
                                <span class="info-box-icon bg-green"><i class="fa fa-database"></i></span>
                                    <%}%>
                                <div class="info-box-content">
                                    <span class="info-box-text">CAPDEV BUDGET</span>
                                    <span class="info-box-number"><%out.print(currency.format(capdevBudget.getBudget()));%></span>
                                </div>
                            </div>
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
                                    <table id="table" class="table table-bordered table-striped modTable">
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
                                    <h3 class="box-title"><a href="view-apcp-status.jsp">Agrarian Production Credit Program (APCP)</a></h3>

                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">

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
                                                <span class="progress-number"><%out.print(apcpRequestDAO.getDistinctARBOCountWithReleased(provincialRequests,year));%>/<strong><%out.print(apcpRequestDAO.getDistinctARBOCountTarget(provincialRequests,year));%></strong></span> 

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-aqua" style="width: <%out.print(apcpRequestDAO.getPercentage(apcpRequestDAO.getDistinctARBOCountWithReleased(provincialRequests,year),apcpRequestDAO.getDistinctARBOCountTarget(provincialRequests,year)) + "%");%>"></div>
                                                </div>
                                            </div>

                                            <div class="progress-group">
                                                <span class="progress-text">ARBs SERVED</span>
                                                <span class="progress-number"><%out.print(apcpRequestDAO.getDistinctRecipientCountWithReleased(provincialRequests,year));%>/<strong><%out.print(apcpRequestDAO.getDistinctRecipientCountTarget(provincialRequests,year));%></strong></span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-red" style="width: <%out.print(apcpRequestDAO.getPercentage(apcpRequestDAO.getDistinctRecipientCountWithReleased(provincialRequests,year),apcpRequestDAO.getDistinctRecipientCountTarget(provincialRequests,year)) + "%");%>"></div>
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
                                                <span class="progress-number"><%out.print(currency.format(apcpRequestDAO.getYearlyBudgetAllocated(provincialRequests, year)));%>/<strong><%out.print(currency.format(apcpBudget.getBudget()));%></strong></span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-yellow" style="width: <%out.print(apcpRequestDAO.getPercentage(apcpRequestDAO.getYearlyBudgetAllocated(provincialRequests, year),apcpBudget.getBudget()) + "%");%>"></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <center>
                                            <%
                                                        for(APCPRequest req : allRequestStatus){ // THIS IS A STATUS
                                                        if(req.getRequestStatus() >= 0 && req.getRequestStatus() <= 4){
                                            %>
                                            <div class="btn-group">
                                                <button class="btn btn-default" id="APCPOnTrackDelayed<%out.print(req.getRequestStatus());%>"><%out.print(req.getRequestStatusDesc());%></button>
                                                <button class="btn btn-success" id="APCPOnTrackDelayed<%out.print(req.getRequestStatus());%>" data-toggle="modal" data-target="#modal-default"><%out.print(apcpRequestDAO.getOnTrackRequestsPerStatus(provincialRequests,req.getRequestStatus()));%> </button>
                                                <button class="btn btn-danger" id="APCPOnTrackDelayed<%out.print(req.getRequestStatus());%>" data-toggle="modal" data-target="#modal-info"><strong><%out.print(apcpRequestDAO.getDelayedRequestsPerStatus(provincialRequests,req.getRequestStatus()));%></strong></button>
                                            </div>
                                            &nbsp;

                                            <div class="modal modal-primary fade" id="modal-primary">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span></button>
                                                            <h4 class="modal-title">Primary Modal</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <p>One fine body&hellip;</p>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>
                                                            <button type="button" class="btn btn-outline">Save changes</button>
                                                        </div>
                                                    </div>
                                                    <!-- /.modal-content -->
                                                </div>
                                                <!-- /.modal-dialog -->
                                            </div>

                                            <div class="modal modal-primary fade" id="modal-info">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span></button>
                                                            <h4 class="modal-title">Primary Modal</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <p>One fine body&hellip;</p>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>
                                                            <button type="button" class="btn btn-outline">Save changes</button>
                                                        </div>
                                                    </div>
                                                    <!-- /.modal-content -->
                                                </div>
                                                <!-- /.modal-dialog -->
                                            </div>
                                            <%}%>
                                            <%}%>
                                            <div class="row">
                                                <h6>Status: <strong class="bg-green">On-track</strong> | <strong class="bg-red">Delayed</strong></h6>
                                            </div>
                                        </center>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <div class="row">
                                        <div class="col-sm-3 col-xs-6" id="totalARBOsAPCP">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 17%</span>
                                                <h5 class="description-header"><%out.print(apcpRequestDAO.getDistinctARBOCountWithReleased(provincialRequests,year));%></h5>
                                                <span class="description-text">TOTAL ARBOs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalARBsAPCP">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                <h5 class="description-header"><%out.print(apcpRequestDAO.getDistinctRecipientCountWithReleased(provincialRequests,year));%></h5>
                                                <span class="description-text">TOTAL ARBs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalReleasedAmountAPCP">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 20%</span>
                                                <h5 class="description-header"><%out.print(currency.format(apcpRequestDAO.getYearlySumOfReleasesByRequest(provincialRequests,year)));%></h5>
                                                <span class="description-text">TOTAL RELEASED AMOUNT (<%out.print(year);%>)</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalPastDueAmountAPCP">
                                            <div class="description-block">
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> 18%</span>
                                                <h5 class="description-header"><%out.print(currency.format(apcpRequestDAO.getYearlyTotalPastDueAmount(provincialRequests, year)));%></h5>
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
                                                <h5 class="description-header"><%out.print(currency.format(apcpRequestDAO.getSumOfAccumulatedReleasesByRequest(provincialRequests)));%></h5>
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
                                    <h3 class="box-title"><a href="view-capdev-status.jsp">Capacity Development (CAPDEV)</a></h3>

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
                                                <span class="progress-number"><b><%out.print(capdevDAO.getDistinctARBOCountWithImplemented(implementedPlans,year));%></b>/<%out.print(capdevDAO.getDistinctARBOCountTarget(allPlans,year));%></span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-aqua" style="width: <%out.print(capdevDAO.getPercentage(capdevDAO.getDistinctARBOCountWithImplemented(implementedPlans,year),capdevDAO.getDistinctARBOCountTarget(allPlans,year)) + "%");%>"></div>
                                                </div>
                                            </div>

                                            <div class="progress-group">
                                                <span class="progress-text">ARBs Served</span>
                                                <span class="progress-number"><b><%out.print(capdevDAO.getDistinctParticipantCountWithImplemented(implementedPlans, year));%></b>/<%out.print(capdevDAO.getDistinctParticipantCountTarget(allPlans,year));%></span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-red" style="width: <%out.print(capdevDAO.getPercentage(capdevDAO.getDistinctParticipantCountWithImplemented(implementedPlans, year),capdevDAO.getDistinctParticipantCountTarget(allPlans,year)) + "%");%>"></div>
                                                </div>
                                            </div>

                                            <div class="progress-group">
                                                <span class="progress-text">Activities Implemented</span>
                                                <span class="progress-number"><b><%out.print(capdevDAO.getYearlyImplementedCount(implementedPlans, year));%></b>/<%out.print(capdevDAO.getYearlyPlanCount(allPlans,year));%></span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-green" style="width: <%out.print(capdevDAO.getPercentage(capdevDAO.getYearlyImplementedCount(implementedPlans, year),capdevDAO.getYearlyPlanCount(allPlans,year)) + "%");%>"></div>
                                                </div>
                                            </div>

                                            <div class="progress-group">
                                                <span class="progress-text">Budget</span>
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
                                                        for(CAPDEVPlan plan : allPlanStatus){ // THIS IS A STATUS
                                                        if(plan.getPlanStatus() == 1 || plan.getPlanStatus() == 2 || plan.getPlanStatus() == 4){
                                            %>

                                            <div class="btn-group">
                                                <button class="btn btn-default" id="CAPDEVOnTrackDelayed<%out.print(plan.getPlanStatus());%>"><%out.print(plan.getPlanStatusDesc());%></button>
                                                <button class="btn btn-success" id="CAPDEVOnTrackDelayed<%out.print(plan.getPlanStatus());%>" data-toggle="modal" data-target="#modal-default"><%out.print(capdevDAO.getOnTrackPlansPerStatus(allPlans,plan.getPlanStatus()));%> </button>
                                                <button class="btn btn-danger" id="CAPDEVOnTrackDelayed<%out.print(plan.getPlanStatus());%>" data-toggle="modal" data-target="#modal-info"><strong><%out.print(capdevDAO.getDelayedPlansPerStatus(allPlans,plan.getPlanStatus()));%></strong>  </button>
                                            </div>
                                            &nbsp;
                                            <div class="modal fade" id="modal-default">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span></button>
                                                            <h4 class="modal-title">Default Modal</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <p>One fine body&hellip;</p>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                            <button type="button" class="btn btn-primary">Save changes</button>
                                                        </div>
                                                    </div>
                                                    <!-- /.modal-content -->
                                                </div>
                                                <!-- /.modal-dialog -->
                                            </div>
                                            <div class="modal fade" id="modal-info">
                                                <div class="modal-dialog">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span></button>
                                                            <h4 class="modal-title">Default Modal</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <p>One fine body&hellip;</p>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                            <button type="button" class="btn btn-primary">Save changes</button>
                                                        </div>
                                                    </div>
                                                    <!-- /.modal-content -->
                                                </div>
                                                <!-- /.modal-dialog -->
                                            </div>
                                            <%}%>
                                            <%}%>
                                            <div class="row">
                                                <h6>Status: <strong class="bg-green">On-track</strong> | <strong class="bg-red">Delayed</strong></h6>
                                            </div>
                                        </center>
                                    </div>
                                </div>
                                <!-- ./box-body -->
                                <div class="box-footer">
                                    <div class="row">
                                        <div class="col-sm-3 col-xs-6" id="totalARBOsCAPDEV">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 17%</span>
                                                <h5 class="description-header"><%out.print(capdevDAO.getDistinctARBOCountWithImplemented(implementedPlans,year));%></h5>
                                                <span class="description-text">TOTAL ARBOs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalARBsCAPDEV">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                <h5 class="description-header"><%out.print(capdevDAO.getDistinctParticipantCountWithImplemented(implementedPlans, year));%></h5>
                                                <span class="description-text">TOTAL ARBs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalImplementedPlans">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 20%</span>
                                                <h5 class="description-header"><%out.print(capdevDAO.getYearlyImplementedCount(implementedPlans, year));%></h5>
                                                <span class="description-text">TOTAL PLANS IMPLEMENTED</span>
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
                                        <div class="col-sm-3" id="totalImplementedBudget">
                                            <div class="description-block border-right">
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                <h5 class="description-header"><%out.print(currency.format(capdevDAO.getYearlyImplementedBudget(implementedPlans, year)));%></h5>
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

            $(document).ready(function () {

                var ctxAPCP = document.getElementById('apcpChart');
                var APCPChart;
            <%
               Chart chart = new Chart();
               String json = "";
               json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince,0,3, "FOR CONDUIT APPROVAL"); // inital chart
            %>

                // INITIAL CHART VIEW!!!
                APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);


                $('#totalARBOsAPCP').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getLineChartAPCPTotalARBOs(releasedRequests, "ARBOs SERVED");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#totalARBsAPCP').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getLineChartAPCPTotalARBs(releasedRequests, "ARBs SERVED");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#totalReleasedAmountAPCP').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getLineChartTotalReleasedAmount(releasedRequests, "TOTAL RELEASED AMOUNT");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#totalPastDueAmountAPCP').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getLineChartTotalPastDueAmount(provincialRequests, "TOTAL PAST DUE AMOUNT");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

            <%--$('#cumulativeReleasedAmountAPCP').on('click', function () {
                APCPChart.destroy();
        <%
                    json = chart.getLineChart();
        %>
                APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
            });
                
            $('#cumulativePastDueAmountAPCP').on('click', function () {
                APCPChart.destroy();
        <%
                    json = chart.getLineChart();
        %>
                APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
            });--%>


                $('#APCPOnTrackDelayed0').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince, 0, 3, "FOR CONDUIT APPROVAL");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#APCPOnTrackDelayed1').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince, 1, 3, "REQUESTED");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#APCPOnTrackDelayed2').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince, 2, 3, "CLEARED");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#APCPOnTrackDelayed3').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince, 3, 3, "ENDORSED");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#APCPOnTrackDelayed4').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince, 4, 3, "APPROVED");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });



            });

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


        </script>

        <script type="text/javascript">

            $(document).ready(function () {

                var ctxCAPDEV = document.getElementById('capdevChart');
                var CAPDEVChart;
            <%
               Chart chart2 = new Chart();
               String json2 = "";
               json2 = chart2.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince,1,3, "PENDING"); // inital chart
            %>

                // INITIAL CHART VIEW!!!
                CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);


                $('#totalARBOsCAPDEV').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart.getLineChartCAPDEVTotalARBOs(implementedPlans, "ARBOs SERVED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#totalARBsCAPDEV').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart.getLineChartCAPDEVTotalARBs(implementedPlans, "ARBs SERVED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#totalImplementedPlans').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart.getLineChartCAPDEVTotalImplementedPlans(implementedPlans, "TOTAL PLANS IMPLEMENTED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#totalImplementedBudget').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart.getLineChartCAPDEVTotalImplementedBudget(implementedPlans, "IMPLEMENTED ACTIVITY BUDGET");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

            <%--$('#cumulativeReleasedAmountAPCP').on('click', function () {
                APCPChart.destroy();
        <%
                    json = chart.getLineChart();
        %>
                APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
            });
                
            $('#cumulativePastDueAmountAPCP').on('click', function () {
                APCPChart.destroy();
        <%
                    json = chart.getLineChart();
        %>
                APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
            });--%>


                $('#CAPDEVOnTrackDelayed1').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince, 1, 3, "PENDING");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#CAPDEVOnTrackDelayed2').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince, 2, 3, "APPROVED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#CAPDEVOnTrackDelayed4').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince, 4, 3, "ASSIGNED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });
            });




        </script>
    </body>
</html>