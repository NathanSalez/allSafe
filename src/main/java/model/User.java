package model;

import java.util.Objects;

import java.util.Date;

/**
 * @author Nathan Salez
 */
public class User implements Comparable<User> {

    private Long id;

    private String pseudo;

    private String password;

    private Role role;

    private Date registerDate;

    private boolean logged;

    public User() {
    }

    public User(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getRegisterDate() { return registerDate; }

    public void setRegisterDate(Date registerDate) { this.registerDate = registerDate; }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return pseudo.equals(user.pseudo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pseudo);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(User o) {
        return this.pseudo.compareTo(o.pseudo);
    }
}
