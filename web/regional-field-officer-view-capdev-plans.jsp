<%-- 
    Document   : provincial-field-officer-add-arbo-qualified
    Created on : Feb 20, 2018, 12:44:25 PM
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
            <%@include file="jspf/regional-field-officer-sidebar.jspf" %>

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

                <!-- Main content -->
                <section class="content">
                    <%if (request.getAttribute("errMessage") != null) {%>
                    <p class="text text-center text-danger"><%=request.getAttribute("errMessage")%></p>
                    <%}%>
                    <%if (request.getAttribute("success") != null) {%>
                    <p class="text text-center text-success"><%=request.getAttribute("success")%></p>
                    <%}%>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Requested</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Requested</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(APCPRequest r : requestedRequests){
                                                    ArrayList<CAPDEVPlan> planList = capdevDAO.getPendingRequestCAPDEVPlans(r.getRequestID());
                                                    for(CAPDEVPlan plan : planList){
                                                        ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <!--WITH CAPDEV-->
                                                <td><a href="ViewCAPDEVPlan?planID=<%out.print(plan.getPlanID());%>&requestID=<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>

                                                <td><%out.print(r.getLoanReason());%></td>
                                                <td><%out.print(r.getLoanAmount());%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(f.format(r.getDateRequested()));%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><%out.print(r.getRequestStatusDesc());%></td>

                                            </tr>
                                            <%
                                                    }
                                                }                                            
                                            %>

                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Cleared</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example5" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Cleared</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <tr>
                                                <td><a href="modal-default" class="btn"data-toggle="modal" data-target="#modal-default">ARB</a></a></td>
                                                <td>Internet
                                                    Explorer 4.0
                                                </td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                            </tr>

                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Endorsed</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example3" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Endorsed</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <tr>
                                                <td><a>ARB</a></td>
                                                <td>Internet
                                                    Explorer 4.0
                                                </td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                            </tr>

                                        </tbody>

                                    </table>

                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Approved</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example5" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Approved</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <tr>
                                                <td><a href="">ARB</a></td>
                                                <td>Internet
                                                    Explorer 4.0
                                                </td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                            </tr>

                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Released</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example6" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Requested</th>
                                                <th>Date Cleared</th>
                                                <th>Date Endorsed</th>
                                                <th>Date Approved</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <tr>
                                                <td><a href="">ARB</a></td>
                                                <td>Internet
                                                    Explorer 4.0
                                                </td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                                <td>Win 95+</td>
                                            </tr>

                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>

                    <!-- /.row -->
                </section>
                <!-- /.content -->

            </div>
        </div>
        <!-- /.row -->

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
