package sm.trading.erp

import grails.gorm.transactions.Transactional

@Transactional
class UserService {
    RoleService roleService
    def createUser(User user) {
        log.info 'in createUser method call: '

        if ((User.findByEmail(user.email) == null) && (User.findByUsername(user.username) == null)) {
            user.role.each {role->
                role.addToUsers(user)
                role.save(failOnError: true, flush: true)
            }
        } else {
            return false
        }
        return true
    }

    User getUserByUsername(String username) {
        User user1 = User.findByUsername(username)
        return user1
    }
}
