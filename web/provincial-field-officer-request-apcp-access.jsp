<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>

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

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>
            <%
                int arboID = (Integer) request.getAttribute("arboID");
                ARBO arbo = arboDAO.getARBOByID(arboID);
                ArrayList<APCPDocument> refDocuments = apcpRequestDAO.getAllAPCPDocuments();
                ArrayList<LoanReason> refLoanReasons = apcpRequestDAO.getAllLoanReasons();
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

                                <div class="row">
                                    <button id="btnAddConduitDocu" class="btn btn-primary center-block">Add Document</button>    
                                </div>

                                <br>


                                <div class="row">
                                    <div class="col-xs-2"></div>
                                    <div class="col-xs-8">
                                        <table id="example1" class="table table-bordered table-striped" >
                                            <tbody>
                                                <%for(APCPDocument d : refDocuments){%>
                                                <%if(d.getIsRequired() == 1 && d.getDocumentType() == 1){ // REQUIRED and CONDUIT%>
                                                <tr>
                                            <input type="hidden" name="documentID" value="<%out.print(d.getDocument());%>">
                                            <td style="border: transparent;"><h5> &#9632; &nbsp; <%out.print(d.getDocumentDesc());%>:</h5></td>
                                            <td style="border: transparent;">
                                                <div class="input-group date">
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                    <input type="date" name="dateSubmitted" class="form-control pull-right" id="datepicker">
                                                </div> 
                                            </td>
                                            </tr>
                                            <%}%>
                                            <%}%>

                                            <!--                                                <tr>
                                                                                                <td  style="border: transparent;"><h5> &#9632; &nbsp; Management Team Document:</h5></td>
                                                                                                <td  style="border: transparent;">
                                                                                                    <div class="input-group date">
                                                                                                        <div class="input-group-addon">
                                                                                                            <i class="fa fa-calendar"></i>
                                                                                                        </div>
                                                                                                        <input type="date" class="form-control pull-right" id="datepicker">
                                                                                                    </div> 
                                                                                                </td>
                                                                                                <td style="border: transparent;">
                                                                                                    <button type="button" id="red" class="btn btn-danger color green"><i class="fa fa-close"></i></button>
                                                                                                </td>
                                                                                            </tr>-->

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
                                            <select class="form-control select2" name="apcpType" id="apcpType" style="width: 100%;">
                                                <option value="1">Crop Production</option>
                                                <option value="2">Livelihood Program</option>
                                            </select>
                                        </div>
                                    </div>
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

                                    <div class="col-md-4">
                                        <div class="form-group">
                                            <label>Other Reason</label>
                                            <input type="text" id="otherReason" name="otherReason" class="form-control">
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
                                            <input type="number" step=".01" class="form-control" name="landArea" id="Area" required>
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
                                                <input id="loanAmount" type='number' name="loanAmount" class="form-control numberOnly" required>
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
                                                    <th>Action</th>
                                                    <th>Full Name</th> 
                                                    <th>Membership Date</th> 
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%for(ARB arb : arbo.getArbList()){%>
                                                <tr>
                                                    <td><input type="checkbox" name="arbID" value="<%out.print(arb.getArbID());%>"></td>
                                                    <td><%out.print(arb.getFLName());%></td>
                                                    <td><%out.print(f.format(arb.getMemberSince()));%></td>
                                                </tr>
                                                <%}%>
                                            </tbody>
                                            <tfoot>
                                                <tr>
                                                    <th>Action</th>
                                                    <th>Full Name</th> 
                                                    <th>Membership Date</th> 
                                                </tr>
                                            </tfoot>
                                        </table>
                                        <div class="form-group pull-right">
                                            <input type="file" name="arbListExcel" id="exampleInputFile">
                                            <p class="help-block">Upload Recipient List</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="box-header with-border">
                                    <h5 class="box-title"><strong>Supporting Documents</strong></h5>
                                </div>
                                <div class="row">
                                    <button id="btnAddConduitDocu" class="btn btn-primary center-block">Add Document</button>    
                                </div>

                                <br>
                                <div class="row">
                                    <div class="col-xs-2"></div>
                                    <div class="col-xs-8">
                                        <table id="example1" class="table table-bordered table-striped" >
                                            <tbody>
                                                <%for(APCPDocument d : refDocuments){%>
                                                <%if(d.getIsRequired() == 1 && d.getDocumentType() == 2){ // REQUIRED and APCP%>
                                                <tr>
                                            <input type="hidden" name="documentID" value="<%out.print(d.getDocument());%>">
                                            <td style="border: transparent;"><h5> &#9632; &nbsp; <%out.print(d.getDocumentDesc());%>:</h5></td>
                                            <td style="border: transparent;">
                                                <div class="input-group date">
                                                    <div class="input-group-addon">
                                                        <i class="fa fa-calendar"></i>
                                                    </div>
                                                    <input type="date" name="dateSubmitted" class="form-control pull-right" id="datepicker">
                                                </div> 
                                            </td>
                                            </tr>
                                            <%}%>
                                            <%}%>

                                            <!--                                                <tr>
                                                                                                <td  style="border: transparent;"><h5> &#9632; &nbsp; Management Team Document:</h5></td>
                                                                                                <td  style="border: transparent;">
                                                                                                    <div class="input-group date">
                                                                                                        <div class="input-group-addon">
                                                                                                            <i class="fa fa-calendar"></i>
                                                                                                        </div>
                                                                                                        <input type="date" class="form-control pull-right" id="datepicker">
                                                                                                    </div> 
                                                                                                </td>
                                                                                                <td style="border: transparent;">
                                                                                                    <button type="button" id="red" class="btn btn-danger color green"><i class="fa fa-close"></i></button>
                                                                                                </td>
                                                                                            </tr>-->

                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="col-xs-2"></div>
                                </div>

                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="form-group">
                                            <label for="">Remarks</label>
                                            <textarea name="remarks" cols="30" rows="10" class="form-control"></textarea>
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


        <script>
            
            $(function () {
                $('#validator').change(function () {
                    $('.color').hide();
                    $('#' + $(this).val()).show();
                });
            });
            $(function () {
                //Initialize Select2 Elements
                $('.select2').select2()
                
                //Datemask dd/mm/yyyy
                $('#datemask').inputmask('dd/mm/yyyy', {'placeholder': 'dd/mm/yyyy'})
                //Datemask2 mm/dd/yyyy
                $('#datemask2').inputmask('mm/dd/yyyy', {'placeholder': 'mm/dd/yyyy'})
                //Money Euro
                $('[data-mask]').inputmask()
                
                //Date range picker
                $('#reservation').daterangepicker()
                //Date range picker with time picker
                $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'MM/DD/YYYY h:mm A'})
                //Date range as a button
                $('#daterange-btn').daterangepicker(
                        {
                            ranges: {
                                'Today': [moment(), moment()],
                                'Yesterday': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                                'Last 7 Days': [moment().subtract(6, 'days'), moment()],
                                'Last 30 Days': [moment().subtract(29, 'days'), moment()],
                                'This Month': [moment().startOf('month'), moment().endOf('month')],
                                'Last Month': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                            },
                            startDate: moment().subtract(29, 'days'),
                            endDate: moment()
                        },
                        function (start, end) {
                            $('#daterange-btn span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'))
                        }
                )
                
                //Date picker
                $('#datepicker').datepicker({
                    autoclose: true
                })
                
                //iCheck for checkbox and radio inputs
                $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
                    checkboxClass: 'icheckbox_minimal-blue',
                    radioClass: 'iradio_minimal-blue'
                })
                //Red color scheme for iCheck
                $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
                    checkboxClass: 'icheckbox_minimal-red',
                    radioClass: 'iradio_minimal-red'
                })
                //Flat red color scheme for iCheck
                $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
                    checkboxClass: 'icheckbox_flat-green',
                    radioClass: 'iradio_flat-green'
                })
                
                //Colorpicker
                $('.my-colorpicker1').colorpicker()
                //color picker with addon
                $('.my-colorpicker2').colorpicker()
                
                //Timepicker
                $('.timepicker').timepicker({
                    showInputs: false
                })
            })
        </script>
        <%@include file="jspf/footer.jspf"%>
    </body>


</html>
