package logica;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import datos.Localidad;

public class GrafoVecinos {
    protected ArrayList<HashSet<Localidad>> vecinos;
    protected List<Localidad> localidades;

    public GrafoVecinos(int numLocalidades, List<Localidad> localidades) {
        if (localidades == null) {
            throw new IllegalArgumentException("La lista de localidades no puede ser null.");
        }
        if (localidades.size() != numLocalidades) {
            throw new IllegalArgumentException("La cantidad de localidades no coincide con el número de vértices.");
        }
        this.localidades = localidades;
        vecinos = new ArrayList<HashSet<Localidad>>();
        for (int i = 0; i < numLocalidades; i++) {
            vecinos.add(new HashSet<Localidad>());
        }
    }

    public void agregarArista(Localidad i, Localidad j) {
        verificarLocalidad(i);
        verificarLocalidad(j);
        verificarDistintas(i, j);
        if (!existeArista(i, j)) {
            vecinos.get(localidades.indexOf(i)).add(j);
            vecinos.get(localidades.indexOf(j)).add(i);
        }
    }

    public void eliminarArista(Localidad i, Localidad j) {
        verificarLocalidad(i);
        verificarLocalidad(j);
        verificarDistintas(i, j);
        vecinos.get(localidades.indexOf(i)).remove(j);
        vecinos.get(localidades.indexOf(j)).remove(i);
    }

    public boolean existeArista(Localidad i, Localidad j) {
        verificarLocalidad(i);
        verificarLocalidad(j);
        verificarDistintas(i, j);
        return vecinos.get(localidades.indexOf(i)).contains(j)
            && vecinos.get(localidades.indexOf(j)).contains(i);
    }

    public Set<Localidad> vecinos(Localidad i) {
        verificarLocalidad(i);
        return vecinos.get(localidades.indexOf(i));
    }

    public int tamanio() {
        return vecinos.size();
    }

    public Localidad getLocalidad(int i) {
        return localidades.get(i);
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public void verificarLocalidad(Localidad i) {
        if (i == null) {
            throw new IllegalArgumentException("La localidad no puede ser null.");
        }
    }

    public void verificarDistintas(Localidad i, Localidad j) {
        if (i.equals(j)) {
            throw new IllegalArgumentException("No se permiten conexiones de una localidad consigo misma.");
        }
    }
}