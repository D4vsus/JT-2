package ui;

import interfaces.MainWindowInterface;
import logic.ProgramInterfaceImplementation;

import javax.swing.*;
import java.awt.event.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>MainWindow</h1>
 * <p>Manage the tabs of the terminals and the menu</p>
 * @author D4vsus
 */
public class MainWindow extends JFrame implements KeyListener, MainWindowInterface {
    private JTabbedPane Folder;
    private JPanel TerminalsWindow;
    private final List<Terminal> Terminals;

    /**
     * <h1>MainWindow()</h1>
     * <p>Initialize the window</p>
     * @author D4vsus
     */
    public MainWindow() {
        this.Terminals = new ArrayList<>();

        this.addMenu();
        this.addTab("Terminal");

        //Set up main window
        this.setTitle("JT-2");
        this.setBounds(100, 100, 500, 400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(TerminalsWindow);
        this.Folder.addKeyListener(this);
        this.addKeyListener(this);
        this.setIconImage(new ImageIcon(Paths.get("..\\Resources\\JT-2.png").normalize().toString()).getImage());

        Folder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    //add dropdown menu
                    addRightMenuButton().show(Folder, e.getX(), e.getY());
                }
            }
        });
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (Terminal terminal:Terminals){
                    terminal.endProgram();
                }
                Terminals.clear();
            }
        });
        this.setVisible(true);
        Folder.addChangeListener(e -> {
            if (getCurrentTerminal().isProgramRunning() && getCurrentTerminal().getPanel().isVisible()) {
                getCurrentTerminal().connectProgram();
            }
        });
    }

    /**
     * <h1>renameTab()</h1>
     * <p>Rename a terminal</p>
     * @param title : String
     *@author D4vsus
     */
    public void renameTab(String title){
        this.Folder.setTitleAt(this.Folder.getSelectedIndex(), title);
    }

    /**
     * <h1>addMenu()</h1>
     * <p>Add the menu on the top of the window</p>
     * @author D4vsus
     */
    private void addMenu(){
        JMenuBar menu_bar = new JMenuBar();
            JMenu menu_Tabs = new JMenu("Tabs");
                JMenuItem new_terminal = new JMenuItem("New Terminal(Ctrl + t)");
                    new_terminal.addActionListener(e -> addTab("Terminal"));
                menu_Tabs.add(new_terminal);
                JMenuItem new_window = new JMenuItem("New Window(Ctrl + Alt + t)");
                    new_window.addActionListener(e -> new MainWindow());
                menu_Tabs.add(new_window);
        JMenuItem clear_window = new JMenuItem("Clear (Ctrl + s)");
        clear_window.addActionListener(e -> clear());
        menu_Tabs.add(clear_window);
        menu_bar.add(menu_Tabs);
        setJMenuBar(menu_bar);
    }

    /**
     * <h1>clear()</h1>
     * <p>Clear the current terminal</p>
     */
    private void clear() {
        Terminals.get(Folder.getSelectedIndex()).clear();
    }

    /**
     * <h1>addRightMenuButton()</h1>
     * <p>Add the right click menu on the tabs</p>
     * @return Right click menu: JPopupMenu
     * @author D4vsus
     */
    private JPopupMenu addRightMenuButton(){
        JPopupMenu rightClickMenu = new JPopupMenu();
            JMenuItem delete = new JMenuItem("Delete (Ctrl + -)");
            JMenuItem rename = new JMenuItem("Rename (Ctrl + r)");
            delete.addActionListener(e -> removeTab());
            rename.addActionListener(e -> openRenameDialog());
        rightClickMenu.add(delete);
        rightClickMenu.add(rename);
        return rightClickMenu;
    }

    /**
     * <h1>openRenameDialog()</h1>
     * <p>Open the rename dialog window</p>
     * @author D4vsus
     */
    private void openRenameDialog(){
        new RenameDialog(this);
    }

    /**
     * <h1>addTab()</h1>
     * <p>Add a new tab with a terminal</p>
     *@author D4vsus
     */
    public void addTab(String title){
        Terminal terminal = new Terminal(new ProgramInterfaceImplementation());
        terminal.addKeyListenerToTheTerminal(this);
        this.Terminals.addFirst(terminal);
        this.Folder.insertTab(title,null,terminal.getPanel(),null,0);
    }

    /**
     * <h1>removeTab()</h1>
     * <p>Remove the selected tab</p>
     * @author D4vsus
     */
    public void removeTab(){
        int tab = Folder.getSelectedIndex();
        if (tab >= 0){
            Folder.removeTabAt(tab);
            Terminals.get(tab).endProgram();
            Terminals.remove(tab);
            if (tab < Terminals.size()) Terminals.get(tab).connectProgram();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_T:
                    if((e.getModifiersEx() & KeyEvent.ALT_DOWN_MASK) != 0) new MainWindow();
                    else addTab("Terminal");
                    break;
                case KeyEvent.VK_S:
                    clear();
                    break;
                case KeyEvent.VK_Z:
                    endTerminalProgram();
                    break;
                case KeyEvent.VK_MINUS:
                    removeTab();
                    break;
                case KeyEvent.VK_N:
                    openRenameDialog();
                    break;
                case KeyEvent.VK_R:
                    new RenameDialog(this);
                    break;
                default:
            }
        }
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                getCurrentTerminal().previousValueRecord();
                break;
            case KeyEvent.VK_DOWN:
                getCurrentTerminal().nextValueRecord();
                break;
            default:
        }
    }

    /**
     * <h1>endTerminalProgram()</h1>
     * <p>End the current terminal program</p>
     * @author D4vsus
     */
    private void endTerminalProgram(){
        Terminal terminal = getCurrentTerminal();
        int exitValue = terminal.endProgram();
        terminal.print("\nProgram ended by force \nexit value: %d".formatted(exitValue));
    }

    /**
     * <h1>getCurrentTerminal()</h1>
     * <p>Get the terminal of the selected tab.</p>
     * <p>Return position 0 if there is no tab.</p>
     * @return Terminal
     * @author D4vsus
     */
    private Terminal getCurrentTerminal(){
        return (Folder.getTabCount() > 1)?Terminals.get(Folder.getSelectedIndex()):Terminals.getFirst();
    }
}