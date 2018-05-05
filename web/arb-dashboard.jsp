<%-- 
    Document   : provincial-field-officer-home
    Created on : Mar 16, 2018, 4:45:24 PM
    Author     : Rey Christian
--%>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>
        <style>
            .rate{
                color:black;
                cursor:pointer;
                width: 80px;
                margin: 0 auto;
            }
            .rate:hover{
                color:red;
            }
            .checked {
                color: orange;
            }
            @media screen and (min-width: 768px) {
                .modal-dialog {
                    width: 700px; /* New width for default modal */
                }
                .modal-sm {
                    width: 350px; /* New width for small modal */
                }
            }
            @media screen and (min-width: 992px) {
                .modal-lg {
                    width: 950px; /* New width for large modal */
                }
            }
            @media screen and (min-width: 1080px) {
                .modal-lger {
                    width: 1080px; /* New width for large modal */
                }
            }

        </style>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/central-sidebar.jspf"%>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Invoice
                        <small>#007612</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Examples</a></li>
                        <li class="active">Invoice</li>
                    </ol>
                </section>



                <!-- Main content -->
                <section class="invoice">
                    <!-- title row -->
                    <div class="row">
                        <div class="col-xs-12">
                            <h2 class="page-header">
                                <i class="fa fa-globe"></i> Agrarian Reform Beneficiary (ARB) Dashboard
                                <small class="pull-right">Date: 2/10/2014</small>
                            </h2>
                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- info row -->
                    <div class="row invoice-info">

                        <div class="col-sm-12">
                            <form id="drillDownGenderForm">
                                <div class="row no-print">
                                    <div class="col-xs-12">
                                        <input type="radio" id="drillDownGender" name="filterBy" value="All" checked onclick="document.getElementById('regions').disabled = true;document.getElementById('provinces').disabled = true;">
                                        <label for="actName">Select All</label>
                                        &nbsp;&nbsp;
                                        <input type="radio" id="drillDownGender" name="filterBy" value="regions" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = true;">
                                        <label for="actName">Regions</label>
                                        &nbsp;&nbsp;
                                        <input type="radio" id="drillDownGender" name="filterBy" value="provinces" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = false;">
                                        <label for="actName">Provinces</label>
                                    </div>
                                </div>
                                <div class="row no-print">
                                    <div class="col-xs-2">
                                        <div class="form-group">
                                            <label for="category">Demographic</label>
                                            <select name="demographic" id="demographic" class="form-control select2">
                                                <option value="">Gender</option>
                                                <option value="">Age</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <div class="form-group">
                                            <label for="actName">Category</label>
                                            <select name="category" id="category" class="form-control select2" >
                                                <option value="">Credit Rating</option>
                                                <option value="">Self Rating</option>
                                                <option value="">Crops</option>
                                                <option value="">Attendance Rate</option>
                                                <option value="">Count</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <div class="form-group">
                                            <label for="actName">Regions</label>
                                            <select name="regions[]" id="regions" onchange="chg2()" class="form-control select2" multiple="multiple" disabled>
                                                <option value="">Region I</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <div class="form-group">
                                            <label for="actName">Provinces</label>
                                            <select name="provinces[]" id="provinces" class="form-control select2" multiple="multiple" disabled>

                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-2" style="padding-top:20px;">
                                        <label for="">&nbsp;</label>
                                        <button type="submit" onclick="form.action = 'DashboardFilterGender'" class="btn btn-success"><i class="fa fa-filter margin-r-5"></i> Filter</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="row">
                                <div class="col-xs-3"></div>
                                <div class="col-xs-6">
                                    <div class="box-body" id="genderCanvas">
                                        <canvas id="pieCanvas"></canvas>
                                        <div class="row text-center">
                                            <a class="btn btn-submit" data-toggle="modal" data-target="#modalPie">View More</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-3"></div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box box-solid" style="border-color: lightgrey; padding-bottom: 20px" >
                                <div class="box-body">
                                    <h5><strong>KEY FINDINGS</strong></h5>                                   
                                    <div class="col-xs-3">
                                        <h6><strong>DEMOGRAPHIC: </strong> </h6>
                                    </div>
                                    <div class="col-xs-3">
                                        <h6><strong>CATEGORY: </strong> </h6>
                                    </div>
                                    <div class="col-xs-3">
                                        <h6><strong>REGION: </strong></h6>
                                    </div>
                                    <div class="col-xs-3">
                                        <h6><strong>PROVINCE: </strong> </h6>
                                    </div>                                   
                                </div>
                                <hr style="margin-left:20px; margin-right: 15px; margin-top: -10px;">
                                <div class="box-body" style="margin:0 auto">
                                     <div class="col-xs-1">
                                    </div>
                                    <div class="col-xs-5"  style="margin-left:5px; margin-right: 15px; margin-bottom: 10px; margin-top: 10px; background: lightgrey; display: inline; ">
                                        <h4>HIGHEST</h4>
                                        <p>This Region has the Highest</p>
                                    </div>
                                    <div class="col-xs-5"  style="margin-left:20px; margin-right: 15px; margin-bottom: 10px; margin-top: 10px; background: lightgrey; display: inline;">
                                        <h4>LOWEST</h4>
                                        <p>This Region has the Lowest</p>
                                    </div>
                                    <div class="col-xs-1">
                                    </div>
                                </div>
                         
                                <div class="box-body" style="margin:0 auto">
                                    <div class="col-xs-1">
                                    </div>
                                    <div class="col-xs-5"  style="margin-left:5px; margin-right: 15px; margin-bottom: 10px; margin-top: 10px; background: lightgrey; display: inline; ">
                                        <h4>HIGHEST</h4>
                                        <p>This Region has the Highest</p>
                                    </div>
                                    <div class="col-xs-5"  style="margin-left:20px; margin-right: 15px; margin-bottom: 10px; margin-top: 10px; background: lightgrey; display: inline;">
                                        <h4>LOWEST</h4>
                                        <p>This Region has the Lowest</p>
                                    </div>
                                    <div class="col-xs-1">
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>



                    <!-- this row will not appear when printing -->
                    <div class="row no-print">
                        <div class="col-xs-12">
                            <a href="invoice-print.html" target="_blank" class="btn btn-default"><i class="fa fa-print"></i> Print</a>
                            <button type="button" class="btn btn-success pull-right"><i class="fa fa-credit-card"></i> Submit Payment
                            </button>
                            <button type="button" class="btn btn-primary pull-right" style="margin-right: 5px;">
                                <i class="fa fa-download"></i> Generate PDF
                            </button>
                        </div>
                    </div>
                </section>
                <!-- /.content -->
                <div class="clearfix"></div>
            </div>
            <!-- /.content-wrapper -->
            <footer class="main-footer no-print">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 2.4.0
                </div>
                <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
                reserved.
            </footer>

        </div>
        <!-- /.content-wrapper -->
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>

    </body>
</html>
