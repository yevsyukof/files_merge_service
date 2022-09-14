package focus.services.mergeFiles.config.cli.parser;

import com.beust.jcommander.Parameter;
import java.util.ArrayList;
import java.util.List;

// TODO добавить валидацию параметров

class Args {

    @Parameter(
        names = "-h", description = "Показывает информацию о параметрах программы", help = true)
    private boolean help = false;

    public boolean getHelpOptionFlag() {
        return help;
    }

    @Parameter(names = "-a", description = "Флаг сортировки данных в возростающем порядке")
    private boolean ascendingSort = false;

    public boolean getAscendingSortOptionFlag() {
        return ascendingSort;
    }

    @Parameter(names = "-d", description = "Флаг сортировки данных в убывающем порядке")
    private boolean descendingSort = false;

    public boolean getDescendingSortOptionFlag() {
        return descendingSort;
    }

    @Parameter(
        names = "-s",
        description = "Установка этого флага конфигурирует программу для сортировки строковых данных")
    private boolean stringData = false;

    public boolean getStringDataOptionFlag() {
        return stringData;
    }

    @Parameter(
        names = "-i",
        description = "Установка этого флага конфигурирует программу для сортировки числовых данных")
    private boolean integerData = false;

    public boolean getIntegerDataOptionFlag() {
        return integerData;
    }

    //inputFileNames[0] - output file name, inputFileNames[1, ...] - input file names
    @Parameter(description = "Имена файлов")
    private List<String> inputFileNames = new ArrayList<>();

    public List<String> getFileNames() {
        return inputFileNames.subList(0, inputFileNames.size());
    }
}
