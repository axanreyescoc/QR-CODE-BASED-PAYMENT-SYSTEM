/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Admin;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AccountStore {

    private static final String FILE_PATH =
        System.getProperty("user.home") + File.separator + "payment_accounts.dat";

    private static List<Account> accounts = loadAccounts();

    public static class Account implements Serializable {
        private static final long serialVersionUID = 1L;

        public String id;
        public String name;
        public String email;
        public String department;
        public String password;
        public String role;

        // New constructor including email.
        public Account(String id, String name, String email, String department,
                       String password, String role) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.department = department;
            this.password = password;
            this.role = role;
        }

        // Keeps compatibility with other existing frames that use the old constructor.
       
    }
public static synchronized void changeAccountRole(
        String id, String newRole) {

    Account account = findAccount(id);

    if (account != null) {
        account.role = newRole;
        saveList(accounts);
    }
}

public static synchronized void ensureAdminAccount() {
    Account admin = findAccount("1234");

    if (admin == null) {
        accounts.add(new Account(
            "1234", "Admin", "",
            "admin", "1234", "ADMIN"
        ));
    } else {
        admin.name = "Admin";
        admin.email = "";
        admin.department = "admin";
        admin.password = "1234";
        admin.role = "ADMIN";
    }

    saveList(accounts);
}

    private static List<Account> loadAccounts() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
                List<Account> loaded = (List<Account>) in.readObject();
                // Old saved accounts will have email == null; the table displays these as blank.
                return loaded;
            } catch (IOException | ClassNotFoundException e) {
                throw new IllegalStateException("Unable to load saved accounts.", e);
            }
        }

        List<Account> list = new ArrayList<>();
        list.add(new Account("02-2526-017386", "REYES, AXYL A.", "", "INFORMATION TECHNOLOGY", "1234", "STUDENT"));
        list.add(new Account("02-2526-017385", "OMPAD, JOHN MELVIC", "", "INFORMATION TECHNOLOGY", "1234", "STUDENT"));
        list.add(new Account("02-2526-017387", "COMING, MILDRED", "", "INFORMATION TECHNOLOGY", "1234", "STUDENT"));
        list.add(new Account("02-2526-017388", "CABALLERO, DOROTHY", "", "INFORMATION TECHNOLOGY", "1234", "STUDENT"));
        list.add(new Account("02-2526-017389", "SURPOSA, ALLYIA", "", "INFORMATION TECHNOLOGY", "1234", "STUDENT"));
        list.add(new Account("CSHR001", "Cashier 1", "", "cashier1", "1234", "CASHIER"));
        list.add(new Account("CSHR002", "Cashier 2", "", "cashier2", "1234", "CASHIER"));

        // Preserves the old Front Frame login behavior.
        list.add(new Account("1234", "Admin", "", "admin", "1234", "ADMIN"));

        saveList(list);
        return list;
    }

    public static synchronized List<Account> getAccounts(String role) {
        List<Account> result = new ArrayList<>();
        for (Account account : accounts) {
            if (account.role.equals(role)) result.add(account);
        }
        return result;
    }

    public static synchronized Account login(String id, String password) {
        for (Account account : accounts) {
            if (account.id.equals(id) && account.password.equals(password)) return account;
        }
        return null;
    }

    public static synchronized boolean addAccount(Account account) {
        if (findAccount(account.id) != null) return false;
        accounts.add(account);
        saveList(accounts);
        return true;
    }

    public static synchronized boolean updateAccount(String oldID, Account updated) {
        Account existing = findAccount(oldID);
        if (existing == null) return false;

        Account duplicate = findAccount(updated.id);
        if (duplicate != null && duplicate != existing) return false;

        existing.id = updated.id;
        existing.name = updated.name;
        existing.email = updated.email;
        existing.department = updated.department;
        existing.password = updated.password;
        existing.role = updated.role;

        saveList(accounts);
        return true;
    }

    public static synchronized boolean deleteAccount(String id) {
        Account existing = findAccount(id);
        if (existing == null) return false;
        accounts.remove(existing);
        saveList(accounts);
        return true;
    }

    private static Account findAccount(String id) {
        for (Account account : accounts) {
            if (account.id.equalsIgnoreCase(id)) return account;
        }
        return null;
    }

    private static void saveList(List<Account> list) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            out.writeObject(list);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to save accounts.", e);
        }
    }
}
