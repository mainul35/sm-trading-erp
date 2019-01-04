package sm.trading.erp.auth

import sm.trading.erp.Permission

class Role{

    String name
    static belongsTo = User
    Collection<Permission> permissions

    static constraints = {
        name nullable: false, unique: true
    }
}
