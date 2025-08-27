package org.main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import org.main.enums.ePriorita;
import org.main.enums.eTypProhl;

import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class FXMLController implements Initializable {
    @javafx.fxml.FXML
    private ListView<Obec> listView;
    @javafx.fxml.FXML
    private Button generovatBtn;
    @javafx.fxml.FXML
    private Button exportBtn;
    @javafx.fxml.FXML
    private Button importBtn;
    @javafx.fxml.FXML
    private Button odebratBtn;
    @javafx.fxml.FXML
    private Button vlozitBtn;
    private ObservableList<Obec> obceList = FXCollections.observableArrayList();
    private HeapControl heapControl;
    @javafx.fxml.FXML
    private Label importovanoLabel;
    @javafx.fxml.FXML
    private ChoiceBox typPruchoduChoiceBox;
    @javafx.fxml.FXML
    private Button vymazatBtn;
    @javafx.fxml.FXML
    private ChoiceBox prioritaChoiceBox;
    @javafx.fxml.FXML
    private Button reorganizaceBtn;
    @javafx.fxml.FXML
    private Button zpristupniMaxBtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(obceList);
        setTypPruchoduChoiceBox();
        heapControl = new HeapControl();
        setPrioritaChoiceBox();
    }

    private void setPrioritaChoiceBox() {
        prioritaChoiceBox.getItems().clear();
        for (ePriorita priorita : ePriorita.values()) {
            prioritaChoiceBox.getItems().add(priorita);
        }
        prioritaChoiceBox.getSelectionModel().selectFirst();
    }


    private void updateList() {
        listView.getItems().clear();
        obceList.clear();


        Iterator<Obec> iterator = heapControl.VytvorIterator((eTypProhl) typPruchoduChoiceBox.getSelectionModel().getSelectedItem());
        while (iterator.hasNext()) {
            obceList.add(iterator.next());
        }
        listView.setItems(obceList);

    }


    @javafx.fxml.FXML
    public void Import(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importovat data");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showOpenDialog(null);


        if (file != null) {


            int pocetObci = heapControl.importData(file.getAbsolutePath());
            importovanoLabel.setText("Importováno obcí: " + pocetObci);

            if (pocetObci > 0) {

                updateList();


            }
        }
    }

    @javafx.fxml.FXML
    public void Generovat(ActionEvent actionEvent) {
        String[] obceRandom = new String[]{"Buštěhrad", "Újezd", "Jestřábí lhota", "Kruty", "Ostrov", "Slaný", "Ovčáry", "Všetaty", "Kovanec", "Koryta"};

        int randObec = (int) (Math.random() * 10);
        int randPsc = (int) (Math.random() * 89999) + 10000;
        int randMuzu = (int) (Math.random() * 150000) + 100;
        int randZen = (int) (Math.random() * 150000) + 100;

        Obec novaObec = new Obec(randPsc, obceRandom[randObec], randMuzu, randZen, (randMuzu + randZen));
        heapControl.vloz(novaObec);

        updateList();

    }

    @javafx.fxml.FXML
    public void Exportovat(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("uložit data");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            heapControl.exportData(file.getAbsolutePath(), (eTypProhl) typPruchoduChoiceBox.getSelectionModel().getSelectedItem());

        }

    }

    @javafx.fxml.FXML
    public void Vlozit(ActionEvent actionEvent) {

        Dialog<Pair<String, String[]>> dialog = new Dialog<>();
        dialog.setTitle("Vložit Data");


        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        TextField pscTf = new TextField();
        TextField nazevTf = new TextField();
        TextField muziTf = new TextField();
        TextField zenyTf = new TextField();


        grid.add(new Label("PSČ:"), 0, 0);
        grid.add(pscTf, 1, 0);

        grid.add(new Label("Název obce:"), 0, 1);
        grid.add(nazevTf, 1, 1);

        grid.add(new Label("Počet mužů:"), 0, 2);
        grid.add(muziTf, 1, 2);

        grid.add(new Label("Počet žen:"), 0, 3);
        grid.add(zenyTf, 1, 3);


        dialog.getDialogPane().setContent(grid);


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {

                    String psc = pscTf.getText().trim();
                    String nazev = nazevTf.getText().trim();
                    int pocetMuzu = Integer.parseInt(muziTf.getText().trim());
                    int pocetZeny = Integer.parseInt(zenyTf.getText().trim());

                    if (psc == null || nazev.isEmpty() || pocetMuzu == 0 || pocetZeny == 0 || psc.length() != 5) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Input Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("Prosím  zkontrolujte zda je vše vyplněné správně a že PSČ je ve správném formátu");
                        errorAlert.showAndWait();
                        return null;
                    }
                    int pscInt = Integer.parseInt(psc);
                    Obec novaObec = new Obec(pscInt, nazev, pocetMuzu, pocetZeny, (pocetMuzu + pocetZeny));
                    heapControl.vloz(novaObec);


                } catch (NumberFormatException e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Input Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Prosím zadejte čísla");
                    errorAlert.showAndWait();
                }
            }
            return null;
        });


        dialog.showAndWait();
        updateList();


    }


    @javafx.fxml.FXML
    public void Odebrat(ActionEvent actionEvent) {

        Dialog<Pair<String, String[]>> dialog = new Dialog<>();
        dialog.setTitle("Odebrat");


        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);


        dialog.getDialogPane().setContent(grid);


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                try {


                    Obec odebrana = heapControl.odeberMax();
                    Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
                    errorAlert.setTitle("Odebrana obec");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Odebrana obec: " + odebrana.toString());
                    errorAlert.showAndWait();
                    return null;


                } catch (Exception e) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Input Error");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Špatný klíč");
                    errorAlert.showAndWait();
                }
            }
            return null;
        });


        dialog.showAndWait();
        updateList();


    }


    @javafx.fxml.FXML
    public void VymazatVse(ActionEvent actionEvent) {
        heapControl.zrus();
        updateList();
    }

    private void setTypPruchoduChoiceBox() {
        typPruchoduChoiceBox.getItems().clear();
        for (eTypProhl pozice : eTypProhl.values()) {
            typPruchoduChoiceBox.getItems().add(pozice);
        }
        typPruchoduChoiceBox.getSelectionModel().selectFirst();
    }

    @javafx.fxml.FXML
    public void Reorganizace(ActionEvent actionEvent) {
        heapControl.reorganizuj((ePriorita) prioritaChoiceBox.getSelectionModel().getSelectedItem());
        updateList();
    }

    @javafx.fxml.FXML
    public void zpristupniMax(ActionEvent actionEvent) {
        try {
            Obec max = heapControl.zpristupniMax();
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setTitle("Obec s max prioritou");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Max obec: " + max.toString());
            errorAlert.showAndWait();
        } catch (Exception e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("prazdny seznam");
            errorAlert.showAndWait();
        }

    }
}
