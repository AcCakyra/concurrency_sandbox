package cache;

public class Volatile {
    /**
     * According to JMM this program can run forever
     * because JMM doesn't know something about cache coherence.
     * But nowadays processors <b>probably<b/> has cache coherence protocol.
     */

    static /*volatile*/ boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (flag) {
                // NOP
            }
        }).start();

        flag = false;
    }
}
