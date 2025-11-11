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
                                <table class="full-width">
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
                                                </tbody>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                        <td>
                            <div class="container data">
                                <table class="input_form full-width">
                                    <tr>
                                        <td>
                                            <h3>Ввод данных</h3>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label>Изменение X:</label>
                                            <div id="x-checkboxes">
                                                <label><input type="checkbox" name="x-checkbox" value="-2"> -2</label>
                                                <label><input type="checkbox" name="x-checkbox" value="-1.5"> -1.5</label>
                                                <label><input type="checkbox" name="x-checkbox" value="-1"> -1</label>
                                                <label><input type="checkbox" name="x-checkbox" value="-0.5"> -0.5</label>
                                                <label><input type="checkbox" name="x-checkbox" value="0"> 0</label>
                                                <label><input type="checkbox" name="x-checkbox" value="0.5"> 0.5</label>
                                                <label><input type="checkbox" name="x-checkbox" value="1"> 1</label>
                                                <label><input type="checkbox" name="x-checkbox" value="1.5"> 1.5</label>
                                                <label><input type="checkbox" name="x-checkbox" value="2"> 2</label>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label>Изменение Y:</label>
                                            <label for="y-data">
                                                <input type="text" id="y-data" placeholder="(-5 ... 3)" />
                                            </label>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <label>Изменение R:</label>
                                            <div class="button-group">
                                                <button class="r-btn" value="1" onclick="clickR(this)">1</button>
                                                <button class="r-btn" value="1.5" onclick="clickR(this)">1.5</button>
                                                <button class="r-btn" value="2" onclick="clickR(this)">2</button>
                                                <button class="r-btn" value="2.5" onclick="clickR(this)">2.5</button>
                                                <button class="r-btn" value="3" onclick="clickR(this)">3</button>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <button id="result_btn">Проверить</button>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                        <td>
                            <div class="container data full-width">
                                <table>
                                    <tr>
                                        <td>
                                            <h3>График</h3>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <svg style="width:100%" id="plot" viewBox="-250 -250 500 500">

                                                <line stroke="rgb(190, 195, 199)" stroke-width="2" x1="-230" y1="0" x2="230" y2="0" />
                                                <line stroke="rgb(190, 195, 199)" stroke-width="2" x1="0" y1="-230" x2="0" y2="230" />
                                                <text x="215" y="-10" fill="rgba(190, 195, 199)">X</text>
                                                <text x="10" y="-215" fill="rgba(190, 195, 199)">Y</text>

                                                <text x="-70" y="20" fill="rgba(190, 195, 199)">-1</text>
                                                <text x="-35" y="20" fill="rgba(190, 195, 199)">-0.5</text>
                                                <text x="70" y="20" fill="rgba(190, 195, 199)">1</text>
                                                <text x="35" y="20" fill="rgba(190, 195, 199)">0.5</text>

                                                <text x="5" y="70" fill="rgba(190, 195, 199)">-1</text>
                                                <text x="5" y="35" fill="rgba(190, 195, 199)">-0.5</text>
                                                <text x="5" y="-70" fill="rgba(190, 195, 199)">1</text>
                                                <text x="5" y="-35" fill="rgba(190, 195, 199)">0.5</text>

                                                <text x="-105" y="20" fill="rgba(190, 195, 199)">-1.5</text>
                                                <text x="105" y="20" fill="rgba(190, 195, 199)">1.5</text>

                                                <text x="5" y="105" fill="rgba(190, 195, 199)">-1.5</text>
                                                <text x="5" y="-105" fill="rgba(190, 195, 199)">1.5</text>

                                                <text x="-140" y="20" fill="rgba(190, 195, 199)">-2</text>
                                                <text x="140" y="20" fill="rgba(190, 195, 199)">2</text>

                                                <text x="5" y="140" fill="rgba(190, 195, 199)">-2</text>
                                                <text x="5" y="-140" fill="rgba(190, 195, 199)">2</text>

                                                <text x="-175" y="20" fill="rgba(190, 195, 199)">-2.5</text>
                                                <text x="175" y="20" fill="rgba(190, 195, 199)">2.5</text>

                                                <text x="5" y="175" fill="rgba(190, 195, 199)">-2.5</text>
                                                <text x="5" y="-175" fill="rgba(190, 195, 199)">2.5</text>

                                                <text x="-210" y="20" fill="rgba(190, 195, 199)">-3</text>
                                                <text x="210" y="20" fill="rgba(190, 195, 199)">3</text>

                                                <text x="5" y="210" fill="rgba(190, 195, 199)">-3</text>
                                                <text x="5" y="-210" fill="rgba(190, 195, 199)">3</text>

                                                <g id="r1" visibility="hidden">
                                                    <polygon points="0,0 -70,0 -70,35 0,35" fill="rgba(52, 152, 219, 0.6)" />
                                                    <polygon points="0,0 35,0 0,70" fill="rgba(52, 152, 219, 0.6)" />
                                                    <path d="M 0 0 L -70 0 A 70 70 0 0 1 0 -70 Z" fill="rgba(52, 152, 219, 0.6)"/>
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="-70" x2="0" y2="0" />
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="0" x2="35" y2="0" />
                                                    <line stroke="#3498db" stroke-width="1" x1="35" y1="0" x2="0" y2="70" />
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="70" x2="0" y2="35" />
                                                    <line stroke="#3498db" stroke-width="1" x1="-0" y1="35" x2="-70" y2="35" />
                                                    <line stroke="#3498db" stroke-width="1" x1="-70" y1="35" x2="-70" y2="0" />
                                                    <path d="M -70 0 A 70 70 0 0 1 0 -70" fill="none" stroke="#3498db" stroke-width="1"/>
                                                </g>

                                                <g id="r1.5" visibility="hidden">
                                                    <polygon points="0,0 -105,0 -105,52.5 0,52.5" fill="rgba(52, 152, 219, 0.6)" />
                                                    <polygon points="0,0 52.5,0 0,105" fill="rgba(52, 152, 219, 0.6)" />
                                                    <path d="M 0 0 L -105 0 A 105 105 0 0 1 0 -105 Z" fill="rgba(52, 152, 219, 0.6)"/>
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="-105" x2="0" y2="0" />
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="0" x2="52.5" y2="0" />
                                                    <line stroke="#3498db" stroke-width="1" x1="52.5" y1="0" x2="0" y2="105" />
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="105" x2="0" y2="52.5" />
                                                    <line stroke="#3498db" stroke-width="1" x1="-0" y1="52.5" x2="-105" y2="52.5" />
                                                    <line stroke="#3498db" stroke-width="1" x1="-105" y1="52.5" x2="-105" y2="0" />
                                                    <path d="M -105 0 A 105 105 0 0 1 0 -105" fill="none" stroke="#3498db" stroke-width="1"/>
                                                </g>

                                                <g id="r2" visibility="hidden">
                                                    <polygon points="0,0 -140,0 -140,70 0,70" fill="rgba(52, 152, 219, 0.6)" />
                                                    <polygon points="0,0 70,0 0,140" fill="rgba(52, 152, 219, 0.6)" />
                                                    <path d="M 0 0 L -140 0 A 140 140 0 0 1 0 -140 Z" fill="rgba(52, 152, 219, 0.6)"/>
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="-140" x2="0" y2="0" />
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="0" x2="70" y2="0" />
                                                    <line stroke="#3498db" stroke-width="1" x1="70" y1="0" x2="0" y2="140" />
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="140" x2="0" y2="70" />
                                                    <line stroke="#3498db" stroke-width="1" x1="-0" y1="70" x2="-140" y2="70" />
                                                    <line stroke="#3498db" stroke-width="1" x1="-140" y1="70" x2="-140" y2="0" />
                                                    <path d="M -140 0 A 140 140 0 0 1 0 -140" fill="none" stroke="#3498db" stroke-width="1"/>
                                                </g>

                                                <g id="r2.5" visibility="hidden">
                                                    <polygon points="0,0 -175,0 -175,87.5 0,87.5" fill="rgba(52, 152, 219, 0.6)" />
                                                    <polygon points="0,0 87.5,0 0,175" fill="rgba(52, 152, 219, 0.6)" />
                                                    <path d="M 0 0 L -175 0 A 175 175 0 0 1 0 -175 Z" fill="rgba(52, 152, 219, 0.6)"/>
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="-175" x2="0" y2="0" />
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="0" x2="87.5" y2="0" />
                                                    <line stroke="#3498db" stroke-width="1" x1="87.5" y1="0" x2="0" y2="175" />
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="175" x2="0" y2="87.5" />
                                                    <line stroke="#3498db" stroke-width="1" x1="-0" y1="87.5" x2="-175" y2="87.5" />
                                                    <line stroke="#3498db" stroke-width="1" x1="-175" y1="87.5" x2="-175" y2="0" />
                                                    <path d="M -175 0 A 175 175 0 0 1 0 -175" fill="none" stroke="#3498db" stroke-width="1"/>
                                                </g>

                                                <g id="r3" visibility="hidden">
                                                    <polygon points="0,0 -210,0 -210,105 0,105" fill="rgba(52, 152, 219, 0.6)" />
                                                    <polygon points="0,0 105,0 0,210" fill="rgba(52, 152, 219, 0.6)" />
                                                    <path d="M 0 0 L -210 0 A 210 210 0 0 1 0 -210 Z" fill="rgba(52, 152, 219, 0.6)"/>
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="-210" x2="0" y2="0" />
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="0" x2="105" y2="0" />
                                                    <line stroke="#3498db" stroke-width="1" x1="105" y1="0" x2="0" y2="210" />
                                                    <line stroke="#3498db" stroke-width="1" x1="0" y1="210" x2="0" y2="105" />
                                                    <line stroke="#3498db" stroke-width="1" x1="-0" y1="105" x2="-210" y2="105" />
                                                    <line stroke="#3498db" stroke-width="1" x1="-210" y1="105" x2="-210" y2="0" />
                                                    <path d="M -210 0 A 210 210 0 0 1 0 -210" fill="none" stroke="#3498db" stroke-width="1"/>
                                                </g>

                                                <% if (result != null) { %>
                                                    <% for (Point point : result.getPoints()) { %>
                                                        <circle cx="<%= point.getX() * 70 %>" cy="<%= -point.getY().doubleValue() * 70 %>" r="3"
                                                        fill="<%= point.isHit() ? "#2ecc71" : "#e74c3c" %>" stroke-width="1" />
                                                    <% } %>
                                                <% } %>

                                            </svg>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div id="error" class="error-message">
                                                <span id="errorText">Текст ошибки появится здесь</span>
                                            </div>
                                        </td>
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
