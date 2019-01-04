package sm.trading.erp.auth

import sm.trading.erp.Permission

class User {
    String name
    String email
    String password
    Role role
    Collection<Permission> permissions

    static constraints = {
        name nullable: false, size: 2..30
        email nullable: false, size: 2..60
        password nullable: false
        role nullable: true
    }
}
