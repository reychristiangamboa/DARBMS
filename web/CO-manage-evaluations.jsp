<%-- 
    Document   : central-manage-evaluations
    Created on : Mar 24, 2018, 7:33:35 AM
    Author     : Rey Christian
--%>
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

            <% ArrayList<Question> questions = (ArrayList<Question>)request.getAttribute("questions"); %>
            <% int type = (Integer)request.getAttribute("type"); %>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1 class="box-title"><i class="fa fa-briefcase"></i> Manage Evaluation Questions</h1>
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
                                <div class="box-header"><h3 class="box-title">Add Question</h3></div>
                                <form method="post" role="form" >
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="form-group">
                                                    <label for="actName">Question</label>
                                                    <input type="text" id="actName" class="form-control" name="question" required/>
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <div class="form-group">
                                                    <label for="actName">Question Weight</label>
                                                    <input type="number" class="form-control" name="weight" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="box-footer">
                                        <button type="submit" onclick="form.action = 'AddQuestion?type=<%out.print(type);%>'" class="btn btn-success pull-right"><i class="fa fa-plus margin-r-5"></i>Add</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Questions</h3></div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th class="text-center">Question</th>
                                                <th class="text-center">Weight</th>
                                                <th class="text-center">Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for (Question q : questions) {
                                            %>
                                            <tr>

                                                <td><%out.print(q.getQuestion());%></td>
                                                <td><%out.print(q.getWeight());%></td>

                                                <td class="text-center">
                                                    <button class="btn btn-primary btn-s" data-toggle="modal" data-target="#modal<%out.print(q.getQuestionID());%>"><i class="fa fa-edit margin-r-5"></i>Edit</button>
                                                </td>
                                            </tr>

                                        <div class="modal fade" id="modal<%out.print(q.getQuestionID());%>">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span></button>
                                                        <h4 class="modal-title">Edit Question</h4>
                                                    </div>

                                                    <form method="post">
                                                        <div class="modal-body" id="modalBody">
                                                            <div class="row">
                                                                <div class="col-xs-8">
                                                                    <div class="form-group">
                                                                        <label for="actName">Question</label>
                                                                        <input type="text" value="<%out.print(q.getQuestion());%>" id="actName" class="form-control" name="question" required/>
                                                                    </div>
                                                                </div>

                                                            </div>
                                                            <div class="row">
                                                                <div class="col-xs-4">
                                                                    <div class="form-group">
                                                                        <label for="actName">Question Weight</label>
                                                                        <input type="number" class="form-control" name="weight" required>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </div>
                                                        <div class="modal-footer">
                                                            <input type="hidden" name="questionID" value="<%out.print(q.getQuestionID());%>">
                                                            <button type="submit" onclick="form.action = 'EditQuestion?type=<%out.print(type);%>'" class="btn btn-primary">Edit</button>
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
                                                <th class="text-center">Question</th>
                                                <th class="text-center">Weight</th>
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
