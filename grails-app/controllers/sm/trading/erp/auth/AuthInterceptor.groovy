package sm.trading.erp.auth

import grails.converters.JSON
import groovy.time.TimeCategory

import javax.servlet.http.Cookie

class AuthInterceptor {

    AuthService authService
    ClientDetailsService clientDetailsService

    AuthInterceptor() {
    }

    boolean before() {
        // Check if the request header has client credentials
        if(request.getHeader("client-details")){
            def clientDetails = authService.decrypt(request.getHeader("client-details"))
            def clientCredentials = clientDetailsService.findBy('appName', clientDetails[0]?.appName)
            if(clientCredentials){
                if (clientCredentials.clientId == clientDetails[0]?.clientId &&
                clientCredentials.clientSecret == clientDetails[0]?.clientSecret){
                    // Check if the request param has an access token.
                    // If any, get the active session querying with the token.
                    if (params?.token) {
                        log.info("token: {}", params.token)
                        def activeSession = authService.getActiveSession(params.token)
                        // Check validity of the access token
                        if (activeSession) {
                            use(TimeCategory){
                                if ((new Date() - activeSession.created) > 1.hour){
                                    activeSession.delete(flush: true)
                                    render([authenticated: false, message: "Your session has been expired."] as JSON)
                                    return false
                                }
                            }
                            if(params?.redirectUrl) {
                                if(params.redirectUrl == activeSession.activeSite) {
                                    render([authenticated: 'true', message: "Valid request."] as JSON)
                                    return true
                                } else {
                                    // If the redirect URL does not match, then we no longer provide support for the service.
                                    activeSession.delete(flush: true)
                                    render([authenticated: false, message: "We no longer provide support"] as JSON)
                                    return true
                                }
                            } else {
                                // If the token matches with default application
                                // Then take to management console
                                render(view: "/management/dashboard")
                                return true
                            }
                        }
                    } else if (params?.key) { // An encrypted key which is a combination of username & password.

                        def authentication = authService.decrypt(params.key)
                        def userAgent = request.getHeader("User-Agent")
                        User user = authService.authenticate(authentication)
                        //If the user is valid
                        if (user) {
                            ActiveSession newActiveSession = new ActiveSession()
                            newActiveSession.user = user
                            newActiveSession.IP = request.getRemoteAddr()
                            newActiveSession.accessToken = UUID.randomUUID().encodeAsBase64()
//                // If the user has any redirectUrl then generate a new token for that site.
//                // Else  generate a new token for default application.
//                if(authentication[2]?.name == 'redirectUrl'){
//                    newActiveSession.activeSite = authentication[2]?.value
//                }
                            newActiveSession.userAgent = request.getHeader("User-Agent")
                            authService.saveSession(newActiveSession)
                            if(authentication[2]?.name == 'redirectUrl'){
                                params.redirectUrl = authentication[2]?.value
                                params.token = newActiveSession.accessToken
                                return true
                            }else{
                                render(view: "/management/dashboard")
                            }
                        }
                    }
                }
            }
        }else {
            false
        }
        true
    }

    boolean after() {
//        Cookie cookie = new Cookie("token", "token")
//        cookie.setMaxAge(3600)
//        cookie.secure = true
//
//        response.addCookie(cookie)
        true
    }

    void afterView() {
        // no-op
    }

}