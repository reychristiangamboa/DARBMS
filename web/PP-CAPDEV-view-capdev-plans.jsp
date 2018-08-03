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
            @media screen and (min-width: 992px) {
                .modal-lg {
                    width: 1080px; /* New width for large modal */
                }
            }
        </style>
    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pp-capdev-sidebar.jspf" %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-file-o"></i> View Capacity Development Plans</strong>
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>
                </section>

                <%
                ArrayList<CAPDEVPlan> reasons = capdevDAO.getAllPostponeReasons();
                %>

                <!-- Main content -->
                <section class="content">
                    
                    <%if(issueDAO.retrieveUnresolvedIssues((Integer)session.getAttribute("userType"),(Integer)session.getAttribute("provOfficeCode")).size() > 0){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> You have <a href="view-issues.jsp">ISSUES</a> pending. Please resolve them immediately.</h4>
                    </div>
                    <%}%>

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
                                <div class="box-header">
                                    <h3 class="box-title"><strong>CALENDAR</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>
                                </div>
                                <div class="box-body">
                                    <div id="calendar"></div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Assigned CAPDEV Proposals</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table class="table table-bordered table-striped modTable">
                                        <thead>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>Plan Date</th>
                                                <th>ARBO</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(CAPDEVPlan p : assignedPlans){
                                                    p.setActivities(capdevDAO.getCAPDEVPlanActivities(p.getPlanID()));
                                                    APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><%out.print(p.getPlanDTN());%></td>
                                                <td><%out.print(p.getPlanDate());%></td>
                                                <td><a target="_blank" rel="noopener noreferrer" href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                                <td>
                                                    <a href="MonitorCAPDEVAttendance?planID=<%out.print(p.getPlanID());%>" class="btn btn-success">ASSESS</a>
                                                    <%if(p.isPlanForReschedule()){%>
                                                    <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#postponeModal<%out.print(p.getPlanID());%>">RESCHEDULE</button>
                                                    <%}else{%>
                                                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#postponeModal<%out.print(p.getPlanID());%>">POSTPONE</button>
                                                    <%}%>
                                                </td>

                                            </tr>

                                        <div class="modal fade" id="postponeModal<%out.print(p.getPlanID());%>">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">×</span>
                                                        </button>
                                                        <h4 class="modal-title">Confirm Postpone/Reschedule</h4>
                                                    </div>
                                                    <form method="post">
                                                        <div class="modal-body">
                                                            <div class="form-group">
                                                                <label for="">Reason: </label>
                                                                <select name="postponeReason" id="" class="form-control">
                                                                    <%for(CAPDEVPlan reason : reasons){%>
                                                                    <option value="<%out.print(reason.getPostponeReason());%>"><%out.print(reason.getPostponeReasonDesc());%></option>
                                                                    <%}%>
                                                                </select>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for=""></label>
                                                                <textarea name="reason" id="" cols="3" rows="2" class="form-control"></textarea>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <a href="PostponePlan?planID=<%out.print(p.getPlanID());%>" class="btn btn-danger">Postpone</a>
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
                                                <th>ARBO</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                                <th>Action</th>      
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
                </section>
                <!-- /.content -->
            </div>
        </div>

        <%@include file="jspf/footer.jspf" %>

        <script type="text/javascript">
//            $(document).ready(function () {
//                $('#calendar').fullCalendar({
//                    theme: true,
//                    editable: false,
//                    defaultView: 'month',
//                    events: '/AssignedPlans'
//                });
//            });

            $(document).ready(function () {

                $.ajax({
                    url: 'AssignedPlans',
                    dataType: "json",
                    success: function (response) {
                        $('#calendar').fullCalendar({
                            header: {
                                left: 'prev,next today',
                                center: 'title',
                                right: 'month,agendaWeek,agendaDay'
                            },
                            editable: true,
                            axisFormat: 'H:mmtt',
                            slotMinutes: 10,
                            firstHour: 8,
                            minTime: 8,
                            maxTime: 22,
                            // use "[response]" if only one event
                            events: response
                        });
                    }
                });
            });
        </script>
        <script type="text/javascript">
            function chg() {
                var arboID = $('input[name=arboID]:checked').val();
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('arboName').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "ARBONameRefresh?valajax=" + arboID, true);
                xhttp.send();
            }
        </script>
    </body>
</html>
