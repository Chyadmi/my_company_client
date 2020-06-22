/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mycompany_v1.pkg1;

import bean.BoiteMail;
import bean.Salarie;
import contrat.DemandeFacadeContrat;
import contrat.MessagesFacadeContrat;
import helper.ListeMessagesEnvoyerFXHelper;
import helper.ListeMessagesRecueFXHelper;
import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
public class BoiteEmployeFXMLController implements Initializable {

    Salarie sal = ((Salarie) Session.getAttribut("connectedUser"));
    Remote r = null ;
    //CategorieDiabeteFacade categorieDiabeteFacade = new CategorieDiabeteFacade();
    //MessagesFacade conseilsFacade = new MessagesFacade();
    MessagesFacadeContrat messageFacade;
    private ListeMessagesRecueFXHelper listeMessagesRecueFXHelper;
    private ListeMessagesEnvoyerFXHelper listeMessagesEnvoyerFXelper;
    // private CategorieDiabeteFxHelper categorieDiabeteFxHelper;
    @FXML
    private Label close;
    @FXML
    private Button minimize;
    @FXML
    private TableView MEtable;
    @FXML
    private TableView MRtable;
    @FXML
    private Label fullname;

    @FXML
    private Text message;
    @FXML
    private Text objet;
    @FXML
    private Text sender;
    @FXML
    private Text reciever;
    @FXML
    private Text date;
    @FXML
    private Label idMessage;
    @FXML
    private Label typeMessage;

    /**
     * Initializes the controller class.
     */
    private void initHelper() {
        try {
            listeMessagesRecueFXHelper = new ListeMessagesRecueFXHelper(MRtable, messageFacade.getMessagesByRecieverID(sal));
            listeMessagesEnvoyerFXelper = new ListeMessagesEnvoyerFXHelper(MEtable, messageFacade.getMessagesBySenderID(sal));
        } catch (RemoteException ex) {
            Logger.getLogger(BoiteEmployeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void toMesDemandes(ActionEvent actionEvent) throws IOException {
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

    public void deleteMessage() {
//        BoiteMail msg = (BoiteMail) MRtable.getSelectionModel().getSelectedItem();
        if (!idMessage.equals("")) {
            Alert aa = new Alert(Alert.AlertType.CONFIRMATION);
            aa.setHeaderText("ATTENTION!!");
            aa.setContentText("Voulez-vous vraiment supprimer ce message ?");
            aa.setTitle("ATTENTION!!");
            Optional<ButtonType> result = aa.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    messageFacade.supprimerMessage(Long.valueOf(idMessage.getText()));
                    
                    if (typeMessage.getText().equals("1")) {
                        listeMessagesRecueFXHelper.remove(listeMessagesRecueFXHelper.getSelected());
                    } else if (typeMessage.getText().equals("2")) {
                        listeMessagesEnvoyerFXelper.remove(listeMessagesEnvoyerFXelper.getSelected());
                    }
                    clear();
                } catch (RemoteException ex) {
                    Logger.getLogger(BoiteEmployeFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void clear() {
        message.setText("");
        objet.setText("");
        sender.setText("");
        reciever.setText("");
        date.setText("");
        idMessage.setText("");
        typeMessage.setText("");
    }

    public void mouseClickedTable1() {
        BoiteMail bm = listeMessagesRecueFXHelper.getSelected();
        if (bm != null) {
            message.setText(bm.getMessage());
            objet.setText(bm.getObjet());
            sender.setText(bm.getSender().getNom() + " " + bm.getSender().getPrenom() + " <" + bm.getSender().getLogin() + ">");
            reciever.setText(bm.getReciever().getNom() + " " + bm.getReciever().getPrenom() + " <" + bm.getReciever().getLogin() + ">");
            date.setText(bm.getDateEnvoi() + "");
            idMessage.setText(bm.getId() + "");
            typeMessage.setText("1");
        }
    }

    public void mouseClickedTable2() {
        BoiteMail bm = listeMessagesEnvoyerFXelper.getSelected();
        if (bm != null) {
            message.setText(bm.getMessage());
            objet.setText(bm.getObjet());
            sender.setText(bm.getSender().getNom() + " " + bm.getSender().getPrenom() + " <" + bm.getSender().getLogin() + ">");
            reciever.setText(bm.getReciever().getNom() + " " + bm.getReciever().getPrenom() + " <" + bm.getReciever().getLogin() + ">");
            date.setText(bm.getDateEnvoi() + "");
            idMessage.setText(bm.getId() + "");
            typeMessage.setText("2");
        }
    }

    @FXML
    private void envoyerMessage(ActionEvent actionEvent) throws IOException {
        ViewLauncher.forward(actionEvent, "AjouterMessageFXML.fxml", this.getClass());
    }
    public void connect_rmi() {
        String adr = (String)Session.getAttribut("adr_ip");
        System.out.println(adr);
        
        System.out.println("hello in Rmi connect Boite Emp ");
        try {
            r = Naming.lookup("rmi://" + adr + ":5655/messageFacade");
            messageFacade = ((MessagesFacadeContrat) r);
            
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        connect_rmi();
        String name = sal.getNom() + " " + sal.getPrenom();

        fullname.setText(name);

        initHelper();
        // TODO
    }

}
