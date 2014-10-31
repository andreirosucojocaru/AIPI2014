package ro.pub.cs.aipi.lab02.main;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ro.pub.cs.aipi.lab02.dataaccess.DataBaseWrapper;
import ro.pub.cs.aipi.lab02.dataaccess.DataBaseWrapperImplementation;
import ro.pub.cs.aipi.lab02.general.Constants;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    Exercise02Test.class,
    Exercise03Test.class,
    Exercise04Test.class,
    Exercise05Test.class,
    Exercise06Test.class,
    Exercise07Test.class,
    Exercise08Test.class,
    Exercise09Test.class,
    Exercise10Test.class,
    Exercise11Test.class
})
public class AllTests {

    private static boolean databaseLoaded = false;
    private static boolean databaseUnloaded = false;

    static class DirectoryScanner implements FileVisitor<Path> {

        @Override
        public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) {
            try {
                Files.delete(file);
            } catch (IOException ioException) {
                System.out.format("File %s has not been deleted: %s!", file.getFileName(), ioException.getMessage());
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path directory, IOException ioException) {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException ioException) throws IOException {
            System.out.format("File %s has not been deleted: %s!", file.getFileName(), ioException.getMessage());
            return FileVisitResult.CONTINUE;
        }
    }

    @BeforeClass
    public static void loadDatabase() {
        if (databaseLoaded) {
            return;
        }
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        try {
            if (Constants.DEBUG) {
                System.out.println("loading database...");
            }
            dbWrapper.runScript(Constants.LOAD_DATABASE_SCRIPT);
            dbWrapper.setDefaultDatabase(Constants.DATABASE_NAME);
        } catch (SQLException exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        } finally {
            databaseLoaded = true;
            dbWrapper.releaseResources();
        }
    }

    @BeforeClass
    public static void clean() {
        if (Constants.DEBUG) {
            System.out.println("started cleaning...");
        }
        Path path = Paths.get("output");
        DirectoryScanner directoryScanner = new DirectoryScanner();
        try {
            Files.walkFileTree(path, directoryScanner);
        } catch (IOException ioException) {
            System.out.format("Directory could not be scanned: %s!%n", ioException.getMessage());
        }
        if (Constants.DEBUG) {
            System.out.println("finished cleaning...");
        }
    }

    @AfterClass
    public static void unloadDatabase() {
        if (databaseUnloaded) {
            return;
        }
        DataBaseWrapper dbWrapper = DataBaseWrapperImplementation.getInstance();
        try {
            if (Constants.DEBUG) {
                System.out.println("unloading database...");
            }
            dbWrapper.runScript(Constants.UNLOAD_DATABASE_SCRIPT);
        } catch (SQLException exception) {
            System.out.println("An exception has occured: " + exception.getMessage());
            if (Constants.DEBUG) {
                exception.printStackTrace();
            }
        } finally {
            databaseUnloaded = true;
            dbWrapper.releaseResources();
        }
    }
}
