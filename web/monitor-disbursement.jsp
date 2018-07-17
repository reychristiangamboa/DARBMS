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
            <%@include file="jspf/pp-apcp-sidebar.jspf"%>
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
                
                ArrayList<CAPDEVActivity> activities = capdevDAO99.getCAPDEVActivities();
                APCPRequest r = apcpRequestDAO99.getRequestByID((Integer)request.getAttribute("requestID"));
                ARBO a = arboDAO99.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO99.getAllARBsARBO(r.getArboID());
                
                APCPRelease release = apcpRequestDAO99.getAPCPReleaseByID((Integer)request.getAttribute("releaseID"));
                
        int reqID = (Integer) request.getAttribute("requestID");
        APCPRequest req = apcpRequestDAO99.getRequestByID(reqID);
        ARBO arbo = arboDAO99.getARBOByID(req.getArboID());
        UserDAO userDAO = new UserDAO();
            %>                                        
            <% User u1 = new User(); %>
            <% User u2 = new User(); %>
            <% User u3 = new User(); %>
            <% User u4 = new User(); %>
            %>

            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">

                    <h1>
                        <i class="fa fa-money"></i> Monitor Disbursements
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="MonitorRelease?id=<%out.print(r.getRequestID());%>"><i class="fa fa-caret-left"></i> Go Back</a></li>
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
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box-group" id="accordion">
                                <!-- we are adding the .panel class so bootstrap.js collapse plugin detects it -->
                                <div class="panel box box-info">
                                    <div class="box-header with-border">
                                        <h4 class="box-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour">
                                                ARBO Information
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseFour" class="panel-collapse collapse">
                                        <div class="box-body">
                                            <%@include file="/jspf/arbo-information.jspf" %>
                                        </div>
                                    </div> 
                                </div>
                                <div class="panel box box-info">
                                    <div class="box-header with-border">
                                        <h4 class="box-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseFive">
                                                APCP Request Information
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseFive" class="panel-collapse collapse">
                                        <div class="box-body">
                                            <%@include file="/jspf/apcp-request-info.jspf" %>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel box box-info">
                                    <div class="box-header with-border">
                                        <h4 class="box-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                                Disbursement Information
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseTwo" class="panel-collapse collapse in">
                                        <div class="box-body">
                                            <%@include file="jspf/disbursement-info.jspf" %>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <!-- /.col -->
                    </div>

                    <div class="modal fade" id="add-disbursement-modal">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Record Disbursement</h4>
                                </div>

                                <form method="post">
                                    <div class="modal-body" id="modalBody">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <table class="table table-bordered table-striped modTable">
                                                    <thead>
                                                        <tr>
                                                            <th>Action</th>
                                                            <th>ARB</th>
                                                            <th>Address</th>
                                                            <th>Crops</th>
                                                        </tr>
                                                    </thead>

                                                    <tbody>
                                                        <%for(ARB arb : arbList){%>
                                                        <tr>
                                                            <td>
                                                                <input type="checkbox" name="arbID" value="<%=arb.getArbID()%>"/>
                                                            </td>
                                                            <td><%out.print(arb.getFLName());%></td>
                                                            <td><%out.print(arb.getFullAddress());%></td>
                                                            <td><%out.print(arb.printAllCrops());%></td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>

                                                </table>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">Disbursement Amount</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i>&#8369;</i>
                                                        </div>
                                                        <input type="text" id="disbursementAmount" class="form-control numberOnly" name="disbursementAmount" autocomplete="off" required>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">O/S Balance</label>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i>&#8369;</i>
                                                        </div>
                                                        <input type="text" id="disbursementOSBalance" class="form-control numberOnly" name="OSBalance" autocomplete="off" required disabled>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">Disbursement Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                                        <input type="date" class="form-control" name="disbursedDate" />        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <input type="hidden" name="releaseID" value="<%=release.getReleaseID()%>">
                                        <button type="submit" name="manual" onclick="form.action = 'RecordDisbursement'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>

                            </div>
                            <!--                                            /.modal-content -->
                        </div>
                        <!--                                        /.modal-dialog -->
                    </div>

                    <div class="modal fade" id="import-disbursements-modal">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Import Disbursements</h4>
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
                                        <input type="hidden" name="releaseID" value="<%=release.getReleaseID()%>">
                                        <button type="submit" name="manual" onclick="form.action = 'ImportDisbursement'" class="btn btn-primary pull-right">Submit</button>
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
            $(document).ready(function () {
                var interestRate = <%out.print(r.getLoanReason().getLoanTerm().getArbInterestRate());%>

                $('#disbursementAmount').on('input', function () {
                    var val = this.value * interestRate;
                    $('#disbursementOSBalance').val(val);
                });


            });
        </script>

    </body>
</html>
