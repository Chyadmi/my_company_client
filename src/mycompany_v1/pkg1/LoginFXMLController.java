/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompany_v1.pkg1;

import bean.Salarie;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import contrat.SalarieFacadeContrat;
import org.apache.commons.validator.routines.InetAddressValidator;
import util.Session;

/**
 * FXML Controller class
 *
 * @author CHAACHAI Youssef
 */
public class LoginFXMLController implements Initializable {

    Remote r = null;
    SalarieFacadeContrat sf;

    @FXML
    private Button loginButton;

    @FXML
    private Label close;

    @FXML
    private Label ip;

    @FXML
    private Label adr_ok;

    @FXML
    private Label adr_null;

    @FXML
    private Button minimize;
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private TextField login;
    @FXML
    private PasswordField password;

    @FXML
    public void connect(ActionEvent actionEvent) throws IOException {
        int res = sf.login(login.getText(), password.getText());
        switch (res) {
            case 1:
                System.out.println("welcome");
                Salarie sal = sf.getSalarieByLogin(login.getText());
                Session.createAtrribute(sal, "connectedUser");
                switch (sal.getRole().getId()) {
                    case 1:
                        System.out.println("vous etes le directeur");
//                        connect_rmi();
                        ViewLauncher.forward(actionEvent, "DirecteurAccueilFXML.fxml", this.getClass());
                        break;
                    case 2:
                        System.out.println("vous etes un responnsable");
//                        connect_rmi();
                        ViewLauncher.forward(actionEvent, "EmployeDemandeFXML.fxml", this.getClass());                      
                        break;
                    case 3:
                        System.out.println("vous etes un employe");
//                        connect_rmi();
                        ViewLauncher.forward(actionEvent, "EmployeAccueilFXML.fxml", this.getClass());
                        break;
                    default:
                        break;
                }
                break;
            case -1:
                JOptionPane.showMessageDialog(null, "L'identifiant ou le mot de passe est incorrect !", "Echec de la connexion", JOptionPane.ERROR_MESSAGE);
                break;
            default:
                JOptionPane.showMessageDialog(null, "Something bad happened, please try again later !", "Error", JOptionPane.ERROR_MESSAGE);
                break;
        }
    }

    public void connect_rmi() {
        String adr = (String) Session.getAttribut("adr_ip");
        System.out.println("hello");
        try {
            r = Naming.lookup("rmi://" + adr + ":5655/salarieFacade");
            sf = ((SalarieFacadeContrat) r);
            if (r != null) {
                loginButton.setDisable(false);
                adr_ok.setVisible(true);
                adr_null.setVisible(false);
                System.out.println("OKKKKKKK");
            }
        } catch (Exception ex) {
            loginButton.setDisable(true);
            adr_ok.setVisible(false);
            adr_null.setVisible(true);
            System.out.println("WALO");
//            Logger.getLogger(LoginFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    private boolean testChamps() {
        if (login.getText().equals("")) {
            return false;
        } else if (password.getText().equals("")) {
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
    public void change_ip() {
        String m = JOptionPane.showInputDialog("Adresse IP du serveur");
        InetAddressValidator validator = InetAddressValidator.getInstance();
        if (validator.isValidInet4Address(m)) {
//            System.out.println("VALIIIID");
            ip.setText(m);
            Session.updateAttribute(m, "adr_ip");
            connect_rmi();
        } else {
            JOptionPane.showMessageDialog(null, "L'adresse que vous avez saisi n'est pas valide !", "Echec", JOptionPane.ERROR_MESSAGE);
//            System.out.println("NOT VALIIID");
        }
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
        String adr = "192.168.190.128";
        ip.setText(adr);
        Session.updateAttribute(adr, "adr_ip");
        connect_rmi();
    }

}
