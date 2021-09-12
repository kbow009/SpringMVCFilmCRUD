<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="C"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Result</title>
</head>
<body>
 <c:choose>
    <c:when test="${! empty film}">
    <ul>
		<li><h5>${film}</h5></li> 
        <li>${film.title}</li>
        <li>${film.description}</li>
      </ul>
</c:when>
 <c:otherwise>
 <p>No film found</p>
</c:otherwise>
  </c:choose>




</body>
</html>