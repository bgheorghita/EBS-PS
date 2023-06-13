package uaic.fii.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Writer<T> {
    public void saveToFile(List<T> list, String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for(T item : list) {
            writer.write(item + System.lineSeparator());
        }
        writer.close();
    }
}
