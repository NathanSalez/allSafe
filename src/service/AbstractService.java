package service;

import java.util.HashMap;

/**
 * @author Nathan Salez
 */
public class AbstractService {

    public static final String DATABASE_FIELD = "databaseError";

    protected HashMap<String,String> errors;

    public AbstractService() {
        errors = new HashMap<String, String>();
    }

    public boolean getFeedback()
    {
        return errors.isEmpty();
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }
}
