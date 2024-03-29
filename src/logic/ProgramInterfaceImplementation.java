package logic;

import interfaces.ProgramInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class ProgramInterfaceImplementation implements ProgramInterface {
    private Process process;
    private BufferedReader inputStream;
    private BufferedReader errorStream;
    private BufferedWriter outputStream;
    public ProgramInterfaceImplementation(){

    }

    /**
     * <h1>startProgram()</h1>
     * <p>Star the program</p>
     * @return String
     * @author D4vsus
     */
    @Override
    public String startProgram(List<String> arguments) {
        ProcessBuilder processBuilder = new ProcessBuilder(arguments);
        try {
            this.process = processBuilder.start();
            this.inputStream = this.process.inputReader();
            this.errorStream = this.process.errorReader();
            this.outputStream = this.process.outputWriter();
            return "";
        } catch (IOException e) {
            return "Program not found";
        }
    }

    /**
     * <h1>readInput()</h1>
     * <p>Read the output from the program running </p>
     * @author D4vsus
     */
    @Override
    public void readInput(String string) {
        try {
            this.outputStream.write("%s\n".formatted(string));
            this.outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <h1>writeOutput</h1>
     * <p>Takes one character of the output stream and return it as a string</p>
     * @return String
     * @author D4vsus
     */
    @Override
    public String writeOutput() {
        try {
            int character;
            if (this.inputStream.ready()) {
                character = this.inputStream.read();
                if (character != -1) {
                    return "" + (char) character;
                } else {
                    System.out.println("asd");
                    return "";
                }
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <h1>writeError</h1>
     * <p>Takes one character of the error stream and return it as a string</p>
     * @return string
     * @author D4vsus
     */
    @Override
    public String writeError() {
        try {
            int character;
            if (this.errorStream.ready()) {
                character = this.errorStream.read();
                if (character != -1) {
                    return "" + (char) character;
                } else {
                    System.out.println("asd");
                    return "";
                }
            }
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * <h1>endProgram()</h1>
     * <p>End the program</p>
     * @author D4vsus
     */
    @Override
    public void endProgram() {
        if(this.process.isAlive()){
            this.process.destroy();
        }
    }

    /**
     * <h1>isAlive()</h1>
     * <p>See if the program is alive</p>
     * @return boolean
     * @author D4vsus
     */
    @Override
    public boolean isAlive() {
        if (this.process != null) return this.process.isAlive();
        else return false;
    }
}
