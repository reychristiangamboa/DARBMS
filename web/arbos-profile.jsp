<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="/jspf/header.jspf"%>

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


    <!DOCTYPE html>




    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/pfo-apcp-sidebar.jspf" %>

            <%
//            APCPRequestDAO apcpRequestDAO = new APCPRequestDAO();
//            ARBODAO arboDAO = new ARBODAO();
            
        int reqID = (Integer) request.getAttribute("requestID");
        APCPRequest req = apcpRequestDAO.getRequestByID(reqID);
        ARBO arbo = arboDAO.getARBOByID(req.getArboID());
        UserDAO userDAO = new UserDAO();
            %>                                        
            <% User u1 = new User(); %>
            <% User u2 = new User(); %>
            <% User u3 = new User(); %>
            <% User u4 = new User(); %>

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>

            <%}else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>

            <%}%>

            <%
                // arbo = (ARBO) request.getAttribute("arbo");
//                ARBODAO dao = new ARBODAO();
//                ARBDAO dao2 = new ARBDAO();
//                CropDAO dao3 = new CropDAO();
//                CAPDEVDAO capdevDAO2 = new CAPDEVDAO();
//                APCPRequestDAO dao4 = new APCPRequestDAO();
//                ArrayList<ARB> arbListARBO = dao2.getAllARBsARBO(arbo.getArboID());
//                ArrayList<APCPRequest> arboRequest = dao4.getAllARBORequests(arbo.getArboID());
//                ArrayList<APCPRequest> arboReleasedRequest = dao4.getAllARBORequestsByStatus(5, arbo.getArboID());
//                ArrayList<CAPDEVActivity> activityHistory = capdevDAO2.getAPCPCAPDEVActivityHistoryByARBO(arbo.getArboID());
//                ArrayList<Repayment> repaymentHistory = dao4.getRepaymentHistoryByARBO(arbo.getArboID());
                //ArrayList<Crop> crops = dao3.getAllCropsByARBList(arbListARBO);
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-money"></i> APCP Request for New Accessing Conduits</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>
                </section>

                <!-- Main content -->
                <section class="content">

                    <%if(request.getAttribute("errMessage") != null){%>
                    <div class="alert alert-danger alert-dismissible">
                        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                        <h4><i class="icon fa fa-ban"></i> <%out.print((String)request.getAttribute("errMessage"));%></h4>
                    </div>
                    <%}%>
                    <div class="box box-solid">
                        <div class="box-header with-border">
                            <h3 class="box-title">Collapsible Accordion</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="row">
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
                                <div class="box-header with-border">
                                    <h3 class="box-title">About the ARBO</h3>
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">
                                    <strong><i class="fa fa-book margin-r-5"></i> Main Office</strong>
                                    <p class="text-muted">
                                        <%=arbo.getProvOfficeCodeDesc()%>
                                    </p>
                                    <hr>
                                    <strong><i class="fa fa-map-marker margin-r-5"></i> Location</strong>
                                    <p class="text-muted"><%=arbo.getFullAddress()%></p>
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
                            </div>
                            <div class="box-group" id="accordion">
                                <!-- we are adding the .panel class so bootstrap.js collapse plugin detects it -->
                                <div class="panel box box-info">
                                    <div class="box-header with-border">
                                        <h4 class="box-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                                APCP Request Information
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseTwo" class="panel-collapse collapse">
                                        <div class="box-body">
                                            <div class="col-xs-12">
                                                <div class="col-xs-3">
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
                                                </div>
                                                <div class="col-xs-3">
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
                                                </div>
                                                <div class="col-xs-3">
                                                    <div class="tab-pane" id="citymun">
                                                        <div class="box-body">
                                                            <%
                                                                //AddressDAO addressDAO = new AddressDAO();
                                                                //ARBDAO arbDAO = new ARBDAO();
                                                                //ARBODAO arboDAO = new ARBODAO();
                                                                ArrayList<ARBO> arboList = new ArrayList();
                                                                if ((Integer) session.getAttribute("userType") == 3) {
                                                                    arboList = arboDAO.getAllARBOsByProvince((Integer) session.getAttribute("provOfficeCode"));
                                                                } else if ((Integer) session.getAttribute("userType") == 4) {
                                                                    arboList = arboDAO.getAllARBOsByRegion((Integer) session.getAttribute("regOfficeCode"));
                                                                }
                                                                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(arbo.getArboID());
                                                                ArrayList<Integer> arbListCityMunCodes = arbDAO.getARBCityMun(arbList);
                                                                //ArrayList<CityMun> cityMunList = addressDAO.getAllCityMunsByID(arbListCityMunCodes);

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
                                                </div>
                                                <div class="col-xs-3">
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
                                                                                //ArrayList<Crop> cropHistory = dao3.getCropHistory(arbListARBO);
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
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="panel box box-primary">
                                    <div class="box-header with-border">
                                        <h4 class="box-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree">
                                                ARBO Information
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseThree" class="panel-collapse collapse">
                                        <div class="box-body">

                                        </div>
                                    </div>
                                </div>                                        
                                <div class="panel box box-primary">
                                    <div class="box-header with-border">
                                        <h4 class="box-title">
                                            <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">
                                                ARBO Information
                                            </a>
                                        </h4>
                                    </div>
                                    <div id="collapseOne" class="panel-collapse collapse">
                                        <div class="box-body">
                                            <div class="col-xs-12">
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

                                                    <% for (Repayment repayment : repaymentHistory) { %>

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
                                                    <canvas id="pieCanvasPastDue" style="height:250px"></canvas>
                                                </div>
                                            </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>                                        
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.box -->

                    </div>
            </div>

        </section>
        <!-- /.content -->

    </div>

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
                String json2 = line.getCropHistory(crops,arbListARBO);
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
