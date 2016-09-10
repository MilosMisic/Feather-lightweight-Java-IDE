package feather.editor;

import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import feather.persistance.Persistance;
import feather.properties.Dirs;
import feather.feather.fife.ui.autocomplete.AutoCompletion;
import feather.feather.fife.ui.autocomplete.BasicCompletion;
import feather.feather.fife.ui.autocomplete.CompletionProvider;
import feather.feather.fife.ui.autocomplete.DefaultCompletionProvider;
import feather.feather.fife.ui.autocomplete.ShorthandCompletion;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.SyntaxScheme;
import org.fife.ui.rtextarea.RTextScrollPane;

public final class MainEditor {

    private RSyntaxTextArea editorPane;
    private JFileChooser fileChooser;
    private RTextScrollPane scrollPane;

    private boolean doneLoadingFiles;

    public static Map<String, String> openedFiles = new HashMap<>();

    public MainEditor() {
        getScrollPane();
    }

    public JScrollPane getScrollPane() {

        editorPane = new RSyntaxTextArea();
        editorPane.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);

        Font font = new Font("Consolas", Font.PLAIN, 16);
        editorPane.setFont(font);
        editorPane.setTabSize(4);
        editorPane.setLineWrap(true);
        editorPane.setMargin(new Insets(20, 40, 0, 0));
//        editorPane.setForeground(Color.BLUE);
        editorPane.setAutoIndentEnabled(true);
        editorPane.getDocument().addDocumentListener(new MyDocumentListener());
        scrollPane = new RTextScrollPane(editorPane);
        CompletionProvider provider = createCompletionProvider();
//        try {
//            Theme theme = Theme.load(new FileInputStream(new File("C:\\Users\\Milos\\Documents\\NetBeansProjects\\MyApp\\libs\\RSyntaxTextArea-feather\\themes\\default.xml")));
//            theme.apply(editorPane);
//        } catch (IOException ioe) {
//            System.out.println("Never theme");
//
//        }
        AutoCompletion ac = new AutoCompletion(provider);
        ac.install(editorPane);
        return scrollPane;
    }

    public static void setFont(RSyntaxTextArea textArea, Font font) {
        if (font != null) {
            SyntaxScheme ss = textArea.getSyntaxScheme();
            ss = (SyntaxScheme) ss.clone();
            for (int i = 0; i < ss.getStyleCount(); i++) {
                if (ss.getStyle(i) != null) {
                    ss.getStyle(i).font = font;
                }
            }
            textArea.setSyntaxScheme(ss);
            textArea.setFont(font);
        }
    }

    private CompletionProvider createCompletionProvider() {

        DefaultCompletionProvider provider = new DefaultCompletionProvider();

        // Add completions for all Java keywords. A BasicCompletion is just
        // a straightforward word completion.
        provider.addCompletion(new BasicCompletion(provider, "abstract"));
        provider.addCompletion(new BasicCompletion(provider, "assert"));
        provider.addCompletion(new BasicCompletion(provider, "break"));
        provider.addCompletion(new BasicCompletion(provider, "case"));
        // ... etc ...
        provider.addCompletion(new BasicCompletion(provider, "transient"));
        provider.addCompletion(new BasicCompletion(provider, "try"));
        provider.addCompletion(new BasicCompletion(provider, "void"));
        provider.addCompletion(new BasicCompletion(provider, "volatile"));
        provider.addCompletion(new BasicCompletion(provider, "while"));

        // Add a couple of "shorthand" completions. These completions don't
        // require the input text to be the same thing as the replacement text.
        provider.addCompletion(new ShorthandCompletion(provider, "sd",
                "System.out.println(", "System.out.println("));
        provider.addCompletion(new ShorthandCompletion(provider, "syserr",
                "System.err.println(", "System.err.println("));

        return provider;

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
                scrollPane = (RTextScrollPane) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
                JViewport viewport = scrollPane.getViewport();
                editorPane = (RSyntaxTextArea) viewport.getView();
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
            editorPane = (RSyntaxTextArea) viewport.getView();
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
