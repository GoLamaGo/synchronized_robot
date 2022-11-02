import java.util.*;
import java.util.concurrent.Executors;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new TreeMap<>();     // TreeMap для сортировки по key

    public static void main(String[] args) {
        int amountOfThreads = 1000;
        String letters = "RLRFR";
        int stringLength = 100;
        char charToFind = 'R';
        var pool = Executors.newFixedThreadPool(amountOfThreads);

        Runnable runnable = () -> {
            String route = generateRoute(letters, stringLength);
            int amountOfR = 0;
            for (int i = 0; i < route.length(); i++) {
                if (route.charAt(i) == charToFind) {
                    amountOfR++;
                }
            }
            synchronized (sizeToFreq) {
                if (sizeToFreq.containsKey(amountOfR)) {
                    sizeToFreq.put(amountOfR, sizeToFreq.get(amountOfR) + 1);
                } else {
                    sizeToFreq.put(amountOfR, 1);
                }
            }
        };

        for (int t = 0; t < amountOfThreads; t++) {
            pool.submit(new Thread(runnable));
        }

        pool.shutdown();

        sizeToFreq.forEach((x, y) -> System.out.println(x + " (" + y + " раз)"));
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
