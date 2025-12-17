public class CommandParser {
    public static Command parse(String input){
        if(input == null){
            return new Command("", "");
        }
        String[] parts = input.trim().toLowerCase().split("\\s+", 2);
        String verb = parts.length >0 ? parts[0] : "";
        String noun = parts.length >1 ? parts[1] : "";
        return new Command(verb, noun);
    }
}
