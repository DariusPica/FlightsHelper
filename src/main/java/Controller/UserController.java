package Controller;

import Domain.Zbor;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLDataException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController implements Initializable {

    Service srv;

    Stage currentStage;

    public void setService(Service srv) {
        this.srv = srv;
    }

    public void setCurrentStage(Stage stg) {this.currentStage=stg;}

    ObservableList<Zbor> zborView= FXCollections.observableArrayList();
    ObservableList<Zbor> zborSearchView= FXCollections.observableArrayList();

    @FXML
    TableView<Zbor> tableZboruri;

    @FXML
    TableColumn<Zbor, String> tableZboruriDestinatie;

    @FXML
    TableColumn<Zbor, LocalDateTime> tableZboruriData;

    @FXML
    TableColumn<Zbor, String> tableZboruriAeroport;

    @FXML
    TableColumn<Zbor, String> tableZboruriLocuri;

    @FXML
    TextField destinatieText;

    @FXML
    DatePicker dataInput;

    @FXML
    Button searchButton;

    @FXML
    TableView<Zbor> tableZboruriSearch;

    @FXML
    TableColumn<Zbor, String> tableZboruriDestinatieSearch;

    @FXML
    TableColumn<Zbor, LocalDateTime> tableZboruriDataSearch;

    @FXML
    TableColumn<Zbor, String> tableZboruriAeroportSearch;

    @FXML
    TableColumn<Zbor, String> tableZboruriLocuriSearch;

    @FXML
    Button buyButton;

    @FXML
    TextField clientText;

    @FXML
    TextField pasageriText;

    @FXML
    TextField adresaText;

    @FXML
    TextField locuriText;

    @FXML
    Button logoutButton;


    private void loadZboruri()
    {
        zborView.setAll(
                StreamSupport.stream(srv.getZboruri().spliterator(),false)
                        .collect(Collectors.toList())
        );
    }

    private void loadZboruriSearch(String destinatie, LocalDate data)
    {
        zborSearchView.setAll(
                StreamSupport.stream(srv.getZboruriSearch(destinatie,data).spliterator(),false)
                        .collect(Collectors.toList())
        );
    }


    private void update()
    {
        loadZboruri();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        this.tableZboruriDestinatie.setCellValueFactory(new PropertyValueFactory<>("Destinatie"));
        this.tableZboruriData.setCellValueFactory(new PropertyValueFactory<>("OraDataPlecare"));
        this.tableZboruriAeroport.setCellValueFactory(new PropertyValueFactory<>("Aeroport"));
        this.tableZboruriLocuri.setCellValueFactory(new PropertyValueFactory<>("LocuriDisponibile"));

        tableZboruri.setItems(zborView);


        this.tableZboruriDestinatieSearch.setCellValueFactory(new PropertyValueFactory<>("Destinatie"));
        this.tableZboruriDataSearch.setCellValueFactory(new PropertyValueFactory<>("OraDataPlecare"));
        this.tableZboruriAeroportSearch.setCellValueFactory(new PropertyValueFactory<>("Aeroport"));
        this.tableZboruriLocuriSearch.setCellValueFactory(new PropertyValueFactory<>("LocuriDisponibile"));


        tableZboruriSearch.setItems(zborSearchView);

        update();

        searchButton.setOnMouseClicked(x->{

            String destinatieString=destinatieText.getText();
            LocalDate data=dataInput.getValue();

            loadZboruriSearch(destinatieString,data);


        });



        buyButton.setOnMouseClicked(x->{

            String clientString=clientText.getText();
            String pasageriString=pasageriText.getText();
            String adresaString=adresaText.getText();
            String locuriString=locuriText.getText();

            try
            {

                String destinatieString=destinatieText.getText();
                LocalDate data=dataInput.getValue();

                Integer locuriInteger=Integer.parseInt(locuriString);

                Zbor zborSelected=tableZboruriSearch.getSelectionModel().getSelectedItem();

                System.out.println(zborSelected);
                if(locuriInteger<=0 || zborSelected==null || clientString.equals("") || pasageriString.equals("") || adresaString.equals(""))
                    throw new Exception();

                zborSelected.setLocuriDisponibile(zborSelected.getLocuriDisponibile()-locuriInteger);
                srv.updateZbor(zborSelected);
                srv.saveBilet(clientString,pasageriString,adresaString,locuriInteger,zborSelected);

                loadZboruriSearch(destinatieString,data);

            }
            catch(Exception ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Date de intrare");
                alert.setWidth(500.0);
                alert.setHeight(300.0);
                alert.setContentText("Date de intrare incorecte sau nu a fost selectat zborul din tabela!");
                alert.showAndWait();

            }

        });

        logoutButton.setOnMouseClicked(x->{

            this.currentStage.close();

        });




    }
}
