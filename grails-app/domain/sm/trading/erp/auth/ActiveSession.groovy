package sm.trading.erp.auth

class ActiveSession {

    Long id
    User user
    String userAgent
    String IP
    String accessToken
    String activeSite = ''
    Date dateCreated
    Date lastUpdated
    Date willDestroyAt = new Date(0, 0, 0, 1, 0)
    ClientDetails clientDetails

    static constraints = {
        willDestroyAt nullable: true
    }
}
