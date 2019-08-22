package model;

/**
 * @author Nathan Salez
 */
// TODO : include class Right in model package.
// TODO : include enum Role in database.
public enum Role {
    ADMIN("Administrator"),
    MODERATOR("Moderator"),
    SIMPLE("Simple user");

    private String description;

    public static Role getRole(String nameRole)
    {
        switch(nameRole)
        {
            case "ADMIN" :
                return ADMIN;

            case "MODERATOR" :
                return MODERATOR;

            default :
                return SIMPLE;
        }
    }


    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
