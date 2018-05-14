<html>
<head>
    <%@include file="include/header.jsp"%>
    <script>
        $(document).ready(function () {
            loadData();
        });
        function loadData() {
            getavaAccounts()
        }
        function getavaAccounts() {
            $.ajax({
                url:"/user/count"
            }).done(function(data){
                $("#accounts").text(data);
            }).fail(function(){
                alert("Unknown error");
            })
        }
        function reg() {
            $.ajax({
                url:"/user",
                method:"POST",
                data:{
                    username:$("#username").val(),
                    password:$("#password").val()
                },
                dataType:"json"
            }).done(function (data) {
                $("#regmsg").text(data).attr("class","label-success");
            })
        }
    </script>
    <style>
        #submitbtn{
            margin-top: 10px;
        }
        #reg{
            border-right: solid 1px #000;
        }
        #dailyCalendar{
            border-top: solid 1px #000;
        }
        #accountStatus{
            float: right;
            margin-left: 5px;
            height: 200px;
        }
    </style>

</head>
<body>
<div id = "header" class="jumbotron">
    <!-- header -->
    <h1 class="display-3">Welcome to use Auto Booking</h1>
</div>
    <div class="container">
        <div class="row">
            <div id="reg" class="col-lg-4">
                <!--Reg area -->
                <form>
                    <label for="username">Username:</label>
                    <input id = "username" name="username" class="form-control" autocomplete="off">
                    <label for="password">Password:</label>
                    <input id="password" name="password" class="form-control" autocomplete="off" type="password">
                    <button id = "submitbtn" class="btn btn-primary" onclick="reg()">Register</button>
                    <span id = "regmsg"></span>
                </form>
            </div>
            <div id = "accountStatus" class="col-lg-7">
                <!-- Account status -->
                <div class="row">
                    <label>Avaliable account(s): <span id ="accounts">N/A</span></label>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Username</th>
                                <th scope="col">Status</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="label-success">
                                <td>1</td>
                                <td>ky******</td>
                                <td>Done</td>
                            </tr>
                            <tr class="label-success">
                                <td>1</td>
                                <td>ky******</td>
                                <td>Done</td>
                            </tr>
                            <tr class="label-success">
                                <td>1</td>
                                <td>ky******</td>
                                <td>Done</td>
                            </tr>
                            <tr class="label-success">
                                <td>1</td>
                                <td>ky******</td>
                                <td>Done</td>
                            </tr>
                            <tr class="label-success">
                                <td>1</td>
                                <td>ky******</td>
                                <td>Done</td>
                            </tr>
                            <tr class="label-success">
                                <td>1</td>
                                <td>ky******</td>
                                <td>Done</td>
                            </tr>


                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div id = "dailyCalendar" class="row">

        </div>
    </div>
</body>


</html>