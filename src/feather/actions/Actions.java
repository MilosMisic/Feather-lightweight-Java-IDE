package feather.actions;

import javax.swing.JFileChooser;
import feather.persistance.Persistance;
import feather.properties.Dirs;
import java.awt.GridLayout;
import javax.swing.*;

public class Actions {

    public static void setWorkspace() {
        JDialog dialog;
        JPanel buttonPanel;
        JButton browseButton;
        JLabel label;
        if (Dirs.WORKING_DIRECTORY == null || Dirs.WORKING_DIRECTORY.equals("")) {
            label = new JLabel("Select working directory");
            dialog = new JDialog();
            buttonPanel = new JPanel();
            browseButton = new JButton("Browse");

            dialog.setSize(300, 200);
            dialog.setLayout(new GridLayout(0, 2));
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setLocationRelativeTo(null);

            buttonPanel.add(browseButton);
            dialog.add(buttonPanel);
            dialog.add(label);
            dialog.setVisible(true);

            browseButton.addActionListener(e -> {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.showOpenDialog(null);
                Dirs.WORKING_DIRECTORY = fc.getSelectedFile().getAbsolutePath();
                Persistance.settings.setProperty("workspace", Dirs.WORKING_DIRECTORY);
            });
        }
    }
}
