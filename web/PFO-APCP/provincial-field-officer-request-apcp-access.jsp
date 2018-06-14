<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/jspf/header.jspf"%>

        <style>
            .example-modal .modal {
                position: relative;
                top: auto;
                bottom: auto;
                right: auto;
                left: auto;
                display: block;
                z-index: 1;
            }

            .example-modal .modal {
                background: transparent !important;
            }
            @media screen and (min-width: 992px) {
                .modal-lg {
                    width: 1080px; /* New width for large modal */
                }
            }
        </style>
    </head>


    <!DOCTYPE html>


    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="/jspf/field-officer-navbar.jspf" %>
            <%@include file="/jspf/pfo-apcp-sidebar.jspf" %>
            <%
                int arboID = (Integer) request.getAttribute("arboID");
                ARBO arbo = arboDAO.getARBOByID(arboID);
                ArrayList<APCPDocument> refDocuments = apcpRequestDAO.getAllAPCPDocuments();
                ArrayList<LoanReason> refLoanReasons = apcpRequestDAO.getAllLoanReasonsByAPCPType(1); // Since new requesting, only Crop Production Loan Reasons
                ArrayList<LoanTerm> refLoanTerms = apcpRequestDAO.getAllLoanTerms();
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
                    
                    <%if(request.getAttribute("errMessage") != null){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String)request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>

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

                                <div class="row">
                                    <button id="btnAddConduitDocu" type="button" class="btnAddConduitDocu btn btn-primary center-block">Add Document</button>    
                                </div>

                                <br>


                                <div class="row">
                                    <div class="col-xs-2"></div>
                                    <div class="col-xs-8">
                                        <table id="conduitTable" class="table table-bordered table-striped" >
                                            <tbody id="conduitWrapper">
                                                <%for(APCPDocument d : refDocuments){%>
                                                <%if(d.getIsRequired() == 1 && d.getDocumentType() == 1){ // REQUIRED and CONDUIT%>
                                                <tr>
                                            <input type="hidden" name="documentID" value="<%out.print(d.getDocument());%>">
                                            <input type="hidden" name="documentName" value="N/A">
                                            <td style="border: transparent;"><h5> &#9632; &nbsp; <%out.print(d.getDocumentDesc());%>:</h5></td>
                                            <td style="border: transparent;">
                                                <div class="input-group date">
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                    <input type="date" name="dateSubmitted" class="form-control pull-right" id="datepicker">
                                                </div> 
                                            </td>
                                            <td style="border: transparent">
                                                &nbsp;
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
                                        <!-- /.form-group -->
                                        <div class="form-group">
                                            <label for="Area">Land Area</label>
                                            <input type="number" step=".01" class="form-control" name="landArea" id="Area" required>
                                        </div>
                                        <!-- /.form-group -->
                                    </div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>Loan Amount</label>
                                            <div class="input-group">
                                                <div class="input-group-addon">
                                                    <i>&#8369;</i>
                                                </div>
                                                <input id="loanAmount" type='number' name="loanAmount" class="form-control numberOnly" required>
                                            </div>
                                        </div>
                                    </div>


                                </div>
                                <div class="row">
                                    <div class="col-xs-1"></div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>Loan Reason</label>
                                            <select class="form-control select2" name="loanReason" id="loanReason" style="width: 100%;">
                                                <%for(LoanReason r : refLoanReasons){%>
                                                <option value="<%out.print(r.getLoanReason());%>"><%out.print(r.getLoanReasonDesc());%></option>
                                                <%}%>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-2"></div>
                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>Other Reason</label>
                                            <input type="text" id="otherReason" name="otherReason" class="form-control" disabled>
                                        </div>
                                    </div>
                                    <div class="col-xs-1"></div>
                                </div>

                                <div class="row">
                                    <div class="col-xs-4">
                                        <div class="form-group">
                                            <label for="">Loan Term</label>
                                            <select name="loanTerm" class="form-control" id="loanTerm">
                                                <%for(LoanTerm t : refLoanTerms){%>
                                                <option value="<%out.print(t.getLoanTerm());%>"><%out.print(t.getLoanTermDesc());%></option>
                                                <%}%>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-4">
                                        <div class="form-group">
                                            <label for="">Minimum Duration (Months)</label>
                                            <input id="minDuration" type="number" class="form-control" value="12" name="minDuration" disabled />
                                        </div>
                                    </div>
                                    <div class="col-xs-4">
                                        <div class="form-group">
                                            <label for="">Maximum Duration (Months)</label>
                                            <input id="maxDuration" type="number" class="form-control" min="13" max="36" name="maxDuration" />
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-xs-12">
                                        <label>Loan Recipients</label>
                                        <table id="example3" class="table table-bordered table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Action</th>
                                                    <th>Full Name</th> 
                                                    <th>Membership Date</th> 
                                                    <th>COMAT</th> 
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%for(ARB arb : arbo.getArbList()){%>
                                                <tr>
                                                    <td><input type="checkbox" name="arbID" value="<%out.print(arb.getArbID());%>"></td>
                                                    <td><%out.print(arb.getFLName());%></td>
                                                    <td><%out.print(f.format(arb.getMemberSince()));%></td>
                                                    <%if(arb.getIsCOMAT() > 0){%>
                                                    <td>&checkmark;</td>
                                                    <%}else{%>
                                                    <td>&nbsp;</td>
                                                    <%}%>
                                                </tr>
                                                <%}%>
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <th>Action</th>
                                                    <th>Full Name</th> 
                                                    <th>COMAT</th> 
                                                </tr>
                                            </tfoot>
                                        </table>
                                        <div class="form-group pull-right">
                                            <input type="file" name="arbListExcel">
                                            <p class="help-block">Upload Recipient List</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-header with-border">
                                    <h5 class="box-title"><strong>Supporting Documents</strong></h5>
                                </div>
                                <br>
                                <div class="row">
                                    <center>
                                        <div class="btn-group">
                                            <button class="btn btn-primary dropdown-toggle" data-toggle="dropdown">Add Document <span class="caret"></span></button>
                                            <ul id="optional-list" class="dropdown-menu" role="menu">
                                                <%for(APCPDocument document : refDocuments){%>
                                                <%if(document.getIsRequired() == 0 && document.getDocumentType() == 2){ // OPTIONAL and APCP%>
                                                <li><a class="optional-document" data-documentID="<%out.print(document.getDocument());%>" data-documentDesc="<%out.print(document.getDocumentDesc());%>"><%out.print(document.getDocumentDesc());%></a></li>
                                                    <%}%>
                                                    <%}%>
                                            </ul>
                                        </div>
                                    </center>
                                </div>

                                <br>
                                <div class="row">
                                    <div class="col-xs-2"></div>
                                    <div class="col-xs-8">
                                        <table id="supportingTable" class="table table-bordered table-striped" >
                                            <tbody id="supportingWrapper">
                                                <%for(APCPDocument d : refDocuments){%>
                                                <%if(d.getIsRequired() == 1 && d.getDocumentType() == 2){ // REQUIRED and APCP%>
                                                <tr>
                                            <input type="hidden" name="documentID" value="<%out.print(d.getDocument());%>">
                                            <input type="hidden" name="documentName" value="N/A">
                                            <td style="border: transparent;"><h5> &#9632; &nbsp; <%out.print(d.getDocumentDesc());%>:</h5></td>
                                            <td style="border: transparent;">
                                                <div class="input-group date">
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                    <input type="date" name="dateSubmitted" class="form-control pull-right" id="datepicker">
                                                </div> 
                                            </td>
                                            <td style="border: transparent">
                                                &nbsp;
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
                                            <textarea name="remarks" cols="20" rows="8" class="form-control"></textarea>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="button" class="btn btn-success pull-right" data-toggle="modal" data-target="#confirm">Submit</button>
                            </div>
                            <div class="modal fade" id="confirm">
                                <div class="modal-dialog modal-sm">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span></button>
                                            <h4 class="modal-title">Confirm Submission</h4>
                                        </div>
                                        <div class="modal-body" id="modalBody">
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <h5>Do you wish to proceed?</h5>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" name="arboID" value="<%out.print(arbo.getArboID());%>">
                                            <button type="submit" name="manual" onclick="form.action = 'RequestConduit'" class="btn btn-primary pull-right"><i class="fa fa-send margin-r-5"></i> Submit</button>
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

        </div>
        <!-- ./wrapper -->

        <%@include file="/jspf/footer.jspf"%>  
        <script type="text/javascript">
            $(document).ready(function () {
                $('#loanReason').on('change', function () { // 
                    if (this.value > 0) {
                        $('#otherReason').prop('disabled', true);
                        $('#otherReason').prop('required', false);
                    } else { // If 'Others' is selected
                        $('#otherReason').prop('disabled', false);
                        $('#otherReason').prop('required', true);
                    }
                });

                var max = $('#maxDuration').attr('min');
                $('#maxDuration').val(max);

                $('#loanTerm').on('change', function () {
                    if (this.value == 1) {
                        $('#minDuration').val('12');
                        $('#maxDuration').attr({
                            "min": 13,
                            "max": 36
                        });
                    } else if (this.value == 2) {
                        $('#minDuration').val('36');
                        $('#maxDuration').attr({
                            "min": 37,
                            "max": 84
                        });
                    } else if (this.value == 3) {
                        $('#minDuration').val('12');
                        $('#maxDuration').attr({
                            "min": 13,
                            "max": 36
                        });
                    } else if (this.value == 4) {
                        $('#minDuration').val('12');
                        $('#maxDuration').attr({
                            "min": 13,
                            "max": 60
                        });
                    }

                    var max = $('#maxDuration').attr('min');
                    $('#maxDuration').val(max);
                });
            <%
                    int optionalConduitCount = 0;
                    int optionalSupportingCount = 0;
                    for(APCPDocument document : refDocuments){
                        if(document.getIsRequired() == 0 && document.getDocumentType() == 1){ // gets count of OPTIONAL & CONDUIT documents
                            optionalConduitCount++;
                        }else if(document.getIsRequired() == 0 && document.getDocumentType() == 2 && document.getDocument() != 12){ // gets count of OPTIONAL & SUPPORTING documents
                            optionalSupportingCount++;
                        }
                    }
            %>
                //----- CONDUIT -----
                var max_fields = <%out.print(optionalConduitCount);%>; //maximum input boxes allowed
                var conduitWrapper = $('#conduitWrapper'); //Fields wrapper
                var btnAddConduitDocu = $('#btnAddConduitDocu'); //Add CONDUIT button
                var conduitDocuMarkup = '<tr><input type="hidden" name="documentName" value="N/A"><td  style="border: transparent;"><div class="form-group"><select name="documentID" id="" class="form-control select2"><%for(APCPDocument document : refDocuments){%><%if(document.getIsRequired() == 0 && document.getDocumentType() == 1){%> <option value="<%out.print(document.getDocument());%>"><%out.print(document.getDocumentDesc());%></option><%}%><%}%></select></div></td><td  style="border: transparent;"><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="dateSubmitted" class="form-control pull-right" id="datepicker"></div> </td><td style="border: transparent;"><button type="button" class="delete-row-conduit btn btn-danger"><i class="fa fa-close"></i></button></td></tr>';
                var x = 0; //initlal text box count

                $(btnAddConduitDocu).click(function (e) { //on add input button click
                    e.preventDefault();
                    if (x < max_fields) { //max input box allowed
                        x++; //text box increment
                        $(conduitWrapper).append(conduitDocuMarkup);
                        $('.select2').select2();
                    }
                });

                $('#conduitTable').on("click", ".delete-row-conduit", function (event) {
                    $(this).closest("tr").remove();
                    x--;
                });

                //----- SUPPORTING -----
                var max_fields = <%out.print(optionalSupportingCount);%>; //maximum input boxes allowed
                var supportingWrapper = $('#supportingWrapper'); //Fields wrapper
                var btnAddSupportingDocu = $('#btnAddSupportingDocu'); //Add Supporting button
                var btnAddOthers = $('#btnAddOthers');
                var supportingDocuMarkup = '<tr><input type="hidden" name="documentName" value="N/A"><td  style="border: transparent;"><div class="form-group"><select name="documentID" id="" class="form-control select2"><%for(APCPDocument document : refDocuments){%><%if(document.getIsRequired() == 0 && document.getDocumentType() == 2 && document.getDocument() != 0){%> <option value="<%out.print(document.getDocument());%>"><%out.print(document.getDocumentDesc());%></option><%}%><%}%></select></div></td><td  style="border: transparent;"><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="dateSubmitted" class="form-control pull-right" id="datepicker"></div> </td><td style="border: transparent;"><button type="button" class="delete-row-supporting btn btn-danger"><i class="fa fa-close"></i></button></td></tr>';
                var othersMarkup = '<tr><input type="hidden" name="documentDesc" value="N/A"><input type="hidden" name="documentID" value="12"><td style="border: transparent;"><div class="form-group"><input type="text" name="documentName" class="form-control" /></div></td><td style="border: transparent;"><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="dateSubmitted" class="form-control pull-right" id="datepicker"></div> </td><td style="border: transparent;"><button type="button" class="delete-row-supporting btn btn-danger"><i class="fa fa-close"></i></button></td></tr>';
                var y = 0; //initlal text box count

                $(btnAddSupportingDocu).click(function (e) { //on add input button click
                    e.preventDefault();

                    if (y < max_fields) { //max input box allowed
                        y++; //text box increment
                        $(supportingWrapper).append(supportingDocuMarkup);
                        $('.select2').select2();
                    }
                });

                $(btnAddOthers).click(function (e) { //on add input button click
                    e.preventDefault();
                    $(supportingWrapper).append(othersMarkup);
                    $('.select2').select2();
                });

                $('#supportingTable').on("click", ".delete-row-supporting", function (event) {
                    $(this).closest("tr").remove();
                    y--;
                });

                $('#optional-list').on("click", ".optional-document", function (e) {
                    e.preventDefault();
                    var documentID = parseInt($(this).attr('data-documentID'));
                    var documentDesc = $(this).attr('data-documentDesc');

                    var markup = "";
                    if (documentID === 12) { // OTHERS
                        markup = '<tr><input type="hidden" name="documentDesc" value="N/A"><input type="hidden" name="documentID" value="' + documentID + '"><td style="border: transparent;"><div class="form-group"><input type="text" name="documentName" class="form-control" /></div></td><td style="border: transparent;"><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="dateSubmitted" class="form-control pull-right" id="datepicker"></div> </td><td style="border: transparent;"><button type="button" class="delete-row-supporting btn btn-danger"><i class="fa fa-close"></i></button></td></tr>';
                    } else {
                        markup = '<tr><input type="hidden" name="documentName" value="N/A"><input type="hidden" name="documentID" value="' + documentID + '"><td style="border: transparent;"><h5> &#9632; &nbsp; ' + documentDesc + ':</h5></td><td style="border: transparent;"><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="dateSubmitted" class="form-control pull-right" id="datepicker"></div> </td><td style="border: transparent;"><button type="button" class="delete-row-supporting btn btn-danger"><i class="fa fa-close"></i></button></td></tr>';
                    }
                    $(supportingWrapper).append(markup);

                });
            });

        </script>

    </body>


</html>
