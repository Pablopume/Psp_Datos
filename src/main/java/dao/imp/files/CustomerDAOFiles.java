package dao.imp.files;

import dao.CustomerDAO;
import model.Customer;

import java.io.IOException;
import java.lang.module.Configuration;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CustomerDAOFiles implements CustomerDAO {
    private List<Customer> listCustomer;
    private Configuration config;
    @Override
    public List<Customer> getAll() {
        return listCustomer;
    }
    public static void createFile(){
        String nombreArchivo = "data/listaElementos";

        try {
            // Obtener el sistema de archivos por defecto
            Path archivo = FileSystems.getDefault().getPath(nombreArchivo);

            // Intentar crear el archivo, pero solo si no existe
            Files.createFile(archivo);
            System.out.println("Archivo creado con éxito: " + nombreArchivo);
        } catch (FileAlreadyExistsException e) {
            System.out.println("El archivo ya existe: " + nombreArchivo);
        } catch (IOException e) {
            System.err.println("Error al crear el archivo: " + e.getMessage());
        }
    }


    public static List readFile(String file) throws IOException {
        if (!Files.exists(FileSystems.getDefault().getPath(file))) {
            createFile();
        }
        ArrayList aux = null;
        try (Stream<String> lines = Files.lines(Path.of(file))) {
            lines.forEach(line -> {
                Customer customer = new Customer(line);
                aux.add(customer);
            });
        }
        return aux;
    }
}
