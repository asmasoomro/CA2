<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CA 1</title>
</head>
<body>
    <form action= "http://localhost:8080/GreenhouseGases/restful-services/sampleservice/hello" method="GET">
        
        
        <input type="submit" value="Hello World Test">
    </form>
	
	  <form action= "http://localhost:8080/GreenhouseGases/restful-services/sampleservice/echo/message" method="GET">
        
        <input type="submit" value="Print Message ">
    </form>

	<form action= "http://localhost:8080/moneyLenders/restful-services/sampleservice/customers" method="GET">  
        <input type="submit" value="View All Customers">
    </form>
	
	<form action= "http://localhost:8080/moneyLenders/restful-services/sampleservice/json/customers" method="GET">  
        <input type="submit" value="View JSON All Customer ">
    </form>
	
	<form action= "http://localhost:8080/moneyLenders/restful-services/sampleservice/json/customer/1" method="GET">  
        <input type="submit" value="View JSON Customer 1">
    </form>
	
</body>
</html>