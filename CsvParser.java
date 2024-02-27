import java.io.*;
import java.util.regex.*;

public class CsvParser {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Usage: java CsvParser <inputCsvPath> <outputCsvPath> <prefix> <suffix> [ignorePattern]");
            return;
        }

        String inputCsvPath = args[0];
        String outputCsvPath = args[1];
        String prefix = args[2];
        String suffix = args[3];
        String ignorePattern = args.length > 4 ? args[4] : "";

        Pattern pattern = createPattern(prefix, suffix);
        Pattern ignorePatternCompiled = createIgnorePattern(ignorePattern);

        processCsv(inputCsvPath, outputCsvPath, pattern, ignorePatternCompiled);
    }

    private static Pattern createPattern(String prefix, String suffix) {
        String prefixPattern = "@@".equals(prefix) ? "" : Pattern.quote(prefix) + "\\s*";
        String suffixPattern = "@@".equals(suffix) ? "" : "\\s*" + Pattern.quote(suffix);
        // Create a pattern that matches text starting with prefix and ending with suffix.
        // If prefix or suffix is "@@", it is treated as a wildcard.
        String patternString = (prefixPattern.isEmpty() ? ".*?" : prefixPattern) + 
                               (suffixPattern.isEmpty() ? ".*?" : suffixPattern);
        return Pattern.compile(patternString);
    }

    private static Pattern createIgnorePattern(String ignorePattern) {
        return ignorePattern.isEmpty() ? null : Pattern.compile(ignorePattern);
    }

    private static void processCsv(String inputCsvPath, String outputCsvPath, Pattern pattern, Pattern ignorePatternCompiled) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputCsvPath));
             PrintWriter pw = new PrintWriter(new FileWriter(outputCsvPath))) {

            String line;
            while ((line = br.readLine()) != null) {
                if (ignorePatternCompiled != null) {
                    Matcher ignoreMatcher = ignorePatternCompiled.matcher(line);
                    line = ignoreMatcher.replaceAll("");
                }

                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    pw.println(line.trim()); // Trim each matched string.
                }
            }

            System.out.println("Extraction complete. Results are saved in " + outputCsvPath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
