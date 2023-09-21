package ui.pantallas.common;

public enum Pantallas {

    PANTALLACUSTOMERS ("/fxml/customers/showCustomers.fxml"),
    PANTALLAADD ("/fxml/customers/addCustomers.fxml"),
    LOGIN ("/fxml/login.fxml"),
    PANTALLAREMOVE ("/fxml/customers/deleteCustomers.fxml"),
    PANTALLAEDIT ("/fxml/customers/editCustomers.fxml"),

    PANTALLANUEVA (ConstantesPantallas.FXML_PANTALLA_NUEVA_FXML);

    private String ruta;
    Pantallas(String ruta) {
        this.ruta=ruta;
    }
    public String getRuta(){return ruta;}


}
