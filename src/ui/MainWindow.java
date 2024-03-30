package ui;

import logic.ProgramInterfaceImplementation;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>MainWindow</h1>
 * <p>Manage the tabs of the terminals and the menu</p>
 * @author D4vsus
 */
public class MainWindow extends JFrame{
    private JTabbedPane Folder;
    private JPanel TerminalsWindow;
    private final List<Terminal> Terminals;

    /**
     * <h1>MainWindow()</h1>
     * <p>Initialize the window</p>
     * @author D4vsus
     */
    public MainWindow() {
        this.Terminals = new ArrayList<Terminal>();

        this.addMenu();
        this.addTerminal("Terminal");

        //Set up main window
        this.setTitle("JT-2");
        this.setBounds(100,100,500,400);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setContentPane(TerminalsWindow);
        this.setVisible(true);
        Folder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(SwingUtilities.isRightMouseButton(e)) {
                    //add dropdown menu
                    addRightMenuButton().show(Folder,e.getX(),e.getY());
                }
            }
        });
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                Terminals.clear();
                System.exit(0);
            }
        });
    }

    /**
     * <h1>addTerminal()</h1>
     * <p>Add a new tab with a terminal</p>
     *@author D4vsus
     */
    private void addTerminal(String titel){
        Terminal terminal = new Terminal(new ProgramInterfaceImplementation());
        this.Terminals.add(terminal);
        this.Folder.addTab(titel,terminal.getPanel());
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
                    new_terminal.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            addTerminal("Terminal");
                        }
                    });
                menu_Tabs.add(new_terminal);
                JMenuItem new_window = new JMenuItem("New Window(Ctrl + Alt + t)");
                    new_window.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                new MainWindow();
            }
                    });
                menu_Tabs.add(new_window);
            menu_bar.add(menu_Tabs);
        setJMenuBar(menu_bar);
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
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        int tab;
                        if ((tab = Folder.getSelectedIndex()) >= 0){
                            Folder.removeTabAt(Math.max(tab, 0));
                            Terminals.remove(tab);
                        }
                }
            });
        rightClickMenu.add(delete);
        return rightClickMenu;
    }
}