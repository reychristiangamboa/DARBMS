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
                APCPRelease release = apcpRequestDAO.getAPCPReleaseByID((Integer)request.getAttribute("releaseID"));
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
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
                                <!-- /.box-body -->

                            </div>
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Disbursement Information</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <!-- /.box-header -->


                                <div class="box-body"> 

                                    <div class="nav-tabs-custom">
                                        <!-- Tabs within a box -->
                                        <ul class="nav nav-tabs pull-left">
                                            <li class="active"><a href="#request" data-toggle="tab">Disbursement Table</a></li>
                                        </ul>

                                        <div class="tab-content no-padding">
                                            <!-- Morris chart - Sales -->
                                            <div class="chart tab-pane active" id="request" style="position: relative;">
                                                <div class="box-body">
                                                    <div class="row">
                                                        <div class="col-xs-3">
                                                            <div class="form-group">
                                                                <label for="">Release Amount</label>
                                                                <input type="text" class="form-control" id="" placeholder="" value="<%=release.getReleaseAmount()%>" disabled>
                                                            </div>
                                                        </div>
                                                        <div class="col-xs-3">
                                                            <div class="form-group">
                                                                <label for="">Release Date</label>
                                                                <input type="text" class="form-control" id="" value="<%out.print(f.format(release.getReleaseDate()));%>" disabled >
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <table id="example1" class="table table-bordered table-striped">
                                                        <thead>
                                                            <tr>
                                                                <th>Disbursed To</th>
                                                                <th>Disbursement Amount</th>
                                                                <th>OS Balance</th>
                                                                <th>Disbursement Date</th>
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <%
                                                                for(Disbursement d : release.getDisbursements()){
                                                                    ARB arb = arbDAO.getARBByID(d.getArbID());
                                                            %>
                                                            <tr>

                                                                <td><%out.print(arb.getFLName());%></td>
                                                                <td><%out.print(d.getDisbursedAmount());%></td>
                                                                <td><%out.print(d.getOSBalance());%></td>
                                                                <td><%out.print(f.format(d.getDateDisbursed()));%></td>

                                                            </tr>
                                                            <%}%>

                                                        </tbody>

                                                    </table>
                                                </div> 
                                            </div>
                                        </div>
                                    </div>

                                    <div class="box-footer">
                                        <div class="pull-right">
                                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#import-disbursements-modal">Import Disbursements</button>
                                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#add-disbursement-modal">Add Disbursement</button>
                                        </div>

                                    </div>
                                </div>
                                <!-- /.box-body -->

                            </div> 
                        </div>
                        <!-- /.col -->
                    </div>

                    <div class="modal fade" id="add-disbursement-modal">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Record Disbursement</h4>
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
                                                                <input type="checkbox" name="arbID" value="<%=arb.getArbID()%>"/>
                                                            </td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>

                                                </table>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">Disbursement Amount</label>
                                                    <input type="text" class="form-control" name="disbursementAmount" required>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">O/S Balance</label>
                                                    <input type="text" class="form-control" name="OSBalance" required>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">Disbursement Date</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                                        <input type="date" class="form-control" name="disbursedDate" />        
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <input type="hidden" name="releaseID" value="<%=release.getReleaseID()%>">
                                        <button type="submit" name="manual" onclick="form.action = 'RecordDisbursement'" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>

                            </div>
                            <!--                                            /.modal-content -->
                        </div>
                        <!--                                        /.modal-dialog -->
                    </div>

                    <div class="modal fade" id="import-disbursements-modal">
                        <div class="modal-dialog modal-sm">
                            <div class="modal-content">

                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span></button>
                                    <h4 class="modal-title">Import Disbursements</h4>
                                </div>

                                <form method="post">
                                    <div class="modal-body" id="modalBody">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="form-group">
                                                    <label for="">Import File</label>
                                                    <input type="file" class="form-control" name="file" required>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="requestID" value="<%=r.getRequestID()%>">
                                        <input type="hidden" name="releaseID" value="<%=release.getReleaseID()%>">
                                        <button type="submit" name="manual" onclick="form.action = 'ImportDisbursement'" class="btn btn-primary pull-right">Submit</button>
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
        <script>
            $(document).ready(function () {
                var max_fields = 9; //maximum input boxes allowed
                var wrapper = $(".input_fields_wrap"); //Fields wrapper
                var add_button = $(".add_field_button"); //Add button ID
                var x = 0; //initlal text box count
                $(add_button).click(function (e) { //on add input button click
                    e.preventDefault();
                    if (x < max_fields) { //max input box allowed
                        x++; //text box increment
                        $('#count').val(x);
                        $(wrapper).append('<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Activity">Activity</label><select name="activities[]" class="form-control" id="Activity" style="width:100%;"><%for(CAPDEVActivity activity : activities){%><option value="<%out.print(activity.getActivityID());%>"><%out.print(activity.getActivityName());%></option><%}%></select></div></div><div class="col-xs-4"><div class="form-group"><label>Date</label><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="activityDates[]" class="form-control pull-right" id="datepicker"></div></div></div><div class="col-xs-4"><div class="form-group"><label for="">Participants</label><input type="file" name="file[]" /></div></div></div>');
                        $('.select2').select2();
                    }
                });



            });
        </script>

    </body>
</html>
