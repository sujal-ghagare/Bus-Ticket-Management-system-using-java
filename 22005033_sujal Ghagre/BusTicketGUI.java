import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Bus class
class Bus {
    private String busNumber, departure, destination, departureTime, arrivalTime;
    private int availableSeats;
    private double fare;

    public Bus(String busNumber, String departure, String destination,
               String departureTime, String arrivalTime, int availableSeats, double fare) {
        this.busNumber = busNumber;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.availableSeats = availableSeats;
        this.fare = fare;
    }

    public String getBusNumber() { return busNumber; }
    public String getDeparture() { return departure; }
    public String getDestination() { return destination; }
    public String getDepartureTime() { return departureTime; }
    public String getArrivalTime() { return arrivalTime; }
    public int getAvailableSeats() { return availableSeats; }
    public void setAvailableSeats(int seats) { this.availableSeats = seats; }
    public double getFare() { return fare; }

    @Override
    public String toString() {
        return "Bus " + busNumber + ": " + departure + " -> " + destination +
                " (" + departureTime + " - " + arrivalTime + ") | Seats: " + availableSeats +
                " | Fare: Rs. " + fare;
    }
}

// BusReservationSystem class
class BusReservationSystem {
    private List<Bus> buses;

    public BusReservationSystem() {
        buses = new ArrayList<>();
        initializeBuses();
    }

    private void initializeBuses() {
        buses.add(new Bus("NB101", "Nagpur", "Bhopal", "06:00 AM", "10:00 AM", 40, 299.0));
        buses.add(new Bus("NB102", "Nagpur", "Pune", "08:00 PM", "06:00 AM", 35, 599.0));
        buses.add(new Bus("MP201", "Mumbai", "Pune", "07:00 AM", "11:00 AM", 50, 349.0));
        buses.add(new Bus("MD202", "Mumbai", "Delhi", "09:00 AM", "01:00 AM", 30, 999.0));
        buses.add(new Bus("DP301", "Delhi", "Pune", "04:00 PM", "09:00 AM", 25, 1099.0));
    }

    public List<Bus> searchBuses(String departure, String destination) {
        List<Bus> result = new ArrayList<>();
        for (Bus bus : buses) {
            if (bus.getDeparture().equalsIgnoreCase(departure) &&
                bus.getDestination().equalsIgnoreCase(destination) &&
                bus.getAvailableSeats() > 0) {
                result.add(bus);
            }
        }
        return result;
    }

    public boolean makeReservation(String busNumber) {
        for (Bus bus : buses) {
            if (bus.getBusNumber().equalsIgnoreCase(busNumber) &&
                bus.getAvailableSeats() > 0) {
                bus.setAvailableSeats(bus.getAvailableSeats() - 1);
                return true;
            }
        }
        return false;
    }

    public List<Bus> getAllBuses() {
        return buses;
    }
}

// GUI Application
public class BusTicketGUI extends JFrame {
    private BusReservationSystem system;
    private JTextArea textArea;
    private JTextField txtFrom, txtTo, txtBusNum;

    public BusTicketGUI() {
        system = new BusReservationSystem();

        setTitle("Bus Ticket Management System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Search / Book Bus"));

        inputPanel.add(new JLabel("From:"));
        txtFrom = new JTextField();
        inputPanel.add(txtFrom);

        inputPanel.add(new JLabel("To:"));
        txtTo = new JTextField();
        inputPanel.add(txtTo);

        inputPanel.add(new JLabel("Bus Number to Book:"));
        txtBusNum = new JTextField();
        inputPanel.add(txtBusNum);

        JButton btnSearch = new JButton("Search Buses");
        JButton btnBook = new JButton("Book Ticket");
        inputPanel.add(btnSearch);
        inputPanel.add(btnBook);

        add(inputPanel, BorderLayout.NORTH);

        // Center Panel
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        JButton btnViewAll = new JButton("View All Buses");
        JButton btnExit = new JButton("Exit");
        bottomPanel.add(btnViewAll);
        bottomPanel.add(btnExit);
        add(bottomPanel, BorderLayout.SOUTH);

        // Actions
        btnSearch.addActionListener(e -> {
            String from = txtFrom.getText().trim();
            String to = txtTo.getText().trim();
            List<Bus> found = system.searchBuses(from, to);
            if (found.isEmpty()) {
                textArea.setText("No matching buses found.");
            } else {
                StringBuilder sb = new StringBuilder("Matching Buses:\n");
                for (Bus b : found) {
                    sb.append(b).append("\n--------------------------\n");
                }
                textArea.setText(sb.toString());
            }
        });

        btnBook.addActionListener(e -> {
            String busNo = txtBusNum.getText().trim();
            if (system.makeReservation(busNo)) {
                textArea.setText("Reservation successful for bus " + busNo + "!");
            } else {
                textArea.setText("Reservation failed. Bus not found or no seats available.");
            }
        });

        btnViewAll.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("All Available Buses:\n");
            for (Bus b : system.getAllBuses()) {
                sb.append(b).append("\n--------------------------\n");
            }
            textArea.setText(sb.toString());
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BusTicketGUI().setVisible(true));
    }
}
