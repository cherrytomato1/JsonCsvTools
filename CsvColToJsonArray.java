import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class CsvColToJsonArray {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java CsvColToJsonArray <inputCsvFile> <outputJsonFile> <columnIndex>");
            return;
        }

        String inputCsvFilePath = args[0];
        String outputJsonFilePath = args[1];
        int columnIndex = Integer.parseInt(args[2]) - 1; // 컬럼 인덱스는 1부터 시작하므로 1을 빼줍니다.
        List<String> columnValues = new ArrayList<>();

        try {
            Path csvPath = Paths.get(inputCsvFilePath);
            List<String> allLines = Files.readAllLines(csvPath);

            for (String line : allLines) {
                String[] columns = line.split(",");
                if (columnIndex < columns.length) {
                    columnValues.add(columns[columnIndex].trim());
                }
            }

            String jsonArray = toJsonArray(columnValues);
            Files.write(Paths.get(outputJsonFilePath), jsonArray.getBytes());
            System.out.println("JSON 배열이 성공적으로 생성되었습니다: " + outputJsonFilePath);

        } catch (IOException e) {
            System.err.println("Error processing the file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Please provide a valid column index.");
        }
    }

    private static String toJsonArray(List<String> values) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < values.size(); i++) {
            sb.append("   \"").append(values.get(i)).append("\"");
            if (i < values.size() - 1) {
                sb.append(",");
            }
            sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
