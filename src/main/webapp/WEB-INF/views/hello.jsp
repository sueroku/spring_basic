<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>hello</title>
</head>
<body>
    <p>data(EL문법) : ${myData}</p>
    <p>data(jstl문법-java코드(조금씩은 다르다.)) : <%
        String getData = (String)request.getAttribute("myData");
        out.print(getData);
    %></p>
</body>
</html>