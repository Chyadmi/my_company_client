/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompany_v1.pkg1;

import bean.DemandeAugmentation;
import bean.DemandeAvance;
import bean.DemandeConge;
import bean.Departement;
import bean.Mois;
import bean.Salarie;
import contrat.DepartementFacadeContrat;
import contrat.SalarieFacadeContrat;
import helper.DepartementFxHelper;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import util.Session;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class DirecteurDepartementFXMLController implements Initializable {

    Remote r = null;
    DepartementFacadeContrat departementFacade ;
    SalarieFacadeContrat salarieFacade;

    private DepartementFxHelper departementFxHelper;

    @FXML
    private Label close;

    @FXML
    private TableView departementTable = new TableView();

    @FXML
    private TextField numDep;
    @FXML
    private TextField nomDep;
    @FXML
    private ComboBox<Salarie> respoDep = new ComboBox<>();

    /**
     * Initializes the controller class.
     */
    private void initHelper() {
        
        System.out.println("Heloo init helper");
        try {
            System.out.println("in initHelper");
//            List<Departement> list = departementFacade.ggetAllDepartements();
//            System.out.println(list);
            departementFxHelper = new DepartementFxHelper(departementTable, departementFacade.ggetAllDepartements());
        } catch (RemoteException ex) {
            System.out.println("EXCEPTION IN INIT HELPER DEPARTEMENT");
        }
    }

    private void initCombo() {
        System.out.println("Hello init comboo");
        try {
            respoDep.setItems(FXCollections.observableArrayList(salarieFacade.getResponsables()));
        } catch (RemoteException ex) {
            Logger.getLogger(DirecteurDepartementFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void addButton(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "AjouterDepartementFXML.fxml", this.getClass());
    }

    @FXML
    private void toMesDemandes(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DirecteurDemandeFXML.fxml", this.getClass());
    }

    @FXML
    private void toBoiteReception(ActionEvent actionEvent) throws IOException {
//        ViewLauncher.forward(actionEvent, "BoiteDirecteurFXML.fxml", this.getClass());
    }

    @FXML
    private void toProfile(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DirecteurAccueilFXML.fxml", this.getClass());
    }

    @FXML
    private void toDepartement(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DirecteurDepartementFXML.fxml", this.getClass());
    }

    @FXML
    private void seDeconnecter(ActionEvent actionEvent) throws IOException {
        Session.clear();
        ViewLauncher.forward(actionEvent, "LoginFXML.fxml", this.getClass());
    }

    public void tableDepClicked() {
        Departement dep = departementFxHelper.getSelected();
        if (dep != null) {
            numDep.setText(dep.getId());
            nomDep.setText(dep.getNom());
            respoDep.getSelectionModel().select(dep.getResponsable());
        }
    }

    public void updateDep(ActionEvent actionEvent) throws IOException {
        Salarie sal = respoDep.getValue();
        int res = departementFacade.updateDep(numDep.getText(), nomDep.getText(), sal.getId());
        if (res == 1) {
            clear();
            ViewLauncher.forward(actionEvent, "DirecteurDepartementFXML.fxml", this.getClass());
            //alert("Le departement a été modifié avec succès", "DONE!");
            departementTable.refresh();
        } else {
            JOptionPane.showMessageDialog(null, "An error has occures, kayn chi mochkil", "Echec", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteDep() {
        Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
        aa.setHeaderText("ATTENTION!!");
        aa.setContentText("Voulez-vous vraiment supprimer ce departement ?");
        aa.setTitle("ATTENTION!!");
        Optional<ButtonType> result = aa.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                departementFacade.supprimerDepartement(departementFxHelper.getSelected().getId());
            } catch (RemoteException ex) {
                Logger.getLogger(DirecteurDepartementFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            departementFxHelper.remove(departementFxHelper.getSelected());
            clear();
        }
    }

    public void alert(String contenttext, String headertext) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headertext);
        alert.setContentText(contenttext);
        alert.setTitle("Information dialog");
        alert.show();
    }

    public void clear() {
        nomDep.clear();
        numDep.clear();
        respoDep.setValue(null);

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
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        connect_rmi();
        initHelper();
        initCombo();
        

    }

}
