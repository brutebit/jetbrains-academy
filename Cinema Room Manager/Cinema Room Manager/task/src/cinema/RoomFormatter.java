package cinema;

public class RoomFormatter {
    private final Room room;

    RoomFormatter(Room room) {
        this.room = room;
    }

    public String formatRoom() {
        return makeFirstLine() + makeSeatLines();
    }

    public String formatStatistics(int currentIncome, int totalIncome) {
        return String.format(
                "Number of purchased tickets: %d%n" +
                "Percentage: %.2f%%%n" +
                "Current income: $%d%n" +
                "Total income: $%d",
                room.getNumberOfPurchasedTickets(),
                ((double)room.getNumberOfPurchasedTickets() / (double)room.totalNumberOfTickets()) * 100,
                currentIncome,
                totalIncome
        );
    }

    private String makeFirstLine() {
        StringBuilder output = new StringBuilder();
        for (int col = 0; col < room.getNumSeatsInRow(); col++) {
            if (col == 0) {
                output.append("  ");
            }
            if (isFinalColumn(col)) {
                output.append(col + 1);
            } else {
                output.append(col + 1).append(" ");
            }
        }
        output.append("\n");
        return output.toString();
    }

    private String makeSeatLines() {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < room.getNumRows(); row++) {
            output.append(row + 1).append(" ");
            for (int col = 0; col < room.getNumSeatsInRow(); col++) {
                output.append(room.getSeats()[row][col]);
                if (!isFinalColumn(col))
                    output.append(" ");
            }
            if (!isFinalRow(row))
                output.append("\n");
        }
        return output.toString();
    }

    private boolean isFinalColumn(int col) {
        return col == room.getNumSeatsInRow() - 1;
    }

    private boolean isFinalRow(int row) {
        return row == room.getNumRows() - 1;
    }
}
