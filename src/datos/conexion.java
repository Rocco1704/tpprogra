package datos;

import java.util.Objects;

public class conexion {
    private Localidad localidad1;
    private Localidad localidad2;
    private double costo;

    public conexion(Localidad localidad1, Localidad localidad2, double costo) {
        this.localidad1 = localidad1;
        this.localidad2 = localidad2;
        this.costo = costo;
    }

    public Localidad getLocalidad1() { return localidad1; }
    public Localidad getLocalidad2() { return localidad2; }
    public double getCosto() { return costo; }

    @Override
    public String toString() {
        return localidad1.getNombre() + " <-> " + localidad2.getNombre() 
               + " | Costo: $" + costo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(localidad1, localidad2, costo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        conexion otra = (conexion) obj;
        return Objects.equals(localidad1, otra.localidad1)
            && Objects.equals(localidad2, otra.localidad2)
            && Double.doubleToLongBits(costo) == Double.doubleToLongBits(otra.costo);
    }
}
