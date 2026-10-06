import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main {

    static BillManager manager = new BillManager();

    public static void main(String[] args) {

        JFrame f = new JFrame("Electricity Bill Management");
        f.setSize(600, 500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);

        JLabel title = new JLabel(
                "ELECTRICITY BILL MANAGEMENT SYSTEM",
                SwingConstants.CENTER);

        title.setFont(new Font("Arial", Font.BOLD, 20));

        JButton add = new JButton("ADD BILL");
        JButton view = new JButton("VIEW BILLS");
        JButton search = new JButton("SEARCH BILL");
        JButton delete = new JButton("DELETE BILL");
        JButton status = new JButton("CHANGE PAYMENT STATUS");

        JPanel p = new JPanel(new GridLayout(5, 1, 15, 15));

        p.add(add);
        p.add(view);
        p.add(search);
        p.add(delete);
        p.add(status);

        f.add(title, BorderLayout.NORTH);
        f.add(p, BorderLayout.CENTER);

        add.addActionListener(e -> addBill());
        view.addActionListener(e -> viewBills());
        search.addActionListener(e -> searchBill());
        delete.addActionListener(e -> deleteBill());
        status.addActionListener(e -> changeStatus());

        f.setVisible(true);
    }

    static void addBill() {

        JTextField c = new JTextField();
        JTextField n = new JTextField();
        JTextField m = new JTextField();
        JTextField month = new JTextField();
        JTextField prev = new JTextField();
        JTextField curr = new JTextField();
        JTextField due = new JTextField();

        String[] fields = {
            "Consumer Number", "Customer Name", "Meter Number",
            "Billing Month", "Previous Reading",
            "Current Reading", "Due Date"
        };

        JTextField[] input = {
            c, n, m, month, prev, curr, due
        };

        JPanel p = new JPanel(new GridLayout(7, 2));

        for (int i = 0; i < fields.length; i++) {
            p.add(new JLabel(fields[i]));
            p.add(input[i]);
        }

        int result = JOptionPane.showConfirmDialog(
                null, p, "Add Electricity Bill",
                JOptionPane.OK_CANCEL_OPTION);

        if (result != JOptionPane.OK_OPTION)
            return;

        try {

            double previous = Double.parseDouble(prev.getText());
            double current = Double.parseDouble(curr.getText());

            if (current < previous) {
                JOptionPane.showMessageDialog(
                        null, "Invalid meter reading");
                return;
            }

            ElectricityBill bill = new ElectricityBill(
                    c.getText(),
                    n.getText(),
                    m.getText(),
                    month.getText(),
                    previous,
                    current,
                    due.getText(),
                    "Pending");

            manager.addBill(bill);

            JOptionPane.showMessageDialog(
                    null,
                    "Bill Saved!\nUnits: " +
                    bill.getUnitsConsumed() +
                    "\nAmount: ₹" +
                    bill.getBillAmount() +
                    "\nStatus: Pending");

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null, "Enter valid readings");
        }
    }

    static void viewBills() {

        ArrayList<ElectricityBill> bills =
                manager.getBills();

        if (bills.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null, "No bills found");
            return;
        }

        String text = "";

        for (ElectricityBill b : bills)
            text += b + "\n\n------------------\n";

        JTextArea area = new JTextArea(text);
        area.setEditable(false);

        JOptionPane.showMessageDialog(
                null,
                new JScrollPane(area),
                "Bill History",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static void searchBill() {

        String no = JOptionPane.showInputDialog(
                "Enter Consumer Number");

        if (no == null)
            return;

        for (ElectricityBill b : manager.getBills()) {

            if (b.getConsumerNumber()
                    .equalsIgnoreCase(no)) {

                JOptionPane.showMessageDialog(
                        null,
                        b,
                        "Bill Found",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(
                null, "Bill not found");
    }

    static void deleteBill() {

        String no = JOptionPane.showInputDialog(
                "Enter Consumer Number to delete");

        if (no == null)
            return;

        for (ElectricityBill b : manager.getBills()) {

            if (b.getConsumerNumber()
                    .equalsIgnoreCase(no)) {

                int choice = JOptionPane.showConfirmDialog(
                        null,
                        "Delete this bill?\n\n" + b,
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {

                    manager.getBills().remove(b);
                    manager.saveBills();

                    JOptionPane.showMessageDialog(
                            null,
                            "Bill deleted successfully!");
                }

                return;
            }
        }

        JOptionPane.showMessageDialog(
                null, "Bill not found");
    }

    static void changeStatus() {

        String no = JOptionPane.showInputDialog(
                null,
                "Enter Consumer Number:");

        if (no == null || no.trim().isEmpty())
            return;

        for (ElectricityBill b : manager.getBills()) {

            if (b.getConsumerNumber()
                    .equalsIgnoreCase(no.trim())) {

                String[] options = {"Paid", "Pending"};

                String newStatus = (String)
                        JOptionPane.showInputDialog(
                                null,
                                "Current Status: "
                                + b.getPaymentStatus()
                                + "\n\nSelect New Status:",
                                "Change Payment Status",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                options,
                                b.getPaymentStatus());

                if (newStatus != null) {

                    b.setPaymentStatus(newStatus);

                    manager.saveBills();

                    JOptionPane.showMessageDialog(
                            null,
                            "Payment Status Updated!\n"
                            + "Consumer: " + no
                            + "\nStatus: " + newStatus);
                }

                return;
            }
        }

        JOptionPane.showMessageDialog(
                null,
                "Consumer Number not found!");
    }
}