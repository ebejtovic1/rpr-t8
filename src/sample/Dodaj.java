package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Dodaj {
    public Button btnPosalji;
    public TextField imePolje;
    public TextField prezimePolje;
    public TextField adresaPolje;
    public TextField gradPolje;
    public TextField postanskiBrojPolje;

    @FXML
    public void initialize() {
        adresaPolje.textProperty().addListener((observableValue, o, n) -> {
            if ( n.length() == 0 ) {
                adresaPolje.getStyleClass().removeAll("poljeIspravno", "poljeNijeIspravno");
                adresaPolje.getStyleClass().add("poljeNeutralno");

            } else if( ispravnostKontaktAdresa(n) && n.length() != 0) {
                adresaPolje.getStyleClass().removeAll("poljeNeutralno", "poljeNijeIspravno");
                adresaPolje.getStyleClass().add("poljeIspravno");
            }
            else if( !(ispravnostKontaktAdresa(n)) && n.length() != 0){
                adresaPolje.getStyleClass().removeAll("poljeNeutralno", "poljeIspravno");
                adresaPolje.getStyleClass().add("poljeNijeIspravno");

            }
        });

        gradPolje.textProperty().addListener((observableValue, o, n) -> {
            if ( n.length() == 0 ) {
                gradPolje.getStyleClass().removeAll("poljeIspravno", "poljeNijeIspravno");
                gradPolje.getStyleClass().add("poljeNeutralno");

            } else if( ispravnost(n) && n.length() != 0) {
                gradPolje.getStyleClass().removeAll("poljeNeutralno", "poljeNijeIspravno");
                gradPolje.getStyleClass().add("poljeIspravno");

            }
            else if( !(ispravnost(n)) && n.length() != 0){
                gradPolje.getStyleClass().removeAll("poljeNeutralno", "poljeIspravno");
                gradPolje.getStyleClass().add("poljeNijeIspravno");

            }
        });
        imePolje.textProperty().addListener((observableValue, o, n) -> {
            if (n.length() == 0) {
                imePolje.getStyleClass().removeAll("poljeIspravno", "poljeNijeIspravno");
                imePolje.getStyleClass().add("poljeNeutralno");
            } else if (ispravnost(n) && n.length() != 0) {
                imePolje.getStyleClass().removeAll("poljeNeutralno", "poljeNijeIspravno");
                imePolje.getStyleClass().add("poljeIspravno");
            } else if (!(ispravnost(n)) && n.length() != 0) {
                imePolje.getStyleClass().removeAll("poljeNeutralno", "poljeIspravno");
                imePolje.getStyleClass().add("poljeNijeIspravno");

            }
        });

        prezimePolje.textProperty().addListener((observableValue, o, n) -> {
            if (n.length() == 0) {
                prezimePolje.getStyleClass().removeAll("poljeIspravno", "poljeNijeIspravno");
                prezimePolje.getStyleClass().add("poljeNeutralno");

            } else if (ispravnost(n) && n.length() != 0) {
                prezimePolje.getStyleClass().removeAll("poljeNeutralno", "poljeNijeIspravno");
                prezimePolje.getStyleClass().add("poljeIspravno");
            } else if (!(ispravnost(n)) && n.length() != 0) {
                prezimePolje.getStyleClass().removeAll("poljeNeutralno", "poljeIspravno");
                prezimePolje.getStyleClass().add("poljeNijeIspravno");

            }
        });

        postanskiBrojPolje.focusedProperty().addListener((obs, o, n) -> {
            if (!n){
                Thread newThread = new Thread(this::ispravnostURL);
                newThread.start();
            }
        });


    }

    private void ispravnostURL() {
        if( postanskiBrojPolje.getText().length() == 0 ){
            Platform.runLater( () ->
            { postanskiBrojPolje.getStyleClass().removeAll("poljeIspravno","poljeNijeIspravno","poljeNeutralno");
                postanskiBrojPolje.getStyleClass().add("poljeNeutralno"); } );
            return;
        }
        try {
            String tempString = "http://c9.etf.unsa.ba/proba/postanskiBroj.php?postanskiBroj=";
            tempString += postanskiBrojPolje.getText();
            URL url = new URL(tempString);
            InputStream tok = url.openStream();
            int size = tok.available();
            String rezultat = "";
            for (int i = 0; i < size; i++) {
                rezultat += (char) tok.read();
            }
            System.out.println(rezultat);
            if (rezultat.equals("OK")){
                Platform.runLater( () ->
                { postanskiBrojPolje.getStyleClass().removeAll("poljeIspravno","poljeNijeIspravno","poljeNeutralno");
                    postanskiBrojPolje.getStyleClass().add("poljeIspravno");} );
            }

            else {
                Platform.runLater( () ->
                { postanskiBrojPolje.getStyleClass().removeAll("poljeIspravno","poljeNijeIspravno","poljeNeutralno");
                    postanskiBrojPolje.getStyleClass().add("poljeNijeIspravno"); } );

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }


    private boolean daLiJeSlovo( char c ) {
        if( c >= 'A' && c <= 'Z' ) return true;
        if( c >= 'a' && c <= 'z' ) return true;
        return false;
    }

    private boolean daLiJeBroj( char c ){
        return ( c >= '0' && c <= '9' );
    }

    private boolean ispravnost( String s ){
        if( s.length() > 20 ) return false;
        if( s.length() < 2 ) return false;
        if( s.charAt(0) < 'A' || s.charAt(0) > 'Z' ) return false;
        for ( int i = 1; i < s.length(); i++ ){
            if( !( daLiJeSlovo( s.charAt(i) ) ) ) return false;
            if( s.charAt(i) >= 'A' && s.charAt(i) <= 'Z' ) return false;
        }
        return true;
    }


    private boolean ispravnostKontaktAdresa( String s ){
        if( s.length() > 20 ) return false;
        if( s.length() < 2 ) return false;
        if( s.charAt(0) < 'A' || s.charAt(0) > 'Z' ) return false;
        for ( int i = 1; i < s.length(); i++ ){
            if( !( daLiJeSlovo( s.charAt(i) ) ) &&  !( daLiJeBroj( s.charAt(i) ) ) &&  s.charAt(i) != ' ' ) return false;
        }
        for( int i = 0; i < s.length() - 1; i++ ){
            if( i == 0 && s.charAt(i) >= 'a' && s.charAt(i) <= 'z' ) return false;
            if( s.charAt(i) == ' ' && s.charAt(i+1) >= 'a' && s.charAt(i) <= 'z' ) return false;
        }
        return true;
    }

    public void pritisniPosalji(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Obavjest");
        alert.setHeaderText("Uspjesno slanje file-a:\n");
        alert.showAndWait();
    }
}
