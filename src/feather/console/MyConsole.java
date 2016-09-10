package feather.console;

import javax.swing.*;
import java.io.*;

public class MyConsole {

    public static void redirectConsoleTo(final JTextArea textarea) {
        PrintStream out = new PrintStream(new ByteArrayOutputStream() {
            @Override
            public synchronized void flush() throws IOException {
                textarea.setText(toString());
            }
        }, true);
        System.setErr(out);
        System.setOut(out);
    }
}
