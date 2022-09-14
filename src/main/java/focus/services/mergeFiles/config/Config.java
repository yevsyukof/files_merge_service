package focus.services.mergeFiles.config;

import focus.services.mergeFiles.config.cli.exceptions.WrongArgumentsException;
import focus.services.mergeFiles.config.cli.parser.CommandLineParser;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Config {

    @NonNull private final List<String> inputFilesNames;
    @NonNull private final String outputFileName;

    @NonNull private final SortMode sortMode;
    @NonNull private final SortingDataType sortingDataType;

    public static Config fromCLIArgs(@NonNull String[] args) throws WrongArgumentsException {
        var commandLineParser = new CommandLineParser(args);
        return new Config(
            commandLineParser.getInputFileNames(),
            commandLineParser.getOutputFileName(),
            commandLineParser.getSortMode(),
            commandLineParser.getSortingDataType());
    }
}
