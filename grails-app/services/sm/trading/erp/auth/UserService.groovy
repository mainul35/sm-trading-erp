package sm.trading.erp.auth

import grails.gorm.transactions.Transactional

class UserService {
    RoleService roleService
    def createUser(User user) {
        log.info "Creating user..."
        if ((User.findByEmail(user.email) == null)) {
            user.save(flush: true)
        } else {
            return false
        }
        return true
    }

    def assignRoles(Collection<Role> roles, String email){
        User user = getUserByEmail(email)
        roles.each {r->
            user.roles.add(r)
        }
    }

    User getUserByEmail(String email) {
        User user = User.find {
            eq 'email', email
        }
        return user
    }
}
