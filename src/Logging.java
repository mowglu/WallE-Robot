import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Logging {

    /**
     *
     * @param pRobot The robot for which logging is done, using unique identifier in the textfile name
     * @param pAction The action which is added/appended to the textfile for logging purposes
     */
    public static void logger(Robot pRobot, Action pAction) {

        Path pathOfLog = Path.of(pRobot.toString() + ".txt");
        Charset charSetOfLog = StandardCharsets.US_ASCII;
        String stringToWrite = pAction.toString() + " action performed, battery level is " + pRobot.getBatteryCharge();
        try {
            BufferedWriter bwOfLog = Files.newBufferedWriter(pathOfLog, charSetOfLog, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            bwOfLog.append(stringToWrite, 0, stringToWrite.length());
            bwOfLog.newLine();
            bwOfLog.close();
        } catch (IOException e) {
        }
    }
}
