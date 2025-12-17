import java.util.Scanner;
public class Game {
    private Player player;
    private boolean running = true;
    private Scanner scanner = new Scanner(System.in);

    public Game(){
        setupworld();
    }

    private void setupworld(){
        Room r1 = new Room("room 1", "this is room 1");
        Room r2 = new Room("room 2", "this is room 2");
        Room r3 = new Room("room 3", "this is room 3");
        player = new Player("Player", r1);
    }

    public void start() {
        System.out.println("Welcome to Kevin");
        while(running){
            System.out.println("----------------------");
            System.out.println("(1) Start game");
            System.out.println("(2) Options");
            System.out.println(">:");
            String input = scanner.nextLine();
            Command cmd = CommandParser.parse(input);
            handleCommand(cmd);
        }
    }
    public void handleCommand(Command cmd){
        // Basic dispatcher: look, go, take, drop, inventory, help, quit
        switch(cmd.getVerb()){
            case "look":
                System.out.println(player.getCurrentRoom());
                break;
            case "go":
                break;
            case "move":
                if(cmd.hasNoun()){
                    Room next = player.getCurrentRoom();
                    if(next != null){
                        player.setCurrentRoom(next);
                        System.out.println(player.getCurrentRoom());
                    }else{
                        System.out.println("You cant go that way!");
                    }
                }else{
                    System.out.println("Go where?");
                }
                break;
            case "quit":
                running = false;
                System.out.println("Goodbye.");
                break;
            default:
                System.out.println("invalid command!");
        }

    }
}
