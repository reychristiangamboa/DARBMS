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
                
                int category = 0;
                
                if((Integer)request.getAttribute("pastDueID") != null){
                    category = 2;
                }else{
                    category = 1;
                }
                
                ArrayList<CAPDEVActivity> categorizedActivities = capdevDAO.getCAPDEVActivitiesByCategory(category);
            %>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">

                    <h1>
                        <strong><i class="fa fa-plus"></i> Create Capacity Development Plan</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
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
                                <form role="form" method="post" action="SendCAPDEVProposal">
                                    <div class="box-header with-border">
                                        <h3 class="box-title">Create CAPDEV Plan</h3>
                                        <div class="col-xs-3 pull-right">
                                            <input type="text" name="dtn" class="form-control" placeholder="CAPDEV Proposal DTN" required/>
                                        </div>
                                    </div>

                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <label for=""> &nbsp;</label>
                                                <button type="button" class="add_field_button btn btn-primary" id="addDependent"><i class="fa fa-user-plus"></i> Add Activity</button>
                                            </div>

                                        </div>
                                        <div class="input_fields_wrap" id="wrapper">


                                        </div>
                                    </div>
                                    <div class="box-footer">
                                        <input type="hidden" name="requestID" value="<%out.print(r.getRequestID());%>">
                                        <%if((Integer)request.getAttribute("pastDueID") != null){%>
                                        <input type="hidden" name="pastDueID" value="<%out.print((Integer)request.getAttribute("pastDueID"));%>">
                                        <%}%>
                                        <button type="submit" name="manual" class="btn btn-primary pull-right"><i class="fa fa-send margin-r-5"></i>Submit</button>
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
                        $(wrapper).append('<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity activity : categorizedActivities){%><option value="<%out.print(activity.getActivityType());%>"><%out.print(activity.getActivityName());%></option><%}%></select></div></div><div class="col-xs-4"><div class="form-group"><label>Date</label><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="activityDates[]" class="form-control pull-right"></div></div></div><div class="col-xs-4"><div class="form-group"><label for="">Participants</label><input type="file" name="file[]" /></div></div></div>');
                        $('.select2').select2();
                    }
                });
            });
        </script>

    </body>
</html>
