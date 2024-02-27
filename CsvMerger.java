import java.io.*;

public class CsvMerger {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java CsvMerger <csvFile1Path> <csvFile2Path> <mergedCsvFilePath>");
            return;
        }

        String csvFile1Path = args[0];
        String csvFile2Path = args[1];
        String mergedCsvFilePath = args[2];

        mergeCsvFiles(csvFile1Path, csvFile2Path, mergedCsvFilePath);
    }

    private static void mergeCsvFiles(String csvFile1Path, String csvFile2Path, String mergedCsvFilePath) {
        try (
            BufferedReader br1 = new BufferedReader(new FileReader(csvFile1Path));
            BufferedReader br2 = new BufferedReader(new FileReader(csvFile2Path));
            PrintWriter pw = new PrintWriter(new FileWriter(mergedCsvFilePath))
        ) {
            String line;

            // Read and write all lines from the first CSV file
            while ((line = br1.readLine()) != null) {
                pw.println(line);
            }

            // Read and write all lines from the second CSV file
            while ((line = br2.readLine()) != null) {
                pw.println(line);
            }

            System.out.println("CSV files have been merged successfully into " + mergedCsvFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
