package com.devs.mazebank.Models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Transaction {
    // instance methods
    ///  We use JavaFX properties because of data binding that will automatically
    /// update the UI when the value changes
    /// i.e. If a Label in our UI is bound to senderAddress it updates automatically when the senderaddress changes
    private final StringProperty senderAddress;
    private final StringProperty receiverAddress;
    private final DoubleProperty amountToBeSent;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty message;

    public Transaction(
            String senderAddress, String recieverAddress,
            double amountToBeSent, LocalDate date, String message
    ) {
        this.senderAddress = new SimpleStringProperty(this,"Sender Address" ,senderAddress);
        this.receiverAddress = new SimpleStringProperty(this, "Reciever Address", recieverAddress);
        this.amountToBeSent = new SimpleDoubleProperty(this, "Amount", amountToBeSent);
        this.date = new SimpleObjectProperty<>(this, "Date", date);
        this.message = new SimpleStringProperty(this, "Message", message);
    }


    public StringProperty senderAddressProperty() {
        return this.senderAddress;
    }


    public StringProperty receiverAddressProperty() {
        return this.receiverAddress;
    }


    public DoubleProperty amountToBeSentProperty() {
        return this.amountToBeSent;
    }


    public ObjectProperty<LocalDate> dateProperty() {
        return this.date;
    }


    public StringProperty messageProperty() {
        return this.message;
    }
}
