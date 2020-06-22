package helper;

import bean.DemandeAugmentation;
import java.util.List;

import javafx.scene.control.TableView;

public class DemandeAugmentationDirecteurFxHelper extends AbstractFxHelper<DemandeAugmentation> {

    private static AbstractFxHelperItem[] titres;

    static {
        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("DATE DEMANDE", "date"),
            new AbstractFxHelperItem("SALARIE", "salarie"),
            new AbstractFxHelperItem("ETAT", "etat")
        };
    }

    public DemandeAugmentationDirecteurFxHelper(TableView<DemandeAugmentation> table, List<DemandeAugmentation> list) {
        super(titres, table, list);
    }

    public DemandeAugmentationDirecteurFxHelper(TableView<DemandeAugmentation> table) {
        super(titres, table);
    }

}
