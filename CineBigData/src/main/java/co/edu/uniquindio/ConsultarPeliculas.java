package co.edu.uniquindio;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.io.FileInputStream;

public class ConsultarPeliculas {

    public static final String NS = "http://www.cinecolombia.com/peliculas#";

    public static void consultarPeliculas() {
        try (FileInputStream in = new FileInputStream("peliculas.rdf")) {
            Model modelo = ModelFactory.createDefaultModel();
            modelo.read(in, null);

            ejecutarConsulta(modelo, "1. Todos los títulos", """
                PREFIX cine: <%s>
                SELECT ?titulo WHERE {
                  ?p cine:titulo ?titulo .
                }
                """);

            ejecutarConsulta(modelo, "2. Películas de acción", """
                PREFIX cine: <%s>
                SELECT ?titulo WHERE {
                  ?p cine:titulo ?titulo ;
                     cine:genero "accion" .
                }
                """);

            ejecutarConsulta(modelo, "3. Películas en español", """
                PREFIX cine: <%s>
                SELECT ?titulo WHERE {
                  ?p cine:titulo ?titulo ;
                     cine:idioma "español" .
                }
                """);

            ejecutarConsulta(modelo, "4. Géneros distintos", """
                PREFIX cine: <%s>
                SELECT DISTINCT ?genero WHERE {
                  ?p cine:genero ?genero .
                }
                """);

            ejecutarConsulta(modelo, "5. Películas cuyo título contiene 'El'", """
                PREFIX cine: <%s>
                SELECT ?titulo WHERE {
                  ?p cine:titulo ?titulo .
                  FILTER(CONTAINS(LCASE(?titulo), "el"))
                }
                """);

            ejecutarConsulta(modelo, "6. Películas en inglés", """
                PREFIX cine: <%s>
                SELECT ?titulo WHERE {
                  ?p cine:titulo ?titulo ;
                     cine:idioma "ingles" .
                }
                """);

            ejecutarConsulta(modelo, "7. Películas por género y título", """
                PREFIX cine: <%s>
                SELECT ?genero ?titulo WHERE {
                  ?p cine:genero ?genero ;
                     cine:titulo ?titulo .
                }
                ORDER BY ?genero
                """);

            ejecutarConsulta(modelo, "8. Cantidad de películas por idioma", """
                PREFIX cine: <%s>
                SELECT ?idioma (COUNT(?p) AS ?cantidad) WHERE {
                  ?p cine:idioma ?idioma .
                }
                GROUP BY ?idioma
                """);

            ejecutarConsulta(modelo, "9. Películas que no son comedia", """
                PREFIX cine: <%s>
                SELECT ?titulo WHERE {
                  ?p cine:titulo ?titulo ;
                     cine:genero ?genero .
                  FILTER(?genero != "comedia")
                }
                """);

            ejecutarConsulta(modelo, "10. Todos los recursos y propiedades", """
                SELECT ?s ?p ?o WHERE {
                  ?s ?p ?o .
                }
                LIMIT 20
                """);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ejecutarConsulta(Model modelo, String descripcion, String plantilla) {
        String consulta = String.format(plantilla, NS);
        System.out.println("\n--- " + descripcion + " ---");
        Query query = QueryFactory.create(consulta);
        try (QueryExecution qexec = QueryExecutionFactory.create(query, modelo)) {
            ResultSet results = qexec.execSelect();
            ResultSetFormatter.out(System.out, results, query);
        }
    }
}
