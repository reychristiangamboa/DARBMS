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
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>
            <%
                int arboID = (Integer)request.getAttribute("arboID");
                ARBO arbo = arboDAO.getARBOByID(arboID);
                
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
                        <li><a href="provincial-field-officer-arbo-list.jsp"><i class="fa fa-dashboard"></i> ARBO List</a></li>
                        <li class="active"><a href="provincial-field-officer-request-loan.jsp">Request Loan (Qualified)</a></li>
                    </ol>

                </section>

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
                                    <h3 class="box-title"><strong>Request for Loan</strong></h3>

                                </div>
                                <!-- /.box-header -->
                                <form role="form" method="post" action="RequestLoanAccess">
                                    <div class="box-body">             

                                        <div class="box-body">

                                            <div class="row">
                                                <div class="col-xs-6">
                                                    <div class="form-group">
                                                        <label for="">Name of ARBO</label>
                                                        <input type="text" class="form-control" value="<%out.print(arbo.getArboName());%>"disabled >
                                                    </div>
                                                </div>
                                                <div class="col-xs-3">
                                                    <div class="form-group">
                                                        <label for="">No. of ARBs</label>
                                                        <input type="text" class="form-control" id="" placeholder="" value="<%out.print(arboDAO.getARBCount(arboID));%>" disabled>
                                                    </div>
                                                </div>
                                                <div class="col-xs-3">
                                                    <div class="form-group">
                                                        <label for="">Land Area (Hectares)</label>
                                                        <input type="text" class="form-control" name="land" placeholder="" >
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-4">
                                                    <label for=''>Loan Amount</label>
                                                    <input type='text' class="form-control" name="loan" placeholder="" >
                                                </div>

                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label for="">Reason for Loan</label>
                                                        <input type="text" class="form-control" name="reason" />
                                                    </div>
                                                </div>       
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <label for=''>Remarks</label>
                                                    <textarea class="form-control" name="remarks" rows="2" placeholder="Remarks"></textarea>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="box-footer">
                                            <input type="hidden" name="arboID" value="<%out.print(arboID);%>">
                                            <button type="submit" class="btn btn-primary pull-right">Submit</button>
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

        <script>
            $('#datepicker').datepicker({
                autoclose: true
            });
        </script>
        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
