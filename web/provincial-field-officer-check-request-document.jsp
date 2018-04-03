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
            <%int userType = (Integer) session.getAttribute("userType");%>
            <%if (userType == 3) {  %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%} else if (userType == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <%} else if (userType == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%} else if (userType == 2) {%>
            <%@include file="jspf/point-person-sidebar.jspf"%>
            <%}%>
            <%
                ARBODAO arboDAO = new ARBODAO();
                ARBDAO arbDAO = new ARBDAO();
                APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
                CAPDEVDAO capdevDAO = new CAPDEVDAO();
                APCPRequest r = apcpRequestDAO.getRequestByID((Integer)request.getAttribute("requestID"));
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
            %>
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
                                        <%@include file="jspf/arboInfo.jspf"%>

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
                                                        <input type="date" name="farmPlanDate" value="<%=r.getFarmPlanDate()%>" class="form-control" id="farmPlanDate" <%if (userType != 3) {out.print("disabled");}%>>
                                                        <%}else{%>
                                                        <input type="date" name="farmPlanDate" class="form-control" id="farmPlanDate" <%if (userType != 3) {out.print("disabled");}%>>
                                                        <%}%>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <%if (userType == 3) {  %>
                                                <label for="">&nbsp;</label>
                                                <button id="farmPlanButton" type="submit" onclick="form.action = 'SendFarmPlan'" class="form-control btn btn-primary"><i class="fa fa-send margin-r-5"></i>Submit</button>
                                                <%}%>
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
                                                        <input type="date" name="businessPlanDate" value="<%=r.getBusinessPlanDate()%>" class="form-control pull-right" id="businessPlanDate" <%if (userType != 3) {out.print("disabled");}%>>
                                                        <%}else{%>
                                                        <input type="date" name="businessPlanDate" class="form-control pull-right" id="businessPlanDate"<%if (userType != 3) {out.print("disabled");}%>>
                                                        <%}%>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <%if (userType == 3) {  %>
                                                <label for="">&nbsp;</label>
                                                <button id="businessPlanButton" type="submit" onclick="form.action = 'SendBusinessPlan'" class="form-control btn btn-primary" style="margin: 0 auto;"><i class="fa fa-send margin-r-5"></i>Submit</button>
                                                <%}%>
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
                                                        <input type="date" name="bankRequirementsDate" value="<%=r.getBankRequirementsDate()%>" class="form-control pull-right" id="bankRequirementsDate" <%if (userType != 3) {out.print("disabled");}%>>
                                                        <%}else{%>
                                                        <input type="date" name="bankRequirementsDate" class="form-control pull-right" id="bankRequirementsDate" <%if (userType != 3) {out.print("disabled");}%>>
                                                        <%}%>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <%if (userType == 3) {  %>
                                                <label for="">&nbsp;</label>
                                                <button id="bankRequirementsButton" type="submit" onclick="form.action = 'SendBankRequirements'" class="form-control btn btn-primary" style="margin: 0 auto;"><i class="fa fa-send margin-r-5"></i>Submit</button>
                                                <%}%>
                                            </div>
                                        </div>

                                    </div>
                                    <div class="box-footer">
                                        <%if (userType == 3) {  %>
                                        <input type="hidden" name="requestID" value="<%out.print(r.getRequestID());%>">
                                        <button type="submit" onclick="form.action = 'ClearAPCPRequest'" name="manual" class="btn btn-primary pull-right"><i class="fa fa-send margin-r-5"></i>Submit</button>
                                        <%}%>
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
