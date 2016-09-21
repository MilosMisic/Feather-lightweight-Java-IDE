package feather.editor;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import feather.persistance.Persistance;
import feather.properties.Dirs;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public final class MainEditor {

    private JTextArea editorPane;
    private JFileChooser fileChooser;
    private JScrollPane scrollPane;

    private boolean doneLoadingFiles;

    public static Map<String, String> openedFiles = new HashMap<>();

    public MainEditor() {
        getScrollPane();
    }

    public JScrollPane getScrollPane() {

        editorPane = new JTextArea();
        Font font = new Font("Consolas", Font.PLAIN, 16);
        editorPane.setFont(font);
        editorPane.setTabSize(4);
        editorPane.setLineWrap(true);
        editorPane.setMargin(new Insets(20, 40, 0, 0));
        editorPane.setForeground(Color.red);
        editorPane.add(new TextLineNumber(editorPane));
        editorPane.getDocument().addDocumentListener(new MyDocumentListener());
        scrollPane = new JScrollPane(editorPane);

        editorPane.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
             
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
//        try {
//            Theme theme = Theme.load(new FileInputStream(new File("C:\\Users\\Milos\\Documents\\NetBeansProjects\\MyApp\\libs\\RSyntaxTextArea-feather\\themes\\default.xml")));
//            theme.apply(editorPane);
//        } catch (IOException ioe) {
//            System.out.println("Never theme");
//
//        }
        return scrollPane;
    }

    public void open(JTabbedPane tabbedPane, JFrame frame) {
        fileChooser = new JFileChooser(Dirs.WORKING_DIRECTORY);

        fileChooser.setMultiSelectionEnabled(false);
        switch (fileChooser.showOpenDialog(frame)) {

            case JFileChooser.APPROVE_OPTION:
                File file = fileChooser.getSelectedFile();
                if (checkOpenedFiles(openedFiles, file.getAbsolutePath())) {
                    break;
                }
                String content = fillEditor(file);
                tabbedPane.addTab(file.getName(), getScrollPane());
                determineMain(content, file);
                editorPane.setText(content);
                tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
                openedFiles.put(file.getName(), file.getAbsolutePath());
                break;
        }
    }

    public void open(JTabbedPane tabbedPane, String dir) {
        File file = new File(dir);
        if (checkOpenedFiles(openedFiles, dir)) {
            return;
        }
        String content = fillEditor(file);
        tabbedPane.addTab(file.getName(), getScrollPane());
        editorPane.setText(content);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
        openedFiles.put(file.getName(), file.getAbsolutePath());
    }

    public void save(JTabbedPane tabbedPane, JFrame frame) {
        String tabName = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());
        if (tabName != null && !tabName.equals("New Document.txt")) {
            try {
                File file = new File(openedFiles.get(tabName));
                FileWriter fw = new FileWriter(file);
                scrollPane = (JScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
                JViewport viewport = scrollPane.getViewport();
                editorPane = (JTextArea) viewport.getView();
                String text = editorPane.getText();
                determineMain(text, file);
                fw.write(text);
                fw.close();
                tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
                return;
            } catch (IOException e) {
            }
        }
        fileChooser = new JFileChooser(Dirs.WORKING_DIRECTORY);
        switch (fileChooser.showSaveDialog(frame)) {
            case JFileChooser.APPROVE_OPTION:
                writeToFile(tabbedPane);
                File file = fileChooser.getSelectedFile();
                openedFiles.put(file.getName(), file.getAbsolutePath());
                break;
        }
    }

    private void writeToFile(JTabbedPane tabbedPane) {
        File file = fileChooser.getSelectedFile();
        try (FileWriter fw = new FileWriter(file)) {
            JViewport viewport = scrollPane.getViewport();
            editorPane = (JTextArea) viewport.getView();
            String text = editorPane.getText();
            determineMain(text, file);
            fw.write(text);
            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), file.getName());
            fw.close();
        } catch (IOException ex) {
        }
    }

    public void determineMain(String text, File file) {
        if (text.contains("main")) {
            String absolutePath = file.getAbsolutePath();
            String absolutePaths = absolutePath.substring(0, absolutePath.lastIndexOf(File.separator));
            Dirs.MAIN_CLASS_PATH = absolutePaths;
            Dirs.MAIN_CLASS_NAME = file.getName();
        }

    }

    public void newDocument(JTabbedPane tabbedPane) {
        tabbedPane.addTab("New Document.txt", getScrollPane());
        if (tabbedPane.getTabCount() > 1) {
            tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() + 1);
            editorPane.requestFocus();
        }
    }

    public boolean checkOpenedFiles(Map<String, String> map, String dir) {
        return map.containsValue(dir);
    }

    public String fillEditor(File f) {
        String content = "";
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            content = sb.toString();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        return content;
    }

    public void addOpenedFiles(JTabbedPane tabbedPane) {
        openedFiles.entrySet().stream().forEach(s -> {
            File file = new File(s.getValue());
            if (file.exists()) {
                String content = fillEditor(file);
                tabbedPane.addTab(file.getName(), getScrollPane());
                editorPane.setText(content);
                tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
            }
        });
        doneLoadingFiles = true;
    }

    public void closeTab(JTabbedPane mainTabPane) {
        if (mainTabPane.getTabCount() == 0) {
            Persistance.StoreOpened();
            System.exit(0);
        }
        openedFiles.remove(mainTabPane.getTitleAt(mainTabPane.getSelectedIndex()));
        mainTabPane.removeTabAt(mainTabPane.getSelectedIndex());
    }

    class MyDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (doneLoadingFiles) {

            }
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (doneLoadingFiles) {

            }
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (doneLoadingFiles) {

            }
        }
    }
}
