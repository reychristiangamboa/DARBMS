<%-- 
    Document   : CO-requested-budgets
    Created on : Jul 13, 2018, 9:01:23 PM
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
            <%@include file="jspf/central-sidebar.jspf" %>
            <%
            
                ArrayList<ProvincialBudget> apcpBudgets = addressDAO.getAllAPCPBudget();
                ArrayList<ProvincialBudget> capdevBudgets = addressDAO.getAllCAPDEVBudget();
            
            %>

            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-money"></i> Budget Requests</strong> 
                    </h1>
                </section>
                <section class="content">

                    <div class="row">
                        <div class="col-xs-12">

                            <%if(request.getAttribute("success") != null){%>
                            <div class="alert alert-success alert-dismissible">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                                <h4><i class="icon fa fa-check"></i> <%out.print((String)request.getAttribute("success"));%></h4>
                            </div>
                            <%}%>

                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">APCP Budget Requests</h3>
                                </div>
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <table class="table table-bordered table-striped export">
                                                <thead>
                                                    <tr>
                                                        <th>Provincial Office</th>
                                                        <th>Requested By</th>
                                                        <th>Amount</th>
                                                        <th>Reason</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        for(ProvincialBudget apcp : apcpBudgets){
                                                            User requestedBy = uDAO.searchUser(apcp.getRequestedBy());
                                                            Province provOffice = addressDAO.getProvOffice(apcp.getProvOfficeCode());
                                                    %>
                                                    <tr>
                                                        <td><%out.print(provOffice.getProvDesc());%></td>
                                                        <td><%out.print(requestedBy.getFullName());%></td>
                                                        <td><%out.print(currency.format(apcp.getBudget()));%></td>
                                                        <td><%out.print(apcp.getReason());%></td>
                                                        <td>
                                                            <div class="btn-group">
                                                                <button type="button" class="btn btn-success" data-toggle="modal" data-target="#approveAPCP<%out.print(apcp.getId());%>">Approve</button>
                                                                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#disapproveAPCP<%out.print(apcp.getId());%>">Disapprove</button>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                <div class="modal fade" id="approveAPCP<%out.print(apcp.getId());%>">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span></button>
                                                                <h4 class="modal-title">Approve Budget</h4>
                                                            </div>

                                                            <form method="post">
                                                                <div class="modal-body">
                                                                    <div class="row">
                                                                        <div class="col-xs-6">
                                                                            <div class="form-group">
                                                                                <label>Start Date:</label>
                                                                                <div class="input-group date">
                                                                                    <div class="input-group-addon">
                                                                                        <i class="fa fa-calendar"></i>
                                                                                    </div>
                                                                                    <input type="date" class="form-control" name="startDate" />
                                                                                </div>
                                                                                <!-- /.input group -->
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-xs-6">
                                                                            <div class="form-group">
                                                                                <label>End Date:</label>
                                                                                <div class="input-group date">
                                                                                    <div class="input-group-addon">
                                                                                        <i class="fa fa-calendar"></i>
                                                                                    </div>
                                                                                    <input type="date" class="form-control" name="endDate" />
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="submit" onclick="form.action = 'ApproveBudget?id=<%out.print(apcp.getId());%>&type=APCP'" class="btn btn-success">Approve</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>

                                                <div class="modal fade" id="disapproveAPCP<%out.print(apcp.getId());%>">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span></button>
                                                                <h4 class="modal-title">Approve Budget</h4>
                                                            </div>


                                                            <div class="modal-body">
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <center>
                                                                            <p>Proceed with budget disapproval?</p>
                                                                        </center>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="modal-footer">
                                                                <button type="submit" onclick="form.action = 'DisapproveBudget?id=<%out.print(apcp.getId());%>&type=APCP'" class="btn btn-danger">Disapprove</button>
                                                            </div>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>
                                                <%}%>
                                                </tbody>
                                            </table>

                                        </div>

                                    </div>
                                </div>
                            </div>

                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">CAPDEV Budget Requests</h3>
                                </div>
                                <div class="box-body">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <table class="table table-bordered table-striped export">
                                                <thead>
                                                    <tr>
                                                        <th>Provincial Office</th>
                                                        <th>Requested By</th>
                                                        <th>Amount</th>
                                                        <th>Reason</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        for(ProvincialBudget capdev : capdevBudgets){
                                                            User requestedBy = uDAO.searchUser(capdev.getRequestedBy());
                                                            Province provOffice = addressDAO.getProvOffice(capdev.getProvOfficeCode());
                                                    %>
                                                    <tr>
                                                        <td><%out.print(provOffice.getProvDesc());%></td>
                                                        <td><%out.print(requestedBy.getFullName());%></td>
                                                        <td><%out.print(currency.format(capdev.getBudget()));%></td>
                                                        <td><%out.print(capdev.getReason());%></td>
                                                        <td>
                                                            <div class="btn-group">
                                                                <button type="button" class="btn btn-success" data-toggle="modal" data-target="#approveCAPDEV<%out.print(capdev.getId());%>">Approve</button>
                                                                <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#disapproveCAPDEV<%out.print(capdev.getId());%>">Disapprove</button>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                <div class="modal fade" id="approveCAPDEV<%out.print(capdev.getId());%>">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span></button>
                                                                <h4 class="modal-title">Approve Budget</h4>
                                                            </div>

                                                            <form method="post">
                                                                <div class="modal-body">
                                                                    <div class="row">
                                                                        <div class="col-xs-6">
                                                                            <div class="form-group">
                                                                                <label>Start Date:</label>
                                                                                <div class="input-group date">
                                                                                    <div class="input-group-addon">
                                                                                        <i class="fa fa-calendar"></i>
                                                                                    </div>
                                                                                    <input type="date" class="form-control" name="startDate" />
                                                                                </div>
                                                                                <!-- /.input group -->
                                                                            </div>
                                                                        </div>
                                                                        <div class="col-xs-6">
                                                                            <div class="form-group">
                                                                                <label>End Date:</label>
                                                                                <div class="input-group date">
                                                                                    <div class="input-group-addon">
                                                                                        <i class="fa fa-calendar"></i>
                                                                                    </div>
                                                                                    <input type="date" class="form-control" name="endDate" />
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="modal-footer">
                                                                    <button type="submit" onclick="form.action = 'ApproveBudget?id=<%out.print(capdev.getId());%>&type=CAPDEV'" class="btn btn-success">Approve</button>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>

                                                <div class="modal fade" id="disapproveCAPDEV<%out.print(capdev.getId());%>">
                                                    <div class="modal-dialog">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span></button>
                                                                <h4 class="modal-title">Approve Budget</h4>
                                                            </div>


                                                            <div class="modal-body">
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <center>
                                                                            <p>Proceed with budget disapproval?</p>
                                                                        </center>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="modal-footer">
                                                                <button type="submit" onclick="form.action = 'DisapproveBudget?id=<%out.print(capdev.getId());%>&type=CAPDEV'" class="btn btn-danger">Disapprove</button>
                                                            </div>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>
                                                <%}%>
                                                </tbody>
                                            </table>

                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
