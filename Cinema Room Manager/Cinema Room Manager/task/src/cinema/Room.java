package cinema;

public class Room {
    private int numRows;
    private int numSeatsInRow;
    private char[][] seats;
    private int numberOfPurchasedTickets = 0;

    public Room(int numRows, int numSeatsInRow) {
        this.numSeatsInRow = numSeatsInRow;
        this.numRows = numRows;

        seats = new char[numRows][numSeatsInRow];
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numSeatsInRow; col++) {
                seats[row][col] = 'S';
            }
        }
    }

    public int totalNumberOfTickets() {
        return numRows * numSeatsInRow;
    }

    public int getNumSeatsInRow() {
        return numSeatsInRow;
    }

    public int getNumRows() {
        return numRows;
    }

    public char[][] getSeats() {
        return seats;
    }

    public void markSeat(int rowNum, int seatNum) {
        seats[rowNum-1][seatNum-1] = 'B';
        numberOfPurchasedTickets++;
    }

    public int getNumberOfPurchasedTickets() {
        return numberOfPurchasedTickets;
    }

    public void setNumberOfPurchasedTickets(int numberOfPurchasedTickets) {
        this.numberOfPurchasedTickets = numberOfPurchasedTickets;
    }
}
