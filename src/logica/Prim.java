package logica;

import java.util.HashSet;
import java.util.Set;
import datos.conexion;
import datos.Localidad;

public class Prim {
    private Set<Localidad> localidadesVisitadas;

    public GrafoConPesos primRecorrido(GrafoConPesos grafoCompleto) {
        BFS bfs = new BFS();
        if (!bfs.estaConectado(grafoCompleto)) {
            throw new IllegalArgumentException("El grafo no es conexo.");
        }

        GrafoConPesos grafoAGM = new GrafoConPesos(grafoCompleto.tamanio(), grafoCompleto.localidades);
        localidadesVisitadas = new HashSet<>();
        localidadesVisitadas.add(grafoCompleto.getLocalidad(0));

        while (localidadesVisitadas.size() < grafoCompleto.tamanio()) {
            conexion conexionMinima = buscarConexionMinima(grafoCompleto, grafoAGM);
            Localidad localidad1 = conexionMinima.getLocalidad1();
            Localidad localidad2 = conexionMinima.getLocalidad2();
            grafoAGM.agregarArista(localidad1, localidad2, conexionMinima.getCosto());
            localidadesVisitadas.add(localidad1);
            localidadesVisitadas.add(localidad2);
        }

        return grafoAGM;
    }

    private conexion buscarConexionMinima(GrafoConPesos grafoCompleto, GrafoConPesos grafoAGM) {
        conexion conexionMinima = null;
        double costoMinimo = Double.POSITIVE_INFINITY;

        for (Localidad localidad : localidadesVisitadas) {
            Set<Localidad> vecinos = grafoCompleto.vecinos(localidad);
            for (Localidad vecino : vecinos) {
                if (!estaEnAGM(vecino) && !grafoAGM.existeArista(localidad, vecino)) {
                    double costo = grafoCompleto.getCostoArista(localidad, vecino);
                    if (costo < costoMinimo) {
                        costoMinimo = costo;
                        conexionMinima = new conexion(localidad, vecino, costo);
                    }
                }
            }
        }
        return conexionMinima;
    }

    private boolean estaEnAGM(Localidad localidad) {
        return localidadesVisitadas.contains(localidad);
    }
}