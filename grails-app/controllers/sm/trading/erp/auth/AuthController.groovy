package sm.trading.erp.auth

import grails.converters.JSON

import javax.servlet.http.Cookie

class AuthController {

    AuthService authService

    def verify() {
        if (params.responseDataModel.authenticated == false) {
            redirect(
                    controller: 'auth',
                    action: 'login',
                    model: [
                            responseData: params.responseDataModel
                    ] as JSON)
        } else {
            Cookie cookie = new Cookie("token", params?.responseDataModel?.token ?: '')
            cookie.setPath('/')
            cookie.setSecure(true)
            response.addCookie(cookie)
            render([responseData: params.responseDataModel] as JSON)
        }
    }

    def login() {
        render(view: "login")
    }
}
