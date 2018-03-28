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
                Cluster c = (Cluster)request.getAttribute("cluster");
                ArrayList<CAPDEVActivity> categorizedActivities = capdevDAO.getCAPDEVActivitiesByCategory(3);
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

                            <!-- /.box -->
                            <div class="box">
                                <form role="form" method="post" action="SendLINKSFARMCAPDEVProposal">
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
                                        <input type="hidden" name="clusterID" value="<%=c.getClusterID()%>">
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
                    String json = bar.getBarChartEducation(c.getClusterMembers());
            %>
            new Chart(ctx, <%out.print(json);%>);

            var ctx3 = $('#pieCanvas').get(0).getContext('2d');
            <%
                    Chart pie = new Chart();
                    String json3 = pie.getPieChartGender(c.getClusterMembers());
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
                        $(wrapper).append('<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity activity : categorizedActivities){%><option value="<%out.print(activity.getActivityType());%>"><%out.print(activity.getActivityName());%></option><%}%></select></div></div><div class="col-xs-4"><div class="form-group"><label>Date</label><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="activityDates[]" class="form-control pull-right"></div></div></div><div class="col-xs-4"><div class="form-group"><label for="">Participants</label><input type="file" name="file[]" /></div></div></div>');
                        $('.select2').select2();
                    }
                });
            });
        </script>

    </body>
</html>
