package json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import model.Role;

import java.lang.reflect.Type;

/**
 * @author Nathan Salez
 */
public class RoleSerializer implements JsonSerializer<Role> {

    @Override
    public JsonElement serialize(Role role, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonRole = new JsonObject();
        jsonRole.addProperty("name",role.getCode());
        jsonRole.addProperty("description",role.getDescription());
        return jsonRole;
    }
}
