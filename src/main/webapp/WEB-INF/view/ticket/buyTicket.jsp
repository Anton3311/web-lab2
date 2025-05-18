<%@ page import="org.example.utils.AttributeConstants" %>
<%@ page import="org.example.utils.ParameterNameConstants" %>
<html>
<body>
    <%@include file="../header.jsp"%>
    <form method="post">
        <input type="text" name="${ParameterNameConstants.MOVIE_NAME}", placeholder="Movie Name">
        <input type="number" name="${ParameterNameConstants.SEAT_NUMBER}" placeholder="Seat Number">
        <button type="submit">Buy</button>
    </form>

    <c:if test="${requestScope[AttributeConstants.HAS_ERRORS]}">
        <p class="error">Cannot buy a ticket</p>
        <div>
            <c:forEach var="error" items="${requestScope[AttributeConstants.ERRORS]}">
                <p class="error"><c:out value="${error}" /></p>
            </c:forEach>
        </div>
    </c:if>
</body>
</html>
