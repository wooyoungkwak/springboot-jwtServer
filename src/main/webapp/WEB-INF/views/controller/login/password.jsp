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
                        <div class="card-header"><h3 class="text-center font-weight-light my-4">Password Recovery</h3>
                        </div>
                        <div class="card-body">
                            <div class="small mb-3 text-muted">Enter your email address and we will send you a link to
                                reset your password.
                            </div>
                            <form>
                                <div class="form-floating mb-3">
                                    <input class="form-control" id="inputEmail" type="email"
                                           placeholder="name@example.com"/>
                                    <label for="inputEmail">Email address</label>
                                </div>
                                <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                    <a class="small" href="login">Return to login</a>
                                    <a class="btn btn-primary" href="login">Reset Password</a>
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
<%--        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>--%>
<%--        <script src="<%=contextPath%>/resources/js/scripts.js"></script>--%>
    </stripes:layout-component>

</stripes:layout-render>