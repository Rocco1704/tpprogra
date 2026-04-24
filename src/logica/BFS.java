package logica;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import datos.Localidad;

public class BFS {
    private boolean[] visitados;
    private List<Localidad> cola;

    public boolean estaConectado(GrafoVecinos grafo) {
        if (grafo == null) {
            throw new IllegalArgumentException("El grafo no puede ser null.");
        }
        if (grafo.tamanio() == 0) {
            return true;
        }
        return alcanzables(grafo, grafo.getLocalidad(0)).size() == grafo.tamanio();
    }

    public Set<Localidad> alcanzables(GrafoVecinos grafo, Localidad localidad) {
        Set<Localidad> resultado = new HashSet<Localidad>();
        inicializarBusqueda(grafo, localidad);
        while (cola.size() > 0) {
            Localidad actual = seleccionarYMarcar(grafo);
            resultado.add(actual);
            agregarVecinosNoVisitados(grafo, actual);
            eliminarPrimero();
        }
        return resultado;
    }

    private void inicializarBusqueda(GrafoVecinos grafo, Localidad localidad) {
        cola = new LinkedList<Localidad>();
        cola.add(localidad);
        visitados = new boolean[grafo.tamanio()];
    }

    private Localidad seleccionarYMarcar(GrafoVecinos grafo) {
        Localidad actual = cola.get(0);
        visitados[grafo.getLocalidades().indexOf(actual)] = true;
        return actual;
    }

    private void agregarVecinosNoVisitados(GrafoVecinos grafo, Localidad actual) {
        for (Localidad vecino : grafo.vecinos(actual)) {
            int indice = grafo.getLocalidades().indexOf(vecino);
            if (!visitados[indice] && !cola.contains(vecino)) {
                cola.add(vecino);
            }
        }
    }

    private void eliminarPrimero() {
        cola.remove(0);
    }
}