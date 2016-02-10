/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library_mgmt_ncstate;

import java.sql.*;
import javax.swing.DefaultListModel;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 *
 * @author Isha
 */
public class CameraCheckInOut extends javax.swing.JFrame {

    /**
     * Creates new form CameraCheckInOut
     */
    static final String jdbcURL 
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    static String patron_id;
    
    public CameraCheckInOut(String id) {
        patron_id = id;
        initComponents();
        connect1();
        load_checkInOut();
    }
    
    public  void refresh()
    {
        jTextField1.setText("");
        jTextArea1.setText("");
        load_checkInOut();
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
                System.out.println("hi6");
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
    
    public  void load_checkInOut()
    {
        DefaultListModel modelcam1 = new DefaultListModel();
        DefaultListModel modelcam2 = new DefaultListModel();
        try{
            System.out.println("SELECT c.camera_id,c.make, c.model, l.lib_name, q.intended_friday"
                    + " from resv_camera_q q, camera c, library l where q.patron_id = '"
            + patron_id +"' and q.que_pos = 1 and c.lib_no = l.lib_no and q.camera_id = c.camera_id");
            rs = stmt.executeQuery("SELECT c.camera_id,c.make, c.model, l.lib_name, q.intended_friday"
                    + " from resv_camera_q q, camera c, library l where q.patron_id = '"
            + patron_id +"' and q.que_pos = 1 and c.lib_no = l.lib_no and q.camera_id = c.camera_id");
            // and q.intended_friday = sysdate;
            
                while (rs.next()) {
		    String camera_id = rs.getString("camera_id");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    String library_name = rs.getString("lib_name");
                    String checkout = rs.getString("intended_friday").substring(0,10);
                    modelcam1.addElement(camera_id+" - "+make+" - "+model+" - "+library_name + " - " + checkout);
                }
                jList1.setModel(modelcam1);
               
                
                System.out.println("SELECT c.camera_id,c.make, c.model, l.lib_name, r.checkout_date"
                    + " from Reservation r, camera c, library l where r.patron_id = '"
            + patron_id +"' and c.lib_no = l.lib_no and r.resc_type = 'Camera' and r.isactive = 1 "
                        + "and c.camera_id = r.resc_id");
            rs = stmt.executeQuery("SELECT c.camera_id,c.make, c.model, l.lib_name, r.checkout_date"
                    + " from Reservation r, camera c, library l where r.patron_id = '"
            + patron_id +"' and c.lib_no = l.lib_no and r.resc_type = 'Camera' and r.isactive = 1 "
            + "and c.camera_id = r.resc_id");
            
                while (rs.next()) {
		    String camera_id = rs.getString("camera_id");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    String library_name = rs.getString("lib_name");
                    String checkout = rs.getString("checkout_date").substring(0,10);
                    modelcam2.addElement(camera_id+" - "+make+" - "+model+" - "+library_name + " - " + checkout);
                }
                jList2.setModel(modelcam2);
                
                
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jList1);

        jScrollPane2.setViewportView(jList2);

        jLabel1.setText("Items ready for Checkout - Select the item and click on Checkout");

        jLabel2.setText("Items to Checkedin - Select the item and click on Checkin");

        jButton1.setText("CheckIn!");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        jButton2.setText("CheckOut!");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jButton3.setText("Refresh");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });

        jLabel3.setText("YYYY/MM/DD HH24:MI:SS");

        jButton4.setText("Close");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(83, 83, 83)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jLabel2)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(55, 55, 55)
                                .addComponent(jButton2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addComponent(jButton3))
                        .addGap(72, 72, 72))))
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(27, 27, 27)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(138, 305, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton3))
                        .addGap(18, 18, 18)
                        .addComponent(jButton4)
                        .addGap(9, 9, 9))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        Calendar c = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String selectedval = (String) jList1.getSelectedValue();
        String cam_id = selectedval.substring(0,3);
        String Checkout = selectedval.substring(selectedval.indexOf("2015"));
        System.out.println("check date: " + Checkout);
        try{
            java.util.Date Checkoutdate = formatter.parse(Checkout);
            c.setTime(Checkoutdate);
            String formatedCheckoutDate = c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" +c.get(Calendar.YEAR);
            c.add(Calendar.DATE, 6);
            String formatedCheckinDate = c.get(Calendar.DATE) + "/" + (c.get(Calendar.MONTH) + 1) + "/" +c.get(Calendar.YEAR);
            System.out.println("formated date: " + formatedCheckoutDate);
            System.out.println("new date: " + formatedCheckinDate);
            System.out.println("Insert into Reservation values (reservation_seq.nextval,'"+ patron_id 
                    +"','Camera','"+ cam_id + "',TO_CHAR(to_date('" + formatedCheckoutDate +"','DD/MM/YYYY'), 'DD-MON-YY'),'',"
                    //+ "TO_CHAR(to_date('" + formatedCheckinDate +"','DD/MM/YYYY'), 'DD-MON-YY'),"+1+")");
                    + "'',"+1+")");
            rs = stmt.executeQuery("Insert into Reservation values (reservation_seq.nextval,'"+ patron_id 
                    +"','Camera','"+ cam_id + "',TO_CHAR(to_date('" + formatedCheckoutDate +"','DD/MM/YYYY'), 'DD-MON-YY'),'',"
                    //+ "TO_CHAR(to_date('" + formatedCheckinDate +"','DD/MM/YYYY'), 'DD-MON-YY'),"+1+")");
                    + "'',"+1+")");
            System.out.println("Delete from resv_camera_q where camera_id = '"+ cam_id + "' "
                    + "and intended_friday = TO_CHAR(to_date('" + formatedCheckoutDate +"','DD/MM/YYYY'), 'DD-MON-YY')");
            rs = stmt.executeQuery("Delete from resv_camera_q where camera_id = '"+ cam_id + "' "
                    + "and intended_friday = TO_CHAR(to_date('" + formatedCheckoutDate +"','DD/MM/YYYY'), 'DD-MON-YY')");
            rs= stmt.executeQuery("Commit");
            jTextArea1.setText("Checked Out!!");
        }
                catch(Throwable oops){
                        oops.printStackTrace();
                        }
        finally {
                close(rs);
                //close(stmt);
                //close(conn);
            }
        
    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        String selectedval = (String) jList2.getSelectedValue();
        String cam_id = selectedval.substring(0,3);
        try {
            System.out.println("Update Reservation set isactive = 0, checkin_date = "
                  + "to_Date('"+jTextField1.getText() +"','YYYY/MM/DD HH24:MI:SS') "
                  +  " where patron_id = '"+ patron_id + "' and resc_id = '"+ cam_id + "' and isactive = 1");
            int upd = stmt.executeUpdate("Update Reservation set isactive = 0, checkin_date = "
                  + "to_Date('"+jTextField1.getText() +"','YYYY/MM/DD HH24:MI:SS') "
                  +  " where patron_id = '"+ patron_id + "' and resc_id = '"+ cam_id + "' and isactive = 1");
            //rs = stmt.executeQuery("Commit");
            if (upd > 0 )
            {
                jTextArea1.setText("Checked In!!");
            }
                
        }
        catch(Throwable oops){
                        oops.printStackTrace();
                        }
        finally {
                close(rs);
                //close(stmt);
                //close(conn);
            }
    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        refresh();
    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton4MouseClicked

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
            java.util.logging.Logger.getLogger(CameraCheckInOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CameraCheckInOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CameraCheckInOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CameraCheckInOut.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CameraCheckInOut(patron_id).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
