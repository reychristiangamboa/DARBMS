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
                                <form role='form' method="post" action="ImportAPCPARBO">
                                    <div class='box-body'>

                                        <div class='box-body'>
                                            <div class="row">
                                                <div class="col-xs-3">
                                                    <div class="form-group">
                                                        <label for="fileExcel">Import Qualified ARBO</label>
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
                                <form role="form" method="post" action="AddAPCPARBO">
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
                                            <form id="modalForm">
                                             
                                                <tr>
                                                    <td><a href="central-view-arbo-profile.jsp">Lanz ARBO</a></td>
                                                    <td>Internet Explorer 4.0</td>
                                                    <td>Win 95+</td>
                                                    
                                                </tr>
                                         
                                            </form>
                                            </tbody>

                                        </table>
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
