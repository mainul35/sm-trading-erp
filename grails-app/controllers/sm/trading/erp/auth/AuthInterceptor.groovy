package sm.trading.erp.auth

import grails.converters.JSON
import groovy.time.TimeCategory

import javax.servlet.http.Cookie

class AuthInterceptor {

    AuthService authService
    ClientDetailsService clientDetailsService

    AuthInterceptor() {
        matchAll()
                .excludes(controller: 'auth', action: 'login')
    }

    boolean before() {
        Map responseDataModel = [authenticated: false, message: '', statusCode: 404, token: '']
        // Check if the request header has client credentials
        if (request.xhr) {
            if (request.getHeader("client-details")) {
                def clientDetails = authService.decrypt(request.getHeader("client-details"))
                def clientCredentials = clientDetailsService.findBy('appName', clientDetails[0]?.appName)
                if (clientCredentials) {
                    if (clientCredentials.clientId == clientDetails[1]?.clientId &&
                            clientCredentials.clientSecret == clientDetails[2]?.clientSecret) {
                        params.appName = clientDetails[0].appName
                        // Check if the request param has an access token.
                        // If any, get the active session querying with the token.
                        if (request.getHeader("token")) {
                            String token = request.getHeader("token")
                            log.info("token: {}", token)
                            ActiveSession activeSession = session[token]
                            // Check validity of the access token
                            processActiveSession(activeSession, responseDataModel)
                        } else if (params?.key) { // An encrypted key which is a combination of username & password.
                            def authentication = authService.decrypt(params.key)
                            User user = authService.authenticate(authentication)
                            //If the user is valid
                            if (user) {
                                ActiveSession newActiveSession = new ActiveSession()
                                newActiveSession.user = user
                                newActiveSession.IP = request.getRemoteAddr()
                                newActiveSession.accessToken = UUID.randomUUID().encodeAsBase64()
                                newActiveSession.userAgent = request.getHeader("User-Agent")
                                newActiveSession.clientDetails = clientCredentials
                                newActiveSession.dateCreated = new Date()
                                session[newActiveSession.accessToken] = newActiveSession
                                responseDataModel.authenticated = true
                                responseDataModel.message = 'Authentication Successful.'
                                responseDataModel.statusCode = 200
                                responseDataModel.token = newActiveSession.accessToken
                                params.responseDataModel = responseDataModel
                                return true
                            } else {
                                responseDataModel.message = 'Invalid username or password.'
                                params.responseDataModel = responseDataModel
                                return false
                            }
                        } else {
                            responseDataModel.message = 'No provided key or token was found.'
                            params.responseDataModel = responseDataModel
                            return false
                        }
                    } else {
                        // Unrecognized Application
                        responseDataModel.message = 'Client authentication failed.'
                        params.responseDataModel = responseDataModel
                        return false
                    }
                } else {
                    responseDataModel.message = 'Unregistered app name.'
                    params.responseDataModel = responseDataModel
                    return false
                }
            } else {
                responseDataModel.message = 'No provided client details.'
                params.responseDataModel = responseDataModel
                return false
            }
        } else {
            if (request.requestURI == '/') {
                ActiveSession activeSession = findActiveSessionByCookie()
                if (activeSession) {
                    redirect(uri: '/management/dashboard')
                    return !processActiveSession(activeSession, responseDataModel)
                } else {
                    return true
                }
            }
            ActiveSession activeSession = findActiveSessionByCookie()
            boolean isSuccess = processActiveSession(activeSession, responseDataModel)
            if (isSuccess) {
                return true
            } else {
                redirect(uri: '/auth/login')
            }
        }
    }

    boolean after() {
        true
    }

    void afterView() {
        // no-op
    }

    ActiveSession findActiveSessionByCookie() {
        Cookie[] cookies = request.getCookies();
        if (cookies) {
            for (Cookie cookie in cookies) {
                if (cookie.name == 'token') {
                    return session[cookie.value]
                }
            }
        }
        return null
    }

    boolean processActiveSession(ActiveSession activeSession, Map responseDataModel) {
        if (activeSession) {
            use(TimeCategory) {
                if ((new Date() - activeSession.dateCreated) > 1.hour) {
                    session[activeSession.accessToken] = null
                    responseDataModel.message = "Your session has been expired."
                    params.responseDataModel = responseDataModel
                    return false
                } else {
                    // If user agent does not match, return with an exception
                    if (activeSession.userAgent != request.getHeader('User-Agent')) {
                        responseDataModel.message = "Invalid token."
                        params.responseDataModel = responseDataModel
                        return false
                    }
                    responseDataModel.authenticated = true
                    responseDataModel.message = 'Authentication Successful.'
                    responseDataModel.statusCode = 200
                    responseDataModel.token = activeSession.accessToken
                    params.responseDataModel = responseDataModel
                    return true
                }
            }
        } else {
            responseDataModel.message = "Your session has been expired."
            return false
        }
    }
}