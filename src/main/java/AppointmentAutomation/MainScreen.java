package AppointmentAutomation;

import com.sun.org.glassfish.external.statistics.Statistic;
import java.awt.event.ActionEvent;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Bilal
 */
public class MainScreen extends javax.swing.JFrame {

    private DefaultTableModel tbl_appointmentsModel;
    private DefaultTableModel tbl_appintmentsSearchModel;
    private String userName;
    private String userSurname;
    private String userIdentificationNumber;
    private String selectedId;
    private String userType;
    private int clickCount;//this variable uses to create AppointmentInformation
    private int selectedRow;//this variable uses to create AppointmentInformation
    private String filename;

    public MainScreen() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Displayer of Appointments");
        //TriangleButton which is customized button is created
        TriangleButton differentButton = new TriangleButton();

        differentButton.setLocation(650, 25);
        differentButton.setVisible(true);
        pnl_tools.add(differentButton);

        differentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JOptionPane.showMessageDialog(rootPane, "This Project is made by Samil Bilal OZAYDIN ");
            }
        });
        //User informations are transmitted
        this.lbl_birthday.setText(LogIn.getBirthday());
        this.lbl_department.setText(LogIn.getDepartment());
        this.lbl_email.setText(LogIn.getEmail());
        this.lbl_gender.setText(LogIn.getGender());
        this.lbl_height.setText(LogIn.getUserheight());
        this.lbl_identificationNumber.setText(LogIn.getIdentificationNumber());
        this.lbl_name.setText(LogIn.getPersonalName());
        this.lbl_phoneNumber.setText(LogIn.getPhoneNumber());
        this.lbl_surname.setText(LogIn.getSurname());
        this.lbl_userType.setText(LogIn.getUserType());
        this.lbl_weight.setText(LogIn.getWeight());

        //Appointments table is created
        tbl_appintmentsSearchModel = new DefaultTableModel();
        tbl_appintmentsSearchModel.setColumnIdentifiers(new Object[]{"Id", "Doctor Name", "Doctor Surname", "Appointment Date",
            "Appointment Time", "Doctor Department", "Patient Name", "Patient Surname", "Patient Identification Number", "Complaint"});
        tbl_appointmentsModel = new DefaultTableModel();
        tbl_appointmentsModel.setColumnIdentifiers(new Object[]{"Id", "Doctor Name", "Doctor Surname", "Appointment Date",
            "Appointment Time", "Doctor Department", "Patient Name", "Patient Surname", "Patient Identification Number", "Complaint"});

        this.tbl_appointments.setModel(tbl_appointmentsModel);
        //According to user type, user can see appointments. 
        boolean isStandartUser = this.lbl_userType.getText().equals("Standart user");
        boolean isDoctor = this.lbl_userType.getText().equals("Doctor");
        String name = this.lbl_name.getText();
        String surname = this.lbl_surname.getText();

        //assinging class variables
        this.userName = lbl_name.getText();
        this.userSurname = lbl_surname.getText();
        this.userIdentificationNumber = lbl_identificationNumber.getText();
        this.userType = lbl_userType.getText();
        this.selectedRow = -1;

        //Following,user's appointments are shown.        
        showAppointments(name, surname, isStandartUser, isDoctor);
        //User signature is displayed
        showSignature();
    }

    public void showAppointments(String name, String surname, boolean isStandartUser, boolean isDoctor) {
        Connection conn = null;
        //Specifying which column is selected.
        String selectColumn = "";
        String selectColumn2 = "";
        String selectColumn3 = "";
        String selectColumn4 = "";
        if (isStandartUser) {
            selectColumn = "PATIENT_NAME";
            selectColumn2 = "PATIENT_SURNAME";
        } else if (isDoctor) {
            selectColumn = "DOCTOR_NAME";
            selectColumn2 = "DOCTOR_SURNAME";
            selectColumn3 = "PATIENT_NAME";
            selectColumn4 = "PATIENT_SURNAME";
        }
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            String query = "SELECT * FROM TBL_APPOINTMENTS WHERE " + selectColumn + "= '" + name + "'" + "AND " + selectColumn2 + "=" + "'" + surname + "'";//User is a patient

            if (isDoctor) {//User is a doctor
                query = "SELECT * FROM TBL_APPOINTMENTS WHERE (" + selectColumn + "= '" + name + "'" + "AND " + selectColumn2 + "=" + "'" + surname + "') OR ("
                        + selectColumn3 + "= '" + name + "'" + "AND " + selectColumn4 + "=" + "'" + surname + "')";
            }
            if (!(isDoctor || isStandartUser)) {//The condition of user is not doctor or standart user.
                query = "SELECT * FROM TBL_APPOINTMENTS";
            }

            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {//all of informaiton about user is displayed below.

                String Id = rst.getString("ID");
                String doctorName = rst.getString("DOCTOR_NAME");
                String doctorSurname = rst.getString("DOCTOR_SURNAME");
                String appointmentDate = rst.getString("APPOINTMENT_DATE");
                String appointmentTime = rst.getString("APPOINTMENT_TIME");
                String doctorDepartment = rst.getString("DOCTOR_DEPARTMENT");
                String patientName = rst.getString("PATIENT_NAME");
                String patientSurname = rst.getString("PATIENT_SURNAME");
                String identificationNumber = rst.getString("PATIENT_ID");
                String complaint = rst.getString("COMPLAINT");
                this.getTbl_appointmentsModel().addRow(new Object[]{Id, doctorName, doctorSurname, appointmentDate,
                    appointmentTime, doctorDepartment, patientName, patientSurname, identificationNumber, complaint});

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

    public void showSignature() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            String query = "SELECT * FROM TBL_USERS_IMAGES WHERE ID= " + LogIn.getId();
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(query);

            while (rst.next()) {
                byte[] bytes = rst.getBytes("SIGNATURE");
                ImageIcon icon = new ImageIcon(bytes);
                this.lbl_image.setIcon(icon);
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

        popup_appointmentsSection = new javax.swing.JPopupMenu();
        mItem_changeColorAppointmentsSection = new javax.swing.JMenuItem();
        popup_userInformations = new javax.swing.JPopupMenu();
        mItem_colorChangeUserInformation = new javax.swing.JMenuItem();
        popup_userSettings = new javax.swing.JPopupMenu();
        mItem_colorChangeUserSettings = new javax.swing.JMenuItem();
        jSplitPane1 = new javax.swing.JSplitPane();
        pnl_appoinmentSection = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_appointments = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txt_search = new javax.swing.JTextField();
        cmbx_search = new javax.swing.JComboBox<>();
        btn_search = new javax.swing.JButton();
        btn_resetTable = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        pnl_tools = new javax.swing.JPanel();
        btn_makeAppointment = new javax.swing.JButton();
        btn_updateAppointment = new javax.swing.JButton();
        btn_deleteAppointment = new javax.swing.JButton();
        btn_selectedAppointment = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();
        btn_statistics = new javax.swing.JButton();
        btn_printAppointments = new javax.swing.JButton();
        pnl_userInformation = new javax.swing.JPanel();
        lbl_nameTag = new javax.swing.JLabel();
        lbl_name = new javax.swing.JLabel();
        lbl_surnameTag = new javax.swing.JLabel();
        lbl_surname = new javax.swing.JLabel();
        lbl_userTypeTag = new javax.swing.JLabel();
        lbl_userType = new javax.swing.JLabel();
        lbl_genderTag = new javax.swing.JLabel();
        lbl_gender = new javax.swing.JLabel();
        lbl_birthdayTag = new javax.swing.JLabel();
        lbl_birthday = new javax.swing.JLabel();
        lbl_departmentTag = new javax.swing.JLabel();
        lbl_weightTag = new javax.swing.JLabel();
        lbl_weight = new javax.swing.JLabel();
        lbl_heightTag = new javax.swing.JLabel();
        lbl_height = new javax.swing.JLabel();
        lbl_department = new javax.swing.JLabel();
        lbl_phoneNumberTag = new javax.swing.JLabel();
        lbl_phoneNumber = new javax.swing.JLabel();
        lbl_emailTag = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        lbl_identificationNumberTag = new javax.swing.JLabel();
        lbl_identificationNumber = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        lbl_image = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mitem_fileAddAnImage = new javax.swing.JMenuItem();
        mitem_fileAddAnSignature = new javax.swing.JMenuItem();
        mItem_exit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        mItem_newAppointment = new javax.swing.JMenuItem();
        mItem_UpdateAppointment = new javax.swing.JMenuItem();
        mItem_deleteAppointment = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mItem_showSelected = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        mItem_changeColorRandomly = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menu_about = new javax.swing.JMenu();

        mItem_changeColorAppointmentsSection.setText("Change Color of This Panel");
        mItem_changeColorAppointmentsSection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItem_changeColorAppointmentsSectionActionPerformed(evt);
            }
        });
        popup_appointmentsSection.add(mItem_changeColorAppointmentsSection);

        mItem_colorChangeUserInformation.setText("Change Color of This Panel");
        mItem_colorChangeUserInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItem_colorChangeUserInformationActionPerformed(evt);
            }
        });
        popup_userInformations.add(mItem_colorChangeUserInformation);

        mItem_colorChangeUserSettings.setText("Change Color of This Panel");
        mItem_colorChangeUserSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItem_colorChangeUserSettingsActionPerformed(evt);
            }
        });
        popup_userSettings.add(mItem_colorChangeUserSettings);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnl_appoinmentSection.setBackground(new java.awt.Color(255, 102, 102));
        pnl_appoinmentSection.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Appointments", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        pnl_appoinmentSection.setComponentPopupMenu(popup_appointmentsSection);

        jPanel1.setPreferredSize(new java.awt.Dimension(2000, 1000));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        tbl_appointments.setRowSelectionAllowed(true);
        tbl_appointments.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_appointmentsMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_appointments);

        jPanel1.add(jScrollPane2);

        jScrollPane3.setViewportView(jPanel1);

        jLabel1.setText("Search:");

        cmbx_search.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Id", "Doctor Name", "Doctor Surname", "Appointment Date", "Patient Name", "Patient Surname", "Patient Identification Number" }));

        btn_search.setText("Search");
        btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_searchActionPerformed(evt);
            }
        });

        btn_resetTable.setText("Reset Table");
        btn_resetTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_appoinmentSectionLayout = new javax.swing.GroupLayout(pnl_appoinmentSection);
        pnl_appoinmentSection.setLayout(pnl_appoinmentSectionLayout);
        pnl_appoinmentSectionLayout.setHorizontalGroup(
            pnl_appoinmentSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_appoinmentSectionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 952, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(pnl_appoinmentSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_appoinmentSectionLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(pnl_appoinmentSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_search)
                            .addComponent(cmbx_search, 0, 246, Short.MAX_VALUE)
                            .addComponent(btn_search, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnl_appoinmentSectionLayout.createSequentialGroup()
                        .addGroup(pnl_appoinmentSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_appoinmentSectionLayout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(jLabel1))
                            .addGroup(pnl_appoinmentSectionLayout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(btn_resetTable)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_appoinmentSectionLayout.setVerticalGroup(
            pnl_appoinmentSectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_appoinmentSectionLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(pnl_appoinmentSectionLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbx_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_search)
                .addGap(36, 36, 36)
                .addComponent(btn_resetTable)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setBottomComponent(pnl_appoinmentSection);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane2.setDividerLocation(500);

        pnl_tools.setBackground(new java.awt.Color(153, 102, 255));
        pnl_tools.setBorder(javax.swing.BorderFactory.createTitledBorder("Appointments Settings"));
        pnl_tools.setComponentPopupMenu(popup_userSettings);

        btn_makeAppointment.setText("Make Appointment");
        btn_makeAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_makeAppointmentActionPerformed(evt);
            }
        });

        btn_updateAppointment.setText("Update Appointment");
        btn_updateAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateAppointmentActionPerformed(evt);
            }
        });

        btn_deleteAppointment.setText("Delete Selected Appointment");
        btn_deleteAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteAppointmentActionPerformed(evt);
            }
        });

        btn_selectedAppointment.setText("Selected Appo. Informations");
        btn_selectedAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_selectedAppointmentActionPerformed(evt);
            }
        });

        btn_exit.setText("Exit");
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });

        btn_statistics.setText("Statistics");
        btn_statistics.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_statisticsActionPerformed(evt);
            }
        });

        btn_printAppointments.setText("Print Appointments");
        btn_printAppointments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printAppointmentsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnl_toolsLayout = new javax.swing.GroupLayout(pnl_tools);
        pnl_tools.setLayout(pnl_toolsLayout);
        pnl_toolsLayout.setHorizontalGroup(
            pnl_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_toolsLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(pnl_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_makeAppointment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_updateAppointment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_deleteAppointment, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                    .addComponent(btn_selectedAppointment, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(pnl_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_toolsLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                        .addComponent(btn_exit, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))
                    .addGroup(pnl_toolsLayout.createSequentialGroup()
                        .addGap(76, 76, 76)
                        .addGroup(pnl_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_statistics, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_printAppointments, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        pnl_toolsLayout.setVerticalGroup(
            pnl_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_toolsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_makeAppointment)
                    .addComponent(btn_statistics))
                .addGap(18, 18, 18)
                .addGroup(pnl_toolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_updateAppointment)
                    .addComponent(btn_printAppointments))
                .addGap(18, 18, 18)
                .addComponent(btn_deleteAppointment)
                .addGap(18, 18, 18)
                .addComponent(btn_selectedAppointment)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(btn_exit)
                .addContainerGap())
        );

        jSplitPane2.setRightComponent(pnl_tools);

        pnl_userInformation.setBackground(new java.awt.Color(0, 204, 204));
        pnl_userInformation.setBorder(javax.swing.BorderFactory.createTitledBorder("User Informations"));
        pnl_userInformation.setComponentPopupMenu(popup_userInformations);

        lbl_nameTag.setText("Name:");

        lbl_surnameTag.setText("Surname:");

        lbl_userTypeTag.setForeground(new java.awt.Color(0, 0, 255));
        lbl_userTypeTag.setText("User Type:");

        lbl_genderTag.setText("Gender:");

        lbl_birthdayTag.setText("Birthday:");

        lbl_departmentTag.setForeground(new java.awt.Color(0, 0, 255));
        lbl_departmentTag.setText("Department:");

        lbl_weightTag.setText("Weight:");

        lbl_heightTag.setText("Height:");

        lbl_phoneNumberTag.setForeground(new java.awt.Color(204, 0, 0));
        lbl_phoneNumberTag.setText("Phone Number:");

        lbl_emailTag.setForeground(new java.awt.Color(204, 0, 0));
        lbl_emailTag.setText("E-mail:");

        lbl_identificationNumberTag.setForeground(new java.awt.Color(204, 0, 0));
        lbl_identificationNumberTag.setText("Identification Number:");

        jPanel5.setBackground(new java.awt.Color(102, 204, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Signature"));

        lbl_image.setMaximumSize(new java.awt.Dimension(1024, 1024));
        lbl_image.setMinimumSize(new java.awt.Dimension(1024, 1024));
        lbl_image.setPreferredSize(new java.awt.Dimension(1024, 1024));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(lbl_image, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel5);

        javax.swing.GroupLayout pnl_userInformationLayout = new javax.swing.GroupLayout(pnl_userInformation);
        pnl_userInformation.setLayout(pnl_userInformationLayout);
        pnl_userInformationLayout.setHorizontalGroup(
            pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_userInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_userInformationLayout.createSequentialGroup()
                        .addComponent(lbl_identificationNumberTag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_identificationNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pnl_userInformationLayout.createSequentialGroup()
                            .addComponent(lbl_emailTag)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lbl_email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(pnl_userInformationLayout.createSequentialGroup()
                            .addComponent(lbl_phoneNumberTag)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lbl_phoneNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)))
                    .addGroup(pnl_userInformationLayout.createSequentialGroup()
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnl_userInformationLayout.createSequentialGroup()
                                .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_birthdayTag)
                                    .addComponent(lbl_genderTag)
                                    .addComponent(lbl_weightTag)
                                    .addComponent(lbl_heightTag))
                                .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnl_userInformationLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_weight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lbl_height, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(pnl_userInformationLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pnl_userInformationLayout.createSequentialGroup()
                                                .addComponent(lbl_gender, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 25, Short.MAX_VALUE))
                                            .addComponent(lbl_birthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(pnl_userInformationLayout.createSequentialGroup()
                                .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_surnameTag)
                                    .addComponent(lbl_nameTag))
                                .addGap(13, 13, 13)
                                .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_surname, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_name, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(73, 73, 73)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_userInformationLayout.createSequentialGroup()
                        .addComponent(lbl_userTypeTag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_userType, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnl_userInformationLayout.createSequentialGroup()
                        .addComponent(lbl_departmentTag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_department, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );
        pnl_userInformationLayout.setVerticalGroup(
            pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_userInformationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnl_userInformationLayout.createSequentialGroup()
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_nameTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_surnameTag)
                            .addComponent(lbl_surname, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_genderTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_gender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_birthdayTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_birthday, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_weightTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_weight, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_heightTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_height, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_phoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_phoneNumberTag, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_userInformationLayout.createSequentialGroup()
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_email, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_emailTag, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lbl_identificationNumberTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbl_identificationNumber, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnl_userInformationLayout.createSequentialGroup()
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbl_userTypeTag)
                            .addComponent(lbl_userType, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnl_userInformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_departmentTag)
                            .addComponent(lbl_department, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane2.setLeftComponent(pnl_userInformation);

        jPanel2.add(jSplitPane2);

        jSplitPane1.setLeftComponent(jPanel2);

        getContentPane().add(jSplitPane1);

        jMenu1.setText("File");

        mitem_fileAddAnImage.setText("Add an Image");
        mitem_fileAddAnImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitem_fileAddAnImageActionPerformed(evt);
            }
        });
        jMenu1.add(mitem_fileAddAnImage);

        mitem_fileAddAnSignature.setText("Add an Signature");
        mitem_fileAddAnSignature.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mitem_fileAddAnSignatureActionPerformed(evt);
            }
        });
        jMenu1.add(mitem_fileAddAnSignature);

        mItem_exit.setText("Exit");
        mItem_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });
        jMenu1.add(mItem_exit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        mItem_newAppointment.setText("New Appointment");
        mItem_newAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_makeAppointmentActionPerformed(evt);
            }
        });
        jMenu2.add(mItem_newAppointment);

        mItem_UpdateAppointment.setText("Update Appointment");
        mItem_UpdateAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updateAppointmentActionPerformed(evt);
            }
        });
        jMenu2.add(mItem_UpdateAppointment);

        mItem_deleteAppointment.setText("Delete Selected Appointment");
        mItem_deleteAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteAppointmentActionPerformed(evt);
            }
        });
        jMenu2.add(mItem_deleteAppointment);
        jMenu2.add(jSeparator1);

        mItem_showSelected.setText("Show Selected Appointment");
        mItem_showSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_selectedAppointmentActionPerformed(evt);
            }
        });
        jMenu2.add(mItem_showSelected);
        jMenu2.add(jSeparator3);

        mItem_changeColorRandomly.setText("Change Background Randomly");
        mItem_changeColorRandomly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mItem_changeColorRandomlyActionPerformed(evt);
            }
        });
        jMenu2.add(mItem_changeColorRandomly);
        jMenu2.add(jSeparator2);

        jMenuBar1.add(jMenu2);

        menu_about.setText("About");
        menu_about.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                menu_aboutMousePressed(evt);
            }
        });
        jMenuBar1.add(menu_about);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_makeAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_makeAppointmentActionPerformed
        appointmentScreen(true);

    }//GEN-LAST:event_btn_makeAppointmentActionPerformed

    private void btn_updateAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updateAppointmentActionPerformed

        appointmentScreen(false);
    }//GEN-LAST:event_btn_updateAppointmentActionPerformed

    private void btn_deleteAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteAppointmentActionPerformed
        int selectedRow = tbl_appointments.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please be sure to select any appointment");
            return;
        }
        String selectedId = (String) tbl_appointments.getValueAt(selectedRow, 0);
        deleteAppointment(selectedId);
        this.tbl_appointmentsModel.removeRow(selectedRow);
    }//GEN-LAST:event_btn_deleteAppointmentActionPerformed

    private void tbl_appointmentsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_appointmentsMousePressed
        clickCount++;
        int selectedRowControl = tbl_appointments.getSelectedRow();
        if (selectedRow == -1 || selectedRow == selectedRowControl) {//control whether same row is selected or not
            selectedRow = selectedRowControl;
        } else {
            clickCount = 0;
        }
        if (SwingUtilities.isLeftMouseButton(evt) && clickCount == 2 && tbl_appointments.getSelectedRow() == selectedRow) {
            clickCount = 0;
            int selectedRowId = Integer.valueOf((String) tbl_appointmentsModel.getValueAt(selectedRow, 0));//Selected row ıd is gotten
            AppointmentInformations appointmentInfo = new AppointmentInformations(this, selectedRowId);
            selectedRow = -1;
            appointmentInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            appointmentInfo.setVisible(true);
        }
    }//GEN-LAST:event_tbl_appointmentsMousePressed

    private void btn_selectedAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_selectedAppointmentActionPerformed
        //selected appointment information is displayed
        int selectedRow = tbl_appointments.getSelectedRow();
        if (selectedRow == -1) {//Controlling any row is selected
            JOptionPane.showMessageDialog(this, "Please be sure to select any appointment");
            return;
        }
        String selectedRowID = (String) tbl_appointments.getValueAt(selectedRow, 0);
        int selectedRowId = Integer.valueOf(selectedRowID);
        AppointmentInformations appointmentInfo = new AppointmentInformations(this, selectedRowId);
        appointmentInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        appointmentInfo.setVisible(true);
    }//GEN-LAST:event_btn_selectedAppointmentActionPerformed

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        this.setVisible(false);
        LogIn newLogIn = new LogIn();
        newLogIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newLogIn.setVisible(true);

    }//GEN-LAST:event_btn_exitActionPerformed

    private void menu_aboutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menu_aboutMousePressed
        JOptionPane.showMessageDialog(this, "This Project is made by Samil Bilal OZAYDIN ");        // TODO add your handling code here:
    }//GEN-LAST:event_menu_aboutMousePressed

    private void mItem_changeColorRandomlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItem_changeColorRandomlyActionPerformed
        int digit = ((int) (Math.random() * 3.0));//one of panel is selected in here.
        ColorChanger colorChanger = new ColorChanger(this, digit);
        colorChanger.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        colorChanger.setVisible(true);
    }//GEN-LAST:event_mItem_changeColorRandomlyActionPerformed

    private void mItem_changeColorAppointmentsSectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItem_changeColorAppointmentsSectionActionPerformed
        ColorChanger colorChanger = new ColorChanger(this, 2);
        colorChanger.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        colorChanger.setVisible(true);
    }//GEN-LAST:event_mItem_changeColorAppointmentsSectionActionPerformed

    private void mItem_colorChangeUserInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItem_colorChangeUserInformationActionPerformed
        ColorChanger colorChanger = new ColorChanger(this, 0);
        colorChanger.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        colorChanger.setVisible(true);
    }//GEN-LAST:event_mItem_colorChangeUserInformationActionPerformed

    private void mItem_colorChangeUserSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mItem_colorChangeUserSettingsActionPerformed
        ColorChanger colorChanger = new ColorChanger(this, 1);
        colorChanger.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        colorChanger.setVisible(true);
    }//GEN-LAST:event_mItem_colorChangeUserSettingsActionPerformed

    private void btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_searchActionPerformed
        //this method find a certain appointment from table model
        this.tbl_appintmentsSearchModel.setRowCount(0);
        String searchMode = (String) cmbx_search.getSelectedItem();
        String search = txt_search.getText();

        int selectedColumn = this.tbl_appointmentsModel.findColumn(searchMode);//Selected colum number is gotten
        for (int i = 0; i < this.tbl_appointmentsModel.getRowCount(); i++) {//all of rows are searched
            String selectedString = (String) this.tbl_appointmentsModel.getValueAt(i, selectedColumn);
            if (selectedString.contains(search)) {
                String column1 = (String) tbl_appointmentsModel.getValueAt(i, 0);
                String column2 = (String) tbl_appointmentsModel.getValueAt(i, 1);
                String column3 = (String) tbl_appointmentsModel.getValueAt(i, 2);
                String column4 = (String) tbl_appointmentsModel.getValueAt(i, 3);
                String column5 = (String) tbl_appointmentsModel.getValueAt(i, 4);
                String column6 = (String) tbl_appointmentsModel.getValueAt(i, 5);
                String column7 = (String) tbl_appointmentsModel.getValueAt(i, 6);
                String column8 = (String) tbl_appointmentsModel.getValueAt(i, 7);
                String column9 = (String) tbl_appointmentsModel.getValueAt(i, 8);
                String column10 = (String) tbl_appointmentsModel.getValueAt(i, 9);
                this.tbl_appintmentsSearchModel.addRow(new Object[]{column1, column2, column3, column4,
                    column5, column6, column7, column8, column9, column10});
            }
        }
        this.tbl_appointments.setModel(tbl_appintmentsSearchModel);
    }//GEN-LAST:event_btn_searchActionPerformed

    private void btn_resetTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetTableActionPerformed
        this.tbl_appointments.setModel(tbl_appointmentsModel);
    }//GEN-LAST:event_btn_resetTableActionPerformed

    private void btn_statisticsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_statisticsActionPerformed
        Statistics statistic = new Statistics();
        statistic.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        statistic.setVisible(true);


    }//GEN-LAST:event_btn_statisticsActionPerformed

    private void btn_printAppointmentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printAppointmentsActionPerformed

        try {
            tbl_appointments.print();
        } catch (PrinterException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btn_printAppointmentsActionPerformed

    private void mitem_fileAddAnImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitem_fileAddAnImageActionPerformed
        executionOfFileChooser(false, "IMAGE");
    }//GEN-LAST:event_mitem_fileAddAnImageActionPerformed

    private void mitem_fileAddAnSignatureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mitem_fileAddAnSignatureActionPerformed
        executionOfFileChooser(true, "SIGNATURE");
    }//GEN-LAST:event_mitem_fileAddAnSignatureActionPerformed
    public void executionOfFileChooser(boolean isMaınScreenComponent, String where) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        filename = f.getAbsolutePath();

        if (isMaınScreenComponent) {//this section is created for signature panel
            ImageIcon imageIcon = new ImageIcon(filename);
            lbl_image.setIcon(imageIcon);
        }

        try {
            File image = new File(filename);
            FileInputStream istream = new FileInputStream(image);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numberReaded;
            while ((numberReaded = istream.read(buf)) != -1) {
                bos.write(buf, 0, numberReaded);
            }
            byte[] imageByte = bos.toByteArray();

            if (isExistedAlready()) {//Control that this user have an image
                insertImage(imageByte, where);//if user does not have image, this method is executed.
            } else {
                //update Ekle
                updateImage(imageByte, where);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MainScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isExistedAlready() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            String query = "SELECT * FROM TBL_USERS_IMAGES WHERE ID = " + LogIn.getId();
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(query);
            while (rst.next()) {
                String newId = "";
                newId = rst.getString("ID");
                if (!newId.isEmpty()) {
                    rst.close();
                    stmt.close();
                    conn.close();
                    return false;
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
        return true;
    }

    public void updateImage(byte[] imageByte, String column) {//image is updated in this method
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            //The inputs are inserted following
            String query = "UPDATE TBL_USERS_IMAGES SET " + column + " =? WHERE ID=?";// column is place where image is updated.
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(2, LogIn.getId());
            pst.setBytes(1, imageByte);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "The image is successfully saved. You can continue from main screen.");
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertImage(byte[] imageByte, String column) {//image is inserted in this method.
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            //The inputs are inserted following
            String query = "INSERT INTO TBL_USERS_IMAGES(ID,NAME,SURNAME," + column + ") VALUES(?,?,?,?)";// column is place where image is inserted.
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, LogIn.getId());
            pst.setString(2, LogIn.getPersonalName());
            pst.setString(3, LogIn.getSurname());
            pst.setBytes(4, imageByte);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "The image is successfully saved. You can continue from main screen.");
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(LogIn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAppointment(String id) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/AppoinmentProgram", "sa", "as");
            String query = "DELETE FROM TBL_APPOINTMENTS WHERE ID =" + id + "";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            JOptionPane.showMessageDialog(this, "The appointment is successfully deleted");
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

    public void appointmentScreen(boolean isNewAppointment) {
        String selectedId = "";
        if (!isNewAppointment) {
            int selectedRow = tbl_appointments.getSelectedRow();
            if (selectedRow == -1) {//Controlling any row is selected
                JOptionPane.showMessageDialog(this, "Please be sure to select any appointment");
                return;
            }
            selectedId = (String) tbl_appointments.getValueAt(selectedRow, 0);

        }
        //Creating appointment screen
        AppointmentScreen appointmentScreen = new AppointmentScreen(this, isNewAppointment, userName, userSurname, userIdentificationNumber, selectedId, userType);
        appointmentScreen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        appointmentScreen.setVisible(true);
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
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_deleteAppointment;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_makeAppointment;
    private javax.swing.JButton btn_printAppointments;
    private javax.swing.JButton btn_resetTable;
    private javax.swing.JButton btn_search;
    private javax.swing.JButton btn_selectedAppointment;
    private javax.swing.JButton btn_statistics;
    private javax.swing.JButton btn_updateAppointment;
    private javax.swing.JComboBox<String> cmbx_search;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JLabel lbl_birthday;
    private javax.swing.JLabel lbl_birthdayTag;
    private javax.swing.JLabel lbl_department;
    private javax.swing.JLabel lbl_departmentTag;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_emailTag;
    private javax.swing.JLabel lbl_gender;
    private javax.swing.JLabel lbl_genderTag;
    private javax.swing.JLabel lbl_height;
    private javax.swing.JLabel lbl_heightTag;
    private javax.swing.JLabel lbl_identificationNumber;
    private javax.swing.JLabel lbl_identificationNumberTag;
    private javax.swing.JLabel lbl_image;
    private javax.swing.JLabel lbl_name;
    private javax.swing.JLabel lbl_nameTag;
    private javax.swing.JLabel lbl_phoneNumber;
    private javax.swing.JLabel lbl_phoneNumberTag;
    private javax.swing.JLabel lbl_surname;
    private javax.swing.JLabel lbl_surnameTag;
    private javax.swing.JLabel lbl_userType;
    private javax.swing.JLabel lbl_userTypeTag;
    private javax.swing.JLabel lbl_weight;
    private javax.swing.JLabel lbl_weightTag;
    private javax.swing.JMenuItem mItem_UpdateAppointment;
    private javax.swing.JMenuItem mItem_changeColorAppointmentsSection;
    private javax.swing.JMenuItem mItem_changeColorRandomly;
    private javax.swing.JMenuItem mItem_colorChangeUserInformation;
    private javax.swing.JMenuItem mItem_colorChangeUserSettings;
    private javax.swing.JMenuItem mItem_deleteAppointment;
    private javax.swing.JMenuItem mItem_exit;
    private javax.swing.JMenuItem mItem_newAppointment;
    private javax.swing.JMenuItem mItem_showSelected;
    private javax.swing.JMenu menu_about;
    private javax.swing.JMenuItem mitem_fileAddAnImage;
    private javax.swing.JMenuItem mitem_fileAddAnSignature;
    private javax.swing.JPanel pnl_appoinmentSection;
    private javax.swing.JPanel pnl_tools;
    private javax.swing.JPanel pnl_userInformation;
    private javax.swing.JPopupMenu popup_appointmentsSection;
    private javax.swing.JPopupMenu popup_userInformations;
    private javax.swing.JPopupMenu popup_userSettings;
    private javax.swing.JTable tbl_appointments;
    private javax.swing.JTextField txt_search;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the tbl_appointmentsModel
     */
    public DefaultTableModel getTbl_appointmentsModel() {
        return tbl_appointmentsModel;
    }

    /**
     * @return the pnl_appoinmentSection
     */
    public javax.swing.JPanel getPnl_appoinmentSection() {
        return pnl_appoinmentSection;
    }

    /**
     * @return the pnl_tools
     */
    public javax.swing.JPanel getPnl_tools() {
        return pnl_tools;
    }

    /**
     * @return the pnl_userInformation
     */
    public javax.swing.JPanel getPnl_userInformation() {
        return pnl_userInformation;
    }
}
