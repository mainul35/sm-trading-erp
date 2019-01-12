package sm.trading.erp.auth

import grails.gorm.transactions.Transactional
import sm.trading.erp.auth.ActiveSession

@Transactional
class ActiveSessionService {

    def activeSessionService(String token) {
        return ActiveSession.findByaccessToken(token)
    }

    def saveActiveSession(ActiveSession activeSession) {
        activeSession.save(flush: true, failOnError: true)
    }
}
