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
    
                ArrayList<Province> perProvinceList = addressDAO.getAllProvinces((Integer) session.getAttribute("regOfficeCode"));
                ArrayList<CityMun> cityMunList = new ArrayList();

                
                ArrayList<APCPRequest> requestedRequests = new ArrayList();
                ArrayList<APCPRequest> clearedRequests = new ArrayList();
                ArrayList<APCPRequest> endorsedRequests = new ArrayList();
                ArrayList<APCPRequest> approvedRequests = new ArrayList();
                ArrayList<APCPRequest> releasedRequests = new ArrayList();
                ArrayList<APCPRequest> forReleaseRequests = new ArrayList();
    
                if(userType == 3){
                    ArrayList<ARBO> arboListProvince = arboDAO.getAllARBOsByProvince((Integer) session.getAttribute("provOfficeCode"));
                    ArrayList<ARB> arbListProvince = arbDAO.getAllARBsOfARBOs(arboListProvince);
                    cityMunList = addressDAO.getAllCityMuns(arboListProvince.get(0).getArboProvince());
                    requestedRequests = apcpRequestDAO.getAllProvincialRequestsByStatus(1, (Integer) session.getAttribute("provOfficeCode"));
                    clearedRequests = apcpRequestDAO.getAllProvincialRequestsByStatus(2, (Integer) session.getAttribute("provOfficeCode"));
                    endorsedRequests = apcpRequestDAO.getAllProvincialRequestsByStatus(3, (Integer) session.getAttribute("provOfficeCode"));
                    approvedRequests = apcpRequestDAO.getAllProvincialRequestsByStatus(4, (Integer) session.getAttribute("provOfficeCode"));
                    releasedRequests = apcpRequestDAO.getAllProvincialRequestsByStatus(5, (Integer) session.getAttribute("provOfficeCode"));
                    forReleaseRequests = apcpRequestDAO.getAllProvincialRequestsByStatus(7, (Integer) session.getAttribute("provOfficeCode"));
                } else if(userType == 4){
                    ArrayList<ARBO> arboListRegion = arboDAO.getAllARBOsByRegion((Integer) session.getAttribute("regOfficeCode"));
                    ArrayList<ARB> arbListRegion = arbDAO.getAllARBsOfARBOs(arboListRegion);
                    requestedRequests = apcpRequestDAO.getAllRegionalRequestsByStatus(1, (Integer) session.getAttribute("regOfficeCode"));
                    clearedRequests = apcpRequestDAO.getAllRegionalRequestsByStatus(2, (Integer) session.getAttribute("regOfficeCode"));
                    endorsedRequests = apcpRequestDAO.getAllRegionalRequestsByStatus(3, (Integer) session.getAttribute("regOfficeCode"));
                    approvedRequests = apcpRequestDAO.getAllRegionalRequestsByStatus(4, (Integer) session.getAttribute("regOfficeCode"));
                    releasedRequests = apcpRequestDAO.getAllRegionalRequestsByStatus(5, (Integer) session.getAttribute("regOfficeCode"));
                    forReleaseRequests = apcpRequestDAO.getAllRegionalRequestsByStatus(7, (Integer) session.getAttribute("regOfficeCode"));
                } else if (userType == 5){
                    releasedRequests = apcpRequestDAO.getAllRequestsByStatus(5);
                    forReleaseRequests = apcpRequestDAO.getAllRequestsByStatus(7);
                    approvedRequests = apcpRequestDAO.getAllRequestsByStatus(4);
                    endorsedRequests = apcpRequestDAO.getAllRequestsByStatus(3);
                    clearedRequests = apcpRequestDAO.getAllRequestsByStatus(2);
                    requestedRequests = apcpRequestDAO.getAllRequestsByStatus(1);
                }
   
    

            %>

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
                    <%if(userType == 3){%>
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Filter</h3></div>
                                <form method="post" role="form">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-2">
                                                <div class="form-group">
                                                    <label for="actName">Select All</label>
                                                    <input type="checkbox" id="filterBy" name="selectAll" value="Yes">
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="actName">Cities / Municipalities</label>
                                                    <select name="cities[]" id="cities" class="form-control select2" multiple="multiple">
                                                        <%for(CityMun city : cityMunList){%>
                                                        <option value="<%=city.getCityMunCode()%>"><%out.print(city.getCityMunDesc());%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="box-footer">
                                        <button type="submit" onclick="form.action = 'FilterLoanRequests'" class="btn btn-success pull-right">Filter</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                    <%}else if(userType == 4){%>
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="box">
                                <div class="box-header"><h3 class="box-title">Filter</h3></div>
                                <form method="post" role="form">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-2">
                                                <div class="form-group">
                                                    <label for="actName">Select All</label>
                                                    <input type="checkbox" id="filterBy" name="selectAll" value="Yes">
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <div class="form-group">
                                                    <label for="actName">By Province</label>
                                                    <input type="checkbox" id="filterBy2" name="byProvince" value="Yes">
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="actName">Provinces</label>
                                                    <select name="provinces[]" id="provinces" onchange="chg()" class="form-control select2" multiple="multiple">
                                                        <%for(Province p : perProvinceList){%>
                                                        <option value="<%=p.getProvCode()%>"><%out.print(p.getProvDesc());%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="actName">Cities / Municipalities</label>
                                                    <select name="cities[]" id="cities" class="form-control select2" multiple="multiple" disabled>

                                                    </select>
                                                </div>
                                            </div>
                                        </div>


                                    </div>
                                    <div class="box-footer">
                                        <button type="submit" onclick="form.action = 'FilterLoanRequests'" class="btn btn-success pull-right">Filter</button>
                                    </div>
                                </form>

                            </div>
                        </div>
                    </div>
                    <%}%>
                    
                    
                    <div class="row">
                        <div class="col-lg-4 col-xs-6" >
                            <!-- small box -->
                            <a href="#" name="btn1">
                                <div class="small-box bg-aqua">
                                    <div class="inner">
                                        <h3><%=requestedRequests.size()%></h3>

                                        <h4>Requested</h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-keyboard-o"></i>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <div class="col-lg-4 col-xs-6">
                            <!-- small box -->
                            <a href="#" name="btn2">
                                <div class="small-box bg-green">
                                    <div class="inner">
                                        <h3><%=clearedRequests.size()%><sup style="font-size: 20px"></sup></h3>

                                        <h4>Cleared</h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-check-square-o"></i>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <div class="col-lg-4 col-xs-6">
                            <!-- small box -->
                            <a href="#" name="btn3">
                                <div class="small-box bg-yellow">
                                    <div class="inner">
                                        <h3><%=endorsedRequests.size()%></h3>

                                        <h4>Endorsed</h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-upload"></i>
                                    </div>  
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <!-- ./col -->
                    </div>
                    <div class="row">
                        <div class="col-lg-4 col-xs-6">
                            <!-- small box -->
                            <a href="#" name="btn4">
                                <div class="small-box bg-aqua">
                                    <div class="inner">
                                        <h3><%=approvedRequests.size()%></h3>

                                        <h4>Approved</h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-thumbs-o-up"></i>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                        <div class="col-lg-4 col-xs-6">
                            <!-- small box -->
                            <a href="#" name="btn5">
                                <div class="small-box bg-green">
                                    <div class="inner">
                                        <h3><%=forReleaseRequests.size()%><sup style="font-size: 20px"></sup></h3>

                                        <h4>For Release</h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-folder-o"></i>
                                    </div>
                                </div>
                            </a>        
                        </div>
                        <!-- ./col -->
                        <div class="col-lg-4 col-xs-6">
                            <!-- small box -->
                            <a href="#" name="btn6">
                                <div class="small-box bg-yellow">
                                    <div class="inner">
                                        <h3><%=releasedRequests.size()%></h3>

                                        <h4>Released</h4>
                                    </div>
                                    <div class="icon">
                                        <i class="fa fa-folder-open-o"></i>
                                    </div>
                                </div>
                            </a>
                        </div>
                        <!-- ./col -->
                    </div>
                    <!-- ./col -->

                    <div class="row"  id="1" style="display:none;">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Requested</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="requested" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Requested</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%
                                                for(APCPRequest r : requestedRequests){
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>

                                                <td><a href="CheckRequirements?id=<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(r.getLoanReason());%></td>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(f.format(r.getDateRequested()));%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><%out.print(r.getRequestStatusDesc());%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>

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
                                    <h3 class="box-title"><strong>Cleared</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="cleared" class="table table-bordered table-striped">
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
                                            <%
                                                for(APCPRequest r : clearedRequests){
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a data-toggle="modal" data-target="#cleared-modal<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(r.getLoanReason());%></td>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(f.format(r.getDateCleared()));%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><%out.print(r.getRequestStatusDesc());%></td>
                                            </tr>

                                        <div class="modal fade" id="cleared-modal<%out.print(r.getRequestID());%>">
                                            <div class="modal-dialog modal-sm">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span></button>
                                                        <h4 class="modal-title">Endorse APCP Request</h4>
                                                    </div>

                                                    <form method="post">
                                                        <div class="modal-body" id="modalBody">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <div class="form-group">
                                                                        <label for="">Loan Tracking Number</label>
                                                                        <input type="number" max="9" name="ltn" class="form-control">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <input type="hidden" name="requestID" value="<%out.print(r.getRequestID());%>">
                                                            <button type="submit" name="manual" onclick="form.action = 'EndorseAPCPRequest'" class="btn btn-primary pull-right">Submit</button>
                                                        </div>
                                                    </form>

                                                </div>
                                                <!--                                            /.modal-content -->
                                            </div>
                                            <!--                                        /.modal-dialog -->
                                        </div>

                                        <%}%>
                                        </tbody>

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
                                    <h3 class="box-title"><strong>Endorsed</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="endorsed" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Endorsed</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(APCPRequest r : endorsedRequests){
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a data-toggle="modal" data-target="#endorsed-modal<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(r.getLoanReason());%></td>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(f.format(r.getDateEndorsed()));%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><%out.print(r.getRequestStatusDesc());%></td>
                                            </tr>
                                        <div class="modal fade" id="endorsed-modal<%out.print(r.getRequestID());%>">
                                            <div class="modal-dialog modal-sm">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                            <span aria-hidden="true">&times;</span></button>
                                                        <h4 class="modal-title">Approve APCP Request</h4>
                                                    </div>

                                                    <form method="post">
                                                        <div class="modal-body" id="modalBody">
                                                            <div class="row">
                                                                <div class="col-xs-12">
                                                                    <div class="form-group">
                                                                        <label for="">Approval Date</label>
                                                                        <div class="input-group date">
                                                                            <div class="input-group-addon"><i class="fa fa-calendar"></i></div>
                                                                            <input type="date" class="form-control" name="approveDate" />        
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <input type="hidden" name="requestID" value="<%out.print(r.getRequestID());%>">
                                                            <button type="submit" name="manual" onclick="form.action = 'ApproveAPCPRequest'" class="btn btn-primary pull-right">Submit</button>
                                                        </div>
                                                    </form>

                                                </div>
                                                <!--                                            /.modal-content -->
                                            </div>
                                            <!--                                        /.modal-dialog -->
                                        </div>
                                        <%}%>

                                        </tbody>

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
                                    <h3 class="box-title"><strong>Approved</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>                                                                                   
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="approved" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Approved</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>

                                            <%
                                                for(APCPRequest r : approvedRequests){
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>
                                            <tr>
                                                <td><a href="SchedulePreRelease?id=<%out.print(r.getRequestID());%>"><%out.print(arbo.getArboName());%></a></td>
                                                <td><%out.print(r.getLoanReason());%></td>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(f.format(r.getDateApproved()));%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><%out.print(r.getRequestStatusDesc());%></td>
                                            </tr>
                                            <%}%>

                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>

                    <div class="row" id="5" style="display:none;">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>For Release</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="forRelease" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date Approved</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%
                                                for(APCPRequest r : forReleaseRequests){
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>

                                            <tr>
                                                <td><a href="#"><%out.print(arbo.getArboName());%> </a></td>
                                                <td><%out.print(r.getLoanReason());%></td>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(f.format(r.getDateApproved()));%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><%out.print(r.getRequestStatusDesc());%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>

                                    </table>
                                </div>
                                <!-- /.box-body -->
                            </div>
                            <!-- /.box -->
                        </div>
                        <!-- /.col -->
                    </div>                    

                    <div class="row" id="6" style="display:none;">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Released</strong></h3>
                                    <div class="btn-group pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                    </div>                         
                                </div>
                                <!-- /.box-header -->
                                <div class="box-body">             
                                    <table id="released" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>ARBO Name</th>
                                                <th>Loan Reason</th>
                                                <th>Loan Amount</th>
                                                <th>Land Area</th>
                                                <th>Date of Last Released</th>
                                                <th>Remarks</th>
                                                <th>Status</th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <%
                                                for(APCPRequest r : releasedRequests){
                                                    ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                            %>

                                            <tr>
                                                <td><a href="#"><%out.print(arbo.getArboName());%> </a></td>
                                                <td><%out.print(r.getLoanReason());%></td>
                                                <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                <td><%out.print(r.getHectares() + " hectares");%></td>
                                                <td><%out.print(f.format(r.getReleases().get(r.getReleases().size()-1).getReleaseDate()));%></td>
                                                <td><%out.print(r.getRemarks());%></td>
                                                <td><%out.print(r.getRequestStatusDesc());%></td>
                                            </tr>
                                            <%}%>
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
            <!-- /.content-wrapper -->

        </div>
        <%@include file="jspf/footer.jspf" %>
        <script>
            
            function chg() {
                var values = $('#provinces').val();

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
                        document.getElementById('cities').innerHTML = xhttp.responseText;
                    }
                };

                var url = "RegionalCityFilterRefresh?";
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

                var update_pizza = function () {
                    if ($("#filterBy").is(":checked")) {

                        $('#cities').prop('disabled', 'disabled');
                        $('#provinces').prop('disabled', 'disabled');

                    } else {
                        $('#cities').prop('disabled', false);
                        $('#provinces').prop('disabled', false);
                        $('#cities').val() = '';
                    }
                };

                $(update_pizza);
                $("#filterBy").change(update_pizza);

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
