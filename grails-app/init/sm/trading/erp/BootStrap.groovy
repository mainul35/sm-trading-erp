package sm.trading.erp

class BootStrap {

    def init = { servletContext ->

        User user = new User()
        user.name = "Syed Mainul Hasan"
        user.email = "mainuls18@gmail.com"
        user.password = "secret".encodeAsBase64()

        Role role = new Role()
        role.name = "ADMIN"
        user.roles.add(role)
        user.save(flush: true)
        log.info('role: {}', role.toString())
        log.info('user: {}', user.toString())
    }

    def destroy = {
    }
}
