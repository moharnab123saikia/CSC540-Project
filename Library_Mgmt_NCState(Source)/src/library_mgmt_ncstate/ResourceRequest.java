/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library_mgmt_ncstate;


import java.sql.*;
import javax.swing.*;

/**
 *
 * @author Isha
 */
public class ResourceRequest extends javax.swing.JFrame {

    /**
     * Creates new form ResourceRequest
     */
    static final String jdbcURL 
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    static String patron_id ; 
    public ResourceRequest(String id) {
        patron_id = id;
        initComponents();
        connect2();
        load_resource_request_list();
    }
    
    public static void connect2()
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
    
    public void load_resource_request_list()
    {
        DefaultListModel modelcam1 = new DefaultListModel();
        DefaultListModel modelcam2 = new DefaultListModel();
        DefaultListModel modelcam3 = new DefaultListModel();
        try{
            rs = stmt.executeQuery("Select r.resc_id, c.make, c.model,r.checkout_date, r.checkin_date"
                    + " from reservation r, camera c where r.isactive = 0 "
                    + "and r.patron_id = '" + patron_id + "' and r.resc_type = 'Camera' "
                    + "and r.resc_id = c.camera_id" );
            modelcam1.addElement("*****Checked out in past*****");
            while (rs.next()) {
		    String cam_id = rs.getString("resc_id");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    String checkout_date = rs.getString("checkout_date");
                    String checkin_date = rs.getString("checkin_date");
                    modelcam1.addElement(cam_id + " - " + make + " - " + model + " from " + checkout_date.substring(0,10)
                    + " to " + checkin_date.substring(0,10));
                }
            modelcam1.addElement("*****Currently waitlisted*****");
            rs = stmt.executeQuery("Select q.camera_id, c.make, c.model,q.intended_friday, q.que_pos "
                    + " from resv_camera_q q, camera c where q.que_pos > 1 "
                    + "and q.patron_id = '" + patron_id
                    + "' and q.camera_id = c.camera_id" );
            while (rs.next()) {
		    String cam_id = rs.getString("camera_id");
                    String make = rs.getString("make");
                    String model = rs.getString("model");
                    String checkout_date = rs.getString("intended_friday");
                    int pos  = rs.getInt("que_pos");
                    pos = pos - 1;
                    modelcam1.addElement(cam_id + " - " + make + " - " + model + " for " + checkout_date.substring(0,10)
                            + " at position " + pos );
                }
            jList2.setModel(modelcam1);
            
            rs = stmt.executeQuery("Select rsv.resc_id, l.lib_name, rsv.checkout_date, rsv.checkin_date"
                    + " from reservation rsv, room r, library l where rsv.isactive = 0 "
                    + "and rsv.patron_id = '" + patron_id + "' and rsv.resc_type = 'Room' "
                    + "and rsv.resc_id = r.room_no and l.lib_no = r.lib_no"  );
            modelcam2.addElement("*****Checked out in past*****");
            while (rs.next()) {
		    String room_id = rs.getString("resc_id");
                    String lib_name = rs.getString("lib_name");
                    String checkout_date = rs.getString("checkout_date");
                    String checkin_date = rs.getString("checkin_date");
                    modelcam2.addElement(room_id + " in " + lib_name + " on " + checkout_date.substring(0,10)
                    + " from " + checkout_date.substring(12,16) + " to " + checkin_date.substring(12,16));
                }
            
            rs = stmt.executeQuery("Select rsv.room_no, l.lib_name, rsv.start_time, rsv.end_time"
                    + " from resv_room rsv, room r, library l where rsv.patron_id = '" + patron_id
                    + "' and rsv.room_no = r.room_no and l.lib_no = r.lib_no"  );
            modelcam2.addElement("*****Future reservations*****");
            while (rs.next()) {
		    String room_id = rs.getString("room_no");
                    String lib_name = rs.getString("lib_name");
                    String checkout_date = rs.getString("start_time").substring(0,10);
                    String from = rs.getString("start_time").substring(12,16);
                    String to = rs.getString("end_time").substring(12,16);
                    modelcam2.addElement(room_id + " in " + lib_name + " on " + checkout_date +" from "
                    + from + " to " + to);
                }
            jList1.setModel(modelcam2);
            
            modelcam3.addElement("*****Checked out in past*****");
            System.out.println("Select rsv.resc_id, l.lib_name, rsv.checkout_date, bk.title, bk.isbn"
                    + " from reservation rsv, BookList b, Book bk, library l where rsv.isactive = 0 "
                    + "and rsv.patron_id = '" + patron_id + "' and rsv.resc_type = 'Book' "
                    + "and rsv.resc_id = b.book_id and l.lib_no = b.lib_no and b.isbn = bk.isbn");
            rs = stmt.executeQuery("Select rsv.resc_id, l.lib_name, rsv.checkout_date, bk.title, bk.isbn"
                    + " from reservation rsv, BookList b, Book bk, library l where rsv.isactive = 0 "
                    + "and rsv.patron_id = '" + patron_id + "' and rsv.resc_type = 'Book' "
                    + "and rsv.resc_id = b.book_id and l.lib_no = b.lib_no and b.isbn = bk.isbn"  );
            while (rs.next()) {
		    String book_id = rs.getString("resc_id");
                    String lib_name = rs.getString("lib_name");
                    String checkout_date = rs.getString("checkout_date");
                    String title = rs.getString("title");
                    String isbn = rs.getString("isbn");
                    modelcam3.addElement(book_id + " - " + title + " - "+ isbn +
                            " from " + lib_name + " on " + checkout_date.substring(0,10));
                }
            
            rs = stmt.executeQuery("Select rsv.resc_id, l.lib_name, rsv.checkout_date, jl.title, jl.issn"
                    + " from reservation rsv, JournalList j, Journal jl, library l where rsv.isactive = 0 "
                    + "and rsv.patron_id = '" + patron_id + "' and rsv.resc_type = 'Journal' "
                    + "and rsv.resc_id = j.journal_id and l.lib_no = j.lib_no and j.issn = jl.issn"  );
            while (rs.next()) {
		    String journal_id = rs.getString("resc_id");
                    String lib_name = rs.getString("lib_name");
                    String checkout_date = rs.getString("checkout_date");
                    String title = rs.getString("title");
                    String issn = rs.getString("issn");
                    modelcam3.addElement(journal_id + " - " + title + " - "+ issn +
                            "from " + lib_name + " on " + checkout_date.substring(0,10));
                }
            
            rs = stmt.executeQuery("Select rsv.resc_id, l.lib_name, rsv.checkout_date, c.title, c.conf_num"
                    + " from reservation rsv, CPlist cl, conf c, library l where rsv.isactive = 0 "
                    + "and rsv.patron_id = '" + patron_id + "' and rsv.resc_type = 'Conf' "
                    + "and rsv.resc_id = cl.paper_id and l.lib_no = cl.lib_no and c.conf_num = cl.conf_num"  );
            while (rs.next()) {
		    String paper_id = rs.getString("resc_id");
                    String lib_name = rs.getString("lib_name");
                    String checkout_date = rs.getString("checkout_date");
                    String title = rs.getString("title");
                    String conf_num = rs.getString("conf_num");
                    modelcam3.addElement(paper_id + " - " + title + " - "+ conf_num +
                            "from " + lib_name + " on " + checkout_date.substring(0,10));
                }
            
            modelcam3.addElement("*****Waitlisted for*****");
            System.out.println("Select rsv.publ_no, l.lib_name, bk.title, bk.isbn"
                    + " from resv_publ_q rsv, BookList b, Book bk, library l where"
                    + "rsv.patron_id = '" + patron_id + 
                    "' and rsv.publ_no = b.book_id and l.lib_no = b.lib_no and b.isbn = bk.isbn" );
            
            rs = stmt.executeQuery("Select rsv.publ_no, l.lib_name, bk.title, bk.isbn"
                    + " from resv_publ_q rsv, BookList b, Book bk, library l where "
                    + "rsv.patron_id = '" + patron_id + 
                    "' and rsv.publ_no = b.book_id and l.lib_no = b.lib_no and b.isbn = bk.isbn"  );
            while (rs.next()) {
		    String publ_no = rs.getString("publ_no");
                    String lib_name = rs.getString("lib_name");
                    String title = rs.getString("title");
                    String isbn = rs.getString("isbn");
                    modelcam3.addElement(publ_no + " - " + title + " - "+ isbn +
                            "from " + lib_name);
                }
            
            rs = stmt.executeQuery("Select rsv.publ_no, l.lib_name, j.title, j.issn"
                    + " from resv_publ_q rsv, journallist jl, journal j, library l where "
                    + "rsv.patron_id = '" + patron_id + 
                    "' and rsv.publ_no = jl.journal_id and l.lib_no = jl.lib_no and jl.issn = j.issn"  );
            while (rs.next()) {
		    String publ_no = rs.getString("publ_no");
                    String lib_name = rs.getString("lib_name");
                    String title = rs.getString("title");
                    String issn = rs.getString("issn");
                    modelcam3.addElement(publ_no + " - " + title + " - "+ issn +
                            "from " + lib_name);
                }
            
            rs = stmt.executeQuery("Select rsv.publ_no, l.lib_name, c.title, c.conf_num"
                    + " from resv_publ_q rsv, cplist cl, conf c, library l where "
                    + "rsv.patron_id = '" + patron_id + 
                    "' and rsv.publ_no = cl.paper_id and l.lib_no = cl.lib_no and cl.conf_num = c.conf_num"  );
            while (rs.next()) {
		    String publ_no = rs.getString("publ_no");
                    String lib_name = rs.getString("lib_name");
                    String title = rs.getString("title");
                    String conf_num = rs.getString("conf_num");
                    modelcam3.addElement(publ_no + " - " + title + " - "+ conf_num +
                            "from " + lib_name);
                }
            
            
            jList3.setModel(modelcam3);
                
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Publications");

        jLabel2.setText("Rooms");

        jLabel3.setText("Camera");

        jScrollPane4.setViewportView(jList1);

        jScrollPane5.setViewportView(jList2);

        jScrollPane6.setViewportView(jList3);

        jButton1.setText("Close");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(78, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(511, 511, 511))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(76, 76, 76))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(195, 195, 195)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(510, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(113, 113, 113)
                .addComponent(jLabel1)
                .addGap(112, 112, 112)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(65, 65, 65))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(jButton1)
                .addGap(31, 31, 31))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(319, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(26, 26, 26)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1MouseClicked

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
            java.util.logging.Logger.getLogger(ResourceRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ResourceRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ResourceRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ResourceRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ResourceRequest(patron_id).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    // End of variables declaration//GEN-END:variables
}
