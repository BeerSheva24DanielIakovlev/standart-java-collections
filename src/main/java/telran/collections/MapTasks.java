package telran.collections;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MapTasks {

    public static void displayOccurences(String[] strings) {
        //input {"lpm, ab, a, c, cb, cb, c, lpm, lpm"}
        //output:
        //lpm -> 3
        //c -> 2
        //cb -> 2
        //a -> 1
        //ab -> 1

        HashMap<String, Long> occurencesMap = getMapOccurences(strings);
        TreeMap<Long, TreeSet<String>> sortedOccurencesMap = getSortedOccurencesMap(occurencesMap);
        displaySortedOccurencesMap(sortedOccurencesMap);
    }

    public static void displayOccurencesStream(String[] strings) {
        Arrays.stream(strings).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().sorted((e1, e2) -> {
            int res = Long.compare(e2.getValue(), e1.getValue());
            return res == 0 ? e1.getKey().compareTo(e2.getKey()) : res;
        }).forEach(e -> System.out.printf("%s -> %d\n", e.getKey(), e.getValue()));
    }

    private static void displaySortedOccurencesMap(TreeMap<Long, TreeSet<String>> sortedOccurencesMap) {
        sortedOccurencesMap.forEach((occurency, treeSet) -> treeSet.forEach(s -> System.out.printf("%s -> %d \n", s, occurency)));
    }

    private static TreeMap<Long, TreeSet<String>> getSortedOccurencesMap(HashMap<String, Long> occurencesMap) {
        TreeMap<Long, TreeSet<String>> result = new TreeMap<>(Comparator.reverseOrder());
        occurencesMap.entrySet().forEach(e -> result.computeIfAbsent(e.getValue(), k -> new TreeSet<>()).add(e.getKey()));
        return result;
    }

    private static HashMap<String, Long> getMapOccurences(String[] strings) {
        HashMap<String, Long> result = new HashMap<>();
        Arrays.stream(strings).forEach(s -> result.merge(s, 1l, Long::sum));
        return result;
    }

    public static Map<Integer, Integer[]> getGroupingByNumberOfDigits(int[][] array) {
        Map<Integer, List<Integer>> map =  streamOfNumbers(array).collect(Collectors.groupingBy(n -> Integer.toString(n).length()));

        return map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().toArray(Integer[]::new)));
    }

    public static Map<Integer, Long> getDistributionByNymberOfDigits(int[][] array) {
        return streamOfNumbers(array).collect(Collectors.groupingBy(n -> Integer.toString(n).length(), Collectors.counting()));
    }

    private static Stream<Integer> streamOfNumbers(int[][] array) {
        return Arrays.stream(array).flatMapToInt(Arrays::stream).boxed();
    }

    public static void displayDigitsDistribution() {
        IntStream digitStream = new Random().ints(1_000_000, 0, Integer.MAX_VALUE)
        .flatMap(n -> Integer.toString(n).chars().map(c -> c - '0'));

        Map<Integer, Long> digitCounts = digitStream
            .boxed()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        digitCounts.entrySet().stream()
            .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
            .forEach(e -> System.out.printf("%d -> %d\n", e.getKey(), e.getValue()));
    }

    public static ParenthesesMaps getParenthesesMaps(Character[][] openCloseParentheses) {
        Map<Character, Character> openCloseMap = Arrays.stream(openCloseParentheses)
            .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));

        Map<Character, Character> closeOpenMap = Arrays.stream(openCloseParentheses)
            .collect(Collectors.toMap(pair -> pair[1], pair -> pair[0]));

        return new ParenthesesMaps(openCloseMap, closeOpenMap);
    }
}
