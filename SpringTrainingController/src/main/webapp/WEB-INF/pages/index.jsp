<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>

<script>
$(document).ready(function(){
    $.get("/SpringTrainingController/user/devs/service?username=bubuzzz&password=12345678&groupType=DEVELOPER", function (data){
      for(var i = 0; i < 10; i ++) {
        $(".userList").append("<tr><td>username</td></tr>");
      }
    });
});

</script>

</head>

<body>

<table class="userList"></table>
</body>
</html>