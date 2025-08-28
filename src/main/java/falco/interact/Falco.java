package falco.interact;

import falco.storage.Storage;
import falco.storage.TaskList;

public class Falco {
    private final static String LIST_PATH = "./data/falcolist.txt";
    private TaskList tasks;
    private Storage storage;
    private Ui ui;

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

    public void run() {
        ui.greetings();
        Parser parser = new Parser(tasks, storage);
        parser.parse(ui.askInput());
    }

    public static void main(String[] args) {
        new Falco(LIST_PATH).run();
    }
}

