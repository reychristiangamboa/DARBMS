<%-- 
    Document   : field-officer-arbo-list
    Created on : Jan 29, 2018, 4:08:13 PM
    Author     : Rey Christian
--%>

<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>

    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-group"></i> Agrarian Reform Beneficiary Organizations</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>

                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Qualified Agrarian Reform Beneficiary Organizations (ARBO)</h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example3" class="table table-bordered table-striped">
                                        <thead>

                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Address</th>
                                                <th>No. of Members</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%for (ARBO arbo : arboListProvince) {%>
                                            <%if (arbo.getAPCPQualified() == 1) {%>
                                            <tr>
                                                <td><a href="SelectARBORequest?id=<%out.print(arbo.getArboID());%>"> <%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(arbo.getFullAddress());%></td>
                                                <td><%out.print(arboDAO.getARBCount(arbo.getArboID()));%></td>
                                            </tr>

                                            <%}%>
                                            <%}%>
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
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Agrarian Reform Beneficiary Organizations (ARBO)</h3>                       
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Address</th>
                                                <th>No. of Members</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%for (ARBO arbo : arboListProvince) {%>
                                            <%if (arbo.getAPCPQualified() == 0) {%>
                                            <tr>
                                                <td><a href="SelectARBORequest?id=<%out.print(arbo.getArboID());%>"> <%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(arbo.getFullAddress());%></td>
                                                <td><%out.print(arboDAO.getARBCount(arbo.getArboID()));%></td>
                                            </tr>

                                            <%}%>
                                            <%}%>
                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>
                </section>
                <!-- /.content -->

            </div>
            <!-- /.content-wrapper -->

        </div>
        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
