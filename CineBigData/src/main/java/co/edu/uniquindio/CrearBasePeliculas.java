package co.edu.uniquindio;

import org.apache.jena.rdf.model.*;

import javax.swing.*;
import java.io.FileOutputStream;

public class CrearBasePeliculas {

    public static final String NS = "http://www.cinecolombia.com/peliculas#";

    public static void crearBasePeliculas() {
        Model modelo = ModelFactory.createDefaultModel();
        modelo.setNsPrefix("cine", NS);

        // Películas predefinidas
        crearPelicula(modelo, "pelicula1", "Godzilla y Kong", "accion", "español");
        crearPelicula(modelo, "pelicula2", "Garfield: fuera de casa", "comedia", "español");
        crearPelicula(modelo, "pelicula3", "El Planeta de los Simios", "ciencia ficción", "español");
        crearPelicula(modelo, "pelicula4", "Back to Black", "drama", "ingles");

        int contador = 5;
        while (true) {
            String ingresarElemento = JOptionPane.showInputDialog("Quieres ingresar una pelicula \nSi => 1 o No => 0):");
            if(ingresarElemento.equals("0")){
                break;
            }
            else{
                contador++;

                String titulo = JOptionPane.showInputDialog("Ingrese el título de la película (o 0 para salir):");
                if (titulo == null ) titulo="sinTituloSugerido"+ contador;

                String genero = JOptionPane.showInputDialog("Ingrese el género:");
                if (genero == null) genero="sinGeneroSugerido"+ contador;

                String idioma = JOptionPane.showInputDialog("Ingrese el idioma:");
                if (idioma == null) idioma="sinIdiomaSugerido"+ contador;

                crearPelicula(modelo, "pelicula" + contador, titulo, genero, idioma);
            }
        }

        try (FileOutputStream out = new FileOutputStream("peliculas.rdf")) {
            modelo.write(out, "RDF/XML");
            JOptionPane.showMessageDialog(null, "Base RDF creada exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void crearPelicula(Model modelo, String id, String titulo, String genero, String idioma) {
        Resource pelicula = modelo.createResource(NS + id);
        pelicula.addProperty(modelo.createProperty(NS, "titulo"), titulo);
        pelicula.addProperty(modelo.createProperty(NS, "genero"), genero);
        pelicula.addProperty(modelo.createProperty(NS, "idioma"), idioma);
    }
}
