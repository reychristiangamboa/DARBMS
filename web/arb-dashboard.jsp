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
            <%@include file="jspf/central-sidebar.jspf"%>
            <%}%>

            <%
                
                ARBDAO arbDAO = new ARBDAO();
                ARBODAO arboDAO = new ARBODAO();
                CropDAO cropDAO = new CropDAO();
                AddressDAO addressDAO = new AddressDAO();
                ArrayList<Region> regionList = addressDAO.getAllRegions();
                
                
                ArrayList<ARB> allArbsList = new ArrayList();
                if((Integer)session.getAttribute("userType") == 5){
                    allArbsList = arbDAO.getAllARBs();    
                }else if((Integer)session.getAttribute("userType") == 4){
                    allArbsList = arbDAO.getAllRegionalARBs((Integer)session.getAttribute("regOfficeCode"));    
                }
                
                int category = 1; // COUNT
                int demographic = 1; // GENDER
                ArrayList<Region> regions = new ArrayList();
                ArrayList<Province> provOffices = new ArrayList();
                
                if(request.getAttribute("category") != null){
                    category = (Integer)request.getAttribute("category");
                }
                
                if(request.getAttribute("demographic") != null){
                    demographic = (Integer)request.getAttribute("demographic");
                }
                
                if(request.getAttribute("filtered") != null){
                    allArbsList = (ArrayList<ARB>)request.getAttribute("filtered");
                }
                

                if(request.getAttribute("regions") != null){
                    regions = (ArrayList<Region>)request.getAttribute("regions");
                }
                

                if(request.getAttribute("provOffices") != null){
                    provOffices = (ArrayList<Province>)request.getAttribute("provOffices");
                }
            %>

            <!-- Content Wrapper. Contains page content -->
            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <i class="fa fa-users"></i> Agrarian Reform Beneficiary (ARB) Dashboard
                    </h1>
                </section>

                <section class="invoice">
                    <!-- info row -->
                    <div class="row no-print">
                        <div class="col-xs-12">
                            <h5 class="page-header">
                                <i class="fa fa-search"></i> Filter By
                            </h5>
                        </div>
                        <!-- /.col -->
                    </div>
                    <div class="row invoice-info">

                        <div class="col-sm-12">
                            <form method="post">
                                <div class="row no-print">
                                    <div class="col-xs-12">
                                        <input type="radio" id="drillDownGender" name="filterBy" value="All" checked onclick="document.getElementById('regions').disabled = true;document.getElementById('provinces').disabled = true;">
                                        <label for="">Select All</label>
                                        &nbsp;&nbsp;
                                        <input type="radio" id="drillDownGender" name="filterBy" value="regions" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = true;">
                                        <label for="">Regions</label>
                                        &nbsp;&nbsp;
                                        <input type="radio" id="drillDownGender" name="filterBy" value="provinces" onclick="document.getElementById('regions').disabled = false;document.getElementById('provinces').disabled = false;">
                                        <label for="">Provinces</label>
                                    </div>
                                </div>
                                <div class="row no-print">
                                    <div class="col-xs-2">
                                        <div class="form-group">
                                            <label for="">Demographic</label>
                                            <select name="demographic" id="demographic" class="form-control select2">
                                                <option value="1">Gender</option>
                                                <option value="2">Age</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <div class="form-group">
                                            <label for="">Category</label>
                                            <select name="category" id="category" class="form-control select2" >
                                                <option value="1">Count</option>
                                                <option value="2">Recipients</option>
                                                <option value="3">Participation Rate</option>
                                                <option value="4">APCP Mean Ave. Disbursed Amount </option>
                                                <option value="5">APCP Mean Ave. Past Due Amount</option>
                                                <option value="5">Settlement Rate</option>
                                                <option value="5">APCP Type Availment</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <div class="form-group">
                                            <label for="">Regions</label>
                                            <select name="regions[]" id="regions" onchange="chg2()" class="form-control select2" multiple="multiple" disabled>
                                                <%for(Region r : regionList){%>
                                                <option value="<%=r.getRegCode()%>"><%out.print(r.getRegDesc());%></option>
                                                <%}%>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-2">
                                        <div class="form-group">
                                            <label for="">Provinces</label>
                                            <select name="provinces[]" id="provinces" class="form-control select2" multiple="multiple" disabled>

                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-2" style="padding-top:20px;">
                                        <label for="">&nbsp;</label>
                                        <button type="submit" onclick="form.action = 'FilterARBDashboard'" class="btn btn-success"><i class="fa fa-filter margin-r-5"></i> Filter</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- /.row -->
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="row">
                                <div class="col-xs-3"></div>
                                <div class="col-xs-6">
                                    <div class="box-body" id="canvasContainer">
                                        <canvas id="chartCanvas"></canvas>
                                        <div class="row text-center">
                                            <a class="btn btn-submit" data-toggle="modal" data-target="#modalPie">View More</a>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-3"></div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box box-solid" style="border-color: lightgrey; padding-bottom: 20px" >
                                <div class="box-body">
                                    <h5><strong>KEY FINDINGS</strong></h5>  
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <h6><strong>DEMOGRAPHIC: </strong> </h6>
                                        </div>
                                        <div class="col-xs-3">
                                            <h6><strong>CATEGORY: </strong> </h6>
                                        </div>
                                        <div class="col-xs-3">
                                            <h6><strong>REGION: </strong></h6>
                                        </div>
                                        <div class="col-xs-3">
                                            <h6><strong>PROVINCE: </strong> </h6>
                                        </div>  
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-3">
                                            <%if(request.getAttribute("demographicDesc") != null){%>
                                            <h6><%out.print((String)request.getAttribute("demographicDesc"));%></h6>
                                            <%}%>
                                        </div>
                                        <div class="col-xs-3">
                                            <%if(request.getAttribute("categoryDesc") != null){%>
                                            <h6><%out.print((String)request.getAttribute("categoryDesc"));%></h6>
                                            <%}%>
                                        </div>
                                        <div class="col-xs-3">
                                            <%
                                                String regionsStr = "";
                                                
                                            %>
                                            <%if(regions.isEmpty()){%>
                                            <h6>N/A</h6>
                                            <%}else{
                                                for(Region region : regions){
                                                    regionsStr += region.getRegDesc() + " ";
                                                }
                                            %>
                                            <h6><%out.print(regionsStr);%></h6>
                                            <%}%>
                                        </div>

                                        <div class="col-xs-3">
                                            <%
                                                String provincesStr = "";
                                                
                                            %>
                                            <%if(provOffices.isEmpty()){%>
                                            <h6>N/A</h6>
                                            <%}else{
                                                for(Province province : provOffices){
                                                    provincesStr += province.getProvDesc() + " ";
                                                }
                                            %>
                                            <h6><%out.print(provincesStr);%></h6>
                                            <%}%>
                                        </div>  
                                    </div>
                                </div>
                                <hr style="margin-left:20px; margin-right: 15px; margin-top: -10px;">
                                <div class="box-body" style="margin:0 auto">
                                    <div class="col-xs-1">
                                    </div>
                                    <div class="col-xs-5"  style="margin-left:5px; margin-right: 15px; margin-bottom: 10px; margin-top: 10px; background: lightgrey; display: inline; ">
                                        <h4>HIGHEST</h4>
                                        <p>This Region has the Highest</p>
                                    </div>
                                    <div class="col-xs-5"  style="margin-left:20px; margin-right: 15px; margin-bottom: 10px; margin-top: 10px; background: lightgrey; display: inline;">
                                        <h4>LOWEST</h4>
                                        <p>This Region has the Lowest</p>
                                    </div>
                                    <div class="col-xs-1">
                                    </div>
                                </div>

                                <div class="box-body" style="margin:0 auto">
                                    <div class="col-xs-1">
                                    </div>
                                    <div class="col-xs-5"  style="margin-left:5px; margin-right: 15px; margin-bottom: 10px; margin-top: 10px; background: lightgrey; display: inline; ">
                                        <h4>HIGHEST</h4>
                                        <p>This Region has the Highest</p>
                                    </div>
                                    <div class="col-xs-5"  style="margin-left:20px; margin-right: 15px; margin-bottom: 10px; margin-top: 10px; background: lightgrey; display: inline;">
                                        <h4>LOWEST</h4>
                                        <p>This Region has the Lowest</p>
                                    </div>
                                    <div class="col-xs-1">
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>



                    <!-- this row will not appear when printing -->
                    <div class="row no-print">
                        <div class="col-xs-12">
                            <button type="button" onclick="window.print()" class="btn btn-default pull-right"><i class="fa fa-print"></i> Print</button>
                        </div>
                    </div>
                </section>
                <!-- /.content -->
                <div class="clearfix"></div>
            </div>
        </div>
        <!-- /.content-wrapper -->
        <!-- ./wrapper -->
        <%@include file="jspf/footer.jspf" %>
        <script>
            function print() {
                window.print();
            }

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
                var ctx = $('#chartCanvas').get(0).getContext('2d');
            <%
                Chart chart = new Chart();
                String json = "";
                
                if(demographic == 1){ // GENDER
                    if(category == 1){ // COUNT
                        if(request.getAttribute("filterByREGION") != null){ // FILTER BY REGION
                            json = chart.getBarChartRecipientsByRegion(allArbsList, regions);
                        }else if(request.getAttribute("filterByPROVINCE") != null){ // FILTER BY PROVINCE
                            json = chart.getBarChartRecipientsByProvOffice(allArbsList, provOffices);
                        }else{
                            json = chart.getPieChartGender(allArbsList); // PIE CHART
                        }
                    }else if(category == 2){ // Recipients
                        if(request.getAttribute("filterByREGION") != null){ // FILTER BY REGION
                            json = chart.getBarChartRecipientsByRegion(allArbsList, regions);
                        }else if(request.getAttribute("filterByPROVINCE") != null){ // FILTER BY PROVINCE
                            json = chart.getBarChartRecipientsByProvOffice(allArbsList, provOffices);
                        }else{
                            json = chart.getPieChartGender(allArbsList);
                        }
                    }else if(category == 3){ // Participants
                        ArrayList<Crop> crops = cropDAO.getAllCropsByARBList(allArbsList);
                        ArrayList<Crop> cropHistory = cropDAO.getCropHistory(allArbsList);
                        json = chart.getCropHistory(crops,allArbsList); // LINE GRAPH
                    }else if(category == 4){ // Participation Rate
                        
                    }else if(category == 5){ // APCP Mean Ave. Disbursed Amount
                        
                    }
                    else if(category == 6){ // APCP Mean Ave. Past Due Amount
                        
                    }else if(category == 7){ // Settlement Rate
                        
                    }else if(category == 8){ // APCP Type Availment
                        
                    }
                }else if(demographic == 2){ // AGE
                    if(category == 1){ // COUNT
                        json = chart.getBarChartAgeCount(allArbsList); // BAR CHART
                    }
                }
            %>
                new Chart(ctx, <%out.print(json);%>);
            });
        </script>

    </body>
</html>
