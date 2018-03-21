
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
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>

            <%
            ARBO arbo = (ARBO) request.getAttribute("arbo");
            %>

            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Invoice
                        <small>#007612</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li><a href="#">Examples</a></li>
                        <li class="active">Invoice</li>
                    </ol>
                </section>

                <div class="pad margin no-print">
                    <div class="callout callout-info" style="margin-bottom: 0!important;">
                        <h4><i class="fa fa-info"></i> Note:</h4>
                        This page has been enhanced for printing. Click the print button at the bottom of the invoice to test.
                    </div>
                </div>

                <!-- Main content -->
                <section class="invoice">
                    <!-- title row -->
                    <div class="row">
                        <div class="col-xs-12">
                            <h2 class="page-header">
                                <i class="fa fa-globe"></i> AdminLTE, Inc.
                                <small class="pull-right">Date: 2/10/2014</small>
                            </h2>
                        </div>
                        <!-- /.col -->
                    </div>
                    <!-- info row -->
                    
                    <!-- /.row -->

                    <!-- this row will not appear when printing -->
                    <div class="row no-print">
                        <div class="col-xs-12">
                            <a href="invoice-print.html" target="_blank" class="btn btn-default pull-right"><i class="fa fa-print"></i> Print</a>
                        </div>
                    </div>
                </section>
                <!-- /.content -->
                <div class="clearfix"></div>
            </div>
            <!-- /.content-wrapper -->
        </div>
        <%@include file="jspf/footer.jspf" %>

    </body>
</html>