 // This class represents an order in my "demo company".
public class Order {
    private String orderId;      // Unique order identifier
    private String supplierId;   // ID of the supplier for this order
    private String orderDate;    // Date when the order was placed
    private String item;         // What is being ordered (e.g., Pens, Paper)
    private int quantity;        // Number of items ordered

    // Constructor to set all the details of the order.
    public Order(String orderId, String supplierId, String orderDate, String item, int quantity) {
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.orderDate = orderDate;
        this.item = item;
        this.quantity = quantity;
    }

    // Returns the order ID.
    public String getOrderId() {
        return orderId;
    }

    // Returns the supplier ID.
    public String getSupplierId() {
        return supplierId;
    }

    // Returns the order date.
    public String getOrderDate() {
        return orderDate;
    }

    // Returns the item ordered.
    public String getItem() {
        return item;
    }

    // Returns the quantity ordered.
    public int getQuantity() {
        return quantity;
    }

    // Converts the order information to a string for saving in a file.
    public String toFileString() {
        return orderId + "," + supplierId + "," + orderDate + "," + item + "," + quantity;
    }

    // Creates an Order object from a string read from a file.
    public static Order fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 5) {
            try {
                int qty = Integer.parseInt(parts[4]);
                return new Order(parts[0], parts[1], parts[2], parts[3], qty);
            } catch (NumberFormatException e) {
                return null;  // If the quantity is not a number, return null.
            }
        }
        return null;  // If the line is not in the right format, return null.
    }

    // Returns a string representation of the order.
    @Override
    public String toString() {
        return "Order ID: " + orderId +
                ", Supplier ID: " + supplierId +
                ", Order Date: " + orderDate +
                ", Item: " + item +
                ", Quantity: " + quantity;
    }
}
