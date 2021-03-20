package thread.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import thread.pool.queue.ResizableBlockingQueue;
import thread.pool.queue.ResizableCapacityLinkedBlockIngQueue;
import thread.pool.queue.ResizableLinkedBlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolTest {
    private static AtomicInteger atomicAId = new AtomicInteger(0);
    private static AtomicInteger atomicBId = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5, 10,
                5, TimeUnit.SECONDS
                //,new ResizableLinkedBlockingQueue<>(10)
                ,new ResizableCapacityLinkedBlockIngQueue<>(10)
                ,new ThreadFactoryBuilder().setNameFormat("test-pool-%d").build()
                ,new ThreadPoolExecutor.AbortPolicy());
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        Thread daemon = new Thread(()-> {
            while(true) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (Exception e) {}
                showThreadPoolStatus(threadPoolExecutor,"监控",null);
            }
        });
        daemon.setDaemon(true);
        daemon.start();
        showThreadPoolStatus(threadPoolExecutor,"修改参数之前",null);
        for (int i = 0; i < 15; i++) {
            //TimeUnit.MICROSECONDS.sleep(150);
            threadPoolExecutor.submit(()-> {
                showThreadPoolStatus(threadPoolExecutor,"提交任务a",atomicAId);
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        Thread.sleep(4000);
        threadPoolExecutor.setCorePoolSize(10);
        showThreadPoolStatus(threadPoolExecutor,"修改参数之后",null);

        //TimeUnit.SECONDS.sleep(25);
        if (threadPoolExecutor.getQueue() instanceof ResizableBlockingQueue) {
            ((ResizableBlockingQueue<Runnable>)threadPoolExecutor.getQueue()).setCapacity(8);
        }
        for (int i = 0; i < 25; i++) {
            TimeUnit.MICROSECONDS.sleep(800);
            try{
                threadPoolExecutor.submit(()-> {
                    showThreadPoolStatus(threadPoolExecutor,"提交任务b",atomicBId);
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void showThreadPoolStatus(ThreadPoolExecutor threadPoolExecutor, String msg,AtomicInteger msgId) {
        BlockingQueue<Runnable> queue = threadPoolExecutor.getQueue();
        String msgIdStr = null == msgId ? null : "" + msgId.getAndIncrement();
        System.out.println(Thread.currentThread().getName() + "-msgId:" + msgIdStr + '.' + msg + '.' +
                " coreSize : " + threadPoolExecutor.getCorePoolSize() +
                " ,maxCoreSize : " + threadPoolExecutor.getMaximumPoolSize() +
                " ,activeNum : " + threadPoolExecutor.getActiveCount() +
                " ,poolSize : " + threadPoolExecutor.getPoolSize() +
                " ,active rate : " + divide(threadPoolExecutor.getActiveCount(),threadPoolExecutor.getMaximumPoolSize()) +
                " ,queueCapacity : " + (queue.size() + queue.remainingCapacity()) +
                " ,queueSize : " +queue.size() +
                ",queueCapacityUsageRate : " + divide(queue.size(),queue.size() + queue.remainingCapacity())
                );
    }

    private static String divide(int n1, int n2) {
        return ((int)(n1 * 1.0 / n2 * 10000))/100.0 + "%";
    }
}
