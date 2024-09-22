package telran.collections;

import java.util.*;

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
}
