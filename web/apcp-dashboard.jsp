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

            <%if ((Integer) session.getAttribute("userType") == 4) {%>
            <%@include file="jspf/regional-field-officer-sidebar.jspf"%>
            <% } else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <% } else if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%}%>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <%if((Integer)session.getAttribute("userType") == 5){%>
                        <strong><i class="fa fa-dashboard"></i> Dashboard: Central</strong>
                        <%}else if((Integer)session.getAttribute("userType") == 4){%>
                        <strong><i class="fa fa-dashboard"></i> Dashboard: <%out.print((String)session.getAttribute("regOfficeDesc"));%></strong>
                        <%}else if((Integer)session.getAttribute("userType") == 3){%>
                        <strong><i class="fa fa-dashboard"></i> Dashboard: <%out.print((String)session.getAttribute("provOfficeDesc"));%></strong>
                        <%}%>
                    </h1>
                </section>

                <%
                ARBODAO arboDAO = new ARBODAO();
                AddressDAO addressDAO = new AddressDAO();
                APCPRequestDAO apcpDAO = new APCPRequestDAO();
                UserDAO uDAO = new UserDAO();
                
                int type = 1; // default type
                
                ArrayList<Region> regionList = addressDAO.getAllRegions();
                ArrayList<Province> provOfficeList = new ArrayList();
                
                ArrayList<Region> regions = new ArrayList(); // printing purposes
                ArrayList<Province> provOffices = new ArrayList(); // printing purposes
                
                if(request.getAttribute("regions") != null){
                    regions = (ArrayList<Region>)request.getAttribute("regions");
                }
                if(request.getAttribute("provOffices") != null){
                    provOffices = (ArrayList<Province>)request.getAttribute("provOffices");
                }
                
                ArrayList<ARBO> allArboList = new ArrayList();
                ArrayList<APCPRequest> allRequests = new ArrayList();
                if((Integer)session.getAttribute("userType") == 5){ // CENTRAL
                    allArboList = arboDAO.getAllARBOs();
                    allRequests = apcpDAO.getAllRequests();
                }else if((Integer)session.getAttribute("userType") == 4){ // REGIONAL
                    allArboList = arboDAO.getAllARBOsByRegion((Integer)session.getAttribute("regOfficeCode"));    
                    provOfficeList = addressDAO.getAllProvOfficesRegion((Integer)session.getAttribute("regOfficeCode"));
                    allRequests = apcpDAO.getAllRegionalRequests((Integer)session.getAttribute("regOfficeCode"));
                }else if((Integer)session.getAttribute("userType") == 3){ // PROVINCIAL
                    allArboList = arboDAO.getAllARBOsByProvince((Integer)session.getAttribute("provOfficeCode")); 
                    allRequests = apcpDAO.getAllProvincialRequests((Integer)session.getAttribute("provOfficeCode"));
                }
                
                if(request.getAttribute("type") != null){ // filtered type
                    type = (Integer)request.getAttribute("type");
                }
                
                if(request.getAttribute("filtered") != null){ // filtered ARBOs
                    allArboList = (ArrayList<ARBO>)request.getAttribute("filtered");
                }
                
                if(request.getAttribute("filteredRequests") != null){ // filtered requests
                    allRequests = (ArrayList<APCPRequest>)request.getAttribute("filteredRequests");
                }
                
                ArrayList<PastDueAccount> pdaByRequestList = apcpDAO.getAllPastDueAccountsByRequestList(allRequests);
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
                        <%if((Integer)session.getAttribute("userType") == 5){%> 
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
                        <%}else if((Integer)session.getAttribute("userType") == 4){%>
                        <div class="row">
                            <div class="col-xs-12">
                                <form id="drillDownGenderForm">
                                    <div class="row no-print">
                                        <div class="col-xs-12">
                                            <input type="radio" id="drillDownGender" name="filterBy" value="All" checked onclick="document.getElementById('regions').disabled = true;document.getElementById('provinces').disabled = true;">
                                            <label for="actName">Select All</label>
                                            &nbsp;&nbsp;
                                            <input type="radio" id="drillDownGender" name="filterBy" value="provinces" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = false;">
                                            <label for="actName">Provinces</label>
                                        </div>
                                    </div>
                                    <div class="row no-print">
                                        <div class="col-xs-4">
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
                                        <div class="col-xs-5">
                                            <div class="form-group">
                                                <label for="actName">Provinces</label>
                                                <select name="provinces[]" id="provinces" class="form-control select2" multiple="multiple" disabled>
                                                    <%for(Province p : provOfficeList){%>
                                                    <option value="<%out.print(p.getProvCode());%>"><%out.print(p.getProvDesc());%></option>
                                                    <%}%>
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
                        <%}%>
                    </section>

                    <section class="invoice">
                        <!-- title row -->
                        <div class="row">
                            <div class="col-xs-12">
                                <h2 class="page-header">
                                    <i class="fa fa-money"></i> Agrarian Production Credit Program (APCP)
                                </h2>
                            </div>
                            <!-- /.col -->
                        </div>
                        <!-- info row -->
                        <div class="row invoice-info">
                            <div class="row">
                                <div class="col-xs-9">
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
                                <div class="col-xs-3">

                                    <div class="row">
                                        <div class="description-block border-right">

                                            <h5 class="description-header"><%=currency.format(apcpDAO.getYearlySumOfReleasesByRequestId(allRequests, year))%></h5>
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
                                            <h5 class="description-header"><%=currency.format(apcpDAO.getSumOfAccumulatedReleasesByRequestId(allRequests))%></h5>
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
                                                                    ArrayList<APCPRequest> accumulatedRequests = rDAO.getAllAccumulatedARBORequests(allArboList);
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
                                            <h5 class="description-header"><%=currency.format(apcpDAO.getTotalApprovedAmount(allRequests))%></h5>
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
                                            <h5 class="description-header"><%=currency.format(apcpDAO.getTotalPastDueAmount(allRequests))%></h5>
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
                                                                            APCPRequest req = apcpDAO.getRequestByID(pda.getRequestID());
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
