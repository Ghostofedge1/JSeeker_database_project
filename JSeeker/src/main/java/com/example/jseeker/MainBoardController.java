package com.example.jseeker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.stage.StageStyle;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.*;

public class MainBoardController {

    // page 1 components
    @FXML
    private Button logoutbtn;

    @FXML
    private Button close;

    @FXML
    private Button minimize;
    @FXML
    private TableColumn<jobOpeningData, String> Application_deadline_col;

    @FXML
    private TableColumn<jobOpeningData, String> Company_name_col;

    @FXML
    private AnchorPane Home;

    @FXML
    private AnchorPane main_form;
    @FXML
    private TableColumn<jobOpeningData, String> Jobtitle_col;

    @FXML
    private TableColumn<jobOpeningData, String> Salary_col;

    @FXML
    private TextField Search_job_openings;

    @FXML
    private TableColumn<jobOpeningData, String> Type_col;

    @FXML
    private Label usernamelb;


    @FXML
    private TextArea description_filed;

    @FXML
    private Button importbtn;

    @FXML
    private TableView<jobOpeningData> jobopeningstable;

    @FXML
    private ImageView CVimage;

    @FXML
    private Button submitbtn;
    private Image image;

    /////////////////////////////////////////

    //page 2 components
    @FXML
    private TableColumn<ApplicationStatusClass, String> App_company_name_col;

    @FXML
    private TextArea App_job_discreption;

    @FXML
    private TableColumn<ApplicationStatusClass, String> App_job_title_col;

    @FXML
    private TableColumn<ApplicationStatusClass, String> App_job_type_col;

    @FXML
    private TableColumn<ApplicationStatusClass, String> App_status_col;
    @FXML
    private TableView<ApplicationStatusClass> applications_table;
    @FXML
    private TableColumn<ApplicationStatusClass, String> sent_date_col;

///////////////////////////////////////////////////////////
    //switch buttons
    @FXML
    private Button Application_btn;
    @FXML
    private Button Home_btn;
    @FXML
    private Button Settings_btn;


    /////////////////////////////////
    // pages anchor

    @FXML
    private AnchorPane page1;

