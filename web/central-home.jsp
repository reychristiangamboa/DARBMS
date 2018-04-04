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

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Dashboard: Central
                    </h1>
                </section>

                <!-- Main content -->
                <section class="content">

                    <div class="row">

                        <div class="col-md-12">
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
                                            <li><a href="#perProv" data-toggle="tab">ARBO Per Region</a></li>
                                            <li><a href="#crop" data-toggle="tab">Crops History</a></li>
                                        </ul>

                                        <div class="tab-content">
                                            <div class="active tab-pane" id="gender">
                                                <div class="row">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-6">
                                                        <div class="box-body">
                                                            <canvas id="pieCanvas"></canvas>
                                                            <div class="row text-center">
                                                                <a class="btn btn-submit" data-toggle="modal" data-target="#modalPie">View More</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-3"></div>
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
                                                            <div class="col-xs-12">
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
                                                            <div class="small-box bg-yellow">
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
                                                                                    <td><%out.print(arboDAO.getARBCount(arbo.getArboID()));%></td>
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
                                    </div>
                                </div>
                                <div class="box-body">
                                    <div class="nav-tabs-custom">
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#release" data-toggle="tab">Release Line</a></li>
                                            <li><a href="#pastDue" data-toggle="tab">Past-Due Requests</a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="active tab-pane" id="release">
                                                <table  class="table table-bordered table-striped export">
                                                    <thead>
                                                        <tr>
                                                            <th>Loan <br>Tracking No.</th>
                                                            <th>Name of ARBO</th>
                                                            <th>Last Release Date</th>
                                                            <th>Progress</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            for (APCPRequest r : releasedRequests) {
                                                                ARBO arbo = arboDAO.getARBOByID(r.getArboID());

                                                        %>
                                                        <tr>
                                                            <td><a href="MonitorRelease?id=<%out.print(r.getRequestID());%>"><%=r.getLoanTrackingNo()%></a></td>
                                                            <td><%=arbo.getArboName()%></td>
                                                            <td><%=r.getReleases().get(r.getReleases().size() - 1).getReleaseDate()%></td>
                                                            <td  width=50%>
                                                                <div class="progress">
                                                                    <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: <%out.print(r.getProgressBarWidth(apcpRequestDAO.getSumOfReleasesByRequestId(r.getRequestID()), r.getLoanAmount()));%>%">
                                                                        <strong><i>&#8369</i><%=apcpRequestDAO.getSumOfReleasesByRequestId(r.getRequestID())%> / <%=currency.format(r.getLoanAmount())%></strong>
                                                                    </div> 
                                                                </div> 
                                                            </td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                    <tfoot>
                                                        <tr>
                                                            <th>Loan <br>Tracking No.</th>
                                                            <th>Name of ARBO</th>
                                                            <th>Last Release Date</th>
                                                            <th>Progress</th>
                                                        </tr>
                                                    </tfoot>
                                                </table>   
                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="pastDue">
                                                <div class ="row">
                                                    <div class="col-xs-3"></div>
                                                    <div class="col-xs-6">
                                                        <div class="box-body">
                                                            <canvas id="pieCanvasPastDue"></canvas>
                                                            <div class="row text-center">
                                                                <a class="btn btn-submit" data-toggle="modal" data-target="#modalPiePastDue">View More</a>
                                                            </div>
                                                        </div>
                                                        <div class="modal fade" id="modalPiePastDue">
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
                                                                                    <th>Loan Tracking No.</th>
                                                                                    <th>Past Due Amount</th>
                                                                                    <th>Reason for Past Due</th>
                                                                                    <th>Other Reason</th>
                                                                                    <th>Date Recorded</th>
                                                                                    <th>Date Settled</th>
                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                    for (PastDueAccount pda : pdaByRequestList) {
                                                                                        APCPRequest apcpPDA = apcpRequestDAO.getRequestByID(pda.getRequestID());

                                                                                %>
                                                                                <tr>
                                                                                    <td><%=apcpPDA.getLoanTrackingNo()%></td>
                                                                                    <td><%=currency.format(pda.getPastDueAmount())%></td>
                                                                                    <td><%=pda.getReasonPastDueDesc()%></td>
                                                                                    <td><%=pda.getOtherReason()%></td>
                                                                                    <td><%=pda.getDateRecorded()%></td>
                                                                                    <td>
                                                                                        <%if(pda.getDateSettled() != null){
                                                                                            out.print(pda.getDateSettled());
                                                                                        }else{
                                                                                            out.print("Unsettled");
                                                                                        }%>
                                                                                    </td>
                                                                                </tr>
                                                                                <%}%>
                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>

                                                                                    <th>ARB Name</th>
                                                                                    <th>Past Due Amount</th>
                                                                                    <th>Reason for Past Due</th>
                                                                                    <th>Date Settled</th>
                                                                                    <th>Recorded By</th>
                                                                                    <th>Date Recorded</th>
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
                                                    <div class="col-xs-3"></div>
                                                </div>
                                            </div>
                                            <!-- /.tab-pane -->

                                        </div>
                                        <!-- /.tab-content -->
                                    </div>
                                    <!-- /.nav-tabs-custom -->
                                    <div class="box-footer">
                                        <div class="row">

                                            <div class="col-sm-3 col-xs-6">
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
                                                                                <th>Loan Tracking No.</th>
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

                                                                                <th>Loan Tracking No.</th>
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
                                            <div class="col-sm-3 col-xs-6">
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
                                                                                <td><%=arboDAO.getARBCount(arbo.getArboID())%></td>
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
                                            <div class="col-sm-3 col-xs-6">
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
                                                                                <th>Loan Tracking No.</th>
                                                                                <th>ARBO Name</th>
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
                                                                                <th>Loan Tracking No.</th>
                                                                                <th>ARBO Name</th>
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
                                            <div class="col-sm-3 col-xs-6">
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
                                        <!-- /.row -->
                                    </div>
                                </div>
                            </div>





                            <!-- /.col -->
                        </div>
                        <!-- /.col -->
                        <div class=" col-xs-12">
                            <div class="box">
                                <div class="box-header with-border" >
                                    <h3 class="box-title"><a href="view-apcp-status.jsp">Agrarian Production Credit Program Requests</a></h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="box-body" >
                                    <div clas="row  color-palette-set">
                                        <div class="col-lg-4 col-xs-6">
                                            <!-- small box -->
                                            <div class="small-box bg-green-gradient color-palette">
                                                <div class="inner">
                                                    <h3><%out.print(requestedRequests.size());%></h3>

                                                    <p>Requested</p>
                                                </div>
                                                <div class="icon">
                                                    <i class="fa fa-keyboard-o"></i>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-xs-6">
                                            <!-- small box -->
                                            <div class="small-box bg-green-active color-palette">
                                                <div class="inner">
                                                    <h3><%out.print(clearedRequests.size());%></h3>

                                                    <p>Cleared</p>
                                                </div>
                                                <div class="icon">
                                                    <i class="fa fa-check-square-o"></i>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-xs-6">
                                            <!-- small box -->
                                            <div class="small-box bg-green">
                                                <div class="inner">
                                                    <h3><%out.print(endorsedRequests.size());%></h3>

                                                    <p>Endorsed</p>
                                                </div>
                                                <div class="icon">
                                                    <i class="fa fa-upload"></i>
                                                </div>

                                            </div>
                                        </div>

                                    </div>
                                    <div clas="row">
                                        <div class="col-lg-4 col-xs-6">
                                            <!-- small box -->
                                            <div class="small-box bg-yellow">
                                                <div class="inner">
                                                    <h3><%out.print(approvedRequests.size());%></h3>

                                                    <p>Approved</p>
                                                </div>
                                                <div class="icon">
                                                    <i class="fa fa-thumbs-o-up"></i>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-xs-6">
                                            <!-- small box -->
                                            <div class="small-box bg-red">
                                                <div class="inner">
                                                    <h3><%out.print(forReleaseRequests.size());%></h3>

                                                    <p>For Release</p>
                                                </div>
                                                <div class="icon">
                                                    <i class="fa fa-folder-o"></i>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="col-lg-4 col-xs-6">
                                            <!-- small box -->
                                            <div class="small-box bg-green">
                                                <div class="inner">
                                                    <h3><%out.print(releasedRequests.size());%></h3>

                                                    <p>Released</p>
                                                </div>
                                                <div class="icon">
                                                    <i class="fa fa-folder-open-o"></i>
                                                </div>

                                            </div>
                                        </div>

                                    </div>

                                    <div class="row">
                                        <div class="col-xs-12">
                                            <table class="table table-bordered table-striped export">
                                                <thead>
                                                    <tr>
                                                        <th>ARBO Name</th>
                                                        <th>Loan Reason</th>
                                                        <th>Loan Amount</th>
                                                        <th>Land Area</th>
                                                        <th>Date</th>
                                                        <th>Status</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <%
                                                        for (APCPRequest r : allRequests) {
                                                            ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                                    %>
                                                    <tr>
                                                        <td><%out.print(arbo.getArboName());%></td>
                                                        <td><%out.print(r.getLoanReason());%></td>
                                                        <td><%out.print(currency.format(r.getLoanAmount()));%></td>
                                                        <td><%out.print(r.getHectares() + " hectares");%></td>

                                                        <%if (r.getRequestStatus() == 1 || r.getRequestStatus() == 6) {%>
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
                                                        <th>ARBO Name</th>
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

                            </div>
                        </div>
                        <div class=" col-xs-12">
                            <div class="box">
                                <div class="box-header with-border" >
                                    <h3 class="box-title"><a href="view-capdev-status.jsp">Capacity Development Proposals</a></h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>




                                <div class="box-body" >

                                    <div clas="row">
                                        <a href="provincial-field-officer-view-capdev-status.jsp">
                                            <div class="col-lg-3 col-xs-6" >
                                                <!-- small box -->
                                                <div class="small-box bg-yellow">
                                                    <div class="inner">
                                                        <h3><%out.print(requestedRequests.size());%></h3>

                                                        <p>Requested</p>
                                                    </div>
                                                    <div class="icon" >
                                                        <i class="fa fa-keyboard-o"></i>
                                                    </div>

                                                </div>
                                            </div>
                                        </a>
                                        <div class="col-lg-3 col-xs-6">
                                            <!-- small box -->
                                            <div class="small-box bg-red">
                                                <div class="inner">
                                                    <h3><%out.print(pendingPlans.size());%></h3>

                                                    <p>Pending</p>
                                                </div>
                                                <div class="icon">
                                                    <i class="fa fa-hourglass-2"></i>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-xs-6">
                                            <!-- small box -->
                                            <div class="small-box bg-green">
                                                <div class="inner">
                                                    <h3><%out.print(approvedPlans.size());%></h3>

                                                    <p>Approved</p>
                                                </div>
                                                <div class="icon">
                                                    <i class="fa  fa-thumbs-up"></i>
                                                </div>

                                            </div>
                                        </div>
                                        <div class="col-lg-3 col-xs-6">
                                            <!-- small box -->
                                            <div class="small-box bg-aqua">
                                                <div class="inner">
                                                    <h3><%out.print(implementedPlans.size());%></h3>

                                                    <p>Implemented</p>
                                                </div>
                                                <div class="icon">
                                                    <i class="fa fa-check-square-o"></i>
                                                </div>

                                            </div>
                                        </div>
                                    </div>
                                    <table class="table table-bordered table-striped export">
                                        <thead>
                                            <tr>
                                                <th>Region</th>
                                                <th>Province</th>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>                                                
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <%
                                                for (CAPDEVPlan cp : allPlans) {
                                                    APCPRequest r = apcpRequestDAO.getRequestByID(cp.getRequestID());
                                                    ARBO arbo2 = arboDAO.getARBOByID(r.getArboID());
                                                    CAPDEVDAO capdao = new CAPDEVDAO();
                                            %>
                                            <tr>
                                                <td><%out.print(arbo2.getArboRegionDesc());%></td>
                                                <td><%out.print(arbo2.getArboProvinceDesc());%></td>
                                                <td><%out.print(arbo2.getArboName());%></td>
                                                <td><a href="ReviewCAPDEVAssessment?planID=<%out.print(cp.getPlanID());%>"><%out.print(cp.getPlanDTN());%></a></td>
                                                <td><%out.print(capdao.getCAPDEVPlanActivities(cp.getPlanID()).size());%></td>
                                                <td><%out.print(cp.getPlanStatusDesc());%></td>
                                            </tr>
                                            <%}%>
                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Region</th>
                                                <th>Province</th>
                                                <th>ARBO Name</th>
                                                <th>Plan DTN</th>
                                                <th>No. of Activities</th>
                                                <th>Status</th>  
                                            </tr>
                                        </tfoot>
                                    </table>  

                                    <!-- /.tab-pane -->

                                </div>

                            </div>
                        </div>
                    </div>

                </section>

            </div>
            <!-- /.row -->


            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>
        <script type="text/javascript">

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
                var ctx = $('#barCanvas').get(0).getContext('2d');
            <%
                Chart bar = new Chart();
                String json = bar.getBarChartEducation(allArbsList);
            %>
                new Chart(ctx, <%out.print(json);%>);

                var ctx2 = $('#lineCanvas').get(0).getContext('2d');
            <%
                Chart line = new Chart();
                String json2 = line.getCropHistory(crops,allArbsList);
            %>
                new Chart(ctx2, <%out.print(json2);%>);

                var ctx3 = $('#pieCanvas').get(0).getContext('2d');
            <%
                Chart pie = new Chart();
                String json3 = pie.getPieChartGender(allArbsList);
            %>
                new Chart(ctx3, <%out.print(json3);%>);

                var ctx4 = $('#pieCanvasPastDue').get(0).getContext('2d');
            <%
                Chart pie2 = new Chart();
                String json4 = pie2.getPieChartPastDue(allRequests);
            %>
                new Chart(ctx4, <%out.print(json4);%>);


            });
        </script>
    </body>
</html>
