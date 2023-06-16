package com.example.jseeker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        String query = "SELECT JobOpening.Job_Opening_ID, Company.Company_Name, JobOpening.Job_Tilte, " +
                "JobOpening.Job_Type, JobOpening.Salary, JobOpening.Deadline ,JobOpening.Job_Description" +
                " FROM JobOpening INNER JOIN Company ON JobOpening.Company_ID = Company.Company_ID" ;
                conn = MSConnection.connect();
        try {
            st = conn.prepareStatement(query);
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
                System.out.println(rs.getString(2)+" "+rs.getString(1)+" "+rs.getString(3));
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

         ShowList_JobOpeningData();
         displayusername();
    }
}
