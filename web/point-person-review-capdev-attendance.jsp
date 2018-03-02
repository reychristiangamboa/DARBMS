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
            <%@include file="jspf/point-person-sidebar.jspf" %>

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
                                        <div class="chart tab-pane active" id="task1" style="position: relative; height: 200px;">
                                            <div class="box-body">
                                                <div class="row">
                                                    <div class="col-xs-4">
                                                        <label>Date:</label>
                                                        <div class="input-group date">
                                                            <div class="input-group-addon">
                                                                <i class="fa fa-calendar"></i>
                                                            </div>
                                                            <input type="text" class="form-control pull-right" id="datepicker"disabled>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-4">
                                                        <label for="">Activity Report DTN</label>
                                                        <input type="text" class="form-control" >
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="box-body">

                                                <div class="row">
                                                    <div class="col-xs-4">
                                                        <label for="">Upload Participants</label>
                                                        <input type="file">
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="chart tab-pane" id="task2" style="position: relative; height: 200px;">
                                            <div class="box-body">
                                                <div class="row">
                                                    <div class="col-xs-4">
                                                        <label>Date:</label>
                                                        <div class="input-group date">
                                                            <div class="input-group-addon">
                                                                <i class="fa fa-calendar"></i>
                                                            </div>
                                                            <input type="text" class="form-control pull-right" id="datepicker"disabled>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-4">
                                                        <label for="">Activity Report DTN</label>
                                                        <input type="text" class="form-control" >
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="box-body">

                                                <div class="row">
                                                    <div class="col-xs-4">
                                                        <label for="">Upload Participants</label>
                                                        <input type="file">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-footer">
                                    <button class="btn btn-success pull-right">Submit</button>
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
