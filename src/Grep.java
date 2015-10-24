import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Grep {
    private static String fileName;
    private static String reg;
    private static Pattern p;

    public static void write(String text) {
        File file = new File("output.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter out = new PrintWriter(file.getAbsoluteFile());
            out.print(text);
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException, FileNotFoundException {
        //Проверка наличия аргументов
        if (args.length < 2) {
            System.out.println("Отсутсвуют аргументы коммандной строки.");
            System.out.print("Введите имя файла:");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            fileName = reader.readLine();
            System.out.println();
            System.out.print("Введите регулярное выражение:");
            reg = reader.readLine();
        } else {
            fileName = args[0];
            reg = args[1];
        }
        try {
            p = Pattern.compile(reg);
        } catch (PatternSyntaxException e) {
            write("incorrect regular expression");
            System.exit(0);
        }
        //Чтение из файла
        StringBuilder sb = new StringBuilder();
        File file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException(file.getName());
        }
        try {
            BufferedReader in = new BufferedReader(new
                    FileReader(file.getAbsoluteFile()));
            String s;
            int i = 1;
            while ((s = in.readLine()) != null) {
                Matcher m = p.matcher(s);
                if (m.find()) {
                    sb.append(i + ": " + s + "\n");
                }
                i++;
            }
            in.close();
            write(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
