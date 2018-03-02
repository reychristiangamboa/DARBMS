
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
                                    <h3 class="box-title">Upload ARBO List</h3>
                                </div>
                                <form role='form' method="post" action="ImportARBO">
                                    <div class='box-body'>

                                        <div class='box-body'>
                                            <div class="row">
                                                <div class="col-xs-3">
                                                    <div class="form-group">
                                                        <label for="fileExcel">Import ARBO</label>
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
                                            <div class="col-xs-6">
                                                <div class="form-group">
                                                    <label for="">Name of ARBO</label>
                                                    <input type="text" name="arboName" class="form-control" id='' placeholder="">
                                                    <input type="hidden" name="provOfficeCode" value="<%out.print((Integer)session.getAttribute("provOfficeCode"));%>">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group" id="cityDiv">
                                                    <label for="">City</label>
                                                    <select class="form-control" id="cityDrop" name="arboCityMun" style="width: 100%;" disabled required>

                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group" id="provinceDiv">
                                                    <label for="">Province</label>
                                                    <select class="form-control" id="provinceDrop" name="arboProvince" onchange="chg2()" style="width: 100%;" disabled required>

                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">Region</label>
                                                    <select class="form-control" id="regionDrop" name="arboRegion" onchange="chg()" style="width: 100%;" required>
                                                             <%for(Region r: regionList){%>
                                                                <option value="<%out.print(r.getRegCode());%>"> <%out.print(r.getRegDesc());%> </option>
                                                             <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                    </div>
                                    <!--                                     /.box-body -->

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