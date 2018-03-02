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
            <%if((Integer)session.getAttribute("userType") == 1){%>
            <%@include file="jspf/admin-navbar.jspf"%>
            <%@include file="jspf/admin-sidebar.jspf"%>
            <%}else if((Integer)session.getAttribute("userType") == 2){%>
            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%}%>
            <div class="content-wrapper">
                <section class="content-header">
                    <h1><i class="fa fa-user-plus"></i> Edit Profile</h1>
                    <ol class="breadcrumb">
                        <%if((Integer)session.getAttribute("userType") == 1){%>            
                        <li><a href="admin-system-logs.jsp"><i class="fa fa-eye"></i> View System Logs</a></li>
                            <%}else if((Integer)session.getAttribute("userType") == 2){%>        
                        // PROVINCIAL FIELD OFFICER HOME HERE
                        <%}%>
                        <li class="disabled"><a href="#"><i class="fa fa-users"></i> Manage Accounts</a></li>
                        <li class="active"><a href="#"><i class="fa fa-user-plus"></i> Edit Profile</a></li>
                    </ol>
                </section>
                <section class="content">
                    <%if (request.getAttribute("errMessage") != null) {%>
                    <p class="text text-center text-danger"><%=request.getAttribute("errMessage")%></p>
                    <%}%>
                    <%if (request.getAttribute("success") != null) {%>
                    <p class="text text-center text-success"><%=request.getAttribute("success")%></p>
                    <%}%>
                    <div class="row">
                        <!-- left column -->
                        <form action="action" method="post" action="EditPersonalInformation">
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
                                                    <input type="text" id="fullName" name="fullName" class="form-control" placeholder="<%=session.getAttribute("fullName")%>" required>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="form-group">
                                                    <label for="address">Address</label>
                                                    <input type="text" id="address" name="address" class="form-control" placeholder="<%=session.getAttribute("address")%>" required/>    
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="contactNo">Contact No.</label>
                                                    <input type="text" id="contactNo" name="contactNo" class="form-control" placeholder="<%=session.getAttribute("contactNo")%>" required/>    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /.box-body -->
                                </div>
                                <!-- /.box -->
                            </div>
                            <!--/.col (left) -->
                        </form>

                        <form action="action" method="post" action="EditAccount">
                            <div class="col-xs-6">
                                <div class="box box-success">
                                    <div class="box-header with-border">
                                        <h3 class="box-title">Edit Account</h3>
                                    </div>
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="email">Email address</label>
                                                    <input type="email" name="email" class="form-control" id="email" placeholder="<%=session.getAttribute("email")%>" required>
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
                                        <button type="submit" class="btn btn-success pull-right">Submit</button>
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
