/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompany_v1.pkg1;

import bean.Salarie;
import contrat.DemandeFacadeContrat;
import contrat.SalarieFacadeContrat;
import helper.DemandeAugmentationFxHelper;
import helper.DemandeAvanceFxHelper;
import helper.DemandeCongeFxHelper;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import util.Session;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class EmployeDemandeFXMLController implements Initializable {

    
    Remote r = null ;
    DemandeFacadeContrat demandeFacade ;
    
    Salarie sal = ((Salarie) Session.getAttribut("connectedUser"));

    private DemandeCongeFxHelper demandeCongeFxHelper;
    private DemandeAvanceFxHelper demandeAvanceFxHelper;
    private DemandeAugmentationFxHelper demandeAugmentationFxHelper;

    @FXML
    private Label close;

    @FXML
    private Button minimize;

    @FXML
    private Label fullname;

    @FXML
    private Button demandesButton;
    @FXML
    private Button inboxButton;
    @FXML
    private Button profileButton;
    @FXML
    private Button seDeconnecterButton;

    @FXML
    private Button ajouterConge;
    @FXML
    private Button ajouterAvance;

    @FXML
    private TableView congeTableView = new TableView();

    @FXML
    private TableView avanceTableView = new TableView();

    @FXML
    private TableView augmentationTableView = new TableView();

    /**
     * Initializes the controller class.
     */
    private void initHelper() {
        try {
            demandeCongeFxHelper = new DemandeCongeFxHelper(congeTableView, demandeFacade.getAllDemandesConges(sal));
            demandeAvanceFxHelper = new DemandeAvanceFxHelper(avanceTableView, demandeFacade.getAllDemandesAvance(sal));
            demandeAugmentationFxHelper = new DemandeAugmentationFxHelper(augmentationTableView, demandeFacade.getAllDemandesAugmentations(sal));
        } catch (RemoteException ex) {
            Logger.getLogger(EmployeDemandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void ajouterConge(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DemandeCongeFXML.fxml", this.getClass());
    }

    @FXML
    private void ajouterAugmentation(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DemandeAugmentationFXML.fxml", this.getClass());
    }

    @FXML
    private void ajouterAvance(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DemandeAvanceFXML.fxml", this.getClass());
    }

    @FXML
    private void toMesDemandes() {
        System.out.println("***********");
    }

    @FXML
    private void toBoiteReception(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "BoiteEmployeFXML.fxml", this.getClass());
    }

    @FXML
    private void toProfile(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "EmployeAccueilFXML.fxml", this.getClass());
    }

    @FXML
    private void seDeconnecter(ActionEvent actionEvent) throws IOException {
        Session.clear();
        ViewLauncher.forward(actionEvent, "LoginFXML.fxml", this.getClass());
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
        System.out.println("hello in Rmi connect ");
        try {
            r = Naming.lookup("rmi://" + adr + ":5655/demandeFacade");
            demandeFacade = ((DemandeFacadeContrat) r);
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        
        String name = sal.getNom() + " " + sal.getPrenom();
        connect_rmi();
        fullname.setText(name);
        initHelper();

    }

    public TableView getCongeTableView() {
        return congeTableView;
    }

    public void setCongeTableView(TableView congeTableView) {
        this.congeTableView = congeTableView;
    }

}
