package sm.trading.erp.auth

import grails.converters.JSON
import groovy.time.TimeCategory

import javax.servlet.http.Cookie

class AuthInterceptor {

    AuthService authService

    AuthInterceptor() {
    }

    boolean before() {
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
                        return true
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


//    boolean before() {
//
//        List<Role> roles = []
//        grailsApplication.controllerClasses.each { controllerArtefact ->
//            def controllerClass = controllerArtefact.getClazz()
//
//            if (request.forwardURI.toLowerCase().contains(controllerArtefact.name.toLowerCase())) {
//                Class clazz = Class.forName(controllerArtefact.fullName)
//                StringTokenizer stk = new StringTokenizer(request.forwardURI, "/")
//                while (stk.hasMoreTokens()) {
//                    try {
//                        def method = clazz.getMethod(stk.nextToken())
//
//                        if (method.isAnnotationPresent(Authorized.class)) {
//                            Authorized authorized = method.getAnnotation(Authorized.class)
//                            authorized.hasRole()
//                            if (authorized.hasRole().equalsIgnoreCase("anonymous")) {
//                                log.info("Anonymous user accessed to " + method.name)
//                            } else {
//                                TokenStore tokenStore = TokenStore.findByAccessToken(params.accessToken)
//                                if(tokenStore){
//                                    roles = tokenStore.user?.roles*.name
//                                    if (
//                                    tokenStore.client.clientId.equals(params.clientId)
//                                            && tokenStore.client.clientSecret.equals(params.clientSecret)
//                                    ){
//                                        if (method.name.equals("logout")) {
//                                            tokenStoreService.destroyToken(tokenStore.accessToken)
//                                        } else {
//                                            def size = roles.size()
//                                            for (int i = 0; i < size; i++) {
//                                                if (authorized.hasRole().equalsIgnoreCase(roles.get(i))) {
//                                                    log.info(roles.get(i) + " accessed to " + method.name)
//                                                    break
//                                                }
//                                            }
//                                        }
//                                    } else {
//                                        throw new UnrecognizedClientException("Client not recognized!")
//                                    }
//                                }else{
//                                    throw new InvalidTokenException("Invalid token!")
//                                }
//                            }
//                        } else {
//                            log.info('Unauthorized')
//                            redirect(controller: "error", action: "unauthorized")
//                        }
//                    } catch (NoSuchMethodException e) {
//                    }
//                }
//            }
//        }
//        true
//    }
//
//    boolean after() { true }
//
//    void afterView() {
//        // no-op
//    }
//}