package m2dl.osgi.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CodeViewerController {

	/**
	 * The main window of the application.
	 */
	private Stage primaryStage;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	/**
	 * Radio button: indicate if the html bundle is started.
	 */
	@FXML
	private RadioMenuItem radioMenuJava;

	/**
	 * Radio button: indicate if the decorator bundle is started.
	 */
	@FXML
	private RadioMenuItem radioMenuDecorator;

	/**
	 * The viewer to display the content of the opened file.
	 */
	@FXML
	private WebView webViewer;

	/**
	 * The radio button: indicate if the css bundle is started.
	 */
	@FXML
	private RadioMenuItem radioMenuCSS;

	/**
	 * The bundle context.
	 */
	private BundleContext bundleContext;

	/**
	 * The button "À propos" have been clicked.
	 *
	 * @param event
	 */
	@FXML
	void fireMenuAPropos(ActionEvent event) {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		final VBox dialogVbox = new VBox(50);
		dialogVbox.setAlignment(Pos.CENTER);
		dialogVbox.getChildren().add(new Text("This is a modulable code viewer"));
		final Scene dialogScene = new Scene(dialogVbox, 300, 80);
		dialog.setScene(dialogScene);
		dialog.show();
	}

	@FXML
	void fireMenuCloseFile(ActionEvent event) {
		/*
		 * TODO close the opened file. The web viewer have to be clean.
		 */
		webViewer.getEngine().loadContent("");
	}

	@FXML
	void fireMenuExit(ActionEvent event) {
		System.exit(0);
	}

	/**
	 * The button to load a bundle have been clicked.
	 *
	 * @param event
	 */
	@FXML
	void fireMenuLoadBundle(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("Bundle files (*.jar)", "*.jar");
		fileChooser.getExtensionFilters().add(fileFilter);
		
		if (new File("D:\\Work\\M2\\FHCI\\OSGi\\src\\plugins").exists()) {
			fileChooser.setInitialDirectory(new File("D:\\Work\\M2\\FHCI\\OSGi\\src\\plugins"));
		} else if (new File("BLA").exists()) {
			fileChooser.setInitialDirectory(new File("BLA"));
		}

		final File selectedFile = fileChooser.showOpenDialog(primaryStage);

		/*
		 * TODO complete this section to load the selected bundle.
		 */
		if (selectedFile != null) {
			Activator.logger.info("File selected: " + selectedFile.getName());
			activateBundle(selectedFile);
		} else {
			Activator.logger.info("File selection cancelled.");
		}
	}

	private void activateBundle(File selectedFile) {
		// TODO Auto-generated method stub
		/*Bundle myBundle;
		try {
			myBundle = FrameworkUtil.getBundle(getClass()).getBundleContext()
			myBundle.start();
			System.out.println("The bundle " + selectedFile + " installed and started");
		} catch (final BundleException e) {
			e.printStackTrace();
		}*/
		Bundle myBundle;		
		try {
			myBundle = bundleContext.installBundle(selectedFile.toURI().toString());
			System.out.println("SYMBOLIC_NAME  >>>> "+myBundle.getSymbolicName());
			myBundle.start();
			System.out.println("The bundle " + selectedFile + " installed and started");
		} catch (final BundleException e) {
			e.printStackTrace();
		}
	}
	
	/*public static void installAndStartBundle(final String fileJar) {

		final File fileBundle = new File(fileJar);
		Bundle myBundle;
		try {
			myBundle = bundleContext.installBundle(fileBundle.toURI().toString());
			myBundle.start();
			System.out.println("The bundle " + fileJar + " installed and started");
		} catch (final BundleException e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * The button to open a file have been clicked.
	 *
	 * @param event
	 */
	@FXML
	void fireMenuOpenFile(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter fileFilter = new FileChooser.ExtensionFilter("SOURCE files (*.css) (*.java)", "*.css", "*.java");
		fileChooser.getExtensionFilters().add(fileFilter);
		
		final File selectedFile = fileChooser.showOpenDialog(primaryStage);

		/*
		 * TODO complete this section to display the content of the file into
		 * the webViewer.
		 */
		if (selectedFile != null) {
			Activator.logger.info("File selected: " + selectedFile.getName());
			
			readFile(selectedFile);
		} else {
			Activator.logger.info("File selection cancelled.");
		}
	}
	
	void readFile(File file) {
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		InputStreamReader isr = new InputStreamReader(is);
		
		BufferedReader br = new BufferedReader(isr);
		
		String html = "<html><head></head><body>";
		String buffer;
		
		try {
			while ((buffer = br.readLine()) != null) {
				html = html + buffer.replaceAll("class", "<span style=\"color:blue;\">class</span>") + "<br/>";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		html = html + "</body></html>";
		
		webViewer.getEngine().loadContent(html);
	}

	@FXML
	void fireRadioMenuCSS(ActionEvent event) {
		/*
		 * If the css bundle is stated -> stop it otherwise start it (if it has
		 * been loaded before)
		 */
	}

	@FXML
	void fireRadioMenuDecorator(ActionEvent event) {
		/*
		 * If the decorator bundle is stated -> stop it otherwise start it (if
		 * it has been loaded before)
		 */
	}

	@FXML
	void fireRadioMenuJava(ActionEvent event) {
		/*
		 * If the Java bundle is stated -> stop it otherwise start it (if it has
		 * been loaded before)
		 */
	}

	@FXML
	void initialize() {
		assert radioMenuJava != null : "fx:id=\"radioMenuJava\" was not injected: check your FXML file 'main-window-exercice.fxml'.";
		assert radioMenuDecorator != null : "fx:id=\"radioMenuDecorator\" was not injected: check your FXML file 'main-window-exercice.fxml'.";
		assert webViewer != null : "fx:id=\"webViewer\" was not injected: check your FXML file 'main-window-exercice.fxml'.";
		assert radioMenuCSS != null : "fx:id=\"radioMenuCSS\" was not injected: check your FXML file 'main-window-exercice.fxml'.";

	}

	public void setPrimaryStage(final Stage _stage) {
		primaryStage = _stage;
	}

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

}
