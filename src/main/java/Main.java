import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        //calculeaza, utilizand ambele fisierre csv
        calculateRatio("countries.csv", "cities.csv");
        System.out.println("fisierele prelucrate sunt: ");
        String citiesContent = readCSV("cities.csv");
        //readCSV("cities.csv"); //doar citeste
        //readCSV("countries.csv"); // doar citeste
        System.out.println(citiesContent);
        System.out.println("!!! reading with new method");

        //readAllDataAtOnce("countries.csv");

    }

    //cream colectia Map
    public static Map<Integer, Integer> readCountries(String filename) {
        Map<Integer, Integer> countries = new HashMap<>();

        try {
            InputStream is = Main.class
                    .getClassLoader()
                    .getResourceAsStream(filename);
            CSVReaderHeaderAware reader =
                    new CSVReaderHeaderAware(new InputStreamReader(is));
            Map<String, String> row;

            while ((row = reader.readMap()) != null) {
                int id = Integer.parseInt(row.get("id"));
                // population should be int, no situation of a one and a half man
                //double population = Double.parseDouble(row.get("population"));

                int population = Integer.parseInt(row.get("population"));

                countries.put(id, population);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }


    // metoda de citire a fisierelor csv
    public static String readCSV(String filename) {

        StringBuilder result = new StringBuilder();

        // punem ca sa se citeasca denumirea fisierelor
        System.out.println("\n===== FILE: " + filename + " =====");
        try (
                InputStream is = Main.class
                        .getClassLoader()
                        .getResourceAsStream(filename);) {


            CSVReaderHeaderAware reader =
                    new CSVReaderHeaderAware(new InputStreamReader(is));

            Map<String, String> row;
            while ((row = reader.readMap()) != null) {
                result.append(row).append("\n");
                //System.out.println(row);
            }
            return result.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }

    //new method of reading
    public void readWithOpenCsv(InputStream inputStream) {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                // nextLine[] is array of values from the line
                System.out.println(nextLine[0] + " " + nextLine[1]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }


    public static int populationRatio(int cityPopulation, int countryPopulation) {
        // unreasonable the population shouldn't be null on invocation
        if (countryPopulation == 0) return 0; //avoi division by 0

        //anyway we need diuble result
        double ratio = (double) cityPopulation / countryPopulation * 100;

        //return (cityPopulation / countryPopulation) * 100; --> old
        // conversion to int
        return (int) ratio;

    }

    // metoda de citire a fisierelor csv
    // this method should return a list of population ratios 
    // the resources will be used to render the results on frontend/java swing
    // calculate what?? 
    public static void calculateRatio(String countriesFile, String citiesFile) {
        Map<Integer, Integer> countries = readCountries(countriesFile);

        try {
            // try to use FileInputStream instead of classloaded resources
            // retain InputStream, becuase FileInputStream implements InputStream interface
            InputStream is = Main.class
                    .getClassLoader()
                    .getResourceAsStream(citiesFile);
            CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new InputStreamReader(is));

            Map<String, String> row;

            while ((row = reader.readMap()) != null) {
                // hardcoded string values. extract to constants
                // maybe values should be taken from values from user input (example userQueryRequest)
                String city = row.get("name");
                int countryId = Integer.parseInt(row.get("country_id"));
                int cityPop = Integer.parseInt(row.get("population"));

                // if the country id from the city table is null -> the data is corrupted
                // suppose we create the city and map to the country, then the country should exist in beforehand
                // think of that like many cities to one country
                // the single possible use-case is when map is empty, also a sign of corrupted data
                Integer countryPop = countries.get(countryId);

                if (countryPop != null) {
                    double percent = populationRatio(cityPop, countryPop);
                    // the use of resource should be made outside of this method, i.e. the resource should be returned
                    System.out.printf("" +
                                    "%s -> %.2f%% din tara\n",
                            city, percent
                    );
                } else {
                    System.out.println(" tara nu e gasit pentru @ " + city);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void readAllDataAtOnce(String file) {

        InputStream is = Main.class
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
