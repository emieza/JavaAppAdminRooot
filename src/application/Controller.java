package application;

import java.awt.event.ActionListener;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;


public class Controller implements Initializable {
	// private String user = "Sergio";
	// private String password = "123456";
	private JSONObject user, newUser;
	private ArrayList<String> userFields=new ArrayList<String>();
	private ArrayList<Pane> pane = new ArrayList<>();
	private ArrayList<MenuItem> menuItems = new ArrayList<>();
	private int delay = 3000; // milliseconds
	  @FXML
	    private DatePicker userBirthday;
	@FXML
	private AnchorPane mainPane;
	@FXML
	private MenuItem menuUsuarios, menuEventos, menuConfig;
	@FXML
	private TextField loginUser, userName, userSurname1, userSurname2, userDNI, userEmail, userEmailConfirm,
			userPhoneNumber, userAddress, userCP,userLanguage,userPassw,userPasswConfirm;
	@FXML
	private PasswordField loginPassw, userCreatePassw, userCreatePass2;
	@FXML
	private Pane paneAdduser, paneLogin, paneMain, paneCreateAcc;
	@FXML
	private Button loginEnter, createUserInfo, createUser, loginBtnPasswError, singUpButton;
	@FXML
	private DialogPane loginPasswError;
	@FXML
	private ComboBox<String> crearComboCiudad;
	@FXML
	private Label errorLogin;
	@FXML
	private ImageView ivProblemas, ivUser, ivPassword, ivLoginLogo, ivMainLogo;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		pane.add(paneLogin);
		pane.add(paneAdduser);
		pane.add(paneMain);
		pane.add(paneCreateAcc);

		menuItems.add(menuConfig);
		menuItems.add(menuUsuarios);
		menuItems.add(menuEventos);

		ivProblemas.setImage(new Image("/Imagenes/LoginError.jpg"));
		ivUser.setImage(new Image("/Imagenes/LoginUser.png"));
		ivPassword.setImage(new Image("/Imagenes/LoginPassword.png"));
		ivLoginLogo.setImage(new Image("/Imagenes/LoginLogoInter.png"));
		ivMainLogo.setImage(new Image("/Imagenes/MainLogo.jpg"));
		ObservableList<String> items = FXCollections.observableArrayList();
		items.addAll("Alava", "Albacete", "Alicante", "Almer�a", "Asturias", "Avila", "Badajoz", "Barcelona", "Burgos",
				"C�ceres", "C�diz", "Cantabria", "Castell�n", "Ciudad Real", "C�rdoba", "La Coru�a", "Cuenca", "Gerona",
				"Granada", "Guadalajara", "Guip�zcoa", "Huelva", "Huesca", "Islas Baleares", "Ja�n", "Le�n", "L�rida",
				"Lugo", "Madrid", "M�laga", "Murcia", "Navarra", "Orense", "Palencia", "Las Palmas", "Pontevedra",
				"La Rioja", "Salamanca", "Segovia", "Sevilla", "Soria", "Tarragona", "Santa Cruz de Tenerife", "Teruel",
				"Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza");
		crearComboCiudad.setItems(items);
		
	}

	@FXML
	private void menuLogin(ActionEvent event) throws InterruptedException { // Login
		String email = loginUser.getText();
		String passwrd = loginPassw.getText();

		Button btn = (Button) event.getSource();
		btn.getOnAction();
		ActionListener taskPerformer = new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				errorLogin.setVisible(false);
			}
		};
		try {
			user = Conexion.Post_JSON_Login(email, passwrd);
		} catch (Exception e) {
			System.out.println("Auth fallida");
		}
		if (user != null) {
			loginPassw.clear();
			loginUser.clear();
			paneLogin.setVisible(false);
			itemsTrue();
			paneMain.setVisible(true);
		} else {
			errorLogin.setText("Credenciales incorrectos");
			errorLogin.setVisible(true);
			javax.swing.Timer tick = new javax.swing.Timer(delay, taskPerformer);
			tick.setRepeats(false);
			tick.start();
			loginPassw.clear();
			loginUser.clear();
		}

		/*
		 * if (loginUser.getText().equals(user)) { if
		 * (loginPassw.getText().equals(password)) { // Quitamos el login y poenos los
		 * menu items y la pagina // inicial. loginPassw.clear(); loginUser.clear();
		 * paneLogin.setVisible(false); itemsTrue(); paneMain.setVisible(true); } else {
		 * errorLogin.setText("La contrase�a es incorrecta");
		 * errorLogin.setVisible(true); javax.swing.Timer tick = new
		 * javax.swing.Timer(delay, taskPerformer); tick.setRepeats(false);
		 * tick.start();
		 * 
		 * loginPassw.clear(); loginUser.clear(); } } else {
		 * errorLogin.setText("El usuario es incorrecto"); errorLogin.setVisible(true);
		 * javax.swing.Timer tick = new javax.swing.Timer(delay, taskPerformer);
		 * tick.setRepeats(false); tick.start(); loginPassw.clear(); loginUser.clear();
		 * }
		 */
	}

	@FXML
	private void actionMenu(ActionEvent event) {
		paneFalse(); // Ponemos el resto de panels en invisible.
		MenuItem menuClicked = (MenuItem) event.getSource(); // Click menu items.
		switch (menuClicked.getId()) {
		case "userCreate":
			paneAdduser.setVisible(true);
			break;
		case "userClose":
			paneLogin.setVisible(true);
			itemsFalse();
			Conexion.Post_JSON_LogOutAll(user);
			user = null;
			break;
		default:
			break;
		}
	}

	public void createNewUser() {
		if (userEmail.getText().equals(userEmailConfirm.getText())&&userPassw.getText().equals(userPasswConfirm.getText())) {
			if(userPassw.getLength()<8) {
				System.out.println("contrase�a tiene k ser mayor a 8 caracteres");
			}else {
			userFields.add(userName.getText());
			userFields.add(userSurname1.getText() + " " + userSurname2.getText());
			userFields.add(userEmail.getText());
			userFields.add(userPassw.getText());
			userFields.add(crearComboCiudad.getValue());
			userFields.add(userAddress.getText());
			userFields.add(userCP.getText());
			userFields.add(userDNI.getText());
			//la fecha no funcionaaaa, hay k repasarla
			LocalDate localDate=userBirthday.getValue();
			Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
			userFields.add(String.valueOf(date));
			userFields.add(userPhoneNumber.getText());
			userFields.add(userLanguage.getText());}
		} else {
			//aki lo suyo seria poner un label k se muestre x 3 sec
			//como tenemos en el login principal
			System.out.println("el email no coincide");
		}
	}

	@FXML
	private void createUsers(ActionEvent event) {
		Button btn = (Button) event.getSource();
		btn.getOnAction();
		paneFalse();
		switch (btn.getId()) {
		case "createUserInfo":
			createNewUser();
			Conexion.Post_JSON_User_Create(userFields);
			paneCreateAcc.setVisible(true);
			break;
		case "createUser":
			paneLogin.setVisible(true);
			break;
		case "singUpButton":
			paneAdduser.setVisible(true);
			break;
		}
	}

	public void itemsFalse() {
		for (int j = 0; j < menuItems.size(); j++) {
			menuItems.get(j).setVisible(false);
		}
	}

	public void itemsTrue() {
		for (int j = 0; j < menuItems.size(); j++) {
			menuItems.get(j).setVisible(true);
		}
	}

	public void paneFalse() {
		for (int i = 0; i < pane.size(); i++) {
			pane.get(i).setVisible(false);
		}
	}
}
