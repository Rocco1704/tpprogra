package logica;

import java.util.ArrayList;
import java.util.List;
import datos.conexion;
import datos.Localidad;
import datos.ParametroCosto;
import datos.PlanificacionResultado;

public class PlanificadorRed {
    private List<Localidad> localidades;

    public PlanificadorRed() {
        this.localidades = new ArrayList<>();
    }

    public boolean agregarLocalidad(Localidad localidad) {
        if (localidad == null) {
            throw new IllegalArgumentException("La localidad no puede ser null.");
        }
        if (localidades.contains(localidad)) {
            //throw new IllegalArgumentException("La localidad ya fue agregada: " + localidad.getNombre());
            return false;
        }
        localidades.add(localidad);
        return true;
    }

    public void eliminarLocalidad(Localidad localidad) {
        if (!localidades.contains(localidad)) {
            throw new IllegalArgumentException("La localidad no existe: " + localidad.getNombre());
        }
        localidades.remove(localidad);
    }

    public List<Localidad> getLocalidades() {
        return localidades;
    }

    public PlanificacionResultado planificar(ParametroCosto parametros) {
        if (localidades.size() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos 2 localidades para planificar.");
        }

        GrafoLocalidades grafoLocalidades = new GrafoLocalidades(parametros);
        GrafoConPesos grafoCompleto = grafoLocalidades.construirGrafoCompleto(localidades);

        Prim prim = new Prim();
        GrafoConPesos grafoAGM = prim.primRecorrido(grafoCompleto);

        List<conexion> conexiones = grafoAGM.getAristas();
        double costoTotal = calcularCostoTotal(conexiones);

        return new PlanificacionResultado(conexiones, costoTotal);
    }

    private double calcularCostoTotal(List<conexion> conexiones) {
        double total = 0;
        for (conexion c : conexiones) {
            total += c.getCosto();
        }
        return total;
    }
}