// This class stores information about a supplier.
public class Supplier {
    private String supplierId;  // Unique ID for the supplier
    private String name;        // Supplier name

    // Constructor to set the supplier's ID and name.
    public Supplier(String supplierId, String name) {
        this.supplierId = supplierId;
        this.name = name;
    }

    // Returns the supplier ID.
    public String getSupplierId() {
        return supplierId;
    }

    // Returns the supplier name.
    public String getName() {
        return name;
    }

    // Converts the supplier information to a simple string for saving in a file.
    public String toFileString() {
        return supplierId + "," + name;
    }

    // Creates a Supplier object from a string read from a file.
    public static Supplier fromFileString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 2) {
            return new Supplier(parts[0], parts[1]);
        }
        return null;  // If the line is not in the right format, return null.
    }

    // Returns a string representation of the supplier.
    @Override
    public String toString() {
        return "Supplier ID: " + supplierId + ", Name: " + name;
    }
}
