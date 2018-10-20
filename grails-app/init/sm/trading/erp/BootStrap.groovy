package sm.trading.erp

class BootStrap {

    def init = { servletContext ->
        Role role
        if (!Role.findByAuthority('ROLE_ADMIN')) {
            role = new Role(authority: 'ROLE_ADMIN').save(failOnError: true, flush: true)
        } else {
            role = Role.findByAuthority('ROLE_ADMIN')
        }
        User user = new User('mainul35', 'secret')

        user.email = "mainuls18@gmail.com"
        user.firstName = 'Syed Mainul'
        user.lastName = 'Hasan'
        user.position = 'Admin'
        user.contact = '+8801634440004'
        user.address = 'Avijan 9/2, Academy Road, College Gate, Tongi, Gazipur'
        user.joiningDate = new Date()
        user.role = role
        if (!User.findByEmail('mainuls18@gmail.com')) {
            user.save(flush: true, failOnError: true)
        }

        log.info('role: {}', role.toString())
        log.info('user: {}', user.toString())
    }

    def destroy = {
    }
}
