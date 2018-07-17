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
                CAPDEVPlan p = capdevDAO.getCAPDEVPlan((Integer) request.getAttribute("planID"));
                ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
                
                User pointPerson = uDAO.searchUser(p.getAssignedTo());
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
                                <div class="box-body">
                                    <%@include file="jspf/arbo-information.jspf" %>
                                </div>
                            </div>

                            <!-- /.box -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">CAPDEV Plan</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>
                                </div>
                                <form method="post">
                                    <div class="box-body">

                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="pointPerson">Point Person</label>
                                                    <input type="text" class="form-control" value="<%out.print(pointPerson.getFullName());%>" disabled />
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <label for="">Budget</label>
                                                <input type="text" class="form-control" value="<%out.print(p.getBudget());%>" disabled />
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-2"></div>
                                            <div class="col-xs-8">
                                                <table class="table table-bordered table-striped modTable">

                                                    <thead>
                                                        <tr>
                                                            <th>Activity</th>
                                                            <th>No. of Participants</th>
                                                        </tr>
                                                    </thead>

                                                    <tbody>
                                                        <%for (CAPDEVActivity activity : p.getActivities()) {%>
                                                        <tr>
                                                            <td><%out.print(activity.getActivityName());%></td>
                                                            <td><a href="#" data-toggle="modal" data-target="#participantsModal<%out.print(activity.getActivityID());%>"><%out.print(activity.getArbList().size());%></a></td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>

                                                    <tfoot>
                                                        <tr>
                                                            <th>Activity</th>
                                                            <th>No. of Participants</th>
                                                        </tr>
                                                    </tfoot>
                                                </table>
                                                <%for (CAPDEVActivity activity : p.getActivities()) {%>
                                                <div class="modal fade" id="participantsModal<%out.print(activity.getActivityID());%>">
                                                    <div class="modal-dialog modal-md">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span>
                                                                </button>
                                                                <h4 class="modal-title"><%out.print(activity.getActivityName());%></h4>

                                                            </div>
                                                            <div class="modal-body" id="modalBody">
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <table class="table table-striped table-bordered modTable">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>ARB</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%for(ARB arb : activity.getArbList()){%>
                                                                                <tr>
                                                                                    <td><a target="_blank" rel="noopener noreferrer" href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFLName());%></a></td>
                                                                                </tr>
                                                                                <%}%>
                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>ARB</th>
                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <!--                                            /.modal-content -->
                                                    </div>
                                                    <!--                                        /.modal-dialog -->
                                                </div>
                                                <%}%>
                                            </div>
                                            <div class="col-xs-2"></div>
                                        </div>

                                    </div>

                                    <div class="box-footer">
                                        <div class="btn-group pull-right">
                                            <button type="button" name="disapprove" class="btn btn-danger" data-toggle="modal" data-target="#disapprove">Disapprove</button>
                                            <button type="button" name="approve" class="btn btn-success" data-toggle="modal" data-target="#approve">Approve</button>
                                        </div>
                                    </div>

                                    <div class="modal fade" id="approve">
                                        <div class="modal-dialog modal-sm">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                    <h4 class="modal-title">Confirm Approval</h4>

                                                </div>
                                                <div class="modal-body" id="modalBody">
                                                    <div class="row">
                                                        <div class="col-xs-12">
                                                            <p class="text-capitalize">Proceed with approval?</p>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="box-footer">
                                                    <input type="hidden" name="planID" value="<%out.print((Integer) request.getAttribute("planID"));%>">
                                                    <div class="btn-group pull-right">
                                                        <button type="submit" name="approve" onclick="form.action = 'ApproveCAPDEVProposal'" class="btn btn-success">Approve</button>
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
