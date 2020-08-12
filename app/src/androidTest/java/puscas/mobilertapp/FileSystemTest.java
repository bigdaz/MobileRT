package puscas.mobilertapp;

import com.google.common.collect.ImmutableList;
import java.io.File;
import java.util.List;
import java.util.logging.Logger;
import java8.util.stream.StreamSupport;
import javax.annotation.Nonnull;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runners.MethodSorters;
import puscas.mobilertapp.utils.Constants;
import puscas.mobilertapp.utils.UtilsContext;

/**
 * The test suite for the File system operations used in {@link MainActivity}.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class FileSystemTest extends AbstractTest {

    /**
     * The {@link Logger} for this class.
     */
    @Nonnull
    private static final Logger LOGGER = Logger.getLogger(FileSystemTest.class.getName());

    /**
     * Setup method called before each test.
     */
    @Before
    @Override
    public void setUp() {
        super.setUp();

        final String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        LOGGER.info(methodName);
    }

    /**
     * Tear down method called after each test.
     */
    @After
    @Override
    public void tearDown() {
        super.tearDown();

        final String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        LOGGER.info(methodName);
    }


    /**
     * Tests that a file in the Android device exists and is readable.
     */
    @Test(timeout = 5L * 1000L)
    public void testFilesExistAndReadable() {
        final String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        LOGGER.info(methodName);

        final List<String> paths = ImmutableList.<String>builder().add(
            Constants.OBJ_FILE_TEAPOT
        ).build();
        StreamSupport.stream(paths)
            .forEach(path -> {
                final File file = new File(path);
                final String filePath = file.getAbsolutePath();
                Assertions.assertTrue(file.exists(), Constants.FILE_SHOULD_EXIST + ": " + filePath);
                Assertions.assertTrue(file.canRead(), "File should be readable: " + filePath);
            });
    }

    /**
     * Tests that a file does not exist in the Android device.
     */
    @Test(timeout = 5L * 1000L)
    public void testFilesNotExist() {
        final String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        LOGGER.info(methodName);

        final String sdCardPath = UtilsContext.getSDCardPath(this.activity);

        final List<String> paths = ImmutableList.<String>builder().add(
            Constants.EMPTY_FILE,
            sdCardPath + Constants.OBJ_FILE_NOT_EXISTS
        ).build();
        StreamSupport.stream(paths)
            .forEach(path -> {
                final File file = new File(path);
                Assertions.assertFalse(file.exists(), "File should not exist!");
                Assertions.assertFalse(file.canRead(), "File should not be readable!");
            });
    }

}