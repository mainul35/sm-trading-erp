package sm.trading.erp.auth

import grails.converters.JSON

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletResponse

class AuthController {

    AuthService authService

    def verify() {
        render(text: [url: params.redirectUrl, token: params.token] as JSON)
    }

    def login(){
        render(view: "login")
    }
}
