import java.util.*;

class Room {
    private int roomNumber;
    private String category;
    private boolean isBooked;

    public Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isBooked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public String getCategory() {
        return category;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void book() {
        isBooked = true;
    }

    public void cancel() {
        isBooked = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + category + ") - " + (isBooked ? "Booked" : "Available");
    }
}

class Booking {
    private String guestName;
    private Room room;
    private double amountPaid;

    public Booking(String guestName, Room room, double amountPaid) {
        this.guestName = guestName;
        this.room = room;
        this.amountPaid = amountPaid;
    }

    public Room getRoom() {
        return room;
    }

    public void showDetails() {
        System.out.println("\n📄 Booking Details:");
        System.out.println("Guest: " + guestName);
        System.out.println("Room: " + room.getRoomNumber() + " (" + room.getCategory() + ")");
        System.out.println("Amount Paid: ₹" + amountPaid);
    }
}

public class HotelReservationSystem {
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();

    public HotelReservationSystem() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Deluxe"));
        rooms.add(new Room(103, "Suite"));
        rooms.add(new Room(104, "Standard"));
        rooms.add(new Room(105, "Deluxe"));
    }

    private double getPrice(String category) {
        switch (category.toLowerCase()) {
            case "standard": return 2000;
            case "deluxe": return 3500;
            case "suite": return 5000;
            default: return 0;
        }
    }

    public void showAvailableRooms() {
        System.out.println("\n🏨 Available Rooms:");
        for (Room room : rooms) {
            if (!room.isBooked()) {
                System.out.println(room);
            }
        }
    }

    public Room findAvailableRoom(String category) {
        for (Room room : rooms) {
            if (!room.isBooked() && room.getCategory().equalsIgnoreCase(category)) {
                return room;
            }
        }
        return null;
    }

    public void bookRoom(String guestName, String category) {
        Room room = findAvailableRoom(category);
        if (room != null) {
            room.book();
            double price = getPrice(category);
            Booking booking = new Booking(guestName, room, price);
            bookings.add(booking);
            System.out.println("✅ Room booked successfully!");
            booking.showDetails();
        } else {
            System.out.println("❌ No available rooms in " + category + " category.");
        }
    }

    public void cancelBooking(int roomNumber) {
        for (Booking booking : bookings) {
            if (booking.getRoom().getRoomNumber() == roomNumber) {
                booking.getRoom().cancel();
                bookings.remove(booking);
                System.out.println("✅ Booking for Room " + roomNumber + " has been cancelled.");
                return;
            }
        }
        System.out.println("❌ Booking not found for Room " + roomNumber + ".");
    }

    public void showAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("ℹ No current bookings.");
        } else {
            for (Booking b : bookings) {
                b.showDetails();
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HotelReservationSystem hotel = new HotelReservationSystem();

        while (true) {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel a Booking");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    hotel.showAvailableRooms();
                    break;
                case 2:
                    System.out.print("Enter your name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter room category (Standard/Deluxe/Suite): ");
                    String category = sc.nextLine();
                    hotel.bookRoom(name, category);
                    break;
                case 3:
                    System.out.print("Enter room number to cancel: ");
                    int roomNo = sc.nextInt();
                    hotel.cancelBooking(roomNo);
                    break;
                case 4:
                    hotel.showAllBookings();
                    break;
                case 5:
                    System.out.println("👋 Thank you for using the system!");
                    return;
                default:
                    System.out.println("⚠ Invalid choice. Try again.");
            }
        }
    }
}