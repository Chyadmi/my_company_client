package helper;

import bean.DemandeAugmentation;
import bean.DemandeConge;
import java.util.List;

import javafx.scene.control.TableView;

public class DemandeAugmentationFxHelper extends AbstractFxHelper<DemandeAugmentation> {

    private static AbstractFxHelperItem[] titres;

    static {
        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("DATE DEMANDE", "date"),
            new AbstractFxHelperItem("POURCENTAGE", "pourcentage"),
            new AbstractFxHelperItem("COMMENTAIRE", "commentaire"),
            new AbstractFxHelperItem("ETAT DEMANDE", "etat")
        };
    }

    public DemandeAugmentationFxHelper(TableView<DemandeAugmentation> table, List<DemandeAugmentation> list) {
        super(titres, table, list);
    }

    public DemandeAugmentationFxHelper(TableView<DemandeAugmentation> table) {
        super(titres, table);
    }

}
