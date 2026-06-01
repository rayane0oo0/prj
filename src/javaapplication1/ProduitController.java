package javaapplication1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Produit;
import service.ProduitService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitController implements Initializable {

    @FXML private TextField txtCode;
    @FXML private TextField txtLibelle;
    @FXML private ComboBox<String> comboType;
    @FXML private TextField txtQuantite;
    @FXML private CheckBox chkDispo;
    @FXML private TextField txtRecherche;
    @FXML private TableView<Produit> tableProduits;
    @FXML private TableColumn<Produit, Integer> colId;
    @FXML private TableColumn<Produit, String> colCode;
    @FXML private TableColumn<Produit, String> colLibelle;
    @FXML private TableColumn<Produit, String> colType;
    @FXML private TableColumn<Produit, Integer> colQuantite;
    @FXML private TableColumn<Produit, Boolean> colDispo;

    private ProduitService service = ProduitService.getInstance();
    private ObservableList<Produit> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colLibelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colQuantite.setCellValueFactory(new PropertyValueFactory<>("quantiteStock"));
        colDispo.setCellValueFactory(new PropertyValueFactory<>("disponibilite"));
        
        comboType.getItems().addAll("Electronique", "Alimentaire", "Vetements", "Mobilier", "Fruit", "Autre");
        comboType.getSelectionModel().selectFirst();
        
        tableProduits.setItems(data);
        
        tableProduits.getSelectionModel().selectedItemProperty().addListener((obs, ancien, nouveau) -> {
            if (nouveau != null) remplirFormulaire(nouveau);
        });
        
        txtRecherche.textProperty().addListener((obs, ancien, nouveau) -> {
            filtrer(nouveau);
        });
        
        chargerListe();
    }
    
    private void chargerListe() {
        data.clear();
        data.addAll(service.getAllProduits());
    }
    
    private void filtrer(String mot) {
        if (mot == null || mot.trim().isEmpty()) {
            chargerListe();
        } else {
            data.clear();
            data.addAll(service.rechercher(mot));
        }
    }
    
    @FXML
    private void handleAjouter() {
        if (!formulaireEstValide()) return;
        
        Produit p = new Produit();
        p.setCode(txtCode.getText().trim());
        p.setLibelle(txtLibelle.getText().trim());
        p.setType(comboType.getValue());
        p.setQuantiteStock(Integer.parseInt(txtQuantite.getText().trim()));
        p.setDisponibilite(chkDispo.isSelected());
        
        if (service.ajouterProduit(p)) {
            afficherMessage("Succes", "Produit ajoute !");
            viderFormulaire();
            chargerListe();
        } else {
            afficherErreur("Erreur", "Code déjà existant");
        }
    }
    
    @FXML
    private void handleModifier() {
        Produit selection = tableProduits.getSelectionModel().getSelectedItem();
        if (selection == null) {
            afficherErreur("Erreur", "Sélectionnez un produit");
            return;
        }
        if (!formulaireEstValide()) return;
        
        selection.setCode(txtCode.getText().trim());
        selection.setLibelle(txtLibelle.getText().trim());
        selection.setType(comboType.getValue());
        selection.setQuantiteStock(Integer.parseInt(txtQuantite.getText().trim()));
        selection.setDisponibilite(chkDispo.isSelected());
        
        if (service.modifierProduit(selection)) {
            afficherMessage("Succès", "Produit modifié !");
            viderFormulaire();
            chargerListe();
        } else {
            afficherErreur("Erreur", "Impossible de modifier");
        }
    }
    
    @FXML
    private void handleSupprimer() {
        Produit selection = tableProduits.getSelectionModel().getSelectedItem();
        if (selection == null) {
            afficherErreur("Erreur", "Sélectionnez un produit");
            return;
        }
        
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setContentText("Supprimer " + selection.getLibelle() + " ?");
        
        if (confirmation.showAndWait().get() == ButtonType.OK) {
            if (service.supprimerProduit(selection)) {
                afficherMessage("Succès", "Produit supprimé");
                viderFormulaire();
                chargerListe();
            } else {
                afficherErreur("Erreur", "Impossible de supprimer");
            }
        }
    }
    
    @FXML
    private void handleActualiser() {
        viderFormulaire();
        txtRecherche.clear();
        chargerListe();
    }
    
    private void remplirFormulaire(Produit p) {
        txtCode.setText(p.getCode());
        txtLibelle.setText(p.getLibelle());
        comboType.setValue(p.getType());
        txtQuantite.setText(String.valueOf(p.getQuantiteStock()));
        chkDispo.setSelected(p.isDisponibilite());
    }
    
    private void viderFormulaire(){
        txtCode.clear();
        txtLibelle.clear();
        comboType.getSelectionModel().selectFirst();
        txtQuantite.clear();
        chkDispo.setSelected(false);
        tableProduits.getSelectionModel().clearSelection();
    }
    
    private boolean formulaireEstValide() {
        if (txtCode.getText().trim().isEmpty()) {
            afficherErreur("Validation", "Code obligatoire");
            return false;
        }
        if (txtLibelle.getText().trim().isEmpty()){
            afficherErreur("Validation", "Libelle obligatoire");
            return false;
        }
        try {
            int qte = Integer.parseInt(txtQuantite.getText().trim());
            if (qte < 0){
                afficherErreur("Validation", "Quantite negative");
                return false;
            }
        } catch (NumberFormatException e) {
            afficherErreur("Validation", "Quantite doit etre un nombre");
            return false;
        }
        return true;
    }
    
    private void afficherMessage(String titre, String texte) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(texte);
        alert.showAndWait();
    }
    
    private void afficherErreur(String titre, String texte) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setContentText(texte);
        alert.showAndWait();
    }
}