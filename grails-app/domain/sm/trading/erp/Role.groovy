package sm.trading.erp

import grails.persistence.Entity
import org.springframework.security.core.GrantedAuthority

@Entity
class Role{

    String name
    static belongsTo = User
    Collection<Permission> permissions

    static constraints = {
        name nullable: false, unique: true
    }
}
