<%-- 
    Document   : view-new-accessing-arbo
    Created on : Jun 5, 2018, 9:42:53 PM
    Author     : Rey Christian
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/jspf/header.jspf"%>
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

            <%@include file="/jspf/field-officer-navbar.jspf"%>
            <%@include file="/jspf/provincial-field-officer-sidebar.jspf"%>
            <%
                APCPRequest r = apcpRequestDAO.getRequestByID((Integer) request.getAttribute("requestID"));
                System.out.print("RequestID = " +r.getRequestID());
                ARBO arbo = arboDAO.getARBOByID(r.getArboID());
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-money"></i> APCP Request for New Accessing Conduits</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>

                </section>

                <!-- Main content -->
                <section class="content">

                    <div class="box box-default">
                        <div class="box-header with-border">
                            <h4 class="box-title"><strong>ARBO INFO</strong></h4>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <form method="post">
                            <div class="box-body">
                                <div class="row">
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label for="">Name of ARBO</label>
                                            <input type="text" class="form-control" value="<%out.print(arbo.getArboName());%>"disabled >
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <!-- /.form-group -->
                                        <div class="form-group">
                                            <label for="Type">ARBO Type</label>
                                            <input type="text" class="form-control" id="Type" value="<%out.print(arbo.getArboTypeDesc());%>">
                                        </div>
                                        <!-- /.form-group -->
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>ARBO Members</label>

                                            <div class="input-group">
                                                <input type="text" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                                <div class="input-group-btn">
                                                    <button type="button" class="btn btn-info">View</button>
                                                </div>
                                            </div>
                                            <!-- /.input group -->
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-md-8">
                                        <div class="form-group">
                                            <label>Address</label>
                                            <input type="text" class="form-control" id="address" value="<%out.print(arbo.getFullAddress());%>" disabled>
                                        </div>
                                    </div>
                                </div>
                                <!-- /.row -->
                                <div class="box-header with-border">
                                    <h4 class="box-title"><strong>Conduit Qualification Checklist</strong></h4>
                                </div>
                                <div class="row" style="margin-top: 10px;">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="exampleInputFile"></label>
                                            <div class="input-group date" style="border: transparent;">
                                                <div class="input-group-addon" style="border: transparent;">
                                                    <i class="fa fa-check"></i>
                                                </div>
                                                <div class="input-group-addon" style="border: transparent;">
                                                    <strong>Operational date: </strong>
                                                </div>
                                                <input type="text" class="form-control pull-right" id="datepicker" value="<%out.print(f.format(arbo.getDateOperational()));%>" style="border: transparent; background: transparent;" disabled="true">
                                            </div>
                                            <p class="help-block"></p>
                                        </div>
                                    </div>

                                </div>
                                <br>

                                <div class="row">
                                    <div class="col-xs-2"></div>
                                    <div class="col-xs-8">
                                        <table id="conduitTable" class="table table-bordered table-striped" >
                                            <tbody id="conduitWrapper">
                                                <%for(APCPDocument d : r.getApcpDocument()){%>
                                                <%if(d.getIsRequired() == 1 && d.getDocumentType() == 1){ // REQUIRED and CONDUIT%>
                                                <tr>
                                                    <td style="border: transparent;"><h5> &#9632; &nbsp; <%out.print(d.getDocumentDesc());%>:</h5></td>
                                                    <td style="border: transparent;">
                                                        <div class="input-group date">
                                                            <div class="input-group-addon">
                                                                <i class="fa fa-calendar"></i>
                                                            </div>
                                                            <input type="date" name="dateSubmitted" value="<%out.print(d.getDateSubmitted());%>" class="form-control pull-right" id="datepicker">
                                                        </div> 
                                                    </td>
                                                </tr>
                                                <%}%>
                                                <%}%>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="col-xs-2"></div>
                                </div>
                                <div class="box-header with-border">
                                    <h4 class="box-title"><strong>LOAN INFORMATION</strong></h4>
                                </div>
                                <div class="row" style="margin-top: 10px">
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>APCP Type</label>
                                            <input type="text" value="Crop Production" class="form-control" disabled />
                                        </div>
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>Loan Reason</label>
                                            <input type="text" class="form-control" value="<%out.print(r.getLoanReason().getLoanReasonDesc());%>">
                                        </div>
                                    </div>

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>Other Reason</label>
                                            <input type="text" id="otherReason" name="otherReason" class="form-control" disabled>
                                        </div>
                                    </div>

                                </div>
                                <div class="row">
                                    <div class="col-md-1">
                                    </div>
                                    <div class="col-md-4">
                                        <!-- /.form-group -->
                                        <div class="form-group">
                                            <label for="Area">Land Area</label>
                                            <input type="number" value="<%out.print(r.getHectares());%>" class="form-control" name="landArea" id="Area" required>
                                        </div>
                                        <!-- /.form-group -->
                                    </div>
                                    <div class="col-md-2">
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>Loan Amount</label>
                                            <div class="input-group">
                                                <div class="input-group-addon">
                                                    <i>&#8369;</i>
                                                </div>
                                                <input id="loanAmount" value="<%out.print(currency.format(r.getLoanAmount()));%>" name="loanAmount" class="form-control numberOnly" required>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-1">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label>Loan Recipients</label>
                                        <table id="example3" class="table table-bordered table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Full Name</th> 
                                                    <th>Membership Date</th> 
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%for(ARB arb : r.getRecipients()){%>
                                                <tr>
                                                    <td><%out.print(arb.getFLName());%></td>
                                                    <td><%out.print(f.format(arb.getMemberSince()));%></td>
                                                </tr>
                                                <%}%>
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <th>Full Name</th> 
                                                    <th>Membership Date</th> 
                                                </tr>
                                            </tfoot>
                                        </table>
                                    </div>
                                </div>
                                <div class="box-header with-border">
                                    <h5 class="box-title"><strong>Supporting Documents</strong></h5>
                                </div>
                                <br>

                                <br>
                                <div class="row">
                                    <div class="col-xs-2"></div>
                                    <div class="col-xs-8">
                                        <table id="supportingTable" class="table table-bordered table-striped" >
                                            <tbody id="supportingWrapper">
                                                <%for(APCPDocument d : r.getApcpDocument()){%>
                                                <%if(d.getIsRequired() == 1 && d.getDocumentType() == 2){ // REQUIRED and APCP%>
                                                <tr>
                                                    <td style="border: transparent;"><h5> &#9632; &nbsp; <%out.print(d.getDocumentDesc());%>:</h5></td>
                                                    <td style="border: transparent;">
                                                        <div class="input-group date">
                                                            <div class="input-group-addon">
                                                                <i class="fa fa-calendar"></i>
                                                            </div>
                                                            <input type="date" name="dateSubmitted" value="<%out.print(d.getDateSubmitted());%>" class="form-control pull-right" id="datepicker">
                                                        </div> 
                                                    </td>
                                                </tr>
                                                <%}%>
                                                <%}%>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="col-xs-2"></div>
                                </div>

                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="form-group">
                                            <label for="">Remarks</label>
                                            <textarea name="remarks" cols="10" rows="8" class="form-control"><%out.print(r.getRemarks());%></textarea>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <div class="btn-group pull-right">
                                    <button type="button" class="btn btn-success pull-right" data-toggle="modal" data-target="#approve">Approve</button>
                                    <button type="button" class="btn btn-danger pull-right" data-toggle="modal" data-target="#disapprove">Disapprove</button>
                                </div>

                            </div>
                            <div class="modal fade" id="disapprove">
                                <div class="modal-dialog modal-sm">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title">Confirm Disapproval</h4>
                                        </div>
                                        <div class="modal-body" id="modalBody">
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <h5>Are you sure you want to disapprove request?</h5>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" name="id" value="<%out.print(r.getRequestID());%>">
                                            <button type="submit" name="manual" onclick="form.action = 'DisapproveNewAccessing'" class="btn btn-danger pull-right"><i class="fa fa-ban margin-r-5"></i> Disapprove</button>
                                        </div>
                                    </div>
                                    <!--                                            /.modal-content -->
                                </div>
                                <!--                                        /.modal-dialog -->
                            </div>
                            <div class="modal fade" id="approve">
                                <div class="modal-dialog modal-sm">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title">Confirm Approval</h4>
                                        </div>
                                        <div class="modal-body" id="modalBody">
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <h5>Are you sure you want to approve request?</h5>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" name="id" value="<%out.print(r.getRequestID());%>">
                                            <button type="submit" name="manual" onclick="form.action = 'ApproveNewAccessing'" class="btn btn-success pull-right"><i class="fa fa-thumbs-up margin-r-5"></i> Approve</button>
                                        </div>
                                    </div>
                                    <!--                                            /.modal-content -->
                                </div>
                                <!--                                        /.modal-dialog -->
                            </div>
                        </form>
                    </div>
                    <!-- /.box -->


                </section>
                <!-- /.content -->
            </div>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->
    </div>
    <!-- ./wrapper -->
    <%@include file="/jspf/footer.jspf" %>

</body>
</html>
