package interfaces;

import java.awt.*;
import java.awt.event.KeyListener;

public interface TerminalInterface {
    /**
     * <h1>print()</h1>
     * <p>Print the information on the screen</p>
     * @param output : string
     * @author D4vsus
     */
    void print(String output);
    /**
     * <h1>clear()</h1>
     * <p>Clear the screen</p>
     * @author D4vsus
     */
    void clear();
    /**
     * <h1>getInput()</h1>
     * <p>Take the input from the input panel</p>
     * @return String
     * @author D4vsus
     */
    String getInput();
    /**
     * <h1>setInput()</h1>
     * <p>set the input panel text</p>
     * @author D4vsus
     */
    void setInput(String string);
    /**
     * <h1>setOutput()</h1>
     * <p>set a string to the output panel</p>
     * @author D4vsus
     */
    void setOutput(String string);
    /**
     * <h1>setFont()</h1>
     * <p>Set the font of all the window</p>
     * @author D4vsus
     */
    void setFont(Font font);

    /**
     * <h1>addKeyListenerToTheTerminal()</h1>
     * <p>Add the key listener to all the components of the terminal</p>
     * @param e : {@link KeyListener}
     */
     void addKeyListenerToTheTerminal(KeyListener e);
}
