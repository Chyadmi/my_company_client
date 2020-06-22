package helper;

import bean.BoiteMail;
import bean.DemandeAugmentation;
import java.util.List;

import javafx.scene.control.TableView;

public class ListeMessagesRecueFXHelper extends AbstractFxHelper<BoiteMail> {

    private static AbstractFxHelperItem[] titres;

    static {
        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("Salarié ", "sender"),
            new AbstractFxHelperItem("Objet", "objet"),
            new AbstractFxHelperItem("DATE", "dateEnvoi")

        };
    }

    public ListeMessagesRecueFXHelper(TableView<BoiteMail> table, List<BoiteMail> list) {
        super(titres, table, list);
    }

    public ListeMessagesRecueFXHelper(TableView<BoiteMail> table) {
        super(titres, table);
    }

}
