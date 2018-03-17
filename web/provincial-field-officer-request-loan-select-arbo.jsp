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
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="field-officer-arbo-list.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
                    </ol>

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
                                                <th>No. of <br> Members</th>
                                                <th>Total Approved <br> Amount</th>
                                                <th>Cumulative <br> Releases</th>
                                                <th><%out.print(year);%> Release</th>
                                                <th>Date Released</th>
                                                <th>O/S Balance</th>
                                                <th>Past Due <br> Amount</th>
                                                <th>Reason for Past Due</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%
                                                for (ARBO arbo : qualifiedARBOs) {
                                                    if (!capdevDAO.checkPastDueAccount(arbo.getArboID())) {
                                            %>
                                            <tr>
                                                <td><a href="SelectARBORequest?id=<%out.print(arbo.getArboID());%>"> <%=arbo.getArboName()%></a></td>
                                                <td><%=arbDAO.getAllARBsARBO(arbo.getArboID()).size()%></td>
                                                <td><%-- total requested amount --%> </td>
                                                <td><%-- cumulative amount --%> </td>
                                                <td><%-- current year release --%> </td>
                                                <td><%-- date released --%> </td>
                                                <td><%-- O/S Balance --%> </td>
                                                <td><%-- Past Due Amount --%> </td>
                                                <td><%-- Reason for Past Due --%> </td>

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
                                                <th>Leader</th>
                                                <th>No. of Members</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%for (ARBO arbo : arboListProvince) {%>
                                            <%if (arbo.getAPCPQualified() == 0) {%>
                                            <tr>
                                                <td><a href="SelectARBORequest?id=<%out.print(arbo.getArboID());%>"> <%out.print(arbo.getArboName());%></a></td>
                                                <td>Internet Explorer 4.0</td>
                                                <td>Win 95+</td>
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
            <footer class="main-footer">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 2.4.0
                </div>
                <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
                reserved.
            </footer>
        </div>
        <%@include file="jspf/footer.jspf" %>
    </body>
</html>
