module javafx {


    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;
    requires org.apache.logging.log4j;


    requires jakarta.inject;
    requires jakarta.cdi;
    requires io.vavr;
    exports ui.pantallas.orders.deleteorders;
    exports services.impl;
    exports dao.imp;
    exports model;
    exports ui.main to javafx.graphics;
    exports ui.pantallas.principal;
    exports ui.pantallas.login;
    exports ui.pantallas.common;
    exports ui.pantallas.bienvenida;
    opens ui.pantallas.login;
    opens ui.pantallas.customers.editcustomers;
    opens ui.pantallas.principal;
    opens ui.main;
    opens ui.pantallas.orders.listorders;
    opens css;
    opens fxml;
    opens ui.pantallas.orders.addorder;
    opens ui.pantallas.orders.editorder;
    opens ui.pantallas.customers.addcustomer;
    opens ui.pantallas.customers.deletecustomer;
    opens ui.pantallas.customers.showcustomers;
    opens ui.pantallas.orders.deleteorders;
    exports ui.pantallas.customers.addcustomer;
    exports ui.pantallas.customers.showcustomers;
    exports ui.pantallas.customers.deletecustomer;
    exports ui.pantallas.customers.editcustomers;
    exports ui.pantallas.orders.listorders;
    exports ui.pantallas.orders.addorder;
}
