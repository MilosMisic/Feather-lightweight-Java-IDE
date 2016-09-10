package feather.actions;

import javax.swing.JFileChooser;
import feather.persistance.Persistance;
import feather.properties.Dirs;
import java.awt.BorderLayout;
import javax.swing.*;

public class Actions {

    public static void setWorkspace() {
        JDialog dialog;
        JPanel buttonPanel;
        JButton browseButton;
        JTextField textField;

        if (Dirs.WORKING_DIRECTORY == null || Dirs.WORKING_DIRECTORY.equals("")) {

            dialog = new JDialog();
            buttonPanel = new JPanel();
            browseButton = new JButton("Browse");
            textField = new JTextField(15);

            dialog.setSize(300, 200);
            dialog.setLayout(new BorderLayout());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setLocationRelativeTo(null);

            buttonPanel.add(browseButton);
            dialog.add(buttonPanel, BorderLayout.SOUTH);
            dialog.add(textField, BorderLayout.CENTER);

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
