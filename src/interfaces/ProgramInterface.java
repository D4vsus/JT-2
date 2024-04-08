package interfaces;

import java.util.List;

public interface ProgramInterface {
    /**
     * <h1>startProgram()</h1>
     * <p>Start the program with arguments</p>
     * @return String
     * @author D4vsus
     */
    String startProgram(List<String> arguments);
    /**
     * <h1>readInput()</h1>
     * <p>Read the output from the program running </p>
     * @author D4vsus
     */
    void readInput(String string);
    /**
     * <h1>writeOutput</h1>
     * <p>Takes one character of the output stream and return it as a string</p>
     * @return String
     * @author D4vsus
     */
    String writeOutput();
    /**
     * <h1>writeError</h1>
     * <p>Takes one character of the error stream and return it as a string</p>
     * @return string
     * @author D4vsus
     */
    String writeError();
    /**
     * <h1>endProgram()</h1>
     * <p>End the program and return the exit value</p>
     * @author D4vsus
     */
    int endProgram();
    /**
     * <h1>isAlive()</h1>
     * <p>See if the program is alive</p>
     * @return boolean
     * @author D4vsus
     */
    boolean isAlive();

    /**
     * <h1>getConfig()</h1>
     * <p>Reads the JT-2.conf file</p>
     * @return String[]
     * @author D4vsus
     */
    String[] getConfig();

    /**
     * <h1>getProgramArguments()</h1>
     * <p>Parse a string to program arguments</p>
     * @param string String
     * @return List<String>
     */
    List<String> getProgramArguments(String string);

    /**
     * <h1>runConfig()</h1>
     * <p>Run the configuration of JT-2.conf file</p>
     */
    void runConfig();
}
