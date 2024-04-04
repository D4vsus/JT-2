package ui;

import interfaces.ProgramInterface;
import interfaces.TerminalInterface;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;

public class Terminal implements TerminalInterface {
    private final ProgramInterface programInterface;
    private JPanel terminal;
    private JEditorPane outputPanel;
    private JTextField inputText;
    private JButton enterButton;
    private final Document info;
    byte pointer;
    private final List<String> commandRecord;

    /**
     * <h1>terminal()</h1>
     * <p>Initialize the terminal</p>
     * @param programInterface : implementation of the program interface
     * @author D4vsus
     */
    public Terminal(ProgramInterface programInterface){
        pointer = 0;
        this.commandRecord = new ArrayList<>();
        this.setFont(terminal.getFont());
        this.programInterface = programInterface;
        info = outputPanel.getDocument();
        outputPanel.setEditable(false);

        //auto scroll
        DefaultCaret caret = (DefaultCaret)outputPanel.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        enterButton.addActionListener(e -> enterMethod());
        inputText.addActionListener(e -> enterMethod());
        this.programInterface.runConfig();
        if (programInterface.isAlive()){
            connectProgram();
        }
    }

    /**
     * <h1>print()</h1>
     * <p>Print the information on the screen</p>
     * @param output : string
     * @author D4vsus
     */
    @Override
    public void print(String output) {
        try {
            info.insertString(info.getLength(),output,null);
        } catch (BadLocationException ignored) {

        }
    }

    /**
     * <h1>setOutput()</h1>
     * <p>Set a string to output text</p>
     * @author D4vsus
     */
    public void setOutput(String string){
        outputPanel.setText(string);
    }

    /**
     * <h1>clear()</h1>
     * <p>Clear the screen</p>
     * @author D4vsus
     */
    @Override
    public void clear() {
        try {
            info.remove(0,info.getLength());
        } catch (BadLocationException ignored) {

        }
    }

    /**
     * <h1>getInput()</h1>
     * <p>Take the input from the input panel</p>
     * @return String
     * @author D4vsus
     */
    @Override
    public String getInput(){
       String get = inputText.getText();

       if(commandRecord.size() < 32){
           commandRecord.add(get);
       } else {
           String string = commandRecord.get(31);
           for (int x = 31; x > 0;x--){
               commandRecord.set(x,commandRecord.get(x));
           }
           commandRecord.set(0,string);
       }
       pointer = (byte) (commandRecord.size());
       inputText.setText("");
       return get;
    }

    /**
     * <h1>setInput()</h1>
     * <p>set the input panel text</p>
     * @author D4vsus
     */
    @Override
    public void setInput(String string) {
        inputText.setText(string);
    }

    /**
     * <h1>setFont()</h1>
     * <p>Set the font of all the window</p>
     * @author D4vsus
     */
    @Override
    public void setFont(Font font) {
        terminal.setFont(font);
        inputText.setFont(font);
        outputPanel.setFont(font);
        enterButton.setFont(font);
    }

    /**
     * <h1>getPanel()</h1>
     * <p>Return the panel of the terminal</p>
     * @return JPanel
     * @author D4vsus
     */
    public JPanel getPanel(){
        return terminal;
    }

    /**
     * <h1>enterMethod()</h1>
     * <p>Run a program when press enter on the input text or in the enter button</p>
     * @author D4vsus
     */
    public void enterMethod(){
        if (!inputText.getText().isEmpty()) {
            if (!programInterface.isAlive()) {
                connectProgram();
            } else programInterface.readInput(getInput());
        }
    }

    /**
     * <h1>connectProgram()</h1>
     * <p>Connects the Terminal to the input and output stream of the other program</p>
     * @author D4vsus
     */
    private void connectProgram(){
        clear();
        String programName;
        if(!(programName = getInput()).isEmpty()) {
            this.setOutput(programInterface.startProgram(programInterface.getProgramArguments(programName)));
        }
        new Thread(() -> {
            while (programInterface.isAlive()) {
                update();
            }
        }).start();
    }

    /**
     * <h1>update()</h1>
     * <p>Refresh the screen</p>
     * @author D4vsus
     */
    public void update(){
            String str;
            while (!(str = programInterface.writeOutput()).isEmpty()) {
                if (!str.equals("clear")) print(str);
                else clear();
            }
            while (!(str = programInterface.writeError()).isEmpty()) {
                print(str);
            }
    }

    /**
     * <h1>endProgram()</h1>
     * <p>End the current program of the terminal and return the exit code</p>
     * @return int : exit value
     */
    public int endProgram(){
        return programInterface.endProgram();
    }

    /**
     * <h1>addKeyListenerToTheTerminal()</h1>
     * <p>Add the key listener to all the components of the terminal</p>
     * @param e : {@link KeyListener}
     */
    public void addKeyListenerToTheTerminal(KeyListener e){
          terminal.addKeyListener(e);
          outputPanel.addKeyListener(e);
          inputText.addKeyListener(e);
          enterButton.addKeyListener(e);
    }

    /**
     * <h1>getRecord()</h1>
     * <p>Get the info of the record of the current position of the pointer</p>
     * @return string
     */
    @Override
    public String getRecord() {
        return commandRecord.get(pointer);
    }

    /**
     * <h1>getRecord()</h1>
     * <p>Get the info of the record of the position you write</p>
     *
     * @param position : int
     * @return string
     */
    @Override
    public String getRecord(int position) {
        return commandRecord.get(position);
    }

    /**
     * <h1>setRecord()</h1>
     * <p>Set a string in the position you said</p>
     *
     * @param string   : String
     * @param position : int
     */
    @Override
    public void setRecord(String string, int position) {
        if (position < 32){
            commandRecord.set(position,string);
        }
    }

    /**
     * <h1>setRecordPointer()</h1>
     * <p>Set the position of the pointer</p>
     * @param pointer : int
     */
    @Override
    public void setRecordPointer(byte pointer) {
        this.pointer = pointer;
    }

    /**
     * <h1>getRecordPointer()</h1>
     * <p>Get the current position of the record</p>
     * @return int
     */
    @Override
    public int getRecordPointer() {
        return pointer;
    }

    /**
     * <h1>nextValueRecord()</h1>
     * <p>Select the next value record</p>
     */
    @Override
    public void nextValueRecord() {
        if (!commandRecord.isEmpty()) {
            if (pointer < commandRecord.size()-1){
                pointer++;
                setInput(getRecord());
            } else {
                pointer = (byte) (commandRecord.size());
                setInput("");
            }
        }
    }

    /**
     * <h1>previousValueRecord()</h1>
     * <p>Select the previous value record</p>
     */
    @Override
    public void previousValueRecord() {
        if (!commandRecord.isEmpty()) {
            if (pointer > 0){
                pointer--;
            }
            else pointer = 0;

            setInput(getRecord());
        }
    }
}
