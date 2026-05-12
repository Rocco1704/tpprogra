package test;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import logica.Prim;
import logica.GrafoConPesos;
import logica.GrafoVecinos;
import datos.Localidad;

public class PrimTest {

    @Test
    public void testPrimEligeCaminosMasBaratos() {
        Localidad a = new Localidad("A", "B", 0, 0);
        Localidad b = new Localidad("B", "B", 0, 1);
        Localidad c = new Localidad("C", "B", 1, 1);
        
        List<Localidad> locs = new ArrayList<>();
        locs.add(a); locs.add(b); locs.add(c);

        GrafoConPesos grafo = new GrafoConPesos(3, locs);
        
        grafo.agregarArista(a, b, 100.0);
        grafo.agregarArista(b, c, 100.0); 
        grafo.agregarArista(a, c, 500.0); 
        
        Prim prim = new Prim();
        GrafoConPesos agm = prim.primRecorrido(grafo);
        
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

        GrafoConPesos grafo = new GrafoConPesos(2, locs);
     
        Prim prim = new Prim();
        prim.primRecorrido(grafo); 
    }
}