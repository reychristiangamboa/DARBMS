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
        </style>

    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">
            <%int userType = (Integer) session.getAttribute("userType");%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%if (userType == 3) {  %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%} else if (userType == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <%} else if (userType == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>

            <%
                CropDAO cropDAO = new CropDAO();
                AddressDAO addressDAO = new AddressDAO();
                ARBODAO arboDAO = new ARBODAO();
                ARBDAO arbDAO = new ARBDAO();
                APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
                CAPDEVDAO capdevDAO = new CAPDEVDAO();
                UserDAO uDAO = new UserDAO();
                LINKSFARMDAO lfDAO = new LINKSFARMDAO();
    
                ArrayList<Region> regionList = addressDAO.getAllRegions();
                ArrayList<Province> perProvinceList = addressDAO.getAllProvinces((Integer) session.getAttribute("regOfficeCode"));
                ArrayList<CityMun> cityMunList = new ArrayList();

                
                ArrayList<CAPDEVPlan> allPlans = new ArrayList();
                ArrayList<CAPDEVPlan> pendingPlans = new ArrayList();
                ArrayList<CAPDEVPlan> approvedPlans = new ArrayList();
                ArrayList<CAPDEVPlan> disapprovedPlans = new ArrayList();
                ArrayList<CAPDEVPlan> implementedPlans = new ArrayList();
                ArrayList<APCPRequest> requestedRequests = new ArrayList();
    
                
                pendingPlans = lfDAO.getAllLINKSFARMCAPDEVPlanByStatus(1);
                approvedPlans = lfDAO.getAllLINKSFARMCAPDEVPlanByStatus(2);
                disapprovedPlans = lfDAO.getAllLINKSFARMCAPDEVPlanByStatus(3);
                implementedPlans = lfDAO.getAllLINKSFARMCAPDEVPlanByStatus(5);
            
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>


                </section>

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


                    <!--PENDING-->
                    <div class="row" id="1" style="display:none;">
                        <div class="col-xs-12" >
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Pending CAPDEV Proposals</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example5" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>Cluster Name</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(CAPDEVPlan p : pendingPlans){
                                                    Cluster c = lfDAO.getClusterByID(p.getClusterID());
                                            %>
                                            <tr>
                                                <%if(userType == 4){ // RFO%>
                                                <td><a href="ViewCAPDEVProposal?planID=<%out.print(p.getPlanID());%>&linksfarm=1"><%out.print(p.getPlanDTN());%></a></td>
                                                    <%}else{%>
                                                <td><a href="ReviewCAPDEVAssessment?planID=<%out.print(p.getPlanID());%>"><%out.print(p.getPlanDTN());%></a></td>
                                                    <%}%>
                                                <td><a href="ViewCluster?clusterID=<%=c.getClusterID()%>"><%=c.getClusterName()%></a></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                            </tr>
                                            <%}%>

                                        </tbody>

                                        <tfoot>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>Cluster Name</th>
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

                    <!--                    APPROVED-->
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
                                    <table id="example6" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>Cluster Name</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>  
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(CAPDEVPlan p : approvedPlans){
                                                    Cluster c = lfDAO.getClusterByID(p.getClusterID());
                                            %>
                                            <tr>
                                                <%if(userType == 3){%>
                                                <td><a href="ProceedAssignPointPerson?planID=<%out.print(p.getPlanID());%>&linksfarm=1"><%out.print(p.getPlanDTN());%></a></td>
                                                    <%}else{%>
                                                <td><a href="ReviewCAPDEVAssessment?planID=<%out.print(p.getPlanID());%>"><%out.print(p.getPlanDTN());%></a></td>
                                                    <%}%>
                                                <td><a href="ViewCluster?clusterID=<%=c.getClusterID()%>"><%=c.getClusterName()%></a></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                            </tr>
                                            <%}%>

                                        </tbody>

                                        <tfoot>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>Cluster Name</th>
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

                    <!--                                            DISAPPROVED-->
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
                                                <th>Plan DTN</th>
                                                <th>Cluster Name</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>  
                                            </tr>
                                        </thead>

                                        <tbody id="proposals">

                                            <%
                                                for(CAPDEVPlan p : disapprovedPlans){
                                                    Cluster c = lfDAO.getClusterByID(p.getClusterID());
                                            %>
                                            <tr>
                                                <!--WITH CAPDEV-->
                                                <td><%out.print(p.getPlanDTN());%></td>
                                                <td><a href="ViewCluster?clusterID=<%=c.getClusterID()%>"><%=c.getClusterName()%></a></td>
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
                                                <th>Plan DTN</th>
                                                <th>Cluster Name</th>
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
                    <!--IMPLEMENTED-->
                    <div class="row" id="4" style="display:none;">
                        <div class="col-xs-12" >
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Implemented CAPDEV Proposals</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="example3" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>Cluster Name</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>   
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(CAPDEVPlan p : implementedPlans){
                                                    Cluster c = lfDAO.getClusterByID(p.getClusterID());
                                            %>
                                            <tr>
                                                <td><a href="ReviewCAPDEVAssessment?planID=<%out.print(p.getPlanID());%>"><%out.print(p.getPlanDTN());%></a></td>
                                                <td><a href="ViewCluster?clusterID=<%=c.getClusterID()%>"><%=c.getClusterName()%></a></td>
                                                <td><%out.print(p.getActivities().size());%></td>
                                                <td><%out.print(p.getPlanStatusDesc());%></td>
                                            </tr>
                                            <%}%>

                                        </tbody>

                                        <tfoot>
                                            <tr>
                                                <th>Plan DTN</th>
                                                <th>Cluster Name</th>
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

                    <!-- /.row -->
                </section>
                <!-- /.content -->

            </div>
            <!-- /.content-wrapper -->

        </div>
        <%@include file="jspf/footer.jspf" %>
        <script>



            function chg2() {
                var values = $('#regions').val();

                for (i = 0; i < values.length; i++) {
                    if (i === 0) {
                        url += "valajax=" + values[i];
                    } else {
                        url += "&valajax=" + values[i];
                    }

                }

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('provinces').innerHTML = xhttp.responseText;
                    }
                };

                var url = "RegionalProvincesFilterRefresh?";
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
    </body>
</html>
