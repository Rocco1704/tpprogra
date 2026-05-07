package datos;

import java.util.Objects;

public class Localidad {
    private String nombre;
    private String provincia;
    private double latitud;
    private double longitud;

    public Localidad(String nombre, String provincia, double latitud, double longitud) {
    	
    	// Validamos Texto
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        if (provincia == null || provincia.trim().isEmpty()) {
            throw new IllegalArgumentException("La provincia no puede estar vacía.");
        }

        // Validamos Coordenadas (Regla geográfica real)
        if (latitud < -90 || latitud > 90) {
            throw new IllegalArgumentException("La latitud debe estar entre -90 y 90.");
        }
        if (longitud < -180 || longitud > 180) {
            throw new IllegalArgumentException("La longitud debe estar entre -180 y 180.");
        }
    	
    	
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

    /*
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
    */
    @Override
    public String toString() {
        return nombre + " (" + provincia + ")";
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Localidad otra = (Localidad) obj;

        // Normalizamos: pasamos a minúsculas y sacamos espacios para comparar nombres
        String nombreNormalizado1 = nombre.toLowerCase().replace(" ", "");
        String nombreNormalizado2 = otra.nombre.toLowerCase().replace(" ", "");
        
        return Objects.equals(nombreNormalizado1, nombreNormalizado2);
    }
    @Override
    public int hashCode() {
        // El hashCode también tiene que ser consistente con el nuevo equals
        return Objects.hash(nombre.toLowerCase().replace(" ", ""));
    }
}
