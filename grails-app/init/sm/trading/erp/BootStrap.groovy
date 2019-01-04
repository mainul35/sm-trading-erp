package sm.trading.erp

import sm.trading.erp.auth.Role
import sm.trading.erp.auth.User
import groovy.time.TimeCategory

class BootStrap {

    def init = { servletContext ->

        User user = new User()
        user.name = "Syed Mainul Hasan"
        user.email = "mainuls18@gmail.com"
        user.password = "secret".encodeAsBase64()

        user.save(flush: true)
        log.info('user: {}', user.toString())
    }

    def destroy = {
    }

    public static void main(String[] args) {
        def currentDate = new Date()
        def after30Mins = new Date()
        use(TimeCategory){
            after30Mins = currentDate - 1.hour
        }
        println after30Mins
    }
}
