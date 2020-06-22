/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompany_v1.pkg1;

import bean.DemandeAugmentation;
import bean.DemandeAvance;
import bean.DemandeConge;
import bean.Mois;
import bean.Salarie;
import contrat.DemandeFacadeContrat;
import contrat.SalarieFacadeContrat;
import helper.DemandeAugmentationDirecteurFxHelper;
import helper.DemandeAvanceDirecteurFxHelper;
import helper.DemandeCongeDirecteurFxHelper;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.Session;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class DirecteurDemandeFXMLController implements Initializable {

    Remote r = null ;
    Salarie sal = ((Salarie) Session.getAttribut("connectedUser"));
    
    DemandeFacadeContrat demandeFacade ;
    SalarieFacadeContrat salarieFacade ;

    private DemandeCongeDirecteurFxHelper demandeCongeDirecteurFxHelper;
    private DemandeAvanceDirecteurFxHelper demandeAvanceDirecteurFxHelper;
    private DemandeAugmentationDirecteurFxHelper demandeAugmentationDirecteurFxHelper;

    @FXML
    private Label close;

    @FXML
    private Text dateDebutConge;
    @FXML
    private Text dateFinConge;
    @FXML
    private Text messageConge;

    @FXML
    private Text moisAvance;
    @FXML
    private Text pourcentAvance;
    @FXML
    private Text salaireAvance;
    @FXML
    private Text messageAvance;

    @FXML
    private Text pourcentageAug;
    @FXML
    private Text salaireActuel;
    @FXML
    private Text salaireApres;
    @FXML
    private Text messageAug;

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
            demandeCongeDirecteurFxHelper = new DemandeCongeDirecteurFxHelper(congeTableView, demandeFacade.getPendingConge());
            demandeAvanceDirecteurFxHelper = new DemandeAvanceDirecteurFxHelper(avanceTableView, demandeFacade.getPendingAvance());
            demandeAugmentationDirecteurFxHelper = new DemandeAugmentationDirecteurFxHelper(augmentationTableView, demandeFacade.getPendingAugmentation());
        } catch (RemoteException ex) {
            Logger.getLogger(DirecteurDemandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void toMesDemandes(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DirecteurDemandeFXML.fxml", this.getClass());
    }

    @FXML
    private void toBoiteReception(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "BoiteDirecteurFXML.fxml", this.getClass());
    }

    @FXML
    private void toProfile(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DirecteurAccueilFXML.fxml", this.getClass());
    }

    @FXML
    private void seDeconnecter(ActionEvent actionEvent) throws IOException {
        Session.clear();
        ViewLauncher.forward(actionEvent, "LoginFXML.fxml", this.getClass());
    }
    
    @FXML
    private void toDepartement(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "DirecteurDepartementFXML.fxml", this.getClass());
    }

    public void mouseClickedTableConge() {
        DemandeConge dc = demandeCongeDirecteurFxHelper.getSelected();
        if (dc != null) {
            Session.updateAttribute(dc, "demandeConge");
            messageConge.setText(dc.getCommentaire());
            dateDebutConge.setText(dc.getDateDebut() + "");
            dateFinConge.setText(dc.getDateFin() + "");
        }

    }

    public void mouseClickedTableAvance() {
        DemandeAvance da = demandeAvanceDirecteurFxHelper.getSelected();
        if (da != null) {
            Session.updateAttribute(da, "demandeAvance");
            messageAvance.setText(da.getCommentaire());
            moisAvance.setText(da.getMois().toString());
            pourcentAvance.setText(da.getPourcentage() + " %");
            salaireAvance.setText(da.getSalarie().getSalaire() + " MAD");
        }

    }

    public void mouseClickedTableAug() {
        DemandeAugmentation da = demandeAugmentationDirecteurFxHelper.getSelected();
        if (da != null) {
            Session.updateAttribute(da, "demandeAugmentation");
            double salActuel = da.getSalarie().getSalaire();
            int pourcentage = da.getPourcentage();
            double nvSal = (salActuel + (salActuel * ((double) pourcentage / 100)));
            System.out.println("################## " + nvSal);
            messageAug.setText(da.getCommentaire());
            pourcentageAug.setText(pourcentage + " %");
            salaireActuel.setText(salActuel + " MAD");
            salaireApres.setText(nvSal + " MAD");
        }

    }

    public void acceptDemandeConge() {
        DemandeConge dc = demandeCongeDirecteurFxHelper.getSelected();
        if (dc != null) {
            try {
                demandeFacade.updateDemande("demandeConge", 1, dc.getId());
                demandeCongeDirecteurFxHelper.remove(dc);
                clearConge();
            } catch (RemoteException ex) {
                Logger.getLogger(DirecteurDemandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void refuseDemandeConge() {
        DemandeConge dc = demandeCongeDirecteurFxHelper.getSelected();
        if (dc != null) {
            try {
                demandeFacade.updateDemande("demandeConge", 2, dc.getId());
                demandeCongeDirecteurFxHelper.remove(dc);
                clearConge();
            } catch (RemoteException ex) {
                Logger.getLogger(DirecteurDemandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void acceptDemandeAvance() {
        DemandeAvance da = demandeAvanceDirecteurFxHelper.getSelected();
        if (da != null) {
            try {
                demandeFacade.updateDemande("demandeAvance", 1, da.getId());
                demandeAvanceDirecteurFxHelper.remove(da);
                clearAvance();
            } catch (RemoteException ex) {
                Logger.getLogger(DirecteurDemandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void refuseDemandeAvance() {
        DemandeAvance da = demandeAvanceDirecteurFxHelper.getSelected();
        if (da != null) {
            try {
                demandeFacade.updateDemande("demandeAvance", 2, da.getId());
                demandeAvanceDirecteurFxHelper.remove(da);
                clearAvance();
            } catch (RemoteException ex) {
                Logger.getLogger(DirecteurDemandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void acceptDemandeAug() {
        DemandeAugmentation da = demandeAugmentationDirecteurFxHelper.getSelected();
        if (da != null) {
            try {
                demandeFacade.updateDemande("demandeAugmentation", 1, da.getId());
                demandeAugmentationDirecteurFxHelper.remove(da);
                clearAug();
            } catch (RemoteException ex) {
                Logger.getLogger(DirecteurDemandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void refuseDemandeAug() {
        DemandeAugmentation da = demandeAugmentationDirecteurFxHelper.getSelected();
        if (da != null) {
            try {
                demandeFacade.updateDemande("demandeAugmentation", 2, da.getId());
                demandeAugmentationDirecteurFxHelper.remove(da);
                clearAug();
            } catch (RemoteException ex) {
                Logger.getLogger(DirecteurDemandeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void clearConge() {
        messageConge.setText("");
        dateDebutConge.setText("");
        dateFinConge.setText("");
    }

    public void clearAvance() {
        messageAvance.setText("");
        moisAvance.setText("");
        pourcentAvance.setText("");
        salaireAvance.setText("");
    }

    public void clearAug() {
        messageAug.setText("");
        pourcentageAug.setText("");
        salaireActuel.setText("");
        salaireApres.setText("");
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
            r = Naming.lookup("rmi://" + adr + ":5655/salarieFacade");
            salarieFacade = ((SalarieFacadeContrat) r);
            r = Naming.lookup("rmi://" + adr + ":5655/demandeFacade");
            demandeFacade = ((DemandeFacadeContrat) r);
            
            
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
        

    }

}
