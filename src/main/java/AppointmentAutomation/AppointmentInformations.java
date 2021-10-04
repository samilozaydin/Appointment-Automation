package AppointmentAutomation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Bilal
 */
public class AppointmentInformations extends javax.swing.JFrame {

    private MainScreen mainScreen;
    private int selectedRowId;
    private String doctorName;
    private String doctorSurname;
    private String patientName;
    private String patientSurname;
    private String complaint;
    private String identificationNumber;
    private String appointmentDate;
    private String appointmentTime;
    private String doctorDepartment;

    public AppointmentInformations() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    public AppointmentInformations(MainScreen mainScreen, int selectedRowId) {
        initComponents();
        this.setLocationRelativeTo(null);
        this.mainScreen = mainScreen;
        this.selectedRowId = selectedRowId;
        this.setTitle("Appointment Informations");
        showAppointmentInformation();
        showImages();
        complaint = this.txta_complaint.getText();
    }

    public void showAppointmentInformation() {
//Appointment Information section is displayed
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            String query = "SELECT * FROM TBL_APPOINTMENTS WHERE ID = " + this.selectedRowId;
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {

                String patientName = rst.getString("PATIENT_NAME");
                String patientSurname = rst.getString("PATIENT_SURNAME");
                String identificationNumber = rst.getString("PATIENT_ID");
                String doctorName = rst.getString("DOCTOR_NAME");//these four variable are going to use for comparing                            
                String doctorSurname = rst.getString("DOCTOR_SURNAME");
                String appointmentDate = rst.getString("APPOINTMENT_DATE");
                String appointmentTime = rst.getString("APPOINTMENT_TIME");
                String doctorDepartment = rst.getString("DOCTOR_DEPARTMENT");
                String complaint = rst.getString("COMPLAINT");

                //appointment informations are transmitted
                this.lbl_patientName.setText(patientName);
                this.lbl_patientSurname.setText(patientSurname);
                this.lbl_patientIdentificationNumber.setText(identificationNumber);
                this.lbl_doctorName.setText(doctorName);
                this.lbl_doctorSurname.setText(doctorSurname);
                this.lbl_appointmentDay.setText(appointmentDate);
                this.lbl_appointmentTime.setText(appointmentTime);
                this.txta_complaint.setText(complaint);
                
                //class variables is assigned
                this.complaint=complaint;
                this.appointmentDate=appointmentDate;
                this.appointmentTime=appointmentTime;
                this.identificationNumber= identificationNumber;
                this.doctorDepartment=doctorDepartment;
                
                
                rst.close();
                stmt.close();
                conn.close();
                showPatientInformation(patientName, patientSurname);
                showDoctorInformation(doctorName, doctorSurname);
                return;

            }

        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showPatientInformation(String patientName, String patientSurname) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            String query = "SELECT * FROM TBL_USERS WHERE NAME = '" + patientName + "'" + " AND SURNAME ='" + patientSurname + "'";
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {

                patientName = rst.getString("NAME");
                patientSurname = rst.getString("SURNAME");
                String identificationNumber = rst.getString("IDENTIFICATION_NUMBER");
                String phoneNumber = rst.getString("PHONE_NUMBER");
                String emailR = rst.getString("E_MAIL");
                String genderR = rst.getString("GENDER");
                String birthdayR = rst.getString("BIRTHDAY");
                String weightR = rst.getString("WEIGHT");
                String heightR = rst.getString("HEIGHT");

                //appointment informations are transmitted
                this.lbl_PatientpatientName.setText(patientName);
                this.lbl_PatientpatientSurname.setText(patientSurname);
                this.lbl_PatientIdentification.setText(identificationNumber);
                this.lbl_PatientPhoneNumber.setText(phoneNumber);
                this.lbl_PatientEmail.setText(emailR);
                this.lbl_PatientGender.setText(genderR);
                this.lbl_PatientBirthday.setText(birthdayR);
                this.lbl_PatientWeight.setText(weightR);
                this.lbl_PatientHeight.setText(heightR);

                //Class variable is assignned
                this.patientName = patientName;
                this.setPatientSurname(patientSurname);

                rst.close();
                stmt.close();
                conn.close();
                return;
            }

        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showDoctorInformation(String doctorName, String doctorSurname) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            String query = "SELECT * FROM TBL_USERS WHERE NAME = '" + doctorName + "'" + " AND SURNAME ='" + doctorSurname + "'";
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {

                doctorName = rst.getString("NAME");
                doctorSurname = rst.getString("SURNAME");
                String phoneNumber = rst.getString("PHONE_NUMBER");
                String emailR = rst.getString("E_MAIL");
                String genderR = rst.getString("GENDER");
                String birthdayR = rst.getString("BIRTHDAY");
                String department = rst.getString("DEPARTMENT");

                //appointment informations are transmitted
                this.lbl_DoctordoctorName.setText(doctorName);
                this.lbl_DoctordoctorSurname.setText(doctorSurname);
                this.lbl_DoctorPhoneNumber.setText(phoneNumber);
                this.lbl_DoctorEmail.setText(emailR);
                this.lbl_DoctorGender.setText(genderR);
                this.lbl_DoctorBirthday.setText(birthdayR);
                this.lbl_DoctorDepartment.setText(department);

                //Class variable is assignned
                this.doctorName = doctorName;
                this.doctorSurname = doctorSurname;
                rst.close();
                stmt.close();
                conn.close();
                return;
            }

        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showImages() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            String query = "SELECT * FROM TBL_USERS_IMAGES WHERE NAME = '" + getDoctorName() + "'" + " AND SURNAME ='" + getDoctorSurname() + "'";
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {
                byte[] bytes = rst.getBytes("SIGNATURE");
                ImageIcon icon = new ImageIcon(bytes);
                byte[] bytes2 = rst.getBytes("IMAGE");
                ImageIcon icon2 = new ImageIcon(bytes2);
                lbl_doctorImage.setIcon(icon2);
                lbl_doctorSignature.setIcon(icon);
            }
            rst.close();

            query = "SELECT * FROM TBL_USERS_IMAGES WHERE NAME = '" + getPatientName() + "'" + " AND SURNAME ='" + getPatientSurname() + "'";
            ResultSet rst2 = stmt.executeQuery(query);
            while (rst2.next()) {
                byte[] bytes3 = rst2.getBytes("SIGNATURE");
                ImageIcon icon3 = new ImageIcon(bytes3);
                byte[] bytes4 = rst2.getBytes("IMAGE");
                ImageIcon icon4 = new ImageIcon(bytes4);
                lbl_patientImage.setIcon(icon4);
                lbl_patientSignature.setIcon(icon3);

            }
            rst2.close();
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
        tbpnl_appointmentInformations = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        pnl_patient = new javax.swing.JPanel();
        lbl_patientNameTag = new javax.swing.JLabel();
        lbl_patientSurnameTag = new javax.swing.JLabel();
        lbl_patientIdentificationNumberTag = new javax.swing.JLabel();
        lbl_patientName = new javax.swing.JLabel();
        lbl_patientSurname = new javax.swing.JLabel();
        lbl_patientIdentificationNumber = new javax.swing.JLabel();
        pnl_doctor = new javax.swing.JPanel();
        lbl_doctorNameTag = new javax.swing.JLabel();
        lbl_doctorSurnameTag = new javax.swing.JLabel();
        lbl_doctorName = new javax.swing.JLabel();
        lbl_doctorSurname = new javax.swing.JLabel();
        pnl_appointmentDate = new javax.swing.JPanel();
        lbl_appointmentDayTag = new javax.swing.JLabel();
        lbl_appointmentTimeTag = new javax.swing.JLabel();
        lbl_appointmentDay = new javax.swing.JLabel();
        lbl_appointmentTime = new javax.swing.JLabel();
        pnl_complaint = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txta_complaint = new javax.swing.JTextArea();
        btn_presricption = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        pnl_Doctordoctor = new javax.swing.JPanel();
        lbl_DoctordoctorNameTag = new javax.swing.JLabel();
        lbl_DoctordoctorSurnameTag = new javax.swing.JLabel();
        lbl_DoctordoctorName = new javax.swing.JLabel();
        lbl_DoctordoctorSurname = new javax.swing.JLabel();
        lbl_DoctorPhoneNumberTag = new javax.swing.JLabel();
        lbl_DoctorPhoneNumber = new javax.swing.JLabel();
        lbl_DoctorEmailTag = new javax.swing.JLabel();
        lbl_DoctorEmail = new javax.swing.JLabel();
        lbl_DoctorGenderTag = new javax.swing.JLabel();
        lbl_DoctorGender = new javax.swing.JLabel();
        lbl_DoctorBirthday = new javax.swing.JLabel();
        lbl_DoctorBirthdayTag1 = new javax.swing.JLabel();
        lbl_DoctorDepartmentTag = new javax.swing.JLabel();
        lbl_DoctorDepartment = new javax.swing.JLabel();
        pnl_DoctorSignature = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pnl_DoctorSignatureShow = new javax.swing.JPanel();
        lbl_doctorSignature = new javax.swing.JLabel();
        pnl_DoctorImage = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pnl_DoctorImageShow = new javax.swing.JPanel();
        lbl_doctorImage = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        pnl_PatientImage = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        pnl_PatientImageShow = new javax.swing.JPanel();
        lbl_patientImage = new javax.swing.JLabel();
        pnl_PatientSignature = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        pnl_PatientSignatureShow = new javax.swing.JPanel();
        lbl_patientSignature = new javax.swing.JLabel();
        pnl_Patientpatient = new javax.swing.JPanel();
        lbl_PatientpatientNameTag = new javax.swing.JLabel();
        lbl_PatientpatientSurnameTag = new javax.swing.JLabel();
        lbl_PatientpatientName = new javax.swing.JLabel();
        lbl_PatientpatientSurname = new javax.swing.JLabel();
        lbl_PatientPhoneNumberTag = new javax.swing.JLabel();
        lbl_PatientPhoneNumber = new javax.swing.JLabel();
        lbl_PatientEmailTag = new javax.swing.JLabel();
        lbl_PatientEmail = new javax.swing.JLabel();
        lbl_PatientGenderTag = new javax.swing.JLabel();
        lbl_PatientGender = new javax.swing.JLabel();
        lbl_PatientBirthday = new javax.swing.JLabel();
        lbl_PatientBirthdayTag = new javax.swing.JLabel();
        lbl_PatientIdentificationTag = new javax.swing.JLabel();
        lbl_PatientIdentification = new javax.swing.JLabel();
        lbl_PatientWeightTag = new javax.swing.JLabel();
        lbl_PatientWeight = new javax.swing.JLabel();
        lbl_PatientHeightTag = new javax.swing.JLabel();
        lbl_PatientHeight = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        jPanel1.setBackground(new java.awt.Color(0, 204, 102));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jPanel2.setBackground(new java.awt.Color(0, 204, 51));

        pnl_patient.setBackground(new java.awt.Color(0, 204, 51));
        pnl_patient.setBorder(javax.swing.BorderFactory.createTitledBorder("Patient"));

        lbl_patientNameTag.setText("Name:");

        lbl_patientSurnameTag.setText("Surname:");

        lbl_patientIdentificationNumberTag.setText("Identification Number:");

        javax.swing.GroupLayout pnl_patientLayout = new javax.swing.GroupLayout(pnl_patient);
        pnl_patient.setLayout(pnl_patientLayout);
        pnl_patientLayout.setHorizontalGroup(
            pnl_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_patientLayout.createSequentialGroup()
                .addGroup(pnl_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_patientLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_patientLayout.createSequentialGroup()
                                .addComponent(lbl_patientNameTag)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_patientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnl_patientLayout.createSequentialGroup()
                                .addComponent(lbl_patientSurnameTag)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_patientSurname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(pnl_patientLayout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(pnl_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_patientIdentificationNumberTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_patientIdentificationNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 38, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_patientLayout.setVerticalGroup(
            pnl_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_patientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_patientNameTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_patientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbl_patientSurnameTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_patientSurname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12)
                .addComponent(lbl_patientIdentificationNumberTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_patientIdentificationNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(6, 6, 6))
        );

        pnl_doctor.setBackground(new java.awt.Color(0, 204, 51));
        pnl_doctor.setBorder(javax.swing.BorderFactory.createTitledBorder("Doctor"));

        lbl_doctorNameTag.setText("Name:");

        lbl_doctorSurnameTag.setText("Surname:");

        javax.swing.GroupLayout pnl_doctorLayout = new javax.swing.GroupLayout(pnl_doctor);
        pnl_doctor.setLayout(pnl_doctorLayout);
        pnl_doctorLayout.setHorizontalGroup(
            pnl_doctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_doctorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_doctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_doctorName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbl_doctorSurname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(pnl_doctorLayout.createSequentialGroup()
                .addGroup(pnl_doctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_doctorLayout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addComponent(lbl_doctorSurnameTag))
                    .addGroup(pnl_doctorLayout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(lbl_doctorNameTag)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        pnl_doctorLayout.setVerticalGroup(
            pnl_doctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_doctorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_doctorNameTag)
                .addGap(9, 9, 9)
                .addComponent(lbl_doctorName, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_doctorSurnameTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_doctorSurname, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_appointmentDate.setBackground(new java.awt.Color(0, 204, 51));
        pnl_appointmentDate.setBorder(javax.swing.BorderFactory.createTitledBorder("Appointment Date"));

        lbl_appointmentDayTag.setText("Appointment Day:");

        lbl_appointmentTimeTag.setText("Time:");

        javax.swing.GroupLayout pnl_appointmentDateLayout = new javax.swing.GroupLayout(pnl_appointmentDate);
        pnl_appointmentDate.setLayout(pnl_appointmentDateLayout);
        pnl_appointmentDateLayout.setHorizontalGroup(
            pnl_appointmentDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_appointmentDateLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_appointmentDayTag, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_appointmentDay, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_appointmentTimeTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_appointmentTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_appointmentDateLayout.setVerticalGroup(
            pnl_appointmentDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_appointmentDateLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_appointmentDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_appointmentDay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnl_appointmentDateLayout.createSequentialGroup()
                        .addGroup(pnl_appointmentDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_appointmentDateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lbl_appointmentDayTag)
                                .addComponent(lbl_appointmentTimeTag))
                            .addComponent(lbl_appointmentTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnl_complaint.setBackground(new java.awt.Color(0, 204, 51));
        pnl_complaint.setBorder(javax.swing.BorderFactory.createTitledBorder("Complaint"));

        txta_complaint.setEditable(false);
        txta_complaint.setBackground(new java.awt.Color(0, 204, 153));
        txta_complaint.setColumns(20);
        txta_complaint.setRows(5);
        jScrollPane1.setViewportView(txta_complaint);

        javax.swing.GroupLayout pnl_complaintLayout = new javax.swing.GroupLayout(pnl_complaint);
        pnl_complaint.setLayout(pnl_complaintLayout);
        pnl_complaintLayout.setHorizontalGroup(
            pnl_complaintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_complaintLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnl_complaintLayout.setVerticalGroup(
            pnl_complaintLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_complaintLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                .addContainerGap())
        );

        btn_presricption.setText("Extract");
        btn_presricption.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_presricptionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_presricption, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(pnl_patient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addComponent(pnl_doctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(pnl_complaint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnl_appointmentDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnl_patient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnl_doctor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(pnl_appointmentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnl_complaint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_presricption)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        tbpnl_appointmentInformations.addTab("Appointment Informations", jPanel2);

        jPanel3.setBackground(new java.awt.Color(0, 204, 204));

        pnl_Doctordoctor.setBackground(new java.awt.Color(0, 204, 204));
        pnl_Doctordoctor.setBorder(javax.swing.BorderFactory.createTitledBorder("Doctor"));

        lbl_DoctordoctorNameTag.setText("Name:");

        lbl_DoctordoctorSurnameTag.setText("Surname:");

        lbl_DoctorPhoneNumberTag.setText("Phone Number:");

        lbl_DoctorEmailTag.setText("E-Mail:");

        lbl_DoctorGenderTag.setText("Gender:");

        lbl_DoctorBirthdayTag1.setText("Birthday:");

        lbl_DoctorDepartmentTag.setText("Department:");

        javax.swing.GroupLayout pnl_DoctordoctorLayout = new javax.swing.GroupLayout(pnl_Doctordoctor);
        pnl_Doctordoctor.setLayout(pnl_DoctordoctorLayout);
        pnl_DoctordoctorLayout.setHorizontalGroup(
            pnl_DoctordoctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                .addGroup(pnl_DoctordoctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_DoctordoctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_DoctordoctorName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(lbl_DoctordoctorSurnameTag)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lbl_DoctordoctorSurname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_DoctordoctorLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(lbl_DoctorPhoneNumberTag)
                                .addGap(60, 60, 60))
                            .addComponent(lbl_DoctorPhoneNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_DoctorEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                                .addGroup(pnl_DoctordoctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addComponent(lbl_DoctorEmailTag))
                                    .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(lbl_DoctorGenderTag)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                        .addGroup(pnl_DoctordoctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(lbl_DoctordoctorNameTag))
                            .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_DoctorGender, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                            .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(lbl_DoctorBirthdayTag1))
                            .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_DoctorBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(lbl_DoctorDepartmentTag))
                            .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_DoctorDepartment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_DoctordoctorLayout.setVerticalGroup(
            pnl_DoctordoctorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DoctordoctorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_DoctordoctorNameTag)
                .addGap(9, 9, 9)
                .addComponent(lbl_DoctordoctorName, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctordoctorSurnameTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctordoctorSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctorPhoneNumberTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctorPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctorEmailTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctorEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctorGenderTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctorGender, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctorBirthdayTag1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctorBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctorDepartmentTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_DoctorDepartment, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnl_DoctorSignature.setBackground(new java.awt.Color(0, 204, 204));
        pnl_DoctorSignature.setBorder(javax.swing.BorderFactory.createTitledBorder("Signature"));

        jScrollPane2.setMinimumSize(new java.awt.Dimension(100, 100));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(509, 270));

        pnl_DoctorSignatureShow.setPreferredSize(new java.awt.Dimension(1000, 1000));

        lbl_doctorSignature.setMaximumSize(new java.awt.Dimension(1024, 1024));
        lbl_doctorSignature.setMinimumSize(new java.awt.Dimension(1024, 1024));
        lbl_doctorSignature.setPreferredSize(new java.awt.Dimension(1024, 1024));

        javax.swing.GroupLayout pnl_DoctorSignatureShowLayout = new javax.swing.GroupLayout(pnl_DoctorSignatureShow);
        pnl_DoctorSignatureShow.setLayout(pnl_DoctorSignatureShowLayout);
        pnl_DoctorSignatureShowLayout.setHorizontalGroup(
            pnl_DoctorSignatureShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DoctorSignatureShowLayout.createSequentialGroup()
                .addComponent(lbl_doctorSignature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_DoctorSignatureShowLayout.setVerticalGroup(
            pnl_DoctorSignatureShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DoctorSignatureShowLayout.createSequentialGroup()
                .addComponent(lbl_doctorSignature, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane2.setViewportView(pnl_DoctorSignatureShow);

        javax.swing.GroupLayout pnl_DoctorSignatureLayout = new javax.swing.GroupLayout(pnl_DoctorSignature);
        pnl_DoctorSignature.setLayout(pnl_DoctorSignatureLayout);
        pnl_DoctorSignatureLayout.setHorizontalGroup(
            pnl_DoctorSignatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DoctorSignatureLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_DoctorSignatureLayout.setVerticalGroup(
            pnl_DoctorSignatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DoctorSignatureLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_DoctorImage.setBackground(new java.awt.Color(0, 204, 204));
        pnl_DoctorImage.setBorder(javax.swing.BorderFactory.createTitledBorder("Image"));

        jScrollPane3.setMinimumSize(new java.awt.Dimension(100, 100));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(509, 270));

        pnl_DoctorImageShow.setPreferredSize(new java.awt.Dimension(1000, 1000));

        lbl_doctorImage.setMaximumSize(new java.awt.Dimension(1024, 1024));
        lbl_doctorImage.setMinimumSize(new java.awt.Dimension(1024, 1024));
        lbl_doctorImage.setPreferredSize(new java.awt.Dimension(1024, 1024));

        javax.swing.GroupLayout pnl_DoctorImageShowLayout = new javax.swing.GroupLayout(pnl_DoctorImageShow);
        pnl_DoctorImageShow.setLayout(pnl_DoctorImageShowLayout);
        pnl_DoctorImageShowLayout.setHorizontalGroup(
            pnl_DoctorImageShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DoctorImageShowLayout.createSequentialGroup()
                .addComponent(lbl_doctorImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_DoctorImageShowLayout.setVerticalGroup(
            pnl_DoctorImageShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DoctorImageShowLayout.createSequentialGroup()
                .addComponent(lbl_doctorImage, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(pnl_DoctorImageShow);

        javax.swing.GroupLayout pnl_DoctorImageLayout = new javax.swing.GroupLayout(pnl_DoctorImage);
        pnl_DoctorImage.setLayout(pnl_DoctorImageLayout);
        pnl_DoctorImageLayout.setHorizontalGroup(
            pnl_DoctorImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DoctorImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_DoctorImageLayout.setVerticalGroup(
            pnl_DoctorImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_DoctorImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(pnl_Doctordoctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_DoctorSignature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_DoctorImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnl_Doctordoctor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(pnl_DoctorSignature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl_DoctorImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        tbpnl_appointmentInformations.addTab("Doctor Informations", jPanel3);

        jPanel4.setBackground(new java.awt.Color(0, 204, 153));

        pnl_PatientImage.setBackground(new java.awt.Color(0, 204, 153));
        pnl_PatientImage.setBorder(javax.swing.BorderFactory.createTitledBorder("Image"));

        jScrollPane4.setMinimumSize(new java.awt.Dimension(100, 100));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(509, 270));

        pnl_PatientImageShow.setPreferredSize(new java.awt.Dimension(1000, 1000));

        lbl_patientImage.setMaximumSize(new java.awt.Dimension(1024, 1024));
        lbl_patientImage.setMinimumSize(new java.awt.Dimension(1024, 1024));
        lbl_patientImage.setPreferredSize(new java.awt.Dimension(1024, 1024));

        javax.swing.GroupLayout pnl_PatientImageShowLayout = new javax.swing.GroupLayout(pnl_PatientImageShow);
        pnl_PatientImageShow.setLayout(pnl_PatientImageShowLayout);
        pnl_PatientImageShowLayout.setHorizontalGroup(
            pnl_PatientImageShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_PatientImageShowLayout.createSequentialGroup()
                .addComponent(lbl_patientImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_PatientImageShowLayout.setVerticalGroup(
            pnl_PatientImageShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_PatientImageShowLayout.createSequentialGroup()
                .addComponent(lbl_patientImage, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane4.setViewportView(pnl_PatientImageShow);

        javax.swing.GroupLayout pnl_PatientImageLayout = new javax.swing.GroupLayout(pnl_PatientImage);
        pnl_PatientImage.setLayout(pnl_PatientImageLayout);
        pnl_PatientImageLayout.setHorizontalGroup(
            pnl_PatientImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_PatientImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_PatientImageLayout.setVerticalGroup(
            pnl_PatientImageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_PatientImageLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_PatientSignature.setBackground(new java.awt.Color(0, 204, 153));
        pnl_PatientSignature.setBorder(javax.swing.BorderFactory.createTitledBorder("Signature"));

        jScrollPane5.setMinimumSize(new java.awt.Dimension(100, 100));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(509, 270));

        pnl_PatientSignatureShow.setPreferredSize(new java.awt.Dimension(1000, 1000));

        lbl_patientSignature.setMaximumSize(new java.awt.Dimension(1024, 1024));
        lbl_patientSignature.setMinimumSize(new java.awt.Dimension(1024, 1024));
        lbl_patientSignature.setPreferredSize(new java.awt.Dimension(1024, 1024));

        javax.swing.GroupLayout pnl_PatientSignatureShowLayout = new javax.swing.GroupLayout(pnl_PatientSignatureShow);
        pnl_PatientSignatureShow.setLayout(pnl_PatientSignatureShowLayout);
        pnl_PatientSignatureShowLayout.setHorizontalGroup(
            pnl_PatientSignatureShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_PatientSignatureShowLayout.createSequentialGroup()
                .addComponent(lbl_patientSignature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_PatientSignatureShowLayout.setVerticalGroup(
            pnl_PatientSignatureShowLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_PatientSignatureShowLayout.createSequentialGroup()
                .addComponent(lbl_patientSignature, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(pnl_PatientSignatureShow);

        javax.swing.GroupLayout pnl_PatientSignatureLayout = new javax.swing.GroupLayout(pnl_PatientSignature);
        pnl_PatientSignature.setLayout(pnl_PatientSignatureLayout);
        pnl_PatientSignatureLayout.setHorizontalGroup(
            pnl_PatientSignatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_PatientSignatureLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnl_PatientSignatureLayout.setVerticalGroup(
            pnl_PatientSignatureLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_PatientSignatureLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnl_Patientpatient.setBackground(new java.awt.Color(0, 204, 153));
        pnl_Patientpatient.setBorder(javax.swing.BorderFactory.createTitledBorder("Doctor"));

        lbl_PatientpatientNameTag.setText("Name:");

        lbl_PatientpatientSurnameTag.setText("Surname:");

        lbl_PatientPhoneNumberTag.setText("Phone Number:");

        lbl_PatientEmailTag.setText("E-Mail:");

        lbl_PatientGenderTag.setText("Gender:");

        lbl_PatientBirthdayTag.setText("Birthday:");

        lbl_PatientIdentificationTag.setText("Identification Number:");

        lbl_PatientWeightTag.setText("Weight:");

        lbl_PatientHeightTag.setText("Height:");

        javax.swing.GroupLayout pnl_PatientpatientLayout = new javax.swing.GroupLayout(pnl_Patientpatient);
        pnl_Patientpatient.setLayout(pnl_PatientpatientLayout);
        pnl_PatientpatientLayout.setHorizontalGroup(
            pnl_PatientpatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                .addGroup(pnl_PatientpatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnl_PatientpatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_PatientpatientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                .addGap(64, 64, 64)
                                .addComponent(lbl_PatientpatientSurnameTag)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(lbl_PatientpatientSurname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_PatientpatientLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(lbl_PatientPhoneNumberTag)
                                .addGap(60, 60, 60))
                            .addComponent(lbl_PatientPhoneNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_PatientEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                .addGroup(pnl_PatientpatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                        .addGap(69, 69, 69)
                                        .addComponent(lbl_PatientEmailTag))
                                    .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                        .addGap(67, 67, 67)
                                        .addComponent(lbl_PatientGenderTag)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                        .addGroup(pnl_PatientpatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(lbl_PatientpatientNameTag))
                            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_PatientGender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(lbl_PatientBirthdayTag))
                            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_PatientBirthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_PatientIdentification, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
                            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(lbl_PatientIdentificationTag))
                            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbl_PatientWeightTag)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_PatientWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_PatientHeightTag)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_PatientHeight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_PatientpatientLayout.setVerticalGroup(
            pnl_PatientpatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_PatientpatientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_PatientpatientNameTag)
                .addGap(9, 9, 9)
                .addComponent(lbl_PatientpatientName, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientpatientSurnameTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientpatientSurname, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientPhoneNumberTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientEmailTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientGenderTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientGender, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientBirthdayTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientBirthday, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientIdentificationTag)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_PatientIdentification, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_PatientpatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_PatientHeightTag)
                    .addComponent(lbl_PatientWeight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnl_PatientpatientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_PatientWeightTag)
                        .addComponent(lbl_PatientHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(pnl_Patientpatient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnl_PatientSignature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnl_PatientImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnl_Patientpatient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(pnl_PatientSignature, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnl_PatientImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(80, Short.MAX_VALUE))
        );

        tbpnl_appointmentInformations.addTab("Patient Informations", jPanel4);

        jPanel1.add(tbpnl_appointmentInformations);

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_presricptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_presricptionActionPerformed
        ExtractAppointmentInformations scr = new ExtractAppointmentInformations(this);
        scr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scr.setVisible(true);

    }//GEN-LAST:event_btn_presricptionActionPerformed

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
            java.util.logging.Logger.getLogger(AppointmentInformations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppointmentInformations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppointmentInformations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppointmentInformations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AppointmentInformations().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_presricption;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lbl_DoctorBirthday;
    private javax.swing.JLabel lbl_DoctorBirthdayTag1;
    private javax.swing.JLabel lbl_DoctorDepartment;
    private javax.swing.JLabel lbl_DoctorDepartmentTag;
    private javax.swing.JLabel lbl_DoctorEmail;
    private javax.swing.JLabel lbl_DoctorEmailTag;
    private javax.swing.JLabel lbl_DoctorGender;
    private javax.swing.JLabel lbl_DoctorGenderTag;
    private javax.swing.JLabel lbl_DoctorPhoneNumber;
    private javax.swing.JLabel lbl_DoctorPhoneNumberTag;
    private javax.swing.JLabel lbl_DoctordoctorName;
    private javax.swing.JLabel lbl_DoctordoctorNameTag;
    private javax.swing.JLabel lbl_DoctordoctorSurname;
    private javax.swing.JLabel lbl_DoctordoctorSurnameTag;
    private javax.swing.JLabel lbl_PatientBirthday;
    private javax.swing.JLabel lbl_PatientBirthdayTag;
    private javax.swing.JLabel lbl_PatientEmail;
    private javax.swing.JLabel lbl_PatientEmailTag;
    private javax.swing.JLabel lbl_PatientGender;
    private javax.swing.JLabel lbl_PatientGenderTag;
    private javax.swing.JLabel lbl_PatientHeight;
    private javax.swing.JLabel lbl_PatientHeightTag;
    private javax.swing.JLabel lbl_PatientIdentification;
    private javax.swing.JLabel lbl_PatientIdentificationTag;
    private javax.swing.JLabel lbl_PatientPhoneNumber;
    private javax.swing.JLabel lbl_PatientPhoneNumberTag;
    private javax.swing.JLabel lbl_PatientWeight;
    private javax.swing.JLabel lbl_PatientWeightTag;
    private javax.swing.JLabel lbl_PatientpatientName;
    private javax.swing.JLabel lbl_PatientpatientNameTag;
    private javax.swing.JLabel lbl_PatientpatientSurname;
    private javax.swing.JLabel lbl_PatientpatientSurnameTag;
    private javax.swing.JLabel lbl_appointmentDay;
    private javax.swing.JLabel lbl_appointmentDayTag;
    private javax.swing.JLabel lbl_appointmentTime;
    private javax.swing.JLabel lbl_appointmentTimeTag;
    private javax.swing.JLabel lbl_doctorImage;
    private javax.swing.JLabel lbl_doctorName;
    private javax.swing.JLabel lbl_doctorNameTag;
    private javax.swing.JLabel lbl_doctorSignature;
    private javax.swing.JLabel lbl_doctorSurname;
    private javax.swing.JLabel lbl_doctorSurnameTag;
    private javax.swing.JLabel lbl_patientIdentificationNumber;
    private javax.swing.JLabel lbl_patientIdentificationNumberTag;
    private javax.swing.JLabel lbl_patientImage;
    private javax.swing.JLabel lbl_patientName;
    private javax.swing.JLabel lbl_patientNameTag;
    private javax.swing.JLabel lbl_patientSignature;
    private javax.swing.JLabel lbl_patientSurname;
    private javax.swing.JLabel lbl_patientSurnameTag;
    private javax.swing.JPanel pnl_DoctorImage;
    private javax.swing.JPanel pnl_DoctorImageShow;
    private javax.swing.JPanel pnl_DoctorSignature;
    private javax.swing.JPanel pnl_DoctorSignatureShow;
    private javax.swing.JPanel pnl_Doctordoctor;
    private javax.swing.JPanel pnl_PatientImage;
    private javax.swing.JPanel pnl_PatientImageShow;
    private javax.swing.JPanel pnl_PatientSignature;
    private javax.swing.JPanel pnl_PatientSignatureShow;
    private javax.swing.JPanel pnl_Patientpatient;
    private javax.swing.JPanel pnl_appointmentDate;
    private javax.swing.JPanel pnl_complaint;
    private javax.swing.JPanel pnl_doctor;
    private javax.swing.JPanel pnl_patient;
    private javax.swing.JTabbedPane tbpnl_appointmentInformations;
    private javax.swing.JTextArea txta_complaint;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the lbl_appointmentDay
     */
    /**
     * @return the complaint
     */
    public String getComplaint() {
        return complaint;
    }

    /**
     * @return the doctorName
     */
    public String getDoctorName() {
        return doctorName;
    }

    /**
     * @return the doctorSurname
     */
    public String getDoctorSurname() {
        return doctorSurname;
    }

    /**
     * @return the patientName
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * @return the patientSurname
     */
    public String getPatientSurname() {
        return patientSurname;
    }

    /**
     * @param patientSurname the patientSurname to set
     */
    public void setPatientSurname(String patientSurname) {
        this.patientSurname = patientSurname;
    }

    /**
     * @return the identificationNumber
     */
    public String getIdentificationNumber() {
        return identificationNumber;
    }

    /**
     * @return the appointmentDate
     */
    public String getAppointmentDate() {
        return appointmentDate;
    }

    /**
     * @return the appointmentTime
     */
    public String getAppointmentTime() {
        return appointmentTime;
    }

    /**
     * @return the doctorDepartment
     */
    public String getDoctorDepartment() {
        return doctorDepartment;
    }
}
