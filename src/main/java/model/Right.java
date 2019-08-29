package model;

/**
 * @author Nathan Salez
 */
public class Right {

    private Long id;

    private Role executorRole;

    private Role affectedRole;

    private String action;

    private Role newRole;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getExecutorRole() {
        return executorRole;
    }

    public void setExecutorRole(Role executorRole) {
        this.executorRole = executorRole;
    }

    public Role getAffectedRole() {
        return affectedRole;
    }

    public void setAffectedRole(Role affectedRole) {
        this.affectedRole = affectedRole;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Role getNewRole() {
        return newRole;
    }

    public void setNewRole(Role newRole) {
        this.newRole = newRole;
    }
}
