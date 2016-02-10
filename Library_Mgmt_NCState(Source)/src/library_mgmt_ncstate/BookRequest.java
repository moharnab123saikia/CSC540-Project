/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library_mgmt_ncstate;


import java.util.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.*;

/**
 *
 * @author Isha
 */
public class BookRequest extends javax.swing.JFrame {

    /**
     * Creates new form Camera_module
     */
    static final String jdbcURL
            = "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl";
    static Connection conn = null;
    static Statement stmt = null;
    static Statement stmt1 = null;
    static ResultSet rs = null;
    static ResultSet rs1 = null;
    static Integer blocking_id;
    static String patron_id ;

    public BookRequest(String id) {
        patron_id = id;
        initComponents();
        connect();
        load_book_list();
    }

    public static void connect() {
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

        } catch (Throwable oops) {
            oops.printStackTrace();
        } finally {
            close(rs);
            //close(stmt);
            //close(conn);
        }
    }

    public void load_book_list() {
        DefaultListModel booklist = new DefaultListModel();
        DefaultListModel booklist2 = new DefaultListModel();
        String usertype = patron_id.substring(0, 1);
        jCheckBox1.setText("Get e-copy");

        if (usertype.equals("S")) {

            try {
                rs1 = stmt.executeQuery("SELECT B.Blocking_ID from BLOCKBOOK B, ENROLLED E WHERE B.COURSE_ID=E.COURSE_ID and B.DEPT_ID=E.DEPT_ID and E.STUDENT_NUM='"+patron_id+"'");
                while (rs1.next()) {
                    blocking_id = rs1.getInt("blocking_id");
                    System.out.println(blocking_id);
                    rs = stmt1.executeQuery("SELECT BOOK_ID FROM BOOKLIST where is_available=1 and isblocked=1 and blocking_id= " + blocking_id);
                    while (rs.next()) {
                        String book_id = rs.getString("BOOK_ID");
                        booklist.addElement(book_id);
                    }
                    
                    rs = stmt1.executeQuery("SELECT BOOK_ID FROM BOOKLIST where is_available=0 and isblocked=1 and blocking_id= " + blocking_id);
                    while (rs.next()) {
                        String book_id = rs.getString("BOOK_ID");
                        booklist2.addElement(book_id);
                    }
                    //jList1.setModel(booklist);
                }
                rs = stmt1.executeQuery("SELECT BOOK_ID FROM BOOKLIST where is_available=1 and isblocked=0");
                while (rs.next()) {
                    String book_id = rs.getString("BOOK_ID");
                    booklist.addElement(book_id);
                }
                
                rs = stmt1.executeQuery("SELECT BOOK_ID FROM BOOKLIST where is_available=0 and isblocked =0");
                while (rs.next()) {
                    String book_id = rs.getString("BOOK_ID");
                    booklist2.addElement(book_id);
                }
                
                jList3.setModel(booklist2);
                jList1.setModel(booklist);
            } catch (Throwable oops) {
                oops.printStackTrace();
            } finally {
                close(rs);
                //close(stmt);
                //close(conn);
            }
        } else if (usertype.equals("F")) {
            try {

                rs = stmt1.executeQuery("SELECT BOOK_ID FROM BOOKLIST where is_available=1");
                while (rs.next()) {
                    String book_id = rs.getString("BOOK_ID");
                    booklist.addElement(book_id);
                }
                
                rs = stmt1.executeQuery("SELECT BOOK_ID FROM BOOKLIST where is_available=0");
                while (rs.next()) {
                    String book_id = rs.getString("BOOK_ID");
                    booklist2.addElement(book_id);
                }
                jList1.setModel(booklist);
                jList3.setModel(booklist2);

            } catch (Throwable oops) {
                oops.printStackTrace();
            } finally {
                close(rs);
                //close(stmt);
                //close(conn);
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(0, 0));
        setName("camer_frame"); // NOI18N
        setSize(new java.awt.Dimension(200, 150));

        jPanel1.setName("cmaera_panel"); // NOI18N

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setName("camera_list"); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel1.setText("Booklist(Book ID)");
        jLabel1.setName("CameraList_label"); // NOI18N

        jList2.setName("Camera_detail"); // NOI18N
        jScrollPane2.setViewportView(jList2);

        jButton1.setText("Request");
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

        jLabel3.setText("Click a book id from the list to view the details");

        jLabel5.setText("Book Details");
        jLabel5.setName("CameraList_label"); // NOI18N

        jCheckBox1.setText("Ebook");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jList3.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jList3);

        jLabel2.setText("Waitlist");

        jButton2.setText("Close");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(129, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)))
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 565, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(38, 38, 38))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1)
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(100, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:
        //int count = 0;
        String book_id = "";
        book_id = (String) jList1.getSelectedValue();
        System.out.println(book_id);
        String book_id2 = (String) jList3.getSelectedValue();
        
        
        try {
            
            if(book_id != null){
            rs=stmt.executeQuery("select isbn from booklist where book_id = '"+book_id+"'");
            rs.next();
            String isbn=rs.getString("isbn");
            rs=stmt.executeQuery("Select r.patron_id from reservation r, booklist bl where r.resc_id=bl.book_id and r.patron_id = '"+patron_id+"' and bl.isbn = '"+isbn+"'");   
            if(rs.next())
            {
                JOptionPane.showMessageDialog(null, "You have already reserved a copy of the book");
            }
            
            else{
            //check for e-copy
            
            rs = stmt1.executeQuery("SELECT bk.e_copy FROM BOOKLIST BL, BOOK Bk where Bk.isbn=bl.isbn and bl.is_available=1 and bl.book_id='"+book_id+"'");
                rs.next();
                    String ecopy = rs.getString("E_COPY");
                    
            if(ecopy.equals("1") && jCheckBox1.isSelected())
            {
                JOptionPane.showMessageDialog(null, "E-copy has been reserved");
            }
            else if(ecopy.equals("0") && jCheckBox1.isSelected())
                {
                JOptionPane.showMessageDialog(null, "E-copy is not available");
            }
            
            else{
            int update_res = stmt.executeUpdate("insert into reservation(RES_ID,PATRON_ID,RESC_TYPE,RESC_ID,CHECKOUT_DATE) values(reservation_seq.nextval,'"+patron_id+"','Book', '" + book_id + "', SYSDATE)");
            int update_booklist = stmt1.executeUpdate("update booklist set is_available = 0 where book_id='" + book_id + "'");
            //rs = stmt.executeQuery("Commit");
            if (update_res > 0 && update_booklist > 0) {
                JOptionPane.showMessageDialog(null, "Book has been reserved");
            } else {
                JOptionPane.showMessageDialog(null, "Book has not been reserved");
            }
            }
            }
            }
            else if(book_id2 != null){ 
                
                rs=stmt.executeQuery("Select patron_id from resv_publ_q where patron_id = '"+patron_id+"' and publ_no = '"+book_id2+"'");
                if(rs.next())
                    JOptionPane.showMessageDialog(null, "You are already in the queue for the book");
                else{
                    
                
                int update = stmt.executeUpdate("insert into resv_publ_q(publ_no,patron_id,req_date) values('"+book_id2+"','"+patron_id+"', SYSDATE)");
              // System.out.println("Update value: "+update);
               if (update> 0) {
               JOptionPane.showMessageDialog(null, "You are in the queue for the book");}
            //} else {
             //   JOptionPane.showMessageDialog(null, "Queuing not successful");
            //}
                }
            }
            load_book_list();
        } catch (Throwable oops) {
            oops.printStackTrace();
        } finally {
            close(rs);
            //close(stmt);
            //close(conn);
        }

    }//GEN-LAST:event_jButton1MouseClicked

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        DefaultListModel bookdetail = new DefaultListModel();
        String book_id = (String) jList1.getSelectedValue();
        try {
            rs = stmt.executeQuery("SELECT b.isbn,b.title,b.author_name,b.edition,b.publisher,b.year_of_pub,b.e_copy,l.lib_name"
                    + " FROM Book b, library l where b.isbn in(select b1.isbn from Book b1, Booklist bl where b1.isbn=bl.isbn and bl.lib_no=l.lib_no and bl.book_id = '" + book_id + "') ");
            bookdetail.clear();
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author_name");
                String edition = rs.getString("edition");
                String publisher = rs.getString("publisher");
                String e_copy = rs.getBoolean("e_copy")?"Yes":"False";
                String library_name = rs.getString("lib_name");
                bookdetail.addElement("ISBN: " + isbn);
                bookdetail.addElement("Title: " + title);
                bookdetail.addElement("Author Names: " + author);
                bookdetail.addElement("Edition: " + edition);
                bookdetail.addElement("Publisher: " + publisher);
                bookdetail.addElement("E-copy available: "+ e_copy);
                bookdetail.addElement("Library Name: " + library_name);
            }
            jList2.setModel(bookdetail);
        } catch (Throwable oops) {
            oops.printStackTrace();
        } finally {
            close(rs);
            //close(stmt);
            //close(conn);
        }
    }//GEN-LAST:event_jList1MouseClicked

    
    
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jList3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList3MouseClicked
        // TODO add your handling code here:
        
        DefaultListModel bookdetail = new DefaultListModel();
        String book_id = (String) jList3.getSelectedValue();
        try {
            rs = stmt.executeQuery("SELECT b.isbn,b.title,b.author_name,b.edition,b.publisher,b.year_of_pub,b.e_copy,l.lib_name"
                    + " FROM Book b, library l where b.isbn in(select b1.isbn from Book b1, Booklist bl where b1.isbn=bl.isbn and bl.lib_no=l.lib_no and bl.book_id = '" + book_id + "') ");
            bookdetail.clear();
            while (rs.next()) {
                String isbn = rs.getString("isbn");
                String title = rs.getString("title");
                String author = rs.getString("author_name");
                String edition = rs.getString("edition");
                String publisher = rs.getString("publisher");
                String e_copy = rs.getBoolean("e_copy")?"Yes":"False";
                String library_name = rs.getString("lib_name");
                bookdetail.addElement("ISBN: " + isbn);
                bookdetail.addElement("Title: " + title);
                bookdetail.addElement("Author Names: " + author);
                bookdetail.addElement("Edition: " + edition);
                bookdetail.addElement("Publisher: " + publisher);
                bookdetail.addElement("E-copy available: "+ e_copy);
                bookdetail.addElement("Library Name: " + library_name);
            }
            jList2.setModel(bookdetail);
        } catch (Throwable oops) {
            oops.printStackTrace();
        } finally {
            close(rs);
            //close(stmt);
            //close(conn);
        }
 
    }//GEN-LAST:event_jList3MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2MouseClicked

    static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (Throwable oops) {
                oops.printStackTrace();
            }
        }
    }

    static void close(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Throwable oops) {
                oops.printStackTrace();
            }
        }
    }

    static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Throwable oops) {
                oops.printStackTrace();
            }
        }
    }

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
            java.util.logging.Logger.getLogger(BookRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BookRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BookRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BookRequest.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BookRequest(patron_id).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
