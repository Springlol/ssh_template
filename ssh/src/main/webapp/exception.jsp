<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>页面出错</title>
    <style type="text/css">
        .top {
            width: 135px;
            height: 128px;
            margin: 20px auto;
        }

        p {
            text-align: center;
            font-family: '微软雅黑 ';
            font-size: 14px;
        }
    </style>
</head>

<body>
<div class="top"><img src="${pageContext.request.contextPath}/images/error.jpg"/></div>
<p>对不起，您访问的页面出错了！</p>
</body>
</html>