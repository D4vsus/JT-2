package logic;

import interfaces.ProgramInterface;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
        try {
            this.process = new ProcessBuilder(arguments).start();
            this.inputStream = this.process.inputReader();
            this.errorStream = this.process.errorReader();
            this.outputStream = this.process.outputWriter();
            if (this.isAlive()) {
                return "";
            }else {
                return "Program not found";
            }
        } catch (IOException e) {

        }
        return "Program not found";
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
                    if (character == 12){
                        return "clear";
                    }
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

    /**
     * <h1>getConfig()</h1>
     * <p>Get the configuration of the .conf file</p>
     * @return String[]
     * @author D4vsus
     */
    @Override
    public String[] getConfig() {
        Path path = Paths.get("..\\Resources\\","JT-2.conf").normalize();
        File config = new File(path.toString());

        List<String> line = new ArrayList<>();

        try (Scanner reader = new Scanner(config)){
            while (reader.hasNextLine()){
                line.add(reader.nextLine());
            }
            return line.toArray(new String[0]);
        } catch (FileNotFoundException e) {
            return new String[]{""};
        }
    }

    /**
     * <h1>runConfig()</h1>
     * <p>Take the information of the .conf and work with it</p>
     * @author D4vsus
     */
    @Override
    public void runConfig(){
        String[] lines = getConfig();
        for (String line:lines) {
            switch (line.split(":",2)[0].toLowerCase().trim()) {
                case "init":
                    startProgram(getProgramArguments(line.split(":",2)[1]));
                    break;
                default:
                     break;
            }
        }
    }

    /**
     * <h1>getProgramArguments()</h1>
     * <p>Turn the string into a list of arguments</p>
     * @param string : String
     * @return List<String>
     * @author D4vsus
     */
    @Override
    public List<String> getProgramArguments(String string){
            List<String> arguments;
            arguments = new ArrayList<>();
            String[] string_array_splitted_by_quotes = string.split("\"");
            for (int x = (string.charAt(0) != '\"') ? 0 : 1; x < string_array_splitted_by_quotes.length; x++) {
                if (x % 2 == 0) {
                    String[] string_array_splitted_by_spaces = string_array_splitted_by_quotes[x].split(" ");
                    arguments.addAll(Arrays.asList(string_array_splitted_by_spaces));
                } else {
                    arguments.add(string_array_splitted_by_quotes[x]);
                }
            }
            return arguments;
    }
}
