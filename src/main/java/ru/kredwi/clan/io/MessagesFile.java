package ru.kredwi.clan.io;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MessagesFile extends CommonIOFile<Map<String, List<String>>> {

    public static final Gson gson = new Gson();

    private final File dataFolder;

    @Override
    public boolean rewrite() {
        return false;
    }

    @Override
    protected File getFile() {
        return new File(dataFolder, "messages.json");
    }

    @Override
    protected @NonNull Map<String, List<String>> deserialize(@NonNull String json) {
        Type type = new TypeToken<Map<String, List<String>>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    @Override
    protected String serialize(@NonNull Map<String, List<String>> stringListMap) {
        return gson.toJson(stringListMap);
    }
}
