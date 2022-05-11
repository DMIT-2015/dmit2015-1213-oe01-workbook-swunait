package dmit2015.javabean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class ReadCsvFileDemo {

    public static void main(String[] args) {
        ReadCsvFileDemo app = new ReadCsvFileDemo();
        app.run();
    }

    public void run() {
        try {
//            Path csvPath = Path.of(getClass().getClassLoader().getResource("data/csv/electricity-exports-and-imports-data-dictionary.csv").toURI());
            Path csvPath = Path.of("/home/user2015/Downloads/electricity-exports-and-imports-data-dictionary.csv");
//            Path csvPath = Path.of("d:/temp/electricity-exports-and-imports-data-dictionary.csv");

            // Read all the lines from the file into a list of String
            System.out.println("I am reading all the lines from the csv file into a list of String.");
            List<String> allLines = Files.readAllLines(csvPath);
            System.out.println("I am printing each line read from the list.");
//            // Iterate through the list using a standard for loop
//            for (int index = 0; index < allLines.size(); index++) {
//                System.out.println(allLines.get(index));
//            }
//            // Iterate through element in the list using a enhanced for loop
//            for (String currentLine : allLines) {
//                System.out.println(currentLine);
//            }
//            // Iterate through each element in the list using the forEach method of the Stream
//            allLines.stream().forEach(element -> System.out.println(element));
//            // Iterator through each element using a method reference
//            allLines.stream().forEach(System.out::println);

            allLines.forEach(System.out::println);

            System.out.println("\n\n");

            // Read all the contents from the file into a single String
            System.out.println("I am reading all content from the csv file into a String.");
            String fileContent = Files.readString(csvPath);
            System.out.println("I am printing the String content.");
            System.out.println(fileContent);
            System.out.println("\n\n");

            // Read all lines from the file as a Stream
            System.out.println("I am reading all lines from the csv file as a Stream.");
            Stream<String> linesStream = Files.lines(csvPath);
            System.out.println("I am printing all lines from the Stream.");
            linesStream.forEach(System.out::println);
            System.out.println("\n\n");

            // Read one line at a time using a BufferedReader
            System.out.println("I am reading one line at a time and printing each line using a BufferedReader.");
            var reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/data/csv/electricity-exports-and-imports-data-dictionary.csv")));
            String line;
            while ( (line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}