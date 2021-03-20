package thread.pool.queue;

import java.util.concurrent.BlockingQueue;

public interface ResizableBlockingQueue<E> extends BlockingQueue<E> {
    /**
     * 设置队列新容量capacity大小
     * @param newSize
     */
    void setCapacity(int newSize);
}
