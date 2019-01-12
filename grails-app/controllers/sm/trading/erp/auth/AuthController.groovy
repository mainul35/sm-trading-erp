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
            render([responseData: params.responseDataModel] as JSON)
        }
    }

    def login() {
        render(view: "login")
    }

    def logout() {
        session[params.responseDataModel.token] = null
        redirect(controller: 'auth', action: 'login')
    }
}
