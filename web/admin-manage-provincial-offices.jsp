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
                    <h1 class="box-title"><i class="fa fa-briefcase"></i> Manage Provincial Offices</h1>
                    <ol class="breadcrumb">
                        <li><a href="admin-system-logs.jsp"><i class="fa fa-eye"></i> View System Logs</a></li>
                        <li><a href="#"><i class="fa fa-users"></i> Manage Provincial Offices</a></li>
                    </ol>
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
                        <div class="col-xs-10">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Add Provincial Office</h3></div>
                                <form method="post" role="form">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="branch">Office Branch</label>
                                                    <input type="text" id="branch" class="form-control" name="branch" required/>
                                                </div>
                                            </div>
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="regCode">Region</label>
                                                    <select name="regCode" class="form-control" id="regCode" required>
                                                        <%for(Region r: regionList){%>
                                                        <option value="<%out.print(r.getRegCode());%>"> <%out.print(r.getRegDesc());%> </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="box-footer">
                                        <button type="submit" onclick="form.action = 'AddProvincialOffice'" class="btn btn-success pull-right">Add</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Provincial Offices</h3></div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th class="text-center">ID</th>
                                                <th class="text-center">Office Branch</th>
                                                <th class="text-center">Region</th>
                                                <th class="text-center">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for (Province p : provOffices) {
                                            %>
                                            <tr>
                                                <td class="provCode"><%out.print(p.getProvCode());%></td>
                                                <td class="provDesc"><%out.print(p.getProvDesc());%></td>
                                                <td><%out.print(addressDAO.getRegDesc(p.getRegCode()));%></td>
                                                <td class="text-center">
                                                    <button class="btn btn-primary btn-s" data-toggle="modal" data-target="#modal<%out.print(p.getProvCode());%>">Edit</button>
                                                </td>
                                            </tr>

                                        <div class="modal fade" id="modal<%out.print(p.getProvCode());%>">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span></button>
                                                        <h4 class="modal-title">Edit Provincial Office</h4>
                                                    </div>

                                                    <form method="post">
                                                        <div class="modal-body" id="modalBody">
                                                            <div class="row">
                                                                <div class="col-xs-4">
                                                                    <div class="form-group">
                                                                        <label for="branch">Office Branch</label>
                                                                        <input type="text" id="branch" class="form-control" value="<%out.print(p.getProvDesc());%>" name="branch" required/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-6">
                                                                    <div class="form-group">
                                                                        <label for="regCode">Region</label>
                                                                        <select name="regCode" class="form-control" id="regCode" required>
                                                                            <%for(Region r: regionList){%>
                                                                            <option value="<%out.print(r.getRegCode());%>"> <%out.print(r.getRegDesc());%> </option>
                                                                            <%}%>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <input type="hidden" name="provOfficeCode" value="<%out.print(p.getProvCode());%>">
                                                            <button type="submit" onclick="form.action = 'EditProvincialOffice'" class="btn btn-primary">Edit</button>
                                                        </div>
                                                    </form>

                                                </div>
                                                <!--                                            /.modal-content -->
                                            </div>
                                            <!--                                        /.modal-dialog -->
                                        </div>
                                        <%}%>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th class="text-center">ID</th>
                                                <th class="text-center">Office Branch</th>
                                                <th class="text-center">Region</th>
                                                <th class="text-center">Action</th>
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
