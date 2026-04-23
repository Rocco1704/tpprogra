package datos;

public class ParametroCosto {
    private double costoPorKm;
    private double porcentajeAumentoLargaDistancia;
    private double costoFijoInterProvincia;

    public ParametroCosto(double costoPorKm, double porcentajeAumentoLargaDistancia, 
                          double costoFijoInterProvincia) {
        this.costoPorKm = costoPorKm;
        this.porcentajeAumentoLargaDistancia = porcentajeAumentoLargaDistancia;
        this.costoFijoInterProvincia = costoFijoInterProvincia;
    }

    public double getCostoPorKm() { return costoPorKm; }
    public void setCostoPorKm(double costoPorKm) { this.costoPorKm = costoPorKm; }

    public double getPorcentajeAumentoLargaDistancia() { return porcentajeAumentoLargaDistancia; }
    public void setPorcentajeAumentoLargaDistancia(double porcentaje) { 
        this.porcentajeAumentoLargaDistancia = porcentaje; 
    }

    public double getCostoFijoInterProvincia() { return costoFijoInterProvincia; }
    public void setCostoFijoInterProvincia(double costo) { 
        this.costoFijoInterProvincia = costo; 
    }

    @Override
    public String toString() {
        return "Costo por km: $" + costoPorKm 
            + " | Aumento larga distancia: " + porcentajeAumentoLargaDistancia + "%"
            + " | Costo interprovincial: $" + costoFijoInterProvincia;
    }
}