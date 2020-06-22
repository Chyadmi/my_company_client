/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompany_v1.pkg1;

import bean.Salarie;
import contrat.DemandeFacadeContrat;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import util.Session;

/**
 * FXML Controller class
 *
 * @author CHAACHAI Youssef
 */
public class DemandeCongeFXMLController implements Initializable {
    

    Salarie sal = ((Salarie) Session.getAttribut("connectedUser"));
    Remote r = null ;
    DemandeFacadeContrat demandeFacade;

    @FXML
    private Label close;
    @FXML
    private Button minimize;

    @FXML
    private DatePicker dateDebut;
    @FXML
    private DatePicker dateFin;

    @FXML
    private TextArea commentaire;

    @FXML
    private Button annuler;
    @FXML
    private Button envoyer;

    @FXML
    public void annuler(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "EmployeDemandeFXML.fxml", this.getClass());
    }

    @FXML
    public void envoyerDemande(ActionEvent actionEvent) throws IOException {
        System.out.println("hani hna ");
        if (testChamps()) {
            Date date_d = java.sql.Date.valueOf(dateDebut.getValue());
            Date date_f = java.sql.Date.valueOf(dateFin.getValue());
            int res = demandeFacade.ajouterDemandeConge(sal,date_d, date_f, commentaire.getText());
            if (res == 1) {
                ViewLauncher.forward(actionEvent, "EmployeDemandeFXML.fxml", this.getClass());
            } else {
                JOptionPane.showMessageDialog(null, "Il y a une erreur, veuillez essayer plus tard !", "Echec!", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs !", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean testChamps() {
        if (commentaire.getText().equals("") || dateDebut.getValue() == null || dateFin.getValue() == null) {
            return false;
        } else {
            return true;
        }
    }

    public void alert(String contenttext, String headertext) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headertext);
        alert.setContentText(contenttext);
        alert.setTitle("Information");
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
    public void connect_rmi() {
        String adr = (String)Session.getAttribut("adr_ip");
        System.out.println(adr);
        
        System.out.println("hello in Rmi connect Demande Conge ");
        try {
            r = Naming.lookup("rmi://" + adr + ":5655/demandeFacade");
            demandeFacade = ((DemandeFacadeContrat) r);
            
//            System.out.println(demandeFacade.getAllDemandesConges((Salarie) Session.getAttribut("ConnectedUser")));
//            r = Naming.lookup("rmi://" + adr + ":5655/demandeFacade");
//            demandeFacade = ((DemandeFacadeContrat) r);
            
            
            if (r != null) {
                
                System.out.println("OKKKKKKK");
            }
        } catch (Exception ex) {
            
            System.out.println("WALO");
//            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connect_rmi();
        
    }

}
