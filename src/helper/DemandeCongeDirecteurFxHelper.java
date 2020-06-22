package helper;

import bean.Demande;
import bean.DemandeConge;
import java.util.List;

import javafx.scene.control.TableView;

public class DemandeCongeDirecteurFxHelper extends AbstractFxHelper<DemandeConge> {

    private static AbstractFxHelperItem[] titres;

    static {
        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("DATE DEMANDE", "date"),
            new AbstractFxHelperItem("SALARIE", "salarie"),
            new AbstractFxHelperItem("ETAT", "etat")
        };
    }

    public DemandeCongeDirecteurFxHelper(TableView<DemandeConge> table, List<DemandeConge> list) {
        super(titres, table, list);
    }

    public DemandeCongeDirecteurFxHelper(TableView<DemandeConge> table) {
        super(titres, table);
    }

}
