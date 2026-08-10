package ru.kredwi.clan.io;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.jspecify.annotations.NonNull;
import ru.kredwi.clan.NClanPlugin;
import ru.kredwi.clan.model.Level;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record LevelFile(File levelFile) {

    public static final Gson gson = new Gson();
    public static final String LEVEL_FILE_NAME = "level.json";

    public LevelFile(File levelFile) {
        this.levelFile = new File(levelFile, LEVEL_FILE_NAME);
    }

    public @NonNull Map<Integer, Level> read() {
        if (!levelFile.exists()) {
            levelFile.getParentFile().mkdirs();
            if (!writeFileFromJar(levelFile))
                writeDefaultWrite(levelFile);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(levelFile)))) {

            StringBuilder json = new StringBuilder();
            String buf;
            while ((buf = reader.readLine()) != null) {
                json.append(buf);
            }

            if (json.isEmpty()) {
                NClanPlugin.log.warning("level file is empty");
                return Map.of();
            }

            var levels = new HashMap<Integer, Level>();

            JsonArray object = gson.fromJson(json.toString(), JsonArray.class);
            object.forEach(e -> {
                Level level = gson.fromJson(e, Level.class);
                levels.put(level.getExp(), level);
            });

            return levels;

        } catch (FileNotFoundException e) {
            NClanPlugin.log.error("levelFile is not found", e);
            return Map.of();
        } catch (IOException e) {
            NClanPlugin.log.error("Error of reading level file", e);
            return Map.of();
        }
    }

    private void writeDefaultWrite(File file) {
        String fileContent = gson.toJson(List.of(new Level("default", 0, 5, 5)));

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {

            writer.write(fileContent);

        } catch (FileNotFoundException e) {
            NClanPlugin.log.error("levelFile is not found", e);
        } catch (IOException e) {
            NClanPlugin.log.error("Error of creating default level file", e);
        }
    }

    private boolean writeFileFromJar(File file) {
        try (InputStream in = LevelFile.class.getResourceAsStream("/" + LEVEL_FILE_NAME)) {
            if (in == null) {
                NClanPlugin.log.warning("File with name " + LEVEL_FILE_NAME + " cannot be found in jar file");
                return false;
            }

            Files.copy(in, file.toPath());
            return true;
        } catch (IOException e) {
            NClanPlugin.log.error("Error of copy file from jar to disk", e);
            return false;

        }

    }

}
