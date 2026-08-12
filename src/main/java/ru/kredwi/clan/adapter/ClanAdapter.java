package ru.kredwi.clan.adapter;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import ru.kredwi.clan.model.Clan;
import ru.kredwi.clan.model.ClanSettings;
import ru.kredwi.clan.model.ClanStats;
import ru.kredwi.clan.model.Member;
import ru.kredwi.clan.role.Role;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ClanAdapter implements JsonDeserializer<Clan>, JsonSerializer<Clan> {
    public static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Clan.class, new ClanAdapter())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    @Override
    public Clan deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext ctx) throws JsonParseException {
        JsonObject obj = jsonElement.getAsJsonObject();
        UUID id = UUID.fromString(obj.get("id").getAsString());
        UUID ownerId = UUID.fromString(obj.get("ownerId").getAsString());
        String name = obj.get("name").getAsString();

        ClanStats clanStats = ctx.deserialize(obj.get("stats"), ClanStats.class);
        ClanSettings settings = ctx.deserialize(obj.get("settings"), ClanSettings.class);
        Map<UUID, Member> members = ctx.deserialize(obj.get("members"),
                new TypeToken<Map<UUID, Member>>() {
                }.getType());

        List<Role> roles = ctx.deserialize(obj.get("roles"), new TypeToken<List<Role>>() {
        }.getType());

        return Clan.of(id, ownerId, name, clanStats, settings, members, roles);
    }

    @Override
    public JsonElement serialize(Clan clan, Type type, JsonSerializationContext obj) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", clan.getId().toString());
        jsonObject.addProperty("ownerId", clan.getOwnerId().toString());
        jsonObject.addProperty("name", clan.getName());
        jsonObject.add("settings", obj.serialize(clan.getSettings()));
        jsonObject.add("stats", obj.serialize(clan.getStats()));
        jsonObject.add("members", obj.serialize(clan.getMembers()));
        jsonObject.add("roles", obj.serialize(clan.getRoles()));

        return jsonObject;
    }
}
