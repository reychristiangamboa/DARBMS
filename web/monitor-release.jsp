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
            
            <%if ((Integer) session.getAttribute("userType") == 1) {%>
            <%@include file="jspf/admin-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 2) {%>
            <%@include file="jspf/point-person-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>
            <%
                ARBDAO arbDAO99 = new ARBDAO();
                ARBODAO arboDAO99 = new ARBODAO();
                APCPRequestDAO apcpRequestDAO99 = new APCPRequestDAO();
                CAPDEVDAO capdevDAO99 = new CAPDEVDAO();
                UserDAO uDAO99 = new UserDAO();
                
                APCPRequest r = apcpRequestDAO99.getRequestByID((Integer) request.getAttribute("requestID"));
                ARBO a = arboDAO99.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO99.getAllARBsARBO(r.getArboID());
                ArrayList<APCPRelease> releaseList = apcpRequestDAO99.getAllAPCPReleasesByRequest((Integer) request.getAttribute("requestID"));
                ArrayList<PastDueAccount> reasons = capdevDAO99.getAllPastDueReasons();
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>
                    <ol class="breadcrumb">

                    </ol>

                </section>


                <!-- Main content -->
                <section class="content">
                    <%if (request.getAttribute("success") != null) {%>
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-check"></i> <%out.print((String) request.getAttribute("success"));%></h4>
                    </div>
                    <%} else if (request.getAttribute("errMessage") != null) {%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String) request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">APCP Information</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <!-- /.box-header -->


                                <div class="box-body"> 

                                    <div class="nav-tabs-custom">
                                        <!-- Tabs within a box -->
                                        <ul class="nav nav-tabs pull-left">
                                            <li class="active"><a href="#request" data-toggle="tab">Request Information</a></li>
                                            <li><a href="#info" data-toggle="tab">ARBO Profile</a></li>
                                            <li><a href="#history" data-toggle="tab">CAPDEV History</a></li>
                                        </ul>
                                        <%@include file="jspf/arboInfo.jspf"%>
                                    </div>
                                    <hr>        
                                </div>
                                <!-- /.box-body -->

                            </div>
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Release Information</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <!-- /.box-header -->


                                <div class="box-body"> 

                                    <div class="nav-tabs-custom">
                                        <!-- Tabs within a box -->
                                        <ul class="nav nav-tabs pull-left">
                                            <li class="active"><a href="#release" data-toggle="tab">Release</a></li>
                                            <li><a href="#repayment" data-toggle="tab">Repayment</a></li>
                                            <li><a href="#pastdue" data-toggle="tab">Past Due</a></li>
                                        </ul>

                                        <div class="tab-content no-padding">
                                            <!-- Morris chart - Sales -->
                                            <div class="chart tab-pane active" id="release" style="position: relative;">
                                                <div class="box-body">

                                                    <table id="releaseTable" class="table table-bordered table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th>Release Amount</th>
                                                                <th>Release Date</th>
                                                                <th>Released By</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <%
                                                                for (APCPRelease release : releaseList) {
                                                                    User u = new User();
                                                                    u = uDAO99.searchUser(release.getReleasedBy());
                                                            %>
                                                            <tr>
                                                                <td><a href="MonitorDisbursement?releaseID=<%out.print(release.getReleaseID());%>&requestID=<%out.print(r.getRequestID());%>"><%out.print(currency.format(release.getReleaseAmount()));%></a></td>
                                                                <td><%out.print(f.format(release.getReleaseDate()));%></td>
                                                                <td><%out.print(u.getFullName());%></td>
                                                            </tr>
                                                            <%}%>
                                                        </tbody>

                                                        <tfoot>
                                                        <th>Release Amount</th>
                                                        <th>Release Date</th>
                                                        <th>Released By</th>
                                                        </tfoot>

                                                    </table>
                                                </div>
                                                <div class="box-footer">
                                                    <div class="pull-right">
                                                        <%if ((Integer) session.getAttribute("userType") == 2) {%>
                                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#import-release-modal">Import Releases</button>
                                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#add-release-modal">Add Release</button>
                                                        <%}%>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="chart tab-pane" id="repayment" style="position: relative;">
                                                <div class="box-body">

                                                    <table id="repaymentTable" class="table table-bordered table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th>Repayment Amount</th>
                                                                <th>Repayment Date</th>
                                                                <th>Repaid By</th>
                                                                <th>Recorded By</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <%
                                                                for (Repayment repayment : r.getRepayments()) {
                                                                    User u = new User();
                                                                    u = uDAO99.searchUser(repayment.getRecordedBy());
                                                                    ARB arb = new ARB();
                                                                    arb = arbDAO99.getARBByID(repayment.getArbID());
                                                            %>
                                                            <tr>
                                                                <td><%out.print(currency.format(repayment.getAmount()));%></td>
                                                                <td><%out.print(f.format(repayment.getDateRepayment()));%></td>
                                                                <td><%out.print(arb.getFLName());%></td>
                                                                <td><%out.print(u.getFullName());%></td>
                                                            </tr>
                                                            <%}%>
                                                        </tbody>

                                                        <tfoot>
                                                            <tr>
                                                                <th>Repayment Amount</th>
                                                                <th>Repayment Date</th>
                                                                <th>Repaid By</th>
                                                                <th>Recorded By</th>
                                                            </tr>
                                                        </tfoot>

                                                    </table>
                                                </div>
                                                <div class="box-footer">
                                                    <div class="pull-right">
                                                        <%if ((Integer) session.getAttribute("userType") == 2) {%>
                                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#import-repayment-modal">Import Repayments</button>
                                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#add-repayment-modal">Add Repayment</button>
                                                        <%}%>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="chart tab-pane" id="pastdue" style="position: relative;">
                                                <div class="box-body">

                                                    <table id="pastDueTable" class="table table-bordered table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th>Amount</th>
                                                                <th>Reason Past Due</th>
                                                                <th>Other Reason</th>
                                                                <th>Date</th>
                                                                <th>Recorded By</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <%
                                                                for (PastDueAccount p : r.getPastDueAccounts()) {
                                                                    User u = new User();
                                                                    u = uDAO99.searchUser(p.getRecordedBy());
                                                            %>
                                                            <tr>
                                                                <td><a href="#" data-toggle="modal" data-target="#pastDueModal<%out.print(p.getPastDueAccountID());%>"><%out.print(currency.format(p.getPastDueAmount()));%></a></td>
                                                                <td><%out.print(p.getReasonPastDueDesc());%></td>
                                                                <td><%out.print(p.getOtherReason());%></td>

                                                                <%if (p.getDateSettled() != null) {%>
                                                                <td><%out.print(f.format(p.getDateSettled()));%></td>
                                                                <%} else {%>
                                                                <td><%out.print("Unsettled.");%></td>
                                                                <%}%>

                                                                <td><%out.print(u.getFullName());%></td>
                                                            </tr>


                                                        <div class="modal fade" id="pastDueModal<%out.print(p.getPastDueAccountID());%>">
                                                            <div class="modal-dialog modal-md">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span></button>
                                                                        <h4 class="modal-title">Past Due Account Details</h4>
                                                                    </div>


                                                                    <div class="modal-body" id="modalBody">
                                                                        <div class="row">
                                                                            <div class="col-xs-12">
                                                                                <div class="col-xs-6">
                                                                                    <div class="form-group">
                                                                                        <label for="">Amount</label>
                                                                                        <input type="text" class="form-control" value="<%=currency.format(p.getPastDueAmount())%>" disabled>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-xs-6">
                                                                                    <form method="post">
                                                                                        <div class="form-group">
                                                                                            <label for="">Date Settled</label>
                                                                                            <div class="input-group">
                                                                                                <input type="date" class="form-control" name="dateSettled" <%if (p.getDateSettled() != null) { %> value="<%out.print(p.getDateSettled());%>"<%}%>>
                                                                                                <input type="hidden" name="pastDueAccountID" value="<%=p.getPastDueAccountID()%>">
                                                                                                <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                                                                                <span class="input-group-btn">
                                                                                                    <button class="btn btn-primary" onclick="form.action = 'SettlePastDueAccount'">Settle</button>
                                                                                                </span>
                                                                                            </div>
                                                                                        </div>
                                                                                    </form>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="row">
                                                                            <div class="col-xs-12">
                                                                                <div class="col-xs-6">
                                                                                    <div class="form-group">
                                                                                        <label for="">Reason for Past Due</label>
                                                                                        <input type="text" class="form-control" value="<%=p.getReasonPastDueDesc()%>" disabled>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="col-xs-6">
                                                                                    <label for="">Other Reason for Past Due</label>
                                                                                    <textarea class="form-control" name="otherReason" id="" cols="10" rows="3" disabled><%out.print(p.getOtherReason());%></textarea>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <form method="post">
                                                                        <div class="modal-footer">
                                                                            <input type="hidden" name="id" value="<%=r.getRequestID()%>">
                                                                            <input type="hidden" name="pastDueID" value="<%=p.getPastDueAccountID()%>">
                                                                            <button type="submit" onclick="form.action = 'CreateCAPDEVProposal'" class="btn btn-primary pull-right">Create CAPDEV Proposal</button>
                                                                        </div>
                                                                    </form>

                                                                </div>
                                                                <!--                                            /.modal-content -->
                                                            </div>
                                                            <!--                                        /.modal-dialog -->
                                                        </div>

                                                        <%}%>
                                                        </tbody>

                                                        <tfoot>

                                                            <tr>
                                                                <th>Amount</th>
                                                                <th>Reason Past Due</th>
                                                                <th>Other Reason</th>
                                                                <th>Date</th>
                                                                <th>Recorded By</th>
                                                            </tr>

                                                        </tfoot>

                                                    </table>
                                                </div>
                                                <div class="box-footer">
                                                    <div class="pull-right">
                                                        <%if ((Integer) session.getAttribute("userType") == 2) {%>
                                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#import-pastdue-modal">Import Past Due Accounts</button>
                                                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#add-pastdue-modal">Add Past Due Account</button>
                                                        <%}%>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>


                                </div>
                                <!-- /.box-body -->

                            </div> 
                        </div>
                        <!-- /.col -->
                    </div>

                    <div class="modal fade" id="add-release-modal">
                        <div class="modal-dialog modal-md">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Record Release</h4>
                                </div>

                                <form method="post">
                                    <div class="modal-body" id="modalBody">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Release Amount</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i>&#8369;</i>
                                                        </div>
                                                        <input type="number" class="form-control numberOnly" name="releaseAmount" autocomplete="off" required>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Release Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                                        <input type="date" class="form-control" name="releaseDate" />        
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <button type="submit" onclick="form.action = 'RecordRequestRelease'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>

                            </div>
                            <!--                                            /.modal-content -->
                        </div>
                        <!--                                        /.modal-dialog -->
                    </div>

                    <div class="modal fade" id="import-release-modal">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Import Releases</h4>
                                </div>

                                <form method="post">
                                    <div class="modal-body" id="modalBody">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="form-group">
                                                    <label for="">Import File</label>
                                                    <input type="file" class="form-control" name="file" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <button type="submit" name="manual" onclick="form.action = 'ImportRelease'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>

                            </div>
                            <!--                                            /.modal-content -->
                        </div>
                        <!--                                        /.modal-dialog -->
                    </div>

                    <div class="modal fade" id="add-repayment-modal">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Record Repayment</h4>
                                </div>

                                <form method="post">
                                    <div class="modal-body" id="modalBody">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <table id="example1" class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>ARB Name</th>
                                                            <th>Address</th>
                                                            <th>Crops</th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </thead>

                                                    <tbody>
                                                        <%for (ARB arb : arbList) {%>
                                                        <tr>
                                                            <td><%out.print(arb.getFLName());%></td>
                                                            <td><%out.print(arb.getFullAddress());%></td>
                                                            <td><%out.print(arb.printAllCrops());%></td>

                                                            <td>
                                                                <input type="checkbox" name="arbIDs" value="<%=arb.getArbID()%>"/>
                                                            </td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>

                                                </table>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Amount</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i>&#8369;</i>
                                                        </div>
                                                        <input type="number" class="form-control numberOnly" name="repaymentAmount" autocomplete="off" required>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Repayment Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                                        <input type="date" class="form-control" name="repaymentDate" />        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <button type="submit" name="manual" onclick="form.action = 'RecordRepayment'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>

                            </div>
                            <!--                                            /.modal-content -->
                        </div>
                        <!--                                        /.modal-dialog -->
                    </div>

                    <div class="modal fade" id="import-repayment-modal">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Import Repayments</h4>
                                </div>

                                <form method="post">
                                    <div class="modal-body" id="modalBody">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="form-group">
                                                    <label for="">Import File</label>
                                                    <input type="file" class="form-control" name="file" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <button type="submit" name="manual" onclick="form.action = 'ImportRepayment'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>

                            </div>
                            <!--                                            /.modal-content -->
                        </div>
                        <!--                                        /.modal-dialog -->
                    </div>

                    <div class="modal fade" id="add-pastdue-modal">
                        <div class="modal-dialog modal-md">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Record Past Due Account</h4>
                                </div>

                                <form method="post">
                                    <div class="modal-body" id="modalBody">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Amount</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i>&#8369;</i>
                                                        </div>
                                                        <input type="number" class="form-control numberOnly" name="pastDueAmount" autocomplete="off" required>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Reason for Past Due </label>
                                                    <select class="form-control" name="reasonPastDue" id="">
                                                        <%for (PastDueAccount p : reasons) {%>
                                                        <option value="<%=p.getReasonPastDue()%>"><%out.print(p.getReasonPastDueDesc());%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Date Recorded</label>
                                                    <input type="date" class="form-control" name="recordedDate" />
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="form-group">
                                                    <label for="">Other Reason</label>
                                                    <textarea class="form-control" name="otherReason" id="" cols="10" rows="3">N/A</textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <button type="submit" name="manual" onclick="form.action = 'RecordPastDueAccount'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>

                            </div>
                            <!--                                            /.modal-content -->
                        </div>
                        <!--                                        /.modal-dialog -->
                    </div>
                    <div class="modal fade" id="import-pastdue-modal">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Import Past Due Accounts</h4>
                                </div>

                                <form method="post">
                                    <div class="modal-body" id="modalBody">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="form-group">
                                                    <label for="">Import File</label>
                                                    <input type="file" class="form-control" name="file" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <button type="submit" name="manual" onclick="form.action = 'ImportPastDueAccount'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>

                            </div>
                            <!--                                            /.modal-content -->
                        </div>
                        <!--                                        /.modal-dialog -->
                    </div>

                </section>
                <!-- /.content -->

            </div>
            <!-- /.content-wrapper -->
            
        </div>
        <%@include file="jspf/footer.jspf" %>
        <script>
            var ctx = $('#barCanvas').get(0).getContext('2d');
            <%
                Chart bar = new Chart();
                String json = bar.getBarChartEducation(arbList);
            %>
            new Chart(ctx, <%out.print(json);%>);

            var ctx3 = $('#pieCanvas').get(0).getContext('2d');
            <%
                Chart pie = new Chart();
                String json3 = pie.getPieChartGender(arbList);
            %>
            new Chart(ctx3, <%out.print(json3);%>);

        </script>

    </body>
</html>
