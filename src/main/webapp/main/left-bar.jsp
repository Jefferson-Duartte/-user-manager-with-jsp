<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="pcoded-navbar">
    <div class="sidebar_toggle"><a href="#"><i class="icon-close icons"></i></a></div>
    <div class="pcoded-inner-navbar main-menu">
        <div class="">
            <div class="main-menu-header">
                <c:choose>
                    <c:when test="${not empty sessionScope.imageUser}">
                        <img class="img-80 img-radius" src="${sessionScope.imageUser}" alt="User-Profile-Image">
                    </c:when>
                    <c:otherwise>
                        <img class="img-80 img-radius"
                             src="${pageContext.request.contextPath}/assets/images/patolino.jpg"
                             alt="User-Profile-Image">
                    </c:otherwise>
                </c:choose>

                <div class="user-details">
                    <span id="more-details"><%=session.getAttribute("user")%></span>
                </div>
            </div>


        </div>

        <div class="pcoded-navigation-label" data-i18n="nav.category.navigation"></div>
        <ul class="pcoded-item pcoded-left-item">
            <li class="active">
                <a href="<%=request.getContextPath()%>/main/main.jsp" class="waves-effect waves-dark">
                    <span class="pcoded-micon"><i class="ti-home"></i><b>D</b></span>
                    <span class="pcoded-mtext" data-i18n="nav.dash.main">Início</span>
                    <span class="pcoded-mcaret"></span>
                </a>
            </li>

        </ul>
        <div class="pcoded-navigation-label" data-i18n="nav.category.forms">Gerenc. de Usuários</div>
        <ul class="pcoded-item pcoded-left-item">
            <c:if test="${profile == 'ADMINISTRADOR'}">
                <li>
                    <a href="<%=request.getContextPath()%>/ServletUserController?urlAction=getUsers"
                       class="waves-effect waves-dark">
                        <span class="pcoded-micon"><i class="ti-plus"></i></span>
                        <span class="pcoded-mtext"
                              data-i18n="nav.form-components.main">Cadastrar Usuários</span>
                        <span class="pcoded-mcaret"></span>
                    </a>
                <li/>
            </c:if>
            <li>
                <a href="<%=request.getContextPath()%>/ServletUserController?urlAction=getUsers"
                   class="waves-effect waves-dark">
                    <span class="pcoded-micon"><i class="ti-user"></i></span>
                    <span class="pcoded-mtext"
                          data-i18n="nav.form-components.main">Ver Usuários</span>
                    <span class="pcoded-mcaret"></span>
                </a>
            <li/>

            <li>
                <a href="<%=request.getContextPath()%>/ServletUserController?urlAction=getUsers"
                   class="waves-effect waves-dark">
                    <span class="pcoded-micon"><i class="ti-clipboard"></i></span>
                    <span class="pcoded-mtext"
                          data-i18n="nav.form-components.main">Gerar Relatórios</span>
                    <span class="pcoded-mcaret"></span>
                </a>
            <li/>

        </ul>
    </div>
</nav>
