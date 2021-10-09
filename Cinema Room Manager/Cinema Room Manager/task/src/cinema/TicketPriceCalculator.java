package cinema;

public class TicketPriceCalculator {
    private Room room;

    public TicketPriceCalculator(Room room) {
        this.room = room;
    }

    public int calculateTotalTicketsPrice() {
        if (isNoMoreThan60()) {
            return 10 * getTotalNumSeats(room.getNumRows());
        }

        int firstHalfNum = getFirstHalfRowNum();
        int secondHalfNum = getSecondHalfRowNum();
        return  (10 * getTotalNumSeats(firstHalfNum)) + (8 * getTotalNumSeats(secondHalfNum));
    }

    public int getTicketPrice(int rowNum) {
        int firstHalfNum = getFirstHalfRowNum();
        if (isNoMoreThan60() || rowNum <= firstHalfNum) {
            return 10;
        }
        return 8;
    }

    private int getFirstHalfRowNum() {
        return room.getNumRows() / 2;
    }

    private int getSecondHalfRowNum() {
        if (room.getNumRows() % 2 != 0) {
            return getFirstHalfRowNum() + 1;
        }
        return getFirstHalfRowNum();
    }

    private boolean isNoMoreThan60() {
        return getTotalNumSeats(room.getNumRows()) <= 60;
    }

    private int getTotalNumSeats(int numRows) {
        return room.getNumSeatsInRow() * numRows;
    }
}
