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
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>

            <%
                APCPRequest r = apcpRequestDAO.getRequestByID((Integer) request.getAttribute("requestID"));
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
                ArrayList<CAPDEVActivity> caList = capdevDAO.getCAPDEVPlanActivities((Integer) request.getAttribute("planID"));
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-file-o"></i> View Capacity Development Plan</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>
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
                            </div>

                            <!-- /.box -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">CAPDEV Plan</h3>
                                </div>
                                <form method="post">
                                    <div class="box-body">
                                        
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="pointPerson">Assign Point Person</label>
                                                    <select name="pointPersonID" id="pointPerson" class="form-control select2">
                                                        <%for(User u : pointPersons){%>
                                                        <option value="<%=u.getUserID()%>"><%out.print(u.getFullName());%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                                    
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <table class="table table-bordered table-striped modTable">
                                                    <thead>
                                                        <tr>
                                                            <th>Activity</th>
                                                            <th>Date</th>
                                                            <th>No. of Participants</th>
                                                        </tr>
                                                    </thead>

                                                    <tbody>
                                                        <%for (CAPDEVActivity activity : caList) {%>
                                                        <tr>
                                                            <td><%out.print(activity.getActivityName());%></td>
                                                            <td><%out.print(activity.getActivityDate());%></td>
                                                            <td><%out.print(activity.getArbList().size());%></td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>

                                                </table>   
                                            </div>
                                        </div>

                                    </div>

                                    <div class="box-footer">
                                        <div class="btn-group pull-right">
                                            <button type="button" name="disapprove" class="btn btn-danger" data-toggle="modal" data-target="#disapprove">Disapprove</button>
                                            <button type="button" name="approve" class="btn btn-success" data-toggle="modal" data-target="#approve">Assign</button>
                                        </div>
                                    </div>

                                    <div class="modal fade" id="approve">
                                        <div class="modal-dialog modal-sm">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                    <h4 class="modal-title">Confirm Assignment</h4>

                                                </div>
                                                <div class="modal-body" id="modalBody">
                                                    <div class="row">
                                                        <div class="col-xs-12">
                                                            <p class="text-capitalize">Proceed with assignment?</p>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="box-footer">
                                                    <input type="hidden" name="planID" value="<%out.print((Integer) request.getAttribute("planID"));%>">
                                                    <div class="btn-group pull-right">
                                                        <button type="submit" name="approve" onclick="form.action = 'AssignPointPerson'" class="btn btn-success">Assign</button>
                                                    </div>
                                                </div>
                                            </div>
                                            <!--                                            /.modal-content -->
                                        </div>
                                        <!--                                        /.modal-dialog -->
                                    </div>
                                    <div class="modal fade" id="disapprove">
                                        <div class="modal-dialog modal-sm">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                    <h4 class="modal-title">Confirm Disapproval</h4>

                                                </div>


                                                <div class="modal-body" id="modalBody">
                                                    <div class="row">
                                                        <div class="col-xs-12">
                                                            <p class="text-capitalize">Proceed with disapproval?</p>
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="box-footer">
                                                    <input type="hidden" name="planID" value="<%out.print((Integer) request.getAttribute("planID"));%>">
                                                    <div class="pull-right">
                                                        <button type="submit" name="disapprove" onclick="form.action = 'DisapproveCAPDEVProposal'" class="btn btn-danger">Disapprove</button>
                                                    </div>
                                                </div>



                                            </div>
                                            <!--                                            /.modal-content -->
                                        </div>
                                        <!--                                        /.modal-dialog -->
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

    </body>
</html>
