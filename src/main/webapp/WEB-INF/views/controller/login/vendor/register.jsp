<%--
  Created by IntelliJ IDEA.
  User: zilet
  Date: 2022-03-02
  Time: 오후 7:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://stripes.sourceforge.net/stripes.tld" prefix="stripes" %>
<% String contextPath = request.getContextPath(); %>

<stripes:layout-render name="/WEB-INF/views/layout/authentication.jsp">

    <!-- content -->
    <stripes:layout-component name="contents">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-7">
                    <div class="card shadow-lg border-0 rounded-lg mt-5">
                        <div class="card-header"><h3 class="text-center font-weight-light my-4">Create Account</h3>
                        </div>
                        <div class="card-body">

                            <form id="FormLogin" method="post" action="/register">
                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3 mb-md-0">
                                            <input class="form-control" name="firstName"
                                                   id="inputFirstName" type="text"
                                                   placeholder="Enter your first name"/>
                                            <label for="inputFirstName">FirstName</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating">
                                            <input class="form-control" name="lastName"
                                                   id="inputLastName" type="text"
                                                   placeholder="Enter your last name"/>
                                            <label for="inputLastName">LastName</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-floating mb-3">
                                    <input class="form-control" name="userName"
                                           id="inputEmail" type="text"
                                           placeholder="name@example.com"/>
                                    <label for="inputEmail">Account</label>
                                </div>
                                <div class="row mb-3">
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3 mb-md-0">
                                            <input class="form-control" name="password"
                                                   id="inputPassword"
                                                   type="password"
                                                   placeholder="Create a password"/>
                                            <label for="inputPassword">Password</label>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-floating mb-3 mb-md-0">
                                            <input class="form-control"
                                                   id="inputPasswordConfirm"
                                                   type="password"
                                                   placeholder="Confirm password"/>
                                            <label for="inputPasswordConfirm">Confirm
                                                Password</label>
                                        </div>
                                    </div>
                                </div>
                                <div class="mt-4 mb-0">
                                    <div class="d-grid"><a
                                            class="btn btn-primary btn-block"
                                            id="BtnRegister">Create
                                        Account</a></div>
                                </div>
                            </form>

                                <%--                            <form id="FormLogin" method="post" action="/register">--%>
                                <%--                                <div class="row mb-3">--%>
                                <%--                                    <div class="col-md-6">--%>
                                <%--                                        <div class="form-floating">--%>
                                <%--                                            <input type="email" class="form-control" id="inputAccount"--%>
                                <%--                                                   placeholder="계정(Email)">--%>
                                <%--                                            <label for="inputAccount" class="form-label">계정(Email)</label>--%>
                                <%--                                        </div>--%>
                                <%--                                    </div>--%>
                                <%--                                    <div class="col-md-6">--%>
                                <%--                                        <div class="form-floating">--%>
                                <%--                                            <input type="password" class="form-control" id="inputPassword"--%>
                                <%--                                                   placeholder="비밀번호">--%>
                                <%--                                            <label for="inputPassword" class="form-label">비밀번호</label>--%>
                                <%--                                        </div>--%>
                                <%--                                    </div>--%>
                                <%--                                </div>--%>
                                <%--                                <div class="row mb-3">--%>
                                <%--                                    <div class="col-md-12">--%>
                                <%--                                        <div class="form-floating">--%>
                                <%--                                            <input type="text" class="form-control" id="inputAddress"--%>
                                <%--                                                   placeholder="주소 예> 광주 광역시 남구 군분로">--%>
                                <%--                                            <label for="inputAddress" class="form-label">주소 예> 광주 광역시 남구 군분로</label>--%>
                                <%--                                        </div>--%>
                                <%--                                    </div>--%>
                                <%--                                </div>--%>

                                <%--                                <div class="row mb-3">--%>
                                <%--                                    <div class="col-12">--%>
                                <%--                                        <div class="form-floating">--%>
                                <%--                                            <input type="text" class="form-control" id="inputAddress2"--%>
                                <%--                                                   placeholder="상세 주소 - 건물, 사무실, 또는 층수">--%>
                                <%--                                            <label for="inputAddress2" class="form-label">상세 주소 - 건물, 사무실, 또는 층수</label>--%>
                                <%--                                        </div>--%>
                                <%--                                    </div>--%>
                                <%--                                </div>--%>

                                <%--                                <div class="row mb-3">--%>
                                <%--                                    <div class="col-md-5">--%>
                                <%--                                        <div class="form-floating">--%>
                                <%--                                            <input type="text" class="form-control" id="inputCity" placeholder="시/군/구">--%>
                                <%--                                            <label for="inputCity" class="form-label">시/군/구</label>--%>
                                <%--                                        </div>--%>
                                <%--                                    </div>--%>
                                <%--                                    <div class="col-md-4">--%>
                                <%--                                        <select id="inputState" class="form-select">--%>
                                <%--                                            <option selected>동/리 선택...</option>--%>
                                <%--                                            <option>...</option>--%>
                                <%--                                        </select>--%>
                                <%--                                    </div>--%>
                                <%--                                    <div class="col-md-3">--%>
                                <%--                                        <div class="form-floating">--%>
                                <%--                                            <input type="text" class="form-control" id="inputZip" placeholder="우편번호">--%>
                                <%--                                            <label for="inputZip" class="form-label">우편번호</label>--%>
                                <%--                                        </div>--%>
                                <%--                                    </div>--%>
                                <%--                                </div>--%>

                                <%--                                <div class="row mb-3">--%>
                                <%--                                    <div class="col-md-10"></div>--%>
                                <%--                                    <div class="col-md-2">--%>
                                <%--                                        <button type="submit" class="btn btn-primary">등록</button>--%>
                                <%--                                    </div>--%>
                                <%--                                </div>--%>

                                <%--                            </form>--%>

                        </div>
                        <div class="card-footer text-center py-3">
                            <div class="small"><a href="login">Have an account? Go to login</a></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </stripes:layout-component>

    <!-- javascript -->
    <stripes:layout-component name="javascript">
        <%--        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>--%>
        <%--        <script src="<%=contextPath%>/resources/js/scripts.js"></script>--%>
        <script type="text/javascript">
            $(document).ready(function () {
                let $btnRegister = $('#BtnRegister');

                $btnRegister.on('click', function () {

                    if ($('#inputPassword').val() == $('#inputPasswordConfirm').val()) {
                        $.ajaxJJ('/register', {
                            'username': $('#inputEmail').val(),
                            'password': $('#inputPassword').val(),
                            'firstName': $('#inputFirstName').val(),
                            'lastName': $('#inputLastName').val()
                        }, function (data) {
                            log("success data = " + data);
                            location.href = '/login';
                        }, function () {
                            log("error data = " + data);
                            location.href = '/register';
                        });
                    } else {
                        alert("비밀 번호와 확인 비밀 번호가 동일하지 않습니다.");
                    }
                });
            });
        </script>
    </stripes:layout-component>

</stripes:layout-render>