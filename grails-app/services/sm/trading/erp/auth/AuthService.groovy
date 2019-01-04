package sm.trading.erp.auth

import grails.converters.JSON
import grails.gorm.transactions.Transactional
import grails.web.servlet.mvc.GrailsParameterMap
import sm.trading.erp.auth.ActiveSession
import sm.trading.erp.auth.User

import javax.servlet.http.HttpServletResponse

@Transactional
class AuthService {

    UserService userService
    ActiveSessionService activeSessionService

    def authenticate(def authentication) {
        if(authentication[0]?.name == 'email') {
            def user = userService.getUserByEmail(authentication[0].value)
            if (authentication[1]?.name == 'password') {
                if (user?.password == authentication[1]?.value.encodeAsBase64()) {
                    return user
                }
            }
        }
        throw new Exception()
    }

    ActiveSession getActiveSession(String accessToken){
        ActiveSession activeSession = ActiveSession.findByAccessToken(accessToken)
    }

    def decrypt(String key) {
        return JSON.parse(new String(key.decodeBase64()))
    }

    def saveSession(ActiveSession activeSession) {
        activeSessionService.saveActiveSession(activeSession)
    }

    def renderContent(HttpServletResponse response, String url){
        URL ur = new URL(url);
        URLConnection conn = ur.openConnection();
        InputStream is = conn.getInputStream();
        String foo = new Scanner(is).useDelimiter("\\A").next();
        response.setContentType("text/html");

        response.setStatus(HttpServletResponse.SC_OK);

        is.close()
        return foo
//        response.sendRedirect(url)
    }
}
