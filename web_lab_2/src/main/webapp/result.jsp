<%@ page import="dao.Result" %>
<%@ page import="dao.Point" %>
<%@ page pageEncoding="UTF-8" %>


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>web_lab_1</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<table class="main_table">
    <tr id="header">
        <td colspan="3">
            <div class="container">
                <table class="info_table">
                    <tr>
                        <td>
                            <p>Группа: P3216</p>
                        </td>
                        <td>
                            <h2>Петрова Полина Алексеевна</h2>
                        </td>
                        <td>
                            <p>Вариант: 321611</p>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>

    <tr>
        <td>
            <div class="content">
                <table class="data_table">
                    <tr>
                        <td>
                            <div class="container data">
                                <table>
                                    <tr>
                                        <td>
                                            <h3>Таблица результатов</h3>

                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table class="results_table" id="results-table">
                                                <thead>
                                                <tr>
                                                    <th>X</th>
                                                    <th>Y</th>
                                                    <th>R</th>
                                                    <th>Результат</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                    <td>
                                                        <% Result result = (Result) request.getSession().getAttribute("result");
                                                        if (result != null) {
                                                            for (Point point : result.getPoints()) {
                                                        %>
                                                        <tr>
                                                            <td>
                                                                <%= point.getX() %>
                                                            </td>
                                                            <td>
                                                                <%= point.getY() %>
                                                            </td>
                                                            <td>
                                                                <%= point.getR() %>
                                                            </td>
                                                            <td>
                                                                <%= point.isHit() ? "<span class=\"success\">hit</span>"
                                                                : "<span class=\"fail\">miss</span>" %>
                                                            </td>
                                                        </tr>
                                                        <% } %>
                                                        <% } %>
                                                    </td>
                                                </tbody>
                                            </table>
                                        </td>
                                        <tr>
                                            <td>
                                                <button id="gotoForm">
                                                    <a href="index.jsp">Вернуться к форме</a>
                                                </button>
                                            </td>
                                        </tr>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>
<script src="main.js">
</script>
</body>
</html>
