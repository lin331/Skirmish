package output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class Output {
    public static class Print {

        public static void printf(String format, Object... args) {
            PrintWriter writer = null;
            writer = new PrintWriter(new OutputStreamWriter(System.out));
            writer.printf(format, args);
            writer.close();
        }

        public static void printf(String file, String format, Object... args) {
            PrintWriter writer = null;
            try {
                writer = new PrintWriter(new FileOutputStream(new File(file),
                        true));
                writer.printf(format, args);
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            System.out.format(format, args);
        }
    }
}
