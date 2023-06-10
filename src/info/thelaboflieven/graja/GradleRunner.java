package info.thelaboflieven.graja;

import java.io.File;
import java.io.IOException;

public class GradleRunner
{
    public void run(File directory) {
        var finder = new GradleFinder();
        var gradlePath = finder.forPath(directory);
        ProcessBuilder processBuilder = new ProcessBuilder(gradlePath.getAbsolutePath(), "assemble");

        // Set the working directory if needed
        // processBuilder.directory(new File("path/to/working/directory"));

        // Start the process
        Process process = null;
        try {
            process = processBuilder.start();
            // You can get the process output or input stream if needed
            // InputStream inputStream = process.getInputStream();
            // OutputStream outputStream = process.getOutputStream();

            // Wait for the process to finish and get the exit value
            int exitCode = process.waitFor();
            // Print the exit value
            System.out.println("Exit Value: " + exitCode);
            // Find gradle
            // Run gradle assemble.

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
