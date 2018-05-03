<%-- 
    Document   : provincial-field-officer-imported-arbo-list
    Created on : Apr 13, 2018, 2:50:07 PM
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

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%ArrayList<ARBO> arboList = (ArrayList<ARBO>) session.getAttribute("arboList");%>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-group"></i> Imported Agrarian Reform Beneficiary Organizations</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>


                </section>

                <!-- Main content -->
                <section class="content">

                    <div class="row">
                        <div class="col-xs-12">

                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Agrarian Reform Beneficiaries Organizations</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table class="table table-bordered table-striped modTable">
                                        <thead>
                                            <tr>
                                                <th>Name</th>
                                                <th>Type</th>
                                                <th>Address</th>
                                                <th>APCP Qualified</th>
                                                <th>Action</th>

                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for (ARBO arbo : arboList) {
                                            %>
                                        <form method="post">
                                            <tr>
                                                <td><%out.print(arbo.getArboName());%></td>
                                                <td><%out.print(arbo.getArboTypeDesc());%></td>
                                                <td><%out.print(arbo.getFullAddress());%></td>
                                                <%if (arbo.getAPCPQualified() == 1) {%>
                                                <td>Yes</td>
                                                <%} else {%>
                                                <td>No</td>
                                                <%}%>
                                                <td><a class="btn btn-primary" href='ProceedAddARB?id=<%out.print(arbo.getArboID());%>&source=import'>Add ARB</a></td>
                                            </tr>
                                        </form>
                                        <%}%>

                                        </tbody>

                                        <tfoot>
                                            <tr>
                                                <th>Name</th>
                                                <th>Type</th>
                                                <th>Address</th>
                                                <th>APCP Qualified</th>
                                                <th>Action</th>
                                            </tr>
                                        </tfoot>

                                    </table>
                                </div>
                                <!-- /.box-body -->

                                <div class="box-footer">
                                    <div class="pull-right">
                                        <form method="post">
                                            <button type="submit" class="btn btn-success" onclick="form.action='ConfirmImportARBO'">Confirm</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                            <!--                             /.box-body -->
                        </div>
                        <!--                         /.box -->
                    </div>
                    <!--                     /.col -->
                </section>
            </div>
            <!-- /.row -->

            <!-- /.content -->
        </div>

        <!-- /.content-wrapper -->

        <%@include file="jspf/footer.jspf" %>

    </body>
</html>
