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
                        <div class="col-lg-3 col-xs-6" >
                            <!-- small box -->
                            <a href="#" name="btn1">
                                <div class="small-box bg-yellow">
                                    <div class="inner">
                                        <h3><%=pendingPlans.size()%></h3>

                                        <h4>Pending </h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-spinner"></i>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <a href="#" name="btn2">
                                <div class="small-box bg-aqua">
                                    <div class="inner">
                                        <h3><%=approvedPlans.size()%></h3>

                                        <h4>Approved </h4>

                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-thumbs-o-up"></i>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <a href="#" name="btn3">
                                <div class="small-box bg-red">
                                    <div class="inner">
                                        <h3><%=disapprovedPlans.size()%></h3>

                                        <h4>Disapproved </h4>

                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-ban"></i>
                                    </div>  
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <div class="col-lg-3 col-xs-6">
                            <!-- small box -->
                            <a href="#" name="btn4">
                                <div class="small-box bg-green">
                                    <div class="inner">
                                        <h3><%=implementedPlans.size()%></h3>

                                        <h4>Implemented </h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa  fa-check-square-o"></i>
                                    </div>  
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <!-- ./col -->
                    </div>


                    <div class="row" id="1" style="display:none;">
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
                                    <table id="apcp1" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                                <th>Type</th>
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
                                                <th>Type</th>
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

                    <div class="row" id="2" style="display:none;">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Approved CAPDEV Proposals</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="apcp2" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                                <th>Type</th>
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
                                                <th>Type</th>
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

                    <div class="row" id="3" style="display:none;">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Disapproved CAPDEV Proposals</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="apcp3" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                                <th>Type</th>
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
                                                <th>Type</th>
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

                    <div class="row" id="4" style="display:none;">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Implemented CAPDEV Proposals</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="capdev3" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                                <th>Type</th>
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
                                                <th>Type</th>
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
        <script>
            $(document).ready(function () {
            $("a[name='btn1']").click(function () {
            $("div[id='1']").toggle();
            if ($("div[id='2']").css('display') != 'none') {
            $("div[id='2']").toggle();
            }
            if ($("div[id='3']").css('display') != 'none') {
            $("div[id='3']").toggle();
            }
            if ($("div[id='4']").css('display') != 'none') {
            $("div[id='4']").toggle();
            }
            if ($("div[id='5']").css('display') != 'none') {
            $("div[id='5']").toggle();
            }
            if ($("div[id='6']").css('display') != 'none') {
            $("div[id='6']").toggle();
            }
            });
            $("a[name='btn2']").click(function () {
            $("div[id='2']").toggle();
            if ($("div[id='1']").css('display') != 'none') {
            $("div[id='1']").toggle();
            }
            if ($("div[id='3']").css('display') != 'none') {
            $("div[id='3']").toggle();
            }
            if ($("div[id='4']").css('display') != 'none') {
            $("div[id='4']").toggle();
            }
            if ($("div[id='5']").css('display') != 'none') {
            $("div[id='5']").toggle();
            }
            if ($("div[id='6']").css('display') != 'none') {
            $("div[id='6']").toggle();
            }
            });
            $("a[name='btn3']").click(function () {
            $("div[id='3']").toggle();
            if ($("div[id='1']").css('display') != 'none') {
            $("div[id='1']").toggle();
            }
            if ($("div[id='2']").css('display') != 'none') {
            $("div[id='2']").toggle();
            }
            if ($("div[id='4']").css('display') != 'none') {
            $("div[id='4']").toggle();
            }
            if ($("div[id='5']").css('display') != 'none') {
            $("div[id='5']").toggle();
            }
            if ($("div[id='6']").css('display') != 'none') {
            $("div[id='6']").toggle();
            }
            });
            $("a[name='btn4']").click(function () {
            $("div[id='4']").toggle();
            if ($("div[id='1']").css('display') != 'none') {
            $("div[id='1']").toggle();
            }
            if ($("div[id='2']").css('display') != 'none') {
            $("div[id='2']").toggle();
            }
            if ($("div[id='3']").css('display') != 'none') {
            $("div[id='3']").toggle();
            }
            if ($("div[id='5']").css('display') != 'none') {
            $("div[id='5']").toggle();
            }
            if ($("div[id='6']").css('display') != 'none') {
            $("div[id='6']").toggle();
            }
            });
            $("a[name='btn5']").click(function () {
            $("div[id='5']").toggle();
            if ($("div[id='1']").css('display') != 'none') {
            $("div[id='1']").toggle();
            }
            if ($("div[id='2']").css('display') != 'none') {
            $("div[id='2']").toggle();
            }
            if ($("div[id='3']").css('display') != 'none') {
            $("div[id='3']").toggle();
            }
            if ($("div[id='4']").css('display') != 'none') {
            $("div[id='4']").toggle();
            }
            if ($("div[id='6']").css('display') != 'none') {
            $("div[id='6']").toggle();
            }
            });
            $("a[name='btn6']").click(function () {
            $("div[id='6']").toggle();
            if ($("div[id='1']").css('display') != 'none') {
            $("div[id='1']").toggle();
            }
            if ($("div[id='2']").css('display') != 'none') {
            $("div[id='2']").toggle();
            }
            if ($("div[id='3']").css('display') != 'none') {
            $("div[id='3']").toggle();
            }
            if ($("div[id='5']").css('display') != 'none') {
            $("div[id='5']").toggle();
            }
            if ($("div[id='4']").css('display') != 'none') {
            $("div[id='4']").toggle();
            }
            });
            });
        </script>
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
                    if (filterDrop.value === 1){
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
