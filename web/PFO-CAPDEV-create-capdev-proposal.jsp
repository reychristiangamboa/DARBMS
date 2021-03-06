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
            <%@include file="jspf/pfo-capdev-sidebar.jspf"%>
            <%
  int reqID = (Integer) request.getAttribute("requestID");
  APCPRequest req = apcpRequestDAO.getRequestByID(reqID);
  ARBO arbo = arboDAO.getARBOByID(req.getArboID());
  System.out.print(arbo);
  
  UserDAO userDAO = new UserDAO();
  ArrayList<CAPDEVActivity> categories = capdevDAO.getCAPDEVCategories();
  ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(req.getArboID());
  
  ArrayList<CAPDEVActivity> activity = capdevDAO.getCAPDEVActivitiesByCategory(1);
  ArrayList<CAPDEVActivity> activity2 = capdevDAO.getCAPDEVActivitiesByCategory(2);
  ArrayList<CAPDEVActivity> activity3 = capdevDAO.getCAPDEVActivitiesByCategory(3);
  ArrayList<CAPDEVActivity> activity4 = capdevDAO.getCAPDEVActivitiesByCategory(4);
  ArrayList<CAPDEVActivity> activity5 = capdevDAO.getCAPDEVActivitiesByCategory(5);
  ArrayList<CAPDEVActivity> activity6 = capdevDAO.getCAPDEVActivitiesByCategory(6);
  ArrayList<CAPDEVActivity> activity7 = capdevDAO.getCAPDEVActivitiesByCategory(7);
  ArrayList<CAPDEVActivity> activity8 = capdevDAO.getCAPDEVActivitiesByCategory(8);
  ArrayList<CAPDEVActivity> activity9 = capdevDAO.getCAPDEVActivitiesByCategory(9);
  ArrayList<CAPDEVActivity> activity10 = capdevDAO.getCAPDEVActivitiesByCategory(10);
  ArrayList<CAPDEVActivity> allActivities = capdevDAO.getCAPDEVActivities();
            %> 
            <% User u1 = new User(); %>
            <% User u2 = new User(); %>
            <% User u3 = new User(); %>
            <% User u4 = new User(); %>
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
                            <div class="box box-solid">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><%=req.getRequestStatusDesc()%></h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <div class="box-group" id="accordion">
                                        <div class="panel box box-info">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                                        APCP Request Information
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseTwo" class="panel-collapse collapse">
                                                <div class="box-body">
                                                    <%@include file="jspf/apcp-request-info.jspf" %>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel box box-primary">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                                        ARBO Information
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseThree" class="panel-collapse collapse">
                                                <div class="box-body">
                                                    <%@include file="jspf/arbo-information.jspf" %>
                                                </div>
                                            </div>
                                        </div>

                                        <%@include file="jspf/create-capdev.jspf" %>
                                    </div>
                                </div>
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
        <script type="text/javascript">

            $(document).ready(function () {

                var max_fields = 9; //maximum input boxes allowed
                var wrapper = $('.input_fields_wrap'); //Fields wrapper
                var x = 0; //initlal text box count
                $('#optional-list').on("click", ".optional-document", function (e) {
                    e.preventDefault();
                    var documentID = parseInt($(this).attr('data-documentID'));
                    var documentDesc = $(this).attr('data-documentDesc');
                    var markup = "";

                    if (documentID === 2) {
                        markup = '<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Category">Category</label><input type="hidden" name="category" value="' + documentID + '"><input type="text" class="form-control" value="' + documentDesc + '" disabled/></div></div><div class="col-xs-6"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity act : activity2){%><option value="<%out.print(act.getActivityType());%>"><%out.print(act.getActivityName());%></option><%}%></select></div></div><div class="col-xs-2"><button type="button" class="delete-row-activity btn btn-danger"><i class="fa fa-close"></i></button></div></div>';
                    } else if (documentID === 3) {
                        markup = '<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Category">Category</label><input type="hidden" name="category" value="' + documentID + '"><input type="text" class="form-control" value="' + documentDesc + '" disabled/></div></div><div class="col-xs-6"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity act : activity3){%><option value="<%out.print(act.getActivityType());%>"><%out.print(act.getActivityName());%></option><%}%></select></div></div><div class="col-xs-2"><button type="button" class="delete-row-activity btn btn-danger"><i class="fa fa-close"></i></button></div></div>';
                    } else if (documentID === 4) {
                        markup = '<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Category">Category</label><input type="hidden" name="category" value="' + documentID + '"><input type="text" class="form-control" value="' + documentDesc + '" disabled/></div></div><div class="col-xs-6"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity act : activity4){%><option value="<%out.print(act.getActivityType());%>"><%out.print(act.getActivityName());%></option><%}%></select></div></div><div class="col-xs-2"><button type="button" class="delete-row-activity btn btn-danger"><i class="fa fa-close"></i></button></div></div>';
                    } else if (documentID === 5) {
                        markup = '<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Category">Category</label><input type="hidden" name="category" value="' + documentID + '"><input type="text" class="form-control" value="' + documentDesc + '" disabled/></div></div><div class="col-xs-6"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity act : activity5){%><option value="<%out.print(act.getActivityType());%>"><%out.print(act.getActivityName());%></option><%}%></select></div></div><div class="col-xs-2"><button type="button" class="delete-row-activity btn btn-danger"><i class="fa fa-close"></i></button></div></div>';
                    } else if (documentID === 6) {
                        markup = '<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Category">Category</label><input type="hidden" name="category" value="' + documentID + '"><input type="text" class="form-control" value="' + documentDesc + '" disabled/></div></div><div class="col-xs-6"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity act : activity6){%><option value="<%out.print(act.getActivityType());%>"><%out.print(act.getActivityName());%></option><%}%></select></div></div><div class="col-xs-2"><button type="button" class="delete-row-activity btn btn-danger"><i class="fa fa-close"></i></button></div></div>';
                    } else if (documentID === 7) {
                        markup = '<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Category">Category</label><input type="hidden" name="category" value="' + documentID + '"><input type="text" class="form-control" value="' + documentDesc + '" disabled/></div></div><div class="col-xs-6"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity act : activity7){%><option value="<%out.print(act.getActivityType());%>"><%out.print(act.getActivityName());%></option><%}%></select></div></div><div class="col-xs-2"><button type="button" class="delete-row-activity btn btn-danger"><i class="fa fa-close"></i></button></div></div>';
                    } else if (documentID === 8) {
                        markup = '<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Category">Category</label><input type="hidden" name="category" value="' + documentID + '"><input type="text" class="form-control" value="' + documentDesc + '" disabled/></div></div><div class="col-xs-6"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity act : activity8){%><option value="<%out.print(act.getActivityType());%>"><%out.print(act.getActivityName());%></option><%}%></select></div></div><div class="col-xs-2"><button type="button" class="delete-row-activity btn btn-danger"><i class="fa fa-close"></i></button></div></div>';
                    } else if (documentID === 9) {
                        markup = '<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Category">Category</label><input type="hidden" name="category" value="' + documentID + '"><input type="text" class="form-control" value="' + documentDesc + '" disabled/></div></div><div class="col-xs-6"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity act : activity9){%><option value="<%out.print(act.getActivityType());%>"><%out.print(act.getActivityName());%></option><%}%></select></div></div><div class="col-xs-2"><button type="button" class="delete-row-activity btn btn-danger"><i class="fa fa-close"></i></button></div></div>';
                    } else if (documentID === 10) {
                        markup = '<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Category">Category</label><input type="hidden" name="category" value="' + documentID + '"><input type="text" class="form-control" value="' + documentDesc + '" disabled/></div></div><div class="col-xs-6"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity act : activity10){%><option value="<%out.print(act.getActivityType());%>"><%out.print(act.getActivityName());%></option><%}%></select></div></div><div class="col-xs-2"><button type="button" class="delete-row-activity btn btn-danger"><i class="fa fa-close"></i></button></div></div>';
                    }

                    if (x < max_fields) {
                        $(wrapper).append(markup);
                    }
                });

                wrapper.on("click", ".delete-row-activity", function (e) {
                    e.preventDefault();
                    $(this).closest("div .row").remove();
                    x--;
                });

            });
        </script>
        <script type="text/javascript">
            $(document).ready(function () {

                $.ajax({
                    url: 'ARBOPlans?id=<%out.print(arbo.getArboID());%>',
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
                            dayClick: function(date,jsEvent,view){
                                document.getElementById('planDate').value = date.format();
                                //$(this).css('background-color','red');
                            },
                            events: response
                        });
                    }
                });
            });
        </script>

    </body>
</html>
