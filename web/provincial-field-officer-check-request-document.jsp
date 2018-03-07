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
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%
                APCPRequest r = apcpRequestDAO.getRequestByID((Integer)request.getAttribute("requestID"));
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
            %>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <%if (request.getAttribute("errMessage") != null) {%>
                    <p class="text text-center text-danger"><%=request.getAttribute("errMessage")%></p>
                    <%}%>
                    <%if (request.getAttribute("success") != null) {%>
                    <p class="text text-center text-success"><%=request.getAttribute("success")%></p>
                    <%}%>
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
                                                <div class="box-body">
                                                    <div class="row">
                                                        <div class="col-xs-4">
                                                            <div class="form-group" id="space" name="space">
                                                                <label for="space">Name of ARBO</label>
                                                                <input type="text" class="form-control" value="<%out.print(a.getArboName());%>" disabled>
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-3">
                                                            <label for="space">No. of Members</label>
                                                            <div class="input-group" id="space" name="space">
                                                                <input type="text" class="form-control" value="<%out.print(arboDAO.getARBCount(a.getArboID()));%>" disabled>
                                                                <span class="input-group-addon" data-toggle="modal" data-target="#arbs"><i class="fa  fa-users"></i>&nbsp; View ARBO Members</span>
                                                            </div>
                                                        </div>

                                                    </div>


                                                </div>

                                                <div class="modal fade" id="arbs">
                                                    <div class="modal-dialog modal-lg">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span></button>
                                                                <h4 class="modal-title">Agrarian Reform Beneficiaries</h4>
                                                            </div>


                                                            <div class="modal-body" id="modalBody">
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <table id="arbTable" class="table table-bordered table-striped">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>Full Name</th>
                                                                                    <th>Address</th>
                                                                                    <th>Crops</th>
                                                                                </tr>
                                                                            </thead>

                                                                            <tbody>
                                                                                <%
                                                                                    for(ARB arb : arbList){
                                                                                %>
                                                                                <tr>
                                                                                    <td><%out.print(arb.getFullName());%></td>
                                                                                    <td><%out.print(arb.getFullAddress());%></td>
                                                                                    <td><%out.print(arb.printAllCrops());%></td>
                                                                                </tr>
                                                                                <%}%>
                                                                            </tbody>

                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>Full Name</th>
                                                                                    <th>Address</th>
                                                                                    <th>Crops</th>
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
                                    <h3 class="box-title">Documentary Requirements</h3>
                                </div>
                                <form role="form" method="post">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <label for="">&nbsp;</label>
                                                <h4>Farm Plan</h4>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label>Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-calendar"></i>
                                                        </div>
                                                        <%if(r.getFarmPlanDate() != null){%>
                                                        <input type="date" name="farmPlanDate" value="<%=r.getFarmPlanDate()%>" class="form-control" id="farmPlanDate">
                                                        <%}else{%>
                                                        <input type="date" name="farmPlanDate" class="form-control" id="farmPlanDate">
                                                        <%}%>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <label for="">&nbsp;</label>
                                                <button id="farmPlanButton" type="submit" onclick="form.action = 'SendFarmPlan'" class="form-control btn btn-primary">Submit</button>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-4">
                                                <label for="">&nbsp;</label>
                                                <h4>Business Plan</h4>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label>Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-calendar"></i>
                                                        </div>
                                                        <%if(r.getBusinessPlanDate() != null){%>
                                                        <input type="date" name="businessPlanDate" value="<%=r.getBusinessPlanDate()%>" class="form-control pull-right" id="businessPlanDate">
                                                        <%}else{%>
                                                        <input type="date" name="businessPlanDate" class="form-control pull-right" id="businessPlanDate">
                                                        <%}%>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <label for="">&nbsp;</label>
                                                <button id="businessPlanButton" type="submit" onclick="form.action = 'SendBusinessPlan'" class="form-control btn btn-primary" style="margin: 0 auto;">Submit</button>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-4">
                                                <label for="">&nbsp;</label>
                                                <h4>Bank Requirements</h4>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label>Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-calendar"></i>
                                                        </div>
                                                        <%if(r.getBankRequirementsDate() != null){%>
                                                        <input type="date" name="bankRequirementsDate" value="<%=r.getBankRequirementsDate()%>" class="form-control pull-right" id="bankRequirementsDate">
                                                        <%}else{%>
                                                        <input type="date" name="bankRequirementsDate" class="form-control pull-right" id="bankRequirementsDate">
                                                        <%}%>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <label for="">&nbsp;</label>
                                                <button id="bankRequirementsButton" type="submit" onclick="form.action = 'SendBankRequirements'" class="form-control btn btn-primary" style="margin: 0 auto;">Submit</button>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="box-footer">
                                        <input type="hidden" name="requestID" value="<%out.print(r.getRequestID());%>">
                                        <button type="submit" onclick="form.action = 'ClearAPCPRequest'" name="manual" class="btn btn-primary pull-right">Submit</button>
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
                if ($('#farmPlanDate').val() !== null) {
                    $('#farmPlanButton').attr('disabled', 'disabled');
                }
                $('#farmPlanDate').on('change', function (e) {
                    $('#farmPlanButton').removeAttr('disabled');
                });

                if ($('#businessPlanDate').val() !== null) {
                    $('#businessPlanButton').attr('disabled', 'disabled');
                }
                $('#businessPlanDate').on('change', function (e) {
                    $('#businessPlanButton').removeAttr('disabled');
                });

                if ($('#bankRequirementsDate').val() !== null) {
                    $('#bankRequirementsButton').attr('disabled', 'disabled');
                }
                $('#bankRequirementsDate').on('change', function (e) {
                    $('#bankRequirementsButton').removeAttr('disabled');
                });
            });
        </script>

    </body>
</html>
