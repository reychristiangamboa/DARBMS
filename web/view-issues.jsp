<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf" %>
        <style>
            li .disabled{
                pointer-events: none;
                opacity: 0.6;
            }
        </style>

    </head>
    <body class="hold-transition skin-green sidebar-mini">
        <div class="wrapper">

            <%if ((Integer) session.getAttribute("userType") == 1) {%>
            <%@include file="jspf/admin-navbar.jspf" %>
            <%@include file="jspf/admin-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 2) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pp-apcp-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/regional-field-officer-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/central-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 6) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pfo-apcp-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 7) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pfo-capdev-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 8) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pp-capdev-sidebar.jspf" %>
            <%}%>
            <%
                APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
                CAPDEVDAO capdevDAO = new CAPDEVDAO();
                UserDAO uDAO = new UserDAO();
                IssueDAO iDAO = new IssueDAO();
                ARBODAO arboDAO = new ARBODAO();
                ARBDAO arbDAO = new ARBDAO();
                
                ArrayList<Issue> iList = new ArrayList();
                ArrayList<Issue> iListResolved = new ArrayList();
                iList = iDAO.retrieveUnresolvedIssues((Integer)session.getAttribute("userType"), (Integer)session.getAttribute("provOfficeCode"));
                iListResolved = iDAO.retrieveResolvedIssues((Integer)session.getAttribute("userType"), (Integer)session.getAttribute("provOfficeCode"));
                
            %>

            <div class="content-wrapper">

                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-warning"></i> Issues</strong> 
                    </h1>
                </section>

                <section class="content">

                    <%if(request.getAttribute("success") != null){%>
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-check"></i> <%out.print((String)request.getAttribute("success"));%></h4>
                    </div>
                    <%}else if(request.getAttribute("errMessage") != null){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String)request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>

                    <div class="row">
                        <div class="col-xs-12">  
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Unresolved Issues</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <table class="table table-bordered table-striped modTable">
                                                <thead>
                                                    <tr>
                                                        <th>Issue Type</th>
                                                        <th>Source</th>
                                                        <th>Issued By</th>
                                                        <th>Date Recorded</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>

                                                <tbody>
                                                    <%
                                                        // ALL ISSUES
                                                        for (Issue i : iList) {
                                                            User u = uDAO.searchUser(i.getIssuedBy());
                                                    %>
                                                    <tr>
                                                        <td><%out.print(i.getIssueTypeDesc());%></td>
                                                        <%if(i.getIssueType() == 1){ //CROP MISMATCH %>
                                                        <td><a data-toggle="modal" data-target="#cropMismatch<%out.print(i.getId());%>">Request ID <%out.print(i.getRequestID());%></a></td>
                                                        <%}else if(i.getIssueType() == 2){ //MANDATORY TRAINING ABSENCE %>
                                                        <td><a data-toggle="modal" data-target="#mandatoryAbsence<%out.print(i.getId());%>">Activity ID <%out.print(i.getPlanID());%></a></td>
                                                        <%}else if(i.getIssueType() == 3){ //SINGLE ARB DISBURSEMENT %>
                                                        <td><a data-toggle="modal" data-target="#singleARBDisbursement<%out.print(i.getId());%>">Disbursement ID <%out.print(i.getRequestID());%></a></td>
                                                        <%}else if(i.getIssueType() == 4){ //PAST DUE ACCOUNT %>
                                                        <td><a href="CreateCAPDEVProposal?pastDueID=<%out.print(i.getPastDueAccountID());%>&id=<%out.print(i.getRequestID());%>">Past Due Account ID <%out.print(i.getPastDueAccountID());%></a></td>
                                                        <%}%>
                                                        <td><%out.print(u.getFullName());%></td>
                                                        <td><%out.print(i.getDateRecorded());%></td>

                                                        <%if(i.getIssueType() == 1){ //CROP MISMATCH %>
                                                        <td><button class="btn btn-success" data-toggle="modal" data-target="#cropMismatchResolve<%out.print(i.getId());%>">Resolve</button></td>
                                                        <%}else if(i.getIssueType() == 2){ //MANDATORY TRAINING ABSENCE %>
                                                        <td><button class="btn btn-success" data-toggle="modal" data-target="#mandatoryAbsenceResolve<%out.print(i.getId());%>">Resolve</button></td>
                                                        <%}else if(i.getIssueType() == 3){ //SINGLE ARB DISBURSEMENT %>
                                                        <td><button class="btn btn-success" data-toggle="modal" data-target="#singleARBDisbursementResolve<%out.print(i.getId());%>">Resolve</button></td>
                                                        <%}else if(i.getIssueType() == 4){ //PAST DUE ACCOUNT %>
                                                        <td><button class="btn btn-success" data-toggle="modal" data-target="#pastDueAccountResolve<%out.print(i.getId());%>">Resolve</button></td>
                                                        <%}%>

                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <%
                            // MODALS
                            for(Issue i : iList){
                            %>

                            <%
                            if(i.getIssueType() == 1){ // CROP MISMATCH
                                APCPRequest apcp = apcpRequestDAO.getRequestByID(i.getRequestID());
                            %>
                            <div class="modal fade" id="cropMismatch<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">ARBs: CROP MISMATCH</h4>
                                        </div>
                                        <form method="post">
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <table class="table table-striped table-bordered modTable">
                                                            <thead>
                                                                <tr>
                                                                    <th>Action</th>
                                                                    <th>ARB</th>
                                                                    <th>Crops</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%for(ARB arb : apcp.getMismatchRecipients()){%>
                                                                <tr>
                                                                    <td><input type="checkbox" name="arbID" value="<%out.print(arb.getArbID());%>"></td>
                                                                    <td><a target="_blank" rel="noopener noreferrer" href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFLName());%></a></td>
                                                                    <td><%out.print(arb.printAllCrops());%></td>
                                                                </tr>
                                                                <%}%>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" onclick="form.action = 'RemoveRecipients?requestID=<%out.print(apcp.getRequestID());%>'" class="btn btn-danger pull-right">Remove</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="cropMismatchResolve<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">ARBs: CROP MISMATCH</h4>
                                        </div>
                                        <form method="post">
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <div class="form-group">
                                                            <label for="">Findings</label>
                                                            <textarea name="findings" id="" cols="3" rows="3" class="form-control" required></textarea>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <div class="form-group">
                                                            <label for="">Resolution</label>
                                                            <textarea name="resolution" id="" cols="3" rows="3" class="form-control" required></textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" onclick="form.action = 'ResolveIssue?issueID=<%out.print(i.getId());%>&requestID=<%out.print(apcp.getRequestID());%>&type=1'" class="btn btn-success pull-right">Resolve</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <%
                                }else if(i.getIssueType() == 2){ // MANDATORY TRAINING ABSENCE
                                // A MANDATORY ACTIVITY    
                                CAPDEVActivity activity = capdevDAO.getCAPDEVPlanActivityById(i.getPlanID()); // getPlanID() HERE IS ACTIVITY ID!!!
                            %>
                            <div class="modal fade" id="mandatoryAbsence<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">MANDATORY TRAINING ABSENCE: <%out.print(activity.getActivityName());%></h4>
                                        </div>
                                        <form method="post">
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <table class="table table-striped table-bordered modTable">
                                                            <thead>
                                                                <tr>
                                                                    <th>Action</th>
                                                                    <th>ARB</th>
                                                                    <th>Mandatory Training</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                    for(ARB arb : activity.getArbList()){
                                                                        if(arb.getIsPresent() == 0){ // ABSENTEES
                                                                %>
                                                                <tr>
                                                                    <td><input type="checkbox" name="attendee" value="<%out.print(arb.getArbID());%>"></td>
                                                                    <td><a target="_blank" rel="noopener noreferrer" href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFLName());%></a></td>
                                                                    <td><%out.print(activity.getActivityName());%></td>
                                                                </tr>
                                                                <%}%>
                                                                <%}%>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" onclick="form.action = 'ReviseAttendance?activityID=<%out.print(activity.getActivityID());%>'" class="btn btn-danger pull-right">Check Attendance</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="mandatoryAbsenceResolve<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">ARBs: MANDATORY TRAINING ABSENCE</h4>
                                        </div>
                                        <form method="post">
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <div class="form-group">
                                                            <label for="">Findings</label>
                                                            <textarea name="findings" id="" cols="3" rows="3" class="form-control" required></textarea>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <div class="form-group">
                                                            <label for="">Resolution</label>
                                                            <textarea name="resolution" id="" cols="3" rows="3" class="form-control" required></textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" onclick="form.action = 'ResolveIssue?issueID=<%out.print(i.getId());%>&type=2&activityID=<%out.print(activity.getActivityID());%>'" class="btn btn-success pull-right">Resolve</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <%
                                }else if(i.getIssueType() == 3){ // SINGLE ARB DISBURSEMENT
                                   Disbursement d = apcpRequestDAO.getDisbursementsByID(i.getRequestID()); // .getRequestID() here is a disbursementID!!!
                            %>
                            <div class="modal fade" id="singleARBDisbursement<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">SINGLE ARB DISBURSEMENT</h4>
                                        </div>
                                        <form method="post">
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <table class="table table-striped table-bordered modTable">
                                                            <thead>
                                                                <tr>

                                                                    <th>ARB</th>
                                                                    <th>Disbursement Amount</th>
                                                                    <th>Total Released Amount</th>
                                                                </tr>
                                                            </thead>
                                                            <tbody>
                                                                <%
                                                                    ARB arb = arbDAO.getARBByID(d.getArbID());
                                                                %>
                                                                <tr>
                                                                    <td><a target="_blank" rel="noopener noreferrer" href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFLName());%></a></td>
                                                                    <td><%out.print(currency.format(d.getDisbursedAmount()));%></td>
                                                                    <td><%out.print(currency.format(d.getTotalReleasedAmount()));%></td>
                                                                </tr>

                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <%--<button type="submit" onclick="form.action = 'ReviseAttendance?activityID=<%out.print(activity.getActivityID());%>'" class="btn btn-danger pull-right">Check Attendance</button>--%>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="singleARBDisbursementResolve<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">SINGLE ARB DISBURSEMENT</h4>
                                        </div>
                                        <form method="post">
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <div class="form-group">
                                                            <label for="">Findings</label>
                                                            <textarea name="findings" id="" cols="3" rows="3" class="form-control" required></textarea>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <div class="form-group">
                                                            <label for="">Resolution</label>
                                                            <textarea name="resolution" id="" cols="3" rows="3" class="form-control" required></textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" onclick="form.action = 'ResolveIssue?issueID=<%out.print(i.getId());%>&type=3&disbursementID=<%out.print(d.getDisbursementID());%>'" class="btn btn-success pull-right">Resolve</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <%
                                }else if(i.getIssueType() == 4){ // PAST DUE ACCOUNT
                            %>
                            <div class="modal fade" id="pastDueAccountResolve<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">PAST DUE ACCOUNT</h4>
                                        </div>
                                        <form method="post">
                                            <div class="modal-body">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <div class="form-group">
                                                            <label for="">Findings</label>
                                                            <textarea name="findings" id="" cols="3" rows="3" class="form-control" required></textarea>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <div class="form-group">
                                                            <label for="">Resolution</label>
                                                            <textarea name="resolution" id="" cols="3" rows="3" class="form-control" required></textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="submit" onclick="form.action = 'ResolveIssue?issueID=<%out.print(i.getId());%>&type=4&pastDueAccountID=<%out.print(i.getPastDueAccountID());%>'" class="btn btn-success pull-right">Resolve</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <%}%>

                            <%}%>

                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">  
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Resolved Issues</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <table class="table table-bordered table-striped modTable">
                                                <thead>
                                                    <tr>
                                                        <th>Issue Type</th>
                                                        <th>Source</th>
                                                        <th>Issued By</th>
                                                        <th>Date Recorded</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>

                                                <tbody>
                                                    <%
                                                        // ALL ISSUES
                                                        for (Issue i : iListResolved) {
                                                            User u = uDAO.searchUser(i.getIssuedBy());
                                                    %>
                                                    <tr>
                                                        <td><%out.print(i.getIssueTypeDesc());%></td>
                                                        <%if(i.getIssueType() == 1){ //CROP MISMATCH %>
                                                        <td>Request ID <%out.print(i.getRequestID());%></td>
                                                        <%}else if(i.getIssueType() == 2){ //MANDATORY TRAINING ABSENCE %>
                                                        <td>Activity ID <%out.print(i.getPlanID());%></td>
                                                        <%}else if(i.getIssueType() == 3){ //SINGLE ARB DISBURSEMENT %>
                                                        <td>Disbursement ID <%out.print(i.getRequestID());%></td>
                                                        <%}else if(i.getIssueType() == 4){ //PAST DUE ACCOUNT %>
                                                        <td>Past Due Account ID <%out.print(i.getPastDueAccountID());%></td>
                                                        <%}%>
                                                        <td><%out.print(u.getFullName());%></td>
                                                        <td><%out.print(i.getDateRecorded());%></td>

                                                        <%if(i.getIssueType() == 1){ //CROP MISMATCH %>
                                                        <td><button class="btn btn-success" data-toggle="modal" data-target="#cropMismatchDetails<%out.print(i.getId());%>">View</button></td>
                                                        <%}else if(i.getIssueType() == 2){ //MANDATORY TRAINING ABSENCE %>
                                                        <td><button class="btn btn-success" data-toggle="modal" data-target="#mandatoryAbsenceDetails<%out.print(i.getId());%>">View</button></td>
                                                        <%}else if(i.getIssueType() == 3){ //SINGLE ARB DISBURSEMENT %>
                                                        <td><button class="btn btn-success" data-toggle="modal" data-target="#singleARBDisbursementDetails<%out.print(i.getId());%>">View</button></td>
                                                        <%}else if(i.getIssueType() == 4){ //PAST DUE ACCOUNT %>
                                                        <td><button class="btn btn-success" data-toggle="modal" data-target="#pastDueAccountDetails<%out.print(i.getId());%>">View</button></td>
                                                        <%}%>

                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>

                                        </div>
                                    </div>
                                </div>
                            </div>

                            <%
                            // MODALS
                            for(Issue i : iListResolved){
                            %>

                            <%
                            if(i.getIssueType() == 1){ // CROP MISMATCH
                                APCPRequest apcp = apcpRequestDAO.getRequestByID(i.getRequestID());
                            %>
                            <div class="modal fade" id="cropMismatchDetails<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">ARBs: CROP MISMATCH</h4>
                                        </div>

                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <div class="form-group">
                                                        <label for="">Findings</label>
                                                        <textarea name="findings" id="" cols="3" rows="3" class="form-control" disabled><%out.print(i.getFindings());%></textarea>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <div class="form-group">
                                                        <label for="">Resolution</label>
                                                        <textarea name="resolution" id="" cols="3" rows="3" class="form-control" disabled><%out.print(i.getResolution());%></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <%
                                }else if(i.getIssueType() == 2){ // MANDATORY TRAINING ABSENCE
                                // A MANDATORY ACTIVITY    
                                CAPDEVActivity activity = capdevDAO.getCAPDEVPlanActivityById(i.getPlanID()); // getPlanID() HERE IS ACTIVITY ID!!!
                            %>

                            <div class="modal fade" id="mandatoryAbsenceDetails<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">ARBs: MANDATORY TRAINING ABSENCE</h4>
                                        </div>

                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <div class="form-group">
                                                        <label for="">Findings</label>
                                                        <textarea name="findings" id="" cols="3" rows="3" class="form-control" disabled><%out.print(i.getFindings());%></textarea>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <div class="form-group">
                                                        <label for="">Resolution</label>
                                                        <textarea name="resolution" id="" cols="3" rows="3" class="form-control" disabled><%out.print(i.getResolution());%></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <%
                                }else if(i.getIssueType() == 3){ // SINGLE ARB DISBURSEMENT
                                   Disbursement d = apcpRequestDAO.getDisbursementsByID(i.getRequestID()); // .getRequestID() here is a disbursementID!!!
                            %>

                            <div class="modal fade" id="singleARBDisbursementDetails<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">SINGLE ARB DISBURSEMENT</h4>
                                        </div>

                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <div class="form-group">
                                                        <label for="">Findings</label>
                                                        <textarea name="findings" id="" cols="3" rows="3" class="form-control" disabled><%out.print(i.getFindings());%></textarea>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <div class="form-group">
                                                        <label for="">Resolution</label>
                                                        <textarea name="resolution" id="" cols="3" rows="3" class="form-control" disabled><%out.print(i.getResolution());%></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <%
                                }else if(i.getIssueType() == 4){ // PAST DUE ACCOUNT
                                   
                            %>
                            <div class="modal fade" id="singleARBDisbursementDetails<%out.print(i.getId());%>">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">×</span>
                                            </button>
                                            <h4 class="modal-title">SINGLE ARB DISBURSEMENT</h4>
                                        </div>

                                        <div class="modal-body">
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <div class="form-group">
                                                        <label for="">Findings</label>
                                                        <textarea name="findings" id="" cols="3" rows="3" class="form-control" disabled><%out.print(i.getFindings());%></textarea>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <div class="form-group">
                                                        <label for="">Resolution</label>
                                                        <textarea name="resolution" id="" cols="3" rows="3" class="form-control" disabled><%out.print(i.getResolution());%></textarea>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                            <%}%>

                            <%}%>

                        </div>
                    </div>
                </section>
            </div>
        </div>

        <!--Import jQuery before materialize.js-->
        <%@include file="jspf/footer.jspf" %>

    </body>
</html>
