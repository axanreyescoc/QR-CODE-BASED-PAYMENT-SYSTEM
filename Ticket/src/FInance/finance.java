/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package FInance;

import MENUframe.Menu;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javax.swing.ImageIcon;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
/**
 *
 * @author hp
 */
public class finance extends javax.swing.JFrame {

    /**
     * Creates new form finance
     */
    
     
   public finance(
         String id,
        String name,
        String department,
        String contact,
        String payment,
        String amount,
        String email) {

    initComponents();
    centerContent();
    userID = id;
    userName = name;
    userDepartment = department;
    userContact = contact;
    userPayment = payment;
    userAmount = amount;
    userEmail = email;
    
    referenceNumber = "REF-" + System.currentTimeMillis();
    
    try {
    ticketNumber = generateDailyTicketNumber();
} catch (IOException | NumberFormatException e) {
    JOptionPane.showMessageDialog(
        this,
        "Failed to generate ticket number: " + e.getMessage()
    );
    return;
}
    
    
    reflabel.setText("Reference No.: " + referenceNumber);
    txtticket.setText("Ticket No.: " + ticketNumber);
txtticket.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
   qrText = "PHINMA-CAGAYAN DE ORO COLLEGE\n"
        + "TICKET NO: " + ticketNumber + "\n"
        + "REFERENCE NO: " + referenceNumber + "\n"
        + "ID: " + userID + "\n"
        + "EMAIL: " + userEmail + "\n"
        + "NAME: " + userName + "\n"
        + "COURSE: " + userDepartment + "\n"
        + "CONTACT: " + userContact + "\n"
        + "PAYMENT: " + userPayment + "\n"
        + "AMOUNT: " + userAmount;

    generateQR();

    setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    setLocationRelativeTo(null);
}
   
   
   private String generateDailyTicketNumber() throws IOException {
    LocalDate today = LocalDate.now();

    Path counterFile = Paths.get(
        System.getProperty("user.home"),
        "daily_ticket_counter.txt"
    );

    int nextNumber = 1;

    if (Files.exists(counterFile)) {
        java.util.List<String> data =
                Files.readAllLines(counterFile);

        if (data.size() >= 2 && data.get(0).equals(today.toString())) {
            int lastNumber = Integer.parseInt(data.get(1));

            if (lastNumber < 1000) {
                nextNumber = lastNumber + 1;
            } else {
                throw new IOException(
                    "Naabot na ang limit nga 1000 tickets karong adlawa."
                );
            }
        }
    }

    Files.write(
        counterFile,
        java.util.Arrays.asList(
            today.toString(),
            String.valueOf(nextNumber)
        )
    );

    return String.format("TICKET-%03d", nextNumber);
}
   
   
   private void centerContent() {

    // Make the green panel fill the entire window
    getContentPane().removeAll();
    getContentPane().setLayout(new java.awt.BorderLayout());
    getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

    // Center the whole group
    jPanel1.removeAll();
    jPanel1.setLayout(new java.awt.GridBagLayout());

    JPanel centerPanel = new JPanel();
    centerPanel.setOpaque(false);
    centerPanel.setLayout(
        new javax.swing.BoxLayout(
            centerPanel,
            javax.swing.BoxLayout.Y_AXIS
        )
    );

    // Center each component
    jLabel2.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    school.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    jPanel2.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    btnDownload.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
    jButton1.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

    // Fixed size of the white QR card
    jPanel2.setPreferredSize(new java.awt.Dimension(356, 399));
    jPanel2.setMinimumSize(new java.awt.Dimension(356, 399));
    jPanel2.setMaximumSize(new java.awt.Dimension(356, 399));

    // Arrange components vertically
    centerPanel.add(jLabel2);
    centerPanel.add(Box.createVerticalStrut(5));
    centerPanel.add(school);
    centerPanel.add(Box.createVerticalStrut(18));
    centerPanel.add(jPanel2);
    centerPanel.add(Box.createVerticalStrut(18));
    centerPanel.add(btnDownload);
    centerPanel.add(Box.createVerticalStrut(10));
    centerPanel.add(jButton1);

    // Center the group horizontally and vertically
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;

    jPanel1.add(centerPanel, gbc);

    jPanel1.revalidate();
    jPanel1.repaint();
}
   
