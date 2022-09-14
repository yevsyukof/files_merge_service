package focus.services.mergeFiles.factories.tasks;

import focus.services.mergeFiles.config.SortMode;
import focus.services.mergeFiles.factories.FilesMerger;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public abstract class MergeFilesTask implements Runnable {

    @NonNull private final FilesMerger filesMerger; // TODO зачем он нужен

    @NonNull private final SortMode sortMode;

    @NonNull private final List<String> tmpInputFileNames; // TODO
    @NonNull private final String tmpFileName;

    @NonNull private final String firstFileName;
    @NonNull private final String secondFileName;

    protected abstract boolean isFileLineValid(String fileLine);

    @Override
    public void run() {
        try (
            var firstFileReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(firstFileName)));
            var secondFileReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(secondFileName)));
            var outputFileWriter = new BufferedWriter(new FileWriter(tmpFileName))) {

            String firstFileLine = firstFileReader.readLine();
            String secondFileLine = secondFileReader.readLine();

            while (firstFileLine != null || secondFileLine != null) {
                if (firstFileLine == null) {
                    outputFileWriter.write(secondFileLine);
                    outputFileWriter.newLine();
                    secondFileLine = secondFileReader.readLine();
                    continue;
                }

                if (secondFileLine == null) {
                    outputFileWriter.write(firstFileLine);
                    outputFileWriter.newLine();
                    firstFileLine = firstFileReader.readLine();
                    continue;
                }

//                List<String> out = sortData(firstFileLine, secondFileLine, sortMode, firstFileReader, secondFileReader,
//                    outputFileWriter);

//                firstFileLine = out.get(0);
//                secondFileLine = out.get(1);
            }
            outputFileWriter.flush();
            tmpInputFileNames.add(tmpFileName);
        } catch (FileNotFoundException e) {
            System.err.println("Invalid file name " + e.getMessage());
            filesMerger.interrupt();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            filesMerger.interrupt();
        }
    }
}