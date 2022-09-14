package focus.services.mergeFiles.factories;

import focus.services.mergeFiles.config.SortMode;
import focus.services.mergeFiles.factories.tasks.MergeFilesTask;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.NonNull;

public abstract class FilesMerger {

    protected abstract MergeFilesTask buildNewMergeFilesTask();

    public void mergeAllFiles(@NonNull String outputFileName, @NonNull List<String> inputFilesNames,
        @NonNull SortMode sortMode) throws InterruptedException, IOException {

        File destFile = new File(outputFileName);

        if (!destFile.isFile()) {
            throw new IOException("Invalid output file name");
        }
        if (inputFilesNames.size() == 0) {
            return;
        }

        HashSet<String> inputFilesNamesSet = new HashSet<>(inputFilesNames);
        Queue<String> filesToMergeQueue = new ConcurrentLinkedQueue<>(inputFilesNames);

        for (int iterationIdx = 0; inputFilesNames.size() != 1; iterationIdx++) {
            List<String> tmpFilesNames = new ArrayList<>();

            for (int i = 0; i < inputFilesNames.size() / 2; i++) {
                String curTmpFileName = iterationIdx + "_" + i + ".txt";
//
//                threadPool.submit(
//                    new MergeFilesTask(
//                        this, sortMode, tmpFilesNames, curTmpFileName,
//                        inputFilesNames.get(2 * i), inputFilesNames.get(2 * i + 1)));
            }


            if (isInterrupted) {
                return;
            }

            //if the number of input files is odd, add the latest file to the end of the list
            if (inputFilesNames.size() % 2 != 0) {
                tmpFilesNames.add(inputFilesNames.get(inputFilesNames.size() - 1));
            }

            //delete already sorted files without input files
//            if (!isFirstInputFiles) {
//                isFirstInputFiles = true;
//            } else {
//                for (int i = 0; i < inputFilesNames.size(); i++) {
//                    if (inputFilesNames.size() % 2 != 0 && inputFilesNames.size() != 2
//                        && i == inputFilesNames.size() - 1) {
//                    } else {
//                        File file = new File(inputFilesNames.get(i));
//                        if (!file.delete()) {
//                            System.err.println(
//                                "File " + inputFilesNames.get(i) + " was not deleted");
//                        }
//                    }
//                }
//            }

            inputFilesNames = tmpFilesNames;
            iterationIdx++;
        }

        File sourceFile = new File(inputFilesNames.get(0));

        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        if (!sourceFile.delete()) {
            System.err.println("File " + sourceFile.getName() + " was not deleted");
        }
    }
}
