package m2dl.osgi.editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

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
import m2dl.osgi.editor.service.ColorationService;

public class CodeViewerController {

	public static final String BUNDLE_PARSER = "m2dl.osgi.decorator";
	public static final String BUNDLE_COL_CSS = "m2dl.osgi.colorationCSS";
	public static final String BUNDLE_COL_JAVA = "m2dl.osgi.colorationJava";

	private Map<String, Bundle> mapBundle = new HashMap<>();

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
		uninstallBundle();
		System.exit(0);
	}

	// Uninstall all bundle
	private void uninstallBundle() {
		for (Bundle bundle : mapBundle.values()) {
			try {
				bundle.uninstall();
			} catch (BundleException e) {
				e.printStackTrace();
			}
		}
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
		} else if (new File("C:\\Users\\Lucas-PCP\\Documents\\FHCI-CSA\\OSGi\\Projet\\OSGi\\plugins").exists()) {
			fileChooser.setInitialDirectory(new File("C:\\Users\\Lucas-PCP\\Documents\\FHCI-CSA\\OSGi\\Projet\\OSGi\\plugins"));
		}

		final File selectedFile = fileChooser.showOpenDialog(primaryStage);

		/*
		 * TODO complete this section to load the selected bundle.
		 */
		if (selectedFile != null) {
			Activator.logger.info("File selected: " + selectedFile.getName());

			installBundle(selectedFile);
		} else {
			Activator.logger.info("File selection cancelled.");
		}
	}

	private void installBundle(File selectedFile) {
		Bundle myBundle;		
		try {
			myBundle = Activator.context.installBundle(selectedFile.toURI().toString());

			mapBundle.put(myBundle.getSymbolicName(), myBundle);
			System.out.println("The bundle " + selectedFile + " installed");
		} catch (final BundleException e) {
			e.printStackTrace();
		}
	}

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
			ServiceReference<?>[] references;
			try {
				references = Activator.context.getServiceReferences(ColorationService.class.getName(), "(type=decorator)");
				System.out.println(references);
				//((ColorationService)bundleContext.getService(references[0])).sayHello();
			} catch (InvalidSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
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
		if (mapBundle.get(BUNDLE_COL_CSS) != null) {
			startStopBundle(mapBundle.get(BUNDLE_COL_CSS));
		} else {
			// TODO: cancel event
		}
	}

	@FXML
	void fireRadioMenuDecorator(ActionEvent event) {
		if (mapBundle.get(BUNDLE_PARSER) != null) {
			startStopBundle(mapBundle.get(BUNDLE_PARSER));
		} else {
			// TODO: cancel event
		}
	}

	@FXML
	void fireRadioMenuJava(ActionEvent event) {
		if (mapBundle.get(BUNDLE_COL_JAVA) != null) {
			startStopBundle(mapBundle.get(BUNDLE_COL_JAVA));
		} else {
			// TODO: cancel event
		}
	}

	private void startStopBundle(Bundle bundle) {
		System.out.println("Call startStopBundle " + bundle.getSymbolicName());

		switch (bundle.getState()) {
		case Bundle.ACTIVE:
			try {
				System.out.println("Stopping bundle " + bundle.getSymbolicName());
				bundle.stop();
			} catch (BundleException e) {
				e.printStackTrace();
			}
			break;
		case Bundle.RESOLVED:
		case Bundle.INSTALLED:
			try {
				System.out.println("Starting bundle " + bundle.getSymbolicName());
				bundle.start();
			} catch (BundleException e) {
				e.printStackTrace();
			}
			break;
		default:
			System.out.println("State => " + bundle.getState());
		}
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
