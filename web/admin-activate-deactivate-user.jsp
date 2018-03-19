<%-- 
    Document   : admin-activate-deactivate-user
    Created on : Jan 17, 2018, 8:22:26 PM
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
                    <h1 class="box-title"><i class="fa fa-check-square"></i> Activate/Deactivate User</h1>
                    <ol class="breadcrumb">
                        <li><a href="admin-system-logs.jsp"><i class="fa fa-eye"></i> View System Logs</a></li>
                        <li><a href="#"><i class="fa fa-users"></i> Manage Accounts</a></li>
                        <li class="active"><a href="admin-activate-deactivate-user.jsp"><i class="fa fa-check-square"></i> Activate/Deactivate User</a></li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Active Users</h3></div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <form action="DeactivateAccount" method="post">
                                        <table id="example1" class="table table-bordered table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Name</th>
                                                    <th>E-mail</th>
                                                    <th>Role</th>
                                                    <th>Action</th>
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
                                                    <td><%out.print(u.getFullName());%></td>
                                                    <td><%out.print(u.getEmail());%></td>
                                                    <td><%out.print(u.getUserTypeDesc());%></td>
                                                    <td> 
                                                        <input type="checkbox" id="<%=u.getUserID()%>" name="activeCheckBox" value="<%=u.getUserID()%>"/>
                                                    </td>
                                                </tr>
                                                <%}
                                            
                                                }%>
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <th>Name</th>
                                                    <th>E-mail</th>
                                                    <th>Role</th>
                                                    <th>Action</th>
                                                </tr>
                                            </tfoot>
                                        </table>
                                        <button type="submit" class="btn btn-danger pull-right">Deactivate</button>
                                    </form>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->

                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Inactive Users</h3></div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <form action="ActivateAccount" method="post">
                                        <table id="example3" class="table table-bordered table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Name</th>
                                                    <th>E-mail</th>
                                                    <th>Role</th>
                                                    <th>Action</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%
                                                    for (User u : inactiveUsers) {
                                                        if (u.getEmail().equals((String) session.getAttribute("email"))) {
                                                                // DO NOTHING
                                                            } else {
                                                %>
                                                <tr>
                                                    <td><%out.print(u.getFullName());%></td>
                                                    <td><%out.print(u.getEmail());%></td>
                                                    <td><%out.print(u.getUserTypeDesc());%></td>
                                                    <td> 
                                                        <input type="checkbox" id="<%=u.getUserID()%>" name="inactiveCheckBox" value="<%=u.getUserID()%>"/>                                
                                                    </td>
                                                </tr>
                                                <%}
                                            
                                                }%>
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <th>Name</th>
                                                    <th>E-mail</th>
                                                    <th>Role</th>
                                                    <th>Action</th>
                                                </tr>
                                            </tfoot>
                                        </table>
                                        <button type="submit" class="btn btn-success pull-right">Activate</button>
                                    </form>
                                </div>
                                <!-- /.box-body -->
                            </div>
                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- /.row -->
                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
        </div>
        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
