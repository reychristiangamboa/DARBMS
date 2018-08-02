<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>

    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/pfo-capdev-sidebar.jspf"%>

            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>

                        <strong><i class="fa fa-dashboard"></i> Dashboard</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>

                    </h1>

                </section>
                        
                <section class="content">
                    
                    <%if(issueDAO.retrieveUnresolvedIssues((Integer)session.getAttribute("userType"),(Integer)session.getAttribute("provOfficeCode")).size() > 0){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> You have <a href="view-issues.jsp">ISSUES</a> pending. Please resolve them immediately.</h4>
                    </div>
                    
                    <div class="row">
                        <div class="col-md-2 col-sm-6 col-xs-12">
                        </div>
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
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Postponed Plans</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table class="table table-bordered table-striped export">
                                        <thead>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>ARBO Name</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>    
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(CAPDEVPlan p : postponedPlans){
                                                    APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a data-toggle="modal" data-target="#rescheduleModal<%out.print(p.getPlanID());%>"><%out.print(p.getPlanDTN());%></a></td>
                                                <td><a target="_blank" rel="noopener noreferrer" href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><span class="label label-success"><%out.print(p.getPlanStatusDesc());%></span></td>
                                            </tr>
                                        <div class="modal fade" id="rescheduleModal<%out.print(p.getPlanID());%>">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">×</span>
                                                        </button>
                                                        <h4 class="modal-title">RESCHEDULE</h4>
                                                    </div>
                                                    <form method="post">
                                                        <div class="modal-body">

                                                            <div class="row">
                                                                <div class="col-xs-4">
                                                                    <div class="form-group">
                                                                        <label for="">Budget Spent (Optional)</label>
                                                                        <div class="input-group">
                                                                            <div class="input-group-addon">
                                                                                <i>&#8369;</i>
                                                                            </div>
                                                                            <input name="budgetSpent" class="form-control numberOnly" value="0">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="row">
                                                                <div class="col-xs-4">
                                                                    <div class="form-group">
                                                                        <label for="">Plan Date</label>
                                                                        <input type="date" class="form-control" name="planDate" required>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-4">
                                                                    <div class="form-group">
                                                                        <label for="">Budget for Rescheduled Plan</label>
                                                                        <div class="input-group">
                                                                            <div class="input-group-addon">
                                                                                <i>&#8369;</i>
                                                                            </div>
                                                                            <input name="budgetRescheduled" class="form-control numberOnly" required>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="row">
                                                                <div class="col-xs-4">
                                                                    <div class="form-group">
                                                                        <label for="">Assign Point Person</label>
                                                                        <select name="pointPersonID" class="form-control select2" required>
                                                                            <%for(User u : pointPersons){%>
                                                                            <option value="<%=u.getUserID()%>"><%out.print(u.getFullName());%></option>
                                                                            <%}%>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <a href="ReschedulePlan?planID=<%out.print(p.getPlanID());%>" class="btn btn-danger">Reschedule</a>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                        <%}%>

                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>ARBO Name</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>           
                                            </tr>

                                        </tfoot>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>  
                        </div>
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
                        json2 = chart2.getLineChartCAPDEVTotalARBOs(implementedPlans, "ARBOs SERVED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#totalARBsCAPDEV').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart2.getLineChartCAPDEVTotalARBs(implementedPlans, "ARBs SERVED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#totalImplementedPlans').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart2.getLineChartCAPDEVTotalImplementedPlans(implementedPlans, "TOTAL PLANS IMPLEMENTED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#totalImplementedBudget').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart2.getLineChartCAPDEVTotalImplementedBudget(implementedPlans, "IMPLEMENTED ACTIVITY BUDGET");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });




                $('#CAPDEVOnTrackDelayed1').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart2.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince, 1, 3, "PENDING");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#CAPDEVOnTrackDelayed2').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart2.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince, 2, 3, "APPROVED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });

                $('#CAPDEVOnTrackDelayed4').on('click', function () {
                    CAPDEVChart.destroy();
            <%
                        json2 = chart2.getStackedBarChartCAPDEVOnTrackDelayedByStatus(arboListProvince, 4, 3, "ASSIGNED");
            %>
                    CAPDEVChart = new Chart(ctxCAPDEV, <%out.print(json2);%>);
                });
            });

        </script>

    </body>
</html>