package com.example.jseeker;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CompanyCreationController {

    @FXML
    private TextField Company_name_filed;

    @FXML
    private AnchorPane Company_reg_form;

    @FXML
    private Button Goback_btn;

    @FXML
    private TextField Location_field;

    @FXML
    private TextField Indusrty_type;

    @FXML
    private ImageView Logoimage;

    @FXML
    private Button Submit_btn;

    @FXML
    private TextField URL_field;

    // image things
    private Image image;

//////////////////////////////////////
    // database connections

    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;
////////////////////////////

    private FileInputStream fis;
    private File file;

    public void submit_btn(){

        Alert alert;
        if(Company_name_filed.getText().isEmpty()||Location_field.getText().isEmpty()
        ||Indusrty_type.getText().isEmpty()  || UserData.path3 == null || UserData.path3 == ""  ){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        }
        else{
            String query1="insert into Company(Company_Name,URL,Location,Industry,Logo)\n" +
                    "values(?,?,?,?,?)";
            String query2="insert into Company(Company_Name,Location,Industry,Logo)\n" +
                    "values(?,?,?,?)";
            try{
                conn=MSConnection.connect();
                if(URL_field.getText().isEmpty()){
                    st = conn.prepareStatement(query2);
                    st.setString(1,Company_name_filed.getText());
                    st.setString(2,Location_field.getText());
                    st.setString(3,Indusrty_type.getText());
                    file = new File(UserData.path3);
                    fis = new FileInputStream(file);
                    st.setBinaryStream(4, fis, file.length());

                    st.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("info");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Created");
                    alert.showAndWait();

                    clearfields();
                }
                else{
                    st = conn.prepareStatement(query1);
                    st.setString(1,Company_name_filed.getText());
                    st.setString(2,URL_field.getText());
                    st.setString(3,Location_field.getText());
                    st.setString(4,Indusrty_type.getText());
                    file = new File(UserData.path3);
                    fis = new FileInputStream(file);
                    st.setBinaryStream(5, fis, file.length());

                    st.executeUpdate();
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("info");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Created");
                    alert.showAndWait();

                    clearfields();

                }

            }
            catch (Exception e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("This company name is already registered !");
                alert.showAndWait();
            }
        }
    }

    public void clearfields(){
        Company_name_filed.setText("");
        URL_field.setText("");
        Location_field.setText("");
        Indusrty_type.setText("");
        Logoimage.setImage(null);

        UserData.path3="";
    }
    public void importPicture() {

        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(Company_reg_form.getScene().getWindow());

        if (file != null) {
            UserData.path3 = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 215, 199, false, true);
            Logoimage.setImage(image);
        }
    }


    public void close() {
        System.exit(0);
    }


    public void minimize() {
        Stage stage = (Stage) Company_reg_form.getScene().getWindow();
        stage.setIconified(true);
    }
    private double x = 0;
    private double y = 0;


    public void returntologin() throws IOException {
        Goback_btn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });
UserData.path3="";
        stage.initStyle(StageStyle.TRANSPARENT);


        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void initialize(){


    }
}
