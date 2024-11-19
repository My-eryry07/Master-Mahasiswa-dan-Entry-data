/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mastermahasiswapbo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author User
 */
public class entrybarang extends javax.swing.JFrame {
private int jumlah = 0;
private Connection con;
private PreparedStatement stat;
private ResultSet rs;   
koneksii dbKoneksii = new koneksii();
    /**
     * Creates new form entrybarang
     */
    public entrybarang() {
        initComponents();
        Tampilentrydata();
         dbKoneksii.connect();
    }
private void Tampilentrydata() {
    Connection con = dbKoneksii.connect();  
    String query = "SELECT kode_barang, nama_barang, harga_beli,harga_jual,quantitiy FROM entry_barang";

    try {
        PreparedStatement ps = con.prepareStatement(query);  
        ResultSet rs = ps.executeQuery(); 

        // Membuat model tabel untuk menyusun kolom
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Harga Beli");
        model.addColumn("Harga Jual");
        model.addColumn("Quantitiy");

      
        boolean dataFound = false;
        while (rs.next()) {
            Object[] row = new Object[5];
            row[0] = rs.getString("kode_barang");
            row[1] = rs.getString("nama_barang");
            row[2] = rs.getString("harga_beli");
            row[3] = rs.getObject("harga_jual");
             row[4] = rs.getObject("quantitiy");

            model.addRow(row); 
            dataFound = true;
        }

        // Cek jika data ditemukan, jika tidak beri peringatan
        if (!dataFound) {
            JOptionPane.showMessageDialog(this, "Tidak ada data yang ditemukan.");
        }

        tblentrybarang.setModel(model); // Mengatur model tabel baru untuk TableStok

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menampilkan: " + e.getMessage());
    } finally {
        try {
            if (con != null) {
                con.close(); // Menutup koneksi database
            }
        } catch (SQLException e) {
            System.out.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }
}
private void insertData() {
    Connection con = dbKoneksii.connect();
    String query = "INSERT INTO entry_barang (kode_barang, nama_barang, harga_beli,harga_jual,quantitiy) VALUES (?, ?, ?, ?,?)";

    try {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, txtKodebarang.getText());
        ps.setString(2, txtNamabarang.getText());
        ps.setDouble(3, Double.parseDouble(txtHargabeli.getText()));
         ps.setDouble(4, Double.parseDouble(txtHargajual.getText()));
        ps.setInt(5, Integer.parseInt(txtQuantitiy.getText()));

        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
        Tampilentrydata(); 
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menambahkan data: " + e.getMessage());
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }
}
private void updateData() {
    Connection con = dbKoneksii.connect();  // Consistent connection object name
    String query = "UPDATE entry_barang SET nama_barang = ?, harga_beli = ?, harga_jual = ?, quantitiy = ? WHERE kode_barang = ?";  // Ensure you're updating the correct table and fields

    try {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, txtNamabarang.getText());
        ps.setDouble(2, Double.parseDouble(txtHargabeli.getText()));
        ps.setDouble(3, Double.parseDouble(txtHargajual.getText()));
        ps.setInt(4, Integer.parseInt(txtQuantitiy.getText()));
        ps.setString(5, txtKodebarang.getText());

        ps.executeUpdate();
        JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
        Tampilentrydata();  // Refresh tabel
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memperbarui data: " + e.getMessage());
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }
}

private void deleteData() {
    Connection con = dbKoneksii.connect();  // Consistent connection object name
    String query = "DELETE FROM entry_barang WHERE kode_barang = ?";

    try {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, txtKodebarang.getText());

        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
            Tampilentrydata(); 
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Gagal menutup koneksi: " + e.getMessage());
        }
    }
}

private void searchData() {
    Connection con = dbKoneksii.connect();  // Consistent connection object name
    String query = "SELECT * FROM entry_barang WHERE nama_barang = ?";

    try {
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, txtCari.getText());
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            txtKodebarang.setText(rs.getString("kode_barang"));
            txtNamabarang.setText(rs.getString("nama_barang"));
            txtHargabeli.setText(rs.getString("harga_beli"));
            txtHargajual.setText(rs.getString("harga_jual"));
            txtQuantitiy.setText(rs.getString("quantitiy"));
            JOptionPane.showMessageDialog(this, "Data ditemukan.");
        } else {
            JOptionPane.showMessageDialog(this, "Data tidak ditemukan.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage());
    } finally {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Gagal menutup koneksi: " + e.getMessage());
        }
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

        txtKodebarang = new javax.swing.JTextField();
        txtNamabarang = new javax.swing.JTextField();
        txtHargabeli = new javax.swing.JTextField();
        txtHargajual = new javax.swing.JTextField();
        txtQuantitiy = new javax.swing.JTextField();
        btnInsert = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblentrybarang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(455, 497));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtKodebarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKodebarangActionPerformed(evt);
            }
        });
        getContentPane().add(txtKodebarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 58, 169, -1));

        txtNamabarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamabarangActionPerformed(evt);
            }
        });
        getContentPane().add(txtNamabarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 98, 169, -1));

        txtHargabeli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargabeliActionPerformed(evt);
            }
        });
        getContentPane().add(txtHargabeli, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 132, 169, -1));

        txtHargajual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargajualActionPerformed(evt);
            }
        });
        getContentPane().add(txtHargajual, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 172, 169, -1));
        getContentPane().add(txtQuantitiy, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 213, 169, -1));

        btnInsert.setText("INSERT");
        btnInsert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertActionPerformed(evt);
            }
        });
        getContentPane().add(btnInsert, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 245, -1, -1));

        btnUpdate.setText("UPDATE");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        getContentPane().add(btnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 245, -1, -1));

        btnSearch.setText("SEARCH");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        getContentPane().add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 280, -1, -1));
        getContentPane().add(txtCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 280, 158, -1));

        btnDelete.setText("DELETE");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        getContentPane().add(btnDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 245, -1, -1));

        tblentrybarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Kode barang", "Nama Barang", "Harga Beli", "Harga Jual", "Quantitiy"
            }
        ));
        jScrollPane1.setViewportView(tblentrybarang);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 338, -1, 157));

        jLabel1.setText("Kode Barang");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 64, -1, -1));

        jLabel2.setText("Nama Barang");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 98, 75, -1));

        jLabel3.setText("Harga Beli");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 132, -1, -1));

        jLabel4.setText("Harga Jual");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 179, -1, -1));

        jLabel5.setText("Quantity");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 216, 56, -1));

        jLabel6.setText("ENTRY DATA BARANG");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(187, 14, 139, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtKodebarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKodebarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKodebarangActionPerformed

    private void txtNamabarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamabarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamabarangActionPerformed

    private void txtHargabeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargabeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargabeliActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
 searchData();        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
    deleteData();    // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtHargajualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargajualActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargajualActionPerformed

    private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
insertData();        // TODO add your handling code here:
    }//GEN-LAST:event_btnInsertActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
updateData();        // TODO add your handling code here:
    }//GEN-LAST:event_btnUpdateActionPerformed

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
            java.util.logging.Logger.getLogger(entrybarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(entrybarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(entrybarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(entrybarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new entrybarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblentrybarang;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHargabeli;
    private javax.swing.JTextField txtHargajual;
    private javax.swing.JTextField txtKodebarang;
    private javax.swing.JTextField txtNamabarang;
    private javax.swing.JTextField txtQuantitiy;
    // End of variables declaration//GEN-END:variables
}
