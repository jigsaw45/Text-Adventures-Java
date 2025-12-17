import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        Scanner scanner = new Scanner(System.in);
        String buttonSelection = "";
        int gameSpeed = 2;
        boolean run = true;
        boolean mainMenu = true;
        boolean room1 = false;
        boolean room2 = false;
        boolean room3 = false;
        boolean room4 = false;
        boolean sword = false;

        while(run){
            //main menu loop
            if(mainMenu) {
                while(true) {
                    System.out.println("Welcome to Kevin");
                    System.out.println("----------------------");
                    System.out.println("(1) Start game");
                    System.out.println("(2) Options");
                    buttonSelection = scanner.nextLine();
                    //exit main menu
                    if (buttonSelection.equalsIgnoreCase("1")) {
                        mainMenu=false;
                        room1=true;
                        break;
                    }
                    //options loop
                    else if (buttonSelection.equalsIgnoreCase("2")) {
                        while (true) {
                            System.out.println("----------------------");
                            System.out.println("(1) Change game speed\n(2) Leave options");
                            buttonSelection = scanner.nextLine();
                            //change game speed
                            if (buttonSelection.equalsIgnoreCase("1")) {
                                System.out.println("----------------------");
                                System.out.println("Enter game speed(1-4):");
                                gameSpeed = scanner.nextInt();
                                scanner.nextLine();
                            }
                            //exit options
                            else if (buttonSelection.equalsIgnoreCase("2")) {
                                break;
                            }
                            //if user inputs incorrect input loops around to options
                            else {
                                System.out.println("----------------------");
                                System.out.println("Invalid input please try again.");
                            }
                        }

                    }
                    //if user inputs incorrect input loops around to main menu
                    else {
                        System.out.println("----------------------");
                        System.out.println("Invalid input please try again.");
                    }
                }
            }
            //room loop
            else if(room1){
                System.out.println("----------------------");
                System.out.println("Hi you are in room 1");
                    //room 1 selection
                    while(true){
                        System.out.println("(1)Move rooms\n(2)look around");
                        buttonSelection = scanner.nextLine();

                        //move rooms
                        if(buttonSelection.equalsIgnoreCase("1")){
                            System.out.println("Which direction do you want to go? (North, South, West, East)");
                            buttonSelection = scanner.nextLine();

                            //changes gameloop to room 2
                            if(buttonSelection.equalsIgnoreCase("n")||buttonSelection.equalsIgnoreCase("north")){
                                room1=false;
                                room2=true;
                                break;
                            }
                            //changes game loop to room 4
                            else if(buttonSelection.equalsIgnoreCase("w")||buttonSelection.equalsIgnoreCase("west")){
                                room1=false;
                                room4=true;
                                break;
                            }
                            else{
                                System.out.println("you cannot go that way");
                            }
                        }
                        //look around
                        else if(buttonSelection.equalsIgnoreCase("2")){
                            System.out.println("theres nothing around");
                        }
                    }
            }
            else if(room2){
                System.out.println("----------------------");
                System.out.println("Hi you are in room 2");
                //room selection
                while(true){
                    System.out.println("(1)Move rooms\n(2)look around");
                    buttonSelection = scanner.nextLine();

                    //move rooms
                    if(buttonSelection.equalsIgnoreCase("1")){
                        System.out.println("Which direction do you want to go? (North, South, West, East)");
                        buttonSelection = scanner.nextLine();

                        //changes game loop to room 1
                        if(buttonSelection.equalsIgnoreCase("s")||buttonSelection.equalsIgnoreCase("south")){
                            room2=false;
                            room1=true;
                            break;
                        }
                        //changes game loop to room 3
                        else if(buttonSelection.equalsIgnoreCase("w")||buttonSelection.equalsIgnoreCase("west")){
                            room2=false;
                            room3=true;
                            break;
                        }
                        else{
                            System.out.println("you cannot go that way");
                        }
                    }
                    //look around
                    else if(buttonSelection.equalsIgnoreCase("2")){
                        System.out.println("You found a sword!");
                        sword=true;
                    }
                }
            }
            else if(room3) {
                System.out.println("----------------------");
                System.out.println("Hi you are in room 3");
                //room selection
                while (true) {
                    System.out.println("(1)Move rooms\n(2)look around");
                    buttonSelection = scanner.nextLine();

                    //move rooms
                    if (buttonSelection.equalsIgnoreCase("1")) {
                        System.out.println("Which direction do you want to go? (North, South, West, East)");
                        buttonSelection = scanner.nextLine();

                        //changes gameloop to room 2
                        if (buttonSelection.equalsIgnoreCase("e") || buttonSelection.equalsIgnoreCase("east")) {
                            room3 = false;
                            room2 = true;
                            break;
                        }
                        //changes game loop to room 4
                        else if (buttonSelection.equalsIgnoreCase("s") || buttonSelection.equalsIgnoreCase("south")) {
                            room3 = false;
                            room4 = true;
                            break;
                        } else {
                            System.out.println("you cannot go that way");
                        }
                    }
                    //look around
                    else if (buttonSelection.equalsIgnoreCase("2")) {
                        System.out.println("theres nothing around");
                    }
                }
            }
            else if(room4){
                System.out.println("----------------------");
                System.out.println("Hi you are in room 4");
                //room selection
                while(true){
                    System.out.println("(1)Move rooms\n(2)look around");
                    buttonSelection = scanner.nextLine();

                    //move rooms
                    if(buttonSelection.equalsIgnoreCase("1")){
                        System.out.println("Which direction do you want to go? (North, South, West, East)");
                        buttonSelection = scanner.nextLine();

                        //changes gameloop to room 2
                        if(buttonSelection.equalsIgnoreCase("n")||buttonSelection.equalsIgnoreCase("north")){
                            room4=false;
                            room3=true;
                            break;
                        }
                        //changes game loop to room 4
                        else if(buttonSelection.equalsIgnoreCase("e")||buttonSelection.equalsIgnoreCase("east")){
                            room4=false;
                            room1=true;
                            break;
                        }
                        else{
                            System.out.println("you cannot go that way");
                        }
                    }
                    //look around
                    else if(buttonSelection.equalsIgnoreCase("2")){
                        System.out.println("theres nothing around");
                    }
                }
            }



        }
    }
}