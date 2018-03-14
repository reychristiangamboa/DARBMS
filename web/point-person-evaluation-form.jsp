
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
                    <%if (request.getAttribute("errMessage") != null) {%>
                    <p class="text text-center text-danger"><%=request.getAttribute("errMessage")%></p>
                    <%}%>
                    <%if (request.getAttribute("success") != null) {%>
                    <p class="text text-center text-success"><%=request.getAttribute("success")%></p>
                    <%}%>
                    <div class='row'>
                        <div class='col-xs-6'>
                            <div class='box'>
                                <div class='box-header with-border'>
                                    <h3 class="box-title">Upload Evaluation</h3>
                                </div>
                                <form role='form' method="post" action="ImportARBO">
                                    <div class='box-body'>

                                        <div class='box-body'>
                                            <div class="row">
                                                <div class="col-xs-3">
                                                    <div class="form-group">
                                                        <label for="fileExcel">Import Evaluation</label>
                                                        <input type='file' id='fileExcel' name="file">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="box-footer">
                                            <button type="submit" name="import" class="btn btn-primary pull-right">Submit</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">

                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Create ARBO</strong></h3>

                                </div>
                                <!--                                 /.box-header -->
                                <form role="form" method="post" action="AddARBO">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <table class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>Question</th>
                                                            <th>1</th>
                                                            <th>2</th>
                                                            <th>3</th>
                                                            <th>4</th>
                                                            <th>5</th>
                                                        </tr>
                                                    </thead>

                                                    <tbody>
                                                        <%
                                                            for(APCPRequest r : requestedRequests){
                                                                ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                                        %>
                                                        <tr>
                                                            <td>Okay na ba?</td>
                                                            <td>   
                                                                <input type="radio" name="r3" class="iradio_flat-green" value="1">
                                                            </td>
                                                            <td>   
                                                                <input type="radio" name="r3" class="flat-red"  value="2">
                                                            </td>
                                                            <td>   
                                                                <input type="radio" name="r3" class="flat-red"  value="3">
                                                            </td>
                                                            <td>   
                                                                <input type="radio" name="r3" class="flat-red"  value="4">
                                                            </td>
                                                            <td>   
                                                                <input type="radio" name="r3" class="flat-red"  value="5">
                                                            </td>

                                                        </tr>
                                                        <%}%>
                                                    </tbody>

                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="box-footer">
                                        <button type="submit" name="manual" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>
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
                var regionVal = document.getElementById('regionDrop').value;

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('provinceDiv').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "RegionRefresh?valajax=" + regionVal, true);
                xhttp.send();
            }

            function chg2() {
                var provinceVal = document.getElementById('provinceDrop').value;

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('cityDiv').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "ProvinceRefresh?valajax=" + provinceVal, true);
                xhttp.send();
            }

            $(document).ready(function () {
                $('#regionDrop').on('change', function (e) {
                    $('#provinceDrop').removeAttr('disabled');
                });
                $('#provinceDrop').on('change', function (e) {
                    $('#cityDrop').removeAttr('disabled');
                });
            });
        </script>
    </body>
</html>
