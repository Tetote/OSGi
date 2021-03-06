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
import m2dl.osgi.editor.service.DecoratorService;
import m2dl.osgi.editor.service.TypeColorationService;

public class CodeViewerController {

	public static final String BUNDLE_PARSER = "m2dl.osgi.decorator";
	public static final String BUNDLE_COL_CSS = "m2dl.osgi.colorationCss1";
	public static final String BUNDLE_COL_JAVA = "m2dl.osgi.colorationJava";

	private Map<String, Bundle> mapBundle = new HashMap<>();

	public DecoratorService decoratorService = null;
	public Map<TypeColorationService, ColorationService> colorationServices = new HashMap<>();

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
		showDialog("A propos", "This is a modulable code viewer made by Lucas Bled & Th�o Vaucher");
	}

	@FXML
	void fireMenuCloseFile(ActionEvent event) {
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

		final File selectedFile = fileChooser.showOpenDialog(primaryStage);

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

		if (selectedFile != null) {
			Activator.logger.info("File selected: " + selectedFile.getName());
			if (this.decoratorService != null) {
				DecoratorService decorator = this.decoratorService;

				String docoratedString = decorator.decorate(selectedFile);

				int i = selectedFile.getPath().lastIndexOf('.');
				String extension = selectedFile.getPath().substring(i+1);

				ColorationService colorationService = null;
				switch (extension) {
				case "java":
					colorationService = colorationServices.get(TypeColorationService.JAVA);
					break;
				case "css":
					colorationService = colorationServices.get(TypeColorationService.CSS);
					break;
				}

				if (colorationService != null) {
					String html = "<html><head></head><body>";
					html = html + colorationService.colorate(docoratedString);
					html = html + "</body></html>";

					webViewer.getEngine().loadContent(html);	
				} else {
					readFile(selectedFile);
					showDialog("Erreur de coloration fichier " + extension.toUpperCase(), "Veuillez importer le bundle pour la coloration "+extension.toUpperCase()+" et l'activer");
				}
			} else {
				readFile(selectedFile);
				showDialog("Erreur de decoration du fichier ", "Veuillez importer le bundle pour la decoration et l'activer");
			}
		} else {
			Activator.logger.info("File selection cancelled.");
		}
	}

	private void showDialog(String title, String text) {
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(primaryStage);
		dialog.setTitle(title);
		final VBox dialogVbox = new VBox(50);
		dialogVbox.setAlignment(Pos.CENTER);

		dialogVbox.getChildren().add(new Text(text));
		final Scene dialogScene = new Scene(dialogVbox, 500, 80);
		dialog.setScene(dialogScene);
		dialog.show();
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
				html += buffer + "<br/>";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		html += "</body></html>";

		webViewer.getEngine().loadContent(html);
	}

	@FXML
	void fireRadioMenuCSS(ActionEvent event) {
		if (mapBundle.get(BUNDLE_COL_CSS) != null) {
			startStopBundle(mapBundle.get(BUNDLE_COL_CSS));
		} else {
			RadioMenuItem radioMenuItem = (RadioMenuItem) event.getSource();
			radioMenuItem.setSelected(false);

			showDialog("Erreur de chargement du bundle", "Le bundle CSS n'est pas charg�");
		}
	}

	@FXML
	void fireRadioMenuDecorator(ActionEvent event) {
		if (mapBundle.get(BUNDLE_PARSER) != null) {
			startStopBundle(mapBundle.get(BUNDLE_PARSER));
		} else {
			RadioMenuItem radioMenuItem = (RadioMenuItem) event.getSource();
			radioMenuItem.setSelected(false);

			showDialog("Erreur de chargement du bundle", "Le bundle Decorator n'est pas charg�");
		}
	}

	@FXML
	void fireRadioMenuJava(ActionEvent event) {
		if (mapBundle.get(BUNDLE_COL_JAVA) != null) {
			startStopBundle(mapBundle.get(BUNDLE_COL_JAVA));
		} else {
			RadioMenuItem radioMenuItem = (RadioMenuItem) event.getSource();
			radioMenuItem.setSelected(false);

			showDialog("Erreur de chargement du bundle", "Le bundle JAVA n'est pas charg�");
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
		case Bundle.STARTING:
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
