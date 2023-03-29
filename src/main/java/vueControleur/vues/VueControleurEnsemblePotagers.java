package vueControleur.vues;

import modele.Ordonnanceur;
import modele.potagers.SimulateurPotager;
import vueControleur.VueManager;
import vueControleur.vues.components.InfoPanel;
import vueControleur.vues.components.TimeSlider;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class VueControleurEnsemblePotagers extends JPanel implements Observer, VueControleur {

    public final SimulateurPotager simulateurPotager;

    private JButton[] listePotagersButton;
    private JButton buyPotagerButton;

    private transient InfoPanel infoPanel;


    public VueControleurEnsemblePotagers(SimulateurPotager simulateurPotager) {
        super();
        this.simulateurPotager = simulateurPotager;
        this.setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        addComponents();
        Ordonnanceur.getInstance().addObserver(this);
    }

    public void addComponents() {
        add(new JLabel("Liste des potagers"), BorderLayout.NORTH);
        add(getButtonsPotagers(), BorderLayout.CENTER);
        infoPanel = new InfoPanel();
        add(infoPanel.getInfoPanel(), BorderLayout.EAST);
        add(new TimeSlider(), BorderLayout.SOUTH);
    }

    @Override
    public void addEventListeners() {
        addEventListenerButtonsPotagers();
        addEventListenerBuyPotagerButton();
    }

    private void addEventListenerBuyPotagerButton() {
        buyPotagerButton.addActionListener(e -> {
            boolean isBuySuccessful = simulateurPotager.buyPotager();
            updateDisplay();

            if (!isBuySuccessful) {
                JOptionPane.showMessageDialog(null, "Vous n'avez pas assez de coins pour acheter un potager");
            }
        });
    }

    @Override
    public void updateDisplay() {
        infoPanel.updateInfos(simulateurPotager.simulateurMeteo);
    }

    public void addEventListenerButtonsPotagers() {
        for (int i = 0; i < listePotagersButton.length; i++) {
            int potagerIndex = i;
            listePotagersButton[i].addActionListener(e -> {
                VueControleurPotager vueControleurPotager = new VueControleurPotager(simulateurPotager.getListePotagers().get(potagerIndex), this);
                VueManager.getInstance().setVueControleurPotager(vueControleurPotager);
            });
        }
    }

    public JComponent getButtonsPotagers() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        listePotagersButton = new JButton[simulateurPotager.getNbPotagers()];
        for (int i = 0; i < simulateurPotager.getNbPotagers(); i++) {
            listePotagersButton[i] = new JButton("Potager " + i);
            panel.add(listePotagersButton[i]);
        }
        buyPotagerButton = new JButton("Acheter un potager : " + SimulateurPotager.POTAGER_PRICE + " pièces");
        buyPotagerButton.setBackground(Color.cyan);
        panel.add(buyPotagerButton);
        addEventListeners();
        return panel;
    }

    @Override
    public void update(Observable observable, Object o) {
        updateDisplay();
    }
}
