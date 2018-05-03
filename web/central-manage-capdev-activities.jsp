<%-- 
    Document   : central-manage-capdev-activities
    Created on : Mar 3, 2018, 5:10:27 AM
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
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/central-sidebar.jspf" %>

            <% ArrayList<CAPDEVActivity> activityList = capdevDAO.getCAPDEVActivities(); %>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1 class="box-title"><i class="fa fa-briefcase"></i> Manage CAPDEV Activities</h1>
                    <ol class="breadcrumb">
                        <li><a href="admin-system-logs.jsp"><i class="fa fa-eye"></i> View System Logs</a></li>
                        <li><a href="#"><i class="fa fa-users"></i> Manage Past Due Reasons</a></li>
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
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Add CAPDEV Activity</h3></div>
                                <form method="post" role="form" >
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="actName">Activity Name</label>
                                                    <input type="text" id="actName" class="form-control" name="activityName" required/>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="actName">Activity Category</label>
                                                    <select name="category" id="" class="form-control">
                                                        <option value="1">APCP/CAPDEV</option>
                                                        <option value="2">Past Due APCP/CAPDEV</option>
                                                        <option value="3">LINKSFARM</option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="actDesc">Activity Description</label>
                                                    <textarea id="actDesc" rows="2" class="form-control" name="activityDesc" required></textarea>
                                                </div>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="box-footer">
                                        <button type="submit" onclick="form.action = 'AddCAPDEVActivity'" class="btn btn-success pull-right"><i class="fa fa-plus margin-r-5"></i>Add</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">CAPDEV Trainings</h3></div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>

                                                <th class="text-center">Name</th>
                                                <th class="text-center">Description</th>
                                                <th class="text-center">Category</th>
                                                <th class="text-center">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for (CAPDEVActivity a : activityList) {
                                            %>
                                            <tr>

                                                <td><%out.print(a.getActivityName());%></td>
                                                <td><%out.print(a.getActivityDesc());%></td>
                                                <td><%out.print(a.getActivityCategoryDesc());%></td>
                                                <td class="text-center">
                                                    <button class="btn btn-primary btn-s" data-toggle="modal" data-target="#modal<%out.print(a.getActivityID());%>"><i class="fa fa-edit margin-r-5"></i>Edit</button>
                                                </td>
                                            </tr>

                                        <div class="modal fade" id="modal<%out.print(a.getActivityID());%>">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span></button>
                                                        <h4 class="modal-title">Edit Reason for Past Due</h4>
                                                    </div>

                                                    <form method="post">
                                                        <div class="modal-body" id="modalBody">
                                                            <div class="row">
                                                                <div class="col-xs-4">
                                                                    <div class="form-group">
                                                                        <label for="actName">Activity Name</label>
                                                                        <input type="text" value="<%=a.getActivityName()%>" id="actName" class="form-control" name="activityName" required/>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-4">
                                                                    <div class="form-group">
                                                                        <label for="actName">Activity Category</label>
                                                                        <select name="category" id="" class="form-control">
                                                                            <option value="1">APCP/CAPDEV</option>
                                                                            <option value="2">Past Due APCP/CAPDEV</option>
                                                                            <option value="3">LINKSFARM</option>
                                                                        </select>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <div class="form-group">
                                                                        <label for="actDesc">Activity Description</label>
                                                                        <textarea id="actDesc" rows="2" class="form-control" name="activityDesc"><%out.print(a.getActivityDesc());%></textarea>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <input type="hidden" name="activityID" value="<%out.print(a.getActivityID());%>">
                                                            <button type="submit" onclick="form.action = 'EditCAPDEVActivity'" class="btn btn-primary">Edit</button>
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

                                                <th class="text-center">Name</th>
                                                <th class="text-center">Description</th>
                                                <th class="text-center">Category</th>
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
        </div>
        <%@include file="jspf/footer.jspf" %>

    </body>
</html>