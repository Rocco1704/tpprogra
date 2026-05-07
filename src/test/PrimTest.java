package test;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import logica.Prim;
import logica.GrafoConPesos;
import datos.Localidad;

public class PrimTest {

    @Test
    public void testPrimEligeCaminosMasBaratos() {
        // 1. Creamos 3 localidades
        Localidad a = new Localidad("A", "B", 0, 0);
        Localidad b = new Localidad("B", "B", 0, 1);
        Localidad c = new Localidad("C", "B", 1, 1);
        
        List<Localidad> locs = new ArrayList<>();
        locs.add(a); locs.add(b); locs.add(c);
        
        // 2. Grafo completo (un triángulo)
        GrafoConPesos grafo = new GrafoConPesos(3, locs);
        
        // Conexiones:
        grafo.agregarArista(a, b, 100.0); // Barato
        grafo.agregarArista(b, c, 100.0); // Barato
        grafo.agregarArista(a, c, 500.0); // CARO
        
        // 3. Ejecutamos Prim
        Prim prim = new Prim();
        GrafoConPesos agm = prim.primRecorrido(grafo);
        
        // 4. Verificaciones
        // El AGM de 3 nodos siempre tiene 2 aristas (n-1)
        // Y no debería contener la arista de 500.0
        assertFalse("Prim no debería elegir la arista más cara", agm.existeArista(a, c));
        assertTrue("Prim debería elegir la arista A-B", agm.existeArista(a, b));
        assertTrue("Prim debería elegir la arista B-C", agm.existeArista(b, c));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPrimGrafoNoConexoLanzaError() {
        Localidad a = new Localidad("A", "B", 0, 0);
        Localidad b = new Localidad("B", "B", 0, 1);
        List<Localidad> locs = new ArrayList<>();
        locs.add(a); locs.add(b);

        // Grafo de 2 nodos sin aristas (no conexo)
        GrafoConPesos grafo = new GrafoConPesos(2, locs);
        
        Prim prim = new Prim();
        prim.primRecorrido(grafo); // Debería saltar el catch de tu BFS
    }
}