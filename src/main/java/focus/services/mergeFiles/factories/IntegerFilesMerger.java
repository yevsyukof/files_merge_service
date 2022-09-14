package focus.services.mergeFiles.factories;

import focus.services.mergeFiles.config.SortMode;
import focus.services.mergeFiles.factories.tasks.MergeFilesTask;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class IntegerFilesMerger extends FilesMerger {

    @Override
    protected MergeFilesTask buildNewMergeFilesTask() {
        return null;
    }
}
