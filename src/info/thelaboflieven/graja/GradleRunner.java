package info.thelaboflieven.graja;

import java.io.*;

public class GradleRunner
{
    public void run(File directory) {
        var finder = new GradleFinder();
        var gradlePath = finder.forPath(directory);
        ProcessBuilder processBuilder = new ProcessBuilder(gradlePath.getAbsolutePath(), "assemble");

        // Set the working directory if needed
        processBuilder.directory(directory);

        // Start the process
        Process process = null;
        try {
            process = processBuilder.start();
            int exitCode = process.waitFor();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            copy(process.getInputStream(), baos);
            // Print the exit value
            System.out.println("Exit Value: " + exitCode + " "+ baos);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    void copy(InputStream source, OutputStream target) throws IOException {
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) != -1) {
            target.write(buf, 0, length);
        }
    }
}
