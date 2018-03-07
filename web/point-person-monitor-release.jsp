<%-- 
    Document   : field-officer-arbo-list
    Created on : Jan 29, 2018, 4:08:13 PM
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

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/point-person-sidebar.jspf"%>
            <%
                APCPRequest r = apcpRequestDAO.getRequestByID((Integer)request.getAttribute("requestID"));
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
                ArrayList<APCPRelease> releaseList = apcpRequestDAO.getAllAPCPReleasesByRequest((Integer)request.getAttribute("requestID"));
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
                                <div class="box-header with-border">
                                    <h3 class="box-title">APCP Information</h3>
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
                                            <li><a href="#profile" data-toggle="tab">ARBO Profile</a></li>
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
                                                            <textarea class="form-control" rows="3" disabled><%out.print(r.getRemarks());%> </textarea>
                                                        </div>

                                                    </div>
                                                </div> 
                                            </div>
                                            <div class="chart tab-pane" id="profile" style="position: relative;">


                                            </div>
                                            <div class="chart tab-pane" id="history" style="position: relative;">


                                            </div>
                                        </div>
                                    </div>
                                    <hr>        
                                </div>
                                <!-- /.box-body -->

                            </div>
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Release Information</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <!-- /.box-header -->


                                <div class="box-body"> 

                                    <div class="nav-tabs-custom">
                                        <!-- Tabs within a box -->
                                        <ul class="nav nav-tabs pull-left">
                                            <li class="active"><a href="#release" data-toggle="tab">Release</a></li>
                                            <li><a href="#repayment" data-toggle="tab">Repayment</a></li>
                                            <li><a href="#pastdue" data-toggle="tab">Past Due</a></li>
                                        </ul>

                                        <div class="tab-content no-padding">
                                            <!-- Morris chart - Sales -->
                                            <div class="chart tab-pane active" id="release" style="position: relative;">
                                                <div class="box-body">

                                                    <table id="example1" class="table table-bordered table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th>Release Amount</th>
                                                                <th>Release Date</th>
                                                                <th>Released By</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <%
                                                                for(APCPRelease release : releaseList){
                                                                    User u = new User(); 
                                                                    u = uDAO.searchUser(release.getReleasedBy());
                                                            %>
                                                            <tr>
                                                                <td><a href="MonitorDisbursement?releaseID=<%out.print(release.getReleaseID());%>&requestID=<%out.print(r.getRequestID());%>"><%out.print(release.getReleaseAmount());%></a></td>
                                                                <td><%out.print(f.format(release.getReleaseDate()));%></td>
                                                                <td><%out.print(u.getFullName());%></td>
                                                            </tr>
                                                            <%}%>
                                                        </tbody>

                                                        <tfoot>
                                                        <th>Release Amount</th>
                                                        <th>Release Date</th>
                                                        <th>Released By</th>
                                                        </tfoot>

                                                    </table>
                                                </div>
                                                <div class="box-footer">
                                                    <button type="button" class="btn btn-primary pull-right" data-toggle="modal" data-target="#add-release-modal">Add Release</button>
                                                </div>
                                            </div>
                                            <div class="chart tab-pane" id="repayment" style="position: relative;">
                                                <div class="box-body">

                                                    <table id="example3" class="table table-bordered table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th>Repayment Amount</th>
                                                                <th>Repayment Date</th>
                                                                <th>Repaid By</th>
                                                                <th>Recorded By</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <%
                                                                for(Repayment repayment : r.getRepayments()){
                                                                    User u = new User(); 
                                                                    u = uDAO.searchUser(repayment.getRecordedBy());
                                                                    ARB arb = new ARB();
                                                                    arb = arbDAO.getARBByID(repayment.getArbID());
                                                            %>
                                                            <tr>
                                                                <td><%out.print(repayment.getAmount());%></td>
                                                                <td><%out.print(f.format(repayment.getDateRepayment()));%></td>
                                                                <td><%out.print(arb.getFLName());%></td>
                                                                <td><%out.print(u.getFullName());%></td>
                                                            </tr>
                                                            <%}%>
                                                        </tbody>

                                                        <tfoot>
                                                            <tr>
                                                                <th>Repayment Amount</th>
                                                                <th>Repayment Date</th>
                                                                <th>Repaid By</th>
                                                                <th>Recorded By</th>
                                                            </tr>
                                                        </tfoot>

                                                    </table>
                                                </div>
                                                <div class="box-footer">
                                                    <button type="button" class="btn btn-primary pull-right" data-toggle="modal" data-target="#add-repayment-modal">Add Repayment</button>
                                                </div>

                                            </div>
                                            <div class="chart tab-pane" id="pastdue" style="position: relative;">
                                                <div class="box-body">

                                                    <table id="example4" class="table table-bordered table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th>Repayment Amount</th>
                                                                <th>Repayment Date</th>
                                                                <th>Repaid By</th>
                                                                <th>Recorded By</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <%
                                                                for(Repayment repayment : r.getRepayments()){
                                                                    User u = new User(); 
                                                                    u = uDAO.searchUser(repayment.getRecordedBy());
                                                                    ARB arb = new ARB();
                                                                    arb = arbDAO.getARBByID(repayment.getArbID());
                                                            %>
                                                            <tr>
                                                                <td><%out.print(repayment.getAmount());%></td>
                                                                <td><%out.print(f.format(repayment.getDateRepayment()));%></td>
                                                                <td><%out.print(arb.getFLName());%></td>
                                                                <td><%out.print(u.getFullName());%></td>
                                                            </tr>
                                                            <%}%>
                                                        </tbody>

                                                        <tfoot>
                                                            <tr>
                                                                <th>Repayment Amount</th>
                                                                <th>Repayment Date</th>
                                                                <th>Repaid By</th>
                                                                <th>Recorded By</th>
                                                            </tr>
                                                        </tfoot>

                                                    </table>
                                                </div>
                                                <div class="box-footer">
                                                    <button type="button" class="btn btn-primary pull-right" data-toggle="modal" data-target="#add-repayment-modal">Add Repayment</button>
                                                </div>

                                            </div>
                                        </div>
                                    </div>


                                </div>
                                <!-- /.box-body -->

                            </div> 
                        </div>
                        <!-- /.col -->
                    </div>

                    <div class="modal fade" id="add-release-modal">
                        <div class="modal-dialog modal-md">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Record Release</h4>
                                </div>

                                <form method="post">
                                    <div class="modal-body" id="modalBody">
                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Release Amount</label>
                                                    <input type="text" class="form-control" name="releaseAmount" required>
                                                </div>
                                            </div>
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Release Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                                        <input type="date" class="form-control" name="releaseDate" />        
                                                    </div>
                                                </div>
                                            </div>

                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <button type="submit" onclick="form.action = 'RecordRequestRelease'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>

                            </div>
                            <!--                                            /.modal-content -->
                        </div>
                        <!--                                        /.modal-dialog -->
                    </div>

                    <div class="modal fade" id="add-repayment-modal">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Record Repayment</h4>
                                </div>

                                <form method="post">
                                    <div class="modal-body" id="modalBody">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <table id="example1" class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>ARB Name</th>
                                                            <th>Address</th>
                                                            <th>Crops</th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </thead>

                                                    <tbody>
                                                        <%for(ARB arb : arbList){%>
                                                        <tr>
                                                            <td><%out.print(arb.getFLName());%></td>
                                                            <td><%out.print(arb.getFullAddress());%></td>
                                                            <td><%out.print(arb.printAllCrops());%></td>

                                                            <td>
                                                                <input type="checkbox" name="arbIDs" value="<%=arb.getArbID()%>"/>
                                                            </td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>

                                                </table>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Amount</label>
                                                    <input type="text" class="form-control" name="repaymentAmount" required>
                                                </div>
                                            </div>
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Repayment Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                                        <input type="date" class="form-control" name="repaymentDate" />        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <button type="submit" name="manual" onclick="form.action = 'RecordRepayment'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>

                            </div>
                            <!--                                            /.modal-content -->
                        </div>
                        <!--                                        /.modal-dialog -->
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


    </body>
</html>
