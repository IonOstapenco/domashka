import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.*;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        String cities = "countries.csv";
        readAllDataAtOnce(cities);
    }
    public static void OLDreadAllDataAtOnce(String file)
    {
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.
            FileReader filereader = new FileReader(file);

            // create csvReader object and skip first Line
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    .withSkipLines(1)
                    .build();
            List<String[]> allData = csvReader.readAll();

            // print Data
            for (String[] row : allData) {
                for (String cell : row) {
                    System.out.print(cell + "\t");
                }
                System.out.println();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

/*
// Try-with-resources automatically closes the stream
try (InputStream file = new FileInputStream("filename.csv")) {
    int data;
    while ((data = file.read()) != -1) {
        System.out.print((char) data);
    }
} catch (IOException e) {
    e.printStackTrace();
}
* */

    public static void readAllDataAtOnce(String file) {

        InputStream is = Test2.class
                .getClassLoader()
                .getResourceAsStream(file);

        if (is == null) {
            throw new RuntimeException("File not found: " + file);
        }

        try (CSVReader csvReader = new CSVReaderBuilder(
                new InputStreamReader(is))
                .withSkipLines(1)
                .build()) {

            List<String[]> allData = csvReader.readAll();

            for (String[] row : allData) {
                System.out.println(String.join("\t", row));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
