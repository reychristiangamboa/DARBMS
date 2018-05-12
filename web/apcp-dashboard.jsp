<%-- 
    Document   : provincial-field-officer-home
    Created on : Mar 16, 2018, 4:45:24 PM
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
            <%@include file="jspf/central-sidebar.jspf"%>
            <%--<%if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <% } else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>--%>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-dashboard"></i> Dashboard: Central</strong>
                    </h1>
                </section>

                <%
                //ARBODAO arboDAO = new ARBODAO();
                //CropDAO cropDAO = new CropDAO();
                //AddressDAO addressDAO = new AddressDAO();
                //ArrayList<Region> regionList = addressDAO.getAllRegions();
                
                ArrayList<Region> regions = new ArrayList();
                ArrayList<Province> provOffices = new ArrayList();
                
                if(request.getAttribute("regions") != null){
                    regions = (ArrayList<Region>)request.getAttribute("regions");
                }
                if(request.getAttribute("provOffices") != null){
                    provOffices = (ArrayList<Province>)request.getAttribute("provOffices");
                }
                
                ArrayList<ARBO> allArboList = new ArrayList();
                if((Integer)session.getAttribute("userType") == 5){
                    allArboList = arboDAO.getAllARBOs();
                }else if((Integer)session.getAttribute("userType") == 4){
                    allArboList = arboDAO.getAllARBOsByRegion((Integer)session.getAttribute("regOfficeCode"));    
                }
                
                int type = 1;
                
                if(request.getAttribute("type") != null){
                    type = (Integer)request.getAttribute("type");
                }
                
                if(request.getAttribute("filtered") != null){
                    allArboList = (ArrayList<ARBO>)request.getAttribute("filtered");
                }
                
                if(request.getAttribute("filteredRequests") != null){
                    allRequests = (ArrayList<APCPRequest>)request.getAttribute("filteredRequests");
                }
                %>

                <!-- Main content -->
                <section class="content">
                    <section class="invoice no-print">
                        <div class="row">
                            <div class="col-xs-12">
                                <h2 class="page-header">
                                    <i class="fa fa-search"></i> Filter By
                                </h2>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12">
                                <form id="drillDownGenderForm">
                                    <div class="row no-print">
                                        <div class="col-xs-12">
                                            <input type="radio" id="drillDownGender" name="filterBy" value="All" checked onclick="document.getElementById('regions').disabled = true;document.getElementById('provinces').disabled = true;">
                                            <label for="actName">Select All</label>
                                            &nbsp;&nbsp;
                                            <input type="radio" id="drillDownGender" name="filterBy" value="regions" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = true;">
                                            <label for="actName">Regions</label>
                                            &nbsp;&nbsp;
                                            <input type="radio" id="drillDownGender" name="filterBy" value="provinces" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = false;">
                                            <label for="actName">Provinces</label>
                                        </div>
                                    </div>
                                    <div class="row no-print">
                                        <div class="col-xs-3">
                                            <div class="form-group">
                                                <label for="actName">Type</label>
                                                <select name="type" id="type" class="form-control select2">
                                                    <option value="1">APCP Requests</option>
                                                    <option value="2">Approval Rate</option>
                                                    <option value="3">APCP Amounts</option>
                                                    <option value="4">Loan Type Availability</option>
                                                    <option value="5">Loan Reason</option>
                                                    <option value="6">Past Due Reason</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-xs-4">
                                            <div class="form-group">
                                                <label for="actName">Regions</label>
                                                <select name="regions[]" id="regions" onchange="chg2()" class="form-control select2" multiple="multiple" disabled>
                                                    <%for(Region r : regionList){%>
                                                    <option value="<%=r.getRegCode()%>"><%out.print(r.getRegDesc());%></option>
                                                    <%}%>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-xs-5">
                                            <div class="form-group">
                                                <label for="actName">Provinces</label>
                                                <select name="provinces[]" id="provinces" class="form-control select2" multiple="multiple" disabled>

                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row no-print">
                                        <button type="submit" onclick="form.action = 'FilterAPCPDashboard'" class="btn btn-success pull-right"><i class="fa fa-filter margin-r-5"></i> Filter</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </section>

                    <section class="invoice">
                        <!-- title row -->
                        <div class="row">
                            <div class="col-xs-12">
                                <h2 class="page-header">
                                    <i class="fa fa-money"></i> Agrarian Production Credit Program (APCP) Dashboard
                                </h2>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- info row -->
                        <div class="row invoice-info">
                            <div class="row">
                                <div class="col-md-12">
                                    <!-- /.col -->
                                    <div class="col-xs-12">
                                        <div class="col-xs-9">
                                            <div class="nav-tabs-custom">
                                                <div class="tab-content">
                                                    <div class="active tab-pane" id="gender">

                                                        <div class="row">
                                                            <div class="col-xs-1"></div>
                                                            <div class="col-xs-10">
                                                                <div class="box-body">
                                                                    <canvas id="chartCanvas"></canvas>
                                                                    <div class="row text-center">
                                                                        <a class="btn btn-submit" data-toggle="modal" data-target="#modalPie">View More</a>
                                                                    </div>
                                                                    <div class="modal fade" id="modalPie">
                                                                        <div class="modal-dialog modal-lger">
                                                                            <div class="modal-content">
                                                                                <div class="modal-header">
                                                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                        <span aria-hidden="true">&times;</span></button>
                                                                                    <h4 class="modal-title">Agrarian Reform Beneficiaries</h4>
                                                                                </div>
                                                                                <div class="modal-body">
                                                                                    <div class="row">
                                                                                        <div class="col-xs-12">
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
                                                                                                        for (ARB arb : allArbsList) {
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
                                                                                                    <%
                                                                                                            }
                                                                                                        }
                                                                                                    %>

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
                                                                                                        for (ARB arb : allArbsList) {
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
                                                                </div>
                                                            </div>
                                                            <div class="col-xs-1"></div>
                                                        </div>
                                                    </div>

                                                    <!-- /.tab-pane -->
                                                    <div class="tab-pane" id="educ">
                                                        <div class="box-body" id="bar">
                                                            <form id="drillDownGenderForm">
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <input type="radio" id="drillDownGender" name="filterBy" value="All" checked onclick="document.getElementById('regions').disabled = true;document.getElementById('provinces').disabled = true;">
                                                                        <label for="actName">Select All</label>
                                                                        &nbsp;&nbsp;
                                                                        <input type="radio" id="drillDownGender" name="filterBy" value="regions" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = true;">
                                                                        <label for="actName">Regions</label>
                                                                        &nbsp;&nbsp;
                                                                        <input type="radio" id="drillDownGender" name="filterBy" value="provinces" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = false;">
                                                                        <label for="actName">Provinces</label>
                                                                    </div>
                                                                </div>


                                                                <div class="row">
                                                                    <div class="col-xs-4">
                                                                        <div class="form-group">
                                                                            <label for="actName">Regions</label>
                                                                            <select name="regions[]" id="regions" onchange="chg2()" class="form-control select2" multiple="multiple" disabled>
                                                                                <%for(Region r : regionList){%>
                                                                                <option value="<%=r.getRegCode()%>"><%out.print(r.getRegDesc());%></option>
                                                                                <%}%>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-xs-4">
                                                                        <div class="form-group">
                                                                            <label for="actName">Provincial Offices</label>
                                                                            <select name="provinces[]" id="provinces" class="form-control select2" multiple="multiple" disabled>

                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-xs-4">
                                                                        <label for="">&nbsp;</label>
                                                                        <button type="submit" onclick="form.action = 'DashboardFilterGender'" class="btn btn-success"><i class="fa fa-filter margin-r-5"></i> Filter</button>
                                                                    </div>
                                                                </div>
                                                            </form>
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
                                                        <div class="modal-dialog modal-lger">
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
                                                                                for (ARB arb : allArbsList) {

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
                                                    <div class="tab-pane" id="perProv">
                                                        <div class="box-body">
                                                            <%
                                                                for (Region region : perRegionList) {
                                                                    ArrayList<ARBO> arboListRegion = arboDAO.getAllARBOsByRegion(region.getRegCode());
                                                            %>
                                                            <div class="active tab-pane" >
                                                                <div class="col-lg-2 col-xs-6" data-toggle="modal" data-target="#modal-default<%out.print(region.getRegCode());%>">
                                                                    <!-- small box -->
                                                                    <div class="small-box bg-green">
                                                                        <div class="inner">
                                                                            <h3><%out.print(arboListRegion.size());%></h3>
                                                                            <p><%=region.getRegDesc()%></p>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="modal fade" id="modal-default<%out.print(region.getRegCode());%>">
                                                                    <div class="modal-dialog modal-lger">
                                                                        <div class="modal-content">
                                                                            <div class="modal-header">
                                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                    <span aria-hidden="true">&times;</span></button>
                                                                                <h4 class="modal-title"><%out.print(region.getRegDesc());%></h4>
                                                                            </div>
                                                                            <div class="modal-body">

                                                                                <table class="table table-bordered table-striped modTable">
                                                                                    <thead>
                                                                                        <tr>
                                                                                            <th>ARBO Name</th>
                                                                                            <th>Address</th>
                                                                                            <th>No. of Members</th>

                                                                                        </tr>
                                                                                    </thead>
                                                                                    <tbody>
                                                                                        <%
                                                                                    for (ARBO arbo : arboListRegion) { %>
                                                                                        <tr>
                                                                                            <td><a href="ViewARBO?id=<%out.print(arbo.getArboID());%>" class="btn btn-link"><%out.print(arbo.getArboName());%></a></td>
                                                                                            <td><%out.print(arbo.getFullAddress());%></td>
                                                                                            <td><%out.print(arbo.getArbList().size());%></td>
                                                                                        </tr>
                                                                                        <%}%>
                                                                                    </tbody>    
                                                                                    <tfoot>
                                                                                        <tr>
                                                                                            <th>ARBO Name</th>
                                                                                            <th>Address</th>
                                                                                            <th>No. of Members</th>
                                                                                        </tr>
                                                                                    </tfoot>
                                                                                </table>

                                                                            </div>
                                                                            <form method="post">
                                                                                <div class="modal-footer">
                                                                                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                                                    <div class="pull-right">
                                                                                        <button class="btn btn-primary" type="submit" onclick="form.action = 'ViewRegionalDashboard?regOfficeCode=<%out.print(region.getRegCode());%>'">View Dashboard</button>
                                                                                    </div>
                                                                                </div>
                                                                            </form>
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
                                                                    <h4 class="modal-title"></h4>
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
                                                                                for (Crop c : cropHistory) {
                                                                                    ARB arb = arbDAO.getARBByID(c.getArbID());
                                                                            %>
                                                                            <tr>
                                                                                <td><%=arb.getFullName()%></td>
                                                                                <td><%=arboDAO.getARBOByID(arb.getArboID()).getArboName()%></td>
                                                                                <td><%=c.getCropTypeDesc()%></td>                                                                        
                                                                                <td><%=c.getStartDate()%></td>                                                                        
                                                                                <td><%=c.getEndDate()%></td>
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
                                        </div>
                                        <div class="col-xs-3">
                                            <div class="row">
                                                <div class="row">
                                                    <div class="description-block border-right">

                                                        <h5 class="description-header"><%=currency.format(apcpRequestDAO.getYearlySumOfReleasesByRequestId(allRequests, year))%></h5>
                                                        <span class="description-text">TOTAL YEARLY RELEASED AMOUNT</span>
                                                        <div class="row text-center">
                                                            <a class="btn btn-submit" data-toggle="modal" data-target="#totalYear">View More</a>
                                                        </div>
                                                    </div>
                                                    <!-- /.description-block -->
                                                </div>
                                                <div class="modal fade" id="totalYear">
                                                    <div class="modal-dialog modal-lger">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span></button>
                                                                <h4 class="modal-title">NATIONAL RELEASES</h4>
                                                            </div>


                                                            <div class="modal-body">
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <table class="table table-bordered table-striped export">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>Loan Application No.</th>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>Province</th>
                                                                                    <th>Release Amount</th>
                                                                                    <th>Release Date</th>
                                                                                    <th>Released By</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                    for(APCPRequest req : allRequests){
                                                                                        for(APCPRelease rel : req.getReleases()){
                                                                                            ARBO arbo = arboDAO.getARBOByID(req.getArboID());
                                                                                            User u = uDAO.searchUser(rel.getReleasedBy());
                                                                                %>
                                                                                <tr>
                                                                                    <td><%=req.getLoanTrackingNo()%></td>
                                                                                    <td><%=arbo.getArboName()%></td>
                                                                                    <td><%=arbo.getArboProvinceDesc()%></td>
                                                                                    <td><%=currency.format(rel.getReleaseAmount())%></td>
                                                                                    <td><%=rel.getReleaseDate()%></td>
                                                                                    <td><%=u.getFullName()%></td>
                                                                                </tr>
                                                                                <%
                                                                                        }                                                                            
                                                                                    }
                                                                                %>
                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>

                                                                                    <th>Loan Application No.</th>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>Province</th>
                                                                                    <th>Release Amount</th>
                                                                                    <th>Release Date</th>
                                                                                    <th>Released By</th>
                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>
                                                                    </div>
                                                                </div>

                                                            </div>
                                                            <form method="post">
                                                                <div class="modal-footer">
                                                                    <div class="pull-left">
                                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                    </div>
                                                                    <div class="pull-right">

                                                                        <button type="button" class="btn btn-default" id="dr-totalYearReleaseReport">
                                                                            <span>
                                                                                <i class="fa fa-calendar"></i> Date range picker
                                                                            </span>
                                                                            <i class="fa fa-caret-down"></i>
                                                                        </button>

                                                                        <input type="hidden" name="reportType" value="1">
                                                                        <input type="hidden" id="start-totalYearReleaseReport" name="start">
                                                                        <input type="hidden" id="end-totalYearReleaseReport" name="end" >

                                                                        <button type="submit" class="btn btn-default" onclick="form.action = 'ViewReport'">Generate Report</button>
                                                                    </div>
                                                                </div>
                                                            </form>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>
                                                <!-- /.col -->
                                                <div class="row">
                                                    <div class="description-block border-right">
                                                        <h5 class="description-header"><%=currency.format(apcpRequestDAO.getSumOfAccumulatedReleasesByRequestId(allRequests))%></h5>
                                                        <span class="description-text">TOTAL ACCUMULATED RELEASED AMOUNT</span>
                                                        <div class="row text-center">
                                                            <a class="btn btn-submit" data-toggle="modal" data-target="#totalAccumulated">View More</a>
                                                        </div>
                                                    </div>
                                                    <!-- /.description-block -->
                                                </div>
                                                <div class="modal fade" id="totalAccumulated">
                                                    <div class="modal-dialog modal-lger">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span></button>
                                                                <h4 class="modal-title">NATIONAL RELEASES</h4>
                                                            </div>
                                                            <div class="modal-body">
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <form method="post">
                                                                            <div class="pull-left">
                                                                                <button type="button" class="btn btn-primary" id="dr-totalAccumulatedReleaseReport">
                                                                                    <span>
                                                                                        <i class="fa fa-calendar"></i> Date range picker
                                                                                    </span>
                                                                                    <i class="fa fa-caret-down"></i>
                                                                                </button>

                                                                                <input type="hidden" name="reportType" value="2">
                                                                                <input type="hidden" id="start-totalAccumulatedReleaseReport" name="start">
                                                                                <input type="hidden" id="end-totalAccumulatedReleaseReport" name="end" >

                                                                                <button type="submit" class="btn btn-primary" onclick="form.action = 'ViewReport'">Generate Report</button>
                                                                            </div>
                                                                        </form>
                                                                    </div>
                                                                </div>
                                                                <br>
                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <table class="table table-bordered table-striped export">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>Region</th>
                                                                                    <th>Province</th>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>No. of ARBs</th>
                                                                                    <th>Total Approved Amount</th>
                                                                                    <th>Accumulated Releases</th>
                                                                                    <th><%=year%> Releases</th>
                                                                                    <th>Date of Last Release</th>
                                                                                    <th>O/S Balance</th>
                                                                                    <th>Past Due Amount</th>
                                                                                    <th>Past Due Reason</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                ReportsDAO rDAO = new ReportsDAO();
                                                                                ArrayList<APCPRequest> accumulatedRequests = rDAO.getAllAccumulatedARBORequests(arboList);
                                                                                    for(APCPRequest req : accumulatedRequests){
                                                                                        ARBO arbo = arboDAO.getARBOByID(req.getArboID());
                                                                                %>
                                                                                <tr>
                                                                                    <td><%=arbo.getArboRegionDesc()%></td>
                                                                                    <td><%=arbo.getArboProvinceDesc()%></td>
                                                                                    <td><%=arbo.getArboName()%></td>
                                                                                    <td><%=arbo.getArbList().size()%></td>
                                                                                    <td><%=currency.format(req.getLoanAmount())%></td>
                                                                                    <td><%=currency.format(req.getTotalReleasedAmount())%></td>
                                                                                    <td><%=currency.format(req.getYearlyReleasedAmount())%></td>
                                                                                    <td><%if(req.getDateLastRelease()!= null){ out.print(req.getDateLastRelease());}%></td>
                                                                                    <td><%=currency.format(req.getTotalOSBalance())%></td>
                                                                                    <td><%=currency.format(req.getTotalPastDueAmount())%></td>
                                                                                    <td><%=req.printAllPastDueReasons()%></td>
                                                                                </tr>
                                                                                <%}%>
                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>Region</th>
                                                                                    <th>Province</th>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>No. of ARBs</th>
                                                                                    <th>Total Approved Amount</th>
                                                                                    <th>Accumulated Releases</th>
                                                                                    <th><%=year%> Releases</th>
                                                                                    <th>Date of Last Release</th>
                                                                                    <th>O/S Balance</th>
                                                                                    <th>Past Due Amount</th>
                                                                                    <th>Past Due Reason</th>
                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>
                                                                    </div>
                                                                </div>


                                                            </div>
                                                            <form method="post">
                                                                <div class="modal-footer">
                                                                    <div class="pull-left">
                                                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                                    </div>

                                                                </div>
                                                            </form>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>
                                                <!-- /.col -->
                                                <div class="row">
                                                    <div class="description-block border-right">
                                                        <h5 class="description-header"><%=currency.format(apcpRequestDAO.getTotalApprovedAmount(allRequests))%></h5>
                                                        <span class="description-text">TOTAL APPROVED AMOUNT</span>
                                                        <div class="row text-center">
                                                            <a class="btn btn-submit" data-toggle="modal" data-target="#totalApprovedAmount">View More</a>
                                                        </div>
                                                    </div>
                                                    <!-- /.description-block -->
                                                </div>
                                                <div class="modal fade" id="totalApprovedAmount">
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
                                                                                    <th>Loan Application No.</th>
                                                                                    <th>ARBO</th>
                                                                                    <th>Approved Amount</th>
                                                                                    <th>Approved Date</th>
                                                                                    <th>Approved By</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                    for (APCPRequest r : allRequests) {
                                                                                        ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                                                                    
                                                                                %>



                                                                                <%if (r.getRequestStatus() == 4) {%>
                                                                                <tr>
                                                                                    <td><%out.print(r.getLoanTrackingNo());%></td>
                                                                                    <td><%out.print(arbo.getArboName());%></td>
                                                                                    <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                                                    <td><%out.print(r.getDateApproved());%></td>
                                                                                    <td><%out.print(uDAO.searchUser(r.getApprovedBy()).getFullName());%></td>
                                                                                </tr>
                                                                                <%} else if (r.getRequestStatus() == 5) {%>
                                                                                <tr>
                                                                                    <td><%out.print(r.getLoanTrackingNo());%></td>
                                                                                    <td><%out.print(arbo.getArboName());%></td>
                                                                                    <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                                                    <td><%out.print(r.getDateApproved());%></td>
                                                                                    <td><%out.print(uDAO.searchUser(r.getApprovedBy()).getFullName());%></td>
                                                                                </tr>
                                                                                <%} else if (r.getRequestStatus() == 7) {%>
                                                                                <tr>
                                                                                    <td><%out.print(r.getLoanTrackingNo());%></td>
                                                                                    <td><%out.print(arbo.getArboName());%></td>
                                                                                    <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                                                    <td><%out.print(r.getDateApproved());%></td>
                                                                                    <td><%out.print(uDAO.searchUser(r.getApprovedBy()).getFullName());%></td>
                                                                                </tr>
                                                                                <%}%>


                                                                                <%}%>

                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>Loan Application No.</th>
                                                                                    <th>ARBO</th>
                                                                                    <th>Approved Amount</th>
                                                                                    <th>Approved Date</th>
                                                                                    <th>Approved By</th>
                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>
                                                                    </div>
                                                                </div>


                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                                <button type="button" class="btn btn-default pull-right" data-dismiss="modal">Generate Report</button>
                                                            </div>
                                                        </div>
                                                        <!-- /.modal-content -->
                                                    </div>
                                                    <!-- /.modal-dialog -->
                                                </div>
                                                <!-- /.col -->
                                                <div class="row">
                                                    <div class="description-block">
                                                        <h5 class="description-header"><%=currency.format(apcpRequestDAO.getTotalPastDueAmount(allRequests))%></h5>
                                                        <span class="description-text">TOTAL PAST DUE AMOUNT</span>
                                                        <div class="row text-center">
                                                            <a class="btn btn-submit" data-toggle="modal" data-target="#totalPastDue">View More</a>
                                                        </div>
                                                    </div>
                                                    <!-- /.description-block -->
                                                </div>
                                                <div class="modal fade" id="totalPastDue">
                                                    <div class="modal-dialog modal-lger">
                                                        <div class="modal-content">
                                                            <div class="modal-header">
                                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                    <span aria-hidden="true">&times;</span></button>
                                                                <h4 class="modal-title">Past Due Accounts</h4>
                                                            </div>
                                                            <div class="modal-body">

                                                                <div class="row">
                                                                    <div class="col-xs-12">
                                                                        <table class="table table-bordered table-striped export">
                                                                            <thead>
                                                                                <tr>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>Loan Tracking No.</th>
                                                                                    <th>Past Due Amount</th>
                                                                                    <th>Reason for Past Due</th>
                                                                                    <th>Other Reason</th>
                                                                                    <th>Date Recorded</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                    for(PastDueAccount pda : pdaByRequestList){
                                                                                        APCPRequest req = apcpRequestDAO.getRequestByID(pda.getRequestID());
                                                                                        ARBO arbo = arboDAO.getARBOByID(req.getArboID());
                                                                                %>
                                                                                <%if(pda.getActive() == 1){%>
                                                                                <tr>
                                                                                    <td><%=arbo.getArboName()%></td>
                                                                                    <td><%=req.getLoanTrackingNo()%></td>
                                                                                    <td><%=currency.format(pda.getPastDueAmount())%></td>
                                                                                    <td><%=pda.getReasonPastDueDesc()%></td>
                                                                                    <td><%=pda.getOtherReason()%></td>
                                                                                    <td><%=pda.getDateRecorded()%></td>
                                                                                </tr>
                                                                                <%}%>
                                                                                <%}%>
                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>Loan Tracking No.</th>
                                                                                    <th>Past Due Amount</th>
                                                                                    <th>Reason for Past Due</th>
                                                                                    <th>Other Reason</th>
                                                                                    <th>Date Recorded</th>
                                                                                </tr>
                                                                            </tfoot>
                                                                        </table>
                                                                    </div>
                                                                </div>

                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Close</button>
                                                                <button type="button" class="btn btn-default pull-right" data-dismiss="modal">Generate Report</button>
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
                            <hr>
                            <div class="row">
                                <div class="col-xs-12">
                                    <table class="table table-bordered table-striped export">
                                        <thead>
                                            <tr>
                                                <th>Region</th>
                                                <th>Province</th>
                                                <th>ARBO Name (# ARBs)</th>
                                                <th>Total Approved Amount</th>
                                                <th>Accumulated Releases</th>
                                                <th><%=year%> Releases</th>
                                                <th>Date of Last Release</th>
                                                <th>O/S Balance</th>
                                                <th>Past Due Amount</th>
                                                <th>Past Due Reason</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for(APCPRequest req : accumulatedRequests){
                                                    ARBO arbo = arboDAO.getARBOByID(req.getArboID());
                                            %>
                                            <tr>
                                                <td><%=arbo.getArboRegionDesc()%></td>
                                                <td><%=arbo.getArboProvinceDesc()%></td>
                                                <td><%=arbo.getArboName()%> (<%=arbo.getArbList().size()%>)</td>
                                                <td><%=currency.format(req.getLoanAmount())%></td>
                                                <td><%=currency.format(req.getTotalReleasedAmount())%></td>
                                                <td><%=currency.format(req.getYearlyReleasedAmount())%></td>
                                                <td><%if(req.getDateLastRelease()!= null){ out.print(req.getDateLastRelease());}%></td>
                                                <td><%=currency.format(req.getTotalOSBalance())%></td>
                                                <td><%=currency.format(req.getTotalPastDueAmount())%></td>
                                                <td><%=req.printAllPastDueReasons()%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <!-- /.row -->
                        <!-- this row will not appear when printing -->
                        <div class="row no-print">
                            <div class="col-xs-12">
                                <a href="#" class="btn btn-default pull-right"><i class="fa fa-print"></i> Print</a>
                            </div>
                        </div>
                    </section>



                </section>

            </div>
            <!-- /.row -->


            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>
        <script type="text/javascript">

            function chg2() {
                var values = $('#regions').val();

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

            $(function () {
                $('#dr-totalYearReleaseReport').daterangepicker(
                        {
                            minDate: moment().startOf('year'),
                            maxDate: moment().endOf('year'),
                            ranges: {
                                'This Month': [moment().startOf('month'), moment().endOf('month')],
                                'This Quarter': [moment().startOf('quarter'), moment().endOf('quarter')],
                                'This Year': [moment().startOf('year'), moment().endOf('year')]
                            }

                        },
                        function (start, end) {
                            $('#dr-totalYearReleaseReport span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                            $('#start-totalYearReleaseReport').val(start.format('YYYY-MM-DD'));
                            $('#end-totalYearReleaseReport').val(end.format('YYYY-MM-DD'));
                        }
                );

                $('#dr-totalAccumulatedReleaseReport').daterangepicker(
                        {
                            minDate: moment().startOf('year'),
                            maxDate: moment().endOf('year'),
                            ranges: {
                                'This Month': [moment().startOf('month'), moment().endOf('month')],
                                'This Quarter': [moment().startOf('quarter'), moment().endOf('quarter')],
                                'This Year': [moment().startOf('year'), moment().endOf('year')]
                            }

                        },
                        function (start, end) {
                            $('#dr-totalAccumulatedReleaseReport span').html(start.format('MMMM D, YYYY') + ' - ' + end.format('MMMM D, YYYY'));
                            $('#start-totalAccumulatedReleaseReport').val(start.format('YYYY-MM-DD'));
                            $('#end-totalAccumulatedReleaseReport').val(end.format('YYYY-MM-DD'));
                        }
                );


            });

            $(function () {
                var ctx = $('#chartCanvas').get(0).getContext('2d');
            <%
                Chart chart = new Chart();
                String json = "";
                
                if(type == 1){ // REQUESTS
                    json = chart.getStackedBarChartAPCPRequests(regions,provOffices,allArboList);
                }else if(type == 2){ // APPROVAL RATE
                    json = chart.getStackedBarChartApprovalRate(regions,provOffices,allArboList);
                }else if(type == 3){ // APCP AMOUNTS
                    json = chart.getBarChartAPCPAmounts(regions,provOffices,allArboList);
                }else if(type == 4){ // LOAN TYPE AVAILMENT
                    json = chart.getBarChartLoanType(regions,provOffices,allArboList);
                }else if(type == 5){ // LOAN REASON
                    json = chart.getStackedBarChartLoanReason(regions,provOffices,allArboList);
                }
                
            %>
                new Chart(ctx, <%out.print(json);%>);
            });
        </script>
    </body>
</html>
