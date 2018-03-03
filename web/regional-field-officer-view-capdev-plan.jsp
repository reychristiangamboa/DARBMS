<%-- 
    Document   : regional-field-officer-view-capdev-activity
    Created on : Feb 24, 2018, 11:07:24 PM
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

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/regional-field-officer-sidebar.jspf" %>

            <%
                APCPRequest r = apcpRequestDAO.getRequestByID((Integer)request.getAttribute("requestID"));
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
                ArrayList<CAPDEVActivity> caList = capdevDAO.getCAPDEVPlanActivities((Integer)request.getAttribute("planID"));
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
                        <li><a href="field-officer-arbo-list.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    </ol>

                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">ARBO Information</h3>
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

                                        <div class="tab-content no-padding">
                                            <!-- Morris chart - Sales -->
                                            <div class="chart tab-pane active" id="request" style="position: relative;">
                                                <div class="box-body">

                                                    <div class="row">
                                                        <div class="col-xs-6">
                                                            <div class="form-group">
                                                                <label for="">Name of ARBO</label>
                                                                <input type="text" class="form-control" value="<%out.print(a.getArboName());%>" disabled >
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-3">
                                                            <div class="form-group">
                                                                <label for="">No. of ARBs</label>
                                                                <input type="text" class="form-control" id="" placeholder="" value="<%out.print(arboDAO.getARBCount(a.getArboID()));%>" disabled>
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-3">
                                                            <div class="form-group">
                                                                <label for="">Land Area (Hectares)</label>
                                                                <input type="text" class="form-control" id="" value="<%out.print(r.getHectares());%>" disabled >
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-xs-4">
                                                            <label for=''>Loan Amount</label>
                                                            <input type='text' class="form-control" id='' value="<%out.print(r.getLoanAmount());%>" disabled>
                                                        </div>

                                                        <div class="col-xs-4">
                                                            <div class="form-group">
                                                                <label for="">Reason for Loan</label>
                                                                <input type="text" class="form-control" value="<%out.print(r.getLoanReason());%>" disabled/>
                                                            </div>
                                                        </div>         
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-xs-12">
                                                            <label for=''>Remarks</label>
                                                            <textarea class="form-control" rows="3" disabled><%out.print(r.getRemarks());%></textarea>
                                                        </div>

                                                    </div>
                                                </div> 
                                            </div>
                                            <div class="chart tab-pane" id="info" style="position: relative;">


                                            </div>
                                            <div class="chart tab-pane" id="history" style="position: relative;">


                                            </div>
                                        </div>
                                    </div>
                                    <hr>        
                                </div>
                                <!-- /.box-body -->



                            </div>
                            <!-- /.box -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">CAPDEV Plan</h3>
                                </div>
                                <div class="box-body">
                                    <table class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Activity</th>
                                                <th>Date</th>
                                                <th>No. of Participants</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%for(CAPDEVActivity activity : p.getActivities()){%>
                                            <tr>
                                                <td><a data-toggle="modal" data-target="#cleared-modal"></a><%out.print(activity.getActivityName());%></td>
                                                <td><%out.print(f.format(activity.getActivityDate()));%></td>
                                                <td><%out.print(activity.getArbList().size());%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>

                                    </table>   
                                </div>
                                <form method="post">
                                    <div class="box-footer">
                                        <input type="hidden" name="requestID" value="<%out.print(r.getRequestID());%>">
                                        <input type="hidden" name="planID" value="<%out.print((Integer)request.getAttribute("planID"));%>">
                                        <div class="btn-group pull-right">
                                            <button type="submit" name="disapprove" onclick="form.action = 'DisapproveCAPDEVProposal'" class="btn btn-danger">Disapprove</button>
                                            <button type="submit" name="approve" onclick="form.action = 'ApproveCAPDEVProposal'" class="btn btn-success">Approve</button>
                                        </div>
                                    </div>
                                </form>

                            </div>
                        </div>
                        <!-- /.col -->
                    </div>
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
        <%@include file="jspf/footer.jspf" %>
        <script>
            $(document).ready(function () {

            });
        </script>
    </body>
</html>
