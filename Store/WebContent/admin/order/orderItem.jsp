<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="s" %>
<table width="100%">
	<s:iterator var="orderItem" value="list">
	<tr>
		<img width="40" height="45" src="${ pageContext.request.contextPath }/<s:property value="#orderItem.product.image"/>">	</tr>
		<tr><s:property value="#orderItem.product.pname"/></tr>
		<tr><s:property value="#orderItem.count"/></tr>
		<tr><s:property value="#orderItem.subtotal"/></tr>

	</s:iterator>
</table>