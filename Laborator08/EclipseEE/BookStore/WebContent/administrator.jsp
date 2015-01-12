<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ page import="ro.pub.cs.aipi.lab08.general.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
        <title><%= Constants.APPLICATION_NAME %></title>
        <link rel="stylesheet" type="text/css" href="css/bookstore.css" />
        <link rel="icon" type="image/x-icon" href="./images/favicon.ico" />
    </head>       
    <body style="text-align: center">
        <sql:setDataSource var="connection" url="<%= Constants.DATABASE_CONNECTION %>" user="<%= Constants.DATABASE_USERNAME %>" password="<%= Constants.DATABASE_PASSWORD %>" /> 
        <sql:query dataSource="${connection}" var="result">
            SELECT * FROM ${currentTableName};
        </sql:query>
        <h2><%= Constants.APPLICATION_NAME.toUpperCase() %></h2> 
        <form action="AdministratorServlet" method="post" name="formular"> 
            <p style="text-align: right">
                <%= Constants.WELCOME_MESSAGE %>${userDisplayName}
                <br />
                <input type="image" name="<%= Constants.SIGNOUT.toLowerCase() %>" value="<%= Constants.SIGNOUT %>" src="./images/user_interface/signout.png" />
                <br />
            </p>
            <h2><%= Constants.ADMINISTRATOR_SERVLET_PAGE_NAME %></h2>  
	        <p style="margin-left: auto; margin-right:auto">	                      
	            <select name="selectedTable" onchange="document.formular.submit()">
	            <c:forEach var="tableName" items="${dataBaseStructure}">
	                <c:choose>
	                    <c:when test="${tableName eq currentTableName}">
	                        <option value="${tableName}" selected="selected">${tableName}</option>
	                    </c:when>
	                    <c:otherwise>
	                        <option value="${tableName}">${tableName}</option>
	                    </c:otherwise>
	                </c:choose>
	            </c:forEach>
	            </select>
	        </p>
            <table style="background: #ffffff; margin: 0px auto;" border="0" cellpadding="4" cellspacing="1">
                <tbody>
	                <tr>
	                    <c:forEach var="attribute" items="${attributes}">
	                        <th>${attribute}</th>
	                    </c:forEach>
	                    <th colspan="2"><%= Constants.OPERATION_TABLE_HEADER %></th>
	                </tr>
	                <tr style="background:#ebebeb">
	                    <c:forEach var="attribute" items="${attributes}">
	                        <c:choose>
	                            <c:when test="${attribute eq primaryKey && currentTableName != 'utilizatori'}">
	                                <td><input type="text" name="${attribute}_<%= Constants.ADD_BUTTON_NAME.toLowerCase() %>" value="${primaryKeyMaxValue}" disabled="disabled" /></td>
	                            </c:when>
	                            <c:otherwise>
	                                <td><input type="text" name="${attribute}_<%= Constants.ADD_BUTTON_NAME.toLowerCase() %>" /></td>
	                            </c:otherwise>
	                        </c:choose>
	                    </c:forEach>
	                    <td style="text-align: center;" colspan="2"><input type="image" name="<%= Constants.ADD_BUTTON_NAME.toLowerCase() %>" value="<%= Constants.ADD_BUTTON_NAME %>" src="./images/user_interface/insert.png" /></td>
	                </tr>
	                <c:forEach var="row" items="${result.rows}">
	                    <tr style="background:#ebebeb">
	                        <c:set var="currentPrimaryKey" scope="request" value="${row[primaryKey]}" />
	                        <c:set var="isEditableRecord" scope="request" value="${not empty primaryKeyValue && primaryKeyValue eq currentPrimaryKey}" />
	                        <c:forEach var="attribute" items="${attributes}">
	                            <c:choose>
	                                <c:when test="${isEditableRecord}">                                       
	                                    <td>
	                                        <input type="text" name="${attribute}_${currentPrimaryKey}" value="${row[attribute]}" 
	                                            <c:if test="${attribute eq primaryKey && currentTableName != '<%= Constants.USERS_TABLE %>'}">
	                                                disabled
	                                            </c:if>
	                                        />
	                                    </td>
	                                </c:when>
	                                <c:otherwise>
	                                    <td>${row[attribute]}</td>
	                                </c:otherwise>
	                            </c:choose>                                
	                        </c:forEach>
	                        <c:choose>
	                            <c:when test="${isEditableRecord}">
	                                <c:set var="updateSuffix" scope="request" value="2" />
	                            </c:when>
	                            <c:otherwise>
	                                <c:set var="updateSuffix" scope="request" value="1" />
	                            </c:otherwise>
	                        </c:choose>
	                        <td><input type="image" name="<%= Constants.UPDATE_BUTTON_NAME.toLowerCase() %>${updateSuffix}_${currentPrimaryKey}" value="<%= Constants.UPDATE_BUTTON_NAME %>" src="./images/user_interface/update.png" /></td>
	                        <td><input type="image" name="<%= Constants.DELETE_BUTTON_NAME.toLowerCase() %>_${currentPrimaryKey}" value="<%= Constants.DELETE_BUTTON_NAME %>" src="./images/user_interface/delete.png" /></td>
	                    </tr>
	                </c:forEach>          
	            </tbody>
	        </table>
        </form>
    </body>
</html>  