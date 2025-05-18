<%@ page import="org.example.utils.AttributeConstants" %>
<%@ page import="org.example.utils.ParameterNameConstants" %>
<html>
<body>
    <%@include file="../header.jsp"%>

    <h2><c:out value="Return ticket to ${requestScope[AttributeConstants.MOVIE_NAME]}" /></h2>

    <form method="post">
        <input type="number" name="${ParameterNameConstants.SEAT_NUMBER}" placeholder="Seat Number">
        <button type="submit">Return ticket</button>
    </form>

    <c:if test="${requestScope[AttributeConstants.HAS_ERRORS]}">
        <p class="error">Cannot return a ticket</p>
        <div>
            <c:forEach var="error" items="${requestScope[AttributeConstants.ERRORS]}">
                <p class="error"><c:out value="${error}" /></p>
            </c:forEach>
        </div>
    </c:if>
</body>
</html>
