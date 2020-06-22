/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompany_v1.pkg1;

import bean.Salarie;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import contrat.SalarieFacadeContrat;
import util.Session;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class DirecteurAccueilFXMLController implements Initializable {

    @FXML
    private Label close;

    @FXML
    private Label fullname;

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField telephone;
    @FXML
    private PasswordField motdepasse;
    @FXML
    private TextField login;
    @FXML
    private TextField fonction;

    private void initForm() {
        Salarie sal = ((Salarie) Session.getAttribut("connectedUser"));
        nom.setText(sal.getNom());
        prenom.setText(sal.getPrenom());
        telephone.setText(sal.getTelephone());
        login.setText(sal.getLogin());
        fonction.setText(sal.getRole().getLibelle());
        motdepasse.setText(sal.getPassword());
    }

    @FXML
    private void seDeconnecter(ActionEvent actionEvent) throws IOException {
        Session.clear();
        ViewLauncher.forward(actionEvent, "LoginFXML.fxml", this.getClass());
    }

    public void alert(String contenttext, String headertext) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headertext);
        alert.setContentText(contenttext);
        alert.setTitle("Information dialog");
        alert.show();
    }

    @FXML
    public void closeApp() {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void minimizeApp(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void toDepartement(ActionEvent actionEvent) throws IOException {
//        System.out.println("Hiii");
        ViewLauncher.forward(actionEvent, "DirecteurDepartementFXML.fxml", this.getClass());
    }
    @FXML
    private void toMesDemandes(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DirecteurDemandeFXML.fxml", this.getClass());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initForm();
    }

}
