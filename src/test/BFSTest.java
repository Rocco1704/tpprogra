package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import logica.BFS;
import logica.GrafoVecinos; // Cambiado para que coincida con tu lógica
import datos.Localidad;

public class BFSTest {

	@Test 
	public void testGrafoConexo3Nodos() {
	    BFS bfs = new BFS();

	    Localidad loc1 = new Localidad("JCP", "BSAS", -34.5, -58.7);
	    Localidad loc2 = new Localidad("San Miguel", "BSAS", -34.5, -58.8);
	    Localidad loc3 = new Localidad("Pilar", "BSAS", -34.4, -58.9);
	    
	    List<Localidad> lista = new ArrayList<>();
	    lista.add(loc1);
	    lista.add(loc2);
	    lista.add(loc3);
	  
	    GrafoVecinos grafo = new GrafoVecinos(3, lista);
	 
	    grafo.agregarArista(loc1, loc2);
	    grafo.agregarArista(loc2, loc3);

	    boolean resultado = bfs.estaConectado(grafo);
	    assertTrue(resultado);
	}
	
	@Test
	public void testGrafoVacio() {
	    BFS bfs = new BFS();
	    GrafoVecinos grafoVacio = new GrafoVecinos(0, new ArrayList<Localidad>());
	    
	    assertTrue(bfs.estaConectado(grafoVacio));
	}

	@Test 
	public void testNOConexo() {
	    BFS bfs = new BFS();
	    
	    Localidad loc1 = new Localidad("JCP", "BSAS", -34.5, -58.7);
	    Localidad loc2 = new Localidad("San Miguel", "BSAS", -34.5, -58.8);
	    Localidad loc3 = new Localidad("Aislada", "BSAS", -30.0, -50.0);
	    
	    List<Localidad> lista = new ArrayList<>();
	    lista.add(loc1);
	    lista.add(loc2);
	    lista.add(loc3);
	 
	    GrafoVecinos grafo = new GrafoVecinos(3, lista);
	    
	    grafo.agregarArista(loc1, loc2);
	    
	    boolean resultado = bfs.estaConectado(grafo);
	    assertFalse(resultado);
	}
}