<%-- 
    Document   : edit-profile
    Created on : Feb 13, 2018, 5:32:03 PM
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

            <%@include file="jspf/admin-navbar.jspf"%>

            <%if ((Integer) session.getAttribute("userType") == 1) {%>
            <%@include file="jspf/admin-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 2) {%>
            <%@include file="jspf/point-person-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1><i class="fa fa-edit"></i> Edit Profile</h1>
     
                </section>
                <section class="content">
                    <%if (request.getAttribute("success") != null) {%>
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-check"></i> <%out.print((String) request.getAttribute("success"));%></h4>
                    </div>
                    <%} else if (request.getAttribute("errMessage") != null) {%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String) request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>
                    <div class="row">
                        <!-- left column -->
                        <form method="post">
                            <div class="col-md-6">
                                <!-- general form elements -->
                                <div class="box box-success">

                                    <div class="box-header with-border">
                                        <h3 class="box-title">Edit Personal Information</h3>
                                    </div>
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="form-group">
                                                    <label for="fullName">Full Name</label>
                                                    <input type="text" id="fullName" name="fullName" class="form-control" value="<%=session.getAttribute("fullName")%>" required>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="form-group">
                                                    <label for="address">Address</label>
                                                    <input type="text" id="address" name="address" class="form-control" value="<%=session.getAttribute("address")%>" required/>    
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="contactNo">Contact No.</label>
                                                    <input type="text" id="contactNo" name="contactNo" class="form-control" value="<%=session.getAttribute("contactNo")%>" required/>    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /.box-body -->
                                    <div class="box-footer">
                                        <button type="submit" onclick="form.action = 'EditPersonalInformation'" class="btn btn-success pull-right"><i class="fa fa-send margin-r-5"></i>Submit</button>
                                    </div>
                                </div>
                                <!-- /.box -->
                            </div>
                            <!--/.col (left) -->
                        </form>

                        <form method="post">
                            <div class="col-xs-6">
                                <div class="box box-success">
                                    <div class="box-header with-border">
                                        <h3 class="box-title">Edit Account</h3>
                                    </div>
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="email">Email address</label>
                                                    <input type="email" name="email" class="form-control" id="email" value="<%=session.getAttribute("email")%>" required>
                                                </div>    
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="password">Password</label>
                                                    <input type="password" name="password" class="form-control" id="password" placeholder="Password" required>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="confirmPassword">Confirm Password</label>
                                                    <input type="password" name="confirmPassword" class="form-control" id="confirmPassword" placeholder="Confirm Password" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /.box-body -->
                                    <div class="box-footer">
                                        <button type="submit" onclick="form.action='EditAccount'" class="btn btn-success pull-right"><i class="fa fa-send margin-r-5"></i>Submit</button>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>

                    <!-- /.row -->
                </section>
                <!-- /.content --> 
            </div>
        </div>

        <%@include file="jspf/footer.jspf"%>
    </body>
</html>
