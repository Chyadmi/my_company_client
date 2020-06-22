package helper;

import bean.DemandeAvance;
import java.util.List;

import javafx.scene.control.TableView;

public class DemandeAvanceDirecteurFxHelper extends AbstractFxHelper<DemandeAvance> {

    private static AbstractFxHelperItem[] titres;

    static {
        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("DATE DEMANDE", "date"),
            new AbstractFxHelperItem("SALARIE", "salarie"),
            new AbstractFxHelperItem("ETAT", "etat")
        };
    }

    public DemandeAvanceDirecteurFxHelper(TableView<DemandeAvance> table, List<DemandeAvance> list) {
        super(titres, table, list);
    }

    public DemandeAvanceDirecteurFxHelper(TableView<DemandeAvance> table) {
        super(titres, table);
    }

}
