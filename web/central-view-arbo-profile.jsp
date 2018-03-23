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
            <%@include file="jspf/central-sidebar.jspf"%>
            <%
                APCPRequest r = apcpRequestDAO.getRequestByID((Integer) request.getAttribute("requestID"));
                ARBO a = arboDAO.getARBOByID(r.getArboID());
                ArrayList<ARB> arbList = arbDAO.getAllARBsARBO(r.getArboID());
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
                    <div class="box-body"> 

                        <div class="nav-tabs-custom">
                            <!-- Tabs within a box -->
                            <ul class="nav nav-tabs pull-left">
                                <li class="active"><a href="#request" data-toggle="tab">Request Information</a></li>
                                <li><a href="#info" data-toggle="tab">ARBO Profile</a></li>
                                <li><a href="#history" data-toggle="tab">CAPDEV History</a></li>
                            </ul>

                            <%@include file="jspf/arboInfo.jspf"%>
                            <form method="post">
                                <div class="box-footer ">
                                    <div class="btn-group pull-right">
                                        <button type="submit" onclick="form.action = 'DisapproveNewAccessing?id=<%out.print(r.getRequestID());%>'" class="btn btn-danger"><i class="fa fa-times margin-r-5"></i> Disapprove</button>
                                        <button type="submit" onclick="form.action = 'ApproveNewAccessing?id=<%out.print(r.getRequestID());%>'" class="btn btn-success"><i class="fa fa-check margin-r-5"></i>Approve</button>
                                    </div>
                                </div>
                            </form>
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
            var ctx = $('#barCanvas').get(0).getContext('2d');
            <%
                Chart bar = new Chart();
                String json = bar.getBarChartEducation(arbList);
            %>
            new Chart(ctx, <%out.print(json);%>);

            var ctx3 = $('#pieCanvas').get(0).getContext('2d');
            <%
                Chart pie = new Chart();
                String json3 = pie.getPieChartGender(arbList);
            %>
            new Chart(ctx3, <%out.print(json3);%>);

        </script>
    </body>
</html>
