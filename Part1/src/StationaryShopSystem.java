import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

// This class contains the main business logic for the "company".
// Basic functions are adding, deleting, and displaying suppliers and orders,
// Also made sense to do simple input validation.
public class StationaryShopSystem {
    private ArrayList<Supplier> suppliers;
    private ArrayList<Order> orders;

    private static final String CUTOFF_DATE = "31/12/1999";

    public StationaryShopSystem() {
        suppliers = DataManager.loadSuppliers();
        orders = DataManager.loadOrders();

        // Default data added regardless of file content
        addOrUpdateDefaultData();
    }

    // This always adds or updates the default suppliers/orders.
    private void addOrUpdateDefaultData() {
        updateOrAddSupplier("A050", "WHSmiths");
        updateOrAddSupplier("B501", "Ryman's");
        updateOrAddSupplier("C3690", "Newmans Stationary Ltd");
        updateOrAddSupplier("D335", "Sussiest Bussy Mario's");

        addOrderIfMissing("1000", "A050", "14/12/2024", "Pens", 20);
        addOrderIfMissing("O250", "D335", "25/03/2025", "Pencils", 15);
        addOrderIfMissing("O420", "D335", "08/01/2025", "Notepads", 34);
        addOrderIfMissing("1570", "B501", "31/12/2024", "Rulers", 50);

        System.out.println("Default suppliers and orders have been added or updated.");
    }

    // Updates supplier if ID exists, otherwise adds it.
    private void updateOrAddSupplier(String supplierId, String name) {
        for (int i = 0; i < suppliers.size(); i++) {
            if (suppliers.get(i).getSupplierId().equalsIgnoreCase(supplierId)) {
                suppliers.set(i, new Supplier(supplierId, name));
                return;
            }
        }
        suppliers.add(new Supplier(supplierId, name));  // If supplier not found, add it
    }

