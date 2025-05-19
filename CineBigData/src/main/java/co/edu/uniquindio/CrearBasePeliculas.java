package co.edu.uniquindio;

import org.apache.jena.rdf.model.*;
import java.io.FileOutputStream;

public class CrearBasePeliculas {

    public static final String NS = "http://www.cinecolombia.com/peliculas#";

    public static void main(String[] args) {
        Model modelo = ModelFactory.createDefaultModel();

        modelo.setNsPrefix("cine", NS);

        crearPelicula(modelo, "pelicula1", "Godzilla y Kong", "Acción", "Español");
        crearPelicula(modelo, "pelicula2", "Garfield: fuera de casa", "Comedia", "Español");
        crearPelicula(modelo, "pelicula3", "El Planeta de los Simios", "Ciencia Ficción", "Español");
        crearPelicula(modelo, "pelicula4", "Back to Black", "Drama", "Inglés");

        try (FileOutputStream out = new FileOutputStream("peliculas.rdf")) {
            modelo.write(out, "RDF/XML");
            System.out.println("Base RDF creada exitosamente.");
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