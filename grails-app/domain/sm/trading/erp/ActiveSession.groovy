package sm.trading.erp

class ActiveSession {

    Long id
    User user
    String currentRole
    String userAgent
    String IP
    String usedToken
    String activeSite

    Integer tokenMaxAge


    static constraints = {
    }
}
