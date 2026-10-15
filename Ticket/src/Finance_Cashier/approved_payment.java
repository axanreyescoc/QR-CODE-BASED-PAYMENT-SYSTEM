/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Finance_Cashier;

/**
 *
 * @author hp
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.DecimalFormat;

public class approved_payment extends JPanel {

    private JTable jTable1;
    private JComboBox<String> jComboBox1;
    private JComboBox<String> dateComboBox;
    private JLabel countLabel;
    private JLabel totalLabel;

    public approved_payment() {
        initComponents();
        loadApprovedPayments();
    }

    private void initComponents() {
    setLayout(new BorderLayout(5, 5));

    JLabel title = new JLabel(
        "APPROVED PAYMENT", SwingConstants.CENTER
    );
    title.setFont(new Font("Segoe UI", Font.BOLD, 18));

    jComboBox1 = new JComboBox<>(
        new String[]{"All", "APPROVED"}
    );

    dateComboBox = new JComboBox<>();
dateComboBox.addItem("All Dates");

// Add dates from the database
loadAvailableDates();

JButton filterButton = new JButton("FILTER");

JPanel filterPanel = new JPanel(
    new FlowLayout(FlowLayout.LEFT)
);

filterPanel.add(new JLabel("Date:"));
filterPanel.add(dateComboBox);
filterPanel.add(filterButton);

filterPanel.add(new JLabel("Status:"));
filterPanel.add(jComboBox1);

    countLabel = new JLabel("Total Payments: 0");
    totalLabel = new JLabel("Total Amount: ₱0.00");

    JPanel summaryPanel = new JPanel(
        new FlowLayout(FlowLayout.LEFT, 20, 10)
    );
    summaryPanel.add(countLabel);
    summaryPanel.add(totalLabel);

    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.add(title, BorderLayout.NORTH);
    topPanel.add(filterPanel, BorderLayout.CENTER);
    topPanel.add(summaryPanel, BorderLayout.SOUTH);

    jTable1 = new JTable(new DefaultTableModel(
        new Object[][]{},
        new String[]{
    "ID NUMBER",  "EMAIL", "NAME", "COURSE",
    "CONTACT NUMBER", "PAYMENT FOR",
    "AMOUNT", "CASH", "CHANGE",
    "REFERENCE NO.", "STATUS", "PAYMENT DATE"
}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    });

    // Add components only after creating them
    add(topPanel, BorderLayout.NORTH);
    add(new JScrollPane(jTable1), BorderLayout.CENTER);
    JScrollPane scrollPane = new JScrollPane(jTable1);
scrollPane.setPreferredSize(new Dimension(1000, 500));

add(scrollPane, BorderLayout.CENTER);
    filterButton.addActionListener(
        e -> loadApprovedPayments()
    );

    

    jComboBox1.addActionListener(
        e -> loadApprovedPayments()
    );
}

    private void loadAvailableDates() {
    String sql = "SELECT DISTINCT DATE(payment_date) AS pay_date "
               + "FROM approved ORDER BY pay_date DESC";

    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery()
    ) {
        while (rs.next()) {
            String date = rs.getString("pay_date");

            if (date != null) {
                dateComboBox.addItem(date);
            }
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(
            this,
            "Error loading dates: " + ex.getMessage()
        );
    }
}
    
    public static void addPayment(
             
        String id,
        String email,
        String name,
        String course,
        String contact,
        String payment,
        String amount,
        String referenceNo,
        String cash,
        String change,
        String receiptNo) {

    String sql = "INSERT INTO approved "
            + "(id_number,email, name, course, contact_number, "
            + "payment_for, amount, cash, change_amount, "
            + "status, reference_no, Receipt_no) "
            + "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql)
    ) {
       pst.setString(1, id);
        pst.setString(2, email);
        pst.setString(3, name);
        pst.setString(4, course);
        pst.setString(5, contact);
        pst.setString(6, payment);
        pst.setDouble(7, Double.parseDouble(amount));
        pst.setDouble(8, Double.parseDouble(cash));
        pst.setDouble(9, Double.parseDouble(change));
        pst.setString(10, "APPROVED");
        pst.setString(11, referenceNo);
        pst.setString(12, receiptNo);

        pst.executeUpdate();

    } catch (SQLException | NumberFormatException ex) {
        JOptionPane.showMessageDialog(
                null,
                "Failed to save payment:\n" + ex.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE
        );
    }
}

    public void loadApprovedPayments() {
        String date = (String) dateComboBox.getSelectedItem();

if (date == null || date.equals("All Dates")) {
    date = "";
}
        String status = (String) jComboBox1.getSelectedItem();

        if (!date.isEmpty()
                && !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Use date format YYYY-MM-DD (example: 2026-10-10).");
            return;
        }

       StringBuilder sql = new StringBuilder(
    "SELECT id_number, email, name, course, contact_number, "
  + "payment_for, amount, cash, change_amount, "
  + "reference_no, status, payment_date "
  + "FROM approved WHERE 1=1"
);

        if (!date.isEmpty()) {
            sql.append(" AND payment_date >= ? "
                     + "AND payment_date < DATE_ADD(?, INTERVAL 1 DAY)");
        }

        if (!"All".equals(status)) {
            sql.append(" AND status = ?");
        }

        sql.append(" ORDER BY payment_date DESC");

        DefaultTableModel model =
                (DefaultTableModel) jTable1.getModel();

        model.setRowCount(0);

        int count = 0;
        double total = 0;

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pst =
                    conn.prepareStatement(sql.toString())
        ) {
            int index = 1;

            if (!date.isEmpty()) {
                pst.setString(index++, date);
                pst.setString(index++, date);
            }

            if (!"All".equals(status)) {
                pst.setString(index, status);
            }

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    double amount = rs.getDouble("amount");

                    model.addRow(new Object[]{
     rs.getString("id_number"),
    rs.getString("email"),
    rs.getString("name"),
    rs.getString("course"),
    rs.getString("contact_number"),
    rs.getString("payment_for"),
    rs.getDouble("amount"),
    rs.getDouble("cash"),
    rs.getDouble("change_amount"),
    rs.getString("reference_no"),
    rs.getString("status"),
    rs.getTimestamp("payment_date")
});

                    count++;
                    total += amount;
                }
            }

            DecimalFormat df = new DecimalFormat("#,##0.00");

            countLabel.setText("Total Payments: " + count);
            totalLabel.setText("Total Amount: ₱" + df.format(total));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading payments:\n" + ex.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}