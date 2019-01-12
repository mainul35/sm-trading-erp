package sm.trading.erp.auth

import grails.gorm.transactions.Transactional

class ClientDetailsService {

    ClientDetails findBy(String key, String value) {
        return ClientDetails.where {
            eq key, value
        }.first()
    }

    ClientDetails findByAppName(String appName) {
        return ClientDetails.findByClientId(clientId: clientId)
    }

    Integer count() {
        return ClientDetails.count()
    }
}
