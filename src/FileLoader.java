import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileLoader
{
    public List<File> getFiles()
    {
        try {
            var writer = new BufferedReader(new FileReader("gradja.hist"));
            return writer.lines().map(File::new).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void storeFiles(List<File> files)
    {
        try {
            var writer = new BufferedWriter(new FileWriter("gradja.hist"));
            for (var file : files) {
                writer.write(file.getAbsolutePath()+"\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
