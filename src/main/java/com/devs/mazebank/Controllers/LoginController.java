package com.devs.mazebank.Controllers;

import com.devs.mazebank.Models.Model;
import com.devs.mazebank.Views.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public ChoiceBox<AccountType> account_selector;
    public Label payee_address_lbl;
    public TextField payee_adress_field;
    public Label password_lbl;
    public PasswordField password_field;
    public Button login_btn;
    public Label error_lbl;

    // When we click the login button it will take us to the client window
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ///  setItems method takes an observable liste as parameters
        ///  this will allow us to select account types on login window
        account_selector.setItems(FXCollections.observableArrayList(AccountType.CLIENT, AccountType.ADMIN));
        ///  This will set the defualt value of the choice box above to be CLIENT
        account_selector.setValue(Model.getInstance().getViewFactory().getLoginAccountType());
        ///  Everytime we change the choice box value we want to set it
        account_selector.valueProperty().addListener(observable->{
             Model.getInstance().getViewFactory().setLoginAccountType(account_selector.getValue());
        });
        login_btn.setOnAction(e ->{
            onLogin();
        });
    }

    private void onLogin() {
        //Change the button text and diable it while processing
        login_btn.setText("Logging in...");
        login_btn.setDisable(true);

        ///  If the account type selected is client, we show client window
        if (Model.getInstance().getViewFactory().getLoginAccountType() == AccountType.CLIENT){
            /// Evaluate credentials
            Model.getInstance().evaluateClientCredentials(payee_adress_field.getText(), password_field.getText());
            /// check if client is Logged in
            if (Model.getInstance().isLoggedIn()){
                ///  Show client window
                Model.getInstance().getViewFactory().showClientWindow();
            }else {
                /// If credentials are wrong then we reset the password and adress fields and show error
                payee_adress_field.setText("");
                password_field.setText("");
                error_lbl.setText("No such Login credentials");

                // Re-enable the button and reset text
                login_btn.setText("Login");
                login_btn.setDisable(false);
            }
        } else {
            Stage stage = (Stage) login_btn.getScene().getWindow();
            stage.close();
            ///  show admin window
            Model.getInstance().getViewFactory().showAdminWindow();
        }
    }

}
