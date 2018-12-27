package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OutputController implements Initializable {

    @FXML
    private TextField input;

    @FXML
    private Label result;

    @FXML
    private TextArea output;

    @FXML
    private void handleGrammar(ActionEvent event){
        if(GrammarInputController.parserKind.equals("LR(0)") || GrammarInputController.parserKind.equals("SLR(1)")){
            output.setText(GrammarInputController.lr0Parser.getGrammar()+"");
        }
    }

    @FXML
    private void handleFirst(ActionEvent event){
        if(GrammarInputController.parserKind.equals("LR(0)") || GrammarInputController.parserKind.equals("SLR(1)")){
            String str = "";
            for(String s : GrammarInputController.lr0Parser.getGrammar().getFirstSets().keySet()){
                str += s + " : " + GrammarInputController.lr0Parser.getGrammar().getFirstSets().get(s) + "\n";
            }
            output.setText(str);
        }
    }

    @FXML
    private void handleFollow(ActionEvent event){
        if(GrammarInputController.parserKind.equals("LR(0)") || GrammarInputController.parserKind.equals("SLR(1)")){
            String str = "";
            for(String s : GrammarInputController.lr0Parser.getGrammar().getFallowSets().keySet()){
                str += s + " : " + GrammarInputController.lr0Parser.getGrammar().getFallowSets().get(s) + "\n";
            }
            output.setText(str);
        }
    }

    @FXML
    private void handleState(ActionEvent event){
        if(GrammarInputController.parserKind.equals("LR(0)") || GrammarInputController.parserKind.equals("SLR(1)")){
            output.setText(GrammarInputController.lr0Parser.canonicalCollectionStr());
        }
    }

    @FXML
    private void handleGoTo(ActionEvent event){
        if(GrammarInputController.parserKind.equals("LR(0)") || GrammarInputController.parserKind.equals("SLR(1)")){
            output.setText(GrammarInputController.lr0Parser.goToTableStr());
        }
    }

    @FXML
    private void handleAction(ActionEvent event){
        if(GrammarInputController.parserKind.equals("LR(0)") || GrammarInputController.parserKind.equals("SLR(1)")){
            output.setText(GrammarInputController.lr0Parser.actionTableStr());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        result.setVisible(false);
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            String str = input.getText();
            ArrayList<String> words = new ArrayList<>();
            String[] split = str.trim().split("\\s+");
            for(String s: split){
                words.add(s);
            }
            if(GrammarInputController.parserKind.equals("LR(0)") || GrammarInputController.parserKind.equals("SLR(1)")){
                boolean accept = GrammarInputController.lr0Parser.accept(words);
                if(accept){
                    result.setText("accepted");
                    result.setTextFill(Color.GREEN);
                    result.setVisible(true);
                }else {
                    result.setText("not accepted");
                    result.setTextFill(Color.RED);
                    result.setVisible(true);
                }
            }
        });
        if(GrammarInputController.parserKind.equals("LR(0)") || GrammarInputController.parserKind.equals("SLR(1)")){
            output.setText(GrammarInputController.lr0Parser.getGrammar()+"");
        }
    }

    public void backToMainPage(ActionEvent event){
        try {
            Button button = (Button) event.getSource();
            Stage stage = (Stage) button.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Output.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        }catch (IOException e ){
            e.printStackTrace();
        }
    }
}
