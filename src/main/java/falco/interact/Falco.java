package falco.interact;

import falco.storage.Storage;
import falco.storage.TaskList;

/**
 * Represents the chatbot named <code>Falco</code>.
 */
public class Falco {
    public static final String LIST_PATH = "./data/falcolist.txt";
    private TaskList tasks;
    private Storage storage;
    private Ui ui;
    /**
     * Create a new <code>Falco</code> instance with the designated file path.
     *
     * @param filePath A path directory to the save file
     */
    public Falco(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }

    }

    /**
     * Run the system to start <code>Falco</code>.
     */
    public void run() {
        ui.sayGreetings();
        Parser parser = new Parser(tasks, storage);
        parser.parse(ui.askInput());
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        ParserGUI parser = new ParserGUI(tasks, storage);
        String response = parser.parse(input);
        return response;
    }

    public static void main(String ... args) {
        new Falco(LIST_PATH).run();
    }


}

