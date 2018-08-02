<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>

    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>

            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>

                        <strong><i class="fa fa-dashboard"></i> Dashboard</strong> 
                        <small><%out.print((String) session.getAttribute("regOfficeDesc"));%></small>

                    </h1>

                </section>

                <!-- Main content -->
                <section class="content">

                    <%
                    if(request.getAttribute("filtered") != null){
                        arboListProvince = (ArrayList<ARBO>)request.getAttribute("filtered");
                    }
                    
                    if(request.getAttribute("provOffices") != null){
                        provOffices2 = (ArrayList<Province>)request.getAttribute("provOffices");
                    }
                    
                    if(request.getAttribute("filteredRequests") != null){
                        provincialRequests = (ArrayList<APCPRequest>)request.getAttribute("filteredRequests");
                        newAccessingRequests = apcpRequestDAO.getFilteredByStatus(provincialRequests,0);
                        requestedRequests = apcpRequestDAO.getFilteredByStatus(provincialRequests,1);
                        clearedRequests = apcpRequestDAO.getFilteredByStatus(provincialRequests,2);
                        endorsedRequests = apcpRequestDAO.getFilteredByStatus(provincialRequests,3);
                        approvedRequests = apcpRequestDAO.getFilteredByStatus(provincialRequests,4);
                        releasedRequests = apcpRequestDAO.getFilteredByStatus(provincialRequests,5);
                        forReleaseRequests = apcpRequestDAO.getFilteredByStatus(provincialRequests,6);
                        pdaByRequestList = apcpRequestDAO.getYearlyPastDueAccountsByRequestList(provincialRequests,year);
                        unsettledPDAByRequestList = apcpRequestDAO.getYearlyUnsettledPastDueAccountsByRequestList(provincialRequests,year);
                        currentAPCPBudget = addressDAO.getCurrentAPCPBudgetRegion(releasedRequests,provOffices2,year);
                        sumAPCPBudget = addressDAO.getSumAPCPBudgetRegion(provOffices2);
                    }
                    
                    if(request.getAttribute("filteredPlans") != null){
                        allPlans = (ArrayList<CAPDEVPlan>)request.getAttribute("filteredPlans");
                        pendingPlans = capdevDAO.getFilteredByStatus(allPlans,1);
                        approvedPlans = capdevDAO.getFilteredByStatus(allPlans,2);
                        disapprovedPlans = capdevDAO.getFilteredByStatus(allPlans,3);
                        assignedPlans = capdevDAO.getFilteredByStatus(allPlans,4);
                        implementedPlans = capdevDAO.getFilteredByStatus(allPlans,5);
                        postponedPlans = capdevDAO.getFilteredByStatus(allPlans,6);
                        currentCAPDEVBudget = addressDAO.getCurrentCAPDEVBudgetRegion(implementedPlans,provOffices2,year);
                        sumCAPDEVBudget = addressDAO.getSumCAPDEVBudgetRegion(provOffices2);
                    }
                    %>

                    <div class="row">
                        <div class="col-xs-6">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><i class="fa fa-search"></i> Filter By</h3>
                                </div>
                                <form>
                                    <div class="box-body">
                                        
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <input type="radio" id="drillDownGender" name="filterBy" value="All" checked onclick="document.getElementById('provinces').disabled = true;">
                                                <label for="actName">Select All</label>
                                                &nbsp;&nbsp;
                                                <input type="radio" id="drillDownGender" name="filterBy" value="provinces" onclick="document.getElementById('provinces').disabled = false;">
                                                <label for="actName">Provinces</label>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <input type="hidden" name="regions[]" value="<%out.print((Integer)session.getAttribute("regOfficeCode"));%>">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="actName">Provinces</label>
                                                    <select name="provinces[]" id="provinces" class="form-control select2" multiple="multiple" disabled>
                                                        <%for(Province p : provOffices){%>
                                                        <option value="<%out.print(p.getProvCode());%>"><%out.print(p.getProvDesc());%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                                    
                                    </div>
                                                    
                                    <div class="box-footer">
                                        <button type="submit" onclick="form.action = 'FilterDashboard'" class="btn btn-success pull-right"><i class="fa fa-filter margin-r-5"></i> Filter</button>            
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>



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
                                double postponedPercentage = capdevDAO.getPercentage(postponedPlans.size(),allPlans.size());
                            %>
                            <div class="info-box">
                                <%if (postponedPercentage >= 80){%>
                                <span class="info-box-icon bg-red"><i class="fa fa-pause"></i></span>
                                    <%}else if(postponedPercentage >= 50){%>
                                <span class="info-box-icon bg-yellow"><i class="fa fa-pause"></i></span>
                                    <%}else{%>
                                <span class="info-box-icon bg-green"><i class="fa fa-pause"></i></span>
                                    <%}%>
                                <div class="info-box-content">
                                    <span class="info-box-text">Postponed Plans</span>
                                    <span class="info-box-number"><%out.print(postponedPlans.size());%></span>
                                    <span class="info-box-text"><small><%out.print(df.format(postponedPercentage));%>%</small></span>
                                </div>
                            </div>
                        </div>
                        <!-- /.col -->
                        <!-- /.col -->
                    </div>
                    <div class="row">
                        <div class="col-md-2 col-sm-6 col-xs-12">
                        </div>
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
                        <div class="col-md-4 col-sm-6 col-xs-12">
                            <%
                                double capdevBudgetPercentage = capdevDAO.getPercentage(capdevDAO.getYearlyImplementedBudget(implementedPlans,year),sumCAPDEVBudget);
                            %>
                            <div class="info-box">
                                <%if (capdevBudgetPercentage >= 80){%>
                                <span class="info-box-icon bg-red"><i class="fa fa-money"></i></span>
                                    <%}else if(capdevBudgetPercentage >= 50){%>
                                <span class="info-box-icon bg-yellow"><i class="fa fa-money"></i></span>
                                    <%}else{%>
                                <span class="info-box-icon bg-green"><i class="fa fa-money"></i></span>
                                    <%}%>
                                <div class="info-box-content">
                                    <span class="info-box-text">CAPDEV BUDGET</span>
                                    <span class="info-box-number"><%out.print(currency.format(currentCAPDEVBudget));%></span>
                                    <span class="info-box-text"><small><%out.print(df.format(capdevBudgetPercentage));%>%</small></span>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-2 col-sm-6 col-xs-12">
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
                                                                            <%if(delayedRequest.checkIfRequestIsOnTrack(delayedRequest.getDateRequested())){%>
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
                                                                            <%if(!delayedRequest.checkIfRequestIsOnTrack(delayedRequest.getDateRequested())){%>
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
                                                <span class="progress-text">Implemented Plans Budget</span>
                                                <span class="progress-number"><b><%out.print(currency.format(capdevDAO.getYearlyImplementedBudget(implementedPlans,year)));%></b>/<%out.print(currency.format(sumCAPDEVBudget));%></span>

                                                <div class="progress sm">
                                                    <div class="progress-bar progress-bar-yellow" style="width: <%out.print(capdevDAO.getPercentage(capdevDAO.getYearlyImplementedBudget(implementedPlans,year),sumCAPDEVBudget));%>%"></div>
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
                                                <button class="btn btn-success" id="CAPDEVOnTrackDelayed<%out.print(plan.getPlanStatus());%>" data-toggle="modal" data-target="#onTrackCAPDEV<%out.print(plan.getPlanStatus());%>"><%out.print(capdevDAO.getOnTrackPlansPerStatus(allPlans,plan.getPlanStatus()));%> </button>
                                                <button class="btn btn-danger" id="CAPDEVOnTrackDelayed<%out.print(plan.getPlanStatus());%>" data-toggle="modal" data-target="#delayedCAPDEV<%out.print(plan.getPlanStatus());%>"><strong><%out.print(capdevDAO.getDelayedPlansPerStatus(allPlans,plan.getPlanStatus()));%></strong>  </button>
                                            </div>
                                            &nbsp;

                                            <%ArrayList<CAPDEVPlan> plans = new ArrayList();%>
                                            <%
                                                if(plan.getPlanStatus() == 1){
                                                    plans = pendingPlans;
                                                }else if(plan.getPlanStatus() == 2){
                                                    plans = approvedPlans;
                                                }else if(plan.getPlanStatus() == 4){
                                                    plans = assignedPlans;
                                                }
                                            %>

                                            <div class="modal fade" id="onTrackCAPDEV<%out.print(plan.getPlanStatus());%>">
                                                <div class="modal-dialog modal-lg">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span></button>
                                                            <h4 class="modal-title">On Track <%out.print(plan.getPlanStatusDesc());%> Plans</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <table class="table table-bordered table-striped export">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>Plan DTN</th>
                                                                                <th>ARBO</th>
                                                                                <th>No. of Activities</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>

                                                                            <%
                                                                                for(CAPDEVPlan plan65 : plans){
                                                                            %>
                                                                            <%if(plan65.checkIfPlanIsOnTrack(plan65.getPlanDate())){%>
                                                                            <tr>
                                                                                <%
                                                                                    plan65.setActivities(capdevDAO.getCAPDEVPlanActivities(plan65.getPlanID()));
                                                                                    APCPRequest rr = apcpRequestDAO.getRequestByID(plan65.getRequestID());
                                                                                    ARBO arbo = arboDAO.getARBOByID(rr.getArboID());
                                                                                %>
                                                                                <td><%out.print(plan65.getPlanDTN());%></td>
                                                                                <td><a rel="noopener noreferrer" target="_blank" href="ViewARBO?id=<%out.print(rr.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                                                <td><%out.print(plan65.getActivities().size());%></td>
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

                                            <div class="modal fade" id="delayedCAPDEV<%out.print(plan.getPlanStatus());%>">
                                                <div class="modal-dialog modal-lg">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span></button>
                                                            <h4 class="modal-title">Delayed <%out.print(plan.getPlanStatusDesc());%> Plans</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <table class="table table-bordered table-striped export">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>Plan DTN</th>
                                                                                <th>ARBO</th>
                                                                                <th>No. of Activities</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>

                                                                            <%
                                                                                for(CAPDEVPlan plan87 : plans){
                                                                            %>
                                                                            <%if(!plan87.checkIfPlanIsOnTrack(plan87.getPlanDate())){%>
                                                                            <tr>
                                                                                <%
                                                                                    plan87.setActivities(capdevDAO.getCAPDEVPlanActivities(plan87.getPlanID()));
                                                                                    APCPRequest rr = apcpRequestDAO.getRequestByID(plan87.getRequestID());
                                                                                    ARBO arbo = arboDAO.getARBOByID(rr.getArboID());
                                                                                %>
                                                                                <td><%out.print(plan87.getPlanDTN());%></td>
                                                                                <td><a rel="noopener noreferrer" target="_blank" href="ViewARBO?id=<%out.print(rr.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                                                <td><%out.print(plan87.getActivities().size());%></td>
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
                                <!-- ./box-body -->
                                <div class="box-footer">
                                    <%
                                    double CAPDEVARBOsPercentage = capdevDAO.getPercentageComparison(capdevDAO.getDistinctARBOCountWithImplemented(implementedPlans,lastYear),capdevDAO.getDistinctARBOCountWithImplemented(implementedPlans,year));
                                    double CAPDEVARBsPercentage = capdevDAO.getPercentageComparison(capdevDAO.getDistinctParticipantCountWithImplemented(implementedPlans, lastYear),capdevDAO.getDistinctParticipantCountWithImplemented(implementedPlans, year));
                                    double CAPDEVImplementedPercentage = capdevDAO.getPercentageComparison(capdevDAO.getYearlyImplementedCount(implementedPlans, lastYear),capdevDAO.getYearlyImplementedCount(implementedPlans, year));
                                    double CAPDEVPostponedPercentage = capdevDAO.getPercentageComparison(capdevDAO.getYearlyPostponedCount(allPlans, lastYear),capdevDAO.getYearlyPostponedCount(allPlans, year));
                                    double CAPDEVImplementedBudgetPercentage = capdevDAO.getPercentageComparison(capdevDAO.getYearlyImplementedBudget(implementedPlans, lastYear),capdevDAO.getYearlyImplementedBudget(implementedPlans, year));
                                    double CAPDEVPostponedBudgetPercentage = capdevDAO.getPercentageComparison(capdevDAO.getYearlyPostponedBudget(allPlans,lastYear),capdevDAO.getYearlyPostponedBudget(allPlans,year));
                                    %>
                                    <div class="row">
                                        <div class="col-sm-3 col-xs-6" id="totalARBOsCAPDEV">
                                            <div class="description-block border-right">

                                                <%if(CAPDEVARBOsPercentage > 0){%>
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> <%out.print(df.format(CAPDEVARBOsPercentage));%>%</span>
                                                <%}else if(CAPDEVARBOsPercentage < 0){%>
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> <%out.print(df.format(CAPDEVARBOsPercentage));%>%</span>
                                                <%}else{%>
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> <%out.print(df.format(CAPDEVARBOsPercentage));%>%</span>
                                                <%}%>

                                                <h5 class="description-header"><%out.print(capdevDAO.getDistinctARBOCountWithImplemented(implementedPlans,year));%></h5>
                                                <span class="description-text">TOTAL ARBOs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalARBsCAPDEV">
                                            <div class="description-block border-right">

                                                <%if(CAPDEVARBsPercentage > 0){%>
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> <%out.print(df.format(CAPDEVARBsPercentage));%>%</span>
                                                <%}else if(CAPDEVARBsPercentage < 0){%>
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> <%out.print(df.format(CAPDEVARBsPercentage));%>%</span>
                                                <%}else{%>
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> <%out.print(df.format(CAPDEVARBsPercentage));%>%</span>
                                                <%}%>

                                                <h5 class="description-header"><%out.print(capdevDAO.getDistinctParticipantCountWithImplemented(implementedPlans, year));%></h5>
                                                <span class="description-text">TOTAL ARBs</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6" id="totalImplementedPlans">
                                            <div class="description-block border-right">

                                                <%if(CAPDEVImplementedPercentage > 0){%>
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> <%out.print(df.format(CAPDEVImplementedPercentage));%>%</span>
                                                <%}else if(CAPDEVImplementedPercentage < 0){%>
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> <%out.print(df.format(CAPDEVImplementedPercentage));%>%</span>
                                                <%}else{%>
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> <%out.print(df.format(CAPDEVImplementedPercentage));%>%</span>
                                                <%}%>

                                                <h5 class="description-header"><%out.print(capdevDAO.getYearlyImplementedCount(implementedPlans, year));%></h5>
                                                <span class="description-text">TOTAL PLANS IMPLEMENTED</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6">
                                            <div class="description-block">

                                                <%if(CAPDEVPostponedPercentage > 0){%>
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> <%out.print(df.format(CAPDEVPostponedPercentage));%>%</span>
                                                <%}else if(CAPDEVPostponedPercentage < 0){%>
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> <%out.print(df.format(CAPDEVPostponedPercentage));%>%</span>
                                                <%}else{%>
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> <%out.print(df.format(CAPDEVPostponedPercentage));%>%</span>
                                                <%}%>

                                                <h5 class="description-header"><%out.print(capdevDAO.getYearlyPostponedCount(allPlans, year));%></h5>
                                                <span class="description-text">TOTAL POSTPONED PLANS</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-3" id="totalImplementedBudget">
                                            <div class="description-block border-right">

                                                <%if(CAPDEVImplementedBudgetPercentage > 0){%>
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> <%out.print(df.format(CAPDEVImplementedBudgetPercentage));%>%</span>
                                                <%}else if(CAPDEVImplementedBudgetPercentage < 0){%>
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> <%out.print(df.format(CAPDEVImplementedBudgetPercentage));%>%</span>
                                                <%}else{%>
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> <%out.print(df.format(CAPDEVImplementedBudgetPercentage));%>%</span>
                                                <%}%>

                                                <h5 class="description-header"><%out.print(currency.format(capdevDAO.getYearlyImplementedBudget(implementedPlans, year)));%></h5>
                                                <span class="description-text">IMPLEMENTED ACTIVITY BUDGET (<%out.print(year);%>)</span>
                                            </div>
                                        </div>
                                        <div class="col-sm-3 col-xs-6">
                                            <div class="description-block border-right">

                                                <%if(CAPDEVPostponedBudgetPercentage > 0){%>
                                                <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> <%out.print(df.format(CAPDEVPostponedBudgetPercentage));%>%</span>
                                                <%}else if(CAPDEVPostponedBudgetPercentage < 0){%>
                                                <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> <%out.print(df.format(CAPDEVPostponedBudgetPercentage));%>%</span>
                                                <%}else{%>
                                                <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> <%out.print(df.format(CAPDEVPostponedBudgetPercentage));%>%</span>
                                                <%}%>

                                                <h5 class="description-header"><%out.print(currency.format(capdevDAO.getYearlyPostponedBudget(allPlans,year)));%></h5>
                                                <span class="description-text">POSTPONED ACTIVITY BUDGET (<%=year%>)</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3 col-xs-6">
                                            <div class="description-block border-right">
                                                <h5 class="description-header"><%out.print(currency.format(capdevDAO.getCumulativeBudgetSpent(allPlans)));%></h5>
                                                <span class="description-text">CUMULATIVE IMPLEMENTED BUDGET</span>
                                            </div>
                                            <!-- /.description-block -->
                                        </div>
                                        <!-- /.col -->
                                        <div class="col-sm-3">
                                            <div class="description-block border-right">
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



        <%@include file="jspf/footer.jspf" %>
        <script type="text/javascript">



            $(document).ready(function () {

                var ctxAPCP = document.getElementById('apcpChart');
                var APCPChart;
            <%
               Chart chart = new Chart();
               String json = "";
               json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince,0,2, "FOR CONDUIT APPROVAL"); // inital chart
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
                        json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince, 0, 2, "FOR CONDUIT APPROVAL");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#APCPOnTrackDelayed1').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince, 1, 2, "REQUESTED");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#APCPOnTrackDelayed2').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince, 2, 2, "CLEARED");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#APCPOnTrackDelayed3').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince, 3, 2, "ENDORSED");
            %>
                    APCPChart = new Chart(ctxAPCP, <%out.print(json);%>);
                });

                $('#APCPOnTrackDelayed4').on('click', function () {
                    APCPChart.destroy();
            <%
                        json = chart.getStackedBarChartAPCPOnTrackDelayedByStatus(arboListProvince, 4, 2, "APPROVED");
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
               json2 = chart2.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince,1,2, "PENDING"); // inital chart
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




                $('#CAPDEVOnTrackDelayed1').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince, 1, 2, "PENDING");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#CAPDEVOnTrackDelayed2').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince, 2, 2, "APPROVED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#CAPDEVOnTrackDelayed4').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince, 4, 2, "ASSIGNED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });
            });

        </script>

    </body>
</html>