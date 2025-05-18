<%@ page import="org.example.utils.AttributeConstants" %>
<%@ page import="org.example.utils.PagePathConstants" %>
<html>
<body>
    <%@include file="../header.jsp"%>
    <c:forEach var="movie" items="${requestScope[AttributeConstants.MOVIES]}">
        <div>
            <p><c:out value="${movie.name}"/></p>
            <a href="${PagePathConstants.BUY_TICKET}${movie.id}"><button>Buy Ticket</button></a>
            <a><button>Return Ticket</button></a>
        </div>
    </c:forEach>
</body>
</html>
