/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompany_v1.pkg1;

import bean.Salarie;
import contrat.DepartementFacadeContrat;
import contrat.SalarieFacadeContrat;
import helper.DemandeAugmentationFxHelper;
import helper.DemandeAvanceFxHelper;
import helper.DemandeCongeFxHelper;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import sun.security.util.Password;
import util.Session;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class EmployeAccueilFXMLController implements Initializable {
   
    
    
    Remote r = null;
//    DemandeFacadeContrat demandeFacade ;
    SalarieFacadeContrat salarieFacade ;

    private DemandeCongeFxHelper demandeCongeFxHelper;
    private DemandeAvanceFxHelper demandeAvanceFxHelper;
    private DemandeAugmentationFxHelper demandeAugmentationFxHelper;

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
    private TextField login;
    @FXML
    private PasswordField motdepasse;
    @FXML
    private TextField departement;
    @FXML
    private TextField fonction;
    @FXML
    private TextField salaire;

    private void initForm() {
        Salarie sal = ((Salarie) Session.getAttribut("connectedUser"));
        nom.setText(sal.getNom());
        prenom.setText(sal.getPrenom());
        telephone.setText(sal.getTelephone());
        login.setText(sal.getLogin());
        departement.setText(sal.getDepartement().getNom());
        fonction.setText(sal.getRole().getLibelle());
        salaire.setText(sal.getSalaire() + "");
        motdepasse.setText(sal.getPassword());
    }

    @FXML
    private void toMesDemandes(ActionEvent actionEvent) throws IOException {
        System.out.println("Heloo");
        ViewLauncher.forward(actionEvent, "EmployeDemandeFXML.fxml", this.getClass());
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

    public void updateEmp(ActionEvent actionEvent) throws IOException {
        Salarie sal = (Salarie) Session.getAttribut("connectedUser");
        int res = salarieFacade.update(nom.getText(), prenom.getText(), telephone.getText(), motdepasse.getText(), sal.getId());
        if (res == 1) {
            Salarie ss = salarieFacade.getSalarieById(sal.getId());
            Session.updateAttribute(ss, "connectedUser");
            alert("Votre profil a été modifié avec succès !", "DONE!");
        } else {
            JOptionPane.showMessageDialog(null, "An error has occures, kayn chi mochkil", "Echec", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void alert(String contenttext, String headertext) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(headertext);
        alert.setContentText(contenttext);
        alert.setTitle("Information dialog");
        alert.show();
    }
    public void connect_rmi() {
        String adr = (String)Session.getAttribut("adr_ip");
        System.out.println("hello in Rmi connect ");
        try {
            r = Naming.lookup("rmi://" + adr + ":5655/salarieFacade");
            salarieFacade = ((SalarieFacadeContrat) r);
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

        Salarie sal = ((Salarie) Session.getAttribut("connectedUser"));
        connect_rmi();

        String name = sal.getNom() + " " + sal.getPrenom();
        fullname.setText(name);
        initForm();
    }

}
