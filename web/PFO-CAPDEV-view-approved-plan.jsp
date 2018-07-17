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
        <style>
            .example-modal .modal {
                position: relative;
                top: auto;
                bottom: auto;
                right: auto;
                left: auto;
                display: block;
                z-index: 1;
            }

            .example-modal .modal {
                background: transparent !important;
            }
        </style>
    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pfo-capdev-sidebar.jspf" %>
            <%
                CAPDEVPlan p = capdevDAO.getCAPDEVPlan((Integer)request.getAttribute("planID"));
                APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
                ArrayList<User> pointPersons = uDAO.getAllPointPersonProvince((Integer) session.getAttribute("provOfficeCode"));
                
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-user"></i> Assign Point Person</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>
                    

                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            
                            <!-- /.box -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">CAPDEV Plan</h3>
                                    <h5 class="box-title pull-right text-muted">DTN: <%out.print(p.getPlanDTN());%></h5>
                                </div>
                                <form role="form" method="post">
                                    <div class="box-body">
                                        <div class="box-body">
                                            <div class="row col-xs-4" style="margin-bottom: 20px;">
                                                
                                                <label>Assign Point Person</label>
                                                <select name="pointPersonID" class="form-control select2">
                                                    <%for(User u : pointPersons){%>
                                                    <option value="<%=u.getUserID()%>"><%out.print(u.getFullName());%></option>
                                                    <%}%>
                                                </select>
                                                
                                            </div>

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
                                                        <td><%out.print(f.format(p.getPlanDate()));%></td>
                                                        <td><%out.print(activity.getArbList().size());%></td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                    <div class="box-footer">
                                        <div class="btn-group pull-right">
                                            <input type="hidden" name="planID" value="<%out.print(p.getPlanID());%>">
                                            <button type="submit" onclick="form.action = 'AssignPointPerson'" class="btn btn-success">Submit</button>
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

        <script>
            
        </script>
    </body>
</html>
