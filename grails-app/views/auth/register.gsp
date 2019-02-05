<!doctype html>
<html>
<head>
    <meta name="layout" content="header"/>
    <title>${'login'}</title>
</head>

<div id="root">
    <div class="container login-container">
        <div class="row">
            <div class="col-md-6 login-form-2">
                <h3><g:message code="login"/></h3>

                <form class="form" to-url="/auth/verify" onsubmit="return false">
                    <div class="form-group">
                        <input type="email" name="email" class="form-control" placeholder="${g.message(code: "email")}"
                               value=""/>
                    </div>

                    <div class="form-group">
                        <input type="password" name="password" class="form-control"
                               placeholder="${g.message(code: "password")}" value=""/>
                    </div>

                    <div class="form-group">
                        <input type="submit" class="btnSubmit" value="${g.message(code: "login")}"/>
                    </div>

                    <div class="form-group">

                        <a href="#" class="ForgetPwd" value="Login"><g:message code="forgot.password"/></a>
                    </div>
                    <input type="hidden" name="redirectUrl" value="127.0.0.1:3000/"/>
                </form>
            </div>
        </div>
    </div>
</div>

<script>

</script>