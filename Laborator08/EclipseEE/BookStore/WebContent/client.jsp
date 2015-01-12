<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="ro.pub.cs.aipi.lab08.general.Constants" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
        <title><%= Constants.APPLICATION_NAME %></title>
        <link rel="stylesheet" type="text/css" href="css/bookstore.css" />
        <link rel="icon" type="image/x-icon" href="./images/favicon.ico" />
    </head>
    <body>
        <sql:setDataSource var="connection" url="<%= Constants.DATABASE_CONNECTION %>" user="<%= Constants.DATABASE_USERNAME %>" password="<%= Constants.DATABASE_PASSWORD %>" />
        <sql:query dataSource="${connection}" var="collections">
            SELECT DISTINCT(name) FROM series;
        </sql:query>        
        <sql:query dataSource="${connection}" var="domains">
            SELECT DISTINCT(name) FROM genre;
        </sql:query>
        <h2 style="text-align: center"><%= Constants.APPLICATION_NAME.toUpperCase() %></h2>
        <form action="ClientServlet" method="post" name="formular">
        	<p style="text-align:right">
	        	<%= Constants.WELCOME_MESSAGE %>${userDisplayName}
	        	<br/>
	        	<input type="image" name="<%= Constants.SIGNOUT.toLowerCase() %>" value="<%= Constants.SIGNOUT %>" src="./images/user_interface/signout.png" />
	        	<br/>
            </p>
	        <h2 style="text-align: center"><%= Constants.CLIENT_SERVLET_PAGE_NAME %></h2>
	        <table border="0" cellpadding="4" cellspacing="1" style="width: 100%; background-image: url(./images/user_interface/background.jpg); margin: 0px auto;">
	            <tbody>
	                <tr>
	                    <td style="width: 20%; text-align: left; vertical-align: top">
	                        <div id="wrapperrelative">
                                <div id="wrappertop"></div>
                                <div id="wrappermiddle">
	                                <table border="0" cellpadding="4" cellspacing="1">
	                                    <tbody>
	                                        <tr>
	                                            <td><%= Constants.COLLECTION %></td>
	                                            <td>
	                                                <select name="selectedCollection" onchange="document.formular.submit()" style="width: 100%">
	                                                    <option value="<%= Constants.ALL %>"
	                                                        <c:if test="${currentCollection eq '<%= Constants.ALL %>'}">
	                                                            selected="selected"
	                                                        </c:if>
	                                                    ><%= Constants.ALL %></option>
	                                                    <c:forEach var="collection" items="${collections.rows}">
	                                                        <option value="${collection['name']}" 
	                                                            <c:if test="${currentCollection eq collection['name']}">
	                                                                selected="selected"
	                                                            </c:if>
	                                                        >${collection['name']}</option>                                                    
	                                                    </c:forEach>
	                                                </select>       
	                                            </td>
	                                        </tr>
	                                        <tr><td colspan="2">&nbsp;</td></tr>
	                                        <tr>
	                                            <td><%= Constants.DOMAIN %></td>
	                                            <td>
	                                                <select name="selectedDomain" onchange="document.formular.submit()" style="width: 100%">
	                                                    <option value="<%= Constants.ALL %>"
	                                                        <c:if test="${currentDomain eq '<%= Constants.ALL %>'}">
	                                                            selected="selected"
	                                                        </c:if>
	                                                    ><%= Constants.ALL %></option>
	                                                    <c:forEach var="domain" items="${domains.rows}">
	                                                        <option value="${domain['name']}" 
	                                                            <c:if test="${currentDomain eq domain['name']}">
	                                                                selected="selected"
	                                                            </c:if>
	                                                        >${domain['name']}</option>
	                                                    </c:forEach>
	                                                </select> 
	                                            </td>
	                                        </tr>
	                                    </tbody>
                                    </table>
	                            </div>
	                            <div id="wrapperbottom"></div>
	                        </div>
	                    </td>
	                    <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	                    <td style="width: 60%; text-align: center">
	                        <c:if test="${not empty errorMessage}">
	                            ${errorMessage}
	                            <br />
	                            <br />
	                        </c:if>
	                        <c:set scope="request" var="whereClause" value="" />
	                        <c:set scope="request" var="all" value="<%= Constants.ALL %>" />
	                        <c:if test="${currentCollection != all}">
	                            <c:set scope="request" var="whereClause" value="${whereClause} series_id IN (SELECT ${collectionPrimaryKey} FROM series WHERE name='${currentCollection}')" />
	                        </c:if>
	                        <c:if test="${currentDomain != all}">
	                            <c:if test="${not empty whereClause}">
	                                <c:set scope="request" var="whereClause" value="${whereClause} AND "/>
	                            </c:if>
	                            <c:set scope="request" var="whereClause" value="${whereClause} genre_id IN (SELECT ${domainPrimaryKey} FROM genre WHERE name='${currentDomain}')" />
	                        </c:if>
	                        <sql:query dataSource="${connection}" var="books">
	                            SELECT (SELECT GROUP_CONCAT(CONCAT(w.first_name,' ',w.last_name)) FROM writer w, author a WHERE a.book_id=b.id AND w.id=a.writer_id) AS author, b.id, b.title, b.description, b.publishing_house_id, b.printing_year, b.edition, b.series_id, b.genre_id, b.stockpile, b.price FROM book b
                                <c:if test="${not empty whereClause}">
                                    WHERE ${whereClause}
                                </c:if>
	                            ;
	                        </sql:query>
	                        <table border="0" cellpadding="4" cellspacing="1" style="margin: 0px auto;">
	                            <tbody>
	                                <c:forEach var="book" items="${books.rows}">
	                                    <c:set var="currentPrimaryKey" scope="request" value="${book[primaryKey]}" />
	                                    <tr>
	                                        <td>
	                                            <div id="wrappertop"></div>
	                                            <div id="wrappermiddle">
		                                            <table border="0" cellpadding="4" cellspacing="4">
		                                                <tbody>
		                                                    <tr>
		                                                        <td style="vertical-align: top"><img src="./images/book_covers/${fn:toLowerCase(fn:replace(book['title'],' ',''))}.jpg" /></td>
		                                                        <td>&nbsp;</td>
		                                                        <td style="width: 100%; background: #ebebeb; text-align: left">                                                
		                                                            <c:choose>
		                                                                <c:when test="${empty book['author']}">
		                                                                    <%= Constants.AUTHORS %> -
		                                                                </c:when>
		                                                                <c:otherwise>
		                                                                    <%= Constants.AUTHORS %> ${book['author']}
		                                                                </c:otherwise>
		                                                            </c:choose>
		                                                            <br />
		                                                            <c:forEach var="attribute" items="${attributes}">
		                                                                <c:if test="${attribute != primaryKey}">
		                                                                    ${attribute}: ${book[attribute]}
		                                                                    <br />
		                                                                </c:if>
		                                                            </c:forEach>
		                                                        </td>
		                                                        <td>&nbsp;</td>
		                                                        <td style="vertical-align: middle;">
		                                                            <table>
		                                                                <tr>
		                                                                    <td>
		                                                                        <input type="text" name="<%= Constants.COPIES.toLowerCase() %>_${currentPrimaryKey}" size="3"/>
		                                                                        <br/>
		                                                                        <input type="image" name="<%= Constants.ADD_BUTTON_NAME.toLowerCase() %>_${currentPrimaryKey}" value="<%= Constants.ADD_BUTTON_NAME %>" src="./images/user_interface/add_to_shopping_cart.png" />
		                                                                    </td>
		                                                                </tr>
		                                                            </table>
		                                                        </td>
		                                                    </tr>
		                                                </tbody>
		                                            </table>
	                                            </div>
	                                            <div id="wrapperbottom"></div>
	                                        </td>
	                                    </tr>
		                                <tr>
		                                    <td>&nbsp;</td>
		                                </tr>
	                                </c:forEach>
	                            </tbody>
	                        </table>
	                    </td>
		                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	                    <td style="width: 20%; text-align: left; vertical-align: top;">
	                        <div id="wrappertop"></div>
	                        <div id="wrappermiddle">
		                        <table style="width: 100%;">
		                            <tr>
	                                    <td style="text-align: center;"><%= Constants.SHOPPING_CART %> ( ${fn:length(shoppingCart)} )</td>
		                            </tr>
		                            <c:choose>
	                                    <c:when test="${not empty shoppingCart}">
			                            <%-- TO DO (exercitiul 8): obtine continut cos de cumparaturi --%>
			                            <c:set var="shoppingCartValue" scope="request" value="0" />
				                            <tr>
				                                <td>		                                        
				                                    <table border="0" cellpadding="4" cellspacing="1" style="width: 100%; background: #ffffff;">
				                                        <tbody>
				                                            <c:forEach var="shoppingCartContent" items="${shoppingCart}">                                                
				                                                <sql:query dataSource="${connection}" var="bookprice">
				                                                    SELECT price FROM book WHERE id='${shoppingCartContent.attribute}';
				                                                </sql:query>
				                                                <c:set var="currentBookOrder" scope="request" value="${shoppingCartContent.value*bookprice.rows[0]['price']}" />
				                                                <c:set var="shoppingCartValue" scope="request" value="${shoppingCartValue+currentBookOrder}" />
				                                                <sql:query dataSource="${connection}" var="bookdetails">
				                                                    SELECT b.title, ph.name, b.printing_year FROM book b, publishing_house ph WHERE b.id='${shoppingCartContent.attribute}' AND ph.id=b.publishing_house_id;
				                                                </sql:query>                                                
				                                                <tr style="background: #ebebeb;">
				                                                    <td>
				                                                        ${shoppingCartContent.value} x ${bookdetails.rows[0]['title']}
				                                                        <br />&nbsp;&nbsp;&nbsp;&nbsp;(${bookdetails.rows[0]['name']}, ${bookdetails.rows[0]['printing_year']})
				                                                        <br />&nbsp;&nbsp;&nbsp;&nbsp;=${currentBookOrder}
				                                                    </td>
				                                                </tr>
				                                            </c:forEach>
				                                            <tr style="background: #ebebeb;"><td></td></tr>
				                                            <tr style="background: #ebebeb;"><td><%= Constants.ORDER_TOTAL %> <b>${shoppingCartValue}</b></td></tr>
				                                        </tbody>
				                                    </table>
				                                </td>
				                            </tr>
				                            <%-- TO DO (exercitiul 9): controale pentru anulare si finalizare comanda --%>                                   
	                                        <tr>
	                                            <td style="text-align: center;">
	                                                <c:set var="cancelCommand" scope="request" value="<%= Constants.CANCEL_COMMAND %>" />
	                                                <input type="image" name="${fn:replace(fn:toLowerCase(cancelCommand), ' ','')}" value="<%= Constants.CANCEL_COMMAND %>" src="./images/user_interface/remove_from_shopping_cart.png" />
	                                                &nbsp;&nbsp;
	                                                <c:set var="completeCommand" scope="request" value="<%= Constants.COMPLETE_COMMAND %>" />
	                                                <input type="image" name="${fn:replace(fn:toLowerCase(completeCommand),' ','')}" value="<%= Constants.COMPLETE_COMMAND %>" src="./images/user_interface/shopping_cart_accept.png" />
	                                            </td>
	                                        </tr>
	                                    </c:when>
				                        <c:otherwise>
				                            <tr>
				                                <td style="text-align: center;"><%= Constants.EMPTY_CART %></td>
				                            </tr>
				                        </c:otherwise>                                
				                    </c:choose>
		                        </table>
		                    </div>
		                    <div id="wrapperbottom"></div>
		                </td>
	                </tr>
	            </tbody>
	        </table>
        </form>
    </body>
</html>  