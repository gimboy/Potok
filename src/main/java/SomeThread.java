public class SomeThread implements Runnable {
    private final static Object lock = new Object();
    private static int numOfThreads;
    private static int currentThreadId;
    private final int threadId;

    public SomeThread() {
        threadId = numOfThreads++;
    }

    @Override
    public void run() {
        for(int i =0; i<10; i++) {

            synchronized (lock) {
                while (currentThreadId != threadId) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " Number: " + i);
                currentThreadId = threadId == numOfThreads - 1 ? 0 : threadId + 1;
                lock.notifyAll();
            }
        }
    }
}