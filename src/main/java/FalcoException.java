public class FalcoException extends Exception{
    public enum ErrorType {
        UNKNOWN_COMMAND,
        EMPTY_TASK,
        NOTIME_DEADLINE,
        UNCLEAR_EVENT,
        UNCLEAR_DELETE,
        UNCLEAR_MARK,
        OUTOFBOUNDS,
        EMPTY_LIST
    }

    private ErrorType type;

    public FalcoException(ErrorType type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        switch(type) {
        case EMPTY_TASK:
            return "Forgive me Sir, but you didn't specify the task... (￣^￣ )ゞ";
        case UNKNOWN_COMMAND:
            return "Negative Sir! Your command can't be understand ૮(˶ㅠ︿ㅠ)ა";
        case NOTIME_DEADLINE:
            return "Forgive me Sir, but you didn't correctly specify the time... ૮(˶ㅠ︿ㅠ)ა" +
                    "\nYou should type it as: Deadline <task> /by <time>";
        case UNCLEAR_EVENT:
            return "Forgive me Sir, but your event time is unclear... ૮(˶ㅠ︿ㅠ)ა" +
                    "\nYou should type it as: Event <task> /from <start-time> /to <end-time>";
        case UNCLEAR_DELETE:
            return "Uh Sir, which task specifically do you want to delete (ಠ_ಠ)?" +
                    "\nSpecify it as: Delete <task-number>";
        case UNCLEAR_MARK:
            return "Uh Sir, which one are you referring to (ಠ_ಠ)?" +
                    "\nSpecify it as: Mark/Unmark <task-number>";
        case OUTOFBOUNDS:
            return "Sir, that number is invalid. (ಠ_ಠ)" +
                    "\nTry checking the task-list by typing: List";
        case EMPTY_LIST:
            return "Sir, you haven't added any task yet (ಠ_ಠ)" +
                    "\nTry adding some task first";
            default:
            return "I don't understand, Sir ૮(˶ㅠ︿ㅠ)ა";
        }
    }
}
