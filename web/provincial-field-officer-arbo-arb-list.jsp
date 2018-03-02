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

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>

            <%
                
            ARBO arbo = (ARBO) request.getAttribute("arbo");
            ArrayList<ARB> arbList = arbDAO.getAllARBsARBO((Integer)arbo.getArboID());
            
            %>

            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><%out.print(arbo.getArboName());%></strong> 
                        <small><%out.print(arbo.getArboProvinceDesc());%>, <%out.print(arbo.getArboRegionDesc());%></small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="FO-Homepage.html"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li class="active"><a href="#">(ARBO Name) Beneficiary List</a></li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header">
                                    <h3 class="box-title">Agrarian Reform Beneficiaries (ARB)</h3>
                                    <div class="btn-group pull-right">
                                        <a href="ProceedAddARB?id=<%out.print(arbo.getArboID());%>" class="btn btn-primary" ><i class="fa fa-user-plus"> </i> Add ARB </a>
                                    </div>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <table id="example1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARB Name</th>
                                                <th>Address</th>
                                                <th>Crops</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%for(ARB arb : arbList){%>
                                            <tr>
                                                <td><a href="#"><%out.print(arb.getFullName());%></a></td>
                                                <td><%out.print(arb.getFullAddress());%></td>
                                                <td><%out.print(arb.printAllCrops());%></td>
                                            </tr>
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
