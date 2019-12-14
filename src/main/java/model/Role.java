package model;

import java.util.Objects;

/**
 * @author Nathan Salez
 */
public class Role {

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Role() {
    }

    public Role(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public Role(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return code.equals(role.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }


    @Override
    public String toString() {
        return description;
    }
}
