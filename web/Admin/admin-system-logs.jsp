<%-- 
    Document   : system-logs
    Created on : Jan 17, 2018, 2:17:22 PM
    Author     : Rey Christian
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/jspf/header.jspf" %>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
            <%@include file="/jspf/admin-navbar.jspf"%>
            <%@include file="/jspf/admin-sidebar.jspf"%>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1 class="box-title"><i class="fa fa-eye"></i> DAR-BMS Logs</h1>
                    <ol class="breadcrumb">
                        <li class="active"><a href="admin-system-logs.jsp"><i class="fa fa-eye"></i> View System Logs</a></li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header"></div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example4" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Date</th>
                                                <th>Time</th>
                                                <th>Action Description</th>
                                                <th>Action By</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for (Log l2:logs) {
                                            %>
                                            <tr>
                                                <td><%out.print(l2.getDate());%></td>
                                                <td><%out.print(l2.getTime());%></td>
                                                <td><%out.print(l2.getActionDesc());%></td>
                                                <td><%out.print(l2.getActionByDesc());%></td>
                                            </tr>
                                            <%}%>
                                            </tr>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Date</th>
                                                <th>Time</th>
                                                <th>Action Description</th>
                                                <th>Action By</th>
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
        </div>


        <%@include file="/jspf/footer.jspf" %>

    </body>
</html>
