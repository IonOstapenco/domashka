import com.opencsv.CSVReader;
import com.opencsv.CSVReaderHeaderAware;

import java.util.HashMap;
import java.util.Map;
import java.io.InputStreamReader;
import java.io.InputStream;


public class Main {
    public static void main(String[] args) {
        //calculeaza, utilizand ambele fisierre csv
        calculate("countries.csv", "cities.csv");
        System.out.println("fisierele prelucrate sunt: ");
        readCSV("countries.csv"); //doar citeste
        readCSV("cities.csv"); // doar citeste

    }

    //cream colectia Map
    public static Map<Integer, Double> readCountries(String filename) {
        Map<Integer, Double> countries = new HashMap<>();

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
                double population = Double.parseDouble(row.get("population"));

                countries.put(id, population);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countries;
    }


    // metoda de citire a fisierelor csv
    public static void readCSV(String filename) {
        // punem ca sa se citeasca denumirea fisierelor
        System.out.println("\n===== FILE: " + filename + " =====");
        try {
            //Main.class.getClassLoader  - permite de a cauta fisier in mapa scr/main/rescources
            InputStream is = Main.class
                    .getClassLoader()
                    .getResourceAsStream(filename);

            //verificarea daca fisierul nu este gasit
            // instead of a nullcheck, use try with resources
            // try (InputStream fis = new FileInputStream("filename.csv") {
            //     ....
            // }
            if (is == null) {
                System.out.println("fisier " + filename + "nu este !");
                return; // iesim din functie, ca sa nu fie eroarea NullPointer
            }
//cream reader cu maintenance header
            CSVReaderHeaderAware reader =
                    new CSVReaderHeaderAware(new InputStreamReader(is));
//fiecare csv rand va fi Map<numele header, Valoare>
            Map<String, String> row;
//citim rand cu rand
            while ((row = reader.readMap()) != null) {
                //afisam rand
                //could use a StringBuilder object to sout in one instance
                System.out.println(row);

            }
            //inchidem reader, eliberam resursele
            reader.close();

        } catch (Exception e) {
            // in caz ca daca ceva nu a mers - aratam stackul erorii
            e.printStackTrace();
        }
    }

    public static double populationRatio(double cityPopulation, double countryPopulation) {
        // unreasonable the population shouldn't be null on invocation
        if (countryPopulation == 0) return 0; //avoi division by 0
        return (cityPopulation / countryPopulation) * 100;

    }

    // metoda de citire a fisierelor csv
    // this method should return a list of population ratios 
    // the resources will be used to render the results on frontend/java swing
    // calculate what?? 
    public static void calculate(String countriesFile, String citiesFile) {
        Map<Integer, Double> countries = readCountries(countriesFile);

        try{
            // try to use FileInputStream instead of classloaded resources
            // retain InputStream, becuase FileInputStream implements InputStream interface
           InputStream is = Main.class
                   .getClassLoader()
                   .getResourceAsStream(citiesFile);
           CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new InputStreamReader(is));

           Map<String, String> row;

           while ((row = reader.readMap()) != null){
                // hardcoded string values. extract to constants
               // maybe values should be taken from values from user input (example userQueryRequest)
               String city  = row.get("name");
               int countryId = Integer.parseInt(row.get("country_id"));
               double cityPop = Double.parseDouble(row.get("population"));

               // if the country id from the city table is null -> the data is corrupted
               // suppose we create the city and map to the country, then the country should exist in beforehand
               // think of that like many cities to one country
               // the single possible use-case is when map is empty, also a sign of corrupted data
               Double countryPop = countries.get(countryId);

               if (countryPop != null){
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
        } catch (Exception e){
            e.printStackTrace();
        }

    }


}
