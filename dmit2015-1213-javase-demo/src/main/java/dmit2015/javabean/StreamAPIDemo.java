package dmit2015.javabean;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Demonstration on how to use the Java 8+ Stream API.
 * <p>
 * A stream is a sequence of elements.
 * A stream pipeline consists of a stream created from a data source, zero or more intermediate methods, and a final terminal method.
 * The methods are invokes using a stream pipeline that consist of a source(e.g., a list, a set, or an array),
 * a method that creates a stream, zero or more intermediate methods, and a final terminal method.
 * Example:
 * set.stream().limit(10).distinct().count()
 * <p>
 * <p>
 * <p>
 * The methods in the Stream interface are divided into three groups:
 * intermediate methods,
 * terminal methods,
 * class-level (static) methods.
 * An intermediate method transforms the stream into another stream.
 * A terminal method returns a result or performs actions. After a terminal method is executed, the stream is closed automatically.
 * A class-level (static) method creates a stream.
 * <p>
 * Intermediate operation methods:
 * distinct 	- Return a stream consisting of distinct elements from the stream
 * skip 		- Return a stream consisting of the remaining elements in this stream after discarding the first n elements
 * limit 		- Return a stream consisting of the first n elements from this stream
 * filter 		- Return a stream consisting of the elements that pass the test implemented by the function
 * sorted 		- Return a stream consisting of the elements of this stream sorted by natural order or by a comparator function
 * map 		- Return a stream consisting of the results of applying the functions to the elements in the stream
 * <p>
 * Terminal operation methods:
 * count 		- Return the number of elements in the stream
 * max 		- Return the maximum element in this stream based on the comparator
 * min 		- Return the minimum element in this stream based on the comparator
 * findFirst 	- Return the first element from this stream
 * findAny 	- Return any element from this stream
 * allMatch 	- Return true if all the elements in this stream match the predicate
 * anyMatch 	- Return true if one element in this stream matches the predicate
 * noneMatch 	- Return true if no element in this stream matches the predicate
 * forEach 	- Execute the provided function once for each element in the stream
 * reduce 		- Reduces the elements in this stream to a value using the identity and an associative accumulation function.
 * Returns an Optional result of an accumulation operation using the values in the stream.
 * collect 	- Perform a mutable reduction operation on the elements of this stream using a Collector
 * toArray 	- Return an array consisting of the elements in this stream
 * <p>
 * Class-level (static) methods:
 * empty		- Return an empty sequential stream
 * of			- Return a stream consisting of the specified values
 * concat		- Returns a lazily concatenated stream
 *
 *
 * To use Jakarta JSON Binding 2.0 in a Java SE app add the following dependency to the `pom.xml` file of a maven project.
 <dependency>
     <groupId>org.eclipse</groupId>
     <artifactId>yasson</artifactId>
     <version>2.0.4</version>
 </dependency>
 *
 * @author Sam Wu
 * @version 2022.05.12
 */

public class StreamAPIDemo {

//    List<VideoGame> gameList = Arrays.asList(
//            new VideoGame("Diablo III Eternal Collection", GamingPlatform.NINTENDO, 34.99, 12919269L),
//            new VideoGame("NBA 2K20", GamingPlatform.PLAYSTATION, 49.99, 13720461L),
//            new VideoGame("NBA 2K20", GamingPlatform.NINTENDO, 49.99, 13720465L),
//            new VideoGame("NBA 2K20", GamingPlatform.XBOX, 49.99, 13720462L),
//            new VideoGame("Forza Horizon 4 (Xbox One)", GamingPlatform.XBOX, 39.99, 12612447L),
//            new VideoGame("Final Fantasy X/X-2 HD Remaster (Switch)", GamingPlatform.NINTENDO, 34.99, 13208397L),
//            new VideoGame("The Outer Worlds (PS4)", GamingPlatform.PLAYSTATION, 49.99, 13642197L),
//            new VideoGame("Kingdom Hearts 3 (PS4)", GamingPlatform.PLAYSTATION, 19.99, 10255421L),
//            new VideoGame("Overwatch Legendary Edition (Switch)", GamingPlatform.NINTENDO, 34.99, 13899355L),
//            new VideoGame("WWE 2K20 (PS4)", GamingPlatform.PLAYSTATION, 39.99, 13836134L),
//            new VideoGame("Kingdom Hearts 3 (Xbox One)", GamingPlatform.XBOX, 19.99, 10255666L),
//            new VideoGame("Dragon Quest Builders 2 (PS4)", GamingPlatform.PLAYSTATION, 29.99, 13414143L),
//            new VideoGame("Battlefield 2042 (PC)", GamingPlatform.PC_GAMING, 54.99, 15547074L),
//            new VideoGame("Star Wars Jedi: Fallen Order (PC)", GamingPlatform.PC_GAMING, 19.97, 13508834L)
//    );

