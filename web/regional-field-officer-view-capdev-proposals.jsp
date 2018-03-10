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
                    <%if(request.getAttribute("success") != null){%>
                    <div class="alert alert-success alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-check"></i> <%out.print((String)request.getAttribute("success"));%></h4>
                    </div>
                    <%}else if(request.getAttribute("errMessage") != null){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String)request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Filter</h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>  
                                </div>
                                <!-- /.box-header -->

                                <form method="post">
                                    <div class="box-body"> 
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <div class="col-xs-6">
                                                    <div class="form-group">
                                                        <label>View:</label>
                                                        <select class="form-control" id="filterDrop" onchange="chg()">
                                                            <option value="1">All</option>
                                                            <option value="2">Per Province</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-6">
                                                    <div class="form-group">
                                                        <label for='provinceDrop'>Province</label>
                                                        <select  id="provinceDrop" multiple class='select2 form-control' onchange="chg2()" name='province' style='width: 100%' disabled>
                                                        </select>
                                                    </div>
                                                    <!-- /.input group -->
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /.box-body -->
                                    </div>

                                    <div class="box-footer">
                                        <div class= "pull-right">
                                            <button type="submit" class="btn btn-primary" onclick="form.action = 'FilterCAPDEVProposals'">Filter</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <!-- /.col -->
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Pending CAPDEV Proposals</strong></h3>
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
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody id="proposals">

                                            <%
                                                for(CAPDEVPlan p : pendingPlans){
                                                    APCPRequest r = apcpRequestDAO.getRequestByID(p.getRequestID());
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <!--WITH CAPDEV-->
                                                <td><a href="ViewCAPDEVProposal?planID=<%out.print(p.getPlanID());%>&requestID=<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(p.getPlanDTN());%></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>

                                            </tr>
                                            <%
                                               }
                                            %>

                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                            </tr>

                                        </tfoot>
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
                                    <h3 class="box-title"><strong>With Past Due CAPDEV Proposals</strong></h3>
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


                    <!-- /.row -->
                </section>
                <!-- /.content -->

            </div>
            <!-- /.content -->
            <footer class="main-footer">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 2.4.0
                </div>
                <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
                reserved.
            </footer>
        </div>
        <!-- /.row -->




        <!-- /.content-wrapper -->

        <%@include file="jspf/footer.jspf" %>

        <script type="text/javascript">

            function chg() {

                var filterDrop = document.getElementById('filterDrop');
                var provinceDrop = document.getElementById('provinceDrop');

                if (provinceDrop.disabled === true) {
                    var filterVal = document.getElementById('filterDrop').value;

                    provinceDrop.disabled = false;

                    var xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function () {
                        if (xhttp.readyState === 4 && xhttp.status === 200) {
                            document.getElementById('provinceDrop').innerHTML = xhttp.responseText;
                        }
                    };
                    xhttp.open("GET", "FilterProvRefresh?valajax=" + filterVal, true);
                    xhttp.send();

                } else {
                    provinceDrop.innerHTML = "";
                    provinceDrop.disabled = true;
                }


            }
            
            $(document).ready(function()){
                var filterDrop = document.getElementById('filterDrop');
                var 
                if(filterDrop.value === 1){
                    var xhttp = new XMLHttpRequest();
                    xhttp.onreadystatechange = function () {
                        if (xhttp.readyState === 4 && xhttp.status === 200) {
                            document.getElementById('provinceDrop').innerHTML = xhttp.responseText;
                        }
                    };
                    xhttp.open("GET", "ShowAllCAPDEVProposals?valajax=" + filterDrop.value, true);
                    xhttp.send();
                }
                
            }
            
            function chg2() {

                var values = $('#provinceDrop').val();
//
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('proposals').innerHTML = xhttp.responseText;
                    }
                };

                var url = "FilterCAPDEVProposals?";

                for (i = 0; i < values.length; i++) {
                    if (i === 0) {
                        url += "valajax=" + values[i];
                    } else {
                        url += "&valajax=" + values[i];
                    }
                }

                xhttp.open("GET", url, true);
                xhttp.send();


            }
            
            
        </script>
    </body>
</html>
