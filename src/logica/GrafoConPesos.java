package logica;

import java.util.ArrayList;
import java.util.List;
import datos.conexion;
import datos.Localidad;

public class GrafoConPesos extends GrafoVecinos {
    private ArrayList<conexion> aristas;

    public GrafoConPesos(int numLocalidades, List<Localidad> localidades) {
        super(numLocalidades, localidades);
        aristas = new ArrayList<conexion>();
    }

    public void agregarArista(Localidad localidad1, Localidad localidad2, double costo) {
        super.agregarArista(localidad1, localidad2);
        conexion nuevaConexion = new conexion(localidad1, localidad2, costo);
        if (!aristas.contains(nuevaConexion)) {
            aristas.add(nuevaConexion);
        }
    }

    @Override
    public void eliminarArista(Localidad localidad1, Localidad localidad2) {
        super.eliminarArista(localidad1, localidad2);
        for (int i = 0; i < aristas.size(); i++) {
            conexion c = aristas.get(i);
            if ((c.getLocalidad1().equals(localidad1) && c.getLocalidad2().equals(localidad2))
             || (c.getLocalidad1().equals(localidad2) && c.getLocalidad2().equals(localidad1))) {
                aristas.remove(i);
                break;
            }
        }
    }

    public double getCostoArista(Localidad localidad1, Localidad localidad2) {
        for (conexion c : aristas) {
            if ((c.getLocalidad1().equals(localidad1) && c.getLocalidad2().equals(localidad2))
             || (c.getLocalidad1().equals(localidad2) && c.getLocalidad2().equals(localidad1))) {
                return c.getCosto();
            }
        }
        throw new RuntimeException("No existe una conexión entre " 
            + localidad1.getNombre() + " y " + localidad2.getNombre());
    }

    public ArrayList<conexion> getAristas() {
        return aristas;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (conexion c : aristas) {
            sb.append(c.toString() + "\n");
        }
        return sb.toString();
    }
}