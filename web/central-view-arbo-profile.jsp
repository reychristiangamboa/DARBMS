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
                        ARBO Profile
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="/DAR-BMS/pages/tables/FO-Homepage.html"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="/DAR-BMS/pages/tables/FO-ARBO-ARBList.html">(ARBO Name) Beneficiary List</a></li>
                        <li class="active">ARB Profile</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">

                    <div class="row">
                        <div class="col-md-3">

                            <!-- Profile Image -->
                            <div class="box box-primary">
                                <div class="box-body box-profile">
                                    <img class="profile-user-img img-responsive img-circle" src="../../dist/img/user4-128x128.jpg" alt="User profile picture">

                                    <h3 class="profile-username text-center">Christopher Jorge P. Francisco</h3>
                                    <div class="rate center-block">
                                        <span class="fa fa-star checked" style></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star checked"></span>
                                        <span class="fa fa-star"></span>
                                        <span class="fa fa-star"></span>
                                    </div>
                                    <p class="text-muted text-center">Agrarian Reform Beneficiary Organization</p>

                                    <ul class="list-group list-group-unbordered">
                                        <li class="list-group-item">
                                            <b>Land Area</b> <a class="pull-right">10 Hectares</a>
                                        </li>
                                        <li class="list-group-item">

                                            <b>Location</b> <a class="pull-right">Region I - Somewhere </a>
                                        </li>
                                        <li class="list-group-item">

                                            <b>Crops</b> <a class="pull-right">Rice, Potato, Corn, Carrot</a>
                                        </li>
                                        <li class="list-group-item">

                                            <b>No. of Members</b> <a class="pull-right">543</a>
                                        </li>
                                    </ul>

                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->

                            <!-- About Me Box -->

                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                        <div class="col-md-9">
                            <div class="box">
                                <div class="nav-tabs-custom">
                                    <ul class="nav nav-tabs">
                                        <li class="active"><a href="#APCP" data-toggle="tab">ARBO Members</a></li>
                                    </ul>
                                    <div class="tab-content">
                                        <div class="active tab-pane" id="APCP">
                                            <div class="box-body">
                                                <table id="example1" class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>ARB Name</th>
                                                            <th>Address</th>
                                                            <th>Crops</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>

                                                        <tr>
                                                            <td><a href="#">Lanz Naguit</a></td>
                                                            <td>1653-B Antonio Rivera Street</td>
                                                            <td>Rice</td>
                                                        </tr>

                                                    </tbody>


                                                </table>
                                            </div>
                                            <div class="box-footer ">
                                                <div class="btn-group pull-right">
                                                    <button type="button" class="btn btn-success">Approve</button>
                                                    <button type="button" class="btn btn-danger">                                                       
                                                    Disapprove
                                                    </button>
                                                </div>

                                            </div>
                                        </div>

                                    </div>
                                    <!-- /.tab-content -->
                                </div>
                            </div>
                            <!-- /.nav-tabs-custom -->
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
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
