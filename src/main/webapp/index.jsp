<html>
<head>
    <%@include file="include/header.jsp"%>
                    password:$("#password").val()
</head>
<body>
    <div class="container">
        <form action="/addUser" method="post">
            <label for="username">Username:</label>
            <input id = "username" name="username" class="form-control" autocomplete="off">
            <label for="password">Password</label>
            <input id="password" name="password" class="form-control" autocomplete="off" type="password">
            <input type="submit">
        </form>
    </div>
</body>


</html>