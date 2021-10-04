package AppointmentAutomation;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Arc2D;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Bilal
 */
public class Statistics extends javax.swing.JFrame {

    private double userType_patientCount;
    private double userType_doctorCount;
    private double userType_ITCount;
    private double userType_Count;
    private double userType_SecretaryCount;
    private double userType_AdministratorCount;
    //User Type variables
    private double doctorDepartments_CardiologyCount;
    private double doctorDepartments_EarNoseAndThroatCount;
    private double doctorDepartments_GastroenterologyCount;
    private double doctorDepartments_GeneralSurgeryCount;
    private double doctorDepartments_NeurologyCount;
    private double doctorDepartments_Psychology;
    private double doctorDepartments_Count;

    public Statistics()  {
        initComponents();
        readUserTypes();//to find all of user type number, database is searched.
        readDepartmentsOfAppointmens();
        this.setTitle("Statistics");
        
        userTypesStatistics(pnl_userTypes.getGraphics());
        departmentsOfAppointments(pnl_departmensOfAppointments.getGraphics());
        
    }

    public void departmentsOfAppointments(Graphics g1) {
        doctorDepartments_Count = doctorDepartments_CardiologyCount + doctorDepartments_EarNoseAndThroatCount + doctorDepartments_GastroenterologyCount
                + doctorDepartments_GeneralSurgeryCount + doctorDepartments_NeurologyCount + doctorDepartments_Psychology;
        double magnitude = 360.0 / doctorDepartments_Count;//the magnitude of one pie
        double sum = 0;//this variable specify begining of a pie.

        Graphics2D g2d = (Graphics2D) g1;

        g2d.setColor(new Color(255, 51, 204));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, 0, magnitude * doctorDepartments_CardiologyCount, Arc2D.PIE));//pie of patient user is created
        sum += magnitude * doctorDepartments_CardiologyCount;

        g2d.fillRect(400, 50, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("Cardiology count: " + doctorDepartments_CardiologyCount, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 65);//String is written

        g2d.setColor(new Color(255, 204, 51));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, sum, magnitude * doctorDepartments_EarNoseAndThroatCount, Arc2D.PIE));//pie of doctor user is created
        sum += magnitude * doctorDepartments_EarNoseAndThroatCount;

        g2d.fillRect(400, 100, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("Ear Nose and Throat count: " + doctorDepartments_EarNoseAndThroatCount, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 115);//String is written

        g2d.setColor(new Color(255, 150, 204));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, sum, magnitude * doctorDepartments_GastroenterologyCount, Arc2D.PIE));//pie of administator user is created
        sum += magnitude * doctorDepartments_GastroenterologyCount;

        g2d.fillRect(400, 150, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("Gastroenterology count: " + doctorDepartments_GastroenterologyCount, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 165);//String is written

        g2d.setColor(new Color(0, 255, 255));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, sum, magnitude * doctorDepartments_GeneralSurgeryCount, Arc2D.PIE));//pie of secretary user is created
        sum += magnitude * doctorDepartments_GeneralSurgeryCount;

        g2d.fillRect(400, 200, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("General Surgery count: " + doctorDepartments_GeneralSurgeryCount, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 215);//String is written

        g2d.setColor(new Color(0, 102, 102));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, sum, magnitude * doctorDepartments_NeurologyCount, Arc2D.PIE));//pie of IT user is created
        sum += magnitude * doctorDepartments_NeurologyCount;

        g2d.fillRect(400, 250, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("Neurology count: " + doctorDepartments_NeurologyCount, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 265);//String is written

        g2d.setColor(new Color(100, 50, 152));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, sum, magnitude * doctorDepartments_Psychology, Arc2D.PIE));//pie of IT user is created

        g2d.fillRect(400, 300, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("Psychology count: " + doctorDepartments_Psychology, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 315);//String is written

    }

    public void userTypesStatistics(Graphics g2) {
        userType_Count = userType_AdministratorCount + userType_ITCount + userType_SecretaryCount + userType_doctorCount + userType_patientCount;
        double magnitude = 360.0 / userType_Count;//the magnitude of one pie
        double sum = 0;//this variable specify begining of a pie.

        Graphics2D g2d = (Graphics2D) g2;

        g2d.setColor(new Color(255, 51, 204));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, 0, magnitude * userType_patientCount, Arc2D.PIE));//pie of patient user is created
        sum += magnitude * userType_patientCount;

        g2d.fillRect(400, 50, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("Patient user count: " + userType_patientCount, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 65);//String is written

        g2d.setColor(new Color(255, 204, 51));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, sum, magnitude * userType_doctorCount, Arc2D.PIE));//pie of doctor user is created
        sum += magnitude * userType_doctorCount;

        g2d.fillRect(400, 100, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("Doctor user count: " + userType_doctorCount, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 115);//String is written

        g2d.setColor(new Color(255, 150, 204));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, sum, magnitude * userType_AdministratorCount, Arc2D.PIE));//pie of administator user is created
        sum += magnitude * userType_AdministratorCount;

        g2d.fillRect(400, 150, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("Admin user count: " + userType_AdministratorCount, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 165);//String is written

        g2d.setColor(new Color(0, 255, 255));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, sum, magnitude * userType_SecretaryCount, Arc2D.PIE));//pie of secretary user is created
        sum += magnitude * userType_SecretaryCount;

        g2d.fillRect(400, 200, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("Secretary user count: " + userType_SecretaryCount, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 215);//String is written

        g2d.setColor(new Color(0, 102, 102));
        g2d.fill(new Arc2D.Double(75, 50, 300, 300, sum, magnitude * userType_ITCount, Arc2D.PIE));//pie of IT user is created

        g2d.fillRect(400, 250, 20, 20);//this rectangle is created to show which part belong to this color.
        g2d.setFont(new Font("IT user count: " + userType_ITCount, Font.BOLD, 18));//Font is changed
        g2d.drawString(g2d.getFont().getName(), 425, 265);//String is written
    }

    public void readUserTypes() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            String query = "SELECT USER_TYPE FROM TBL_USERS";//KKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {
                String userType = rst.getString("USER_TYPE");
                if (userType.equals("Standart user")) {
                    userType_patientCount++;
                } else if (userType.equals("Doctor")) {
                    userType_doctorCount++;
                } else if (userType.equals("IT")) {
                    userType_ITCount++;
                } else if (userType.equals("Secretary")) {
                    userType_SecretaryCount++;
                } else {
                    userType_AdministratorCount++;
                }
            }
            rst.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void readDepartmentsOfAppointmens() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            String query = "SELECT DOCTOR_DEPARTMENT FROM TBL_APPOINTMENTS";//BURASI TAMAMEN DEGİSECEKTKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {
                String doctorDepartment = rst.getString("DOCTOR_DEPARTMENT");
                if (doctorDepartment.equals("Cardiology")) {
                    doctorDepartments_CardiologyCount++;
                } else if (doctorDepartment.equals("Ear Nose and Throat")) {
                    doctorDepartments_EarNoseAndThroatCount++;
                } else if (doctorDepartment.equals("Gastroenterology")) {
                    doctorDepartments_GastroenterologyCount++;
                } else if (doctorDepartment.equals("General Surgery")) {
                    doctorDepartments_GeneralSurgeryCount++;
                } else if (doctorDepartment.equals("Neurology")) {
                    doctorDepartments_NeurologyCount++;
                } else {
                    doctorDepartments_Psychology++;
                }
            }
            rst.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnl_userTypes = new javax.swing.JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                userTypesStatistics(g);
            }
        };
        pnl_departmensOfAppointments = new javax.swing.JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                departmentsOfAppointments(g);
            }
        };

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        pnl_userTypes.setBackground(new java.awt.Color(0, 153, 153));

        javax.swing.GroupLayout pnl_userTypesLayout = new javax.swing.GroupLayout(pnl_userTypes);
        pnl_userTypes.setLayout(pnl_userTypesLayout);
        pnl_userTypesLayout.setHorizontalGroup(
            pnl_userTypesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 731, Short.MAX_VALUE)
        );
        pnl_userTypesLayout.setVerticalGroup(
            pnl_userTypesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("User Types", pnl_userTypes);

        pnl_departmensOfAppointments.setBackground(new java.awt.Color(0, 153, 204));

        javax.swing.GroupLayout pnl_departmensOfAppointmentsLayout = new javax.swing.GroupLayout(pnl_departmensOfAppointments);
        pnl_departmensOfAppointments.setLayout(pnl_departmensOfAppointmentsLayout);
        pnl_departmensOfAppointmentsLayout.setHorizontalGroup(
            pnl_departmensOfAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 731, Short.MAX_VALUE)
        );
        pnl_departmensOfAppointmentsLayout.setVerticalGroup(
            pnl_departmensOfAppointmentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 405, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Departments of Appointments", pnl_departmensOfAppointments);

        jPanel1.add(jTabbedPane1);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Statistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Statistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Statistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Statistics.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Statistics().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnl_departmensOfAppointments;
    private javax.swing.JPanel pnl_userTypes;
    // End of variables declaration//GEN-END:variables
}
