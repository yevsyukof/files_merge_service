package focus.services.mergeFiles.utils;

public interface DataExtractor<T> {

    T getData(String dataLine);
}
