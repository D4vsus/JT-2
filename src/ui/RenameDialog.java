package ui;

import javax.swing.*;
import java.awt.event.*;

/**
 * <h1>RenameDialog</h1>
 * <p>A dialog window to rename tabs of the file</p>
 * @author D4vsus
 */
public class RenameDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField newName;

    MainWindow mw;

    public RenameDialog(MainWindow mw) {
        this.mw = mw;
        setContentPane(contentPane);
        setBounds(100,100,400,100);
        setModal(true);
        setTitle("Rename");
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        mw.renameTab(newName.getText());
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