    private String userID;
private String userName;
private String userDepartment;
private String userContact;
private String userPayment;
private String userAmount;
private String qrText;
private String referenceNumber;
private String userEmail;
private String ticketNumber;
private void generateQR() {

    try {

        QRCodeWriter writer = new QRCodeWriter();

        BitMatrix matrix = writer.encode(
                qrText,
                BarcodeFormat.QR_CODE,
                250,
                250
        );

        BufferedImage image =
                MatrixToImageWriter.toBufferedImage(matrix);

        ticket1.setIcon(new ImageIcon(image));
        ticket1.setText("");

    } catch (Exception e) {

        JOptionPane.showMessageDialog(
                this,
                "QR Error: " + e.getMessage()
        );
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        ticket1 = new javax.swing.JLabel();
        dtime = new javax.swing.JLabel();
        reflabel = new javax.swing.JLabel();
        txtticket = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        school = new javax.swing.JLabel();
        btnDownload = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 153, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(504, 646));
        jPanel1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jPanel1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(500, 650));

        ticket1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        ticket1.setPreferredSize(new java.awt.Dimension(250, 250));
        ticket1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                ticket1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                ticket1AncestorMoved(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        dtime.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dtime.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                dtimeAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dtime, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ticket1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reflabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtticket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 51, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(txtticket, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ticket1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reflabel, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(dtime, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("QR CODE");

        school.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        school.setText("PHINMA-CAGAYAN DE ORO COLLEGE");
        school.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        school.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                schoolAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        btnDownload.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDownload.setText("Download");
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(177, 177, 177)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(school))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))))
                .addGap(72, 72, 72))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(school)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDownload, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jButton1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Menu a = new Menu(
    userID,
    userName,
    userDepartment,
    userEmail
);

    a.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ticket1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ticket1AncestorAdded
        // TODO add your handling code here:

    }//GEN-LAST:event_ticket1AncestorAdded

    private void schoolAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_schoolAncestorAdded
        // TODO add your handling code here:
        System.out.println("PHINMA-CAGAYAN DE ORO COLLEGE");
    }//GEN-LAST:event_schoolAncestorAdded

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed
        // TODO add your handling code here:
         try {

        String downloadsPath =
                System.getProperty("user.home")
                + File.separator
                + "Downloads";

        File folder = new File(downloadsPath);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        String fileName =
                "QR_" + userID + ".png";

        File file =
                new File(downloadsPath, fileName);

        // Hide download button
        btnDownload.setVisible(false);

        BufferedImage image = new BufferedImage(
                jPanel2.getWidth(),
                jPanel2.getHeight(),
                BufferedImage.TYPE_INT_RGB
        );

        java.awt.Graphics2D g2 =
                image.createGraphics();

        jPanel2.paint(g2);

        g2.dispose();

        ImageIO.write(
                image,
                "png",
                file
        );

        btnDownload.setVisible(true);

        JOptionPane.showMessageDialog(
                this,
                "QR downloaded successfully!\n\n"
                + "Saved to:\n"
                + file.getAbsolutePath()
        );

    } catch (IOException ex) {

        btnDownload.setVisible(true);

        JOptionPane.showMessageDialog(
                this,
                "Failed to download QR!\n"
                + ex.getMessage()
        );
    }
    }//GEN-LAST:event_btnDownloadActionPerformed

    private void dtimeAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_dtimeAncestorAdded
        // TODO add your handling code here:
         LocalDateTime now = LocalDateTime.now();

    DateTimeFormatter format =
            DateTimeFormatter.ofPattern("MM/dd/yyyy  hh:mm a");

    dtime.setText(now.format(format));
    }//GEN-LAST:event_dtimeAncestorAdded

    private void ticket1AncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_ticket1AncestorMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_ticket1AncestorMoved

    private void jPanel1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jPanel1AncestorAdded
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jPanel1AncestorAdded

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(finance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(finance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(finance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(finance.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               JOptionPane.showMessageDialog(
                null,
                "Please open the QR window through the Menu."
            );
        
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDownload;
    private javax.swing.JLabel dtime;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel reflabel;
    private javax.swing.JLabel school;
    private javax.swing.JLabel ticket1;
    private javax.swing.JLabel txtticket;
    // End of variables declaration//GEN-END:variables
}
