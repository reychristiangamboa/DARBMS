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
            <%
//            APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
//            ARBODAO arboDAO = new ARBODAO();
            
  int reqID = (Integer) request.getAttribute("planID");
 
  APCPRequest req = apcpRequestDAO.getRequestByID(reqID);
  ARBO arbo = arboDAO.getARBOByID(req.getArboID());
  UserDAO userDAO = new UserDAO();
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
                                    <h3 class="box-title">Collapsible Accordion</h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <div class="box-group" id="accordion">
                                        <div class="panel box box-success">
                                            <div class="box-header with-border">
                                                <h4 class="box-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                                        Add Participants
                                                    </a>
                                                </h4>
                                            </div>
                                            <div id="collapseOne" class="panel-collapse collapse in">
                                                <div class="box-body">
                                                    <form role="form" method="post" action="SendCAPDEVProposal">
                                                        <div class="box-body">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <table>
                                                                        <thead>
                                                                            <tr>
                                                                                <th>Category</th>
                                                                                <th>Type</th>
                                                                                <th>Date</th>
                                                                                <th>No. Participants</th>
                                                                                <th>Action</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <tr>
                                                                                <td></td>
                                                                                <td></td>
                                                                                <td></td>
                                                                                <td></td>
                                                                                <td> 
                                                                                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#modal-default">
                                                                                        Add Participants
                                                                                    </button>
                                                                                </td>
                                                                            </tr>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                            <div class="modal fade in" id="modal-default" style="display: block; padding-right: 17px;">
                                                                <div class="modal-dialog">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                <span aria-hidden="true">×</span></button>
                                                                            <h4 class="modal-title">Default Modal</h4>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <label>Participants</label>
                                                                            <table id="example3" class="table table-bordered table-striped">
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th>Action</th>
                                                                                        <th>Full Name</th> 
                                                                                        <th>Is Comat</th> 
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>
                                                                                    <%for(ARB arb : arbo.getArbList()){%>
                                                                                    <tr>
                                                                                        <td><input type="checkbox" name="arbID" value="<%out.print(arb.getArbID());%>"></td>
                                                                                        <td><%out.print(arb.getFLName());%></td>
                                                                                        <%if(arb.getIsCOMAT() > 0){%>
                                                                                        <td>&checkmark;</td>
                                                                                        <%}else{%>
                                                                                        <td>&nbsp;</td>
                                                                                        <%}%>
                                                                                    </tr>
                                                                                    <%}%>
                                                                                </tbody>
                                                                            </table>
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button type="Submit" class="btn btn-primary">Add</button>
                                                                        </div>
                                                                    </div>
                                                                    <!-- /.modal-content -->
                                                                </div>
                                                                <!-- /.modal-dialog -->
                                                            </div>
                                                        </div>
                                                        <div class="box-footer">
                                                            <button type="submit" name="manual" class="btn btn-primary pull-right"><i class="fa fa-send margin-r-5"></i>Submit</button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>

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
        <%@include file="/jspf/footer.jspf" %>
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
                        $(wrapper).append('<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity activity : categorizedActivities){%><option value="<%out.print(activity.getActivityType());%>"><%out.print(activity.getActivityName());%></option><%}%></select></div></div><div class="col-xs-4"><div class="form-group"><label>Date</label><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="activityDates[]" class="form-control pull-right"></div></div></div><div class="col-xs-4"><div class="form-group"><label for="">Participants</label><input type="file" name="file[]" /></div></div></div>');
                        $('.select2').select2();
                    }
                });
            });
        </script>

    </body>
</html>
