package helper;

import bean.BoiteMail;
import bean.DemandeAugmentation;
import java.util.List;

import javafx.scene.control.TableView;

public class ListeMessagesEnvoyerFXHelper extends AbstractFxHelper<BoiteMail> {

    private static AbstractFxHelperItem[] titres;

    static {
        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("Objet", "objet"),
            new AbstractFxHelperItem("Envoyé à ..", "reciever"),
            new AbstractFxHelperItem("Date", "dateEnvoi")
            
        };
    }

    public ListeMessagesEnvoyerFXHelper(TableView<BoiteMail> table, List<BoiteMail> list) {
        super(titres, table, list);
    }

    public ListeMessagesEnvoyerFXHelper(TableView<BoiteMail> table) {
        super(titres, table);
    }

}
