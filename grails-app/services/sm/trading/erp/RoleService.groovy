package sm.trading.erp

import grails.gorm.transactions.Transactional

@Transactional
class RoleService {

    def createRole(Role role) {
        role.authority = ('ROLE_'+role.authority.toUpperCase())
        return role.save(flush: true)
    }

    List<Role> getRolesMatchingWithIds(def ids){
        def roles = []
        for(def id : ids){
            roles.add(Role.findById(id.toLong()))
        }
        return roles
    }

    def getAllRoles(){
        def roles = Role.findAll();
    }

    def getRolesContainingUser(User user){
        Role.createCriteria().list {
            eq("users", [user])
        }
    }
}
