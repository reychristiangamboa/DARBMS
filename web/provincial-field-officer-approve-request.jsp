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
                APCPRequest r = apcpRequestDAO.getRequestByID(1);
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

                                         <%@include file="jspf/arboInfo.jspf"%>
                                    </div>
                                    <hr>        
                                </div>
                                <!-- /.box-body -->

                            </div>
                            <!-- /.box -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Approval Date</h3>
                                </div>
                                <form action="action">
                                    <div class="box-body">
                                        <div class="input-group date">
                                            <div class="input-group-addon">
                                                <i class="fa fa-calendar"></i>
                                            </div>
                                            <input type="date" name="approveDate" class="form-control pull-right" id="datepicker">
                                        </div>
                                    </div>
                                    <div class="box-footer">
                                        <input type="hidden" name="requestID" value="<%out.print(r.getRequestID());%>">
                                        <input type="hidden" id="count" name="count">
                                        <button type="submit" onclick="form.action='ApproveAPCPRequest'" class="btn btn-primary pull-right">Approve</button>
                                    </div>
                                </form>

                            </div>

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
        var max_fields = 9; //maximum input boxes allowed
        var wrapper = $(".input_fields_wrap"); //Fields wrapper
        var add_button = $(".add_field_button"); //Add button ID
        var x = 0; //initlal text box count
        $(add_button).click(function (e) { //on add input button click
            e.preventDefault();
            if (x < max_fields) { //max input box allowed
                x++; //text box increment
                $('#count').val(x);
                $(wrapper).append('<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity activity : activities){%><option value="<%out.print(activity.getActivityID());%>"><%out.print(activity.getActivityName());%></option><%}%></select></div></div><div class="col-xs-4"><div class="form-group"><label>Date</label><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="activityDates[]" class="form-control pull-right" id="datepicker"></div></div></div><div class="col-xs-4"><div class="form-group"><label for="">No. of Participants</label><select name="noOfParticipants[]' + x + '" class="form-control select2" multiple="multiple" required><%for(ARB arb : arbList){%><option value="<%out.print(arb.getArbID());%>"><%out.print(arb.getFLName());%></option><%}%></select></div></div></div>');
                $('.select2').select2();
            }
        });



    });
</script>

</body>
</html>
