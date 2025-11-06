package org.jhipster.projectintern.service.dto;

import org.jhipster.projectintern.domain.User;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String login;
    private Collection<String> roles; // New property for roles

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }


    public UserDTO(User user) {
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.login = user.getLogin();
    }

    public UserDTO(User user, Collection<? extends GrantedAuthority> authorities) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.roles = authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()); // Collect roles into a list
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Collection<String> getRoles() {
        return roles;
    }

    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserDTO userDTO = (UserDTO) o;
        if (userDTO.getId() == null || getId() == null) {
            return false;
        }

        return Objects.equals(getId(), userDTO.getId()) &&
            Objects.equals(getLogin(), userDTO.getLogin()) &&
            Objects.equals(getRoles(), userDTO.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getRoles());
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserDTO{" +
            "id='" + id + '\'' +
            ", login='" + login + '\'' +
            ", roles='" + roles + '\'' +
            "}";
    }
}
