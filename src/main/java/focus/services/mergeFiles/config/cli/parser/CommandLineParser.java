package focus.services.mergeFiles.config.cli.parser;

import com.beust.jcommander.JCommander;
import focus.services.mergeFiles.config.cli.exceptions.WrongArgumentsException;
import focus.services.mergeFiles.config.SortMode;
import focus.services.mergeFiles.config.SortingDataType;
import java.util.Arrays;
import java.util.List;

public class CommandLineParser {

    private final Args commandLineParameters;

    public CommandLineParser(String[] args) {
        commandLineParameters = new Args();
        JCommander.newBuilder()
            .addObject(commandLineParameters)
            .build()
            .parse(Arrays.copyOfRange(args, 0, args.length));
    }

    public boolean getHelpFlag() {
        return commandLineParameters.getHelpOptionFlag();
    }

    public void showHelpInformation() {
        JCommander.newBuilder().addObject(commandLineParameters).build().usage();
    }

    public SortMode getSortMode() throws WrongArgumentsException {
        boolean isDescendingSort = commandLineParameters.getDescendingSortOptionFlag();
        boolean isAscendingSort = commandLineParameters.getAscendingSortOptionFlag();

        if (isDescendingSort && !isAscendingSort) {
            return SortMode.DESCENDING_MODE;
        } else if (!isDescendingSort) {
            return SortMode.ASCENDING_MODE;
        } else {
            throw new WrongArgumentsException(
                "Mutually exclusive options '-a' and -'d' are indicated");
        }
    }

    public SortingDataType getSortingDataType() throws WrongArgumentsException {
        boolean isIntegerData = commandLineParameters.getIntegerDataOptionFlag();
        boolean isStringData = commandLineParameters.getStringDataOptionFlag();

        if (isIntegerData && !isStringData) {
            return SortingDataType.INTEGER;
        } else if (!isIntegerData && isStringData) {
            return SortingDataType.STRING;
        } else if (isIntegerData) {
            throw new WrongArgumentsException(
                "Mutually exclusive options '-s' and '-i' are indicated");
        } else {
            throw new WrongArgumentsException("No data type option set");
        }
    }

    public String getOutputFileName() throws WrongArgumentsException {
        if (commandLineParameters.getFileNames().size() == 0) {
            throw new WrongArgumentsException("No output file name");
        }
        return commandLineParameters.getFileNames().get(0);
    }

    public List<String> getInputFileNames() throws WrongArgumentsException {
        if (commandLineParameters.getFileNames().size() < 2) {
            throw new WrongArgumentsException("No input files names");
        }
        return commandLineParameters.getFileNames()
            .subList(1, commandLineParameters.getFileNames().size());
    }
}
