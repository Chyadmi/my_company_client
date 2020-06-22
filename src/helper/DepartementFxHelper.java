package helper;

import bean.Demande;
import bean.DemandeConge;
import bean.Departement;
import java.util.List;

import javafx.scene.control.TableView;

public class DepartementFxHelper extends AbstractFxHelper<Departement> {

    private static AbstractFxHelperItem[] titres;

    static {
        titres = new AbstractFxHelperItem[]{
            new AbstractFxHelperItem("NUMERO DE DEPARTEMENT", "id"),
            new AbstractFxHelperItem("NOM", "nom"),
            new AbstractFxHelperItem("RESPONSABLE", "responsable")
        };
    }

    public DepartementFxHelper(TableView<Departement> table, List<Departement> list) {
        super(titres, table, list);
    }

    public DepartementFxHelper(TableView<Departement> table) {
        super(titres, table);
    }

}
