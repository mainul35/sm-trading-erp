package sm.trading.erp

import grails.persistence.Entity
import org.springframework.security.core.GrantedAuthority

@Entity
class Role implements GrantedAuthority{

    String authority
    static hasMany = [users: User]
    static constraints = {
        authority nullable: false, unique: false
        users nullable: true
    }

    Role(String authority) {
        this.authority = authority
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}
