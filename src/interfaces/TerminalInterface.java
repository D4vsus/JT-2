package interfaces;

import java.awt.*;

public interface TerminalInterface {
    public void print(String output);
    public void clear();
    public String getInput();
    public void setInput(String string);
    public void setFont(Font font);
}
