//Stephen Hanna 109097796
package Java2HTML;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class Java2HTML {

    public static String translateEmphasis(String line) {
        return line = "<em>" + line + "</em>";
    }

    public static String translateStrongEmphasis(String line) {
        return line = "<strong>" + line + "</strong>";
    }

    public static String translateHyperLink(String linkText, String URL) {
        String hyperLink = "<a href=\"" + URL + "\">" + linkText + "</a>";
        return hyperLink;
    }

    public static String translateImage(String alternateText, String imagePath, String titleText) {
        String image = "<img src=\"" + imagePath + "\" alt=\"" + alternateText + "\" title=\"" + titleText + ">";
        return image;
    }

    public static String translateCode(String line) {
        return line = "<code>" + line + "</code>";
    }

    public static String translateListItem(String line) {
        return line = "<li>" + line + "</li>";
    }

    public static String fixString(String line) {
        String a[] = new String[1];
        if (line.contains("**")) {
            int begin = line.indexOf("**");
            String lineSub = line.substring(begin + 1);
            int end = lineSub.indexOf("**");
            String toFeed = line.substring(begin + 2, end + begin + 1);
            String New = line.substring(0, begin) + translateStrongEmphasis(toFeed) + line.substring(end + begin + 3);
            if (New.contains("**") || New.contains("*") || New.contains("![") || New.contains("[") || New.contains("`")) {
                New = fixString(New);
            }
            a[0] = New;
        } else if (line.contains("*")) {
            int begin = line.indexOf("*");
            String lineSub = line.substring(begin + 1);
            int end = lineSub.indexOf("*");
            String toFeed = line.substring(begin + 1, end + begin + 1);
            String New = line.substring(0, begin) + translateEmphasis(toFeed) + line.substring(end + begin + 2);
            if (New.contains("**") || New.contains("*") || New.contains("![") || New.contains("[") || New.contains("`")) {
                New = fixString(New);
            }
            a[0] = New;
        } else if (line.contains("![")) {
            int begin1 = line.indexOf("!");
            String lineSub1 = line.substring(begin1 + 2);
            int end1 = lineSub1.indexOf("]");
            String alternateText = line.substring(begin1 + 2, end1 + begin1 + 2);
            int begin2 = line.indexOf("(");
            String lineSub2 = line.substring(begin2 + 1);
            int end2 = lineSub2.indexOf("\"");
            String path = line.substring(begin2 + 1, end2 + begin2);
            int begin3 = line.indexOf("\"");
            String lineSub3 = line.substring(begin3 + 1);
            int end3 = lineSub3.indexOf(")");
            String title = line.substring(begin3 + 1, end3 + begin3 + 1);
            String New = line.substring(0, begin1) + translateImage(alternateText, path, title) + line.substring(begin3 + end3 + 2);
            if (New.contains("**") || New.contains("*") || New.contains("![") || New.contains("[") || New.contains("`")) {
                New = fixString(New);
            }
            a[0] = New;
        } else if (line.contains("[")) {
            int begin1 = line.indexOf("[");
            String lineSub1 = line.substring(begin1 + 1);
            int end1 = lineSub1.indexOf("]");
            String linkText = line.substring(begin1 + 1, end1 + begin1 + 1);
            int begin2 = line.indexOf("](");
            String lineSub2 = line.substring(begin2 + 2);
            int end2 = lineSub2.indexOf(")");
            String URLtoLink = line.substring(begin2 + 2, end2 + begin2 + 2);
            String New = line.substring(0, begin1) + translateHyperLink(linkText, URLtoLink) + line.substring(begin2 + end2 + 3);
            if (New.contains("**") || New.contains("*") || New.contains("![") || New.contains("[") || New.contains("`")) {
                New = fixString(New);
            }
            a[0] = New;
        } else if (line.contains("`")) {
            int begin = line.indexOf("`");
            String lineSub = line.substring(begin + 1);
            int end = lineSub.indexOf("`");
            String toFeed = line.substring(begin + 1, end + begin + 1);
            String New = line.substring(0, begin) + translateCode(toFeed) + line.substring(end + begin + 2);
            if (New.contains("**") || New.contains("*") || New.contains("![") || New.contains("[") || New.contains("`")) {
                New = fixString(New);
            }
            a[0] = New;
        }
        return a[0];
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner inputName = new Scanner(System.in);
        System.out.println("Please enter the name of the input file: ");
        String readName = inputName.nextLine();
        System.out.println("Please enter the name of the output file: ");
        String outName = inputName.nextLine();
        File fileOut = new File(outName);
        FileWriter writer = null;
        String result = "<!DOCTYPE html>\n<html>\n<head>\n<title>Results of Markdown Translation</title>\n</head>\n<body>";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(readName)));
            File fout = new File(outName);
            FileOutputStream fos = new FileOutputStream(fout);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            String line;
            boolean test = false;
            int count = 0;
            bw.write("<!DOCTYPE html>");
            bw.newLine();
            bw.write("<html>");
            bw.newLine();
            bw.write("<head>");
            bw.newLine();
            bw.write("<title>Results of Markdown Translation</title>");
            bw.newLine();
            bw.write("</head>");
            bw.newLine();
            bw.write("<body>");
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("**")) {
                    if (test == true) {
                        result = result + "\n</ul>";
                        bw.newLine();
                        bw.write("</ul>");
                    }
                    int begin = line.indexOf("**");
                    String lineSub = line.substring(begin + 1);
                    int end = lineSub.indexOf("**");
                    String toFeed = line.substring(begin + 2, end + begin + 1);
                    String New = line.substring(0, begin) + translateStrongEmphasis(toFeed) + line.substring(end + begin + 3);
                    if (New.contains("**") || New.contains("*") || New.contains("![") || New.contains("[") || New.contains("`")) {
                        New = fixString(New);
                    }
                    bw.newLine();
                    bw.write(New);
                    result = result + "\n" + New;
                    test = false;
                } else if (line.contains("*")) {
                    if (test == true) {
                        result = result + "\n</ul>";
                        bw.newLine();
                        bw.write("</ul>");
                    }
                    int begin = line.indexOf("*");
                    String lineSub = line.substring(begin + 1);
                    int end = lineSub.indexOf("*");
                    String toFeed = line.substring(begin + 1, end + begin + 1);
                    String New = line.substring(0, begin) + translateEmphasis(toFeed) + line.substring(end + begin + 2);
                    if (New.contains("**") || New.contains("*") || New.contains("![") || New.contains("[") || New.contains("`")) {
                        New = fixString(New);
                    }
                    bw.newLine();
                    bw.write(New);
                    result = result + "\n" + New;
                    test = false;
                } else if (line.contains("![")) {
                    if (test == true) {
                        result = result + "\n</ul>";
                        bw.newLine();
                        bw.write("</ul>");
                    }
                    int begin1 = line.indexOf("!");
                    String lineSub1 = line.substring(begin1 + 2);
                    int end1 = lineSub1.indexOf("]");
                    String alternateText = line.substring(begin1 + 2, end1 + begin1 + 2);
                    int begin2 = line.indexOf("(");
                    String lineSub2 = line.substring(begin2 + 1);
                    int end2 = lineSub2.indexOf("\"");
                    String path = line.substring(begin2 + 1, end2 + begin2);
                    int begin3 = line.indexOf("\"");
                    String lineSub3 = line.substring(begin3 + 1);
                    int end3 = lineSub3.indexOf(")");
                    String title = line.substring(begin3 + 1, end3 + begin3 + 1);
                    String New = line.substring(0, begin1) + translateImage(alternateText, path, title) + line.substring(begin3 + end3 + 2);
                    if (New.contains("**") || New.contains("*") || New.contains("![") || New.contains("[") || New.contains("`")) {
                        New = fixString(New);
                    }
                    bw.newLine();
                    bw.write(New);
                    result = result + "\n" + New;
                    test = false;
                } else if (line.contains("[")) {
                    if (test == true) {
                        result = result + "\n</ul>";
                        bw.newLine();
                        bw.write("</ul>");
                    }
                    int begin1 = line.indexOf("[");
                    String lineSub1 = line.substring(begin1 + 1);
                    int end1 = lineSub1.indexOf("]");
                    String linkText = line.substring(begin1 + 1, end1 + begin1 + 1);
                    int begin2 = line.indexOf("](");
                    String lineSub2 = line.substring(begin2 + 2);
                    int end2 = lineSub2.indexOf(")");
                    String URLtoLink = line.substring(begin2 + 2, end2 + begin2 + 2);
                    String New = line.substring(0, begin1) + translateHyperLink(linkText, URLtoLink) + line.substring(begin2 + end2 + 3);
                    System.out.print(New);
                    if (New.contains("**") || New.contains("*") || New.contains("![") || New.contains("[") || New.contains("`")) {
                        New = fixString(New);
                    }
                    bw.newLine();
                    bw.write(New);
                    result = result + "\n" + New;
                    test = false;
                } else if (line.contains("`")) {
                    if (test == true) {
                        result = result + "\n</ul>";
                        bw.newLine();
                        bw.write("</ul>");
                    }
                    int begin = line.indexOf("`");
                    String lineSub = line.substring(begin + 1);
                    int end = lineSub.indexOf("`");
                    String toFeed = line.substring(begin + 1, end + begin + 1);
                    String New = line.substring(0, begin) + translateCode(toFeed) + line.substring(end + begin + 2);
                    if (New.contains("**") || New.contains("*") || New.contains("![") || New.contains("[") || New.contains("`")) {
                        New = fixString(New);
                    }
                    bw.newLine();
                    bw.write(New);
                    result = result + "\n" + New;
                    test = false;
                } else if (line.contains("+") && test == false) {
                    result = result + "\n<ul>";
                    int begin1 = line.indexOf("+ ");
                    String toFeed1 = line.substring(begin1 + 2);
                    String New1 = translateListItem(toFeed1);
                    bw.newLine();
                    bw.write("<ul>");
                    bw.newLine();
                    bw.write(New1);
                    result = result + "\n" + New1;
                    test = true;
                } else if (line.contains("+") && test == true) {
                    int begin1 = line.indexOf("+ ");
                    String toFeed1 = line.substring(begin1 + 2);
                    String New1 = translateListItem(toFeed1);
                    result = result + "\n" + New1;
                    bw.newLine();
                    bw.write(New1);
                    test = true;
                } else if (!(line.isEmpty())) {
                    if (test == true) {
                        result = result + "\n</ul>";
                        bw.newLine();
                        bw.write("</ul>");
                    }
                    bw.newLine();
                    bw.write(line);
                    result = result + "\n" + line;
                    test = false;
                } else if (line.trim().contains("")) {
                    if (test == true) {
                        result = result + "\n</ul>";
                    }
                    result = result + "\n<p>";
                    bw.newLine();
                    bw.write("<p>");
                    test = false;
                }
            }
            if (test == true) {
                bw.newLine();
                bw.write("</ul>");
                result = result + "\n</ul>";
            }
            bw.newLine();
            bw.write("</body>");
            bw.newLine();
            bw.write("</html>");
            bw.close();
            result = result + "\n</body>\n</html>";

        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + readName + "'");
        }
        System.out.println(result);
    }
}
