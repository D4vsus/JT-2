package ui;

import interfaces.ProgramInterface;
import interfaces.TerminalInterface;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

public class Terminal implements TerminalInterface {
    private final ProgramInterface programInterface;
    private JPanel Terminal;
    private JEditorPane OutputPanel;
    private JTextField InputText;
    private JButton EnterButton;
    private final Document info;

    public Terminal(ProgramInterface programInterface){
        this.setFont(Terminal.getFont());
        this.programInterface = programInterface;
        info = OutputPanel.getDocument();
        OutputPanel.setEditable(false);

        EnterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterMethod();
            }
        });
        InputText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterMethod();
            }
        });
    }

    @Override
    public void print(String output) {
        try {
            info.insertString(info.getLength(),output,null);
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try {
            info.remove(0,info.getLength());
        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getInput(){
       String get = InputText.getText();
       InputText.setText("");
       return get;
    }

    @Override
    public void setInput(String string) {
        InputText.setText("");
    }

    @Override
    public void setFont(Font font) {
        Terminal.setFont(font);
        InputText.setFont(font);
        OutputPanel.setFont(font);
        EnterButton.setFont(font);
    }

    public JPanel getPanel(){
        return Terminal;
    }

    public void enterMethod(){
        if (!InputText.getText().isEmpty()) {
            if (!programInterface.isAlive()) {
                this.print(programInterface.startProgram(Collections.singletonList(getInput())));
                new Thread(() -> {
                    while (programInterface.isAlive()) {
                        update();
                    }
                }).start();
            } else programInterface.readInput(getInput());
        }
    }

    public void update(){
            String str;
            while (!(str = programInterface.writeOutput()).isEmpty()) {
                print(str);
            }
            while (!(str = programInterface.writeError()).isEmpty()) {
                print(str);
            }
    }
}
