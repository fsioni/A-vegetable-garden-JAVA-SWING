import com.formdev.flatlaf.FlatLightLaf;
import modele.Ordonnanceur;
import modele.SimulateurPotager;
import vueControleur.VueControleurPotager;

public class Main {
    public static void main(String[] args) {
        SimulateurPotager simulateurPotager = new SimulateurPotager();

        FlatLightLaf.setup(); // look and feel
        VueControleurPotager vc = new VueControleurPotager(simulateurPotager);
        vc.setVisible(true);

        Ordonnanceur.getInstance().addObserver(vc);
        Ordonnanceur.getInstance().start(300);

    }
}
