import java.io.Serializable;

public class ElectricityBill implements Serializable {

    private String consumerNumber;
    private String customerName;
    private String meterNumber;
    private String billingMonth;
    private double previousReading;
    private double currentReading;
    private double unitsConsumed;
    private double billAmount;
    private String dueDate;
    private String paymentStatus;

    public ElectricityBill(
            String consumerNumber,
            String customerName,
            String meterNumber,
            String billingMonth,
            double previousReading,
            double currentReading,
            String dueDate,
            String paymentStatus) {

        this.consumerNumber = consumerNumber;
        this.customerName = customerName;
        this.meterNumber = meterNumber;
        this.billingMonth = billingMonth;
        this.previousReading = previousReading;
        this.currentReading = currentReading;
        this.dueDate = dueDate;
        this.paymentStatus = paymentStatus;

        // Calculate units
        this.unitsConsumed = currentReading - previousReading;

        // Calculate electricity bill
        this.billAmount = calculateBill(unitsConsumed);
    }

    // Electricity bill calculation
    private double calculateBill(double units) {

        double amount = 0;

        if (units <= 100) {

            amount = units * 3;

        } else if (units <= 200) {

            amount = (100 * 3) +
                     ((units - 100) * 5);

        } else if (units <= 300) {

            amount = (100 * 3) +
                     (100 * 5) +
                     ((units - 200) * 7);

        } else {

            amount = (100 * 3) +
                     (100 * 5) +
                     (100 * 7) +
                     ((units - 300) * 9);
        }

        return amount;
    }

    public String getConsumerNumber() {
        return consumerNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public String getBillingMonth() {
        return billingMonth;
    }

    public double getPreviousReading() {
        return previousReading;
    }

    public double getCurrentReading() {
        return currentReading;
    }

    public double getUnitsConsumed() {
        return unitsConsumed;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(String status) {
    paymentStatus = status;
}

    @Override
    public String toString() {

        return "Consumer Number: " + consumerNumber +
                "\nCustomer Name: " + customerName +
                "\nMeter Number: " + meterNumber +
                "\nBilling Month: " + billingMonth +
                "\nPrevious Reading: " + previousReading +
                "\nCurrent Reading: " + currentReading +
                "\nUnits Consumed: " + unitsConsumed +
                "\nBill Amount: ₹" + billAmount +
                "\nDue Date: " + dueDate +
                "\nPayment Status: " + paymentStatus;
    }
}