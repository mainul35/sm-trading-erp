package sm.trading.erp.auth

class ActiveSession {

    Long id
    User user
    String userAgent
    String IP
    String accessToken
    String activeSite
    Date created
    Date updated
    Date willDestroyAt


    static constraints = {
    }
}
