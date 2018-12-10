package sm.trading.erp

import grails.persistence.Entity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class User {
    String name
    String email
    String password
    Collection roles = []
    static hasMany = [roles: Role]

    static constraints = {
        name nullable: false, size: 2..30
        email nullable: false, size: 2..60
        password nullable: false
        roles nullable: true
    }
}
