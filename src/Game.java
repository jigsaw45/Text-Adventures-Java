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
}
