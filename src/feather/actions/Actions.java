package feather.actions;

import javax.swing.JFileChooser;
import feather.persistance.Persistance;
import feather.properties.Dirs;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Actions {

    public static void setWorkspace() {
        if (Dirs.WORKING_DIRECTORY == null || Dirs.WORKING_DIRECTORY.equals("")) {
//C\:\\Users\\Milos\\Desktop\\projekti
            JDialog setWorkspaceDialog = new JDialog();
            setWorkspaceDialog.setSize(300, 200);
            setWorkspaceDialog.setLayout(new BorderLayout());
            JPanel buttonPanel = new JPanel();

            setWorkspaceDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setWorkspaceDialog.setLocationRelativeTo(null);
            JButton browseButton = new JButton("Browse");
            buttonPanel.add(browseButton);
            setWorkspaceDialog.add(buttonPanel, BorderLayout.SOUTH);

            JTextField textField = new JTextField(15);
            setWorkspaceDialog.add(textField, BorderLayout.CENTER);

            setWorkspaceDialog.setVisible(true);
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
