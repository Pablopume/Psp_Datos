package ui.pantallas.common;

public enum Pantallas {

    PANTALLACUSTOMERS ("/fxml/customers/listCustomers.fxml"),
    PANTALLAADD ("/fxml/customers/addCustomers.fxml"),
    LOGIN ("/fxml/login.fxml"),
    PANTALLAREMOVE ("/fxml/customers/deleteCustomers.fxml"),
    PANTALLAEDIT ("/fxml/customers/editCustomers.fxml"),
    PANTALLAORDERS ("/fxml/orders/listOrders.fxml"),
    ADDORDERS ("/fxml/orders/addOrders.fxml"),

    REMOVEORDERS ("/fxml/orders/deleteOrders.fxml"),
    EDITORDERS ("/fxml/orders/editOrders.fxml"),

    PANTALLANUEVA (ConstantesPantallas.FXML_PANTALLA_NUEVA_FXML);

    private String ruta;
    Pantallas(String ruta) {
        this.ruta=ruta;
    }
    public String getRuta(){return ruta;}


}
