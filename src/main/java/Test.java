import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Test {
    public static void main(String[] args) {

    }
    public void readWithOpenCsv(InputStream inputStream){
        try(CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))){
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null){
                // nextLine[] is array of alues from the line
                System.out.println(nextLine[0] + " " + nextLine[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
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

}
