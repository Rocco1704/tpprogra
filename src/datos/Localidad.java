package datos;

import java.util.Objects;

public class Localidad {
    private String nombre;
    private String provincia;
    private double latitud;
    private double longitud;

    public Localidad(String nombre, String provincia, double latitud, double longitud) {
        this.nombre = nombre;
        this.provincia = provincia;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Localidad() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, provincia, latitud, longitud);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Localidad otra = (Localidad) obj;
        return Double.doubleToLongBits(latitud) == Double.doubleToLongBits(otra.latitud)
            && Double.doubleToLongBits(longitud) == Double.doubleToLongBits(otra.longitud)
            && Objects.equals(nombre, otra.nombre)
            && Objects.equals(provincia, otra.provincia);
    }

    @Override
    public String toString() {
        return nombre + " (" + provincia + ")";
    }
}
