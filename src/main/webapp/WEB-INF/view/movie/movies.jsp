<%@ page import="org.example.utils.AttributeConstants" %>
<html>
<body>
    <%@include file="../header.jsp"%>
    <c:forEach var="movie" items="${requestScope[AttributeConstants.MOVIES]}">
        <div>
            <p><c:out value="${movie.name}"/></p>
            <a href="/cinema/buyTicket/${movie.id}"><button>Buy Ticket</button></a>
            <a href="/cinema/returnTicket/${movie.id}"><button>Return Ticket</button></a>
        </div>
    </c:forEach>
</body>
</html>