    private List<VideoGame> gameList;
    private Map<Long, VideoGame> gameMap;

    public StreamAPIDemo() {

        loadCsvData();

        // Convert the List<VideoGame> to a Map<String, VideoGame> where the webCode of the VideoGame is the key for the map
        // https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/stream/Collectors.html
        gameMap = gameList.stream().collect(Collectors.toMap(VideoGame::getWebCode, Function.identity()));
    }

    private void loadCsvData() {
        try {
//            Path jsonPath = Path.of(getClass().getClassLoader().getResource("data/csv/VideoGameList.csv").toURI());
//            Path csvPath = Path.of("d:/temp/VideoGameList.csv");
            Path csvPath = Path.of("/home/user2015/Downloads/VideoGameList.csv");
            Stream<String> linesStream = Files.lines(csvPath);
            gameList = linesStream
                    .skip(1)
                    .map(line -> VideoGame.parseCsv(line).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private void writeVideoGameListToCsvFile() {
        try {
//            Path csvPath = Path.of("d:/temp/VideoGameList.csv");
            Path csvPath = Path.of("/home/user2015/Downloads/VideoGameList.csv");

//            try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8)) {
//                writer.write(VideoGame.CSV_HEADER);
//                gameList.forEach(game -> {
//                    try {
//                        writer.write(game.toCsv());
//                        writer.newLine();
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//            }

            Files.writeString(csvPath, VideoGame.CSV_HEADER);
            List<String> linesToWrite = gameList.stream().map(item -> item.toCsv()).toList();
            Files.write(csvPath, linesToWrite, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
            System.out.println("Finished writing list of games to the CSV " + csvPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private void writeVideoGameListToJsonFile() {
        // Write the list of games to a JSON file using Jakarta JSON Binding 2.0
        try {
//            Path jsonPath = Path.of(getClass().getClassLoader().getResource("data/json/VideoGames.json").toURI());
            Path jsonPath = Path.of("d:/temp/VideoGameList.json");
            Jsonb jsonb = JsonbBuilder.create();
//            jsonb.toJson(games, Files.newBufferedWriter(jsonPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE));
			jsonb.toJson(gameList, Files.newBufferedWriter(jsonPath));
            System.out.println("Finished writing list of games to the JSON file " + jsonPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void writeVideoGameMapToJsonFile() {
        // Write the list of games to a JSON file using Jakarta JSON Binding 2.0
        try {
//            Path jsonPath = Path.of(getClass().getClassLoader().getResource("data/json/VideoGames.json").toURI());
            Path jsonPath = Path.of("d:/temp/VideoGameMap.json");
            Jsonb jsonb = JsonbBuilder.create();
//            jsonb.toJson(games, Files.newBufferedWriter(jsonPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE));
            jsonb.toJson(gameMap, Files.newBufferedWriter(jsonPath));
            System.out.println("Finished writing map of games to the JSON file " + jsonPath.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void readVideoGameListFromCsvFile() {
        // Write the list of games to a JSON file using Jakarta JSON Binding 2.0
        try {
//            Path jsonPath = Path.of(getClass().getClassLoader().getResource("data/csv/VideoGameList.csv").toURI());
//            Path csvPath = Path.of("d:/temp/VideoGameList.csv");
            Path csvPath = Path.of("/home/user2015/Downloads/VideoGameList.csv");

            Stream<String> linesStream = Files.lines(csvPath);
            List<VideoGame> importedGameList = linesStream
                    .skip(1)
                    .map(line -> VideoGame.parseCsv(line).orElse(null))
                    .filter(Objects::nonNull)
                    .toList();
            System.out.println("The following list of games were read from the CSV file " + csvPath.getFileName());
            printVideoGameList(importedGameList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private void readVideoGameListFromJsonFile() {
        // Write the list of games to a JSON file using Jakarta JSON Binding 2.0
        try {
//            Path jsonPath = Path.of(getClass().getClassLoader().getResource("data/json/VideoGameList.json").toURI());
            Path jsonPath = Path.of("d:/temp/VideoGameList.json");
            Jsonb jsonb = JsonbBuilder.create();
            List<VideoGame> importedGameList = jsonb.fromJson(Files.newBufferedReader(jsonPath, StandardCharsets.UTF_8), new ArrayList<VideoGame>(){}.getClass().getGenericSuperclass());
            System.out.println("The following list of games were read from the JSON file " + jsonPath.getFileName());
            printVideoGameList(importedGameList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    private void readVideoGameMapFromJsonFile() {
        // Write the list of games to a JSON file using Jakarta JSON Binding 2.0
        try {
//            Path jsonPath = Path.of(getClass().getClassLoader().getResource("data/json/VideoGameMap.json").toURI());
            Path jsonPath = Path.of("d:/temp/VideoGameMap.json");
            Jsonb jsonb = JsonbBuilder.create();
            Map<Long, VideoGame> importedGameMap = jsonb.fromJson(Files.newBufferedReader(jsonPath, StandardCharsets.UTF_8), new HashMap<Long, VideoGame>(){}.getClass().getGenericSuperclass());
            System.out.println("Finished reading map of games from the JSON file " + jsonPath.getFileName());
            printVideoGameMap(importedGameMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void writeOneVideoGamesToJsonFile() {
        // Write the list of games to a JSON file using Jakarta JSON Binding 2.0
        try {
//            Path jsonPath = Path.of(getClass().getClassLoader().getResource("data/json/VideoGame.json").toURI());
            Path jsonPath = Path.of("d:/temp/VideoGame.json");
            Jsonb jsonb = JsonbBuilder.create();
            VideoGame singleGame = gameList.stream().findAny().get();
//            jsonb.toJson(singleGame, Files.newBufferedWriter(jsonPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE));
            jsonb.toJson(singleGame, Files.newBufferedWriter(jsonPath));
            System.out.println("Finished writing a single game to a JSON file " + jsonPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void readOneVideoGamesFromJsonFile() {
        // Write the list of games to a JSON file using Jakarta JSON Binding 2.0
        try {
//            Path jsonPath = Path.of(getClass().getClassLoader().getResource("data/json/VideoGame.json").toURI());
            Path jsonPath = Path.of("d:/temp/VideoGame.json");
            Jsonb jsonb = JsonbBuilder.create();
            VideoGame importedGame = jsonb.fromJson(Files.newBufferedReader(jsonPath, StandardCharsets.UTF_8), VideoGame.class);
            System.out.println("Finishing reading one game from a JSON file " + jsonPath.getFileName());
            System.out.println(importedGame);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void printVideoGameList(List<VideoGame> gameList) {
//        // Print each VideoGame in gameList using a `for` loop.
//        for (int index = 0; index < gameList.size(); index++) {
//            System.out.println(gameList.get(index));
//        }

//        // Print each VideoGame in gameList using a `enhanced for` loop.
//        for (VideoGame game : gameList) {
//            System.out.println(game);
//        }

//        // Print each VideoGame in gameList using the `forEach` method of the collection object using a lambda expression syntax.
//        gameList.forEach(game -> System.out.println(game)); // Syntax using a lambda expression

        // Print each VideoGame in gameList using the `forEach` method of the collection object and a Static Method Reference syntax
        // https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html
        gameList.forEach(System.out::println);  // Syntax using a static Method Reference syntax
    }

    public void printVideoGameMap(Map<Long, VideoGame> gameMap) {
//        // Iterate the Entries of a Map using an `enhanced for` loop
//        for (Map.Entry<Long, VideoGame> entry : gameMap.entrySet()) {
//            Long webCode = entry.getKey();
//            VideoGame currentGame = entry.getValue();
//            System.out.println("Key: " + webCode);
//            System.out.println("Value: " + currentGame);
//        }

        // Iterate the Entries of a Map using the `forEach` method of the collection object
        gameMap.entrySet().forEach(item -> {
            System.out.println("Key: " + item.getKey());
            System.out.println("Value: " + item.getValue());
        });
    }

    public List<VideoGame> findByGamingPlatform(GamingPlatform platform) {
        return gameList.stream()
                .filter(currentGame -> currentGame.getPlatform() == platform)
                .toList();
    }

    public List<String> mapToTitle() {
        return gameList.stream()
                .map(VideoGame::getTitle)
                .sorted()
                .toList();
    }

    public List<Double> mapToDistinctPrices() {
        return gameList.stream()
                .map(VideoGame::getPrice)
                .distinct()
                .sorted()
                .toList();
    }

    public Set<Double> mapToPriceSet() {
        return gameList.stream()
                .map(VideoGame::getPrice)
//                .collect(Collectors.toSet());
                .collect(Collectors.toCollection(TreeSet::new));
    }

    public List<VideoGame> sortByPlatformThenTitleThenPriceDescending() {
        var customComparator = Comparator.comparing(VideoGame::getPlatform).
                 thenComparing(VideoGame::getTitle)
                .thenComparing(VideoGame::getPrice).reversed();
        return gameList.stream()
                .sorted(customComparator)
                .toList();
    }

    public Optional<VideoGame> findFirstByTitleAndPlatform(String title, GamingPlatform platform) {
        return gameList.stream()
                .filter(item -> item.getTitle().equalsIgnoreCase(title) )
                .filter(item -> item.getPlatform() == platform)
                .findFirst();
    }

    public double sumAllPrices() {
        return gameList.stream()
                .mapToDouble(VideoGame::getPrice)
                .sum();
    }

    public double sumPricesByPlatform(GamingPlatform platform) {
//        return gameList.stream()
//                .filter(item -> item.getPlatform() == platform)
//                .mapToDouble(VideoGame::getPrice)
//                .sum();

//        return gameList.stream()
//                .filter(item -> item.getPlatform() == platform)
////                .collect(Collectors.summingDouble(item -> item.getPrice());
//                .collect(Collectors.summingDouble(VideoGame::getPrice));

        return gameList.stream()
                .filter(item -> item.getPlatform() == platform)
                .collect(Collectors.reducing(0.00, VideoGame::getPrice, (item1,item2) -> item1 + item2));
    }

    public Optional<VideoGame> highestPriceGame() {
        return gameList.stream()
                .collect(Collectors.maxBy(Comparator.comparing(VideoGame::getPrice)));
    }

    public Optional<VideoGame> lowestPriceGame() {
        return gameList.stream()
                .collect(Collectors.minBy(Comparator.comparing(VideoGame::getPrice)));
    }

    public double highestPriceByPlatform(GamingPlatform platform) {
        return gameList.stream()
                .filter(item -> item.getPlatform() == platform)
                .mapToDouble(VideoGame::getPrice)
                .max()
                .orElseThrow();
    }

    public double lowestPriceByPlatform(GamingPlatform platform) {
        return gameList.stream()
                .filter(item -> item.getPlatform() == platform)
                .mapToDouble(VideoGame::getPrice)
                .min()
                .orElseThrow();
    }

    public double averagePriceByPlatform(GamingPlatform platform) {
        return gameList.stream()
                .filter(item -> item.getPlatform() == platform)
                .mapToDouble(VideoGame::getPrice)
                .average()
                .orElseThrow();
    }

    public Map<GamingPlatform, List<VideoGame>> groupByPlatform() {
        return gameList.stream()
                .collect(Collectors.groupingBy(VideoGame::getPlatform));
    }
    void demoProcessingObjects() {
        // Display all Nintendo games
        System.out.println("------------------------------------------------------------------------");
        System.out.println("All Nintendo games on sale");
        List<VideoGame> nintendoGames = findByGamingPlatform(GamingPlatform.NINTENDO);
        printVideoGameList(nintendoGames);

        // Display the title of each game
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Game Titles");
        List<String> gameTitles = mapToTitle();
        gameTitles.forEach(System.out::println);

        // Display the unique game prices
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Distinct List of Game Prices");
        List<Double> distinctGamePrices = mapToDistinctPrices();
        distinctGamePrices.forEach(System.out::println);

        // Display the unique game prices
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Set of Game Prices");
        Set<Double> priceSet = mapToPriceSet();
        priceSet.forEach(System.out::println);

        // Display video games sorted by Platform then by Title then descending by price
        System.out.println("------------------------------------------------------------------------");
        System.out.println("Games sorted by Platform, Title, and Price (descending)");
        List<VideoGame> sortedVideoGameByPlatformThenPrice = sortByPlatformThenTitleThenPriceDescending();
        printVideoGameList(sortedVideoGameByPlatformThenPrice);

        // Find the first XBox game sale NBA 2K20
        System.out.println("------------------------------------------------------------------------");
        Optional<VideoGame> optionalGame = findFirstByTitleAndPlatform("NBA 2K20", GamingPlatform.PC_GAMING);
        if (optionalGame.isPresent()) {
            VideoGame currentGame = optionalGame.get();
            System.out.println("Found NBA 2K20 for PC Gaming: " + currentGame);
        } else {
            System.out.println("NBA 2K20 is not available for PC Gaming.");
        }

        // Any games less than $20?
        System.out.println("------------------------------------------------------------------------");
        boolean isAnyGameLessThan20 = gameList.stream().anyMatch(item -> item.getPrice() < 20);
        System.out.println("Any games less than $20 " + isAnyGameLessThan20);

        // All games less than $50?
        System.out.println("------------------------------------------------------------------------");
        boolean isAllGamesLessThan50 = gameList.stream().allMatch(item -> item.getPrice() < 50);
        System.out.println("All games less than $50 " + isAllGamesLessThan50);

        // No PC Games on sale?
        System.out.println("------------------------------------------------------------------------");
        boolean isNoPCGamesOnSale = gameList.stream().noneMatch(item -> item.getPlatform() == GamingPlatform.PC_GAMING);
        System.out.println("No PC Games for sale " + isNoPCGamesOnSale);

        // sum, max, min, average in a Stream
        System.out.println("------------------------------------------------------------------------");
        double sumNintendoGamePrice = sumAllPrices();
        System.out.println("The sum of all games is: " + sumNintendoGamePrice);

        System.out.println("------------------------------------------------------------------------");
        double maxNintendoGamePrice = highestPriceByPlatform(GamingPlatform.NINTENDO);
        System.out.println("The maximum price for a Nintendo game on sale is: " + maxNintendoGamePrice);

        System.out.println("------------------------------------------------------------------------");
        double minNintendoGamePrice = lowestPriceByPlatform(GamingPlatform.NINTENDO);
        System.out.println("The minimum price for a Nintendo game on sale is: " + minNintendoGamePrice);

        System.out.println("------------------------------------------------------------------------");
        double sumPlaystationGamePrice = sumPricesByPlatform(GamingPlatform.PLAYSTATION);
        System.out.println("The sum of all Playstation games is: " + sumPlaystationGamePrice);

        // Collecting the result of a stream
        // Double the price for each game and collect the result in a List
//		List<VideoGame> doublePriceGames = games.stream()
//			.map(item -> {
//				item.setPrice( item.getPrice() * 2);
//				return item;
//			})
//			.collect(Collectors.toList());

        System.out.println("------------------------------------------------------------------------");
        // Collect the result in a sorted Map with title for key and price for value
        Map<String, Double> sortedTitleMap = gameList.stream()
                .sorted(Comparator.comparingDouble(VideoGame::getPrice).reversed())
                .collect(Collectors.toMap(VideoGame::getTitle, VideoGame::getPrice,
                        (oldValue, newValue) -> oldValue, // Merge function used to resolve collisions between values associated with the same key
                        LinkedHashMap::new));
        sortedTitleMap.entrySet().stream().forEach(item -> System.out.println(item.getKey() + ":" + item.getValue()));
        System.out.println("\n\n");

        System.out.println("------------------------------------------------------------------------");
        // Concatenate the elements of a stream into a String
        String allWebCodeCsv = gameList.stream()
                .map(item -> String.valueOf(item.getWebCode()))    // value must be a String
                .sorted()
                .collect(Collectors.joining(","));
        System.out.println(allWebCodeCsv);

        // Return a new List of VideoGame for every even location in the collection
        List<VideoGame> evenGameList = IntStream.range(0, gameList.size())
                .filter(index -> index % 2 == 0)
                .mapToObj(index -> gameList.get(index))
                .collect(Collectors.toList());

        // Convert a List to an Array
        VideoGame[] evenGameArray = evenGameList.toArray(VideoGame[]::new);
        System.out.println("Even index games");
        Arrays.stream(evenGameArray).forEach(System.out::println);

        // Summarization Collectors:
        double sumGST = gameList.stream()
                .collect(Collectors.reducing(0.0, item -> item.getPrice() * 0.05, (item1, item2) -> item1 + item2));
        System.out.println("The total gst of all games are: " + sumGST);

        //	averagingInt(), averagingLong(), averagingDouble()
        double averagePrice = gameList.stream()
                .collect(Collectors.averagingDouble(VideoGame::getPrice));
        System.out.println("The average price of a game is: " + averagePrice);

        //	counting()
        double countGames = gameList.stream()
                .collect(Collectors.counting());
        System.out.println("The number of games on sale: " + countGames);

        //	maxBy(), minBy()
        double maxPrice = gameList.stream()
                .collect(Collectors.maxBy(Comparator.comparing(VideoGame::getPrice)))
                .orElseThrow()
                .getPrice();
        System.out.println("The max price for a game: " + maxPrice);

        //	summarizingInt(), summarizingLong(), summarizingDouble()
        DoubleSummaryStatistics gameStatistics = gameList.stream()
                .collect(Collectors.summarizingDouble(VideoGame::getPrice));
        System.out.println("Price statistics: " + gameStatistics);

        // Grouping: groupingBy()
        Map<GamingPlatform, List<VideoGame>> groupGamesByPlatform = gameList.stream()
                .collect(Collectors.groupingBy(VideoGame::getPlatform));
        System.out.println(groupGamesByPlatform);

    }

    void demoProcessingValues() {
        // Generate a stream with 7 numbers between 1 and 50
        IntStream lottoMaxStream = new Random().ints(7, 1, 50 + 1);
        // Sort stream of numbers in natural order sequence and return an array containing the elements of this stream
        int[] sortedRandomNumbers = lottoMaxStream.sorted().toArray();
        System.out.print("Lotto MAX quick pick numbers: ");
        Arrays.stream(sortedRandomNumbers).forEach(number -> System.out.print(number + " "));

        // Sort stream of numbers in natural order sequence and return an array containing the elements of this stream
//		int[] reversedSortedRandomNumbers =
//				lottoMaxStream
//				.mapToObj(Integer::valueOf)
//				.sorted(Comparator.reverseOrder())
//				.mapToInt(item -> item.intValue())
//				.toArray();
//		Arrays.stream(reversedSortedRandomNumbers).forEach((number -> System.out.print(number + " ")));

        System.out.println();

        // Declare and initial an array of game titles
        List<String> games = Arrays.asList(
                "Mario Kart 8 Deluxe",
                "Super Mario Party",
                "Super Mario Odyssey",
                "New Super Mario Bros. U Deluxe",
                "Mario & Sonic at the Olympic Games: Tokyo 2021",
                "Super Mario Maker 2",
                "Mario + Rabbids Kingdom Battle",
                "Luigi's Mansion 3",
                "Mario Tennis Aces",
                "Super Smash Bros Ultimate",
                "Mario Party Superstars",
                "Mario Kart 8 Deluxe"
        );

        // Use the sort() method to sort the elements in the sequence
        System.out.println("All games in sorted sequence:");
        // Display all the games in sorted sequence
        games.stream()
                .sorted()                                                // sort elements using natural order
//				.sorted(String::compareToIgnoreCase) 					// sort elements using method reference
//				.sorted((lhs, rhs) -> lhs.compareToIgnoreCase(rhs))		// sort elements using a custom comparator
                .forEach(instance -> System.out.println("\t" + instance));

        // Use the skip() and limit() methods to implement pagination
        // Display the game titles in sets of 5 games per page
        final int PAGE_SIZE = 5;
        final int TOTAL_PAGES = (int) Math.ceil((double) games.size() / PAGE_SIZE);
        for (int pageNumber = 1; pageNumber <= TOTAL_PAGES; pageNumber++) {
            String message = String.format("Page %d of %d of games: ", pageNumber, TOTAL_PAGES);
            System.out.println(message);
            games.stream()
                    .skip((pageNumber - 1) * PAGE_SIZE)
                    .limit(PAGE_SIZE)
                    .forEach(instance -> System.out.println("\t" + instance));
        }

        // Use the filter() method to return a new stream with elements that matches the given predicate
        System.out.print("\nGame titles with Super keyword: \n");
        games.stream()
                .filter(instance -> instance.contains("Super"))
                .sorted()
                .forEach(instance -> System.out.println("\t" + instance));

        // Use the map() method to return a new stream with elements in upper case
        System.out.print("\nGame titles in upper case: \n");
        games.stream()
                .sorted()
                //.map(String::toUpperCase)
                .map(instance -> instance.toUpperCase())
                .forEach(instance -> System.out.println("\t" + instance));


        // Use the min()/max() methods to get the minimum/maximum element in the stream
        System.out.println("\nThe shortest game title: " + games.stream().min(String::compareTo).get());
        System.out.println("\nThe longest game title: " + games.stream().max(String::compareTo).get());

        // Use the anyMatch() method to determine if there are any element that matches the provided predicate
        System.out.println("\nAre there any games with contain the Mario title? "
                + games.stream().anyMatch(instance -> instance.contains("Mario")));

        // Use the allMatch() method to determine if all elements match the provided predicate
        System.out.println("\nDoes all games contains Mario? "
                + games.stream().allMatch(instance -> instance.contains("Mario")));

        // Use the noneMatch() method to determine if there are no elements that matches the provided predicate
        System.out.println("\nAre there any that does not contain Mario? "
                + games.stream().noneMatch(instance -> instance.contains("Mario")));

        // Use distinct() terminal method to return a new stream of distinct elements
        System.out.println("\nNumber of distinct game titles: " + games.stream().distinct().count());

        // Use the findFirst() method to find the first element in the stream
        var startsWithFilterValue = "Super Mario";
        Optional<String> firstMatch = games.stream()
                .filter(instance -> instance.startsWith(startsWithFilterValue))
                .findFirst();
        if (firstMatch.isPresent()) {
            var firstMatchGame = firstMatch.get();
            System.out.printf("\nFirst game title that starts with %s: %s", startsWithFilterValue, firstMatchGame);
        } else {
            System.out.printf("\nThere is no game title that starts with %s.", startsWithFilterValue);
        }


        // Use the findAny() method to return any elements in the stream
        var containsFilterValue = "Bros";
        Optional<String> anyMatch = games.stream()
                .filter(instance -> instance.contains(containsFilterValue))
                .findAny();
        if (anyMatch.isPresent()) {
            var anyMatchGame = anyMatch.get();
            System.out.printf("\nAny game title that contains %s: %s", containsFilterValue, anyMatchGame);
        } else {
            System.out.printf("\nThere is no game title that contains %s.", containsFilterValue);
        }

        // Use the takeWhile() method to filter data before a condition
        System.out.print("\nGame titles before start with Super Mario:\n");
        games.stream()
                .sorted()
                .takeWhile(instance -> !instance.startsWith("Super Mario"))
                .forEach(instance -> System.out.println("\t" + instance));

        // Use the dropWhile() method to filter data after a condition
        System.out.print("\nGame titles after start with Super Mario:\n");
        games.stream()
                .sorted()
                .dropWhile(instance -> !instance.startsWith("Super Mario"))
                .forEach(instance -> System.out.println("\t" + instance));


        // Convert a Stream to Array
        String[] superMarioTitles = games.stream().filter(instance -> instance.contains("Super Mario")).toArray(String[]::new);
        System.out.println(Arrays.toString(superMarioTitles));

    }

    public void run() {
//		demoProcessingValues();

        demoProcessingObjects();

//        writeVideoGameListToCsvFile();
//        readVideoGameListFromCsvFile();

//        writeVideoGameListToJsonFile();
//        readVideoGameListFromJsonFile();
//
//        writeOneVideoGamesToJsonFile();
//        readOneVideoGamesFromJsonFile();
//
//        writeVideoGameMapToJsonFile();
//        readVideoGameMapFromJsonFile();

    }

    public static void main(String[] args) {

        StreamAPIDemo app = new StreamAPIDemo();
        app.run();

    }
}