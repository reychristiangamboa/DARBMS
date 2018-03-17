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

        </style>
    </head>
    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        User Profile
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
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>
                                <div class="box-body">
                                    <div class="nav-tabs-custom">
                                        <ul class="nav nav-tabs">
                                            <li class="active"><a href="#gender" data-toggle="tab">ARB Per Gender</a></li>
                                            <li><a href="#educ" data-toggle="tab">ARB Education Level</a></li>
                                            <li><a href="#citymun" data-toggle="tab">ARBO Per City Municipality</a></li>
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
                                                                        <th>ARBO Name</th>
                                                                        <th>No. of Members</th>

                                                                    </tr>
                                                                </thead>
                                                                <tbody>

                                                                    <tr>
                                                                        <td></td>
                                                                        <td></td>
                                                                    </tr>

                                                                </tbody>
                                                                <tfoot>
                                                                    <tr>
                                                                        <th>ARBO Name</th>
                                                                        <th>City Mun ID</th>
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
                                                                        <th>ARBO Name</th>
                                                                        <th>No. of Members</th>

                                                                    </tr>
                                                                </thead>
                                                                <tbody>

                                                                    <tr>
                                                                        <td></td>
                                                                        <td></td>
                                                                    </tr>

                                                                </tbody>
                                                                <tfoot>
                                                                    <tr>
                                                                        <th>ARBO Name</th>
                                                                        <th>City Mun ID</th>
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
                                                        for (CityMun cm : cityMunList) {
                                                            ArrayList<ARBO> arboListCityMun = arboDAO.getAllARBOsByCityMun(cm.getCityMunCode());
                                                    %>
                                                    <div class="active tab-pane" >
                                                        <div class="col-lg-2 col-xs-6" data-toggle="modal" data-target="#modal-default<%out.print(cm.getCityMunCode());%>">
                                                            <!-- small box -->
                                                            <div class="small-box bg-yellow">
                                                                <div class="inner">
                                                                    <h3><%out.print(arboListCityMun.size());%></h3>

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
                                                                                    <th>ARBO Name</th>
                                                                                    <th>No. of Members</th>

                                                                                </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                                <%
                                                                                    for (ARBO arbo : arboListCityMun) { %>
                                                                                <tr>
                                                                                    <td><a href="ViewARBO?id=<%out.print(arbo.getArboID());%>" class="btn btn-link"><%out.print(arbo.getArboName());%></a></td>
                                                                                    <td><%out.print(arboDAO.getARBCount(arbo.getArboID()));%></td>
                                                                                </tr>
                                                                                <%}%>
                                                                            </tbody>
                                                                            <tfoot>
                                                                                <tr>
                                                                                    <th>ARBO Name</th>
                                                                                    <th>City Mun ID</th>
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
                                                            <h4 class="modal-title"></h4>
                                                        </div>
                                                        <div class="modal-body">

                                                            <table class="table table-bordered table-striped modTable">
                                                                <thead>
                                                                    <tr>
                                                                        <th>ARBO Name</th>
                                                                        <th>No. of Members</th>

                                                                    </tr>
                                                                </thead>
                                                                <tbody>

                                                                    <tr>
                                                                        <td></td>
                                                                        <td></td>
                                                                    </tr>

                                                                </tbody>
                                                                <tfoot>
                                                                    <tr>
                                                                        <th>ARBO Name</th>
                                                                        <th>City Mun ID</th>
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
                                            <li><a href="#pend" data-toggle="tab">Past-Due Requests</a></li>
                                        </ul>
                                        <div class="tab-content">
                                            <div class="active tab-pane" id="release">
                                                <table id="example1" class="table table-bordered table-striped">
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
                                                            for(APCPRequest r : releasedRequests){
                                                                ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                                            
                                                        %>
                                                        <tr>
                                                            <td><a href="MonitorRelease?id=<%out.print(r.getRequestID());%>"><%=r.getLoanTrackingNo()%></a></td>
                                                            <td><%=arbo.getArboName()%></td>
                                                            <td><%=f.format(r.getReleases().get(r.getReleases().size()-1).getReleaseDate())%></td>
                                                            <td  width=50%>
                                                                <div class="progress">
                                                                    <div class="progress-bar progress-bar-green" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: <%out.print(r.getProgressBarWidth(apcpRequestDAO.getSumOfReleasesByRequestId(r.getRequestID()), r.getLoanAmount()));%>%">
                                                                        <strong><i>&#8369</i><%=apcpRequestDAO.getSumOfReleasesByRequestId(r.getRequestID())%> / <i>&#8369</i><%=r.getLoanAmount()%></strong>
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
                                            <div class="tab-pane" id="pend">
                                                <table id="example3" class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>Request ID</th>
                                                            <th>Name of ARBO</th>
                                                            <th>Loan Request</th>
                                                            <th>Loan Amount</th>
                                                            <th>Status</th>
                                                            <th>Date</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <tr>
                                                            <td>1</td>
                                                            <td>Rey ARBO</td>
                                                            <td>April 1, 2011</td>
                                                            <td>
                                                                10100000
                                                            </td>
                                                            <td>
                                                                <span class="label label-danger">RICE</span>
                                                            </td>
                                                            <td>
                                                                April 1, 2011
                                                            </td>
                                                        </tr>

                                                    </tbody>
                                                    <tfoot>
                                                        <tr>
                                                            <th>Request ID</th>
                                                            <th>Name of ARBO</th>
                                                            <th>Loan Request</th>
                                                            <th>Loan Amount</th>
                                                            <th>Status</th>
                                                            <th>Date</th>
                                                        </tr>
                                                    </tfoot>
                                                </table>  
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
                                                    
                                                    <h5 class="description-header"><i>&#8369 </i><%=apcpRequestDAO.getYearlySumOfReleasesByRequestId(provincialRequests,year)%></h5>
                                                    <span class="description-text">TOTAL YEARLY RELEASED AMOUNT</span>
                                                </div>
                                                <!-- /.description-block -->
                                            </div>
                                            <!-- /.col -->
                                            <div class="col-sm-3 col-xs-6">
                                                <div class="description-block border-right">
                                                    <span class="description-percentage text-yellow"><i class="fa fa-caret-left"></i> 0%</span>
                                                    <h5 class="description-header"><i>&#8369</i>10,390.90</h5>
                                                    <span class="description-text">TOTAL ACCUMULATED RELEASED AMOUNT</span>
                                                </div>
                                                <!-- /.description-block -->
                                            </div>
                                            <!-- /.col -->
                                            <div class="col-sm-3 col-xs-6">
                                                <div class="description-block border-right">
                                                    <span class="description-percentage text-green"><i class="fa fa-caret-up"></i> 20%</span>
                                                    <h5 class="description-header"><i>&#8369</i>24,813.53</h5>
                                                    <span class="description-text">TOTAL REQUESTED AMOUNT</span>
                                                </div>
                                                <!-- /.description-block -->
                                            </div>
                                            <!-- /.col -->
                                            <div class="col-sm-3 col-xs-6">
                                                <div class="description-block">
                                                    <span class="description-percentage text-red"><i class="fa fa-caret-down"></i> 18%</span>
                                                    <h5 class="description-header"><i>&#8369</i>1200</h5>
                                                    <span class="description-text">TOTAL PAST DUE AMOUNT</span>
                                                </div>
                                                <!-- /.description-block -->
                                            </div>
                                        </div>
                                        <!-- /.row -->
                                    </div>
                                </div>
                            </div>





                            <!-- /.col -->
                        </div>
                        <!-- /.col -->
                        <div class=" col-xs-6">
                            <div class="box">
                                <div class="box-header with-border" >
                                    <h3 class="box-title">APCP Requests</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
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
                                            <table id="example6" class="table table-bordered table-striped">
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
                                                        for (APCPRequest r : provincialRequests) {
                                                            ARBO arbo = arboDAO.getARBOByID(r.getArboID());
                                                    %>
                                                    <tr>
                                                        <td><%out.print(arbo.getArboName());%></td>
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
                        <div class=" col-xs-6">
                            <div class="box">
                                <div class="box-header with-border" >
                                    <h3 class="box-title">CAPDEV Proposals</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                        <button type="button" class="btn btn-box-tool" data-widget="remove"><i class="fa fa-times"></i></button>
                                    </div>
                                </div>




                                <div class="box-body" >

                                    <div clas="row">
                                        <div class="col-lg-3 col-xs-6">
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
                                    <table id="example5" class="table table-bordered table-striped">
                                        <thead>
                                            <tr>
                                                <th>Request ID</th>
                                                <th>Loan Request</th>
                                                <th>Loan Amount</th>
                                                <th>Status</th>
                                                <th>Date</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>1</td>
                                                <td>April 1, 2011
                                                </td>
                                                <td>
                                                    10100000
                                                </td>
                                                <td>
                                                    <span class="label label-danger">RICE</span>
                                                </td>
                                                <td>
                                                    April 1, 2011
                                                </td>
                                            </tr>

                                        </tbody>
                                        <tfoot>
                                            <tr>
                                                <th>Request ID</th>
                                                <th>Loan Request</th>
                                                <th>Loan Amount</th>
                                                <th>Status</th>
                                                <th>Date</th>
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

            <footer class="main-footer">
                <div class="pull-right hidden-xs">
                    <b>Version</b> 2.4.0
                </div>
                <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
                reserved.
            </footer>
            <!-- /.row -->


            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>
        <script type="text/javascript">
            $(function () {
                var ctx = $('#barCanvas').get(0).getContext('2d');
            <%
                Chart bar = new Chart();
                String json = bar.getBarChartEducation(arbListProvince);
            %>
                new Chart(ctx, <%out.print(json);%>);

                var ctx2 = $('#lineCanvas').get(0).getContext('2d');
            <%
                Chart line = new Chart();
                String json2 = line.getCropHistory(arbListProvince);
            %>
                new Chart(ctx2, <%out.print(json2);%>);

                var ctx3 = $('#pieCanvas').get(0).getContext('2d');
            <%
                Chart pie = new Chart();
                String json3 = pie.getPieChartGender(arbListProvince);
            %>
                new Chart(ctx3, <%out.print(json3);%>);
            });
        </script>
    </body>
</html>
