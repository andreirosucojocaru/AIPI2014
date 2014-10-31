package ro.pub.cs.aipi.lab02.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

public class Exercise03Test {

    @BeforeClass
    public static void executeExercise03() {
        BookStore bookstore = new BookStore();
        bookstore.exercise03();
    }

    @Test
    public void checkFileContent() {
        Path outputFile = Paths.get("output/books.txt");
        assertTrue("File books.txt does not exist or cannot be accessed!", outputFile != null && Files.isRegularFile(outputFile) && Files.isReadable(outputFile));
        Path inputFile = Paths.get("input/books.txt");
        assertTrue("Reference file does not exist or cannot be accessed!", inputFile != null && Files.isRegularFile(inputFile) && Files.isReadable(inputFile));
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader outputFileBufferedReader = Files.newBufferedReader(outputFile, charset); BufferedReader inputFileBufferedReader = Files.newBufferedReader(inputFile, charset)) {
            String outputFileCurrentLine = null, inputFileCurrentLine = null;
            int currentLine = 1;
            do {
                outputFileCurrentLine = outputFileBufferedReader.readLine();
                inputFileCurrentLine = inputFileBufferedReader.readLine();
                if (outputFileCurrentLine != null && inputFileCurrentLine != null) {
                    assertEquals("Files do not match at line " + currentLine, outputFileCurrentLine, inputFileCurrentLine);
                }
                if ((outputFileCurrentLine == null) ^ (inputFileCurrentLine == null)) {
                    assertEquals("Files do not match at line " + currentLine, outputFileCurrentLine, inputFileCurrentLine);
                }
                currentLine++;
            } while (outputFileCurrentLine != null && inputFileCurrentLine != null);
        } catch (IOException ioException) {
            fail("An exception has occurred!" + ioException.getMessage());
        }
    }

}
