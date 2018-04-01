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
                ArrayList<CAPDEVActivity> caList = capdevDAO.getCAPDEVPlanActivities((Integer)request.getAttribute("planID"));
                int linksfarm = (Integer)request.getAttribute("linksfarm");
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
                    <div class="row">
                        <div class="col-xs-12">
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
                                                <td><%out.print(activity.getActivityName());%></td>
                                                <td><%out.print(activity.getActivityDate());%></td>
                                                <td><%out.print(activity.getArbList().size());%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>

                                    </table>   
                                </div>
                                <form method="post">
                                    <div class="box-footer">
                                        <input type="hidden" name="planID" value="<%out.print((Integer)request.getAttribute("planID"));%>">
                                        <input type="hidden" name="linksfarm" value="<%=linksfarm%>">
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
            
        </div>
        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
