package model.builder;

import model.Right;
import model.Role;

import java.util.List;

public class RoleBuilder {

    private final Role role;

    public RoleBuilder() {
        role = new Role();
    }

    public RoleBuilder setId(long id) {
        role.setId(id);
        return this;
    }

    public RoleBuilder setRole(String roleTxt) {
        role.setRole(roleTxt);
        return this;
    }

    public RoleBuilder setRights(List<Right> rights) {
        role.setRights(rights);
        return this;
    }

    public Role build() {
        return role;
    }
}
