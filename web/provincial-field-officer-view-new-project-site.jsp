<%-- 
    Document   : provincial-field-officer-view-new-project-site
    Created on : Mar 23, 2018, 6:23:52 PM
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
                int cityMunCode = (Integer) request.getAttribute("cityMunDesc");
                ARBODAO dao = new ARBODAO();
                ARBDAO dao2 = new ARBDAO();
                CropDAO dao3 = new CropDAO();
                ArrayList<ARB> allArbPerCity = dao2.getAllARBsByCityMun(cityMunCode);
                ArrayList<Crop> crops = dao3.getAllCropsByARBList(allArbPerCity);

            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        Project Site
                    </h1>
                </section>
                <section class="content-header">
                    <div class="row">
                        <div class="col-md-3">

                            <!-- Profile Image -->
                            <div class="box box-primary">
                                <div class="box-body box-profile">
                                    <h3 class="profile-username text-center"><%=allArbPerCity.get(0).getCityMunDesc()%></h3>
                                    <p class="text-muted text-center">New Project Site</p>

                                    <ul class="list-group list-group-unbordered">
                                        <li class="list-group-item">
                                            <b>No. of ARBs</b> <a class="pull-right" data-toggle="modal" data-target="#arbs"><%=allArbPerCity.size()%></a>
                                        </li>
                                        <li class="list-group-item text-center">
                                            <a class="btn btn-primary btn-lg" href="ProceedClusterARB?id=<%out.print(cityMunCode);%>">Cluster ARB</a>
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
                                                            </tr>
                                                        </thead>

                                                        <tbody>
                                                            <%
                                                                for (ARB arb : allArbPerCity) {
                                                            %>
                                                            <tr>
                                                                <td><a href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFullName());%></a></td>
                                                                <td><%out.print(arb.getFullAddress());%></td>
                                                            </tr>
                                                            <%}%>
                                                        </tbody>

                                                        <tfoot>
                                                            <tr>
                                                                <th>Full Name</th>
                                                                <th>Address</th>
                                                            </tr>
                                                        </tfoot>

                                                    </table>
                                                </div>
                                            </div>
                                        </div>


                                    </div>
                                    <!--                                            /.modal-content -->
                                </div>
                                <!--                                        /.modal-dialog -->
                            </div>

                            <!-- About Me Box -->
                            <!-- /.box -->
                        </div>
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
                                            <li><a href="#citymun" data-toggle="tab">ARB List</a></li>
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
                                                                                    for (ARB arb : allArbPerCity) {
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
                                                                                    for (ARB arb : allArbPerCity) {
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
                                                                        for (ARB arb : allArbPerCity) {

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
                                                    <div class="row">
                                                        <table id="arbTable" class="table table-bordered table-striped">
                                                            <thead>
                                                                <tr>
                                                                    <th>Full Name</th>
                                                                    <th>Address</th>
                                                                </tr>
                                                            </thead>

                                                            <tbody>
                                                                <%
                                                                    for (ARB arb : allArbPerCity) {
                                                                %>
                                                                <tr>
                                                                    <td><a href="ViewARB?id=<%out.print(arb.getArbID());%>"><%out.print(arb.getFullName());%></a></td>
                                                                    <td><%out.print(arb.getFullAddress());%></td>
                                                                </tr>
                                                                <%}%>
                                                            </tbody>

                                                            <tfoot>
                                                                <tr>
                                                                    <th>Full Name</th>
                                                                    <th>Address</th>
                                                                </tr>
                                                            </tfoot>

                                                        </table>
                                                    </div>
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
                                                                            ArrayList<Crop> cropHistory = dao3.getCropHistory(allArbPerCity);
                                                                            for (Crop c : cropHistory) {
                                                                                ARB arb = dao2.getARBByID(c.getArbID());
                                                                        %>
                                                                        <tr>
                                                                            <td><%=arb.getFullName()%></td>
                                                                            <td><%=dao.getARBOByID(arb.getArboID()).getArboName()%></td>
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

                                            <li class="active"><a href="#disbursement" data-toggle="tab">Disbursements</a></li>
                                            <li><a href="#repayment" data-toggle="tab">Repayments</a></li>
                                            <li><a href="#pastDue" data-toggle="tab">Past Due</a></li>
                                        </ul>
                                        <div class="tab-content">

                                            <div class="active tab-pane" id="disbursement">
                                                <div class="box-body">
                                                    <canvas id="pieDisbursements" style="height:250px"></canvas>
                                                </div>
                                            </div>
                                            <!-- /.tab-pane -->
                                            <div class="tab-pane" id="repayment" style="overflow-y: scroll; overflow-x: hidden;  max-height: 300px; ">
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
                            <!-- /.col -->

                        </div>
                    </div>

                </section>

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
                String json = bar.getBarChartEducation(allArbPerCity);
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
                String json3 = pie.getPieChartGender(allArbPerCity);
            %>
                new Chart(ctx3, <%out.print(json3);%>);

                var ctx4 = $('#pieDisbursements').get(0).getContext('2d');
            <%
                Chart pie2 = new Chart();
                String json4 = pie2.getPieChartDisbursement(allArbPerCity);
            %>
                new Chart(ctx4, <%out.print(json4);%>);


            });
        </script>
    </body>
</html>