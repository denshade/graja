package info.thelaboflieven.graja;

import java.io.File;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GradleFinderStubs
{
    public GradleFinder foundGradleStubWindows() {
        var mock = mock(GradleFinder.class);
        when(mock.forPath(any())).thenReturn(new File("c:\\projects\\test\\gradlew.bat"));
        return mock;
    }
    public GradleFinder foundGradleStubLinux() {
        var mock = mock(GradleFinder.class);
        when(mock.forPath(any())).thenReturn(new File("/test/gradlew"));
        return mock;
    }

}
