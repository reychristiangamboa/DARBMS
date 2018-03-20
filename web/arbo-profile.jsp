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

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%if ((Integer) session.getAttribute("userType") == 2) {%>
            <%@include file="jspf/point-person-sidebar.jspf" %>
            <%} else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>

            <%
                ARBO arbo = (ARBO) request.getAttribute("arbo");
                ARBODAO dao = new ARBODAO();
                ARBDAO dao2 = new ARBDAO();
                CropDAO dao3 = new CropDAO();
                CAPDEVDAO capdevDAO2 = new CAPDEVDAO();
                APCPRequestDAO dao4 = new APCPRequestDAO();
                ArrayList<ARB> arbListARBO = dao2.getAllARBsARBO(arbo.getArboID());
                ArrayList<APCPRequest> arboRequest = dao4.getAllARBORequests(arbo.getArboID());
                ArrayList<APCPRequest> arboReleasedRequest = dao4.getAllARBORequestsByStatus(5, arbo.getArboID());
                ArrayList<CAPDEVActivity> activityHistory = capdevDAO2.getAPCPCAPDEVActivityHistoryByARBO(arbo.getArboID());
                ArrayList<Repayment> repaymentHistory = dao4.getRepaymentHistoryByARBO(arbo.getArboID());
                ArrayList<Crop> crops = dao3.getAllCropsByARBList(arbListARBO);
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        ARBO Profile
                    </h1>
                </section>

                <!-- Main content -->
                <section class="content">

                    <div class="row">
                        <div class="col-md-3">

                            <!-- Profile Image -->
                            <div class="box box-primary">
                                <div class="box-body box-profile">
                                    <h3 class="profile-username text-center"><%=arbo.getArboName()%></h3>
                                    <p class="text-muted text-center">Agrarian Reform Beneficiary Organization</p>

                                    <ul class="list-group list-group-unbordered">
                                        <li class="list-group-item">
                                            <b>Crops</b>
                                            <p>
                                                <%
                                                    for (Crop c : dao3.getAllCropsByARBList(arbListARBO)) {
                                                %>
                                                <span class="label label-success"><%out.print(c.getCropTypeDesc());%></span>
                                                <%}%>
                                            </p>
                                        </li>
                                        <li class="list-group-item">
                                            <b>Members</b> <a class="pull-right" data-toggle="modal" data-target="#arbs"><%=dao.getARBCount(arbo.getArboID())%></a>
                                        </li>
                                    </ul>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->

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
                                                    <table id="arbTable" class="table table-bordered table-striped">
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
                                                            %>
                                                            <tr>
                                                                <td><a href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFullName());%></a></td>
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
                                                    <button class="btn btn-primary" onclick="form.action = 'ProceedAddARB?id=<%out.print(arbo.getArboID());%>'">Add ARB</button>
                                                </form>
                                            </div>
                                        </div>
                                        <%}%>

                                    </div>
                                    <!--                                            /.modal-content -->
                                </div>
                                <!--                                        /.modal-dialog -->
                            </div>

                            <!-- About Me Box -->
                            <div class="box box-primary">
                                <div class="box-header with-border">
                                    <h3 class="box-title">About the ARBO</h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <strong><i class="fa fa-book margin-r-5"></i> Main Office</strong>
                                    <p class="text-muted">
                                        Bataan
                                    </p>
                                    <hr>
                                    <strong><i class="fa fa-map-marker margin-r-5"></i> Location</strong>
                                    <p class="text-muted"><%=arbo.getFullAddress()%></p>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                        <div class="col-md-9">
                            <!-- /.col -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">ARB Visuals</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
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
                                                                    <div class="col-xs-6">
                                                                        <table class="table table-bordered table-striped modTable">
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
                                                                    <div class="col-xs-6">
                                                                        <table class="table table-bordered table-striped modTable">
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

                                                            <table class="table table-bordered table-striped modTable">
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
                                                        ArrayList<ARB> arbList = arbDAO.getAllARBsOfARBOs(arboList);
                                                        ArrayList<Integer> arbListCityMunCodes = arbDAO.getARBCityMun(arbList);
                                                        ArrayList<CityMun> cityMunList = addressDAO.getAllCityMunsByID(arbListCityMunCodes);

                                                        for (CityMun cm : cityMunList) {
                                                            ArrayList<ARB> arbListCityMun = arbDAO.getAllARBsByCityMun(cm.getCityMunCode());
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

                                                                        <table class="table table-bordered table-striped modTable">
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
                                                                                    <td><a href="ViewARB?id=<%out.print(arb.getArboID());%>" class="btn btn-link"><%out.print(arb.getFullName());%></a></td>
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

                                                            <table class="table table-bordered table-striped modTable">
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
                                            <!-- /.tab-pane -->
                                        </div>
                                        <!-- /.tab-content -->
                                    </div>
                                    <!-- /.nav-tabs-custom -->

                                </div>
                            </div>

                            <!-- /.col -->
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">APCP Visuals</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
                                <div class="box-body">
                                    <div class="nav-tabs-custom">
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#release" data-toggle="tab">Release Line</a></li>
                                            <li><a href="#pend" data-toggle="tab">Pending Requests</a></li>
                                            <li><a href="#disbursement" data-toggle="tab">Disbursements</a></li>
                                            <li><a href="#repayment" data-toggle="tab">Repayments</a></li>
                                            <li><a href="#pastDue" data-toggle="tab">Past Due</a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="active tab-pane" id="release">
                                                <table id="example1" class="table table-bordered table-striped">
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
                                                        %>
                                                        <tr>
                                                            <td><a href="MonitorRelease?id=<%out.print(r.getRequestID());%>"><%=r.getLoanTrackingNo()%></a></td>

                                                            <td><%=f.format(r.getReleases().get(r.getReleases().size() - 1).getReleaseDate())%></td>
                                                            <td  width=50%>
                                                                <div class="progress">
                                                                    <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: <%out.print(r.getProgressBarWidth(dao4.getSumOfReleasesByRequestId(r.getRequestID()), r.getLoanAmount()));%>%">
                                                                        <strong><i>&#8369</i><%=dao4.getSumOfReleasesByRequestId(r.getRequestID())%> / <i>&#8369</i><%=r.getLoanAmount()%></strong>
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
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="pend">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <table id="example6" class="table table-bordered table-striped">
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
                                                                    <td><%out.print(r.getLoanReason());%></td>
                                                                    <td><%out.print(r.getLoanAmount());%></td>
                                                                    <td><%out.print(r.getHectares() + " hectares");%></td>

                                                                    <%if (r.getRequestStatus() == 1) {%>
                                                                    <td><%out.print(f.format(r.getDateRequested()));%></td>
                                                                    <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                        <%} else if (r.getRequestStatus() == 2) {%>
                                                                    <td><%out.print(f.format(r.getDateCleared()));%></td>
                                                                    <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                        <%} else if (r.getRequestStatus() == 3) {%>
                                                                    <td><%out.print(f.format(r.getDateEndorsed()));%></td>
                                                                    <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                        <%} else if (r.getRequestStatus() == 4) {%>
                                                                    <td><%out.print(f.format(r.getDateApproved()));%></td>
                                                                    <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                        <%} else if (r.getRequestStatus() == 5) {%>
                                                                    <td><%out.print(f.format(r.getDateApproved()));%></td>
                                                                    <td><span class="label label-success"><%out.print(r.getRequestStatusDesc());%></span></td>
                                                                        <%} else if (r.getRequestStatus() == 7) {%>
                                                                    <td><%out.print(f.format(r.getDateApproved()));%></td>
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
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="disbursement">
                                                <div class="box-body">
                                                    <canvas id="pieDisbursements" style="height:250px"></canvas>
                                                </div>
                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="repayment" style="overflow-y: scroll; overflow-x: hidden;  max-height: 300px; ">
                                                <ul class="timeline">

                                                    <%System.out.println(repaymentHistory.size());
                                                        System.out.println(arbo.getArboID());
                                                        for (Repayment repayment : repaymentHistory) { %>

                                                    <li class="time-label">
                                                        <span class="bg-green">
                                                            <%out.print(f.format(repayment.getDateRepayment()));%>
                                                        </span>
                                                    </li>
                                                    <li>
                                                        <!-- for loop for plans -->
                                                        <i class="fa fa-money bg-green"></i>
                                                        <div class="timeline-item">
                                                            <!--                                <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>-->
                                                            <h3 class="timeline-header"><i>&#8369</i><%=repayment.getAmount()%></h3>
                                                            <div class="timeline-body">

                                                                <i class="fa  fa-user"></i>&nbsp;<%=dao2.getARBByID(repayment.getArbID()).getFullName()%>
                                                            </div>
                                                        </div>
                                                    </li>

                                                    <% }%>
                                                </ul>
                                            </div>
                                            <!-- /.tab-pane -->

                                            <div class="tab-pane" id="pastDue">
                                                <div class="box-body">
                                                    <canvas id="pieCanvas" style="height:250px"></canvas>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- /.tab-content -->
                                    </div>
                                    <!-- /.nav-tabs-custom -->

                                </div>
                            </div>
                            <div class="box">
                                <div class="box-header with-border" >
                                    <h3 class="box-title">CAPDEV Visuals</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="box-body" >
                                    <div class="nav-tabs-custom">
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#apcpCapdev" data-toggle="tab">APCP CAPDEV</a></li>
                                            <li><a href="#linksfarmCapdev" data-toggle="tab">LINKSFARM CAPDEV</a></li>

                                        </ul>
                                        <div class="tab-content"  style="overflow-y: scroll; overflow-x: hidden;  max-height: 300px; ">
                                            <div class="active tab-pane" id="apcpCapdev">
                                                <div class="col-xs-12" style="margin:10px;" >
                                                    <ul class="timeline">
                                                        <%
                                                            boolean firstInstance = true;
                                                            Date date = null;
                                                        %>
                                                        <% for (CAPDEVActivity activity : activityHistory) { %>
                                                        <%
                                                            boolean dateChanged = false;

                                                            if (firstInstance) { // FIRST INSTANCE
                                                                date = activity.getActivityDate();
                                                            }
                                                        %>

                                                        <%
                                                            if (date.compareTo(activity.getActivityDate()) != 0) { // NEW DATE, change currDate
                                                                date = activity.getActivityDate();
                                                                dateChanged = true;
                                                                System.out.print("Date changed!");
                                                            }
                                                        %>

                                                        <%if (firstInstance || dateChanged) {%>
                                                        <li class="time-label">
                                                            <span class="bg-green">
                                                                <%out.print(f.format(date));%>
                                                            </span>
                                                        </li>
                                                        <%firstInstance = false;%>
                                                        <%}%>

                                                        <li>
                                                            <%if (activity.getActivityCategory() == 1) {%>
                                                            <i class="fa fa-clipboard bg-green"></i>
                                                            <%} else if (activity.getActivityCategory() == 2) {%>
                                                            <i class="fa fa-clipboard bg-red"></i>
                                                            <%} else if (activity.getActivityCategory() == 3) {%>
                                                            <i class="fa fa-clipboard bg-orange"></i>
                                                            <%}%>
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
                                                                            <div class="col-xs-4">
                                                                                <div class="form-group">
                                                                                    <label for="">Activity Date</label>
                                                                                    <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(f.format(activity.getActivityDate()));%>" disabled>
                                                                                </div>
                                                                            </div>
                                                                            <div class="col-xs-4">
                                                                                <div class="form-group">
                                                                                    <label for="">No. of Participants</label>
                                                                                    <input style='border-left: none; border-right: none; border-top: none; background: none;' type="text" class="form-control" value="<%out.print(activity.getArbList().size());%>" disabled>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <div class="row">
                                                                            <div class="col-xs-12">
                                                                                <div class="form-group">
                                                                                    <label for="">Observations</label>
                                                                                    <textarea id="" cols="30" rows="3" class="form-control" disabled><%if (activity.getObservations() != null) {
                                                                                            out.print(activity.getObservations());
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
                                                                                    <textarea id="" cols="30" rows="3" class="form-control" disabled><%if (activity.getRecommendation() != null) {
                                                                                            out.print(activity.getRecommendation());
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
                                                            <!--                                        /.modal-dialog -->
                                                        </div>
                                                        <% }%>
                                                    </ul>

                                                </div>


                                            </div>

                                            <div class="tab-pane" id="linksfarmCapdev">

                                                <div class="col-xs-12" style="margin:10px;" >
                                                    lol

                                                </div>


                                            </div>
                                        </div>


                                        <!-- /.tab-content -->
                                    </div>
                                    <!-- /.nav-tabs-custom -->

                                </div>
                            </div>

                            <!-- /.col -->

                        </div>
                        <!-- /.col -->


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
                String json2 = line.getCropHistory(crops);
            %>
                new Chart(ctx2, <%out.print(json2);%>);

                var ctx3 = $('#pieCanvas').get(0).getContext('2d');
            <%
                Chart pie = new Chart();
                String json3 = pie.getPieChartGender(arbListARBO);
            %>
                new Chart(ctx3, <%out.print(json3);%>);

                var ctx4 = $('#pieDisbursements').get(0).getContext('2d');
            <%
                Chart pie2 = new Chart();
                String json4 = pie2.getPieChartDisbursement(arbListARBO);
            %>
                new Chart(ctx4, <%out.print(json4);%>);


            });
        </script>
    </body>
</html>