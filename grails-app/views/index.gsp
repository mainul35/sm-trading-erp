<!doctype html>
<html>
<head>
    <meta name="layout" content="header"/>
    <title>${'login'}</title>
</head>

<div class="container login-container">
    <div class="row">
        <div class="col-md-6 login-form-2">
            <h3><g:message code="login"/></h3>
            <form class="form">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="${g.message(code: "email")}" value="" />
                </div>
                <div class="form-group">
                    <input type="password" class="form-control" placeholder="${g.message(code: "password")}" value="" />
                </div>
                <div class="form-group">
                    <input type="submit" class="btnSubmit" value="${g.message(code: "login")}" />
                </div>
                <div class="form-group">

                    <a href="#" class="ForgetPwd" value="Login"><g:message code="forgot.password"/></a>
                </div>
            </form>
        </div>
    </div>
</div>