    // Adds an order if it doesn't already exist in the list
    private void addOrderIfMissing(String orderId, String supplierId, String date, String item, int qty) {
        for (Order o : orders) {
            if (o.getOrderId().equalsIgnoreCase(orderId)) {
                return;  // If order already exists, do nothing
            }
        }
        orders.add(new Order(orderId, supplierId, date, item, qty));  // Add new order if missing
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        // Loop until user chooses to exit (choice 6)
        while (choice != 6) {
            showMenu();
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(scanner.nextLine());  // Get choice from user
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");  // Handle invalid input
                continue;  // Ask again if invalid input
            }

            // Perform actions based on user's choice
            switch (choice) {
                case 1:
                    addNewSupplier(scanner);
                    break;
                case 2:
                    addNewOrder(scanner);
                    break;
                case 3:
                    displaySuppliers();
                    break;
                case 4:
                    displayOrders();
                    break;
                case 5:
                    deleteSupplier(scanner);
                    break;
                case 6:
                    System.out.println("Exiting system...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        // Save data when the program exits
        DataManager.saveSuppliers(suppliers);
        DataManager.saveOrders(orders);
        scanner.close();
    }

    private void showMenu() {
        System.out.println("\n--- Stationary Shop Menu ---");
        System.out.println("1. Add New Supplier");
        System.out.println("2. Add New Order");
        System.out.println("3. Display Suppliers");
        System.out.println("4. Display Orders");
        System.out.println("5. Delete Supplier");
        System.out.println("6. Exit");
    }

    private void addNewSupplier(Scanner scanner) {
        // Ask the user for supplier details and add them
        String supplierId = getNonEmptyString(scanner, "Enter Supplier ID: ");
        String name = getNonEmptyString(scanner, "Enter Supplier Name: ");
        suppliers.add(new Supplier(supplierId, name));
        System.out.println("Supplier added!");
    }

    private void addNewOrder(Scanner scanner) {
        System.out.println("Available suppliers:");
        displaySuppliers();  // Display all suppliers for the user to choose from

        // Get order details from user
        String orderId = getNonEmptyString(scanner, "Enter Order ID: ");
        String supplierId;

        while (true) {
            supplierId = getNonEmptyString(scanner,
                    "Enter Supplier ID for this order (or type 'exit' to cancel): ");
            if (supplierId.equalsIgnoreCase("exit")) {
                System.out.println("Order creation cancelled.");
                return;  // Stop the order creation process if user types 'exit'
            }
            boolean exists = false;
            for (Supplier s : suppliers) {
                if (s.getSupplierId().equals(supplierId)) {
                    exists = true;
                    break;  // Stop checking once the supplier is found
                }
            }
            if (!exists) {
                System.out.println("Supplier not found. Please try again or type 'exit' to cancel.");
            } else {
                break;  // Continue if supplier is found
            }
        }

        String orderDate = getValidatedDate(scanner, "Enter Order Date (dd/mm/yyyy): ");
        String item = getNonEmptyString(scanner, "Enter Item (e.g., Pens, Paper, Rulers): ");
        int quantity = getPositiveInteger(scanner, "Enter Quantity: ");

        // Add the new order to the list
        orders.add(new Order(orderId, supplierId, orderDate, item, quantity));
        System.out.println("Order added!");
    }

    private void deleteSupplier(Scanner scanner) {
        System.out.println("Here are the current suppliers:");
        displaySuppliers();  // Show list of current suppliers

        // Ask for supplier ID to delete
        String supplierId = getNonEmptyString(scanner, "Enter Supplier ID to delete: ");

        Supplier supplierToDelete = null;
        for (Supplier s : suppliers) {
            if (s.getSupplierId().equals(supplierId)) {
                supplierToDelete = s;
                break;  // Stop once supplier is found
            }
        }

        if (supplierToDelete == null) {
            System.out.println("Supplier not found. Deletion cancelled.");
            return;  // Stop if supplier is not found
        }

        // Remove the supplier's orders as well
        ArrayList<Order> ordersToRemove = new ArrayList<>();
        for (Order o : orders) {
            if (o.getSupplierId().equals(supplierId)) {
                ordersToRemove.add(o);
            }
        }
        orders.removeAll(ordersToRemove);  // Remove orders linked to the supplier
        suppliers.remove(supplierToDelete);  // Remove the supplier
        System.out.println("Supplier and associated orders have been deleted.");
    }

    private void displaySuppliers() {
        System.out.println("\n--- List of Suppliers ---");
        if (suppliers.isEmpty()) {
            System.out.println("No suppliers available.");  // Display if no suppliers
        } else {
            for (Supplier s : suppliers) {
                System.out.println(s);  // Print each supplier
            }
        }
    }

    private void displayOrders() {
        System.out.println("\n--- List of Orders ---");
        if (orders.isEmpty()) {
            System.out.println("No orders available.");  // Display if no orders
        } else {
            for (Order o : orders) {
                System.out.println(o);  // Print each order
            }
        }
    }

    private String getNonEmptyString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);  // Show the prompt
            input = scanner.nextLine().trim();  // Get input and remove extra spaces
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());  // Keep asking until input is not empty
        return input;  // Return the valid input
    }

    private int getPositiveInteger(Scanner scanner, String prompt) {
        int number = -1;
        while (number <= 0) {
            System.out.print(prompt);  // Prompt for a positive number
            try {
                number = Integer.parseInt(scanner.nextLine());  // Try converting input to integer
                if (number <= 0) {
                    System.out.println("Please enter a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
        return number;  // Return valid positive integer
    }

    private String getValidatedDate(Scanner scanner, String prompt) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);  // Set strict date validation
        Date cutoffDate = null;

        try {
            cutoffDate = sdf.parse(CUTOFF_DATE);  // Parse cutoff date
        } catch (ParseException e) {
            System.out.println("Error with cutoff date format.");
        }

        // Ask for a valid date input
        while (true) {
            System.out.print(prompt);  // Prompt for a date
            String dateInput = scanner.nextLine().trim();
            try {
                Date enteredDate = sdf.parse(dateInput);
                if (!enteredDate.after(cutoffDate)) {
                    System.out.println("Invalid input, enter a date after " + CUTOFF_DATE + ".");
                    continue;  // Ask again if date is before cutoff
                }
                return dateInput;  // Return valid date
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use dd/mm/yyyy.");
            }
        }
    }
}
