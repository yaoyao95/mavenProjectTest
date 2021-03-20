package thread.pool.queue.core;

public class Reject {
    public static <E> void ifNull(E e, String s) {
        if (null == e) {
            throw new RuntimeException(s);
        }
    }
}
