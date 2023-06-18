package info.thelaboflieven.graja;

import java.io.File;

public class GradleFinderImpl implements GradleFinder {
    public File forPath(File dir) {
        if (OSValidator.isWindows()) {
            return new File(dir+File.separator+"gradlew.bat");
        }
        return new File(dir+File.separator+"gradlew");
    }
}
