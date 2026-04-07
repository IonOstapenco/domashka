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
                System.out.println(row);

            }
            //inchidem reader, eliberam resursele
            reader.close();

        } catch (Exception e) {
            // in caz ca daca ceva nu a mers - aratam stackul erorii
            e.printStackTrace();
        }
    }

    public static double percentageFrom(double part, double total) {
        if (total == 0) return 0; //avoi division by 0
        return (part / total) * 100;

    }

    // metoda de citire a fisierelor csv
    public static void calculate(String countriesFile, String citiesFile) {
        Map<Integer, Double> countries = readCountries(countriesFile);

        try{
           InputStream is = Main.class
                   .getClassLoader()
                   .getResourceAsStream(citiesFile);
           CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new InputStreamReader(is));

           Map<String, String> row;

           while ((row = reader.readMap()) != null){

               String city  = row.get("name");
               int countryId = Integer.parseInt(row.get("country_id"));
               double cityPop = Double.parseDouble(row.get("population"));

               Double countryPop = countries.get(countryId);

               if (countryPop != null){
                   double percent = percentageFrom(cityPop, countryPop);

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
