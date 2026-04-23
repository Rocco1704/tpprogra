package datos;

import java.util.List;

public class PlanificacionResultado {
    private List<conexion> conexiones;
    private double costoTotal;

    public PlanificacionResultado(List<conexion> conexiones, double costoTotal) {
        this.conexiones = conexiones;
        this.costoTotal = costoTotal;
    }

    public List<conexion> getConexiones() { return conexiones; }
    public double getCostoTotal() { return costoTotal; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Resultado de la Planificación ===\n");
        for (conexion c : conexiones) {
            sb.append("  " + c.toString() + "\n");
        }
        sb.append("Costo total: $" + costoTotal);
        return sb.toString();
    }
}
