import java.io.*;
import java.nio.file.*;

public class TxtToCsv {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java TxtToCsv <inputFile> <outputFile> <delimiter>");
            return;
        }

        String inputFilePath = args[0];
        String outputFilePath = args[1];
        String delimiter = args[2];

        try {
            Path inputPath = Paths.get(inputFilePath);
            BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath));

            Files.readAllLines(inputPath).forEach(line -> {
                try {
                    String csvLine = line.replace(delimiter, ",");
                    writer.write(csvLine);
                    writer.newLine();
                } catch (IOException e) {
                    System.err.println("Error writing to CSV file: " + e.getMessage());
                }
            });

            writer.close();
            System.out.println("CSV 파일이 성공적으로 생성되었습니다: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error processing the file: " + e.getMessage());
        }
    }
}
