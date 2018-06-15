<%-- 
    Document   : admin-add-personnel
    Created on : Jan 17, 2018, 4:20:23 PM
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
                    <h1><i class="fa fa-user-plus"></i> Add Personnel</h1>
                    <ol class="breadcrumb">
                        <li><a href="admin-system-logs.jsp"><i class="fa fa-eye"></i> View System Logs</a></li>
                        <li class="disabled"><a href="#"><i class="fa fa-users"></i> Manage Accounts</a></li>
                        <li class="active"><a href="admin-add-personnel.jsp"><i class="fa fa-user-plus"></i> Add Personnel</a></li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <%if (request.getAttribute("errMessage") != null) {%>
                    <p class="text text-center text-danger"><%=request.getAttribute("errMessage")%></p>
                    <%}%>
                    <%if (request.getAttribute("success") != null) {%>
                    <p class="text text-center text-success"><%=request.getAttribute("success")%></p>
                    <%}%>
                    <div class="row">
                        <!-- left column -->
                        <form role="form" method="post" action="AddPersonnel">
                            <div class="col-md-6">
                                <!-- general form elements -->
                                <div class="box box-success">

                                    <div class="box-header with-border">
                                        <h3 class="box-title">Personal Information</h3>
                                    </div>
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="form-group">
                                                    <label for="fullName">Full Name</label>
                                                    <input type="text" id="fullName" name="fullName" class="form-control" placeholder="Enter full name" required>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="form-group">
                                                    <label for="address">Address</label>
                                                    <input type="text" id="address" name="address" class="form-control" placeholder="Enter address" required/>    
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="contactNo">Contact No.</label>
                                                    <input type="text" id="contactNo" name="contactNo" class="form-control" placeholder="Enter contact no." required/>    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- /.box-body -->
                                </div>
                                <!-- /.box -->
                            </div>
                            <!--/.col (left) -->

                            <div class="col-xs-6">
                                <div class="box box-success">
                                    <div class="box-header with-border">
                                        <h3 class="box-title">Account Information</h3>
                                    </div>
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-8">
                                                <div class="form-group">
                                                    <label for="email">Email address</label>
                                                    <input type="email" name="email" class="form-control" id="email" placeholder="Enter email" required>
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

                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="role">Role</label>
                                                    <select name="role" class="form-control" id="role" required>
                                                        <option value="1">Administrator</option>
                                                        <option value="2">Point Person</option>
                                                        <option value="3">Provincial Field Officer</option>
                                                        <option value="4">Regional Field Officer</option>
                                                        <option value="5">Central Officer</option>
                                                    </select>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="row">

                                            <div class="col-xs-4">
                                                <div class="form-group" id="regionDiv" style="display:none">
                                                    <label for="region">Region</label>
                                                    <select name="region" onchange="chg()" class="form-control" id="regionDrop" required>
                                                        <%for(Region r: regionList){%>
                                                        <option value="<%out.print(r.getRegCode());%>"> <%out.print(r.getRegDesc());%> </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>

                                            <div class="col-xs-4">
                                                <div class="form-group" id="provinceDiv" style="display:none">
                                                    <label for="province">Province</label>
                                                    <select name="province" class="form-control" id="provinceDrop" disabled>

                                                    </select>
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
            <!-- /.content-wrapper -->
        </div>


        <%@include file="/jspf/footer.jspf" %>
        <script type="text/javascript">
            function chg() {
                var regionVal = document.getElementById('regionDrop').value;

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('provinceDiv').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "ProvOfficeRefresh?valajax=" + regionVal, true);
                xhttp.send();
            }
            $(document).ready(function () {
                $("#regionDiv").hide();
                $("#provinceDiv").hide();
                $("#role").on('change', function (e) {
                    if ($("#role").val() === 2) { // TA
                        $("#regionDiv").show();
                        $("#provinceDiv").show();
                    } else if ($("#role").val() === 3) { // PFO
                        $("#regionDiv").show();
                        $("#provinceDiv").show();
                    } else if ($("#role").val() === 1) {
                        $("#regionDiv").hide();
                        $("#provinceDiv").hide();
                    } else if ($("#role").val() === 4) { // RFO
                        $("#regionDiv").show();
                        $("#provinceDiv").hide();
                    }
                });
            });
        </script>
    </body>
</html>
