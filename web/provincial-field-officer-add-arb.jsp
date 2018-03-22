
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
    </head>
    <body class="hold-transition skin-blue sidebar-mini">

        <div class="wrapper">

            <%@include file="jspf/field-officer-navbar.jspf"%>
            <%@include file="jspf/provincial-field-officer-sidebar.jspf"%>

            <%
            ARBO arbo = (ARBO) request.getAttribute("arbo");
            %>

            <div class="content-wrapper">
                <!-- Content Header (Page header) -->
                <section class="content-header">
                    <h1>
                        <strong><%out.print(arbo.getArboName());%></strong> 
                        <small><%out.print(arbo.getArboProvinceDesc());%>, <%out.print(arbo.getArboRegionDesc());%></small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="FO-Homepage.html"><i class="fa fa-dashboard"></i> Home</a></li>
                        <li class="active">(ARBO Name) Beneficiary List</li>
                    </ol>
                </section>

                <!-- Main content -->
                <section class="content">
                    <%if (request.getAttribute("errMessage") != null) {%>
                    <p class="text text-center text-danger"><%=request.getAttribute("errMessage")%></p>
                    <%}%>
                    <%if (request.getAttribute("success") != null) {%>
                    <p class="text text-center text-success"><%=request.getAttribute("success")%></p>
                    <%}%>
                    <div class='row'>
                        <div class='col-xs-6'>
                            <div class='box'>
                                <div class='box-header with-border'>
                                    <h3 class="box-title">Upload ARB List</h3>
                                </div>
                                <form role='form' method="post" action="ImportARB">
                                    <div class='box-body'>
                                        <div class="row">
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="fileExcel">Import ARB</label>
                                                    <input type='file' id='fileExcel' name="file">
                                                </div>
                                            </div>
                                        </div>

                                        <div class="box-footer">
                                            <input type="hidden" name="arboID" value="<%out.print(arbo.getArboID());%>">
                                            <button type="submit" name="import" class="btn btn-primary pull-right">Submit</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title">Agrarian Reform Beneficiaries (ARB)</h3>
                                </div>
                                <!-- /.box-header -->

                                <form role="form" method="post" action="AddARB">
                                    <div class="box-body">
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="firstName">First Name</label>
                                                    <input type="text" class="form-control" name="firstName" id="firstName" placeholder="Ex. Juan">
                                                </div>        
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="middleName">Middle Name</label>
                                                    <input type="text" class="form-control" name="middleName" id="middleName" placeholder="Ex. Lopez">
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="lastName">Last Name</label>
                                                    <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Ex. Cruz" required>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="Gender">Gender</label>
                                                    <select name="gender" id="Gender" class="form-control">
                                                        <option value="M">Male</option>
                                                        <option value="F">Female</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label>Member Since:</label>
                                                    <div class="input-group date">
                                                        <div class="input-group-addon">
                                                            <i class="fa fa-calendar"></i>
                                                        </div>
                                                        <input type="date" name="memberSince" class="form-control pull-right">
                                                    </div>
                                                </div>    
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="">ARBO Representative?</label>
                                                    <div class="radio">
                                                        <div class="row">
                                                            <div class="col-xs-2">
                                                                <label>
                                                                    <input type="radio" name="arboRep" value="Yes">
                                                                    Yes
                                                                </label>        
                                                            </div>
                                                            <div class="col-xs-2">
                                                                <label>
                                                                    <input type="radio" name="arboRep" value="No" checked>
                                                                    No
                                                                </label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">Unit No. Street</label>
                                                    <input type="text" class="form-control" name="arbUnitNumStreet" />
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <div class="form-group" id="barangayDiv">
                                                    <label for="">Barangay</label>
                                                    <select class="form-control" id="barangayDrop" name="barangay" style="width: 100%;" disabled required>

                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <div class="form-group" id="cityDiv">
                                                    <label for="">City</label>
                                                    <select class="form-control" id="cityDrop" name="arboCityMun" onchange="chg3()" style="width: 100%;" disabled required>

                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-xs-2">
                                                <div class="form-group" id="provinceDiv">
                                                    <label for="">Province</label>
                                                    <select class="form-control" id="provinceDrop" name="arboProvince" onchange="chg2()" style="width: 100%;" disabled required>

                                                    </select>
                                                </div>
                                            </div>
                                            <div class="col-xs-3">
                                                <div class="form-group">
                                                    <label for="">Region</label>
                                                    <select class="form-control" id="regionDrop" name="arbRegion" onchange="chg()" style="width: 100%;" required>
                                                        <%for(Region r: regionList){%>
                                                        <option value="<%out.print(r.getRegCode());%>"> <%out.print(r.getRegDesc());%> </option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label>Education Level</label>
                                                    <select name="educationalLevel" class="form-control" style="width: 100%;" required>
                                                        <option value="1">Primary</option>
                                                        <option value="2">Secondary</option>
                                                        <option value="3">Tertiary - Undergraduate</option>
                                                    </select>    
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label for="landArea">Land Area</label>
                                                    <input type="text" name="landArea" class="form-control" id="landArea" placeholder="Ex. 3 Acres">    
                                                </div>
                                            </div>
                                            <div class="col-xs-4">
                                                <div class="form-group">
                                                    <label>Crops</label>
                                                    <select id="select" class="form-control select2" name="crops[]" multiple="multiple">
                                                        <%for(Crop c : allCrops){%>
                                                        <option value="<%out.print(c.getCropType());%>"><%out.print(c.getCropTypeDesc());%></option>
                                                        <%}%>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>

                                        <button type="button" class="add_crop_button btn btn-primary" id="addCrop"><i class="fa fa-user-plus"></i> Add Crop</button>

                                        <div class="input_crops_wrap">

                                        </div>

                                        <br>

                                        <button type="button" class="add_field_button btn btn-primary" id="addDependent"><i class="fa fa-user-plus"></i> Add Dependent</button>

                                        <div class="input_fields_wrap" id="wrapper">

                                        </div>

                                    </div>
                                    <!-- /.box-body -->

                                    <div class="box-footer">
                                        <input type="hidden" name="arboID" value="<%out.print(arbo.getArboID());%>">
                                        <button type="submit" name="manual" class="btn btn-primary pull-right">Submit</button>
                                    </div>
                                </form>


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
        <script type="text/javascript">

            function chg() {
                var regionVal = document.getElementById('regionDrop').value;

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('provinceDiv').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "RegionRefresh?valajax=" + regionVal, true);
                xhttp.send();
            }

            function chg2() {
                var provinceVal = document.getElementById('provinceDrop').value;

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('cityDiv').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "ProvinceRefresh?valajax=" + provinceVal, true);
                xhttp.send();
            }

            function chg3() {
                var cityVal = document.getElementById('cityDrop').value;

                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                        document.getElementById('barangayDiv').innerHTML = xhttp.responseText;
                    }
                };
                xhttp.open("GET", "CityRefresh?valajax=" + cityVal, true);
                xhttp.send();
            }

            $(document).ready(function () {
                $('#regionDrop').on('change', function (e) {
                    $('#provinceDrop').removeAttr('disabled');
                });
                $('#provinceDrop').on('change', function (e) {
                    $('#cityDrop').removeAttr('disabled');
                });
                $('#cityDrop').on('change', function (e) {
                    $('#barangayDrop').removeAttr('disabled');
                });


                var max_fields = 3; //maximum input boxes allowed

                var wrapper = $(".input_fields_wrap"); //Fields wrapper
                var wrapper2 = $(".input_crops_wrap"); //Fields wrapper

                var add_button = $(".add_field_button"); //Add button ID
                var add_crop = $(".add_crop_button"); //Add button ID

                var x = 0; //initlal text box count
                var y = 0; //initlal text box count
                $(add_button).click(function (e) { //on add input button click
                    e.preventDefault();
                    if (x < max_fields) { //max input box allowed
                        x++; //text box increment
                        $(wrapper).append('<div class="row"><div class="col-xs-3"><div class="form-group"><label for="">Name</label><input type="text" name="dependentName[]" class="form-control" required></div></div><div class="col-xs-3"><label>Birthday</label><div class="input-group date"><div class="input-group-addon"><i class="fa fa-calendar"></i></div><input type="date" name="dependentBirthday[]" class="form-control pull-right" id="datepicker"></div></div><div class="col-xs-3"><div class="form-group"><label for="EL">Education Level</label><select name="dependentEL[]" class="form-control" id="EL" style="width:100%;"><%for(int i = 0; i < educationLevel.size(); i++){%><option value="<%=i%>"><%out.print(educationLevel.get(i));%></option><%}%></select></div></div><div class="col-xs-3"><div class="form-group"><label for="Re">Relationship</label><select name="dependentR[]" class="form-control" id="Re" style="width:100%;"><%for(int j = 0; j < relationshipType.size(); j++){%><option value="<%=j%>"><%out.print(relationshipType.get(j));%></option><%}%></select></div></div></div>');
                    }
                });

                $(add_crop).click(function (e) { //on add input button click
                    e.preventDefault();
                    if (y < max_fields) { //max input box allowed
                        y++; //text box increment
                        $(wrapper2).append('<div class="row"><div class="col-xs-4"><div class="form-group"><label for="Crop">Crop</label><select id="select" class="form-control" name="crops[]" style="width: 100%;"><%for(Crop c : allCrops){%><option value="<%out.print(c.getCropType());%>"><%out.print(c.getCropTypeDesc());%></option><%}%></select></div></div><div class="col-xs-4"><div class="form-group"><label for="">Start Date: </label><input type="date" name="startDate[]" class="form-control" required /></div></div><div class="col-xs-4"><div class="form-group"><label for="">End Date: </label><input type="date" name="endDate[]" class="form-control" /></div></div></div>');
                    }
                });
            });
        </script>
    </body>
</html>