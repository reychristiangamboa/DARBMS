<%-- 
    Document   : arb-profile
    Created on : Mar 14, 2018, 6:35:19 PM
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

            .example-modal .modal {
                position: relative;
                top: auto;
                bottom: auto;
                right: auto;
                left: auto;
                display: block;
                z-index: 1;
            }

            .example-modal .modal {
                background: transparent !important;
            }

        </style>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%if ((Integer) session.getAttribute("userType") == 2) {%>
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
                CAPDEVDAO arbCapdevDAO = new CAPDEVDAO();
                APCPRequestDAO arDAO = new APCPRequestDAO();
                ARBODAO dao = new ARBODAO();
                EvaluationDAO eDAO = new EvaluationDAO();
                CropDAO cDAO = new CropDAO();
                ARBDAO arbDAO = new ARBDAO();
                
                APCPRequestDAO dao4 = new APCPRequestDAO();
                
                ArrayList<Crop> allCrops = cDAO.getAllCrops();

                ARB arb = (ARB) request.getAttribute("arb");
                ARBO arbo = dao.getARBOByID(arb.getArboID());
                ArrayList<Disbursement> disbursements = arDAO.getAllDisbursementsByARB(arb.getArbID());
                ArrayList<Repayment> repayments = arDAO.getArbRepaymentsByARB(arb.getArbID());
                ArrayList<Evaluation> arbEvaluations = eDAO.getEvaluationPerARBIDByType(arb.getArbID(), 1);
                ArrayList<Evaluation> apcpEvaluations = eDAO.getEvaluationPerARBIDByType(arb.getArbID(), 2);
                ArrayList<Evaluation> capdevEvaluations = eDAO.getEvaluationPerARBIDByType(arb.getArbID(), 3);
                

                CAPDEVActivity attendance = new CAPDEVActivity();
                ArrayList<CAPDEVActivity> myActivities = arbCapdevDAO.getCAPDEVPlanByARB(arb.getArbID());
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1><i class="fa fa-user"></i> Agrarian Reform Beneficiary Profile</h1>

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
                        <div class="col-xs-3">
                            <div class="box box-primary">
                                <div class="box-body box-profile">
                                    <h3 class="profile-username text-center"><%=arb.getFullName()%></h3>

                                    <p class="text-center"><a href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%=arbo.getArboName()%></a></p>

                                    <ul class="list-group list-group-unbordered">
                                        <li class="list-group-item">
                                            <b>Sex</b> 
                                            <a class="pull-right">
                                                <%
                                                    if (arb.getGender().equals("M")) {
                                                        out.print("Male");
                                                    } else if (arb.getGender().equals("F")) {
                                                        out.print("Female");
                                                    }
                                                %>
                                            </a>
                                        </li>
                                        <br>
                                        <strong> Educational Attainment</strong>

                                        <p class="text-muted">
                                            <%=arb.getEducationLevelDesc()%>
                                        </p>

                                        <hr>

                                        <strong> Address</strong>

                                        <p class="text-muted"><%=arb.getFullAddress()%></p>
                                        <li class="list-group-item">
                                            <b>Member Since</b> <a class="pull-right"><%out.print(f.format(arb.getMemberSince()));%></a>
                                        </li>
                                        <li class="list-group-item">
                                            <b>Land Area</b> <a class="pull-right"><%out.print(arb.getLandArea());%> Hectares</a>
                                        </li>
                                        <li class="list-group-item">
                                            <%arb.setDependents(arbDAO.getAllARBDependents(arb.getArbID()));
                                                if (arb.getDependents().size() > 0) {%>
                                            <b>Dependents</b> <a class="pull-right" data-toggle="modal" data-target="#dependents"><%=arb.getDependents().size()%></a>
                                            <%} else {%>
                                            <b>Dependents</b> <a class="pull-right">N/A</a>
                                            <%}%>
                                        </li>
                                    </ul>

                                    <!-- /.box-body -->

                                    <div class="modal fade" id="dependents">
                                        <div class="modal-dialog modal-lg">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span></button>
                                                    <h4 class="modal-title">Dependents</h4>
                                                </div>


                                                <div class="modal-body" id="modalBody">
                                                    <div class="row">
                                                        <div class="col-xs-12">
                                                            <table id="arbTable" class="table table-bordered table-striped">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Full Name</th>
                                                                        <th>Birthday</th>
                                                                        <th>Education Level</th>
                                                                        <th>Relationship</th>
                                                                    </tr>
                                                                </thead>

                                                                <tbody>
                                                                    <%
                                                                        for (Dependent d : arb.getDependents()) {
                                                                    %>
                                                                    <tr>
                                                                        <td><%=d.getName()%></td>
                                                                        <td><%=f.format(d.getBirthday())%></td>
                                                                        <td><%=d.getEducationLevelDesc()%></td>
                                                                        <td><%=d.getRelationshipTypeDesc()%></td>

                                                                    </tr>
                                                                    <%}%>
                                                                </tbody>

                                                                <tfoot>
                                                                    <tr>
                                                                        <th>Full Name</th>
                                                                        <th>Birthday</th>
                                                                        <th>Education Level</th>
                                                                        <th>Relationship</th>
                                                                    </tr>
                                                                </tfoot>

                                                            </table>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                            <!--                                            /.modal-content -->
                                        </div>
                                        <!--                                        /.modal-dialog -->
                                    </div>

                                    <div class="modal fade" id="add-evaluation-arb">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span></button>
                                                    <h4 class="modal-title">Add Evaluation: Overall</h4>
                                                </div>
                                                <form method="post">
                                                    <div class="modal-body">

                                                        <div class="row">
                                                            <div class="col-xs-6">
                                                                <div class="form-group">
                                                                    <label>Date:</label>
                                                                    <div class="input-group date">
                                                                        <div class="input-group-addon">
                                                                            <i class="fa fa-calendar"></i>
                                                                        </div>
                                                                        <input type="date" name="evaluationDate" class="form-control pull-right" id="datepicker">
                                                                    </div>
                                                                    <!-- /.input group -->
                                                                </div>
                                                            </div>
                                                            <div class="col-xs-6">
                                                                <div class="form-group">
                                                                    <label>Evaluation Quarter:</label>

                                                                    <div class="input-group">
                                                                        <button type="button" class="btn btn-default pull-right" id="daterange-btn">
                                                                            <span>
                                                                                <i class="fa fa-calendar"></i> Date range picker
                                                                            </span>
                                                                            <i class="fa fa-caret-down"></i>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-xs-6">
                                                                <div class="form-group">
                                                                    <label>Document Tracking No.</label>
                                                                    <input type="text" name="dtn" class="form-control pull-right">
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="pull-right">
                                                            <input type="hidden" id="start" name="start">
                                                            <input type="hidden" id="end" name="end" >
                                                            <input type="hidden" id="maxDate" name="maxDate">
                                                            <input type="hidden" name="type" value="1">
                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                            <button type="submit" id="submitEval" class="btn btn-primary" onclick="form.action = 'AddEvaluation?id=<%out.print(arb.getArbID());%>'">Submit</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <!-- /.modal-content -->
                                        </div>
                                        <!-- /.modal-dialog -->
                                    </div>
                                    <div class="modal fade" id="add-evaluation-apcp">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span></button>
                                                    <h4 class="modal-title">Add Evaluation: APCP</h4>
                                                </div>
                                                <form method="post">
                                                    <div class="modal-body">

                                                        <div class="row">
                                                            <div class="col-xs-6">
                                                                <div class="form-group">
                                                                    <label>Date:</label>
                                                                    <div class="input-group date">
                                                                        <div class="input-group-addon">
                                                                            <i class="fa fa-calendar"></i>
                                                                        </div>
                                                                        <input type="date" name="evaluationDate" class="form-control pull-right" id="datepicker">
                                                                    </div>
                                                                    <!-- /.input group -->
                                                                </div>
                                                            </div>
                                                            <div class="col-xs-6">
                                                                <div class="form-group">
                                                                    <label>Evaluation Quarter:</label>

                                                                    <div class="input-group">
                                                                        <button type="button" class="btn btn-default pull-right" id="daterange-btn">
                                                                            <span>
                                                                                <i class="fa fa-calendar"></i> Date range picker
                                                                            </span>
                                                                            <i class="fa fa-caret-down"></i>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-xs-6">
                                                                <div class="form-group">
                                                                    <label>Document Tracking No.</label>
                                                                    <input type="text" name="dtn" class="form-control pull-right">
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="pull-right">
                                                            <input type="hidden" id="start" name="start">
                                                            <input type="hidden" id="end" name="end" >
                                                            <input type="hidden" id="maxDate" name="maxDate">
                                                            <input type="hidden" name="type" value="2">
                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                            <button type="submit" id="submitEval" class="btn btn-primary" onclick="form.action = 'AddEvaluation?id=<%out.print(arb.getArbID());%>'">Submit</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <!-- /.modal-content -->
                                        </div>
                                        <!-- /.modal-dialog -->
                                    </div>
                                    <div class="modal fade" id="add-evaluation-capdev">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span></button>
                                                    <h4 class="modal-title">Add Evaluation: CAPDEV</h4>
                                                </div>
                                                <form method="post">
                                                    <div class="modal-body">

                                                        <div class="row">
                                                            <div class="col-xs-6">
                                                                <div class="form-group">
                                                                    <label>Date:</label>
                                                                    <div class="input-group date">
                                                                        <div class="input-group-addon">
                                                                            <i class="fa fa-calendar"></i>
                                                                        </div>
                                                                        <input type="date" name="evaluationDate" class="form-control pull-right" id="datepicker">
                                                                    </div>
                                                                    <!-- /.input group -->
                                                                </div>
                                                            </div>
                                                            <div class="col-xs-6">
                                                                <div class="form-group">
                                                                    <label>Evaluation Quarter:</label>

                                                                    <div class="input-group">
                                                                        <button type="button" class="btn btn-default pull-right" id="daterange-btn">
                                                                            <span>
                                                                                <i class="fa fa-calendar"></i> Date range picker
                                                                            </span>
                                                                            <i class="fa fa-caret-down"></i>
                                                                        </button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-xs-6">
                                                                <div class="form-group">
                                                                    <label>Document Tracking No.</label>
                                                                    <input type="text" name="dtn" class="form-control pull-right">
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="pull-right">
                                                            <input type="hidden" id="start" name="start">
                                                            <input type="hidden" id="end" name="end" >
                                                            <input type="hidden" id="maxDate" name="maxDate">
                                                            <input type="hidden" name="type" value="3">
                                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                            <button type="submit" id="submitEval" class="btn btn-primary" onclick="form.action = 'AddEvaluation?id=<%out.print(arb.getArbID());%>'">Submit</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <!-- /.modal-content -->
                                        </div>
                                        <!-- /.modal-dialog -->
                                    </div>

                                    <strong><i class="fa fa-file-text-o margin-r-5"></i> Crops</strong>

                                    <p>
                                        <%
                                            arb.setCurrentCrops(arbDAO.getAllARBCurrentCrops(arb.getArbID()));
                                            for (Crop c : arb.getCurrentCrops()) {
                                        %>
                                        <span class="label label-success"><%=c.getCropTypeDesc()%></span>
                                        <%}%>
                                    </p>
                                    <a href="#" data-toggle="modal" data-target="#cropTimeline" class="text-center">View Crop Timeline</a>
                                    <a href="#" data-toggle="modal" data-target="#addCrop" class="text-center">Add Crop</a>


                                    <div class="modal fade" id="cropTimeline">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span></button>
                                                    <h4 class="modal-title">Crop Timeline</h4>
                                                </div>

                                                <div class="modal-body" style="overflow-y: scroll; overflow-x: hidden;  max-height: 500px; ">
                                                    <ul class="timeline">
                                                        <%
                                                            boolean firstInstance99 = true;
                                                            Date date99 = null;
                                                        %>
                                                        <%
                                                            arb.setCrops(arbDAO.getAllARBCrops(arb.getArbID()));
                                                            for (Crop c : arb.getCrops()) {%>

                                                        <li class="time-label">
                                                            <span class="bg-aqua">
                                                                <%=c.getCropTypeDesc()%>
                                                            </span>
                                                        </li>

                                                        <li>
                                                            <i class="fa fa-calendar-check-o bg-green"></i>
                                                            <div class="timeline-item">
                                                                <h3 class="timeline-header">
                                                                    Start Date: <%out.print(f.format(c.getStartDate()));%>
                                                                </h3>
                                                            </div>
                                                        </li>

                                                        <li>
                                                            <i class="fa fa-calendar-times-o bg-red"></i>
                                                            <div class="timeline-item">
                                                                <h3 class="timeline-header">
                                                                    End Date: <%out.print(f.format(c.getEndDate()));%>
                                                                </h3>
                                                            </div>
                                                        </li>
                                                        <%}%>
                                                    </ul>

                                                </div>
                                            </div>
                                            <!-- /.modal-content -->
                                        </div>
                                        <!-- /.modal-dialog -->
                                    </div>
                                    <div class="modal fade" id="addCrop">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span></button>
                                                    <h4 class="modal-title">Add Crop</h4>
                                                </div>
                                                <form method="post">
                                                    <div class="modal-body" style="overflow-y: scroll; overflow-x: hidden;  max-height: 500px; ">
                                                        <div class="row">
                                                            <div class="col-xs-4">
                                                                <button class="add_field_button btn btn-primary" type="button">Add Crop</button>
                                                            </div>
                                                        </div>
                                                        <div class="input_fields_wrap" id="wrapper">


                                                        </div>


                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="pull-right">
                                                            <button class="btn btn-primary" onclick="form.action = 'AddARBCrop?arbID=<%out.print(arb.getArbID());%>'" type="submit">Submit</button>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <!-- /.modal-content -->
                                        </div>
                                        <!-- /.modal-dialog -->
                                    </div>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Credit Standing</h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <strong><i class="fa fa-money margin-r-5"></i> Disbursement Amount</strong>
                                    <p class="text-muted"><%out.print(currency.format(arb.getTotalDisbursementAmount()));%></p>
                                    <hr>
                                    <% APCPRequest arbRequest = new APCPRequest(); %>
                                    <strong><i class="fa fa-money margin-r-5"></i> Outstanding Balance</strong>
                                    <p class="text-muted"><%out.print(currency.format(arbRequest.getTotalARBOSBalance(arb.getArbID())));%></p>

                                </div>
                            </div>
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                    <h3 class="box-title">CAPDEV</h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <strong><i class="fa fa-clipboard margin-r-5"></i> Activities</strong>
                                    <p class="text-muted"><%out.print(myActivities.size());%></p>
                                    <hr>
                                    <strong><i class="fa fa-user margin-r-5"></i> Participation Rates</strong>
                                    <p class="text-muted"><%out.print(attendance.getAttendance(myActivities));%> / <%out.print(myActivities.size());%> (<%out.print(arbCapdevDAO.getPercentage(attendance.getAttendance(myActivities),myActivities.size()));%>%)</p>

                                </div>
                            </div>
                        </div>
                        <div class="col-xs-9">
                            <div class="box box-solid">

                                <div class="box-body">
                                    
                                    <div class="panel box box-success">
                                        <div class="box-header with-border">
                                            <h4 class="box-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" class="collapsed" aria-expanded="false">
                                                    APCP Visuals
                                                </a>
                                            </h4>
                                        </div>
                                        <div id="collapseThree" class="panel-collapse collapse in" aria-expanded="false">
                                            <div class="box-body">                                                
                                                <div class="nav-tabs-custom">

                                                    <ul class="nav nav-tabs">
                                                        <li class="active"><a href="#disbursement" data-toggle="tab">Disbursement</a></li>
                                                        <li><a href="#repayment" data-toggle="tab">Repayments</a></li>
                                                            <%--<li><a href="#attendance" data-toggle="tab">Attendance</a></li>--%>

                                                    </ul>

                                                    <div class="tab-content" style="overflow-y: scroll; overflow-x: hidden;  max-height: 300px; ">
                                                        <div class="active tab-pane" id="disbursement">
                                                            <ul class="timeline timeline-inverse">
                                                                <%
                                                                    boolean firstInstance = true;
                                                                    Date date = null;
                                                                %>
                                                                <% for (Disbursement db : disbursements) { %>
                                                                <%
                                                                    boolean dateChanged = false;

                                                                    if (firstInstance) { // FIRST INSTANCE
                                                                        date = db.getDateDisbursed();
                                                                    }
                                                                %>

                                                                <%
                                                                    if (date.compareTo(db.getDateDisbursed()) != 0) { // NEW DATE, change currDate
                                                                        date = db.getDateDisbursed();
                                                                        dateChanged = true;
                                                                        System.out.print("Date changed!");
                                                                    }
                                                                %>

                                                                <%if (firstInstance || dateChanged) {%>
                                                                <li class="time-label">
                                                                    <span class="bg-green">
                                                                        <%out.print(f.format(date));%>
                                                                    </span>
                                                                </li>
                                                                <%firstInstance = false;%>
                                                                <%}%>

                                                                <li>
                                                                    <i class="fa fa-clipboard bg-green"></i>
                                                                    <div class="timeline-item">
                                                                        <h3 class="timeline-header">
                                                                            <i>&#8369</i>&nbsp;<%out.print(db.getDisbursedAmount());%>
                                                                        </h3>
                                                                    </div>
                                                                </li>
                                                                <%}%>
                                                                <li>
                                                                    <i class="fa fa-clock-o bg-gray"></i>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                        <!-- /.tab-pane -->
                                                        <div class="tab-pane" id="repayment" >
                                                            <!-- The timeline -->
                                                            <ul class="timeline timeline-inverse">
                                                                <%
                                                                    boolean firstInstance1 = true;
                                                                    Date date1 = null;
                                                                %>
                                                                <% for (Repayment rp : repayments) { %>
                                                                <%
                                                                    boolean dateChanged1 = false;

                                                                    if (firstInstance1) { // FIRST INSTANCE
                                                                        date1 = rp.getDateRepayment();
                                                                    }
                                                                %>

                                                                <%
                                                                    if (date1.compareTo(rp.getDateRepayment()) != 0) { // NEW DATE, change currDate
                                                                        date1 = rp.getDateRepayment();
                                                                        dateChanged1 = true;
                                                                        System.out.print("Date changed!");
                                                                    }
                                                                %>

                                                                <%if (firstInstance1 || dateChanged1) {%>
                                                                <li class="time-label">
                                                                    <span class="bg-green">
                                                                        <%out.print(f.format(date1));%>
                                                                    </span>
                                                                </li>
                                                                <%firstInstance1 = false;%>
                                                                <%}%>

                                                                <li>
                                                                    <i class="fa fa-clipboard bg-green"></i>
                                                                    <div class="timeline-item">
                                                                        <h3 class="timeline-header">
                                                                            <i>&#8369</i>&nbsp;<%out.print(rp.getAmount());%>
                                                                        </h3>
                                                                    </div>
                                                                </li>
                                                                <%}%>
                                                                <li>
                                                                    <i class="fa fa-clock-o bg-gray"></i>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                        <!-- /.tab-pane -->
                                                        <%--<div class="tab-pane" id="attendance" >
                                                            <!-- The timeline -->
                                                            <%ArrayList<CAPDEVActivity> myActivities = arbCapdevDAO.getCAPDEVPlanByARB(arb.getArbID());%>
                                                            <%CAPDEVActivity attendance = new CAPDEVActivity();%>
                                                            <div class="progress">
                                                                <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: <%out.print(attendance.getAttendanceRate(myActivities));%>%">
                                                                    <%out.print(attendance.getAttendance(myActivities));%> / <%out.print(myActivities.size());%>
                                                                </div> 
                                                            </div> 

                                                            <%

                                                                boolean firstInstance23 = true;
                                                                Date date23 = null;
                                                            %>

                                                            <ul class="timeline timeline-inverse">
                                                                <%for (CAPDEVActivity act : myActivities) {%>
                                                                <%
                                                                    boolean dateChanged = false;

                                                                            if (firstInstance23) { // FIRST INSTANCE
                                                                                date23 = act.getActivityDate();
                                                                            }
                                                                %>

                                                                <%
                                                                    if (date23.compareTo(act.getActivityDate()) != 0) { // NEW DATE, change currDate
                                                                        date23 = act.getActivityDate();
                                                                        dateChanged = true;
                                                                        System.out.print("Date changed!");
                                                                    }
                                                                %>
                                                                <!-- timeline time label -->
                                                                <%if (firstInstance23 || dateChanged) {%>
                                                                <li class="time-label">
                                                                    <span class="bg-green">
                                                                        <%out.print(f.format(date23));%>
                                                                    </span>
                                                                </li>
                                                                <%firstInstance23 = false;%>
                                                                <%}%>
                                                                <li>
                                                                    <%if (act.getIsPresent() == 1) {%>
                                                                    <i class='fa fa-check bg-green'></i>
                                                                    <%} else if (act.getIsPresent() == 0) {%>
                                                                    <i class='fa fa-times bg-red'></i>
                                                                    <%}%>
                                                                    <div class="timeline-item">
                                                                        <h3 class="timeline-header">
                                                                            <a href="#" data-toggle='modal' data-target='#activity<%out.print(act.getActivityID());%>'><%out.print(act.getActivityName());%></a>
                                                                        </h3>
                                                                        <div class="timeline-body">
                                                                            <p><%out.print(act.getActivityDesc());%></p>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                                <div class="modal fade" id="activity<%out.print(act.getActivityID());%>">
                                                                    <div class="modal-dialog modal-md">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                    <span aria-hidden="true">&times;</span></button>
                                                                                <h4 class="modal-title">Activity Details</h4>
                                                                            </div>


                                                                            <div class="modal-body" id="modalBody">

                                                                                <div class="row">
                                                                                    <div class="col-xs-4">
                                                                                        <div class="form-group">
                                                                                            <label for="">Activity Title</label>
                                                                                            <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(act.getActivityName());%>" disabled>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="row">
                                                                                    <div class="col-xs-12">
                                                                                        <div class="form-group">
                                                                                            <label for="">Activity Description</label>
                                                                                            <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(act.getActivityDesc());%>" disabled>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="row">
                                                                                    <div class="col-xs-4">
                                                                                        <div class="form-group">
                                                                                            <label for="">Activity Date</label>
                                                                                            <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(f.format(act.getActivityDate()));%>" disabled>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-xs-4">
                                                                                        <div class="form-group">
                                                                                            <label for="">No. of Participants</label>
                                                                                            <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(act.getArbList().size());%>" disabled>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>

                                                                            </div>


                                                                        </div>
                                                                        <!--                                            /.modal-content -->
                                                                    </div>
                                                                    <!--                                        /.modal-dialog -->
                                                                </div>
                                                                <%}%>
                                                                <li>
                                                                    <i class="fa fa-clock-o bg-gray"></i>
                                                                </li>
                                                            </ul>
                                                        </div>--%>

                                                    </div>
                                                    <!-- /.tab-content -->
                                                </div>                                         
                                            </div>
                                        </div>
                                    </div>
                                    <div class="panel box box-success">
                                        <div class="box-header with-border">
                                            <h4 class="box-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour" class="collapsed" aria-expanded="false">
                                                    CAPDEV Visuals
                                                </a>
                                            </h4>
                                        </div>
                                        <div id="collapseFour" class="panel-collapse collapse in" aria-expanded="false">
                                            <div class="box-body">                                                
                                                <div class="nav-tabs-custom">

                                                    <ul class="nav nav-tabs">
                                                        <li class="active"><a href="#attendance" data-toggle="tab">Attendance</a></li>
                                                    </ul>

                                                    <div class="tab-content" style="overflow-y: scroll; overflow-x: hidden; max-height: 300px">

                                                        <div class="active tab-pane" id="attendance">
                                                            <!-- The timeline -->
                                                            
                                                            
                                                            <div class="progress">
                                                                <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: <%out.print(attendance.getAttendanceRate(myActivities));%>%">
                                                                    <%out.print(attendance.getAttendance(myActivities));%> / <%out.print(myActivities.size());%>
                                                                </div> 
                                                            </div> 

                                                            <%

                                                                boolean firstInstance23 = true;
                                                                Date date23 = null;
                                                            %>

                                                            <ul class="timeline timeline-inverse">
                                                                <%for (CAPDEVActivity act : myActivities) {%>
                                                                <%
                                                                    boolean dateChanged = false;

                                                                            if (firstInstance23) { // FIRST INSTANCE
                                                                                date23 = act.getActivityDate();
                                                                            }
                                                                %>

                                                                <%
                                                                    if (date23.compareTo(act.getActivityDate()) != 0) { // NEW DATE, change currDate
                                                                        date23 = act.getActivityDate();
                                                                        dateChanged = true;
                                                                        System.out.print("Date changed!");
                                                                    }
                                                                %>
                                                                <!-- timeline time label -->
                                                                <%if (firstInstance23 || dateChanged) {%>
                                                                <li class="time-label">
                                                                    <span class="bg-green">
                                                                        <%out.print(f.format(date23));%>
                                                                    </span>
                                                                </li>
                                                                <%firstInstance23 = false;%>
                                                                <%}%>
                                                                <li>
                                                                    <%if (act.getIsPresent() == 1) {%>
                                                                    <i class='fa fa-check bg-green'></i>
                                                                    <%} else if (act.getIsPresent() == 0) {%>
                                                                    <i class='fa fa-times bg-red'></i>
                                                                    <%}%>
                                                                    <div class="timeline-item">
                                                                        <h3 class="timeline-header">
                                                                            <a href="#" data-toggle='modal' data-target='#activity<%out.print(act.getActivityID());%>'><%out.print(act.getActivityName());%></a>
                                                                        </h3>
                                                                        <div class="timeline-body">
                                                                            <p><%out.print(act.getActivityDesc());%></p>
                                                                        </div>
                                                                    </div>
                                                                </li>
                                                                <div class="modal fade" id="activity<%out.print(act.getActivityID());%>">
                                                                    <div class="modal-dialog modal-md">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                    <span aria-hidden="true">&times;</span></button>
                                                                                <h4 class="modal-title">Activity Details</h4>
                                                                            </div>


                                                                            <div class="modal-body" id="modalBody">

                                                                                <div class="row">
                                                                                    <div class="col-xs-4">
                                                                                        <div class="form-group">
                                                                                            <label for="">Activity Title</label>
                                                                                            <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(act.getActivityName());%>" disabled>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="row">
                                                                                    <div class="col-xs-12">
                                                                                        <div class="form-group">
                                                                                            <label for="">Activity Description</label>
                                                                                            <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(act.getActivityDesc());%>" disabled>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                                <div class="row">
                                                                                    <div class="col-xs-4">
                                                                                        <div class="form-group">
                                                                                            <label for="">Activity Date</label>
                                                                                            <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(f.format(act.getActivityDate()));%>" disabled>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="col-xs-4">
                                                                                        <div class="form-group">
                                                                                            <label for="">No. of Participants</label>
                                                                                            <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(act.getArbList().size());%>" disabled>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>

                                                                            </div>


                                                                        </div>
                                                                        <!--                                            /.modal-content -->
                                                                    </div>
                                                                    <!--                                        /.modal-dialog -->
                                                                </div>
                                                                <%}%>
                                                                <li>
                                                                    <i class="fa fa-clock-o bg-gray"></i>
                                                                </li>
                                                            </ul>
                                                        </div>

                                                    </div>
                                                    <!-- /.tab-content -->
                                                </div>                                         
                                            </div>
                                        </div>
                                    </div>
                                                                
                                    <div class="panel box box-danger">
                                        <div class="box-header with-border">
                                            <h4 class="box-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="collapsed" aria-expanded="false">
                                                    Evaluations
                                                </a>
                                            </h4>
                                        </div>
                                        <div id="collapseTwo" class="panel-collapse collapse" aria-expanded="false">
                                            <div class="box-body">

                                                <div class="nav-tabs-custom">
                                                    <ul class="nav nav-tabs">
                                                        <li class="active"><a href="#apcp" data-toggle="tab">APCP Rating</a></li>
                                                        <li><a href="#capdev" data-toggle="tab">CAPDEV</a></li>
                                                        <li><a href="#overall" data-toggle="tab">Overall</a></li>

                                                    </ul>

                                                    <div class="tab-content">
                                                        <div class="active tab-pane" id="apcp">
                                                            <div class="box-body">
                                                                <div class="chart">
                                                                    <canvas id="lineAPCPRating" style="height:250px"></canvas>
                                                                    <div class="row text-center">
                                                                        <a class="btn btn-submit" data-toggle="modal" data-target="#modalAPCP">View More</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <%if ((Integer) session.getAttribute("userType") == 2) {%>
                                                            <div class="box-footer">
                                                                <button id="addEval" class="btn btn-primary pull-right" data-toggle="modal" data-target="#add-evaluation-apcp">Add Evaluation</button>
                                                            </div>
                                                            <%}%>
                                                        </div>
                                                        <div class="modal fade" id="modalAPCP">
                                                            <div class="modal-dialog modal-lger">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span></button>
                                                                        <h4 class="modal-title"></h4>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <table class="table table-bordered table-striped modTable">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>Evaluation DTN</th>
                                                                                    <th>Rating</th>
                                                                                    <th>Evaluation Date</th>
                                                                                    <th>Evaluation Start & End</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%

                                                                                    for (Evaluation evalArb : apcpEvaluations) {
                                                                                %>
                                                                                <tr>
                                                                                    <td><%out.print(evalArb.getEvaluationDTN());%></td>
                                                                                    <td><%out.print(evalArb.getRating());%></td>
                                                                                    <td><%out.print(evalArb.getEvaluationDate());%></td>
                                                                                    <td><%out.print(evalArb.getEvaluationStartDate() + "-" + evalArb.getEvaluationEndDate());%></td>
                                                                                </tr>
                                                                                <%}%>

                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>Evaluation DTN</th>
                                                                                    <th>Rating</th>
                                                                                    <th>Evaluation Date</th>
                                                                                    <th>Evaluation Start & End</th>
                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>
                                                                    </div>
                                                                    <form method="post">
                                                                        <div class="modal-footer">
                                                                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                                            <div class="pull-right">
                                                                                <button type="button" class="btn btn-default" id="dr-totalYearReleaseReport">
                                                                                    <span>
                                                                                        <i class="fa fa-calendar"></i> Date range picker
                                                                                    </span>
                                                                                    <i class="fa fa-caret-down"></i>
                                                                                </button>


                                                                                <input type="hidden" name="reportType" value="3">
                                                                                <input type="hidden" id="start-totalYearReleaseReport" name="start">
                                                                                <input type="hidden" id="end-totalYearReleaseReport" name="end">
                                                                                <input type="hidden" value="<%=arb.getArbID()%>" name="arbID">
                                                                                <button type="submit" class="btn btn-default" onclick="form.action = 'ViewReport'">Generate Report</button>    



                                                                            </div>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                                <!-- /.modal-content -->
                                                            </div>
                                                            <!-- /.modal-dialog -->
                                                        </div>

                                                        <div class="tab-pane" id="capdev">
                                                            <div class="box-body">
                                                                <div class="chart">
                                                                    <canvas id="lineCAPDEV" style="height:250px"></canvas>
                                                                    <div class="row text-center">
                                                                        <a class="btn btn-submit" data-toggle="modal" data-target="#modalCAPDEV">View More</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <%if ((Integer) session.getAttribute("userType") == 8) {%>
                                                            <div class="box-footer">
                                                                <button id="addEval" class="btn btn-primary pull-right" data-toggle="modal" data-target="#add-evaluation-capdev">Add Evaluation</button>
                                                            </div>
                                                            <%}%>
                                                        </div>
                                                        <div class="modal fade" id="modalCAPDEV">
                                                            <div class="modal-dialog modal-lger">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span></button>
                                                                        <h4 class="modal-title"></h4>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <table class="table table-bordered table-striped modTable">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>Evaluation DTN</th>
                                                                                    <th>Rating</th>
                                                                                    <th>Evaluation Date</th>
                                                                                    <th>Evaluation Start & End</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%

                                                                                    for (Evaluation evalArb : capdevEvaluations) {
                                                                                %>
                                                                                <tr>
                                                                                    <td><a href="ViewARB?id=<%out.print(arb.getArbID());%>"> <%out.print(evalArb.getEvaluationDTN());%> </a></td>
                                                                                    <td><%out.print(evalArb.getRating());%></td>
                                                                                    <td><%out.print(evalArb.getEvaluationDate());%></td>
                                                                                    <td><%out.print(evalArb.getEvaluationStartDate() + "-" + evalArb.getEvaluationEndDate());%></td>
                                                                                </tr>
                                                                                <%}%>

                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>Evaluation DTN</th>
                                                                                    <th>Rating</th>
                                                                                    <th>Evaluation Date</th>
                                                                                    <th>Evaluation Start & End</th>
                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>
                                                                    </div>
                                                                    <form method="post">
                                                                        <div class="modal-footer">
                                                                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                                            <div class="pull-right">
                                                                                <button type="button" class="btn btn-default" id="dr-totalAccumulatedReleaseReport">
                                                                                    <span>
                                                                                        <i class="fa fa-calendar"></i> Date range picker
                                                                                    </span>
                                                                                    <i class="fa fa-caret-down"></i>
                                                                                </button>


                                                                                <input type="hidden" name="reportType" value="4">
                                                                                <input type="hidden" id="start-totalAccumulatedReleaseReport" name="start">
                                                                                <input type="hidden" id="end-totalAccumulatedReleaseReport" name="end">
                                                                                <input type="hidden" value="<%=arb.getArbID()%>" name="arbID">
                                                                                <button type="submit" class="btn btn-default" onclick="form.action = 'ViewReport'">Generate Report</button>



                                                                            </div>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                                <!-- /.modal-content -->
                                                            </div>
                                                            <!-- /.modal-dialog -->
                                                        </div>

                                                        <div class="tab-pane" id="overall">
                                                            <div class="box-body">
                                                                <div class="chart">
                                                                    <canvas id="lineARB" style="height:250px"></canvas>
                                                                    <div class="row text-center">
                                                                        <a class="btn btn-submit" data-toggle="modal" data-target="#overallRate">View More</a>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <%if ((Integer) session.getAttribute("userType") == 2 || (Integer) session.getAttribute("userType") == 8) {%>
                                                            <div class="box-footer">
                                                                <button id="addEval" class="btn btn-primary pull-right" data-toggle="modal" data-target="#add-evaluation-arb">Add Evaluation</button>
                                                            </div>
                                                            <%}%>
                                                        </div>
                                                        <div class="modal fade" id="overallRate">
                                                            <div class="modal-dialog modal-lger">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span></button>
                                                                        <h4 class="modal-title"></h4>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <table class="table table-bordered table-striped modTable">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>Evaluation DTN</th>
                                                                                    <th>Rating</th>
                                                                                    <th>Evaluation Date</th>
                                                                                    <th>Evaluation Start & End</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%

                                                                                    for (Evaluation evalArb : arbEvaluations) {
                                                                                %>
                                                                                <tr>
                                                                                    <td><a href="ViewARB?id=<%out.print(arb.getArbID());%>"> <%out.print(evalArb.getEvaluationDTN());%> </a></td>
                                                                                    <td><%out.print(evalArb.getRating());%></td>
                                                                                    <td><%out.print(evalArb.getEvaluationDate());%></td>
                                                                                    <td><%out.print(evalArb.getEvaluationStartDate() + "-" + evalArb.getEvaluationEndDate());%></td>
                                                                                </tr>
                                                                                <%}%>

                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>Evaluation DTN</th>
                                                                                    <th>Rating</th>
                                                                                    <th>Evaluation Date</th>
                                                                                    <th>Evaluation Start & End</th>
                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>
                                                                    </div>
                                                                    <form method="post">
                                                                        <div class="modal-footer">
                                                                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                                            <div class="pull-right">
                                                                                <button type="button" class="btn btn-default" id="dr-totalAccumulatedReleaseReport2">
                                                                                    <span>
                                                                                        <i class="fa fa-calendar"></i> Date range picker
                                                                                    </span>
                                                                                    <i class="fa fa-caret-down"></i>
                                                                                </button>


                                                                                <input type="hidden" name="reportType" value="5">
                                                                                <input type="hidden" id="start-totalAccumulatedReleaseReport2" name="start">
                                                                                <input type="hidden" id="end-totalAccumulatedReleaseReport2" name="end">
                                                                                <input type="hidden" value="<%=arb.getArbID()%>" name="arbID">
                                                                                <button type="submit" class="btn btn-default" onclick="form.action = 'ViewReport'">Generate Report</button>    



                                                                            </div>
                                                                        </div>
                                                                    </form>
                                                                </div>
                                                                <!-- /.modal-content -->
                                                            </div>
                                                            <!-- /.modal-dialog -->
                                                        </div>               
                                                    </div>
                                                    <!-- /.tab-content -->
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    
                                </div>
                                <!-- /.box-body -->
                            </div>
                        </div>

                    </div>

                    <!-- /.row -->

                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
        </div>
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>
        <script>

            $(document).ready(function () {
                var max_fields = 5; //maximum input boxes allowed
                var wrapper = $(".input_fields_wrap"); //Fields wrapper
                var add_button = $(".add_field_button"); //Add button ID
                var x = 0; //initlal text box count
                $(add_button).click(function (e) { //on add input button click
                    e.preventDefault();
                    if (x < max_fields) { //max input box allowed
                        x++; //text box increment
                        $('#count').val(x);
                        $(wrapper).append('<div class="row"><div class="col-xs-4"><div class="form-group"><label for="">Crop</label><select name="cropType" class="form-control select2" id=""><%for(Crop c : allCrops){%><option value="<%=c.getCropType()%>"><%out.print(c.getCropTypeDesc());%></option><%}%></select></div></div><div class="col-xs-4"><div class="form-group"><label for="">Start Date</label><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="startDate" class="form-control pull-right"></div></div></div><div class="col-xs-4"><div class="form-group"><label for="">End Date</label><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="endDate" class="form-control pull-right"></div></div></div></div>');
                        $('.select2').select2();
                    }
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

                $('#dr-totalAccumulatedReleaseReport2').daterangepicker(
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
                            $('#dr-totalAccumulatedReleaseReport2 span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                            $('#start-totalAccumulatedReleaseReport2').val(start.format('YYYY-MM-DD'));
                            $('#end-totalAccumulatedReleaseReport2').val(end.format('YYYY-MM-DD'));
                        }
                );

                $('#dr-totalAccumulatedReleaseReport3').daterangepicker(
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
                            $('#dr-totalAccumulatedReleaseReport3 span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                            $('#start-totalAccumulatedReleaseReport3').val(start.format('YYYY-MM-DD'));
                            $('#end-totalAccumulatedReleaseReport3').val(end.format('YYYY-MM-DD'));
                        }
                );


            });

            $(function () {
                $('#daterange-btn').daterangepicker(
                        {
                            ranges: {
                                'This Quarter': [moment().startOf('quarter'), moment().endOf('quarter')],
                                'Past Quarter': [moment().subtract(1, 'quarter').startOf('quarter'), moment().subtract(1, 'quarter').endOf('quarter')],
                            },

                        },
                        function (start, end) {
                            $('#daterange-btn span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                            $('#start').val(start.format('YYYY-MM-DD'));
                            $('#end').val(end.format('YYYY-MM-DD'));
                            $('#maxDate').val(end.add(1, 'month').endOf('month').format('YYYY-MM-DD'));
                            end.subtract(1, 'month').endOf('month').format('YYYY-MM-DD');
                        }
                );


            });
        </script>

        <script>
            $(function () {
                var ctx = $('#lineAPCPRating').get(0).getContext('2d');
                var ctx2 = $('#lineCAPDEV').get(0).getContext('2d');
                var ctx3 = $('#lineARB').get(0).getContext('2d');
            <%
                Chart chart = new Chart();
            %>
            <%
                String json = chart.getAPCPRating(apcpEvaluations);
                String json2 = chart.getCAPDEVRating(capdevEvaluations);
                String json3 = chart.getARBRating(arbEvaluations);
            %>
                new Chart(ctx, <%out.print(json);%>);
                new Chart(ctx2, <%out.print(json2);%>);
                new Chart(ctx3, <%out.print(json3);%>);
            });
        </script>

    </body>
</html>