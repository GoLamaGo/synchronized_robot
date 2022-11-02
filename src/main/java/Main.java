import java.util.*;
import java.util.concurrent.Executors;

public class Main {

    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) {
        int amountOfThreads = 1000;
        char r = 'R';

        var pool = Executors.newFixedThreadPool(1000);

        for (int t = 0; t < amountOfThreads; t++) {
            pool.submit(
                    new Thread(() -> {
                        String route = generateRoute("RLRFR", 100);
                        int amountOfR = 0;
                        for (int i = 0; i < route.length(); i++) {
                            if (route.charAt(i) == r) {
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
                    }
                    )
            );
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
