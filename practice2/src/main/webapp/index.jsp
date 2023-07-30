<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>IBM Emp</title>
</head>
<body>
    <div>
        <h2>IBM Emp</h2>
    </div>
    <div>
        <h3>員工資料查詢</h3>
        <c:if test="${not empty errorMsgs}">
            <font color='red'>請修正以下錯誤:
                <ul>
                    <c:forEach var="message" items="${errorMsgs}">
                        <li>${message}</li>
                    </c:forEach>
                </ul>
            </font>
        </c:if>
        <ul>
            <li>
                <a href='${pageContext.request.contextPath}/emp/emp.do?action=listAll'>List</a> all Emps.
            </li>
            <br>

            <li>
                <form method="POST" action="${pageContext.request.contextPath}/emp/emp.do" >
                    <b>輸入員工編號 (如7001):</b>
                    <input type="text" name="empno">
                    <input type="submit" value="送出">
                    <input type="hidden" name="action" value="getOne_For_Display">
                </form>
            </li>

            <li>
                <form method="POST" action="${pageContext.request.contextPath}/emp/emp.do" >
                    <b>選擇員工編號:</b>
                    <select size="1" name="empno">
                        <c:forEach var="empDO" items="${empDOs}" >
                            <option value="${empDO.empno}">${empDO.empno}</option>
                        </c:forEach>
                    </select>
                    <input type="submit" value="送出">
                    <input type="hidden" name="action" value="getOne_For_Display">
                </form>
            </li>

            <li>
                <form method="POST" action="${pageContext.request.contextPath}/emp/emp.do" >
                    <b>選擇員工姓名:</b>
                    <select size="1" name="empno">
                        <c:forEach var="empDO" items="${empDOs}" >
                            <option value="${empDO.empno}">${empDO.ename}</option>
                        </c:forEach>
                    </select>
                    <input type="submit" value="送出">
                    <input type="hidden" name="action" value="getOne_For_Display">
                </form>
            </li>

            <li>
                <form method="POST" action="${pageContext.request.contextPath}/dept/dept.do" >
                    <b><font color=blue>選擇部門:</font></b>
                    <select size="1" name="deptno">
                        <c:forEach var="deptDO" items="${deptDOs}" >
                            <option value="${deptDO.deptno}">${deptDO.dname}
                        </c:forEach>
                    </select>
                    <input type="submit" value="送出">
                    <input type="hidden" name="action" value="listEmps_ByDeptno_A">
                </form>
            </li>
        </ul>
    </div>
    <div>
        <h3>員工管理</h3>
        <ul>
            <li><a href='${pageContext.request.contextPath}/emp/emp.do?action=add'>Add</a> a new Emp.</li>
        </ul>
    </div>
    <div>
        <h3><font color=blue>部門管理</font></h3>
        <ul>
            <li><a href='${pageContext.request.contextPath}/dept/dept.do?action=listAll'>List</a> all Depts. </li>
        </ul>
    </div>
</body>
</html>
