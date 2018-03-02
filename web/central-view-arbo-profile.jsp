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
            <%
                            APCPRequest r = apcpRequestDAO.getRequestByID((Integer)request.getAttribute("requestID"));
                            ARBO a = arboDAO.getARBOByID(r.getArboID());
                            ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
            %>

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
                                    <div class="box-body" style="margin:5px 10px 5px 10px;">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Name of ARBO</label>
                                                    <input type="text" class="form-control" value="<%out.print(a.getArboName());%>" disabled >
                                                </div>
                                            </div>
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">No. of ARBs</label>
                                                    <input type="text" class="form-control" id="" placeholder="" value="<%out.print(arboDAO.getARBCount(a.getArboID()));%>" disabled>
                                                </div>
                                            </div>
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">Land Area (Hectares)</label>
                                                    <input type="text" class="form-control" id="" value="<%out.print(r.getHectares());%>" disabled >
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <label for=''>Loan Amount</label>
                                                <input type='text' class="form-control" id='' value="<%out.print(r.getLoanAmount());%>" disabled>
                                            </div>

                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">Reason for Loan</label>
                                                    <input type="text" class="form-control" value="<%out.print(r.getLoanReason());%>" disabled/>
                                                </div>
                                            </div>         
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <label for=''>Remarks</label>
                                                <textarea class="form-control" rows="3" disabled><%out.print(r.getRemarks());%></textarea>
                                            </div>

                                        </div>
                                    </div> 
                                </div>
                                <div class="chart tab-pane" id="info" style="position: relative;">
                                    <div class="col-xs-12" style="margin-top: 30px;">
                                        <div class="col-md-3">

                                            <!-- Profile Image -->
                                            <div class="box">
                                                <div class="box-body box-profile">
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
                                                <div class="box-header">
                                                    <h3>ARBO Members</h3>
                                                </div>
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
                                            </div>
                                            <!-- /.nav-tabs-custom -->
                                        </div>
                                        <!-- /.col -->
                                    </div>

                                </div>
                                <div class="chart tab-pane" id="history" style="position: relative;">
                                    <div class="box-body">     
                                        <div class="col-xs-12" style="margin-top:10px;">
                                            <table id="example3" class="table table-bordered table-striped">
                                                <thead>
                                                    <tr>
                                                        <th>CAPDEV Name</th>
                                                        <th>Date</th>
                                                        <th>No. of Participants</th>
                                                    </tr>
                                                </thead>

                                                <tbody>

                                                    <tr>

                                                        <td>Training</td>
                                                        <td>March 12, 2018</td>
                                                        <td>5</td>
                                                    </tr>

                                                </tbody>

                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <form method="post">
                                <div class="box-footer ">
                                    <div class="btn-group pull-right">
                                        <button type="submit" onclick="form.action = 'DisapproveNewAccessing?id=<%out.print(r.getRequestID());%>'" class="btn btn-danger">Disapprove</button>
                                        <button type="submit" onclick="form.action = 'ApproveNewAccessing?id=<%out.print(r.getRequestID());%>'" class="btn btn-success">Approve</button>
                                    </div>
                                </div>
                            </form>
                        </div>


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
