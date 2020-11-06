package cache_lines;

public class CacheLineSize {
    /**
     * By iteration throw some data in memory we can measure size of cache line.
     * When step size equal or bigger than your cache line,
     * each access to memory will cause cache miss (in L1 at least).
     * It cause reading a new line into L1 cache.
     * So the benchmark results should get slower and slower until we find size of cache line
     * and then it became constant.
     * <p>
     * Example (running on intel i7-10510U):
     * 16 bytes - 165
     * 24 bytes - 221
     * 32 bytes - 272
     * 40 bytes - 345
     * 48 bytes - 387
     * 56 bytes - 437
     * 64 bytes - 516
     * 72 bytes - 571
     * 80 bytes - 596
     * 88 bytes - 557
     * 96 bytes - 607
     * 104 bytes - 666
     * 112 bytes - 714
     * 120 bytes - 775
     * 128 bytes - 940
     * 136 bytes - 817
     * 144 bytes - 723
     * 152 bytes - 733
     * <p>
     * So depending on that my cache line <b>probably</b> is 64 byte
     */

    // 16 Mb
    private static final int ARRAY_SIZE = 16 * 1024 * 1024;

    public static void main(String[] args) {
        byte[] data = new byte[ARRAY_SIZE];
        int step = 16;
        while (step < 160) {
            int stepCount = data.length / step;
            long iterationTime = calcTimeForFullIteration(data, step, 100);
            int timeForOneStep = (int) (iterationTime / stepCount);
            System.out.println(step + " bytes - " + timeForOneStep);
            step += 8;
        }
    }

    private static long calcTimeForFullIteration(byte[] data, int step, int repeat) {
        // Sum is useless but allow to see fair result
        // Without it compiler can just remove all loop
        int sum = 0;
        long start = System.nanoTime();
        for (int j = 0; j < repeat; j++) {
            for (int i = 0; i < data.length; i += step) {
                sum += data[i];
            }
        }
        if (sum > 0) {
            // NOP
            System.out.println();
        }
        return System.nanoTime() - start;
    }
}
