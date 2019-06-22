/**
 * Skeleton for 'Borders.fxml' Controller Class
 */

package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BordersController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="boxNazione"
    private ComboBox<Country> boxNazione; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

	private Model model;

    @FXML
    void doCalcolaConfini(ActionEvent event) {
    	
    	txtResult.clear();

    	try {
    		
    		int anno = Integer.parseInt(this.txtAnno.getText());
    		model.creaGrafo(anno);
    		this.boxNazione.getItems().addAll(model.getVertex());
    		for(Country country : model.getConfini())
    			txtResult.appendText(country.toString()+"\n");
    		
    	} catch (NumberFormatException e) {
			// TODO: handle exception
    		e.printStackTrace();
    		txtResult.appendText("Inserisci un valore numerico come input!");
    	}
    }

    @FXML
    void doSimula(ActionEvent event) {
    	
    	txtResult.clear();
    	
    	Country country = this.boxNazione.getValue();
    	
    	if(country != null) {
    		
    		model.simula(country);
    		for(String string : model.getPersone())
    			txtResult.appendText(string+"\n");
    		txtResult.appendText("Passi totali: "+model.getPasso()+"\n");
    		
    	} else txtResult.appendText("Seleziona almeno un paese!");

    }
    
    public void setModel(Model m) {
    	this.model = m;
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Borders.fxml'.";
        assert boxNazione != null : "fx:id=\"boxNazione\" was not injected: check your FXML file 'Borders.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Borders.fxml'.";

    }
}
