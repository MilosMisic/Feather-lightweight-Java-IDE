package feather.window;

import javax.swing.JOptionPane;
import feather.actions.Actions;
import feather.console.MyConsole;
import feather.editor.MainEditor;
import feather.persistance.Persistance;
import feather.popumenu.RightClickMenu;
import feather.project.Project;
import feather.resources.Icons;
import feather.run.Run;
import feather.tree.ProjectTree;

public class Window extends javax.swing.JFrame {

    private final MainEditor mainEditor;
    String nodeName = "";
    String nodePath = "";

    public Window() {
        this.setIconImage(Icons.LOGO.getImage());
        Persistance.loadOpened();
        Actions.setWorkspace();
        mainEditor = new MainEditor();
        initComponents();
        mainEditor.addOpenedFiles(mainTabPane);
        MyConsole.redirectConsoleTo(ConsolePane);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainSplitPane = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        projectTree = new ProjectTree().getTree();
        jSplitPane3 = new javax.swing.JSplitPane();
        mainTabPane = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        ConsolePane = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        projectMenuItem = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        closeMenuItem = new javax.swing.JMenuItem();
        RunMenu = new javax.swing.JMenu();
        RunMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Feather - a lightweight Java IDE by Milos Misic");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(getIconImage());
        setMinimumSize(new java.awt.Dimension(1200, 800));
        setName("MyEdit"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        mainSplitPane.setDividerLocation(200);

        projectTree.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                projectTreeMouseClicked(evt);
            }

            public void mousePressed(java.awt.event.MouseEvent evt) {
                rightClick(evt);
            }
        });
        projectTree.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                Selection(evt);
            }
        });
        jScrollPane2.setViewportView(projectTree);

        mainSplitPane.setLeftComponent(jScrollPane2);

        jSplitPane3.setDividerLocation(500);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setTopComponent(mainTabPane);

        ConsolePane.setEditable(false);
        ConsolePane.setColumns(20);
        ConsolePane.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        ConsolePane.setRows(5);
        ConsolePane.setMargin(new java.awt.Insets(10, 10, 2, 2));
        ConsolePane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ConsolePaneMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ConsolePane);

        jSplitPane3.setRightComponent(jScrollPane1);

        mainSplitPane.setRightComponent(jSplitPane3);

        getContentPane().add(mainSplitPane, java.awt.BorderLayout.CENTER);

        fileMenu.setText("File");

        jMenu1.setText("New");

        projectMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.ALT_MASK));
        projectMenuItem.setText("Project");
        projectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                projectMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(projectMenuItem);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem1.setText("Class");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        fileMenu.add(jMenu1);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        closeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        closeMenuItem.setText("Close");
        closeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(closeMenuItem);

        jMenuBar1.add(fileMenu);

        RunMenu.setText("Run");

        RunMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        RunMenuItem.setText("Run");
        RunMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RunMenuItemActionPerformed(evt);
            }
        });
        RunMenu.add(RunMenuItem);

        jMenuBar1.add(RunMenu);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        mainEditor.open(mainTabPane, this);
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void closeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuItemActionPerformed
        new Thread(() -> {
            mainEditor.closeTab(mainTabPane);
        }).start();
    }//GEN-LAST:event_closeMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        new Thread(() -> {
            mainEditor.save(mainTabPane, this);
        }).start();
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new Thread(() -> {
            mainEditor.newDocument(mainTabPane);
        }).start();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        new Thread(() -> {
            Persistance.StoreOpened();
        }).start();
    }//GEN-LAST:event_formWindowClosing

    private void RunMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RunMenuItemActionPerformed
        new Thread(() -> {
            mainEditor.save(mainTabPane, this);
            new Run().exec(ConsolePane);
        }).start();

    }//GEN-LAST:event_RunMenuItemActionPerformed

    private void Selection(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_Selection

    }//GEN-LAST:event_Selection

    private void rightClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rightClick
        new Thread(() -> {
            new RightClickMenu().TreeRightClick(evt, projectTree);
        }).start();

    }//GEN-LAST:event_rightClick

    private void projectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_projectMenuItemActionPerformed
        String name = JOptionPane.showInputDialog("Enter Project Name: ");
        new Project(name);
        new ProjectTree().addProjectNode(projectTree, name);
    }//GEN-LAST:event_projectMenuItemActionPerformed

    private void projectTreeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_projectTreeMouseClicked
        new Thread(() -> {
            if (evt.getClickCount() == 2) {
                try {
                    String dir = RightClickMenu.getTreePath(projectTree);
                    if (dir.endsWith(".java") || dir.endsWith(".txt")) {
                        mainEditor.open(mainTabPane, dir);
                    }
                } catch (NullPointerException e) {
                }
            }
        }).start();

    }//GEN-LAST:event_projectTreeMouseClicked

    private void ConsolePaneMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ConsolePaneMouseClicked
        new RightClickMenu().consolePaneRightClick(evt, ConsolePane);
    }//GEN-LAST:event_ConsolePaneMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ConsolePane;
    private javax.swing.JMenu RunMenu;
    private javax.swing.JMenuItem RunMenuItem;
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane mainSplitPane;
    private javax.swing.JTabbedPane mainTabPane;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem projectMenuItem;
    private javax.swing.JTree projectTree;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
        java.awt.EventQueue.invokeLater(() -> {
            new Window().setVisible(true);
        });
    }

}
