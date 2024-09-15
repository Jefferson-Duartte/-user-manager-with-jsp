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
                                                    <h5>Gráfico de Salário</h5>
                                                </div>

                                                <form class="form-material"
                                                      action="<%= request.getContextPath()%>/ServletUserController"
                                                      method="get">

                                                    <input type="hidden" name="urlAction" id="urlAction"
                                                           value="generateGraph"/>

                                                    <div class="form-row align-items-center">
                                                        <div class="col-auto">
                                                            <label class="sr-only" for="start_date">Data
                                                                Inicial</label>
                                                            <input type="date" class="form-control mb-2"
                                                                   name="start_date" value="${startDate}"
                                                                   id="start_date" placeholder="Data Inicial">
                                                        </div>
                                                        <div class="col-auto">
                                                            <label class="sr-only"
                                                                   for="end_date">Data Final</label>
                                                            <div class="input-group mb-2">
                                                                <input type="date" class="form-control"
                                                                       id="end_date" name="end_date" value="${endDate}"
                                                                       placeholder="Data Final">
                                                            </div>
                                                        </div>
                                                        <div class="col-auto">
                                                            <button type="button" onclick="generateGraph()"
                                                                    class="btn btn-primary mb-2">Gerar
                                                                gráfico
                                                            </button>
                                                        </div>
                                                    </div>
                                                </form>

                                                <div>
                                                    <canvas id="myChart"></canvas>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <jsp:include page="javascriptfile.jsp"/>
                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                <script>

                    function generateGraph() {

                        var myChart = new Chart(
                            document.getElementById('myChart'),
                            {
                                type: 'line',
                                data: {
                                    labels: [
                                        'January',
                                        'February',
                                        'March',
                                        'April',
                                        'May',
                                        'June',
                                    ],
                                    datasets: [{
                                        label: 'My First dataset',
                                        backgroundColor: 'rgb(255, 99, 132)',
                                        borderColor: 'rgb(255, 99, 132)',
                                        data: [0, 10, 5, 2, 20, 30, 45],
                                    }]
                                },
                                options: {}
                            }
                        )
                    }
                </script>
</body>


</html>
