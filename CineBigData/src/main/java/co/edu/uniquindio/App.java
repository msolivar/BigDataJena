package co.edu.uniquindio;

import javax.swing.JOptionPane;

import static org.apache.jena.vocabulary.OWLResults.system;

public class App {
    public static void main(String[] args) {
        while (true) {
            String opcionStr = JOptionPane.showInputDialog("""
                    MENÚ PRINCIPAL
                    1. Crear base de datos
                    2. Consultar base de datos
                    0. Salir
                    Ingrese una opción:
                    """);

            if (opcionStr == null) {
                // Si el usuario cierra el diálogo, también salimos
                System.exit(0);
            }

            int opcion;
            try {
                opcion = Integer.parseInt(opcionStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
                continue;
            }

            switch (opcion) {
                case 1 -> CrearBasePeliculas.crearBasePeliculas();  // Método estático que encapsula la lógica de creación
                case 2 -> ConsultarPeliculas.consultarPeliculas();  // Método estático que encapsula la lógica de consulta
                case 0 -> {
                    JOptionPane.showMessageDialog(null, "Gracias por usar el sistema. ¡Hasta luego!");
                    System.exit(0);
                }
                default -> JOptionPane.showMessageDialog(null, "Opción inválida. Intente de nuevo.");
            }
        }
    }
}
