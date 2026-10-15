/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AccountManagementPanel extends JPanel {

    private final String role;
    private final String thirdColumn;

    private JTable table;
    private DefaultTableModel model;

    private JTextField idField;
    private JTextField emailField;
    private JTextField nameField;
    private JTextField thirdField;
    private JPasswordField passwordField;

    private String selectedOldID = null;

    public AccountManagementPanel(String title, String thirdColumn, String role) {
        this.role = role;
        this.thirdColumn = thirdColumn;

        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel(title);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(heading, BorderLayout.NORTH);

        model = new DefaultTableModel(
            new String[]{"ID Number", "Email", "Full Name", thirdColumn, "Password"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));

        idField = new JTextField();
        emailField = new JTextField();
        nameField = new JTextField();
        thirdField = new JTextField();
        passwordField = new JPasswordField();

        form.add(new JLabel("ID Number:"));
        form.add(idField);

        form.add(new JLabel("Email:"));
        form.add(emailField);

        form.add(new JLabel("Full Name:"));
        form.add(nameField);

        form.add(new JLabel(thirdColumn + ":"));
        form.add(thirdField);

        form.add(new JLabel("Password:"));
        form.add(passwordField);

        JButton addButton = new JButton("ADD");
        JButton updateButton = new JButton("UPDATE");
        JButton deleteButton = new JButton("DELETE");
        JButton clearButton = new JButton("CLEAR");

        JPanel buttons = new JPanel(new FlowLayout());
        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(deleteButton);
        buttons.add(clearButton);

        JPanel bottom = new JPanel(new BorderLayout(5, 5));
        bottom.add(form, BorderLayout.CENTER);
        bottom.add(buttons, BorderLayout.SOUTH);
        add(bottom, BorderLayout.SOUTH);

        addButton.addActionListener(e -> addAccount());
        updateButton.addActionListener(e -> updateAccount());
        deleteButton.addActionListener(e -> deleteAccount());
        clearButton.addActionListener(e -> clearFields());

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRow();
            }
        });

        refreshTable();
    }

    private void refreshTable() {
        model.setRowCount(0);
        for (AccountStore.Account account : AccountStore.getAccounts(role)) {
            model.addRow(new Object[]{
                account.id,
                account.email == null ? "" : account.email,
                account.name,
                account.department,
                account.password
            });
        }
    }

    private AccountStore.Account readFields() {
        String id = idField.getText().trim();
        String email = emailField.getText().trim();
        String name = nameField.getText().trim();
        String third = thirdField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (id.isEmpty() || email.isEmpty() || name.isEmpty()
                || third.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return null;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address.");
            return null;
        }

        // Student accounts store the department in this field; cashier accounts store the username.
        return new AccountStore.Account(id, name, email, third, password, role);
    }

    private void addAccount() {
        AccountStore.Account account = readFields();
        if (account == null) return;

        if (AccountStore.addAccount(account)) {
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Account added successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "That ID Number already exists.");
        }
    }

    private void updateAccount() {
        if (selectedOldID == null) {
            JOptionPane.showMessageDialog(this, "Select an account from the table first.");
            return;
        }

        AccountStore.Account updated = readFields();
        if (updated == null) return;

        if (AccountStore.updateAccount(selectedOldID, updated)) {
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Account updated successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "That ID Number already exists.");
        }
    }

    private void deleteAccount() {
        if (selectedOldID == null) {
            JOptionPane.showMessageDialog(this, "Select an account from the table first.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(
            this, "Delete account " + selectedOldID + "?",
            "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION
                && AccountStore.deleteAccount(selectedOldID)) {
            refreshTable();
            clearFields();
            JOptionPane.showMessageDialog(this, "Account deleted successfully.");
        }
    }

    private void loadSelectedRow() {
        int row = table.getSelectedRow();
        if (row < 0) return;

        selectedOldID = model.getValueAt(row, 0).toString();
        idField.setText(model.getValueAt(row, 0).toString());
        emailField.setText(model.getValueAt(row, 1) == null ? "" : model.getValueAt(row, 1).toString());
        nameField.setText(model.getValueAt(row, 2).toString());
        thirdField.setText(model.getValueAt(row, 3) == null ? "" : model.getValueAt(row, 3).toString());
        passwordField.setText(model.getValueAt(row, 4) == null ? "" : model.getValueAt(row, 4).toString());
    }

    private void clearFields() {
        table.clearSelection();
        selectedOldID = null;
        idField.setText("");
        emailField.setText("");
        nameField.setText("");
        thirdField.setText("");
        passwordField.setText("");
    }
}
