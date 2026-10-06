import java.io.*;
import java.util.ArrayList;

public class BillManager {

    private static final String FILE_NAME = "bills.dat";

    private ArrayList<ElectricityBill> bills;

    public BillManager() {
        bills = loadBills();
    }

    // Add a new electricity bill
    public void addBill(ElectricityBill bill) {
        bills.add(bill);
        saveBills();
    }

    // Get all bills
    public ArrayList<ElectricityBill> getBills() {
        return bills;
    }

    // Save bills to file
    public void saveBills() {

        try {

            ObjectOutputStream output =
                    new ObjectOutputStream(
                            new FileOutputStream(FILE_NAME)
                    );

            output.writeObject(bills);
            output.close();

        } catch (IOException e) {

            System.out.println("Error saving bills.");

        }
    }

    // Load bills from file
    @SuppressWarnings("unchecked")
    private ArrayList<ElectricityBill> loadBills() {

        try {

            ObjectInputStream input =
                    new ObjectInputStream(
                            new FileInputStream(FILE_NAME)
                    );

            ArrayList<ElectricityBill> loadedBills =
                    (ArrayList<ElectricityBill>) input.readObject();

            input.close();

            return loadedBills;

        } catch (Exception e) {

            return new ArrayList<>();
        }
    }
}