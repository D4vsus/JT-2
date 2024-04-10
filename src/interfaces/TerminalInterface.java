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

    /**
     * <h1>getRecord()</h1>
     * <p>Get the info of the record of the current position of the pointer</p>
     * @return string
     */
    String getRecord();
    /**
     * <h1>getRecord()</h1>
     * <p>Get the info of the record of the position you write</p>
     * @param position : int
     * @return string
     */
    String getRecord(int position);

    /**
     * <h1>setRecord()</h1>
     * <p>Set a string in the position you said</p>
     * @param string : String
     * @param position : int
     */
    void setRecord(String string,int position);

    /**
     * <h1>setRecordPointer()</h1>
     * <p>Set the position of the pointer</p>
     * @param pointer : int
     */
    void setRecordPointer(byte pointer);

    /**
     * <h1>getRecordPointer()</h1>
     * <p>Get the current position of the record</p>
     * @return int
     */
    int getRecordPointer();

    /**
     * <h1>nextValueRecord()</h1>
     * <p>Select the next value record</p>
     */
    void nextValueRecord();
    /**
     * <h1>previousValueRecord()</h1>
     * <p>Select the previous value record</p>
     */
    void previousValueRecord();
    /**
     * <h1>isProgramRunning()</h1>
     * <p>Return true if there is a current program running</p>
     * @return boolean
     * @author D4vsus
     */
    boolean isProgramRunning();
}
