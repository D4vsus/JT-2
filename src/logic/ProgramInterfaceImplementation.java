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

    @Override
    public void readInput(String string) {
        try {
            this.outputStream.write("%s\n".formatted(string));
            this.outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String writeOutput() {
        try {
            int character = 0;
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

    @Override
    public String writeError() {
        try {
            int character = 0;
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

    @Override
    public void endProgram() {
        if(this.process.isAlive()){
            this.process.destroy();
        }
    }

    @Override
    public boolean isAlive() {
        if (this.process != null) return this.process.isAlive();
        else return false;
    }
}
