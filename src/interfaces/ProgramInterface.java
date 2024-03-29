package interfaces;

import java.util.List;

public interface ProgramInterface {
    public String startProgram(List<String> arguments);
    public void readInput(String string);
    public String writeOutput();
    public String writeError();
    public void endProgram();
    public boolean isAlive();
}
