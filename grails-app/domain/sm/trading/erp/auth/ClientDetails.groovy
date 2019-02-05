package sm.trading.erp.auth

class ClientDetails {

    String clientId
    String clientSecret
    String appName
    String fallbackUrl
    Date dateCreated
    Date lastUpdated

    static constraints = {
        fallbackUrl nullable: true
    }
}
