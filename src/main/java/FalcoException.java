public class FalcoException extends Exception{
    public enum ErrorType {
        UNKNOWN_COMMAND,
        EMPTY_TASK,
        NOTIME_DEADLINE,
        UNCLEAR_EVENT
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
                        "\nYou should type it as (Deadline <task> /by <time>)";
            case UNCLEAR_EVENT:
                return "Forgive me Sir, but your event time is unclear... ૮(˶ㅠ︿ㅠ)ა" +
                        "\nYou should type it as (Event <task> /from <start-time> /to <end-time>)";
            default:
                return "I don't understand, Sir ૮(˶ㅠ︿ㅠ)ა";
        }
    }
}
