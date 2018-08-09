

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
                int requestID = (Integer) request.getAttribute("requestID");
                int planID = (Integer) request.getAttribute("planID");
                CAPDEVPlan plan = capdevDAO.getCAPDEVPlan(planID);
                APCPRequest req = apcpRequestDAO.getRequestByID(requestID);
                req.setRecipients(apcpRequestDAO.getAllAPCPRecipientsByRequest(requestID));
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
                                                    <form role="form" method="post">
                                                        <div class="box-body">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <table class="table table-bordered table-striped modTable">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>Category</th>
                                                                                <th>Activity</th>
                                                                                <th>No. Participants</th>
                                                                                <th>Action</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <%
                                                                                plan.setActivities(capdevDAO.getCAPDEVPlanActivities(plan.getPlanID()));
                                                                                for(CAPDEVActivity act : plan.getActivities()){
                                                                                    act.setArbList(capdevDAO.getCAPDEVPlanActivityParticipants(act.getActivityID()));
                                                                            %>
                                                                            <tr>
                                                                                <td><%out.print(act.getActivityCategoryDesc());%></td>
                                                                                <td><%out.print(act.getActivityName());%></td>
                                                                                <td><%out.print(act.getArbList().size());%></td>
                                                                                <td> 
                                                                                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#activityModal<%out.print(act.getActivityID());%>">
                                                                                        Add Participants
                                                                                    </button>
                                                                                </td>
                                                                            </tr>
                                                                            <%}%>          
                                                                        </tbody>
                                                                    </table>

                                                                </div>
                                                            </div>

                                                            <%for(CAPDEVActivity act : plan.getActivities()){%>

                                                            <div class="modal fade" id="activityModal<%out.print(act.getActivityID());%>">
                                                                <div class="modal-dialog">
                                                                    <div class="modal-content">
                                                                        <div class="modal-header">
                                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                <span aria-hidden="true">×</span></button>
                                                                            <h4 class="modal-title">Add Participants</h4>
                                                                        </div>
                                                                        <div class="modal-body">
                                                                            <label>Participants</label>
                                                                            <div class="pull-right">
                                                                                <input type="checkbox" id="checkAll<%out.print(act.getActivityID());%>" />
                                                                                Check All
                                                                            </div>
                                                                            <table class="table table-bordered table-striped modTable">
                                                                                <thead>
                                                                                    <tr>
                                                                                        <th>Action</th>
                                                                                        <th>Full Name</th> 
                                                                                        <th>COMAT</th> 
                                                                                    </tr>
                                                                                </thead>
                                                                                <tbody>

                                                                                    <%for(ARB arb : req.getRecipients()){%>
                                                                                    <%if(act.getActivityCategory() == 2 || act.getActivityCategory() == 3){ // APCP ACTIVITY: COMATs ONLY %>
                                                                                    <tr>
                                                                                        <td><input type="checkbox" name="arbID" value="<%out.print(arb.getArbID());%>"></td>
                                                                                        <td><%out.print(arb.getFLName());%></td>
                                                                                        <%if(arb.getIsCOMAT() > 0){%>
                                                                                        <td>&checkmark;</td>
                                                                                        <%}else{%>
                                                                                        <td>&nbsp;</td>
                                                                                        <%}%>
                                                                                    </tr>
                                                                                    <%}else{ // RECIPIENTS%>
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
                                                                                    <%}%>
                                                                                </tbody>
                                                                            </table>
                                                                        </div>
                                                                        <div class="modal-footer">
                                                                            <button type="submit" onclick="form.action = 'AddActivityParticipants?activityID=<%out.print(act.getActivityID());%>&requestID=<%out.print(requestID);%>&planID=<%out.print(planID);%>'" class="btn btn-primary">Add</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <%}%>
                                                        </div>
                                                        <div class="box-footer">
                                                            <button type="submit" onclick="form.action = 'SendCAPDEVProposal'" class="btn btn-primary pull-right"><i class="fa fa-send margin-r-5"></i>Submit</button>
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
        <%@include file="jspf/footer.jspf" %>
        <script type="text/javascript">
            $(document).ready(function () {
            <%for(CAPDEVActivity act : plan.getActivities()){%>
                $('#checkAll<%out.print(act.getActivityID());%>').click(function () {
                    $('#activityModal<%out.print(act.getActivityID());%> input:checkbox').not(this).prop('checked', this.checked);
                });
            <%}%>
            });
        </script>

    </body>
</html>
