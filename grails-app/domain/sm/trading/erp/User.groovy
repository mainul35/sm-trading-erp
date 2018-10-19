package sm.trading.erp

import grails.persistence.Entity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
class User implements UserDetails {
    long id
    String username
    String password
    boolean enabled
    String firstName
    String lastName
    String email
    String contact
    String position
    String address
    String profileImage
    Date joiningDate
    Date leavingDate
    boolean accountExpired = false
    boolean accountLocked = false
    boolean passwordExpired = false
    Role role
    static belongsTo = Role

    static constraints = {
        firstName nullable: false, blank: false
        lastName nullable: true, blank: true
        username nullable: true, blank: true, unique: true
        email unique: true, nullable: false, blank: false, email: true
        password nullable: false, blank: false
        address nullable: true
        position nullable: false, blank: false
        contact nullable: false, blank: false
        joiningDate nullable: true
        leavingDate nullable: true
        profileImage nullable: true, blank: true
        role nullable: true
    }

    static mapping = {
        version false
    }

    User(String username, String password) {
        this.username = username
        this.password = password
    }


    Collection<? extends GrantedAuthority> getAuthorities() {
        return [role]
    }

    @Override
    boolean isAccountNonExpired() {
        return false
    }

    @Override
    boolean isAccountNonLocked() {
        return false
    }

    @Override
    boolean isCredentialsNonExpired() {
        return false
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        User user = (User) o

        if (accountExpired != user.accountExpired) return false
        if (accountLocked != user.accountLocked) return false
        if (enabled != user.enabled) return false
        if (id != user.id) return false
        if (org_grails_datastore_gorm_GormValidateable__skipValidate != user.org_grails_datastore_gorm_GormValidateable__skipValidate) return false
        if (passwordExpired != user.passwordExpired) return false
        if (address != user.address) return false
        if (contact != user.contact) return false
        if (email != user.email) return false
        if (firstName != user.firstName) return false
        if (joiningDate != user.joiningDate) return false
        if (lastName != user.lastName) return false
        if (leavingDate != user.leavingDate) return false
        if (org_grails_datastore_gorm_GormValidateable__errors != user.org_grails_datastore_gorm_GormValidateable__errors) return false
        if (org_grails_datastore_mapping_dirty_checking_DirtyCheckable__$changedProperties != user.org_grails_datastore_mapping_dirty_checking_DirtyCheckable__$changedProperties) return false
        if (password != user.password) return false
        if (position != user.position) return false
        if (profileImage != user.profileImage) return false
        if (role != user.role) return false
        if (username != user.username) return false
        if (version != user.version) return false

        return true
    }

    int hashCode() {
        int result
        result = (int) (id ^ (id >>> 32))
        result = 31 * result + (username != null ? username.hashCode() : 0)
        result = 31 * result + (password != null ? password.hashCode() : 0)
        result = 31 * result + (enabled ? 1 : 0)
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0)
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0)
        result = 31 * result + (email != null ? email.hashCode() : 0)
        result = 31 * result + (contact != null ? contact.hashCode() : 0)
        result = 31 * result + (position != null ? position.hashCode() : 0)
        result = 31 * result + (address != null ? address.hashCode() : 0)
        result = 31 * result + (profileImage != null ? profileImage.hashCode() : 0)
        result = 31 * result + (joiningDate != null ? joiningDate.hashCode() : 0)
        result = 31 * result + (leavingDate != null ? leavingDate.hashCode() : 0)
        result = 31 * result + (accountExpired ? 1 : 0)
        result = 31 * result + (accountLocked ? 1 : 0)
        result = 31 * result + (passwordExpired ? 1 : 0)
        result = 31 * result + (version != null ? version.hashCode() : 0)
        result = 31 * result + (role != null ? role.hashCode() : 0)
        result = 31 * result + (org_grails_datastore_mapping_dirty_checking_DirtyCheckable__$changedProperties != null ? org_grails_datastore_mapping_dirty_checking_DirtyCheckable__$changedProperties.hashCode() : 0)
        result = 31 * result + (org_grails_datastore_gorm_GormValidateable__skipValidate ? 1 : 0)
        result = 31 * result + (org_grails_datastore_gorm_GormValidateable__errors != null ? org_grails_datastore_gorm_GormValidateable__errors.hashCode() : 0)
        return result
    }

    @Override
    public String toString() {
        return "User{" +
                ", role=" + role +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                ", position='" + position + '\'' +
                ", address='" + address + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", joiningDate=" + joiningDate +
                ", leavingDate=" + leavingDate +
                ", accountExpired=" + accountExpired +
                ", accountLocked=" + accountLocked +
                ", passwordExpired=" + passwordExpired +
                '}';
    }
}
