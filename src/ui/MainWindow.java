package ui;

import logic.ProgramInterfaceImplementation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainWindow extends JFrame{
    private JTabbedPane Folder;
    private JPanel TerminalsWindow;
    private final List<Terminal> Terminals;

    private void addTerminal(String titel){
        Terminal terminal = new Terminal(new ProgramInterfaceImplementation());
        this.Terminals.add(terminal);
        this.Folder.addTab(titel,terminal.getPanel());
    }
    
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
            menu_bar.add(menu_Tabs);
        setJMenuBar(menu_bar);
    }

    private JPopupMenu addRightMenuButton(){
        JPopupMenu rightClickMenu = new JPopupMenu();
            JMenuItem delete = new JMenuItem("Delete (Ctrl + -)");
            delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int tab = Folder.getSelectedIndex();
                        if ((tab = Folder.getSelectedIndex()) >= 0){
                            Folder.removeTabAt(Math.max(tab, 0));
                            Terminals.remove(tab);
                        }
                    } catch(IndexOutOfBoundsException exception){
                        exception.printStackTrace();
                    }
                }
            });
        rightClickMenu.add(delete);
        return rightClickMenu;
    }
    public MainWindow() {
        this.Terminals = new ArrayList<Terminal>();

        this.addMenu();
        this.addTerminal("Terminal");

        //Set up main window
        this.setTitle("JT-2");
        this.setBounds(100,100,500,400);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
    }
}