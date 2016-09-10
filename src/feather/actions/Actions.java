package feather.actions;

import javax.swing.JFileChooser;
import feather.persistance.Persistance;
import feather.properties.Dirs;

public class Actions {

    public static void setWorkspace() {
        if (Dirs.WORKING_DIRECTORY == null || Dirs.WORKING_DIRECTORY.equals("")) {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fc.showOpenDialog(null);
            Dirs.WORKING_DIRECTORY = fc.getSelectedFile().getAbsolutePath();
            Persistance.settings.setProperty("workspace", Dirs.WORKING_DIRECTORY);
        }

    }
}
