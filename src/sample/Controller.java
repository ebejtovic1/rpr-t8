package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Label labelUzorak;
    public TextField textBoxPretraga;
    public Button dugmeTrazi;
    public ListView<String> listaPretrage;
    public Button dugmePrekini;
    Thread PretragaThread;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dugmePrekini.setDisable(true);

    }

    public void prekini(ActionEvent actionEvent) {
        dugmeTrazi.setDisable(false);
        dugmePrekini.setDisable(true);
        PretragaThread.stop();
    }

    public class Pretraga implements Runnable{

        @Override
        public void run() {
            String textName = textBoxPretraga.getText();
            prikaz( new File( "/Users/User/Desktop" ) , textName );
        }
    }
    public void prikaz(File path , String name){
        if( path.isDirectory() ){
            File[] files = path.listFiles();
            if( files != null ){
                for( File f: files ){
                    prikaz( f, name );
                }
            }
        }
        if( path.isFile() ){

            if( path.getName().contains( name ) ) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(() -> {
                    listaPretrage.getItems().add(path.getAbsolutePath());
                } );
            }
        }
        if( path.getAbsolutePath().equals( "/Users/User/Desktop" ) ){
            dugmeTrazi.setDisable(false);
            dugmePrekini.setDisable(true);
        }

    }


    public void enter(KeyEvent keyEvent) {
        if( keyEvent.getCode().equals(KeyCode.ENTER) && !(dugmeTrazi.isDisable()) ){
            dugmeTrazi.requestFocus();
            trazi(null);
        }

        if( keyEvent.getCode().equals(KeyCode.ESCAPE) ){
            dugmePrekini.requestFocus();
            prekini(null);
        }
    }

    public void trazi(ActionEvent actionEvent)  {
        dugmeTrazi.setDisable(true);
        dugmePrekini.setDisable(false);
        listaPretrage.getSelectionModel().clearSelection();
        listaPretrage.getItems().clear();
        Pretraga pretraga = new Pretraga();
        PretragaThread = new Thread(pretraga);
        PretragaThread.start();
    }
}
