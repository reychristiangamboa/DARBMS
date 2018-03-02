<%-- 
    Document   : technical-assistant-review-capdev-attendance
    Created on : 02 25, 18, 12:47:54 AM
    Author     : Z40
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
            <%@include file="jspf/technical-assistant-sidebar.jspf" %>

            <div class="content-wrapper">
                <section class="content-header">
                    <h1>
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="field-officer-arbo-list.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    </ol>

                </section>
                <section class="content">
                    <div class=''>
                        <div class="box">
                            <div class="box-header with-borderr">
                                <h3 class="box-title">Attendance</h3>
                            </div>
                            <div class="box-body">
                                <div class="nav-tabs-custom">
                                    <!-- Tabs within a box -->
                                    <ul class="nav nav-tabs pull-left">
                                        <li class="active"><a href="#task1" data-toggle="tab">Orientation</a></li>
                                        <li><a href="#task2" data-toggle="tab">Training</a></li>
                                    </ul>

                                    <div class="tab-content no-padding">
                                        <!-- Morris chart - Sales -->
                                        <div class="chart tab-pane active" id="task1" style="position: relative; height: 300px;">
                                            <div class="box-body">
                                                <div class="row">
                                                    <div class="col-xs-4">
                                                        <label for="">Date</label>
                                                        <input type="date" class="form-control" disabled>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="box-body">
                                                <table id="example1" class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>ARB Name</th>
                                                            <th>Attendance</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td><a href="#">Rey Gamboas</a></td>
                                                            <td><input type="checkbox"></td>                                                            
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                <div class="col-xs-4">
                                                    <button class="btn btn-success">Submit</button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="chart tab-pane" id="task2" style="position: relative; height: 300px;"><div class="box-body">
                                                <div class="row">
                                                    <div class="col-xs-4">
                                                        <label for="">Date</label>
                                                        <input type="date" class="form-control" disabled>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="box-body">
                                                <table id="example3" class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>ARB Name</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td><a href="#">Rey Gamboas</a></td>
                                                            <td><input type="check" class="form-control"></td>                                                            
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                <div class="col-xs-4">
                                                    <button class="btn btn-success">Submit</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>   
                    </div>    
                </section>
            </div>

        </div>
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
