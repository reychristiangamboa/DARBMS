<%-- 
    Document   : provincial-field-officer-view-filtered-capdev-status
    Created on : Mar 21, 2018, 2:57:49 AM
    Author     : ijJPN
--%>

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
        <style>
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

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="field-officer-arbo-list.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    </ol>

                </section>

                <!-- Main content -->
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

                    <%
                        ArrayList<CAPDEVPlan> pendingPlans2 = (ArrayList)request.getAttribute("pending");
                        ArrayList<CAPDEVPlan> approvedPlans2 = (ArrayList)request.getAttribute("approved");
                        ArrayList<CAPDEVPlan> implementedPlans2 = (ArrayList)request.getAttribute("implemented");
                        ArrayList<APCPRequest> requestedRequests2 = (ArrayList)request.getAttribute("requested");
                    %>

                    <div class="row">
                        <div class="col-xs-6">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Filter</h3></div>
                                <form method="post" role="form">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="actName">Select All</label>
                                                    <input type="checkbox" id="filterBy" name="selectAll" value="Yes">
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="actName">Cities / Municipalities</label>
                                                    <select name="cities[]" id="cities" class="form-control select2" multiple="multiple">
                                                        <%for(CityMun city : cityMunList){%>
                                                        <option value="<%=city.getCityMunCode()%>"><%out.print(city.getCityMunDesc());%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="box-footer">
                                        <button type="submit" onclick="form.action = 'FilterCAPDEVRequests'" class="btn btn-success pull-right">Filter</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-3 col-xs-6" >
                            <!-- small box -->
                            <a href="#" name="btn1">
                                <div class="small-box bg-aqua">
                                    <div class="inner">
                                        <h3><%=requestedRequests2.size()%></h3>

                                        <h4>Requested</h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-keyboard-o"></i>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <div class="col-lg-3 col-xs-6" >
                            <!-- small box -->
                            <a href="#" name="btn4">
                                <div class="small-box bg-aqua">
                                    <div class="inner">
                                        <h3><%=pendingPlans2.size()%></h3>

                                        <h4>Pending CAPDEV Proposals</h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-hourglass-2"></i>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <a href="#" name="btn2">
                                <div class="small-box bg-green">
                                    <div class="inner">
                                        <h3><%=approvedPlans2.size()%><sup style="font-size: 20px"></sup></h3>

                                        <h4>Approved CAPDEV Proposals</h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa  fa-thumbs-up"></i>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <a href="#" name="btn3">
                                <div class="small-box bg-yellow">
                                    <div class="inner">
                                        <h3><%=implementedPlans2.size()%></h3>

                                        <h4>Implemented CAPDEV Proposals</h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-check-square-o"></i>
                                    </div>  
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <!-- ./col -->
                    </div>
                    <div class="row text-center">
                        <div class="col-xs-12">
                            <a name="all" href="#">Select All <i class="fa fa-chevron-down"></i></a>
                        </div>
                    </div>
                    <div class="row" id="1" style="display:none;">
                        <div class="col-xs-12" >
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Requested</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example1" class="table table-bordered table-striped">
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
                                                for(APCPRequest r : requestedRequests2){
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>

                                                <td><a href="CreateCAPDEVProposal?id=<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(r.getLoanReason());%></td>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(f.format(r.getDateRequested()));%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><%out.print(r.getRequestStatusDesc());%></td>
                                            </tr>
                                            <%}%>

                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                    <div class="row" id="2" style="display:none;">
                        <div class="col-xs-12" >
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Pending CAPDEV Proposals</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example5" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(CAPDEVPlan p : pendingPlans2){
                                                    APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="MonitorCAPDEVAttendance?id=<%out.print(p.getPlanID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(p.getPlanDTN());%></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                            </tr>
                                            <%}%>

                                        </tbody>

                                        <tfoot>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>        
                                            </tr>

                                        </tfoot>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>

                    <div class="row" id="3" style="display:none;">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Approved CAPDEV Proposals</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example6" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(CAPDEVPlan p : approvedPlans2){
                                                    APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="ProceedAssignPointPerson?planID=<%out.print(p.getPlanID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(p.getPlanDTN());%></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                            </tr>
                                            <%}%>

                                        </tbody>

                                        <tfoot>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>        
                                            </tr>

                                        </tfoot>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>

                    <div class="row" id="4" style="display:none;">
                        <div class="col-xs-12" >
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Implemented CAPDEV Proposals</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example3" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(CAPDEVPlan p : implementedPlans2){
                                                    APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="ReviewCAPDEVAssessment?planID=<%out.print(p.getPlanID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(p.getPlanDTN());%></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                            </tr>
                                            <%}%>

                                        </tbody>

                                        <tfoot>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>        
                                            </tr>

                                        </tfoot>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>

                    <!-- /.row -->
                </section>
                <!-- /.content -->

            </div>
            <!-- /.content-wrapper -->
        </div>
        <%@include file="jspf/footer.jspf" %>
        <script>




            $(document).ready(function () {

                var update_pizza = function () {
                    if ($("#filterBy").is(":checked")) {

                        $('#cities').prop('disabled', 'disabled');

                    } else {
                        $('#cities').prop('disabled', false);
                        $('#cities').val() = '';
                    }
                };

                $(update_pizza);
                $("#filterBy").change(update_pizza);

                $('#filterDrop').on('change', function (e) {
                    e.preventDefault();
                    $('#provinceDrop').removeAttr('disabled');
                    if ($('#filterDrop').val() === 1 && $('#provinceDrop').attr('disabled')) {
                        $('#provinceDrop').attr('disabled');
                    } else {
                        $('#provinceDrop').removeAttr('disabled');
                    }
                });

                $("a[name='all']").click(function () {
                    if ($("div[id='1']").css('display') == 'none') {
                        $("div[id='1']").toggle();
                    }
                    if ($("div[id='2']").css('display') == 'none') {
                        $("div[id='2']").toggle();
                    }
                    if ($("div[id='3']").css('display') == 'none') {
                        $("div[id='3']").toggle();
                    }
                    if ($("div[id='4']").css('display') == 'none') {
                        $("div[id='4']").toggle();
                    }
                });
                $("a[name='btn1']").click(function () {
                    $("div[id='1']").toggle();
                    if ($("div[id='2']").css('display') != 'none') {
                        $("div[id='2']").toggle();
                    }
                    if ($("div[id='3']").css('display') != 'none') {
                        $("div[id='3']").toggle();
                    }
                    if ($("div[id='4']").css('display') != 'none') {
                        $("div[id='4']").toggle();
                    }
                    if ($("div[id='5']").css('display') != 'none') {
                        $("div[id='5']").toggle();
                    }
                    if ($("div[id='6']").css('display') != 'none') {
                        $("div[id='6']").toggle();
                    }
                });
                $("a[name='btn2']").click(function () {
                    $("div[id='3']").toggle();
                    if ($("div[id='1']").css('display') != 'none') {
                        $("div[id='1']").toggle();
                    }
                    if ($("div[id='2']").css('display') != 'none') {
                        $("div[id='2']").toggle();
                    }
                    if ($("div[id='4']").css('display') != 'none') {
                        $("div[id='4']").toggle();
                    }
                    if ($("div[id='5']").css('display') != 'none') {
                        $("div[id='5']").toggle();
                    }
                    if ($("div[id='6']").css('display') != 'none') {
                        $("div[id='6']").toggle();
                    }
                });
                $("a[name='btn3']").click(function () {
                    $("div[id='4']").toggle();
                    if ($("div[id='1']").css('display') != 'none') {
                        $("div[id='1']").toggle();
                    }
                    if ($("div[id='2']").css('display') != 'none') {
                        $("div[id='2']").toggle();
                    }
                    if ($("div[id='3']").css('display') != 'none') {
                        $("div[id='3']").toggle();
                    }
                    if ($("div[id='5']").css('display') != 'none') {
                        $("div[id='5']").toggle();
                    }
                    if ($("div[id='6']").css('display') != 'none') {
                        $("div[id='6']").toggle();
                    }
                });
                $("a[name='btn4']").click(function () {
                    $("div[id='2']").toggle();
                    if ($("div[id='1']").css('display') != 'none') {
                        $("div[id='1']").toggle();
                    }
                    if ($("div[id='4']").css('display') != 'none') {
                        $("div[id='4']").toggle();
                    }
                    if ($("div[id='3']").css('display') != 'none') {
                        $("div[id='3']").toggle();
                    }
                    if ($("div[id='5']").css('display') != 'none') {
                        $("div[id='5']").toggle();
                    }
                    if ($("div[id='6']").css('display') != 'none') {
                        $("div[id='6']").toggle();
                    }
                });
            });
        </script>
    </body>
</html>
