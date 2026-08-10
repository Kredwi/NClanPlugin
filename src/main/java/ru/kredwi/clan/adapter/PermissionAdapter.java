package ru.kredwi.clan.adapter;

import cn.nukkit.permission.Permission;
import com.google.gson.*;
import ru.kredwi.clan.model.Clan;

import java.lang.reflect.Type;

public class PermissionAdapter implements JsonDeserializer<Permission>, JsonSerializer<Permission> {
    @Override
    public Permission deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new Permission(jsonElement.getAsString());
    }

    @Override
    public JsonElement serialize(Permission permission, Type type, JsonSerializationContext jsonSerializationContext) {
        return jsonSerializationContext.serialize(permission.getName());
    }
}
