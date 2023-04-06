package AVegetableGarden.vueControleur.vues.components;

import AVegetableGarden.modele.legumes.Legume;
import AVegetableGarden.modele.potagers.Potager;
import AVegetableGarden.modele.potagers.cases.CaseCultivable;
import AVegetableGarden.modele.potagers.cases.CaseNonCultivable;
import AVegetableGarden.vueControleur.VueManager;
import AVegetableGarden.vueControleur.icon.IconNames;
import AVegetableGarden.vueControleur.vues.VueControleurPotager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static AVegetableGarden.vueControleur.icon.IconNames.*;

public class PotagerGrid {

    private final int sizeX;
    private final int sizeY;
    private final Potager potager; // référence sur une classe de modèle : permet d'accéder aux données du modèle pour le rafraichissement, permet de communiquer les actions clavier (ou souris)
    private Cases cases;

    private ButtonsPanel buttonsPanel;


    public PotagerGrid(Potager potager, ButtonsPanel buttonsPanel) {
        this.sizeX = potager.getSizeX();
        this.sizeY = potager.getSizeY();
        this.potager = potager;
        this.buttonsPanel = buttonsPanel;
    }

    public JComponent getGridPanel() {
        GridLayout gridLayout = new GridLayout(sizeY, sizeX);
        gridLayout.setHgap(2);
        gridLayout.setVgap(2);
        JComponent grilleCases = new JPanel(gridLayout);

        cases = new Cases(sizeX, sizeY);

        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                grilleCases.add(cases.getCase(new Point(x, y)));
            }
        }

        addListenerCases();
        return grilleCases;
    }

    private void addListenerCases() {
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                final int xx = x; // constantes utiles au fonctionnement de la classe anonyme
                final int yy = y;
                cases.getCase(new Point(x, y)).addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        String actionLog = potager.actionUtilisateur(buttonsPanel.getAction(), buttonsPanel.getCurrentVariete(), new Point(xx, yy));
                        if (actionLog != null) {
                            VueManager.getInstance().showErrorWindow(actionLog);
                        }
                    }
                });
            }
        }
    }

    private void updateCase(int x, int y) {
        if (potager.getPlateau()[x][y] instanceof CaseCultivable) {
            setLegumeCase(x, y);
        } else if (potager.getPlateau()[x][y] instanceof CaseNonCultivable) {
            cases.setIconCase(new Point(x, y), MUR);
        } else {
            cases.setIconCase(new Point(x, y), VIDE);

        }
    }

    public void updatePotagerVue(VueControleurPotager vueControleurPotager) {
        vueControleurPotager.infoPanel.updateInfos(vueControleurPotager.vueControleurEnsemblePotagers.simulateurPotager.simulateurMeteo);
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                updateCase(x, y);
            }
        }
    }

    private void setLegumeCase(int x, int y) {
        Legume legume = ((CaseCultivable) potager.getPlateau()[x][y]).getLegume();
        if (legume == null) {
            cases.setIconCase(new Point(x, y), TERRE);
            return;
        }

        IconNames icon;
        float legumeVie = (float) legume.getEtatVie();
        float legumeCroissance = (float) legume.getEtatCroissance();

        switch (legume.getVariete()) {
            case SALADE -> icon = SALADE;
            case CAROTTE -> icon = CAROTTE;
            default -> throw new IllegalStateException("Unexpected value: " + legume.getVariete());
        }

        cases.setIconCase(new Point(x, y), icon, legumeVie, legumeCroissance);

    }
}