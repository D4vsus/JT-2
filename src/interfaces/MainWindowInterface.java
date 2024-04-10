package interfaces;

public interface MainWindowInterface {
    /**
     * <h1>addTerminal()</h1>
     * <p>Add a new tab with a terminal</p>
     *
     * @author D4vsus
     */
    void addTab(String title);

    /**
     * <h1>renameTerminal()</h1>
     * <p>Rename a terminal</p>
     *
     * @param title : String
     * @author D4vsus
     */
    void renameTab(String title);

    /**
     * <h1>removeTab()</h1>
     * <p>Remove the selected tab</p>
     * @author D4vsus
     */
     void removeTab();
}
