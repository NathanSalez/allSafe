package model;

/**
 * @author Nathan Salez
 */
public enum Role {
    ADMIN("Administrator"),
    MODERATOR("Moderator"),
    SIMPLE("Simple user"),
    NOT_FOUND("Role not found");

    private String description;

    public static Role getRole(String nameRole)
    {
        switch(nameRole)
        {
            case "ADMIN" :
                return ADMIN;

            case "MODERATOR" :
                return MODERATOR;

            case "SIMPLE" :
                return SIMPLE;

            default :
                return NOT_FOUND;
        }
    }


    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
