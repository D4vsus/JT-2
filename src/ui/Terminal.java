package ui;

import interfaces.ProgramInterface;
import interfaces.TerminalInterface;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Terminal implements TerminalInterface {
    private final ProgramInterface programInterface;
    private JPanel terminal;
    private JEditorPane outputPanel;
    private JTextField inputText;
    private JButton enterButton;
    private final Document info;

    /**
     * <h1>terminal()</h1>
     * <p>Initialize the terminal</p>
     * @param programInterface : implementation of the program interface
     * @author D4vsus
     */
    public Terminal(ProgramInterface programInterface){
        this.setFont(terminal.getFont());
        this.programInterface = programInterface;
        info = outputPanel.getDocument();
        outputPanel.setEditable(false);

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterMethod();
            }
        });
        inputText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterMethod();
            }
        });
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
     * @return Jpanel
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

    private void connectProgram(){
        clear();
        programInterface.startProgram(programInterface.getProgramArguments(getInput()));
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
}
