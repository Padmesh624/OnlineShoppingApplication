package com.hyperloop_passenger_booking;
import java.util.*;

public class CommandFunctionController {
    private static final Scanner scanner = new Scanner(System.in);
    private static final InputValidationUtil inputValidationUtil = new InputValidationUtil();
    private static final List<String[]> passengerList = new ArrayList<>();
    private static final HashMap<String, HashMap<String, Integer>> pathMap = new HashMap<>();
    private static HashMap<String ,String> shortestPathMap=new HashMap<>();
    private static int init = 0;
    private static String startingStation = "";

    ShortestPathService shortestPathService= new ShortestPathService();
    public static void main(String[] args) {
        CommandFunctionController controller = new CommandFunctionController();
        controller.run();
    }

    private void run() {
        System.out.println("               Welcome to Hyperloop Passenger Booking");
        while (true) {
            try {
                System.out.print("Enter the command: ");
                String[] command = scanner.nextLine().trim().split("\\s+");
                inputValidationUtil.validateCommand(command);

                if(!command[0].equals("INIT") && init==0)
                {
                    System.out.println("You need use INIT command first");
                    continue;
                }
                switch (command[0]) {
                    case "INIT":
                        initCommand(command);
                        break;
                    case "ADD_PASSENGER":
                        addPassengerCommand(command);
                        break;
                    case "START_POD":
                        startPodCommand(command);
                        break;
                    case "PRINT_Q":
                        printQueueCommand(command);
                        break;
                    default:
                        System.out.println("Enter a valid command");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e.getMessage());
            }
        }
    }

    private void initCommand(String[] command) throws Exception {
        if (command.length == 3) {
            init = 1;
            int numOfInterconnectingRoutes = Integer.parseInt(command[1]);
            startingStation = command[2];
            for (int i = 0; i < numOfInterconnectingRoutes; i++) {
                while (true) {
                    String[] path = scanner.nextLine().trim().split("\\s+");
                    if (path.length != 3) {
                        System.out.println("Enter a valid path (source dest distance): ");
                    } else {
                        inputValidationUtil.validateRoute(path);
                        String source = path[0];
                        String destination = path[1];
                        int distance = Integer.parseInt(path[2]);
                        if (!pathMap.containsKey(source)) {
                            pathMap.put(source, new HashMap<>());
                        }
                        if (!pathMap.containsKey(destination)) {
                            pathMap.put(destination, new HashMap<>());
                        }
                        pathMap.get(source).put(destination, distance);
                        pathMap.get(destination).put(source, distance);
                        break;
                    }
                }
            }

            //shortest path for all the routes are found once the interconnecting routes given
            shortestPathMap=  shortestPathService.path(startingStation, pathMap);

        } else {
            System.out.println("Invalid number of arguments for INIT command");
        }
    }

    private void addPassengerCommand(String[] command) throws Exception {
        if (command.length == 2) {
            int numOfPassengers = Integer.parseInt(command[1]);
            for (int p = 0; p < numOfPassengers; p++) {
                while (true) {
                    String[] passenger = scanner.nextLine().trim().split("\\s+");
                    if (passenger.length != 3) {
                        System.out.println("Enter a valid detail (name age dest): ");
                    } else {
                        inputValidationUtil.validatePassenger(passenger);
                        passengerList.add(passenger);
                        break;
                    }
                }
            }
        }
        else {
            System.out.println("Enter a valid command");
        }
    }

    private void startPodCommand(String[] command) {
        if (command.length == 2) {
            int numOfPods = Integer.parseInt(command[1]);
            if (numOfPods <= passengerList.size()) {
                for (int n = 0; n < numOfPods; n++) {
                    String[] passenger = new String[3];
                    int passengerAge = 0;
                    int index = 0;
                    for (int p = 0; p < passengerList.size(); p++) {
                        if (Integer.parseInt(passengerList.get(p)[1]) > passengerAge) {
                            passengerAge = Integer.parseInt(passengerList.get(p)[1]);
                            passenger = passengerList.get(p);
                            index = p;
                        }
                    }
                    // If starting station and destination is same
                    if (startingStation.equals(passenger[2])) {
                        System.out.println(passenger[0] + " " + startingStation);
                        passengerList.remove(index);
                        continue;
                    }

                    System.out.print(passenger[0] + " ");
                    shortestPathService.printShortestPath(startingStation, passenger, shortestPathMap);
                    passengerList.remove(index);
                }
            } else {
                System.out.println("Number of passengers is insufficient to pod");
            }
        } else {
            System.out.println("Enter a valid command");
        }
    }

    private void printQueueCommand(String[] command) {
        if (command.length == 1) {
            System.out.println(passengerList.size());
            for (String[] passenger : passengerList) {
                System.out.println(passenger[0] + " " + passenger[1]);
            }
        } else {
            System.out.println("No arguments required for PRINT_Q command");
        }
    }
}