    @FXML
    private AnchorPane page2;
///////////////////////////////////////////////////////
    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;
//    public ObservableList<jobOpeningData> addjobopeningsListData() {
//
//        ObservableList<jobOpeningData> listData = FXCollections.observableArrayList();
//        String sql = "SELECT * FROM JobOpening";
//
//        conn = MSConnection.connect();
//
//        try {
//            st = conn.prepareStatement(sql);
//            rs = st.executeQuery();
//            jobOpeningData jobD;
//
//            while (rs.next()) {
//                jobD = new jobOpeningData(rs.getInt(1),
//                        rs.getInt(2), rs.getString(3),
//                        rs.getString(4),
//                        rs.getString(5),rs.getDouble(6),
//                        rs.getDate(7));
//                listData.add(jobD);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(joblist);
//        return listData;
//    }
//    private ObservableList<jobOpeningData> joblist;
//
////    public void addjobsShowListData() {
////        joblist = addjobopeningsListData();
//////System.out.println(joblist);
////        Jobtitle_col.setCellValueFactory(new PropertyValueFactory<>("job_title"));
////        Application_deadline_col.setCellValueFactory(new PropertyValueFactory<>("deadline"));
////        Salary_col.setCellValueFactory(new PropertyValueFactory<>("salary"));
////        Type_col.setCellValueFactory(new PropertyValueFactory<>("type"));
////        Company_name_col.setCellValueFactory(new PropertyValueFactory<>("Combany_ID"));
////
////
////        jobopeningstable.setItems(joblist);
////
////    }
//public void loadjobData() {
//    Map<TableColumn<jobOpeningData, ?>, String> columnMappings = new HashMap<>();
//    columnMappings.put(Jobtitle_col, "job_title");
//    columnMappings.put(Application_deadline_col, "deadline");
//    columnMappings.put(Salary_col, "salary");
//    columnMappings.put(Type_col, "type");
//    columnMappings.put(Company_name_col, "Combany_ID");
//
//    for (TableColumn<jobOpeningData, ?> column : columnMappings.keySet()) {
//        column.setCellValueFactory(new PropertyValueFactory<>(columnMappings.get(column)));
//    }
//}
//
//
//
//    public void ShowList_jobsData() throws SQLException {
//        joblist = addjobopeningsListData();
//        loadjobData();
//        jobopeningstable.setItems(joblist);
//
//    }
public void switchForm(ActionEvent event) throws SQLException {

    if (event.getSource() == Home_btn) {
        page1.setVisible(true);
        page2.setVisible(false);

        Home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
        Application_btn.setStyle("-fx-background-color:transparent");

        try {
            ShowList_JobOpeningData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
//
//        homeTotalEmployees();
//        homeEmployeeTotalPresent();
//        homeTotalInactive();
//        homeChart();

    } else if (event.getSource() == Application_btn) {
        page1.setVisible(false);
        page2.setVisible(true);
//        salary_form.setVisible(false);

        Application_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
        Home_btn.setStyle("-fx-background-color:transparent");
//        salary_btn.setStyle("-fx-background-color:transparent");
ShowList_Applicationstatus();
//        addEmployeeGendernList();
//        addEmployeePositionList();
//        addEmployeeSearch();

//    } else if (event.getSource() == salary_btn) {
//        home_form.setVisible(false);
//        addEmployee_form.setVisible(false);
//        salary_form.setVisible(true);
//
//        salary_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
//        addEmployee_btn.setStyle("-fx-background-color:transparent");
//        home_btn.setStyle("-fx-background-color:transparent");
//
//        salaryShowListData();
//
    }

}
public void showApplication_jobdescription() {
    ApplicationStatusClass jobD = applications_table.getSelectionModel().getSelectedItem();
    int num = applications_table.getSelectionModel().getSelectedIndex();

    if ((num - 1) < -1) {
        return;
    }


    App_job_discreption.setText(jobD.getDescription());

}

    private ObservableList<ApplicationStatusClass> addAppstatus;
    public void ShowList_Applicationstatus() throws SQLException {
        addAppstatus = list_Applicationstatus();
        loadApplicationStatus();
        applications_table.setItems(addAppstatus);

    }
public void loadApplicationStatus() {
    Map<TableColumn<ApplicationStatusClass, ?>, String> columnMappings = new HashMap<>();

    columnMappings.put(App_status_col, "App_status");
    columnMappings.put(sent_date_col, "Sent_Date");
    columnMappings.put(App_job_title_col, "Jobtitle");
    columnMappings.put(App_company_name_col, "Combany_Name");
    columnMappings.put(App_job_type_col, "Type");

    for (TableColumn<ApplicationStatusClass, ?> column : columnMappings.keySet()) {
        column.setCellValueFactory(new PropertyValueFactory<>(columnMappings.get(column)));
    }
}
public ObservableList<ApplicationStatusClass> list_Applicationstatus() throws SQLException {
    ObservableList<ApplicationStatusClass> listData = FXCollections.observableArrayList();
    String query = "select a.ID,a.Job_Opening_ID,a.Application_Status,a.Sent_Date,c.Company_Name\n" +
            ", j.Job_Tilte,j.Job_Description,j.Job_Type\n" +
            "from Application a,JobOpening j ,Company c\n" +
            "where a.Job_Opening_ID=j.Job_Opening_ID and j.Company_ID=c.Company_ID\n" +
            "and ID=?" ;
    conn = MSConnection.connect();
    try {
        st = conn.prepareStatement(query);
        st.setInt(1,UserData.id);
        rs = st.executeQuery();
        ApplicationStatusClass Appstatus;
        while (rs.next()) {
            Appstatus = new ApplicationStatusClass(
                    rs.getInt("ID"),
                    rs.getInt("Job_Opening_ID"),
                    rs.getString("Application_Status"),
                    rs.getDate("Sent_Date"),
                    rs.getString("Company_Name"),
                    rs.getString("Job_Tilte"),
                    rs.getString("Job_Description"),
                    rs.getString("Job_Type")

            );
            // System.out.println(rs.getString(2)+" "+rs.getString(1)+" "+rs.getString(3));
            listData.add(Appstatus);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return listData;
}
    public void importCVImage() {

        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            UserData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 175, 174, false, true);
            CVimage.setImage(image);
        }
    }
    private FileInputStream fis;
    private File file;
    public void submitapplication(){

        jobOpeningData jobD = jobopeningstable.getSelectionModel().getSelectedItem();
        int num = jobopeningstable.getSelectionModel().getSelectedIndex();

     //   dsffds@hj.com

        Alert alert;

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());


        if ((num - 1) < -1) {
            return;
        }
        String sql = "INSERT INTO Application (ID, Job_Opening_ID, Resume, Application_Status, Sent_Date) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {

            if(UserData.path !=null) {
                file=new File(UserData.path);
                conn = MSConnection.connect();
                st = conn.prepareStatement(sql);

                st.setInt(1, UserData.id);
                st.setInt(2, jobD.getId());
                fis = new FileInputStream(file);
                st.setBinaryStream(3, fis, file.length());
                st.setString(4, "Pending");
               st.setDate(5,sqlDate);

               st.executeUpdate();

//
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("info");
                alert.setHeaderText(null);
                alert.setContentText("Sent Successfully");
                alert.showAndWait();
            }
            else{
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("pls select import your CV");
                alert.showAndWait();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
public void loadJobOpeningsData() {
    Map<TableColumn<jobOpeningData, ?>, String> columnMappings = new HashMap<>();

    columnMappings.put(Company_name_col, "Combany_Name");
    columnMappings.put(Jobtitle_col, "Jobtitle");
    columnMappings.put(Type_col, "Type");
    columnMappings.put(Salary_col, "Salary");
    columnMappings.put(Application_deadline_col, "Deadline");

    for (TableColumn<jobOpeningData, ?> column : columnMappings.keySet()) {
        column.setCellValueFactory(new PropertyValueFactory<>(columnMappings.get(column)));
    }
}
    public ObservableList<jobOpeningData> list_JobOpeningData() throws SQLException {
        ObservableList<jobOpeningData> listData = FXCollections.observableArrayList();
        String query = "select DISTINCT j.Deadline,j.Job_Description,j.Job_Opening_ID,\n" +
                "j.Job_Tilte,j.Job_Type,j.Salary,c.Company_Name\n" +
                "from Company c,Member m,JobOpening j  \n" +
                ",MemberIntersets mi,JobOpeningInterset ji\n" +
                "where  j.Company_ID=c.Company_ID and \n" +
                "m.ID=mi.ID and j.Job_Opening_ID=ji.Job_Opening_ID and \n" +
                "mi.Interset_ID=ji.Interset_ID and m.id=?" ;
                conn = MSConnection.connect();
        try {
            st = conn.prepareStatement(query);
            st.setInt(1,UserData.id);
            rs = st.executeQuery();
            jobOpeningData JobOpeningD;
            while (rs.next()) {
                JobOpeningD = new jobOpeningData(
                        rs.getInt("Job_Opening_ID"),
                        rs.getString("Company_Name"),
                        rs.getString("Job_Tilte"),
                        rs.getString("Job_Description"),
                        rs.getString("Job_Type"),
                        rs.getDouble("Salary"),
                        rs.getDate("Deadline")

                );
               // System.out.println(rs.getString(2)+" "+rs.getString(1)+" "+rs.getString(3));
                listData.add(JobOpeningD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<jobOpeningData> addJobOpeningsList;
    public void ShowList_JobOpeningData() throws SQLException {
        addJobOpeningsList = list_JobOpeningData();
        loadJobOpeningsData();
        jobopeningstable.setItems(addJobOpeningsList);

    }
    public void showjobdescription() {
        jobOpeningData jobD = jobopeningstable.getSelectionModel().getSelectedItem();
        int num = jobopeningstable.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }


        description_filed.setText(jobD.getDescription());

    }

    private double x = 0;
    private double y = 0;

    public void displayusername(){
        usernamelb.setText(UserData.username);
    }
    public void logout() {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {

                logoutbtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
     @FXML

    public void initialize() throws SQLException {
        // TODO

         ShowList_Applicationstatus();

         ShowList_JobOpeningData();
         displayusername();
    }
}
