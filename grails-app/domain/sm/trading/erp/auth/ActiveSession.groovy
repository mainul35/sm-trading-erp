package sm.trading.erp.auth

class ActiveSession {

    Long id
    User user
    String userAgent
    String IP
    String accessToken
    String activeSite
    Date dateCreated
    Date lastUpdated
    Date willDestroyAt = null
    ClientDetails clientDetails

    static constraints = {
    }
}
