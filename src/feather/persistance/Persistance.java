package feather.persistance;

import java.io.*;
import java.util.Properties;
import static feather.editor.MainEditor.openedFiles;
import feather.properties.Dirs;

public class Persistance {

    public static Properties settings = new Properties();
    public static Properties workspace = new Properties();

    public static void StoreOpened() {
        settings.clear();
        workspace.setProperty("workspace", Dirs.WORKING_DIRECTORY);
        openedFiles.entrySet().stream().forEach(s -> {
            File file = new File(s.getValue());
            if (file.exists()) {
                settings.setProperty(s.getKey(), s.getValue());
            }
        });
        try (OutputStream out = new FileOutputStream(Dirs.OPENED_FILES)) {
            settings.store(out, "Opened Files");
            out.close();
        } catch (Exception e) {
        }
        try (OutputStream out = new FileOutputStream(Dirs.SETTINGS)) {
            workspace.store(out, "workspace");
            out.close();
        } catch (Exception e) {
        }
    }

    public static void loadOpened() {
        try (FileInputStream in = new FileInputStream(Dirs.OPENED_FILES)) {
            settings.load(in);
            in.close();
        } catch (Exception e) {
            System.out.println("loading failed");
        }
        try (FileInputStream in = new FileInputStream(Dirs.SETTINGS)) {
            workspace.load(in);
            in.close();
        } catch (Exception e) {
            System.out.println("loading failed");
        }
        Dirs.WORKING_DIRECTORY = workspace.getProperty("workspace");
        settings.entrySet().stream().forEach(s -> {
            openedFiles.put(s.getKey().toString(), s.getValue().toString());
        });
    }
}
