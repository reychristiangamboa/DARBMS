<%-- 
    Document   : regional-field-officer-view-capdev-activity
    Created on : Feb 24, 2018, 11:07:24 PM
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

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/point-person-sidebar.jspf" %>

            <%
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="field-officer-arbo-list.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    </ol>

                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">ARBO Information</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <!-- /.box-header -->


                                <div class="box-body"> 

                                    <div class="nav-tabs-custom">
                                        <!-- Tabs within a box -->
                                        <ul class="nav nav-tabs pull-left">
                                            <li class="active"><a href="#request" data-toggle="tab">Request Information</a></li>
                                            <li><a href="#info" data-toggle="tab">ARBO Profile</a></li>
                                            <li><a href="#history" data-toggle="tab">CAPDEV History</a></li>
                                        </ul>

                                        <div class="tab-content no-padding">
                                            <!-- Morris chart - Sales -->
                                            <div class="chart tab-pane active" id="request" style="position: relative;">
                                                <div class="box-body">

                                                    <div class="row">
                                                        <div class="col-xs-6">
                                                            <div class="form-group">
                                                                <label for="">Name of ARBO</label>
                                                                <input type="text" class="form-control" value="" disabled >
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-3">
                                                            <div class="form-group">
                                                                <label for="">No. of ARBs</label>
                                                                <input type="text" class="form-control" id="" placeholder="" value="" disabled>
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-3">
                                                            <div class="form-group">
                                                                <label for="">Land Area (Hectares)</label>
                                                                <input type="text" class="form-control" id="" value="" disabled >
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-xs-4">
                                                            <label for=''>Loan Amount</label>
                                                            <input type='text' class="form-control" id='' value="" disabled>
                                                        </div>

                                                        <div class="col-xs-4">
                                                            <div class="form-group">
                                                                <label for="">Reason for Loan</label>
                                                                <input type="text" class="form-control" value="" disabled/>
                                                            </div>
                                                        </div>         
                                                    </div>
                                                    <div class="row">
                                                        <div class="col-xs-12">
                                                            <label for=''>Remarks</label>
                                                            <textarea class="form-control" rows="3" disabled></textarea>
                                                        </div>

                                                    </div>
                                                </div> 
                                            </div>
                                            <div class="chart tab-pane" id="info" style="position: relative;">
                                            </div>
                                            <div class="chart tab-pane" id="history" style="position: relative;">


                                            </div>
                                        </div>
                                    </div>
                                    <hr>        
                                </div>
                                <!-- /.box-body -->



                            </div>
                            <!-- /.box -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">CAPDEV Plan</h3>
                                </div>
                                <form role="form" method="post" action="AddARB">
                                    <div class="box-body">
                                        <%%>
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="EL">Activity</label>
                                                    <select name="dependentEL[]" class="form-control" id="EL" style="width:100%;" disabled>
                                                        <option value="1">Module 1 - Orientation</option>
                                                        <option value="2">Module 2 - Training</option>
                                                        <option value="3">Module 3 - Assessment</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label>Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-calendar"></i>
                                                        </div>
                                                        <input type="date" name="moduleDate" class="form-control pull-right" id="datepicker" disabled>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">No. of Participants</label>
                                                    <input type="text" name="noOfParticipants" class="form-control" required disabled>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="EL">Activity</label>
                                                    <select name="dependentEL[]" class="form-control" id="EL" style="width:100%;" disabled>
                                                        <option value="1">Module 1 - Orientation</option>
                                                        <option value="2">Module 2 - Training</option>
                                                        <option value="3">Module 3 - Assessment</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label>Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-calendar"></i>
                                                        </div>
                                                        <input type="date" name="moduleDate" class="form-control pull-right" id="datepicker" disabled>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">No. of Participants</label>
                                                    <input type="text" name="noOfParticipants" class="form-control" required disabled>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="form-group">
                                                    <label for="EL">Activity</label>
                                                     <textarea class="form-control" rows="3" ></textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="box-footer">
                                        <input type="hidden" name="arboID" value="">
                                        <div class="btn-group pull-right">
                                            <button type="button" class="btn btn-success">Submit</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <!-- /.col -->
                    </div>
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
        <script>
            $(document).ready(function () {

            });
        </script>
    </body>
</html>
