package focus.services.mergeFiles;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class FilesMergeManager {

    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    private final BlockingQueue<String> filesToMergeNamesQueue;
    private final AtomicInteger numTasksInWork = new AtomicInteger(0);

    private final Runnable filesMerger;

    public FilesMergeManager(List<String> filesToMergeNames, Runnable filesMerger) {
        this.filesToMergeNamesQueue = new LinkedBlockingQueue<>(filesToMergeNames);
        this.filesMerger = filesMerger;
    }

    public void run() throws InterruptedException {
        int idx = 0;
        synchronized (numTasksInWork) {
            while (numTasksInWork.get() > 0) {
                if (filesToMergeNamesQueue.size() > 1) {
                    numTasksInWork.incrementAndGet();

                    String nextTaskTmpFileName = "tmp_file" + (idx++) + ".txt";
                    executorService.submit(() -> {
                        filesMerger.run(); // TODO передача параметров
                        filesToMergeNamesQueue.add(nextTaskTmpFileName);

                        numTasksInWork.decrementAndGet();
                        numTasksInWork.notify();
                    });
                } else {
                    numTasksInWork.wait();
                }
            }
        }
    }
}
