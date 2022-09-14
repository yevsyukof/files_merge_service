package focus.services.mergeFiles;

import focus.services.mergeFiles.config.Config;
import focus.services.mergeFiles.factories.FilesMerger;
import focus.services.mergeFiles.factories.IntegerFilesMerger;
import focus.services.mergeFiles.factories.StringFilesMerger;
import java.io.IOException;
import lombok.NonNull;

public class MergeSortedFilesService {

    public static void mergeFiles(@NonNull Config config) throws InterruptedException, IOException {
        FilesMerger fileMerger = switch (config.getSortingDataType()) {
            case STRING -> new StringFilesMerger();
            case INTEGER -> new IntegerFilesMerger();
        };

        fileMerger.mergeAllFiles(
            config.getOutputFileName(), config.getInputFilesNames(),
            config.getSortMode());
    }
}
