import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class MapUtils {
    public static Map<Integer, String> getSubMap(TreeMap<Integer, String> map) {
        if (map.firstKey() % 2 != 0) {
            return map.descendingMap().tailMap(map.firstKey() + 4, true);
        } else {
            return map.descendingMap().headMap(map.lastKey() - 4, true);
        }
    }
}

/* Do not modify code below */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        TreeMap<Integer, String> map = new TreeMap<>();
        Arrays.stream(scanner.nextLine().split("\\s")).forEach(s -> {
            String[] pair = s.split(":");
            map.put(Integer.parseInt(pair[0]), pair[1]);
        });

        Map<Integer, String> res = MapUtils.getSubMap(map);
        res.forEach((k, v) -> System.out.println(k + " : " + v));
    }
}