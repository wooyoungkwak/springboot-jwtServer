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
                <div class="col-lg-5">
                    <div class="card shadow-lg border-0 rounded-lg mt-5">
                        <div class="card-header"><h3 class="text-center font-weight-light my-4">Login</h3></div>
                        <div class="card-body">

                            <form method="post" id="FormLogin" action="/loginProcess">
                                <div class="form-floating mb-3">
                                    <input class="form-control" name="username" id="inputEmail" type="email" placeholder="young"/>
                                    <label for="inputEmail">Account</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input class="form-control" name="password" id="inputPassword" type="password" placeholder="qwer1234"/>
                                    <label for="inputPassword">Password</label>
                                </div>
                                <div class="form-check mb-3">
                                    <input class="form-check-input" id="inputRememberPassword" type="checkbox"
                                           value=""/>
                                    <label class="form-check-label" for="inputRememberPassword">Remember
                                        Password</label>
                                </div>
                                <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                    <a class="small" href="password">Forgot Password?</a>
                                    <a class="btn btn-primary" id="BtnLogin">Login</a>
                                </div>
                            </form>

                        </div>
                        <div class="card-footer text-center py-3">
                            <div class="small"><a href="register">Need an account? Sign up!</a></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </stripes:layout-component>

    <!-- javascript -->
    <stripes:layout-component name="javascript">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script type="text/javascript">
            $(document).ready(function () {

                let $form = $('#FormLogin');
                let $btnLogin = $('#BtnLogin');

                $btnLogin.on('click', function () {
                    $form.submit();
                });

                $('#inputPassword').on('keydown', function (e) {
                    if (e.key == 'Enter') { // Enter key
                        $form.submit();
                    }
                });

                let url = location.href;
                let path = url.split('?');
                if (path.length > 1) {
                    let state = path[1].split('=')[1];

                    if (state == 'fail') {
                        alert('인증 실패 하였습니다.');
                    }
                    location.href = path[0];
                }

            });
        </script>

    </stripes:layout-component>

</stripes:layout-render>