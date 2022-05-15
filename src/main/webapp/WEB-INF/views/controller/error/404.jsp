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

<stripes:layout-render name="/WEB-INF/views/layout/error.jsp">

    <!-- content -->
    <stripes:layout-component name="contents">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-lg-6">
                    <div class="text-center mt-4">
                        <img class="mb-4 img-error"
                             src="<%=contextPath%>/resources/assets/img/error-404-monochrome.svg"/>
                        <p class="lead">This requested URL was not found on this server.</p>
                        <a href="home">
                            <i class="fas fa-arrow-left me-1"></i>
                            Return to Dashboard
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </stripes:layout-component>

    <!-- javascript -->
    <stripes:layout-component name="javascript"></stripes:layout-component>

</stripes:layout-render>