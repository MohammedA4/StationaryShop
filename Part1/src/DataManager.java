import java.io.*;
import java.util.ArrayList;

// This class handles reading from and writing to files.
public class DataManager {
    // File names where the data is saved.
    public static final String SUPPLIERS_FILE = "suppliers.txt";
    public static final String ORDERS_FILE = "orders.txt";

    // Loads suppliers from the file and returns a list.
    public static ArrayList<Supplier> loadSuppliers() {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        File file = new File(SUPPLIERS_FILE);
        if (!file.exists()) {
            return suppliers;  // Return an empty list if the file doesn't exist.
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // Read each line and create a Supplier object.
            while ((line = br.readLine()) != null) {
                Supplier supplier = Supplier.fromFileString(line);
                if (supplier != null) {
                    suppliers.add(supplier);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading suppliers: " + e.getMessage());
        }
        return suppliers;
    }

    // Saves the list of suppliers to a file.
    public static void saveSuppliers(ArrayList<Supplier> suppliers) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(SUPPLIERS_FILE))) {
            for (Supplier s : suppliers) {
                pw.println(s.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving suppliers: " + e.getMessage());
        }
    }

    // Loads orders from the file and returns a list.
    public static ArrayList<Order> loadOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        File file = new File(ORDERS_FILE);
        if (!file.exists()) {
            return orders;  // Return an empty list if the file doesn't exist.
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // Read each line and create an Order object.
            while ((line = br.readLine()) != null) {
                Order order = Order.fromFileString(line);
                if (order != null) {
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
        return orders;
    }

    // Saves the list of orders to a file.
    public static void saveOrders(ArrayList<Order> orders) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ORDERS_FILE))) {
            for (Order o : orders) {
                pw.println(o.toFileString());
            }
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }
}
