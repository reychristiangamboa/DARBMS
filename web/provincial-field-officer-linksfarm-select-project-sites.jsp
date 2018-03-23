<%-- 
    Document   : provincial-field-officer-linksfarm-select-project-sites
    Created on : Mar 23, 2018, 6:27:16 PM
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
            .bs-example{
                margin: 20px;
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
                                    <h3 class="box-title">Project Site</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="box-body">

                                    <div class="box-body">
                                        <%
                                            AddressDAO cityMunCode = new AddressDAO();
                                            for (CityMun cm : cityMunList) {
                                                ArrayList<ARBO> arboListCityMun = arboDAO.getAllARBOsByCityMun(cm.getCityMunCode());
                                                
                                        %>
                                        <div class="active tab-pane" >

                                            <div class="col-lg-2 col-xs-6">
                                                <!-- small box -->
                                                <a href="ViewSite?id=<%out.print(cityMunCode.getCityMunCode(cm.getCityMunDesc()));%>" class="btn-link">
                                                <div class="small-box bg-yellow">
                                                    <div class="inner">
                                                        <h3><%out.print(arboListCityMun.size());%></h3>

                                                        <p><%=cm.getCityMunDesc()%></p>
                                                    </div>
                                                </div>
                                                </a>
                                            </div>
                                        </div>
                                        <%}%>
                                    </div>
                                </div>
                            </div>
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Non Project Site</h3>
                                    <div class="box-tools pull-right">
                                        <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
                                        </button>
                                    </div>
                                </div>
                                <div class="box-body">

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

                                                            <table class="table table-bordered table-striped export">
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
                                                                        <td><a href="ViewSite?id=<%out.print(arbo.getArboID());%>" class="btn btn-link"><%out.print(arbo.getArboName());%></a></td>
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
                String json = bar.getBarChartEducation(arbListProvince);
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
                String json3 = pie.getPieChartGender(arbListProvince);
            %>
                new Chart(ctx3, <%out.print(json3);%>);

                var ctx4 = $('#pieCanvasPastDue').get(0).getContext('2d');
            <%
                Chart pie2 = new Chart();
                String json4 = pie2.getPieChartPastDue(provincialRequests);
            %>
                new Chart(ctx4, <%out.print(json4);%>);


            });
        </script>
    </body>
</html>
