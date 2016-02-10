/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library_mgmt_ncstate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author hp
 */
public class BookCheckin extends javax.swing.JFrame {

    /**
     * Creates new form PublCheckIn
     */
    
    static final String jdbcURL 
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
    static Connection conn = null;
    static Statement stmt = null;
    static Statement stmt1 = null;
    static Statement stmt2 = null;
    static ResultSet rs = null;
    static String patron_id;
    
    public BookCheckin(String id) {
        initComponents();
        patron_id = id;
        connect1();
        load_checkinpubl();
    }
public static void connect1()
    {
                try {
            // Load the driver. This creates an instance of the driver
	    // and calls the registerDriver method to make Oracle Thin
	    // driver available to clients.
            Class.forName("oracle.jdbc.driver.OracleDriver");
           
	    String user = "msaikia"; 	// For example, "jsmith"
	    //String user = "ibobra";
            //String passwd = "200109140";	// Your 9 digit student ID number
	    String passwd = "200109120";
                 // Get a connection from the first driver in the
		// DriverManager list that recognizes the URL jdbcURL
		conn = DriverManager.getConnection(jdbcURL, user, passwd);
		// Create a statement object that will be sending your
		// SQL statements to the DBMS
		stmt = conn.createStatement();
                stmt1 = conn.createStatement();
                stmt2 = conn.createStatement();
               // System.out.println("hi6");
                }
       
     catch(Throwable oops) {
            oops.printStackTrace();
        }
         finally {
                close(rs);
                //close(stmt);
                //close(conn);
            }
    }
    
    static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); }
            catch(Throwable oops) 
            {
                oops.printStackTrace();
            }
        }
    }

    static void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable oops) {oops.printStackTrace();}
        }
    }

    static void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch(Throwable oops) {oops.printStackTrace();}
        }
    }
    
    public void load_checkinpubl(){
        DefaultListModel modelpublication = new DefaultListModel();
        try{
            rs = stmt.executeQuery("SELECT b.book_id, b.isbn, l.lib_name, r.checkout_date"
                    + " from Reservation r, booklist b, library l where r.patron_id = '"
            + patron_id +"' and b.lib_no = l.lib_no and r.resc_type = 'Book' and r.isactive = 1 "
            + "and b.book_id = r.resc_id");
            
            while (rs.next()) {
		    String book_id = rs.getString("book_id");
                    String isbn = rs.getString("isbn");
                    //String model = rs.getString("model");
                    String library_name = rs.getString("lib_name");
                    String checkout = rs.getString("checkout_date").substring(0,10);
                    modelpublication.addElement(book_id+" - "+isbn+" - "+library_name + " - " + checkout);
                }
                jList1.setModel(modelpublication);
        }
        catch(Throwable oops){
                        oops.printStackTrace();
                        }
        finally {
                close(rs);
                //close(stmt);
                //close(conn);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("Checked out Books");

        jButton1.setText("Check in");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Close");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1)
                            .addComponent(jButton2))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
String selectedval = (String) jList1.getSelectedValue();
        String book_id = selectedval.substring(0,4);
        try {
            int upd = stmt.executeUpdate("Update Reservation set isactive = 0, checkin_date = SYSDATE"
                  +  " where patron_id = '"+ patron_id + "' and resc_id = '"+ book_id + "' and isactive = 1");
            
            if (upd > 0 )
            {
                JOptionPane.showMessageDialog(null, "Successfully checked in!");
                
            }
            
            rs = stmt.executeQuery("select patron_id from resv_publ_q where rownum<=1 and patron_id like '%F' and publ_no = '"+book_id+"'");
            if(rs.next()){
                String faculty_id = rs.getString("patron_id");
                System.out.println(faculty_id+book_id);
                int update_res = stmt.executeUpdate("insert into reservation(RES_ID,PATRON_ID,RESC_TYPE,RESC_ID,CHECKOUT_DATE) values(reservation_seq.nextval,'"+faculty_id+"','Book', '" + book_id + "', SYSDATE)");
            int update_booklist = stmt1.executeUpdate("update booklist set is_available = 0 where book_id='" + book_id + "'");
            //rs = stmt.executeQuery("Commit");
            if (update_res > 0 && update_booklist > 0) {
                JOptionPane.showMessageDialog(null, "Book has been reserved for the 1st faculty in queue");
            } else {
                JOptionPane.showMessageDialog(null, "Book has not been reserved");
            }
            }
            else{
             rs = stmt.executeQuery("select patron_id from resv_publ_q where rownum<=1 and publ_no = '"+book_id+"'");   
             if(rs.next()){
                String student_id = rs.getString("patron_id");
                System.out.println("update booklist set is_available = 0 where book_id='" + book_id + "'");
                int update_res = stmt.executeUpdate("insert into reservation(RES_ID,PATRON_ID,RESC_TYPE,RESC_ID,CHECKOUT_DATE) values(reservation_seq.nextval,'"+student_id+"','Book', '" + book_id + "', SYSDATE)");
                int update_booklist = stmt2.executeUpdate("update booklist set is_available = 0 where book_id='" + book_id + "'");
            //rs = stmt.executeQuery("Commit");
                System.out.println(update_booklist);
            if (update_res > 0 && update_booklist > 0) {
                JOptionPane.showMessageDialog(null, "Book has been reserved for the 1st student in queue");
            } else {
                JOptionPane.showMessageDialog(null, "Book has not been reserved");
            }
            }
            }
            //rs = stmt.executeQuery("Commit");
            
            load_checkinpubl();
                
        }
        catch(Throwable oops){
                        oops.printStackTrace();
                        }
        finally {
                close(rs);
                //close(stmt);
                //close(conn);
            }        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1MouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_formMouseClicked

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
    this.dispose();
// TODO add your handling code here:
    }//GEN-LAST:event_jButton2MouseClicked

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
            java.util.logging.Logger.getLogger(BookCheckin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookCheckin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookCheckin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookCheckin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookCheckin(patron_id).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}