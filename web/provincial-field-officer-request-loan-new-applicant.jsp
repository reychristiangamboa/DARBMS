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
                        <strong>APCP</strong> 
                        <small>Region I</small>
                    </h1>
                    <ol class="breadcrumb">
                        <li><a href="provincial-field-officer-arbo-list.jsp"><i class="fa fa-dashboard"></i> ARBO List</a></li>
                        <li class="active"><a href="provincial-field-officer-request-loan.jsp">Request Loan (Qualified)</a></li>
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
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="box">
                                <div class="box-header with-border">
                                    <h3 class="box-title"><strong>Request for Loan</strong></h3>

                                </div>
                                <!-- /.box-header -->
                                <form role="form" method="post" action="RequestLoanAccess">
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
                                                        <input type='text' name="loan" class="form-control numberOnly" autocomplete="off" maxlength="4" >
                                                    </div>
                                                </div>

                                                <div class="col-xs-4">
                                                    <div class="form-group">
                                                        <label for="">Reason for Loan</label>
                                                        <input type="text" class="form-control" name="reason" />
                                                    </div>
                                                </div>       
                                            </div>
                                            <div class="row">
                                                <div class="col-xs-12">
                                                    <label for=''>Remarks</label>
                                                    <textarea class="form-control" name="remarks" rows="2" placeholder="Remarks"></textarea>
                                                </div>

                                            </div>
                                        </div>

                                        <div class="box-footer">
                                            <input type="hidden" name="arboID" value="<%out.print(arboID);%>">
                                            <button type="submit" class="btn btn-primary pull-right">Submit</button>
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
        </div>
        <!-- /.content-wrapper -->

        <%@include file="jspf/footer.jspf" %>
        <script>
            $('#datepicker').datepicker({
                autoclose: true
            });

            $('.money > div').click(function () {
                $('.money > input:eq(' + $('.money > div').index(this) + ')').focus();
            });

            $('.numberOnly').on('keydown', function (e) {

                if (this.selectionStart || this.selectionStart == 0) {
                    // selectionStart won't work in IE < 9

                    var key = e.which;
                    var prevDefault = true;

                    var thouSep = ",";  // your seperator for thousands
                    var deciSep = ".";  // your seperator for decimals
                    var deciNumber = 2; // how many numbers after the comma

                    var thouReg = new RegExp(thouSep, "g");
                    var deciReg = new RegExp(deciSep, "g");

                    function spaceCaretPos(val, cPos) {
                        /// get the right caret position without the spaces

                        if (cPos > 0 && val.substring((cPos - 1), cPos) == thouSep)
                            cPos = cPos - 1;

                        if (val.substring(0, cPos).indexOf(thouSep) >= 0) {
                            cPos = cPos - val.substring(0, cPos).match(thouReg).length;
                        }

                        return cPos;
                    }

                    function spaceFormat(val, pos) {
                        /// add spaces for thousands

                        if (val.indexOf(deciSep) >= 0) {
                            var comPos = val.indexOf(deciSep);
                            var int = val.substring(0, comPos);
                            var dec = val.substring(comPos);
                        } else {
                            var int = val;
                            var dec = "";
                        }
                        var ret = [val, pos];

                        if (int.length > 3) {

                            var newInt = "";
                            var spaceIndex = int.length;

                            while (spaceIndex > 3) {
                                spaceIndex = spaceIndex - 3;
                                newInt = thouSep + int.substring(spaceIndex, spaceIndex + 3) + newInt;
                                if (pos > spaceIndex)
                                    pos++;
                            }
                            ret = [int.substring(0, spaceIndex) + newInt + dec, pos];
                        }
                        return ret;
                    }

                    $(this).on('keyup', function (ev) {

                        if (ev.which == 8) {
                            // reformat the thousands after backspace keyup

                            var value = this.value;
                            var caretPos = this.selectionStart;

                            caretPos = spaceCaretPos(value, caretPos);
                            value = value.replace(thouReg, '');

                            var newValues = spaceFormat(value, caretPos);
                            this.value = newValues[0];
                            this.selectionStart = newValues[1];
                            this.selectionEnd = newValues[1];
                        }
                    });

                    if ((e.ctrlKey && (key == 65 || key == 67 || key == 86 || key == 88 || key == 89 || key == 90)) ||
                            (e.shiftKey && key == 9)) // You don't want to disable your shortcuts!
                        prevDefault = false;

                    if ((key < 37 || key > 40) && key != 8 && key != 9 && prevDefault) {
                        e.preventDefault();

                        if (!e.altKey && !e.shiftKey && !e.ctrlKey) {

                            var value = this.value;
                            if ((key > 95 && key < 106) || (key > 47 && key < 58) ||
                                    (deciNumber > 0 && (key == 110 || key == 188 || key == 190))) {

                                var keys = {// reformat the keyCode
                                    48: 0, 49: 1, 50: 2, 51: 3, 52: 4, 53: 5, 54: 6, 55: 7, 56: 8, 57: 9,
                                    96: 0, 97: 1, 98: 2, 99: 3, 100: 4, 101: 5, 102: 6, 103: 7, 104: 8, 105: 9,
                                    110: deciSep, 188: deciSep, 190: deciSep
                                };

                                var caretPos = this.selectionStart;
                                var caretEnd = this.selectionEnd;

                                if (caretPos != caretEnd) // remove selected text
                                    value = value.substring(0, caretPos) + value.substring(caretEnd);

                                caretPos = spaceCaretPos(value, caretPos);

                                value = value.replace(thouReg, '');

                                var before = value.substring(0, caretPos);
                                var after = value.substring(caretPos);
                                var newPos = caretPos + 1;

                                if (keys[key] == deciSep && value.indexOf(deciSep) >= 0) {
                                    if (before.indexOf(deciSep) >= 0)
                                        newPos--;
                                    before = before.replace(deciReg, '');
                                    after = after.replace(deciReg, '');
                                }
                                var newValue = before + keys[key] + after;

                                if (newValue.substring(0, 1) == deciSep) {
                                    newValue = "0" + newValue;
                                    newPos++;
                                }

                                while (newValue.length > 1 && newValue.substring(0, 1) == "0" && newValue.substring(1, 2) != deciSep) {
                                    newValue = newValue.substring(1);
                                    newPos--;
                                }

                                if (newValue.indexOf(deciSep) >= 0) {
                                    var newLength = newValue.indexOf(deciSep) + deciNumber + 1;
                                    if (newValue.length > newLength) {
                                        newValue = newValue.substring(0, newLength);
                                    }
                                }

                                newValues = spaceFormat(newValue, newPos);

                                this.value = newValues[0];
                                this.selectionStart = newValues[1];
                                this.selectionEnd = newValues[1];
                            }
                        }
                    }

                    $(this).on('blur', function (e) {

                        if (deciNumber > 0) {
                            var value = this.value;

                            var noDec = "";
                            for (var i = 0; i < deciNumber; i++)
                                noDec += "0";

                            if (value == "0" + deciSep + noDec) {
                                this.value = ""; //<-- put your default value here
                            } else if (value.length > 0) {
                                if (value.indexOf(deciSep) >= 0) {
                                    var newLength = value.indexOf(deciSep) + deciNumber + 1;
                                    if (value.length < newLength) {
                                        while (value.length < newLength)
                                            value = value + "0";
                                        this.value = value.substring(0, newLength);
                                    }
                                } else
                                    this.value = value + deciSep + noDec;
                            }
                        }
                    });
                }
            });

            $('.price > input:eq(0)').focus();
        </script>

    </body>
</html>
