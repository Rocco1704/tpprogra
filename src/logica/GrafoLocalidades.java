package logica;

import java.util.List;
import datos.Localidad;
import datos.ParametroCosto;

public class GrafoLocalidades {
    private double costoPorKm;
    private double porcentajeAumentoLargaDistancia;
    private double costoFijoInterProvincia;

    public GrafoLocalidades(ParametroCosto parametros) {
        this.costoPorKm = parametros.getCostoPorKm();
        this.porcentajeAumentoLargaDistancia = parametros.getPorcentajeAumentoLargaDistancia();
        this.costoFijoInterProvincia = parametros.getCostoFijoInterProvincia();
    }

    public GrafoConPesos construirGrafoCompleto(List<Localidad> localidades) {
        if (localidades == null || localidades.isEmpty()) {
            throw new IllegalArgumentException("La lista de localidades no puede ser vacía.");
        }
        if (localidades.size() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos 2 localidades para planificar.");
        }

        GrafoConPesos grafo = new GrafoConPesos(localidades.size(), localidades);

        for (int i = 0; i < localidades.size(); i++) {
            for (int j = i + 1; j < localidades.size(); j++) {
                Localidad localidad1 = localidades.get(i);
                Localidad localidad2 = localidades.get(j);
                double costo = calcularCosto(localidad1, localidad2);
                grafo.agregarArista(localidad1, localidad2, costo);
            }
        }

        return grafo;
    }

    public double calcularCosto(Localidad localidad1, Localidad localidad2) {
        double distancia = calcularDistanciaKm(
            localidad1.getLatitud(), localidad1.getLongitud(),
            localidad2.getLatitud(), localidad2.getLongitud()
        );

        double costo = distancia * costoPorKm;

        // Regla 1: aumento por larga distancia
        if (distancia > 300) {
            double factor = 1 + (porcentajeAumentoLargaDistancia / 100.0);
            costo = costo * factor;
        }

        // Regla 2: costo fijo interprovincial
        if (!localidad1.getProvincia().equalsIgnoreCase(localidad2.getProvincia())) {
            costo += costoFijoInterProvincia;
        }

        return Math.floor(costo);
    }

    public double calcularDistanciaKm(double lat1, double lon1, double lat2, double lon2) {
        final int RADIO_TIERRA_KM = 6371;
        double diferenciaLat = Math.toRadians(lat2 - lat1);
        double diferenciaLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(diferenciaLat / 2) * Math.sin(diferenciaLat / 2)
                 + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                 * Math.sin(diferenciaLon / 2) * Math.sin(diferenciaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RADIO_TIERRA_KM * c;
    }
}