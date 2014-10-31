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

public class Exercise07Test {

    private static final int NO_DIFFERENCE = 0;
    private static final int DIFFERENCE_NOT_PROCESSED = 1;
    private static final int DIFFERENCE_PROCESSED = 2;

    @BeforeClass
    public static void executeExercise07() {
        BookStore bookstore = new BookStore();
        bookstore.exercise07();
    }

    @Test
    public void checkFileContent() {
        Path outputFile = Paths.get("output/user_total_invoice_value.txt");
        assertTrue("File user_total_invoce_value.txt does not exist or cannot be accessed!", outputFile != null && Files.isRegularFile(outputFile) && Files.isReadable(outputFile));
        Path inputFile = Paths.get("input/user_total_invoice_value.txt");
        assertTrue("Reference file does not exist or cannot be accessed!", inputFile != null && Files.isRegularFile(inputFile) && Files.isReadable(inputFile));
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader outputFileBufferedReader = Files.newBufferedReader(outputFile, charset); BufferedReader inputFileBufferedReader = Files.newBufferedReader(inputFile, charset)) {
            String outputFileCurrentLine = null, inputFileCurrentLine = null;
            int currentLine = 1;
            int differenceStatus = NO_DIFFERENCE;
            do {
                outputFileCurrentLine = outputFileBufferedReader.readLine();
                if (differenceStatus != DIFFERENCE_NOT_PROCESSED) {
                    inputFileCurrentLine = inputFileBufferedReader.readLine();
                } else {
                    differenceStatus = DIFFERENCE_PROCESSED;
                }
                currentLine++;
                if (outputFileCurrentLine != null && inputFileCurrentLine != null
                        && !outputFileCurrentLine.equals(inputFileCurrentLine)
                        && differenceStatus == NO_DIFFERENCE) {
                    differenceStatus = DIFFERENCE_NOT_PROCESSED;
                } else {
                    assertEquals("Files do not match at line " + currentLine, outputFileCurrentLine, inputFileCurrentLine);
                }
            } while (outputFileCurrentLine != null && inputFileCurrentLine != null);
        } catch (IOException ioException) {
            fail("An exception has occurred!" + ioException.getMessage());
        }
    }
}
