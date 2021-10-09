package cinema;

import java.util.Scanner;

public class Cinema {
    private Room room;
    private RoomFormatter formatter;
    private TicketPriceCalculator calculator;
    private int totalIncome;
    private int currentIncome = 0;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public RoomFormatter getFormatter() {
        return formatter;
    }

    public void setFormatter(RoomFormatter formatter) {
        this.formatter = formatter;
    }

    public TicketPriceCalculator getCalculator() {
        return calculator;
    }

    public void setCalculator(TicketPriceCalculator calculator) {
        this.calculator = calculator;
        totalIncome = calculator.calculateTotalTicketsPrice();
    }

    public void printRoom() {
        System.out.println();
        System.out.println("Cinema:");
        System.out.println(formatter.formatRoom());
        System.out.println();
    }

    public void purchaseTicket(int rowNum, int seatNum) {
        currentIncome += calculator.getTicketPrice(rowNum);
        room.markSeat(rowNum, seatNum);
    }

    public void printTicketPrice(int rowNum) {
        System.out.println();
        System.out.println("Ticket price: $" + calculator.getTicketPrice(rowNum));
        System.out.println();
    }

    public void printStatistics() {
        System.out.println();
        System.out.println(formatter.formatStatistics(currentIncome, totalIncome));
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        int numRows = s.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int numSeatsInRow = s.nextInt();

        Cinema cinema = new Cinema();
        Room room = new Room(numRows, numSeatsInRow);
        cinema.setRoom(room);
        cinema.setFormatter(new RoomFormatter(room));
        cinema.setCalculator(new TicketPriceCalculator(room));

        int choice;
        do {
            System.out.println(
                    "1. Show the seats\n" +
                            "2. Buy a ticket\n" +
                            "3. Statistics\n" +
                            "0. Exit"
            );
            choice = s.nextInt();

            if (choice == 1) {
                cinema.printRoom();
            } else if (choice == 2) {

                boolean validTicketNumber = false;
                do {
                    System.out.println("Enter a row number:");
                    int rowNum = s.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    int seatNum = s.nextInt();

                    try {
                        if (cinema.getRoom().getSeats()[rowNum - 1][seatNum - 1] == 'B') {
                            System.out.println("That ticket has already been purchased");
                        } else {
                            cinema.purchaseTicket(rowNum, seatNum);
                            cinema.printTicketPrice(rowNum);
                            validTicketNumber = true;
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Wrong input!");
                    }
                } while (!validTicketNumber);

            } else if (choice == 3) {
                cinema.printStatistics();
            }
        } while (choice != 0);
    }
}
