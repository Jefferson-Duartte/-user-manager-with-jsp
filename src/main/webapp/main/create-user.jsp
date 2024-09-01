<%@ page import="model.Login" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="pt-br">

<jsp:include page="head.jsp"/>
<style>
    .isFalse {
        display: none;
    }

</style>

<body>
<%--Pre-Loader--%>
<jsp:include page="pre-loader.jsp"/>


<div id="pcoded" class="pcoded">
    <div class="pcoded-overlay-box"></div>
    <div class="pcoded-container navbar-wrapper">
        <jsp:include page="navbar.jsp"/>
        <div class="pcoded-main-container">
            <div class="pcoded-wrapper">
                <jsp:include page="left-bar.jsp"/>
                <div class="pcoded-content">
                    <jsp:include page="page-header.jsp"/>
                    <div class="pcoded-inner-content">
                        <div class="main-body">
                            <div class="page-wrapper">
                                <div class="page-body">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="card">
                                                <div class="card-header">
                                                    <h5>Cadastre um novo usuário</h5>
                                                </div>

                                                <div class="card-block">
                                                    <form enctype="multipart/form-data" class="form-material" id="create_user_form"
                                                          action="<%=request.getContextPath()%>/ServletUserController"
                                                          method="post">
                                                        <input type="hidden" name="urlAction" id="urlAction"
                                                               value=""/>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="id" class="form-control" readonly
                                                                   value="${dataLogin.id}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">ID</label>
                                                        </div>
                                                        <div class="form-group" style="display: flex; flex-direction: column; gap: 5px">
                                                            <label style="display: block;" for="img-file">Foto de perfil:</label>
                                                            <c:if test="${dataLogin.photoUser != null && dataLogin.photoUser != ''}">
                                                                <img id="image-user" src="${dataLogin.photoUser}" width="70px">
                                                            </c:if>
                                                            <c:if test="${dataLogin.photoUser == null || dataLogin.photoUser == ''}">
                                                                <img id="image-user" src="assets/images/user-default.png" width="70px">
                                                            </c:if>

                                                            <input id="img-file" name="filePhoto" accept="image/*" onchange="changeImage('image-user', 'img-file')" type="file" class="form-control-file">
                                                        </div>
                                                        <div class="form-group form-default">
                                                            <input type="text" name="name" class="form-control" required
                                                                   value="${dataLogin.name}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Nome</label>
                                                        </div>
                                                        <div class="form-group form-default">
                                                            <input type="email" name="email"
                                                                   class="form-control" required
                                                                   value="${dataLogin.email}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">E-mail</label>
                                                        </div>

                                                        <div class="form-group form-default">
                                                            <input type="text" name="login"
                                                                   class="form-control" required
                                                                   value="${dataLogin.login}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Login</label>
                                                        </div>
                                                        <div class="form-group form-default">
                                                            <input type="password" name="password"
                                                                   class="form-control" required
                                                                   value="${dataLogin.password}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Senha</label>

                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <select class="form-select" name="profile" id="select"
                                                                    required>
                                                                <option selected disabled value="">Selecione um perfil
                                                                </option>
                                                                <option
                                                                        <c:if test="${dataLogin.profile == 'ADMINISTRADOR'}">selected</c:if>
                                                                        value="ADMINISTRADOR">Administrador(a)
                                                                </option>
                                                                <option
                                                                        <c:if test="${dataLogin.profile == 'AUXILIAR'}">selected</c:if>
                                                                        value="AUXILIAR">Auxiliar
                                                                </option>
                                                                <option
                                                                        <c:if test="${dataLogin.profile == 'SECRETARIO'}">selected</c:if>
                                                                        value="SECRETARIO">Secretário(a)
                                                                </option>
                                                            </select>
                                                        </div>
                                                        <div class="form-group form-default form-static-label">
                                                            <label>Sexo:</label>
                                                        <div class="form-check" style="display: flex; gap: 60px">
                                                            <div>
                                                                <input <c:if test="${dataLogin.gender == 'Masculino'}">checked</c:if>
                                                                        class="form-check-input" type="radio"
                                                                        name="gender" id="masculino" required checked value="Masculino">
                                                                <label class="form-check-label" style="padding-left: 0" for="masculino">
                                                                    Masculino
                                                                </label>
                                                            </div>

                                                            <div>
                                                                <input
                                                                        <c:if test="${dataLogin.gender == 'Feminino'}">checked</c:if>
                                                                        class="form-check-input" type="radio"
                                                                        name="gender" id="feminino" value="Feminino">
                                                                <label class="form-check-label" style="padding-left: 0" for="feminino">
                                                                    Feminino
                                                                </label>
                                                            </div>

                                                            </div>
                                                        </div>
                                                            <button onclick=cleanForm() type="button"
                                                                    class="btn btn-primary waves-effect waves-light">
                                                                Novo
                                                            </button>
                                                            <button type="submit"
                                                                    class="btn btn-success waves-effect waves-light">
                                                                Salvar
                                                            </button>
                                                            <button class="btn btn-warning waves-effect waves-light">
                                                                Editar
                                                            </button>
                                                            <button type="button" onclick=creteDelete()
                                                                    class="btn btn-danger waves-effect waves-light">
                                                                Excluir
                                                            </button>
                                                            <button type="button" class="btn btn-secondary"
                                                                    data-toggle="modal" data-target="#exampleModal">
                                                                Pesquisar
                                                            </button>
                                                                <%
                                                            String msg = (String) request.getAttribute("msg");
                                                            String style = "";
                                                            if (msg == null || msg == "null") {
                                                                msg = "";
                                                                style = "isFalse";
                                                            }

                                                        %>

                                                            <div class="alert alert-success mt-4 col-md-3  <%=style%>"
                                                                 role="alert" id="msg">
                                                                <%=msg%>
                                                            </div>
                                                    </form>
                                                    <div style="height: 300px; overflow-y: scroll">
                                                        <table class="table" id="table_all_users">
                                                            <thead style="position: sticky">
                                                            <tr>
                                                                <th scope="col">ID</th>
                                                                <th scope="col">Nome</th>
                                                                <th scope="col">Email</th>
                                                                <th scope="col">Perfil</th>
                                                                <th scope="col">Genêro</th>
                                                                <th scope="col">Visualizar</th>
                                                            </tr>
                                                            </thead>
                                                            <tbody>

                                                            <c:forEach var='user' items="${allUsers}">
                                                                <tr>
                                                                    <td><c:out value="${user.id}"/></td>
                                                                    <td><c:out value="${user.name}"/></td>
                                                                    <td><c:out value="${user.email}"/></td>
                                                                    <td><c:out value="${user.profile}"/></td>
                                                                    <td><c:out value="${user.gender}"/></td>
                                                                    <td>
                                                                        <a href="<%=request.getContextPath()%>/ServletUserController?urlAction=editSearch&id=${user.id}"
                                                                           class="btn btn-info">Ver</a></td>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
                     aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Buscar Usuários</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <div class="input-group mb-3">
                                    <input type="text" class="form-control" placeholder="Nome"
                                           aria-label="Nome" aria-describedby="basic-addon2" id="search">
                                    <div class="input-group-append">
                                        <button class="btn btn-success" type="button" onclick=searchUser()>Buscar
                                        </button>
                                    </div>
                                </div>

                                <div style="height: 300px; overflow-y: scroll">
                                    <table class="table" id="resultTable">
                                        <thead style="position: sticky">
                                        <tr>
                                            <th scope="col">ID</th>
                                            <th scope="col">Nome</th>
                                            <th scope="col">Visualizar</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="modal-footer" style="display: flex; justify-content: space-between">
                                <span id="totalResult"></span>
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <%--JavaScript imports--%>
                <jsp:include page="javascriptfile.jsp"/>


                <script>

                    function changeImage(imageUser, filePhoto){

                        let imageUserPreview = document.getElementById(imageUser);
                        let filePhotoInput = document.getElementById(filePhoto).files[0];
                        let reader = new FileReader();

                        reader.onloadend = function(){
                            imageUserPreview.src = reader.result
                        }

                        if(filePhotoInput){
                            reader.readAsDataURL(filePhotoInput);
                        }else{
                            imageUserPreview.src = "";
                        }

                    }

                    function searchUser() {
                        let name = document.getElementById("search").value;

                        if (name != null && name !== "" && name.trim() !== "") {

                            let urlAction = document.getElementById("create_user_form").action;
                            $.ajax({
                                method: "get",
                                url: urlAction,
                                data: "name=" + name + '&urlAction=searchUserAjax',
                                dataType: "json",
                                success: function (resp) {


                                    $('#resultTable > tbody > tr').remove();


                                    for (let i = 0; i < resp.length; i++) {
                                        $('#resultTable > tbody').append('<tr><td>' + resp[i].id + '</td><td>' + resp[i].name + '</td><td><button onclick=moreInfo(' + resp[i].id + ') type="button" class="btn btn-info">Ver</button></td> </tr>');

                                    }

                                    document.getElementById("totalResult").textContent = 'Resultados: ' + resp.length;


                                }

                            }).fail(function (xhr, status, errorThrown) {
                                console.error("Status: " + status);
                                console.error("Erro: " + errorThrown);
                                alert("Erro ao buscar usuário: " + xhr.responseText);
                            });
                        }

                    }

                    function moreInfo(id) {
                        let urlAction = document.getElementById("create_user_form").action;
                        window.location.href = urlAction + "?urlAction=editSearch&id=" + id

                    }


                    function creteDelete() {

                        if (confirm("Deseja realmente excluir?")) {
                            let form = document.getElementById("create_user_form");
                            form.method = "get";
                            document.getElementById("urlAction").value = "Delete";
                            form.submit();
                        }

                    }

                    function cleanForm() {
                        let elements = document.getElementById("create_user_form").elements;
                        document.getElementById("msg").style.display = "none";
                        for (let i = 0; i < elements.length; i++) {
                            if (elements[i].type !== "hidden") {
                                elements[i].value = "";
                            }
                        }
                        document.getElementById("select").selectedIndex = 0;
                        document.getElementById("masculino").checked  = true;
                        document.getElementById("image-user").src = "assets/images/user-default.png"
                    }

                </script>


</body>

</html>
