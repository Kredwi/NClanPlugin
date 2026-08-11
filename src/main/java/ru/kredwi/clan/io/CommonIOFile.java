package ru.kredwi.clan.io;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import ru.kredwi.clan.NClanPlugin;

import java.io.*;
import java.nio.file.Files;
import java.util.function.Supplier;

public abstract class CommonIOFile<T> {

    public boolean rewrite() {
        return true;
    }

    public T read() {
        String json = readFile();
        return deserilize(json);
    }

    public void write(T t) {
        if (getFile().exists() && !rewrite())
            return;

        String json = seserilize(t);
        writeFile(json);
    }

    private @NonNull String readFile() {
        File file = getFile();

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            String defaultValue = getDefaultValue();
            if ((!writeFileFromJar(file) && defaultValue != null) && writeDefaultValue()) {
                NClanPlugin.log.error("Error of read file. Returned empty value");
                return "";
            }
        }

        var json = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                json.append(line);
            }
        } catch (FileNotFoundException e) {
            NClanPlugin.log.debug("File not found " + e.getMessage() + " (this not error but logged)");
        } catch (IOException e) {
            NClanPlugin.log.error("Error of reading file", e);
        }
        return json.toString();
    }

    private boolean writeFileFromJar(File file) {
        try (InputStream in = getClass().getResourceAsStream("/" + file.getName())) {
            if (in == null) {
                NClanPlugin.log.warning("File with name " + file.getName() + " cannot be found in jar file");
                return false;
            }

            Files.copy(in, file.toPath());
            return true;
        } catch (IOException e) {
            NClanPlugin.log.error("Error of copy file from jar to disk", e);
            return false;
        }

    }

    private void writeFile(String fileContent) {
        // OutputStreamWriter already rewrite file. Delete cannot be required

        writeToFile(() -> fileContent);
    }

    private boolean writeDefaultValue() {
        String defValue = getDefaultValue();
        if (defValue == null || defValue.isEmpty())
            return false;
        return writeToFile(() -> defValue);
    }

    private boolean writeToFile(Supplier<String> content) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getFile())))) {

            writer.write(content.get());
            return true;
        } catch (FileNotFoundException e) {
            NClanPlugin.log.error("File is not found", e);
        } catch (IOException e) {
            NClanPlugin.log.error("Error of creating default file", e);
        }
        return false;
    }

    protected abstract File getFile();

    protected abstract @NonNull T deserilize(@NonNull String json);

    protected abstract String seserilize(@NonNull T t);

    protected @Nullable String getDefaultValue() {
        return null;
    }

}
