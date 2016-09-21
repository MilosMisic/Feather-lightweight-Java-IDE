package feather.run;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.swing.JTextArea;
import feather.properties.Dirs;

public class Run {

    public void exec(JTextArea ConsolePane) {
        try {
            ConsolePane.setText("");
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd " + Dirs.MAIN_CLASS_PATH + " && javac " + Dirs.MAIN_CLASS_NAME + " && java " + Dirs.MAIN_CLASS_NAME.substring(0, Dirs.MAIN_CLASS_NAME.length() - 5));

            builder.redirectErrorStream(true);
            Process p = builder.start();
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            String text = "";

            while (true) {
                line = r.readLine();
                if (line == null) {
                    break;
                }
                text = text + line + "\n";
            }
            ConsolePane.setText(text);
        } catch (Exception e) {
        }

    }

}
