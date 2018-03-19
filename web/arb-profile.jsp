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

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%if ((Integer) session.getAttribute("userType") == 2) {%>
            <%@include file="jspf/point-person-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>

            <%
                ARB arb = (ARB) request.getAttribute("arb");
                APCPRequestDAO arDAO = new APCPRequestDAO();
                ARBODAO dao = new ARBODAO();
                ARBO arbo = dao.getARBOByID(arb.getArboID());
                ArrayList<Disbursement> disbursements = arDAO.getAllDisbursementsByARB(arb.getArbID());
                ArrayList<Repayment> repayments = arDAO.getAllRepaymentsByARB(arb.getArbID());
                
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        ARB Profile
                    </h1>

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
                        <div class="col-md-3">

                            <!-- Profile Image -->
                            <div class="box box-primary">
                                <div class="box-body box-profile">
                                    <h3 class="profile-username text-center"><%=arb.getFullName()%></h3>
                                    <div class="rate center-block">
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                    </div>
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
                                        <li class="list-group-item">
                                            <b>Member Since</b> <a class="pull-right"><%out.print(f.format(arb.getMemberSince()));%></a>
                                        </li>
                                        <li class="list-group-item">
                                            <b>Land Area</b> <a class="pull-right"><%out.print(arb.getLandArea());%> Hectares</a>
                                        </li>
                                        <li class="list-group-item">
                                            <%if (arb.getDependents().size() > 0) {%>
                                            <b>Dependents</b> <a class="pull-right" data-toggle="modal" data-target="#dependents"><%=arb.getDependents().size()%></a>
                                            <%} else {%>
                                            <b>Dependents</b> <a class="pull-right">N/A</a>
                                            <%}%>
                                        </li>
                                    </ul>
                                </div>
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
                                                                </tr>
                                                                <%}%>
                                                            </tbody>

                                                            <tfoot>
                                                                <tr>
                                                                    <th>Full Name</th>
                                                                    <th>Birthday</th>
                                                                    <th>Education Level</th>
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

                                <div class="modal fade" id="add-evaluation">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span></button>
                                                <h4 class="modal-title">Add Evaluation</h4>
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
                                                                <label>DTN</label>
                                                                <input type="text" name="dtn" class="form-control pull-right">
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-6">
                                                            <div class="form-group">
                                                                <label>Type</label>
                                                                <select name="type" class="form-control select2" style="width: 100%;">
                                                                    <option selected="selected">Select</option>
                                                                    <option value="1">ARB</option>
                                                                    <option value="2">APCP</option>
                                                                    <option value="3">CAPDEV</option>
                                                                    <option value="4">LINKSFARM</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </div>
                                                <div class="modal-footer">
                                                    <div class="pull-right">
                                                        <input type="hidden" id="start" name="start"><input type="hidden" id="end" name="end" >
                                                        <input type="hidden" id="maxDate" name="maxDate">
                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                        <button type="submit" class="btn btn-primary" onclick="form.action = 'AddEvaluation?id=<%out.print(arb.getArbID());%>'">Submit</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <!-- /.modal-content -->
                                    </div>
                                    <!-- /.modal-dialog -->
                                </div>
                            </div>
                            <!-- /.box -->

                            <!-- About Me Box -->
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                    <h3 class="box-title">About Me</h3>

                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <strong><i class="fa fa-book margin-r-5"></i> Educational Attainment</strong>

                                    <p class="text-muted">
                                        <%=arb.getEducationLevelDesc()%>
                                    </p>

                                    <hr>

                                    <strong><i class="fa fa-map-marker margin-r-5"></i> Address</strong>

                                    <p class="text-muted"><%=arb.getFullAddress()%></p>

                                    <hr>

                                    <strong><i class="fa fa-file-text-o margin-r-5"></i> Crops</strong>

                                    <p>
                                        <%for (Crop c : arb.getCurrentCrops()) {%>
                                        <span class="label label-success"><%=c.getCropTypeDesc()%></span>
                                        <%}%>
                                    </p>
                                    <a href="#" data-toggle="modal" data-target="#cropTimeline" class="text-center">View Crop Timeline</a>


                                    <div class="modal fade" id="cropTimeline">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span></button>
                                                    <h4 class="modal-title">Crop Timeline</h4>
                                                </div>

                                                <div class="modal-body" style="overflow-y: scroll; overflow-x: hidden;  max-height: 300px; ">
                                                    <ul class="timeline">
                                                        <%
                                                            boolean firstInstance99 = true;
                                                            Date date99 = null;
                                                        %>
                                                        <%for(Crop c : arb.getCrops()){%>

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
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                        <div class="col-md-9">
                            <!-- /.col -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Evaluations</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
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
                                                        <canvas id="lineCanvas" style="height:250px"></canvas>
                                                    </div>
                                                </div>

                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="capdev">
                                                <div class="box-body">
                                                    <div class="chart">
                                                        <canvas id="lineCanvas" style="height:250px"></canvas>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="overall">
                                                <div class="box-body">
                                                    <div class="chart">
                                                        <canvas id="lineCanvas" style="height:250px"></canvas>
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                        <!-- /.tab-content -->
                                    </div>
                                    <!-- /.nav-tabs-custom -->
                                    <%if ((Integer) session.getAttribute("userType") == 2) {%>
                                    <div class="box-footer">
                                        <button class="btn btn-primary pull-right" data-toggle="modal" data-target="#add-evaluation">Add Evaluation</button>
                                    </div>
                                    <%}%>
                                </div>
                            </div>

                            <!-- /.col -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">APCP/CAPDEV Visuals</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
                                <div class="box-body">
                                    <div class="nav-tabs-custom">
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#disbursement" data-toggle="tab">Disbursement</a></li>
                                            <li><a href="#repayment" data-toggle="tab">Repayments</a></li>
                                            <li><a href="#attendance" data-toggle="tab">Attendance</a></li>
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
                                                            
                                                        if(firstInstance){ // FIRST INSTANCE
                                                            date = db.getDateDisbursed();
                                                        }
                                                    %>

                                                    <%
                                                        if(date.compareTo(db.getDateDisbursed()) != 0){ // NEW DATE, change currDate
                                                            date = db.getDateDisbursed();
                                                            dateChanged = true;
                                                            System.out.print("Date changed!");
                                                        }
                                                    %>

                                                    <%if(firstInstance || dateChanged){%>
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
                                                            
                                                        if(firstInstance1){ // FIRST INSTANCE
                                                            date1 = rp.getDateRepayment();
                                                        }
                                                    %>

                                                    <%
                                                        if(date1.compareTo(rp.getDateRepayment()) != 0){ // NEW DATE, change currDate
                                                            date1 = rp.getDateRepayment();
                                                            dateChanged1 = true;
                                                            System.out.print("Date changed!");
                                                        }
                                                    %>

                                                    <%if(firstInstance1 || dateChanged1){%>
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
                                            <div class="tab-pane" id="attendance" >
                                                <!-- The timeline -->
                                                <div class="progress">
                                                    <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                                        80% Complete (success)
                                                    </div> 
                                                </div> 
                                                <ul class="timeline timeline-inverse">
                                                    <!-- timeline time label -->
                                                    <li class="time-label">
                                                        <span class="bg-red">
                                                            10 Feb. 2014
                                                        </span>
                                                    </li>
                                                    <!-- /.timeline-label -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-envelope bg-blue"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>

                                                            <h3 class="timeline-header"><a href="#">Support Team</a> sent you an email</h3>

                                                            <div class="timeline-body">
                                                                Etsy doostang zoodles disqus groupon greplin oooj voxy zoodles,
                                                                weebly ning heekya handango imeem plugg dopplr jibjab, movity
                                                                jajah plickers sifteo edmodo ifttt zimbra. Babblely odeo kaboodle
                                                                quora plaxo ideeli hulu weebly balihoo...
                                                            </div>
                                                            <div class="timeline-footer">
                                                                <a class="btn btn-primary btn-xs">Read more</a>
                                                                <a class="btn btn-danger btn-xs">Delete</a>
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-user bg-aqua"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>

                                                            <h3 class="timeline-header no-border"><a href="#">Sarah Young</a> accepted your friend request
                                                            </h3>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-comments bg-yellow"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>

                                                            <h3 class="timeline-header"><a href="#">Jay White</a> commented on your post</h3>

                                                            <div class="timeline-body">
                                                                Take me to your leader!
                                                                Switzerland is small and neutral!
                                                                We are more like Germany, ambitious and misunderstood!
                                                            </div>
                                                            <div class="timeline-footer">
                                                                <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <!-- timeline time label -->
                                                    <li class="time-label">
                                                        <span class="bg-green">
                                                            3 Jan. 2014
                                                        </span>
                                                    </li>
                                                    <!-- /.timeline-label -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-camera bg-purple"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 2 days ago</span>

                                                            <h3 class="timeline-header"><a href="#">Mina Lee</a> uploaded new photos</h3>

                                                            <div class="timeline-body">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <li>
                                                        <i class="fa fa-clock-o bg-gray"></i>
                                                    </li>
                                                </ul>
                                            </div>

                                        </div>
                                        <!-- /.tab-content -->
                                    </div>
                                    <!-- /.nav-tabs-custom -->

                                </div>
                            </div>
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">LINKSFARMS Visuals</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
                                <div class="box-body">
                                    <div class="nav-tabs-custom">
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#timeline" data-toggle="tab">Timeline</a></li>

                                        </ul>

                                        <div class="tab-content" style="overflow-y: scroll; overflow-x: hidden;  max-height: 300px; ">
                                            <div class="active tab-pane" id="timeline">
                                                <ul class="timeline timeline-inverse">
                                                    <!-- timeline time label -->
                                                    <li class="time-label">
                                                        <span class="bg-red">
                                                            INTERVENTION: RICE TRAINING 101
                                                        </span>
                                                    </li>
                                                    <!-- /.timeline-label -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-envelope bg-blue"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>

                                                            <h3 class="timeline-header"><a href="#">Support Team</a> sent you an email</h3>

                                                            <div class="timeline-body">
                                                                Etsy doostang zoodles disqus groupon greplin oooj voxy zoodles,
                                                                weebly ning heekya handango imeem plugg dopplr jibjab, movity
                                                                jajah plickers sifteo edmodo ifttt zimbra. Babblely odeo kaboodle
                                                                quora plaxo ideeli hulu weebly balihoo...
                                                            </div>
                                                            <div class="timeline-footer">
                                                                <a class="btn btn-primary btn-xs">Read more</a>
                                                                <a class="btn btn-danger btn-xs">Delete</a>
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-user bg-aqua"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>

                                                            <h3 class="timeline-header no-border"><a href="#">Sarah Young</a> accepted your friend request
                                                            </h3>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-comments bg-yellow"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>

                                                            <h3 class="timeline-header"><a href="#">Jay White</a> commented on your post</h3>

                                                            <div class="timeline-body">
                                                                Take me to your leader!
                                                                Switzerland is small and neutral!
                                                                We are more like Germany, ambitious and misunderstood!
                                                            </div>
                                                            <div class="timeline-footer">
                                                                <a class="btn btn-warning btn-flat btn-xs">View comment</a>
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <!-- timeline time label -->
                                                    <li class="time-label">
                                                        <span class="bg-green">
                                                            3 Jan. 2014
                                                        </span>
                                                    </li>
                                                    <!-- /.timeline-label -->
                                                    <!-- timeline item -->
                                                    <li>
                                                        <i class="fa fa-camera bg-purple"></i>

                                                        <div class="timeline-item">
                                                            <span class="time"><i class="fa fa-clock-o"></i> 2 days ago</span>

                                                            <h3 class="timeline-header"><a href="#">Mina Lee</a> uploaded new photos</h3>

                                                            <div class="timeline-body">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                                <img src="http://placehold.it/150x100" alt="..." class="margin">
                                                            </div>
                                                        </div>
                                                    </li>
                                                    <!-- END timeline item -->
                                                    <li>
                                                        <i class="fa fa-clock-o bg-gray"></i>
                                                    </li>
                                                </ul>
                                            </div>
                                            <!-- /.tab-pane -->

                                        </div>
                                        <!-- /.tab-content -->
                                    </div>
                                    <!-- /.nav-tabs-custom -->

                                </div>
                            </div>

                            <!-- /.col -->

                        </div>
                        <!-- /.col -->


                    </div>
                    <!-- /.row -->

                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            <footer class="main-footer">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 2.4.0
                </div>
                <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
                reserved.
            </footer>
        </div>
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>
        <script>
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
                            $('#maxDate').val(end.add(1, 'quarter').endOf('quarter').format('YYYY-MM-DD'));
                            end.subtract(1, 'quarter').endOf('quarter').format('YYYY-MM-DD');
                        }
                );


            });
        </script>

    </body>
</html>
