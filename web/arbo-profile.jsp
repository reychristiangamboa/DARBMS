<%-- 
    Document   : arbo-profile
    Created on : Mar 14, 2018, 6:33:30 PM
    Author     : Rey Christian
--%>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="jspf/header.jspf"%>
        <style>
            .rate{
                color:black;
                cursor:pointer;
                width: 80px;
                margin: 0 auto;
            }
            .rate:hover{
                color:red;
            }
            .checked {
                color: orange;
            }

            @media screen and (min-width: 768px) {
                .modal-dialog {
                    width: 700px; /* New width for default modal */
                }
                .modal-sm {
                    width: 350px; /* New width for small modal */
                }
            }
            @media screen and (min-width: 992px) {
                .modal-lg {
                    width: 950px; /* New width for large modal */
                }
            }
            @media screen and (min-width: 1080px) {
                .modal-lger {
                    width: 1080px; /* New width for large modal */
                }
            }


        </style>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%if ((Integer) session.getAttribute("userType") == 2) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pp-apcp-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/regional-field-officer-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/central-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 6) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pfo-apcp-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 7) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pfo-capdev-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 8) {%>
            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pp-capdev-sidebar.jspf" %>
            <%}%>

            <%
                ARBO arbo = (ARBO) request.getAttribute("arbo");
                
                ARBODAO dao = new ARBODAO();
                ARBDAO dao2 = new ARBDAO();
                CropDAO dao3 = new CropDAO();
                CAPDEVDAO capdevDAO2 = new CAPDEVDAO();
                APCPRequestDAO dao4 = new APCPRequestDAO();
                
                arbo.setRequestList(dao4.getAllARBORequests(arbo.getArboID()));
                arbo.setArbList(dao2.getAllARBsARBO(arbo.getArboID()));
                
                ArrayList<ARB> arbListARBO = dao2.getAllARBsARBO(arbo.getArboID());
                ArrayList<APCPRequest> arboRequest = dao4.getAllARBORequests(arbo.getArboID());
                ArrayList<APCPRequest> arboReleasedRequest = dao4.getAllARBORequestsByStatus(5, arbo.getArboID());
                
                ArrayList<CAPDEVPlan> plans = capdevDAO2.getAllCAPDEVPlanARBO(arbo.getArboID());
                
                ArrayList<Repayment> repaymentHistory = dao4.getRepaymentHistoryByARBO(arbo.getArboID());
                ArrayList<Crop> crops = dao3.getAllCropsByARBList(arbListARBO);
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1><i class="fa fa-group"></i> Agrarian Reform Beneficiary Organization Profile</h1>
                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-3">
                            <!-- Profile Image -->
                            <div class="box box-primary">
                                <div class="box-body box-profile">
                                    <h3 class="profile-username text-center"><%=arbo.getArboName()%></h3>
                                    <p class="text-muted text-center">Agrarian Reform Beneficiary Organization</p>

                                    <ul class="list-group list-group-unbordered">
                                        <li class="list-group-item">
                                            <b><i class="fa fa-tree margin-r-5"></i>Crops</b>
                                            <p>
                                                <%
                                                    for (Crop c : dao3.getAllCropsByARBList(arbListARBO)) {
                                                %>
                                                <span class="label label-success"><%out.print(c.getCropTypeDesc());%></span>
                                                <%}%>
                                            </p>
                                        </li>
                                        <li class="list-group-item">
                                            <b><i class="fa fa-user margin-r-5"></i>Members</b> <a class="pull-right" data-toggle="modal" data-target="#arbs"><%out.print(arbo.getArbList().size());%></a>
                                        </li>
                                        <%if((Integer)session.getAttribute("userType") == 3){%>
                                        <li class="list-group-item text-center">
                                            <a class="btn btn-primary btn-lg" href="ProceedAddARB?id=<%out.print(arbo.getArboID());%>&source=profile"><i class="fa fa-user-plus margin-r-5"></i>Add ARB</a>
                                        </li>
                                        <%}%>
                                    </ul>
                                </div>



                                <div class="modal fade" id="arbs">
                                    <div class="modal-dialog modal-lg">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                                <h4 class="modal-title">Agrarian Reform Beneficiaries</h4>

                                            </div>


                                            <div class="modal-body" id="modalBody">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <table class="table table-bordered table-striped export">
                                                            <thead>
                                                                <tr>
                                                                    <th>Full Name</th>
                                                                    <th>Address</th>
                                                                    <th>Crops</th>
                                                                </tr>
                                                            </thead>

                                                            <tbody>
                                                                <%
                                                                    for (ARB arb : arbListARBO) {
                                                                        arb.setCurrentCrops(dao2.getAllARBCurrentCrops(arb.getArbID()));
                                                                %>
                                                                <tr>
                                                                    <td><a target="_blank" rel="noopener noreferrer" href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFullName());%></a></td>
                                                                    <td><%out.print(arb.getFullAddress());%></td>
                                                                    <td><%out.print(arb.printAllCrops());%></td>
                                                                </tr>
                                                                <%}%>
                                                            </tbody>

                                                            <tfoot>
                                                                <tr>
                                                                    <th>Full Name</th>
                                                                    <th>Address</th>
                                                                    <th>Crops</th>
                                                                </tr>
                                                            </tfoot>

                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                            <%if ((Integer) session.getAttribute("userType") == 3) {%>
                                            <div class="modal-footer">
                                                <div class="pull-right">
                                                    <form method="post">
                                                        <button class="btn btn-primary btn-sm" onclick="form.action = 'ProceedAddARB?id=<%out.print(arbo.getArboID());%>'">Add ARB</button>
                                                    </form>
                                                </div>
                                            </div>
                                            <%}%>

                                        </div>
                                    </div>
                                </div>
                                <div class="box-body">
                                    <strong><i class="fa fa-book margin-r-5"></i> Main Office</strong>
                                    <p class="text-muted">
                                        <%=arbo.getProvOfficeCodeDesc()%>
                                    </p>
                                    <hr>
                                    <strong><i class="fa fa-map-marker margin-r-5"></i> Location</strong>
                                    <p class="text-muted"><%=arbo.getFullAddress()%></p>
                                </div>
                            </div>
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Credit Standing</h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <strong><i class="fa fa-money margin-r-5"></i> Approved Amount</strong>
                                    <p class="text-muted"><%out.print(currency.format(dao4.getTotalApprovedAmount(arbo.getRequestList())));%></p>
                                    <hr>
                                    <strong><i class="fa fa-money margin-r-5"></i> Released Amount</strong>
                                    <p class="text-muted"><%out.print(currency.format(dao4.getSumOfAccumulatedReleasesByRequest(arbo.getRequestList())));%></p>
                                    <hr>
                                    <strong><i class="fa fa-money margin-r-5"></i> Outstanding Balance</strong>
                                    <p class="text-muted"><%out.print(currency.format(dao4.getTotalARBOOSBalance(arbo.getRequestList())));%></p>
                                    <hr>
                                    <strong><i class="fa fa-money margin-r-5"></i> Past Due Amount</strong>
                                    <p class="text-muted"><%out.print(currency.format(dao4.getTotalPastDueAmount(arbo.getRequestList())));%></p>
                                    <hr>
                                    <strong><i class="fa fa-briefcase margin-r-5"></i> Average Days Unsettled</strong>
                                    <p class="text-muted"><%out.print(dao4.getAverageDaysUnsettled(arbo.getRequestList()));%></p>
                                </div>
                            </div>
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                    <h3 class="box-title">CAPDEV</h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <strong><i class="fa fa-clipboard margin-r-5"></i> Implemented Plans</strong>
                                    <p class="text-muted"><%out.print(capdevDAO2.getImplementedPlanCountARBO(arbo.getRequestList()));%></p>
                                    <hr>
                                    <strong><i class="fa fa-clipboard margin-r-5"></i> Scheduled Plans</strong>
                                    <p class="text-muted"><%out.print(capdevDAO2.getScheduledPlanCountARBO(arbo.getRequestList()));%></p>
                                    <hr>
                                    <strong><i class="fa fa-clipboard margin-r-5"></i> Participation Rate</strong>
                                    <p class="text-muted"><%out.print(df.format(capdevDAO2.getMeanAverageAttendanceRateARBO(arbListARBO))+"%");%></p>

                                </div>
                            </div>

                        </div>
                        <div class="col-xs-9">
                            <div class="box box-solid">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Details</h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">

                                    <!--ARB VISUALS--> 
                                    <div class="panel box box-danger">
                                        <div class="box-header with-border">
                                            <h4 class="box-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" class="collapsed" aria-expanded="false">
                                                    ARB Visuals
                                                </a>
                                            </h4>
                                        </div>
                                        <div id="collapseTwo" class="panel-collapse collapse in" aria-expanded="false">
                                            <div class="box-body">
                                                <div class="nav-tabs-custom">
                                                    <ul class="nav nav-tabs">

                                                        <li class="active"><a href="#gender" data-toggle="tab">ARB Per Gender</a></li>
                                                        <li><a href="#educ" data-toggle="tab">ARB Education Level</a></li>
                                                        <li><a href="#citymun" data-toggle="tab">ARB Per City Municipality</a></li>
                                                        <li><a href="#crop" data-toggle="tab">Crops History</a></li>
                                                    </ul>
                                                    <div class="tab-content">
                                                        <div class="active tab-pane" id="gender">
                                                            <div class="row">
                                                                <div class="col-xs-2"></div>
                                                                <div class="col-xs-8">
                                                                    <div class="box-body">
                                                                        <canvas id="pieCanvas" style="height:150px"></canvas>
                                                                        <div class="row text-center">
                                                                            <a class="btn btn-submit" data-toggle="modal" data-target="#modalPie">View More</a>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-2"></div>
                                                            </div>
                                                        </div>
                                                        <div class="modal fade" id="modalPie">
                                                            <div class="modal-dialog modal-lger">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span></button>
                                                                        <h4 class="modal-title"></h4>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <div class="row">
                                                                            <div class="col-xs-12">
                                                                                <table class="table table-bordered table-striped export">
                                                                                    <thead>
                                                                                        <tr>
                                                                                            <th>Male</th>
                                                                                            <th>ARBO</th>
                                                                                            <th>Address</th>
                                                                                            <th>Land Area</th>
                                                                                            <th>ARB Rating</th>
                                                                                            <th>ARB Status</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                    <tbody>
                                                                                        <%
                                                                                            ARBODAO arbol = new ARBODAO();
                                                                                            for (ARB arb : arbListARBO) {
                                                                                                if (arb.getGender().equalsIgnoreCase("M")) {
                                                                                        %>
                                                                                        <tr>
                                                                                            <td><a href="ViewARB?id=<%out.print(arb.getArbID());%>"> <%out.print(arb.getFullName());%> </a></td>
                                                                                            <td><%out.print(arbol.getARBOByID(arb.getArboID()).getArboName());%></td>
                                                                                            <td><%out.print(arb.getFullAddress());%></td>
                                                                                            <td><%out.print(arb.getLandArea());%></td>
                                                                                            <td><%out.print(arb.getArbRating());%></td>
                                                                                            <td><%out.print(arb.getArbStatusDesc());%></td>
                                                                                        </tr>
                                                                                        <%}
                                                                                    }%>

                                                                                    </tbody>
                                                                                    <tfoot>
                                                                                        <tr>
                                                                                            <th>Male</th>
                                                                                            <th>ARBO</th>
                                                                                            <th>Address</th> 
                                                                                            <th>Land Area</th>
                                                                                            <th>ARB Rating</th>
                                                                                            <th>ARB Status</th>

                                                                                        </tr>
                                                                                    </tfoot>
                                                                                </table>
                                                                            </div>
                                                                        </div>
                                                                        <div class="row">
                                                                            <div class="col-xs-12">
                                                                                <table class="table table-bordered table-striped export">
                                                                                    <thead>
                                                                                        <tr>
                                                                                            <th>Female</th>
                                                                                            <th>ARBO</th>
                                                                                            <th>Address</th>
                                                                                            <th>Land Area</th>
                                                                                            <th>ARB Rating</th>
                                                                                            <th>ARB Status</th>
                                                                                        </tr>
                                                                                    </thead>
                                                                                    <tbody>
                                                                                        <%
                                                                                            ARBODAO arboll = new ARBODAO();
                                                                                            for (ARB arb : arbListARBO) {
                                                                                                if (arb.getGender().equalsIgnoreCase("F")) {
                                                                                        %>
                                                                                        <tr>
                                                                                            <td><a href="ViewARB?id=<%out.print(arb.getArbID());%>"> <%out.print(arb.getFullName());%> </a></td>
                                                                                            <td><%out.print(arboll.getARBOByID(arb.getArboID()).getArboName());%></td>
                                                                                            <td><%out.print(arb.getFullAddress());%></td>
                                                                                            <td><%out.print(arb.getLandArea());%></td>
                                                                                            <td><%out.print(arb.getArbRating());%></td>
                                                                                            <td><%out.print(arb.getArbStatusDesc());%></td>
                                                                                        </tr>
                                                                                        <%}
                                                                                    }%>

                                                                                    </tbody>
                                                                                    <tfoot>
                                                                                        <tr>
                                                                                            <th>Female</th>
                                                                                            <th>ARBO</th>
                                                                                            <th>Address</th> 
                                                                                            <th>Land Area</th>
                                                                                            <th>ARB Rating</th>
                                                                                            <th>ARB Status</th>

                                                                                        </tr>
                                                                                    </tfoot>
                                                                                </table>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                                    </div>
                                                                </div>
                                                                <!-- /.modal-content -->
                                                            </div>
                                                            <!-- /.modal-dialog -->
                                                        </div>
                                                        <!-- /.tab-pane -->
                                                        <div class="tab-pane" id="educ">
                                                            <div class="box-body" id="bar">
                                                                <div class="row">
                                                                    <div class="col-xs-2"></div>
                                                                    <div class="col-xs-8">
                                                                        <div class="chart">
                                                                            <canvas id="barCanvas" style="height:230px"></canvas>
                                                                            <div class="row text-center">
                                                                                <a class="btn btn-submit" data-toggle="modal" data-target="#modalBar">View More</a>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-xs-2"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal fade" id="modalBar">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span></button>
                                                                        <h4 class="modal-title"></h4>
                                                                    </div>
                                                                    <div class="modal-body">

                                                                        <table class="table table-bordered table-striped export">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>ARB Name</th>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>Gender</th>
                                                                                    <th>Education Level</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                    ARBODAO arboEduc = new ARBODAO();
                                                                                    for (ARB arb : arbListARBO) {

                                                                                %>
                                                                                <tr>
                                                                                    <td><%out.print(arb.getFullName());%></td>
                                                                                    <td><%out.print(arboEduc.getARBOByID(arb.getArboID()).getArboName());%></td>
                                                                                    <td><%out.print(arb.getGender());%></td>
                                                                                    <td><%out.print(arb.getEducationLevelDesc());%></td>
                                                                                </tr>
                                                                                <%}%>
                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>ARB Name</th>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>Gender</th>
                                                                                    <th>Education Level</th>
                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>

                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                                    </div>
                                                                </div>
                                                                <!-- /.modal-content -->
                                                            </div>
                                                            <!-- /.modal-dialog -->
                                                        </div>
                                                        <!-- /.tab-pane -->
                                                        <div class="tab-pane" id="citymun">
                                                            <div class="box-body">
                                                                <%
                                                                    AddressDAO addressDAO = new AddressDAO();
                                                                    ARBDAO arbDAO = new ARBDAO();
                                                                    ARBODAO arboDAO = new ARBODAO();
                                                                    ArrayList<ARBO> arboList = new ArrayList();
                                                                    if ((Integer) session.getAttribute("userType") == 3) {
                                                                        arboList = arboDAO.getAllARBOsByProvince((Integer) session.getAttribute("provOfficeCode"));
                                                                    } else if ((Integer) session.getAttribute("userType") == 4) {
                                                                        arboList = arboDAO.getAllARBOsByRegion((Integer) session.getAttribute("regOfficeCode"));
                                                                    }
                                                                    ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(arbo.getArboID());
                                                                    ArrayList<Integer> arbListCityMunCodes = arbDAO.getARBCityMun(arbList);
                                                                    ArrayList<CityMun> cityMunList = addressDAO.getAllCityMunsByID(arbListCityMunCodes);

                                                                    for (CityMun cm : cityMunList) {
                                                                        ArrayList<ARB> arbListCityMun = arbDAO.getAllARBsByCityMun(cm.getCityMunCode(), arbo.getArboID());
                                                                %>
                                                                <div class="active tab-pane" >
                                                                    <div class="col-lg-2 col-xs-6" data-toggle="modal" data-target="#modal-default<%out.print(cm.getCityMunCode());%>">
                                                                        <!-- small box -->
                                                                        <div class="small-box bg-yellow">
                                                                            <div class="inner">
                                                                                <h3><%out.print(arbListCityMun.size());%></h3>

                                                                                <p><%=cm.getCityMunDesc()%></p>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal fade" id="modal-default<%out.print(cm.getCityMunCode());%>">
                                                                        <div class="modal-dialog">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                        <span aria-hidden="true">&times;</span></button>
                                                                                    <h4 class="modal-title"><%out.print(cm.getCityMunDesc());%></h4>
                                                                                </div>
                                                                                <div class="modal-body">

                                                                                    <table class="table table-bordered table-striped export">
                                                                                        <thead>
                                                                                            <tr>
                                                                                                <th>ARB Name</th>
                                                                                                <th>Gender</th>
                                                                                                <th>Member Since</th>
                                                                                                <th>Address</th>
                                                                                            </tr>
                                                                                        </thead>
                                                                                        <tbody>
                                                                                            <%
                                                                                    for (ARB arb : arbListCityMun) { %>
                                                                                            <tr>
                                                                                                <td><a href="ViewARB?id=<%out.print(arb.getArbID());%>" class="btn btn-link"><%out.print(arb.getFullName());%></a></td>
                                                                                                <td><%out.print(arb.getGender());%></td>
                                                                                                <td><%out.print(arb.getMemberSince());%></td>
                                                                                                <td><%out.print(arb.getFullAddress());%></td>
                                                                                            </tr>
                                                                                            <%}%>
                                                                                        </tbody>
                                                                                        <tfoot>
                                                                                            <tr>
                                                                                                <th>ARB Name</th>
                                                                                                <th>Gender</th>
                                                                                                <th>Member Since</th>
                                                                                                <th>Address</th>
                                                                                            </tr>
                                                                                        </tfoot>
                                                                                    </table>

                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                                                </div>
                                                                            </div>
                                                                            <!-- /.modal-content -->
                                                                        </div>
                                                                        <!-- /.modal-dialog -->
                                                                    </div>
                                                                </div>
                                                                <%}%>
                                                            </div>
                                                        </div>
                                                        <!-- /.tab-pane -->
                                                        <div class="tab-pane" id="crop">
                                                            <div class="box-body">
                                                                <div class="row">
                                                                    <div class="col-xs-2"></div>
                                                                    <div class="col-xs-8">
                                                                        <div class="chart">
                                                                            <canvas id="lineCanvas" style="height:250px"></canvas>
                                                                            <div class="row text-center">
                                                                                <a class="btn btn-submit" data-toggle="modal" data-target="#modalLine">View More</a>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-xs-2"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal fade" id="modalLine">
                                                            <div class="modal-dialog">
                                                                <div class="modal-content">
                                                                    <div class="modal-header">
                                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                            <span aria-hidden="true">&times;</span></button>
                                                                        <h4 class="modal-title">Provincial ARB Crop History</h4>
                                                                    </div>
                                                                    <div class="modal-body">

                                                                        <table class="table table-bordered table-striped export">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>ARB Name</th>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>Crop</th>
                                                                                    <th>Start Date</th>
                                                                                    <th>End Date</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                    ArrayList<Crop> cropHistory = dao3.getCropHistory(arbListARBO);
                                                                                    for (Crop c : cropHistory) {
                                                                                        ARB arb = arbDAO.getARBByID(c.getArbID());
                                                                                %>
                                                                                <tr>
                                                                                    <td><%=arb.getFullName()%></td>
                                                                                    <td><%=arboDAO.getARBOByID(arb.getArboID()).getArboName()%></td>
                                                                                    <td><%=c.getCropTypeDesc()%></td>                                                                        
                                                                                    <td><%=f.format(c.getStartDate())%></td>                                                                        
                                                                                    <td><%=f.format(c.getEndDate())%></td>
                                                                                </tr>
                                                                                <%}%>
                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>ARB Name</th>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>Crop</th>


                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>

                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                                    </div>
                                                                </div>
                                                                <!-- /.modal-content -->
                                                            </div>
                                                            <!-- /.modal-dialog -->
                                                        </div>
                                                    </div>
                                                    <!-- /.tab-pane -->
                                                </div>

                                                <!-- /.nav-tabs-custom -->

                                            </div>

                                        </div>
                                    </div>

                                    <!--APCP-->                                           
                                    <div class="panel box box-success">
                                        <div class="box-header with-border">
                                            <h4 class="box-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" class="collapsed" aria-expanded="false">
                                                    APCP Visuals
                                                </a>
                                            </h4>
                                        </div>
                                        <div id="collapseThree" class="panel-collapse collapse in" aria-expanded="false">
                                            <div class="box-body">

                                                <div class="nav-tabs-custom">
                                                    <ul class="nav nav-tabs">
                                                        <li class="active"><a href="#release" data-toggle="tab">Release Line</a></li>
                                                        <li><a href="#pend" data-toggle="tab">Requests</a></li>
                                                        <li><a href="#disbursement" data-toggle="tab">Disbursements & Repayments</a></li>
                                                        <li><a href="#pastDue" data-toggle="tab">Past Due</a></li>
                                                    </ul>
                                                    <div class="tab-content">
                                                        <div class="active tab-pane" id="release">
                                                            <table class="table table-bordered table-striped export">
                                                                <thead>
                                                                    <tr>
                                                                        <th>Loan <br>Tracking No.</th>

                                                                        <th>Last Release Date</th>
                                                                        <th>Progress</th>
                                                                    </tr>
                                                                </thead>
                                                                <tbody>
                                                                    <%
                                                                        for (APCPRequest r : arboReleasedRequest) {
                                                                            r.setReleases(dao4.getAllAPCPReleasesByRequest(r.getRequestID()));
                                                                    %>
                                                                    <tr>
                                                                        <td><a href="MonitorRelease?id=<%out.print(r.getRequestID());%>"><%=r.getLoanTrackingNo()%></a></td>

                                                                        <td><%=r.getReleases().get(r.getReleases().size() - 1).getReleaseDate()%></td>
                                                                        <td width=50%>
                                                                            <div class="progress">
                                                                                <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: <%out.print(r.getProgressBarWidth(dao4.getSumOfReleasesByRequest(r.getRequestID()), r.getLoanAmount()));%>%">
                                                                                    <strong><%=currency.format(dao4.getSumOfReleasesByRequest(r.getRequestID()))%> / <%=currency.format(r.getLoanAmount())%></strong>
                                                                                </div> 
                                                                            </div> 
                                                                        </td>
                                                                    </tr>
                                                                    <%}%>
                                                                </tbody>
                                                                <tfoot>
                                                                    <tr>
                                                                        <th>Loan <br>Tracking No.</th>

                                                                        <th>Last Release Date</th>
                                                                        <th>Progress</th>
                                                                    </tr>
                                                                </tfoot>
                                                            </table>   
                                                        </div>

                                                        <div class="tab-pane" id="pend">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <table class="table table-bordered table-striped export">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>Request ID</th>
                                                                                <th>Loan Reason</th>
                                                                                <th>Loan Amount</th>
                                                                                <th>Land Area</th>
                                                                                <th>Date</th>
                                                                                <th>Status</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <%
                                                                                for (APCPRequest r : arboRequest) {
                                                                            %>
                                                                            <tr>
                                                                                <td><%out.print(r.getRequestID());%></td>
                                                                                <%if(r.getLoanReason().getLoanReason() == 0){%> <!--OTHERS-->
                                                                                <td><%out.print(r.getLoanReason().getLoanReasonDesc() + ": " + r.getLoanReason().getOtherReason());%></td>
                                                                                <%}else{%> <!--LOAN REASON-->
                                                                                <td><%out.print(r.getLoanReason().getLoanReasonDesc());%></td>
                                                                                <%}%>
                                                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                                                <td><%out.print(r.getHectares() + " hectares");%></td>

                                                                                <%if (r.getRequestStatus() == 1) {%>
                                                                                <td><%out.print(r.getDateRequested());%></td>
                                                                                <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                                    <%} else if (r.getRequestStatus() == 2) {%>
                                                                                <td><%out.print(r.getDateCleared());%></td>
                                                                                <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                                    <%} else if (r.getRequestStatus() == 3) {%>
                                                                                <td><%out.print(r.getDateEndorsed());%></td>
                                                                                <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                                    <%} else if (r.getRequestStatus() == 4) {%>
                                                                                <td><%out.print(r.getDateApproved());%></td>
                                                                                <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                                    <%} else if (r.getRequestStatus() == 5) {%>
                                                                                <td><%out.print(r.getDateApproved());%></td>
                                                                                <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                                    <%} else if (r.getRequestStatus() == 7) {%>
                                                                                <td><%out.print(r.getDateApproved());%></td>
                                                                                <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                                    <%}%>

                                                                            </tr>
                                                                            <%}%>
                                                                        </tbody>
                                                                        <tfoot>
                                                                            <tr>
                                                                                <th>Request ID</th>
                                                                                <th>Loan Reason</th>
                                                                                <th>Loan Amount</th>
                                                                                <th>Land Area</th>
                                                                                <th>Date</th>
                                                                                <th>Status</th>
                                                                            </tr>
                                                                        </tfoot>
                                                                    </table>  
                                                                </div>
                                                            </div>

                                                        </div>

                                                        <div class="tab-pane" id="disbursement">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <canvas id="disbursementRepaymentChart"></canvas>
                                                                </div>  

                                                            </div>
                                                            <div class="row">

                                                                <div class="col-xs-12">
                                                                    <table class="table table-bordered table-striped export">
                                                                        <thead>
                                                                            <tr>
                                                                                <th>ARB</th>
                                                                                <th id="disbursementPie">Disbursement</th>
                                                                                <th id="repaymentPie">Repayment</th>
                                                                                <th id="osPie">O/S Balance</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <%
                                                                                APCPRequest wew = new APCPRequest();
                                                                                for (ARB arb : arbo.getArbList()) {
                                                                            %>
                                                                            <tr>
                                                                                <td><a target="_blank" rel="noopener noreferrer" href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFLName());%></a></td>
                                                                                <td><%out.print(currency.format(arb.getTotalDisbursementAmount()));%></td>
                                                                                <td><%out.print(currency.format(arb.getTotalRepaymentsAmount()));%></td>
                                                                                <td><%out.print(currency.format(wew.getTotalARBOSBalance(arb.getArbID())));%></td>
                                                                            </tr>
                                                                            <%}%>
                                                                        </tbody>
                                                                        <tfoot>
                                                                            <tr>
                                                                                <th>ARB</th>
                                                                                <th>Disbursement</th>
                                                                                <th>Repayment</th>
                                                                                <th>O/S Balance</th>
                                                                            </tr>
                                                                        </tfoot>
                                                                    </table>  
                                                                </div>
                                                            </div>

                                                        </div>

                                                        <div class="tab-pane" id="pastDue">
                                                            <div class="box-body">
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <canvas id="pastDueChart"></canvas>
                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <table class="table table-bordered table-striped export">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>Date Recorded</th>
                                                                                    <th id="amountLine">Amount</th>
                                                                                    <th>Date Settled</th>
                                                                                    <th>Days Unsettled</th>
                                                                                    <th id="reasonPie">Reason</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                    for (APCPRequest r : arbo.getRequestList()) {
                                                                                        r.setPastDueAccounts(dao4.getAllPastDueAccountsByRequest(r.getRequestID()));
                                                                                        for(PastDueAccount pda : r.getPastDueAccounts()){
                                                                                            int daysDiff = pda.getDaysUnsettled();
                                                                                            String color = pda.getCreditStanding(daysDiff);
                                                                                %>
                                                                                <tr>
                                                                                    <td><%out.print(pda.getDateRecorded());%></td>
                                                                                    <td><%out.print(currency.format(pda.getPastDueAmount()));%></td>

                                                                                    <%if(pda.getDateSettled() != null){%>
                                                                                    <td><%out.print(pda.getDateSettled());%></td>
                                                                                    <%}else{%>
                                                                                    <td>Unsettled</td>
                                                                                    <%}%>

                                                                                    <td><small class="label <%out.print("bg-"+color);%>"><%out.print(daysDiff);%></small></td>
                                                                                    <td><%out.print(pda.getReasonPastDueDesc());%></td>
                                                                                </tr>
                                                                                <%
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>Date Recorded</th>
                                                                                    <th>Amount</th>
                                                                                    <th>Date Settled</th>
                                                                                    <th>Days Unsettled</th>
                                                                                    <th>Reason</th>
                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>  
                                                                    </div>
                                                                </div>


                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </div>

                                    <!--CAPDEV-->
                                    <div class="panel box box-success">
                                        <div class="box-header with-border">
                                            <h4 class="box-title">
                                                <a data-toggle="collapse" data-parent="#accordion" href="#collapseFour" class="collapsed" aria-expanded="false">
                                                    CAPDEV Visuals
                                                </a>
                                            </h4>
                                        </div>
                                        <div id="collapseFour" class="panel-collapse collapse in" aria-expanded="false">
                                            <div class="box-body">

                                                <div class="nav-tabs-custom">
                                                    <ul class="nav nav-tabs">
                                                        <li class="active"><a href="#attendanceRate" data-toggle="tab">Attendance Rate</a></li>
                                                        <li><a href="#actTimeline" data-toggle="tab">Activity Timeline</a></li>
                                                    </ul>
                                                    <div class="tab-content">
                                                        <div class="tab-pane active" id ="attendanceRate">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <table class="table table-bordered table-striped export">
                                                                        <thead>
                                                                            <tr>
                                                                                <th class="text-center">ARB</th>
                                                                                <th>Attendance Rate</th>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
                                                                            <%
                                                                                for(ARB arb : arbListARBO){
                                                                                    CAPDEVActivity attendance = new CAPDEVActivity();
                                                                                    ArrayList<CAPDEVActivity> myActivities = capdevDAO2.getCAPDEVPlanByARB(arb.getArbID());
                                                                            %>
                                                                            <tr>
                                                                                <td class="text-center"><a target="_blank" rel="noopener noreferrer" href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFLName());%></a></td>
                                                                                <td width=50%>
                                                                                    <div class="progress">
                                                                                        <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: <%out.print(attendance.getAttendanceRate(myActivities));%>%">
                                                                                            <%out.print(attendance.getAttendance(myActivities));%> / <%out.print(myActivities.size());%>
                                                                                        </div> 
                                                                                    </div> 
                                                                                </td>
                                                                            </tr>
                                                                            <%}%>
                                                                        </tbody>
                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>


                                                        <div class="tab-pane" id="actTimeline">
                                                            <div class="col-xs-12" style="margin:10px; overflow-x: hidden; overflow-y: scroll; max-height: 300px;"  >
                                                                <ul class="timeline">
                                                                    <%
                                                                        boolean firstInstance = true;
                                                                        Long wew123 = System.currentTimeMillis();
                                                                        Date date = null;
                                                                    %>
                                                                    <% 
                                                                        for (CAPDEVPlan plan : plans) { 
                                                                            if(plan.getPlanStatus() == 5){
                                                                            plan.setActivities(capdevDAO2.getCAPDEVPlanActivities(plan.getPlanID()));
                                                                            for(CAPDEVActivity activity : plan.getActivities()){
                                                                                activity.setArbList(capdevDAO2.getCAPDEVPlanActivityParticipants(activity.getActivityID()));
                                                                    %>
                                                                    <%
                                                                        boolean dateChanged = false;

                                                            if (firstInstance) { // FIRST INSTANCE
                                                                date = plan.getImplementedDate();
                                                                System.out.print("WEW!!");
                                                            }
                                                                    %>

                                                                    <%
                                                                        System.out.print("QWERTY");
                                                                        if (date.compareTo(plan.getImplementedDate()) != 0) { // NEW DATE, change currDate
                                                                            date = plan.getImplementedDate();
                                                                            dateChanged = true;
                                                                            System.out.print("Date changed!");
                                                                        }
                                                                        System.out.print("HELOOO!!");
                                                                    %>

                                                                    <%if (firstInstance || dateChanged) {%>
                                                                    <li class="time-label">
                                                                        <span class="bg-green">
                                                                            <%out.print(f.format(date));%>
                                                                            <%System.out.print("EWEWEWEW");%>
                                                                        </span>
                                                                    </li>
                                                                    <%firstInstance = false;%>
                                                                    <%}%>

                                                                    <li>
                                                                        <i class="fa fa-clipboard bg-green"></i>
                                                                        <div class="timeline-item">
                                                                            <h3 class="timeline-header">
                                                                                <a href="#" data-toggle='modal' data-target='#activity<%out.print(activity.getActivityID());%>'><%out.print(activity.getActivityName());%></a>
                                                                            </h3>
                                                                        </div>
                                                                    </li>

                                                                    <div class="modal fade" id="activity<%out.print(activity.getActivityID());%>">
                                                                        <div class="modal-dialog modal-md">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                        <span aria-hidden="true">&times;</span></button>
                                                                                    <h4 class="modal-title">Activity Details</h4>
                                                                                </div>
                                                                                <div class="modal-body" id="modalBody">
                                                                                    <div class="row">
                                                                                        <div class="col-xs-4">
                                                                                            <div class="form-group">
                                                                                                <label for="">Activity Title</label>
                                                                                                <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(activity.getActivityName());%>" disabled>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="row">
                                                                                        <div class="col-xs-8">
                                                                                            <div class="form-group">
                                                                                                <label for="">Activity Description</label>
                                                                                                <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(activity.getActivityDesc());%>" disabled>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="row">
                                                                                        <div class="col-xs-2">
                                                                                            <div class="form-group">
                                                                                                <label for="">No. of Participants</label>
                                                                                                <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(activity.getArbList().size());%>" disabled>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="row">
                                                                                        <div class="col-xs-12">
                                                                                            <table class="table table-bordered table-striped export">
                                                                                                <thead>
                                                                                                    <tr>
                                                                                                        <th>ARB</th>
                                                                                                        <th>Absent/Present</th>
                                                                                                    </tr>
                                                                                                </thead>
                                                                                                <tbody>
                                                                                                    <%for(ARB arb : activity.getArbList()){%>
                                                                                                    <tr>
                                                                                                        <td><a target="_blank" rel="noopener noreferrer" href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFLName());%></a></td>
                                                                                                            <%if(arb.getIsPresent() > 0){%>
                                                                                                        <td>Present</td>
                                                                                                        <%}else{%>
                                                                                                        <td>Absent</td>
                                                                                                        <%}%>
                                                                                                    </tr>
                                                                                                    <%}%>
                                                                                                </tbody>
                                                                                            </table>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="row">
                                                                                        <div class="col-xs-12">
                                                                                            <div class="form-group">
                                                                                                <label for="">Observations</label>
                                                                                                <textarea id="" cols="30" rows="3" class="form-control" disabled><%if (plan.getObservations() != null) {
                                                                                                        out.print(plan.getObservations());
                                                                                                    } else {
                                                                                                        out.print("N/A");
                                                                                                    };%></textarea>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                    <div class="row">
                                                                                        <div class="col-xs-12">
                                                                                            <div class="form-group">
                                                                                                <label for="">Recommendation</label>
                                                                                                <textarea id="" cols="30" rows="3" class="form-control" disabled><%if (plan.getRecommendation() != null) {
                                                                                                        out.print(plan.getRecommendation());
                                                                                                    } else {
                                                                                                        out.print("N/A");
                                                                                                    };%></textarea>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>

                                                                                </div>
                                                                                <div class="modal-footer">
                                                                                    <div class="pull-right">
                                                                                        <button type='button' class="btn btn-default">Cancel</button>
                                                                                        <button type='button' class="btn btn-primary">View More</button>
                                                                                    </div>
                                                                                </div>

                                                                            </div>
                                                                            <!--                                            /.modal-content -->
                                                                        </div>
                                                                    </div>
                                                                    <%}}}%>
                                                                </ul>

                                                            </div>
                                                        </div>



                                                        <!-- /.tab-content -->
                                                    </div>
                                                    <!-- /.nav-tabs-custom -->


                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- /.box-body -->
                            </div>
                        </div>


                    </div>

                    <!-- /.row -->

                </section>
                <!-- /.content -->
            </div>
            <!-- /.content-wrapper -->
        </div>
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>
        <script>

            $(document).ready(function () {
                var context = $('#disbursementRepaymentChart').get(0).getContext('2d');
                var APCPChart;
            <%
                    Chart chart = new Chart();
                    String chartJSON = "";
                    chartJSON = chart.getPieChartDisbursement(arbListARBO);
            %>

                APCPChart = new Chart(context, <%out.print(chartJSON);%>);

                $('#disbursementPie').on('click', function () {
                    APCPChart.destroy();
            <%
                    chartJSON = chart.getPieChartDisbursement(arbListARBO);
            %>
                    APCPChart = new Chart(context, <%out.print(chartJSON);%>);
                });


                $('#repaymentPie').on('click', function () {
                    APCPChart.destroy();
            <%
                    chartJSON = chart.getPieChartRepayment(arbListARBO);
            %>
                    APCPChart = new Chart(context, <%out.print(chartJSON);%>);
                });

                $('#osPie').on('click', function () {
                    APCPChart.destroy();
            <%
                    chartJSON = chart.getPieChartOSBalance(arbListARBO);
            %>
                    APCPChart = new Chart(context, <%out.print(chartJSON);%>);
                });


            });

            $(document).ready(function () {
                var context2 = $('#pastDueChart').get(0).getContext('2d');
                var APCPChart2;
            <%
                    Chart chart2 = new Chart();
                    String chartJSON2 = "";
                    chartJSON2 = chart2.getLineChartPastDue(arbo.getRequestList(),"SETTLED PAST DUE AMOUNTS");
            %>

                APCPChart2 = new Chart(context2, <%out.print(chartJSON2);%>);

                $('#amountLine').on('click', function () {
                    APCPChart2.destroy();
            <%
                    chartJSON2 = chart2.getLineChartPastDue(arbo.getRequestList(),"SETTLED PAST DUE AMOUNTS");
            %>
                    APCPChart2 = new Chart(context2, <%out.print(chartJSON2);%>);
                });


                $('#reasonPie').on('click', function () {
                    APCPChart2.destroy();
            <%
                    chartJSON2 = chart2.getPieChartPastDue(arbo.getRequestList());
            %>
                    APCPChart2 = new Chart(context2, <%out.print(chartJSON2);%>);
                });

            });


            $(function () {
                var ctx = $('#barCanvas').get(0).getContext('2d');
            <%
                Chart bar = new Chart();
                String json = bar.getBarChartEducation(arbListARBO);
            %>
                new Chart(ctx, <%out.print(json);%>);

                var ctx2 = $('#lineCanvas').get(0).getContext('2d');
            <%
                Chart line = new Chart();
                String json2 = line.getCropHistory(crops,arbListARBO);
            %>
                new Chart(ctx2, <%out.print(json2);%>);

                var ctx3 = $('#pieCanvas').get(0).getContext('2d');
            <%
                Chart pie = new Chart();
                String json3 = pie.getPieChartGender(arbListARBO);
            %>
                new Chart(ctx3, <%out.print(json3);%>);

                var ctx5 = $('#pieCanvasPastDue').get(0).getContext('2d');
            <%
                Chart pie3 = new Chart();
                String json5 = pie3.getPieChartPastDue(arboReleasedRequest);
            %>
                new Chart(ctx5, <%out.print(json5);%>);


            });
        </script>
    </body>
</html>