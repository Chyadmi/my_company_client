/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompany_v1.pkg1;

import bean.Salarie;
import contrat.MessagesFacadeContrat;
import contrat.SalarieFacadeContrat;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import util.Session;

/**
 * FXML Controller class
 *
 * @author hamza
 */
public class AjouterMessageFXMLController implements Initializable {
    
    Salarie sal = ((Salarie) Session.getAttribut("connectedUser"));
    Remote r = null ;
    MessagesFacadeContrat messageFacade;
    SalarieFacadeContrat sf;

    @FXML
    private Label close;
    @FXML
    private TextField loginRecepTF;
    @FXML
    private TextArea objetTA;
    @FXML
    private TextArea messageTA;
    @FXML
    private Button annulerBTN;
    @FXML
    private Button envoyerBTN;

    @FXML
    public void envoyerMessage(ActionEvent actionEvent) throws IOException {
        String login = loginRecepTF.getText();
        String objet = objetTA.getText();
        String message = messageTA.getText();
        int res = messageFacade.envoyerMessage(sal,objet, message, login);
        if (res == 1) {
            ViewLauncher.forward(actionEvent, "BoiteEmployeFXML.fxml", this.getClass());
        } else {
            JOptionPane.showMessageDialog(null, "L'utilisateur n'existe pas ! ", "Echec", JOptionPane.ERROR_MESSAGE);
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

    @FXML
    public void annuler(ActionEvent actionEvent) throws IOException {
        Salarie sal = (Salarie) Session.getAttribut("connectedUser");
        if (sal.getRole().getId() == 1) {
            ViewLauncher.forward(actionEvent, "BoiteDirecteurFXML.fxml", this.getClass());
        } else if (sal.getRole().getId() == 3) {
            ViewLauncher.forward(actionEvent, "BoiteEmployeFXML.fxml", this.getClass());
        }

    }
    public void connect_rmi() {
        String adr = (String)Session.getAttribut("adr_ip");
        System.out.println(adr);
        
        System.out.println("hello in Rmi connect Boite Emp ");
        try {
            r = Naming.lookup("rmi://" + adr + ":5655/messageFacade");
            messageFacade = ((MessagesFacadeContrat) r);
            r = Naming.lookup("rmi://" + adr + ":5655/salarieFacade");
            sf = ((SalarieFacadeContrat) r) ;
            

            
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
        connect_rmi();
        // TODO
    }

}
