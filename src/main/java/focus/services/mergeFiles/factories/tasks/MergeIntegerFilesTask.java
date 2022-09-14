package focus.services.mergeFiles.factories.tasks;

import focus.services.mergeFiles.config.SortMode;
import focus.services.mergeFiles.factories.FilesMerger;
import java.util.List;
import lombok.NonNull;

public class MergeIntegerFilesTask extends MergeFilesTask {

    public MergeIntegerFilesTask(
        @NonNull FilesMerger filesMerger,
        @NonNull SortMode sortMode,
        @NonNull List<String> tmpInputFileNames, @NonNull String tmpFileName,
        @NonNull String firstFileName, @NonNull String secondFileName) {
        super(filesMerger, sortMode, tmpInputFileNames, tmpFileName, firstFileName, secondFileName);
    }

    @Override
    protected boolean isFileLineValid(String fileLine) {
        return fileLine != null && fileLine.matches("[-+]?\\d+");
    }
}
