package sm.trading.erp

import grails.gorm.Entity
import sm.trading.erp.auth.ClientDetails
import sm.trading.erp.auth.Role
import sm.trading.erp.auth.User
import groovy.time.TimeCategory

class BootStrap {

    def init = { servletContext ->

        if (!User.findByEmail("mainuls18@gmail.com")) {
            User user = new User()
            user.name = "Syed Mainul Hasan"
            user.email = "mainuls18@gmail.com"
            user.password = "secret".encodeAsBase64()
            user.save(flush: true)
            log.info('User: {}', user.toString())
        }

        if (!ClientDetails.findAllByAppName('bismillah-app')) {
            ClientDetails clientDetails = new ClientDetails()
            clientDetails.appName = 'bismillah-app'
            clientDetails.fallbackUrl = 'http://localhost:3000/'
            clientDetails.clientId = UUID.randomUUID().encodeAsBase64()
            clientDetails.clientSecret = UUID.randomUUID().encodeAsBase64()
            clientDetails.save(flush: true, failOnError: true)
            log.info('Client Details: {}', clientDetails.toString())
        }
    }

    def destroy = {
    }

    public static void main(String[] args) {
        def currentDate = new Date()
        def after30Mins = new Date()
        use(TimeCategory) {
            after30Mins = currentDate - 1.hour
        }
        println after30Mins
    }

    def tenantInit = {
        Scheduler.schedule(c = {
        })
    }
}
