package com.example.jseeker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SignupFormController {
    @FXML
    private AnchorPane Sign_Up_Form;
    @FXML
    private TextField City;

    @FXML
    private ComboBox<?> Company_selection;

    @FXML
    private TextField Country;

    @FXML
    private DatePicker Date_selection;

    @FXML
    private TextField Email;

    @FXML
    private TextField F_name;

    @FXML
    private ComboBox<?> Gender_selection;

    @FXML
    private Button Import_picture_btn;

    @FXML
    private TextField L_name;

    @FXML
    private ComboBox<?> Level1;

    @FXML
    private ComboBox<?> Level2;

    @FXML
    private ComboBox<?> Level3;

    @FXML
    private PasswordField Password;

    @FXML
    private TextField Phone1;

    @FXML
    private TextField Phone2;

    @FXML
    private ImageView Pic_frame;

    @FXML
    private TextField Position;

    @FXML
    private ComboBox<?> Skill1;

    @FXML
    private ComboBox<?> Skill2;

    @FXML
    private ComboBox<?> Skill3;

    @FXML
    private Button Submit_btn;

    @FXML
    private ComboBox<?> Type_selection;

    @FXML
    private ComboBox<?> interst1;

    @FXML
    private ComboBox<?> interst2;

    @FXML
    private ComboBox<?> interst3;

// database connections

    private Connection conn;
    private PreparedStatement st;
    private ResultSet rs;
////////////////////////////
// image things
private Image image;

///////////////////

    public int returnid() throws SQLException {
        String query4="select ID from Member where Email=? ";
         Connection conn1;
         PreparedStatement st1;
         ResultSet rs1;
        int id = -1;
        try {
            conn1 = MSConnection.connect();

            st1 = conn1.prepareStatement(query4);
            st1.setString(1, Email.getText());
            rs1 = st1.executeQuery();

            if (rs1.next()) {
                id = rs1.getInt(1);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }


private FileInputStream fis;
    private File file;
    public void submit_btn(){
        Alert alert;


        if(F_name.getText().isEmpty()||L_name.getText().isEmpty()
        || Email.getText().isEmpty()|| Password.getText().isEmpty()
        ||Gender_selection.getSelectionModel().getSelectedItem()==null
        ||Date_selection.getValue()==null || Country.getText().isEmpty()
        || City.getText().isEmpty()||Phone1.getText().isEmpty() ||
        Type_selection.getSelectionModel().getSelectedItem()==null||
        Skill1.getSelectionModel().getSelectedItem()==null
        || Level1.getSelectionModel().getSelectedItem()==null
        || interst1.getSelectionModel().getSelectedItem()==null
                || UserData.path2 == null || UserData.path2 == ""  ){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        }
        else if((Type_selection.getSelectionModel().getSelectedItem()=="employer" &&
                 Company_selection.getSelectionModel().getSelectedItem()==null
                 &&  Position.getText().isEmpty())
                ||
                (Type_selection.getSelectionModel().getSelectedItem()=="employee" &&
                Company_selection.getSelectionModel().getSelectedItem()==null
                &&  Position.getText().isEmpty())){

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();
        }
        else{
            int company_id = -1;
            int new_memberid;
            String query1="select Company_ID from Company where Company_Name=?";
            String query2="insert into \n" +
                    "Member(Company_ID,User_Type,First_Name,\n" +
                    "Last_Name,Email,Password,Sex,Country,City,Birthday,Profile_Pic,Postion)\n" +
                    "Values(?,?,?,?,?,?,?,?,?,?,?,?)"; //for employer or employee with picture

            String query3="insert into \n" +
                    "Member(User_Type,First_Name,\n" +
                    "Last_Name,Email,Password,Sex,Country,City,Birthday,Profile_Pic)\n" +
                    "Values(?,?,?,?,?,?,?,?,?,?)"; //for jobseeker with picture

            String query4="select ID from Member where Email=? ";

            String query5="insert into MemberIntersets values(?,?)";
            String query6="insert into MemberSkills values(?,?,?)";
            String query7="insert into MemberPhones values(?,?)";

            conn = MSConnection.connect();
            try {
                if(Type_selection.getSelectionModel().getSelectedItem()=="employer"
                ||Type_selection.getSelectionModel().getSelectedItem()=="employee" && UserData.path2 !=null) {

                    st = conn.prepareStatement(query1);

                    st.setString(1, (String) Company_selection.getSelectionModel().getSelectedItem());
                    rs = st.executeQuery();
                    while (rs.next()){
                        company_id=rs.getInt(1);
                    }


                    st = conn.prepareStatement(query2);

                    st.setInt(1,company_id);
                    st.setString(2, (String) Type_selection.getSelectionModel().getSelectedItem());
                    st.setString(3,F_name.getText());
                    st.setString(4,L_name.getText());
                    st.setString(5,Email.getText());
                    st.setString(6,Password.getText());
                    st.setString(7,(String) Gender_selection.getSelectionModel().getSelectedItem());
                    st.setString(8,Country.getText());
                    st.setString(9,City.getText());
                    st.setDate(10, Date.valueOf(Date_selection.getValue()));
                    file=new File(UserData.path2);
                    fis = new FileInputStream(file);
                    st.setBinaryStream(11, fis, file.length());
                    st.setString(12,Position.getText());

                    st.executeUpdate();

                     new_memberid=returnid();

                     if(interst2.getSelectionModel().getSelectedItem()!=null){
                         st = conn.prepareStatement(query5);
                         st.setInt(1,new_memberid);
                         st.setInt(2,interst2.getSelectionModel().getSelectedIndex()+1);
                         st.executeUpdate();
                     }
                    if(interst3.getSelectionModel().getSelectedItem()!=null){
                        st = conn.prepareStatement(query5);
                        st.setInt(1,new_memberid);
                        st.setInt(2,interst3.getSelectionModel().getSelectedIndex()+1);
                        st.executeUpdate();
                    }
                    st = conn.prepareStatement(query5);
                    st.setInt(1,new_memberid);
                    st.setInt(2,interst1.getSelectionModel().getSelectedIndex()+1);
                    st.executeUpdate();

                    if(Skill2.getSelectionModel().getSelectedItem()!=null){
                        st = conn.prepareStatement(query6);
                        st.setInt(1,new_memberid);
                        st.setInt(2,Skill2.getSelectionModel().getSelectedIndex()+1);
                        st.setString(3, (String) Level2.getSelectionModel().getSelectedItem());
                        st.executeUpdate();
                    }
                    if(Skill3.getSelectionModel().getSelectedItem()!=null){
                        st = conn.prepareStatement(query6);
                        st.setInt(1,new_memberid);
                        st.setInt(2,Skill3.getSelectionModel().getSelectedIndex()+1);
                        st.setString(3, (String) Level3.getSelectionModel().getSelectedItem());
                        st.executeUpdate();
                    }
                    st = conn.prepareStatement(query6);
                    st.setInt(1,new_memberid);
                    st.setInt(2,Skill1.getSelectionModel().getSelectedIndex()+1);
                    st.setString(3, (String) Level1.getSelectionModel().getSelectedItem());
                    st.executeUpdate();

                    if(!(Phone2.getText().isEmpty())){
                        st = conn.prepareStatement(query7);
                        st.setInt(1,Integer.parseInt(Phone2.getText()));
                        st.setInt(2,new_memberid);
                        st.executeUpdate();
                    }
                    st = conn.prepareStatement(query7);
                    st.setInt(1,Integer.parseInt(Phone1.getText()));
                    st.setInt(2,new_memberid);
                    st.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("info");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Signed up");
                    alert.showAndWait();
                }
                else{

                        st = conn.prepareStatement(query3);


                        st.setString(1, (String) Type_selection.getSelectionModel().getSelectedItem());
                        st.setString(2, F_name.getText());
                        st.setString(3, L_name.getText());
                        st.setString(4, Email.getText());
                        st.setString(5, Password.getText());
                        st.setString(6, (String) Gender_selection.getSelectionModel().getSelectedItem());
                        st.setString(7, Country.getText());
                        st.setString(8, City.getText());
                        st.setDate(9, Date.valueOf(Date_selection.getValue()));
                        file = new File(UserData.path2);
                        fis = new FileInputStream(file);
                        st.setBinaryStream(10, fis, file.length());


                        st.executeUpdate();


                    new_memberid=returnid();

                    if(interst2.getSelectionModel().getSelectedItem()!=null){
                        st = conn.prepareStatement(query5);
                        st.setInt(1,new_memberid);
                        st.setInt(2,interst2.getSelectionModel().getSelectedIndex()+1);
                        st.executeUpdate();
                    }
                    if(interst3.getSelectionModel().getSelectedItem()!=null){
                        st = conn.prepareStatement(query5);
                        st.setInt(1,new_memberid);
                        st.setInt(2,interst3.getSelectionModel().getSelectedIndex()+1);
                        st.executeUpdate();
                    }
                    st = conn.prepareStatement(query5);
                    st.setInt(1,new_memberid);
                    st.setInt(2,interst1.getSelectionModel().getSelectedIndex()+1);
                    st.executeUpdate();

                    if(Skill2.getSelectionModel().getSelectedItem()!=null){
                        st = conn.prepareStatement(query6);
                        st.setInt(1,new_memberid);
                        st.setInt(2,Skill2.getSelectionModel().getSelectedIndex()+1);
                        st.setString(3, (String) Level2.getSelectionModel().getSelectedItem());
                        st.executeUpdate();
                    }
                    if(Skill3.getSelectionModel().getSelectedItem()!=null){
                        st = conn.prepareStatement(query6);
                        st.setInt(1,new_memberid);
                        st.setInt(2,Skill3.getSelectionModel().getSelectedIndex()+1);
                        st.setString(3, (String) Level3.getSelectionModel().getSelectedItem());
                        st.executeUpdate();
                    }
                    st = conn.prepareStatement(query6);
                    st.setInt(1,new_memberid);
                    st.setInt(2,Skill1.getSelectionModel().getSelectedIndex()+1);
                    st.setString(3, (String) Level1.getSelectionModel().getSelectedItem());
                    st.executeUpdate();

                    if(!(Phone2.getText().isEmpty())){
                        st = conn.prepareStatement(query7);
                        st.setInt(1,Integer.parseInt(Phone2.getText()));
                        st.setInt(2,new_memberid);
                        st.executeUpdate();
                    }
                    st = conn.prepareStatement(query7);
                    st.setInt(1,Integer.parseInt(Phone1.getText()));
                    st.setInt(2,new_memberid);
                    st.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("info");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Signed up");
                    alert.showAndWait();
                }

                }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        clearfields();
    }

    public void clearfields() {
        F_name.setText("");
        L_name.setText("");
        Email.setText("");
        Password.setText("");
        Gender_selection.getSelectionModel().clearSelection();
        Type_selection.getSelectionModel().clearSelection();
        Date_selection.setValue(null);
        Country.setText("");
        City.setText("");
        Phone1.setText("");
        Phone2.setText("");
        Pic_frame.setImage(null);
        Company_selection.getSelectionModel().clearSelection();
        Position.setText("");
        Skill1.getSelectionModel().clearSelection();
        Skill2.getSelectionModel().clearSelection();
        Skill3.getSelectionModel().clearSelection();
        Level1.getSelectionModel().clearSelection();
        Level2.getSelectionModel().clearSelection();
        Level3.getSelectionModel().clearSelection();
        interst1.getSelectionModel().clearSelection();
        interst2.getSelectionModel().clearSelection();
        interst3.getSelectionModel().clearSelection();

        UserData.path2 = "";
    }




    public void importPicture() {

        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(Sign_Up_Form.getScene().getWindow());

        if (file != null) {
            UserData.path2 = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 200, 226, false, true);
            Pic_frame.setImage(image);
        }
    }




    private String[] listlevels = {"Beginner", "Intermediate","Advanced","Expert"};
    public void addlevellist(){
        List<String> listL = new ArrayList<>();

        for (String data : listlevels) {
            listL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listL);
        Level1.setItems(listData);
        Level2.setItems(listData);
        Level3.setItems(listData);

    }



public void addInterstlist(){
    List<String> listintersts = new ArrayList<>();
    String sql="select Interset_Name from Interset";
    try{
        conn = MSConnection.connect();
        st = conn.prepareStatement(sql);
        rs=st.executeQuery();
        while(rs.next()){
            listintersts.add(rs.getString(1));
        }
    }
    catch (Exception e) {
        e.printStackTrace();
    }
    ObservableList listData = FXCollections.observableArrayList(listintersts);
    interst1.setItems(listData);
    interst2.setItems(listData);
    interst3.setItems(listData);
}



    public void addSkillslist(){
        List<String> listskills = new ArrayList<>();
        String sql="select Skill_Name from Skill";
        try{
            conn = MSConnection.connect();
            st = conn.prepareStatement(sql);
            rs=st.executeQuery();
            while(rs.next()){
                listskills.add(rs.getString(1));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList listData = FXCollections.observableArrayList(listskills);
        Skill1.setItems(listData);
        Skill2.setItems(listData);
        Skill3.setItems(listData);

    }




    public void checkemployer(){
if(Type_selection.getSelectionModel().getSelectedItem()=="employer" ||
        Type_selection.getSelectionModel().getSelectedItem()=="employee"){
    Company_selection.setVisible(true);
    Position.setVisible(true);
}
else {
    Company_selection.setVisible(false);
    Position.setVisible(false);
}
    }
    public void addCompanylist(){
        List<String> listcompany = new ArrayList<>();
        String sql="select Company_Name from Company";
        try{
            conn = MSConnection.connect();
            st = conn.prepareStatement(sql);
            rs=st.executeQuery();
            while(rs.next()){
                listcompany.add(rs.getString(1));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        ObservableList listData = FXCollections.observableArrayList(listcompany);
        Company_selection.setItems(listData);
    }

    private String[] listType = {"employer", "employee","JobSeeker"};

    public void addTypeList() {
        List<String> listT = new ArrayList<>();

        for (String data : listType) {
            listT.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listT);
        Type_selection.setItems(listData);
    }




    private String[] listGender = {"Male", "Female"};

    public void addGendernList() {
        List<String> listG = new ArrayList<>();

        for (String data : listGender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        Gender_selection.setItems(listData);
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) Sign_Up_Form.getScene().getWindow();
        stage.setIconified(true);
    }



    @FXML
    public void initialize(){
        Company_selection.setVisible(false);
        Position.setVisible(false);

        addGendernList();
        addTypeList();
        addCompanylist();
        addSkillslist();
        addInterstlist();
        addlevellist();

        clearfields();

    }
}
