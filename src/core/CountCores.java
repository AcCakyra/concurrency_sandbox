package core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class CountCores {
    /**
     * To calc count of cores on processor this program tries
     * to submit some CPU job to different count of threads.
     * If x count of threads can calc this job faster
     * than x - 1, so it <b>probably</b> means that we have at least x cores
     * <p>
     * Example (running on intel i7-10510U):
     * 1 - 5853553436
     * 2 - 2965796708
     * 3 - 2007691469
     * 4 - 1601507642
     * 5 - 1541059089
     * 6 - 1491919898
     * 7 - 1507003479
     * 8 - 1492863273
     * </p>
     * So depending on that my machine has 4 real cores.
     */

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 8; i++) {
            System.out.println(i + " - " + executeJob(i));
        }
    }

    private static long executeJob(int threadsCount) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threadsCount);

        long start = System.nanoTime();
        for (int i = 0; i < 256; i++) {
            service.submit(() -> {
                int a = 0;
                for (long j = 0; j < 25_000_000; j++) {
                    j++;
                    a = (int) ((j * j) % 1000);
                }
                if (a < -12) {
                    System.out.println(a);
                }
            });
        }

        service.shutdown();
        service.awaitTermination(1, TimeUnit.HOURS);

        return System.nanoTime() - start;
    }
}
