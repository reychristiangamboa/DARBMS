<%-- 
    Document   : admin-manage-roles
    Created on : Jan 18, 2018, 11:44:43 AM
    Author     : Rey Christian
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf" %>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
            <%@include file="jspf/admin-navbar.jspf"%>
            <%@include file="jspf/admin-sidebar.jspf"%>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1 class="box-title"><i class="fa fa-briefcase"></i> Manage Roles</h1>
                    <ol class="breadcrumb">
                        <li><a href="admin-system-logs.jsp"><i class="fa fa-eye"></i> View System Logs</a></li>
                        <li><a href="#"><i class="fa fa-users"></i> Manage Accounts</a></li>
                        <li class="active"><a href="admin-manage-roles.jsp"><i class="fa fa-briefcase"></i> Manage Roles</a></li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-5">
                            <div class="box">
                                <form method="post" role="form">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="userID">User ID</label>
                                                    <input type="text" id="userID" class="form-control" name="userID" required/>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="role">Role</label>
                                                    <select name="role" class="form-control" id="role" required>
                                                        <option value=2>Evaluator</option>
                                                        <option value=3>Inspector</option>
                                                        <option value=4>Division Chief</option>
                                                        <option value=5>Director</option>
                                                    </select>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="box-footer">
                                        <button type="submit" class="btn btn-success pull-right">Change</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Active Users</h3></div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Name</th>
                                                <th>E-mail</th>
                                                <th>Role</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for (User u : activeUsers) {
                                                    if (u.getEmail().equals((String) session.getAttribute("email"))) {
                                                            // DO NOTHING
                                                        } else {
                                            %>
                                            <tr>
                                                <td><%out.print(u.getUserID());%></td>
                                                <td><%out.print(u.getFullName());%></td>
                                                <td><%out.print(u.getEmail());%></td>
                                                <td><%out.print(u.getUserTypeDesc());%></td>
                                            </tr>
                                            <%}
                                            
                                            }%>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ID</th>
                                                <th>Name</th>
                                                <th>E-mail</th>
                                                <th>Role</th>
                                            </tr>
                                        </tfoot>
                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- /.row -->
                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
            
            <footer class="main-footer">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 2.4.0
                </div>
                <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
                reserved.
            </footer>
        </div>
        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
