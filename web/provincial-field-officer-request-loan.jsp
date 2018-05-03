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
            @media screen and (min-width: 992px) {
                .modal-lg {
                    width: 1080px; /* New width for large modal */
                }
            }
        </style>
    </head>

    <body class="hold-transition skin-blue sidebar-mini">
        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf" %>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf" %>
            <%
                int arboID = (Integer) request.getAttribute("arboID");
                ARBO arbo = arboDAO.getARBOByID(arboID);
            %>
            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><i class="fa fa-money"></i> Request for Loan</strong> 
                        <small><%out.print((String) session.getAttribute("provOfficeDesc") + ", " + (String) session.getAttribute("regOfficeDesc"));%></small>
                    </h1>


                </section>

                <!-- Main content -->
                <section class="content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Request for Loan</strong></h3>

                                </div>
                                <!-- /.box-header -->
                                <form role="form" method="post" action="RequestLoan">
                                    <div class="box-body">             

                                        <div class="box-body">

                                            <div class="row">
                                                <div class="col-xs-6">
                                                    <div class="form-group">
                                                        <label for="">Name of ARBO</label>
                                                        <input type="text" class="form-control" value="<%out.print(arbo.getArboName());%>"disabled >
                                                    </div>
                                                </div>
                                                <div class="col-xs-3">
                                                    <div class="form-group">
                                                        <label for="">No. of ARBs</label>
                                                        <input type="text" class="form-control" id="" placeholder="" value="<%out.print(arboDAO.getARBCount(arboID));%>" disabled>
                                                    </div>
                                                </div>

                                            </div>
                                            <div class="row">
                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label for="">Type</label>
                                                        <select name="apcpType" id="" class="form-control select2">
                                                            <option value="1">Crop Production</option>
                                                            <option value="2">Livelihood Program</option>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-3">
                                                    <div class="form-group">
                                                        <label for="">Land Area (Hectares)</label>
                                                        <input type="text" class="form-control" name="land" placeholder="" >
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-xs-4">
                                                    <label for=''>Loan Amount</label><br>
                                                    <div class="input-group">
                                                        <div class="input-group-addon">
                                                            <i>&#8369;</i>
                                                        </div>
                                                        <input id="loanAmount" type='text' name="loan" class="form-control numberOnly">
                                                    </div>
                                                </div>
                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label for="">Reason for Loan</label>
                                                        <select name="reason" id="" class="form-control select2">
                                                            <%for(LoanReason lr : loanReasons){%>
                                                            <option value="<%out.print(lr.getLoanReason());%>"><%out.print(lr.getLoanReasonDesc());%></option>
                                                            <%}%>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label for="">Other Reason</label>
                                                        <input type="text" class="form-control" name="otherReason" value="N/A">
                                                    </div>
                                                </div>    
                                            </div>

                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <label for=''>Remarks</label>
                                                    <textarea class="form-control" name="remarks" rows="1" placeholder="Remarks"></textarea>
                                                </div>
                                            </div>

                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <table class="modTable">
                                                        <thead>
                                                            <tr>
                                                                <th>Name</th>
                                                                <th>Address</th>
                                                                <th>Crops</th>
                                                                <th>Action</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <%for(ARB arb : arbo.getArbList()){%>
                                                            <tr>
                                                                <td><%=arb.getFullName()%></td>
                                                                <td><%=arb.getFullAddress()%></td>
                                                                <td><%=arb.printAllCrops()%></td>
                                                                <td><input type="checkbox" name="recipients" required></td>
                                                            </tr>
                                                            <%}%>
                                                        </tbody>
                                                        <tfoot>
                                                            <tr>
                                                                <th>Name</th>
                                                                <th>Address</th>
                                                                <th>Crops</th>
                                                                <th>Action</th>
                                                            </tr>
                                                        </tfoot>
                                                    </table>
                                                </div>
                                            </div>

                                        </div>

                                        <div class="box-footer">
                                            <input type="hidden" name="arboID" value="<%out.print(arboID);%>">
                                            <%if(request.getAttribute("newAccessing") != null){%>
                                            <input type="hidden" name="newAccessing" value="1">
                                            <%}%>
                                            <button type="submit" class="btn btn-primary pull-right"><i class="fa fa-send margin-r-5"></i> Submit</button>
                                        </div>

                                    </div>
                                </form>

                                <!-- /.box-body -->
                            </div>

                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->
                    </div>
                    <!-- /.col -->


                </section>
            </div>
            <!-- /.row -->

            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->


        <%@include file="jspf/footer.jspf" %>



    </body>
</html>
