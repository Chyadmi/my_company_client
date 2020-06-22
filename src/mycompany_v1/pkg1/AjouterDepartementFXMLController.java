/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompany_v1.pkg1;

import bean.Salarie;
import contrat.DepartementFacadeContrat;
import contrat.SalarieFacadeContrat;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import util.Session;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class AjouterDepartementFXMLController implements Initializable {

    
    Remote r = null ;
    SalarieFacadeContrat salarieFacade ;
    DepartementFacadeContrat departementFacade;

    @FXML
    private Label close;
    @FXML
    private TextField numero;
    @FXML
    private TextField nom;
    @FXML
    private ComboBox<Salarie> respoDep = new ComboBox<>();

    private void initCombo() {
        try {
            respoDep.setItems(FXCollections.observableArrayList(salarieFacade.getResponsables()));
        } catch (RemoteException ex) {
            Logger.getLogger(AjouterDepartementFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void annuler(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DirecteurDepartementFXML.fxml", this.getClass());

    }

    public void addDep(ActionEvent actionEvent) throws IOException {
        if (testChamps()) {
            departementFacade.ajouterDepartement(numero.getText(), nom.getText(), respoDep.getValue().getId());
            ViewLauncher.forward(actionEvent, "DirecteurDepartementFXML.fxml", this.getClass());
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs !", "ERREUR", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void connect_rmi() {
        String adr = (String)Session.getAttribut("adr_ip");
        System.out.println("hello");
        try {
            r = Naming.lookup("rmi://" + adr + ":5655/salarieFacade");
            salarieFacade = ((SalarieFacadeContrat) r);
            r = Naming.lookup("rmi://" + adr + ":5655/departementFacade");
            departementFacade = ((DepartementFacadeContrat) r);
            
            
            if (r != null) {
                
                System.out.println("OKKKKKKK");
            }
        } catch (Exception ex) {
            
            System.out.println("WALO");
//            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    

    private boolean testChamps() {
        if (numero.getText().equals("") || nom.getText().equals("") || respoDep.getValue() == null) {
            return false;
        } else {
            return true;
        }
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        connect_rmi();
        initCombo();
    }

}
