package ca.uwo.eng.se2205b.lab03;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Simple AutoComplete example to utilize a Trie.
 */
public class AutoComplete extends Application {

    @FXML
    private ListView<String> options;

    @FXML
    private TextField input;

    @FXML
    private Spinner<Integer> resultCounter;

    @FXML
    private Label countLabel;

    /**
     * Trie of words
     */
    private Trie prefixTrie;
    private String word = null;
    private int limit;
    private ObservableList<String> listViewData = FXCollections.observableArrayList();

    /**
     * Load the {@link #prefixTrie} field with the values from the provided dictionary.
     * There is a single word per line.
     */
    private void loadTrie() {
        // TODO SE2205B
        try {
            // new input stream created
            InputStream io = AutoComplete.class.getResourceAsStream("/dictionary.txt");
            // reads till the end of the stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(io));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                prefixTrie.put(line.toString());
            }
            reader.close();
            // releases system resources associated with this stream
            io.close();
        } catch (Exception e) {
            // if any I/O error occurs
            e.printStackTrace();
        }
    }


    /**
     * Read from the input box, and the spinner and calculate the auto complete and update the "countLabel"
     * with the number of results.
     */
    private void loadAutoComplete() {
        // TODO SE2205B
        if (word != null) {
            SortedSet<String> output = new TreeSet<>();
            output = prefixTrie.getNextN(word, limit);
            if (output != null) {
                countLabel.setText(Integer.toString(output.size()));
                listViewData.clear();
                for (String elem : output) {
                    listViewData.add(elem);
                    options.setItems(listViewData);
                }


            } else {
                listViewData.clear();
                options.setItems(listViewData);
                countLabel.setText("0");
            }
        }
    }


    @FXML
    protected void initialize() {

        // TODO SE2205B
        prefixTrie = new LinkedTrie();
        input.setDisable(false);
        input.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                word = newValue.toLowerCase();
                    loadAutoComplete();
            }
        });
        resultCounter.valueProperty().addListener((obs, oldValue, newValue) -> {
            limit = newValue;
        });
        options.setDisable(false);


        ////////////////////////////////////////
        // DO NOT CHANGE BELOW
        ////////////////////////////////////////

        resultCounter.valueProperty().addListener((obs, oldValue, newValue) -> {
            loadAutoComplete();
        });

        countLabel.setText("0");

        loadTrie();
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AutoComplete.fxml"));

        Scene scene = new Scene(root);

        stage.setTitle("Auto-complete Example");
        stage.setScene(scene);
        stage.show();
    }


}
