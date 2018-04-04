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
            <%@include file="jspf/point-person-sidebar.jspf" %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>

                </section>

                <!-- Main content -->

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Pending CAPDEV Proposals</strong></h3>
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
                                                <th>ARBO Name</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(CAPDEVPlan p : assignedPlans){
                                                    APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="MonitorCAPDEVAttendance?planID="<%out.print(p.getPlanID());%>><%out.print(p.getPlanDTN());%></a></td>
                                                <td><a href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                            </tr>
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
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>With Past Due CAPDEV Plans</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example5" class="table table-bordered table-striped">
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
                                                for(CAPDEVPlan p : assignedPlansPastDue){
                                                    APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="MonitorCAPDEVAttendance?planID="<%out.print(p.getPlanID());%>><%out.print(p.getPlanDTN());%></a></td>
                                                <td><a href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                            </tr>
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
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Pre-Release Orientation</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example1" class="table table-bordered table-striped modTable">
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
                                                for(CAPDEVPlan p : assignedPROs){
                                                    APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="MonitorPreReleaseAttendance?planID=<%out.print(p.getPlanID());%>&requestID=<%out.print(r.getRequestID());%>"><%out.print(p.getPlanDTN());%></a></td>
                                                <td><a href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                            </tr>
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
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Assigned LINKSFARM CAPDEV Proposals</strong></h3>
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
                                                <th>Cluster Name</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(CAPDEVPlan p : assignedLINKSFARMPlans){
                                                    Cluster c = linksfarmDAO.getClusterByID(p.getClusterID());
                                            %>
                                            <tr>
                                                <td><a href="MonitorCAPDEVAttendance?planID=<%out.print(p.getPlanID());%>&clusterID=<%out.print(c.getClusterID());%>"><%out.print(p.getPlanDTN());%></a></td>
                                                <td><a href="ViewCluster?clusterID=<%=c.getClusterID()%>"><%=c.getClusterName()%></a></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                            </tr>
                                            <%}%>

                                        </tbody>

                                        <tfoot>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>Cluster Name</th>
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
        </div>

        <%@include file="jspf/footer.jspf" %>

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
