<%-- 
    Document   : provincial-field-officer-linksfarm-cluster-arb
    Created on : Mar 23, 2018, 6:28:26 PM
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
            <%if ((Integer) session.getAttribute("userType") == 3) {%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>
            <%} else if ((Integer) session.getAttribute("userType") == 5) {%>
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>

            <%
                int cityMunCode = (Integer) request.getAttribute("cityMunCode");
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
                        Cluster ARBs
                    </h1>
                </section>

                <!-- Main content -->
                <section class="content">

                    <div class="row">
                        <div class="col-md-12">
                            <div class="box">
                                <form method="post" action="ClusterARB">
                                    <div class="box-header">
                                        <h3 class="box-title">Active ARBs</h3>
                                    </div>
                                    <!-- /.box-header -->
                                    <div class="box-body">

                                        <div class="row">
                                            <div class="col-xs-10">
                                                <div class="form-group">
                                                    <label for="">Cluser Name:</label>
                                                    <input type="text" name="clusterName" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-12">
                                                <table id="example1" class="table table-bordered table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>ARB Name</th>
                                                            <th>Address</th>
                                                            <th>ARBO</th>
                                                            <th>Gender</th>
                                                            <th>Education Level</th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <%
                                                            ARBODAO arboDAO = new ARBODAO(); 
                                                            for (ARB arb : allArbPerCity) {
                                                        %>
                                                        <tr>
                                                            <td><%out.print(arb.getFullName());%></td>
                                                            <td><%out.print(arb.getFullAddress());%></td>
                                                            <td><%out.print(arboDAO.getARBOByID(arb.getArboID()).getArboName());%></td>
                                                            <td><%out.print(arb.getGender());%></td>
                                                            <td><%out.print(arb.getEducationLevelDesc());%></td>
                                                            <td> 
                                                                <input type="checkbox" id="<%=arb.getArbID()%>" name="IDs" value="<%=arb.getArbID()%>"/>
                                                            </td>
                                                        </tr>
                                                        <%}%>
                                                    </tbody>
                                                    <tfoot>
                                                        <tr>
                                                            <th>ARB Name</th>
                                                            <th>Address</th>
                                                            <th>ARBO</th>
                                                            <th>Gender</th>
                                                            <th>Education Level</th>
                                                            <th>Action</th>
                                                        </tr>
                                                    </tfoot>
                                                </table>
                                            </div>
                                        </div>

                                    </div>
                                    <!-- /.box-body -->
                                    <div class="box-footer">
                                        <div class="pull-right">
                                            <input type="hidden" name="cityMunCode" value="<%=cityMunCode%>">
                                            <button type="submit" class="btn btn-primary pull-right">Cluster</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
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
    </body>
</html>