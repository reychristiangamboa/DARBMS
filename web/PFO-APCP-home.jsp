<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>

    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/pfo-apcp-sidebar.jspf"%>

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
                    
                    <%if(issueDAO.retrieveUnresolvedIssues((Integer)session.getAttribute("userType"),(Integer)session.getAttribute("provOfficeCode")).size() > 0){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> You have <a href="view-issues.jsp">ISSUES</a> pending. Please resolve them immediately.</h4>
                    </div>
                    <%}%>
                    
                    
                    <div class="row">
                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <%
                                double delayedPercentage = apcpRequestDAO.getPercentage(apcpRequestDAO.getDelayedRequests(provincialRequests),provincialRequests.size());
                            %>
                            <div class="info-box">
                                <%if (delayedPercentage <= 20) {%>
                                <span class="info-box-icon bg-green"><i class="fa fa-warning"></i></span>
                                <%}else if (delayedPercentage <= 50){%>
                                <span class="info-box-icon bg-yellow"><i class="fa fa-warning"></i></span>
                                <%}else{%>
                                <span class="info-box-icon bg-red"><i class="fa fa-warning"></i></span>
                                <%}%>
                                <div class="info-box-content">
                                    <span class="info-box-text">Delayed Requests</span>
                                    <span class="info-box-number"><%out.print(df.format(delayedPercentage));%><small>%</small></span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>

                            <!-- /.info-box -->
                        </div>
                        <!-- /.col -->
                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <%
                                double pdaPercentage = apcpRequestDAO.getPercentage(unsettledPDAByRequestList.size(),pdaByRequestList.size());
                            %>
                            <div class="info-box">
                                <%if (pdaPercentage >= 80){%>
                                <span class="info-box-icon bg-red"><i class="fa fa-hourglass-end"></i></span>
                                    <%}else if (pdaPercentage >= 50){%>
                                <span class="info-box-icon bg-yellow"><i class="fa fa-hourglass-end"></i></span>
                                    <%}else{%>
                                <span class="info-box-icon bg-green"><i class="fa fa-hourglass-end"></i></span>
                                    <%}%>
                                <div class="info-box-content">
                                    <span class="info-box-text">Past Due Accounts</span>
                                    <span class="info-box-number"><%out.print(unsettledPDAByRequestList.size());%></span>
                                    <span class="info-box-text"><small><%out.print(df.format(pdaPercentage));%>%</small></span>
                                </div>
                                <!-- /.info-box-content -->
                            </div>

                            <!-- /.info-box -->
                        </div>
                        <!-- /.col -->

                        <!-- fix for small devices only -->
                        <div class="clearfix visible-sm-block"></div>

                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <%
                                double apcpBudgetPercentage = apcpRequestDAO.getPercentage(apcpRequestDAO.getYearlySumOfReleasesByRequest(releasedRequests,year),sumAPCPBudget);
                            %>
                            <div class="info-box">
                                <%if (apcpBudgetPercentage >= 80){%>
                                <span class="info-box-icon bg-red"><i class="fa fa-money"></i></span>
                                    <%}else if(apcpBudgetPercentage >= 50){%>
                                <span class="info-box-icon bg-yellow"><i class="fa fa-money"></i></span>
                                    <%}else{%>
                                <span class="info-box-icon bg-green"><i class="fa fa-money"></i></span>
                                    <%}%>
                                <div class="info-box-content">
                                    <span class="info-box-text">APCP BUDGET</span>
                                    <span class="info-box-number"><%out.print(currency.format(currentAPCPBudget));%></span>
                                    <span class="info-box-text"><small><%out.print(df.format(apcpBudgetPercentage));%>%</small></span>
                                </div>
                            </div>
                            <!-- /.info-box -->
                        </div>
                        <!-- /.col -->
                        <!-- /.col -->
                    </div>
                    
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong><a href="view-issues.jsp">Pending Requests</a></strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table class="table table-bordered table-striped export">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Requested</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%
                                                for(APCPRequest r : pendingRequests){
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="ViewAPCP?id=<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <%if(r.getLoanReason().getLoanReason() == 0){%> <!--OTHERS-->
                                                <td><%out.print(r.getLoanReason().getLoanReasonDesc() + ": " + r.getLoanReason().getOtherReason());%></td>
                                                <%}else{%> <!--LOAN REASON-->
                                                <td><%out.print(r.getLoanReason().getLoanReasonDesc());%></td>
                                                <%}%>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(r.getDateRequested());%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><span class="label label-info"><%out.print(r.getRequestStatusDesc());%></span></td>
                                            </tr>
                                            <%}%>
                                        </tbody>

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
                                                <span class="progress-number"><%out.print(currency.format(apcpRequestDAO.getYearlyBudgetAllocated(provincialRequests, year)));%>/<strong><%out.print(currency.format(sumAPCPBudget));%></strong></span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-yellow" style="width: <%out.print(apcpRequestDAO.getPercentage(apcpRequestDAO.getYearlyBudgetAllocated(provincialRequests, year),sumAPCPBudget) + "%");%>"></div>
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
                                                <button class="btn btn-success" id="APCPOnTrackDelayed<%out.print(req.getRequestStatus());%>" data-toggle="modal" data-target="#onTrackAPCP<%out.print(req.getRequestStatus());%>"><%out.print(apcpRequestDAO.getOnTrackRequestsPerStatus(provincialRequests,req.getRequestStatus()));%> </button>
                                                <button class="btn btn-danger" id="APCPOnTrackDelayed<%out.print(req.getRequestStatus());%>" data-toggle="modal" data-target="#delayedAPCP<%out.print(req.getRequestStatus());%>"><strong><%out.print(apcpRequestDAO.getDelayedRequestsPerStatus(provincialRequests,req.getRequestStatus()));%></strong></button>
                                            </div>
                                            &nbsp;

                                            <%ArrayList<APCPRequest> requests = new ArrayList();%>
                                            <%
                                                if(req.getRequestStatus() == 0){
                                                    requests = newAccessingRequests;
                                                }else if(req.getRequestStatus() == 1){
                                                    requests = requestedRequests;
                                                }else if(req.getRequestStatus() == 2){
                                                    requests = clearedRequests;
                                                }else if(req.getRequestStatus() == 3){
                                                    requests = endorsedRequests;
                                                }else if(req.getRequestStatus() == 4){
                                                    requests = approvedRequests;
                                                }
                                            %>

                                            <div class="modal fade" id="onTrackAPCP<%out.print(req.getRequestStatus());%>">
                                                <div class="modal-dialog modal-lg">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span></button>
                                                            <h4 class="modal-title">On Track <%out.print(req.getRequestStatusDesc());%> Requests</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <table class="table table-bordered table-striped export">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>ARBO</th>
                                                                                <th>Loan Reason</th>
                                                                                <th>Loan Amount</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>

                                                                            <%
                                                                                for(APCPRequest delayedRequest : requests){
                                                                            %>
                                                                            <%if(delayedRequest.checkIfRequestIsOnTrack()){%>
                                                                            <tr>
                                                                                <%ARBO arbo = arboDAO.getARBOByID(delayedRequest.getArboID());%>
                                                                                <td><a rel="noopener noreferrer" target="_blank" href="ViewARBO?id=<%out.print(delayedRequest.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                                                <%if(delayedRequest.getLoanReason().getLoanReason() == 0){%> <!--OTHERS-->
                                                                                <td><%out.print(delayedRequest.getLoanReason().getLoanReasonDesc() + ": " + delayedRequest.getLoanReason().getOtherReason());%></td>
                                                                                <%}else{%> <!--LOAN REASON-->
                                                                                <td><%out.print(delayedRequest.getLoanReason().getLoanReasonDesc());%></td>
                                                                                <%}%>
                                                                                <td><%out.print(currency.format(delayedRequest.getLoanAmount()));%></td>
                                                                            </tr>
                                                                            <%}%>
                                                                            <%}%>

                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>
                                                        </div>
                                                    </div>
                                                    <!-- /.modal-content -->
                                                </div>
                                                <!-- /.modal-dialog -->
                                            </div>

                                            <div class="modal fade" id="delayedAPCP<%out.print(req.getRequestStatus());%>">
                                                <div class="modal-dialog modal-lg">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span></button>
                                                            <h4 class="modal-title">Delayed <%out.print(req.getRequestStatusDesc());%> Requests</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <table class="table table-bordered table-striped export">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>ARBO</th>
                                                                                <th>Loan Reason</th>
                                                                                <th>Loan Amount</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>

                                                                            <%
                                                                                for(APCPRequest delayedRequest : requests){
                                                                            %>
                                                                            <%if(!delayedRequest.checkIfRequestIsOnTrack()){%>
                                                                            <tr>
                                                                                <%ARBO arbo = arboDAO.getARBOByID(delayedRequest.getArboID());%>
                                                                                <td><a rel="noopener noreferrer" target="_blank" href="ViewARBO?id=<%out.print(delayedRequest.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                                                <%if(delayedRequest.getLoanReason().getLoanReason() == 0){%> <!--OTHERS-->
                                                                                <td><%out.print(delayedRequest.getLoanReason().getLoanReasonDesc() + ": " + delayedRequest.getLoanReason().getOtherReason());%></td>
                                                                                <%}else{%> <!--LOAN REASON-->
                                                                                <td><%out.print(delayedRequest.getLoanReason().getLoanReasonDesc());%></td>
                                                                                <%}%>
                                                                                <td><%out.print(currency.format(delayedRequest.getLoanAmount()));%></td>
                                                                            </tr>
                                                                            <%}%>
                                                                            <%}%>

                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-outline pull-left" data-dismiss="modal">Close</button>
                                                        </div>
                                                    </div>
                                                    <!-- /.modal-content -->
                                                </div>
                                                <!-- /.modal-dialog -->
                                            </div>
                                            <%}%>
                                            <%}%>
                                            <div class="row">
                                                <h6>Status: <strong class="text-green">On-track</strong> | <strong class="text-red">Delayed</strong></h6>
                                            </div>
                                        </center>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <%
                                    double APCPARBOsPercentage = apcpRequestDAO.getPercentageComparison(apcpRequestDAO.getDistinctARBOCountWithReleased(provincialRequests,lastYear),apcpRequestDAO.getDistinctARBOCountWithReleased(provincialRequests,year));
                                    double APCPARBsPercentage = apcpRequestDAO.getPercentageComparison(apcpRequestDAO.getDistinctRecipientCountWithReleased(provincialRequests,lastYear),apcpRequestDAO.getDistinctRecipientCountWithReleased(provincialRequests,year));
                                    double APCPReleasedPercentage = apcpRequestDAO.getPercentageComparison(apcpRequestDAO.getYearlySumOfReleasesByRequest(provincialRequests,lastYear),apcpRequestDAO.getYearlySumOfReleasesByRequest(provincialRequests,year));
                                    double APCPPDAPercentage = apcpRequestDAO.getPercentageComparison(apcpRequestDAO.getYearlyTotalPastDueAmount(provincialRequests, lastYear),apcpRequestDAO.getYearlyTotalPastDueAmount(provincialRequests, year));
                                    %>
                                    <div class="row">
                                        <div class="col-sm-3 col-xs-6" id="totalARBOsAPCP">
                                            <div class="description-block border-right">
                                                
                                                <%if(APCPARBOsPercentage > 0){%>
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> <%out.print(df.format(APCPARBOsPercentage));%>%</span>
                                                <%}else if(APCPARBOsPercentage < 0){%>
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> <%out.print(df.format(APCPARBOsPercentage));%>%</span>
                                                <%}else{%>
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> <%out.print(df.format(APCPARBOsPercentage));%>%</span>
                                                <%}%>
                                                
                                                <h5 class="description-header"><%out.print(apcpRequestDAO.getDistinctARBOCountWithReleased(provincialRequests,year));%></h5>
                                                <span class="description-text">TOTAL ARBOs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalARBsAPCP">
                                            <div class="description-block border-right">
                                                
                                                <%if(APCPARBsPercentage > 0){%>
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> <%out.print(df.format(APCPARBsPercentage));%>%</span>
                                                <%}else if(APCPARBsPercentage < 0){%>
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> <%out.print(df.format(APCPARBsPercentage));%>%</span>
                                                <%}else{%>
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> <%out.print(df.format(APCPARBsPercentage));%>%</span>
                                                <%}%>
                                                
                                                <h5 class="description-header"><%out.print(apcpRequestDAO.getDistinctRecipientCountWithReleased(provincialRequests,year));%></h5>
                                                <span class="description-text">TOTAL ARBs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalReleasedAmountAPCP">
                                            <div class="description-block border-right">
                                                
                                                <%if(APCPReleasedPercentage > 0){%>
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> <%out.print(df.format(APCPReleasedPercentage));%>%</span>
                                                <%}else if(APCPReleasedPercentage < 0){%>
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> <%out.print(df.format(APCPReleasedPercentage));%>%</span>
                                                <%}else{%>
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> <%out.print(df.format(APCPReleasedPercentage));%>%</span>
                                                <%}%>
                                                
                                                <h5 class="description-header"><%out.print(currency.format(apcpRequestDAO.getYearlySumOfReleasesByRequest(provincialRequests,year)));%></h5>
                                                <span class="description-text">TOTAL RELEASED AMOUNT (<%out.print(year);%>)</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalPastDueAmountAPCP">
                                            <div class="description-block">
                                                
                                                <%if(APCPPDAPercentage > 0){%>
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> <%out.print(df.format(APCPPDAPercentage));%>%</span>
                                                <%}else if(APCPPDAPercentage < 0){%>
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> <%out.print(df.format(APCPPDAPercentage));%>%</span>
                                                <%}else{%>
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> <%out.print(df.format(APCPPDAPercentage));%>%</span>
                                                <%}%>
                                                
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
                                                <h5 class="description-header"><%out.print(currency.format(apcpRequestDAO.getSumOfAccumulatedReleasesByRequest(provincialRequests)));%></h5>
                                                <span class="description-text">CUMULATIVE RELEASED AMOUNT</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-4 col-xs-6" id="cumulativePastDueAmountAPCP">
                                            <div class="description-block border-right">
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
                    

                </section>
                <!-- /.content -->
            </div>


        </div>



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

    </body>
</html>