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

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>
                    <ol class="breadcrumb">

                        <li><a href="provincial-field-officer-arbo-list.jsp"><i class="fa fa-dashboard"></i> ARBO List</a></li>
                        <li><a href="provincial-field-officer-request-loan.jsp">Request Loan (Qualified)</a></li>
                        <li class="active"><a href="provincial-field-officer-request-loan-new.jsp">Request Loan (Not Qualified)</a></li>

                    </ol>

                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Request for Loan (Not Qualified ARBO)</strong></h3>

                                </div>
                                <!-- /.box-header -->
                                <form role="form">
                                    <div class="box-body">             

                                        <div class="box-body">

                                            <div class="row">
                                                <div class="col-xs-6">
                                                    <div class="form-group" id="arboName">
                                                        <label for="">Name of ARBO</label>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control" disabled>
                                                            <span class="input-group-btn">
                                                                <button type="button" class="btn btn-success" data-toggle="modal" data-target="#modal-default">Select ARBO</button>
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>


                                                <div class="col-xs-3">
                                                    <div class="form-group">
                                                        <label for="">Land Area (Hectares)</label>
                                                        <input type="text" class="form-control" id="" placeholder="" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-xs-3">
                                                    <div class="form-group">
                                                        <label for="">No. of ARBs</label>
                                                        <input type="text" class="form-control" id="" placeholder="" disabled>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-4">
                                                    <label for=''>Loan Amount</label>
                                                    <input type='text' class="form-control" id='' placeholder="" >
                                                </div>
                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label for="">Reason for Loan</label>
                                                        <input type="text" class="form-control" />
                                                    </div>
                                                </div>
                                                <div class="col-xs-4">

                                                    <label for=''>Date</label>
                                                    <input type="date" class="form-control" data-inputmask="'alias': 'dd/mm/yyyy'" data-mask>
                                                    <input type="hidden" name="country" value="">
                                                </div>         
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <label for=''>Remarks</label>
                                                    <textarea class="form-control" rows="3" placeholder="Enter ..."></textarea>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="box-footer">
                                            <button type="submit" class="btn btn-primary pull-right">Submit</button>
                                        </div>
                                        <div class="modal fade" id="modal-default">
                                            <div class="modal-dialog modal-lg">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span></button>
                                                        <h4 class="modal-title">Default Modal</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <div class="row">
                                                            <div class="col-xs-12">
                                                                <div class="box">
                                                                    <div class="box-header with-border">
                                                                        <h3 class="box-title">Agrarian Reform Beneficiary Organizations (ARBO)</h3>
                                                                        <div class="btn-group pull-right">
                                                                            <a href="provincial-field-officer-add-arbo.jsp" class="btn btn-primary" ><i class="fa fa-user-plus "> </i> Add ARBO </a>                                                                                   
                                                                        </div>                         
                                                                    </div>
                                                                    <!-- /.box-header -->
                                                                    <div class="box-body">             
                                                                        <table id="example1" class="table table-bordered table-striped">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th></th>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>Leader</th>
                                                                                    <th>No. of Members</th>
                                                                                </tr>
                                                                            </thead>

                                                                            <tbody>
                                                                            <form id="modalForm">
                                                                                <%for(ARBO arbo : nonQualifiedARBOs){%>
                                                                                <tr>
                                                                                    <td><input name="arboID" value="<%out.print(arbo.getArboID());%>" type="radio"/></td>
                                                                                    <td><a href="ViewARBO?id=<%out.print(arbo.getArboID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                                                    <td>Internet
                                                                                        Explorer 4.0
                                                                                    </td>
                                                                                    <td>Win 95+</td>
                                                                                </tr>
                                                                                <%}%>
                                                                            </form>
                                                                            </tbody>

                                                                        </table>
                                                                    </div>
                                                                    <!-- /.box-body -->
                                                                </div>
                                                                <!-- /.box -->
                                                            </div>
                                                            <!-- /.col -->
                                                        </div>

                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                        <button type="button" onclick="chg()" class="btn btn-primary" data-dismiss="modal">Select</button>
                                                    </div>
                                                </div>
                                                <!-- /.modal-content -->
                                            </div>
                                            <!-- /.modal-dialog -->
                                        </div>
                                    </div>
                                </form>

                                <!-- /.box-body -->
                            </div>

                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->
                    </div>
                    <!-- /.col -->


                </section>
            </div>
            <!-- /.content -->
            <footer class="main-footer">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 2.4.0
                </div>
                <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
                reserved.
            </footer>
        </div>
        <!-- /.content-wrapper -->

        <script>
            $('#datepicker').datepicker({
                autoclose: true
            });
        </script>
        <%@include file="jspf/footer.jspf" %>
        
        <script type="text/javascript">
            function chg() {
                var arboID = $('input[name=arboID]:checked').val();
                var xhttp = new XMLHttpRequest();

                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('arboName').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "ARBONameRefresh?valajax=" + arboID, true);
                xhttp.send();
            }
        </script>
    </body>
</html>
