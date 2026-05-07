package test;
import static org.junit.Assert.*; 
import org.junit.Test; //importa junit
import datos.Localidad;	//CLASE LOCALIDAD
import logica.PlanificadorRed;

public class LocalidadTest {

	//Localidad vacia= lanza error
	@Test(expected= IllegalArgumentException.class)
	public void testLocalidadvaciaError() {
		new Localidad("","Buenos Aires", -34.5,-58.9);
	}
	// Provincia Vacia
	@Test(expected= IllegalArgumentException.class)
	public void testProvinciaVacioError() {
		new Localidad("Polvorines","", 80.4, 150.0);
	}
	//Latitud Vacia, como las coordenadas son double el erro es NumberFormaExceptio
	@Test(expected = NumberFormatException.class)
	public void testLatitudVaciaError() {
		Double.parseDouble("");
	}
	
	// longitud vacia
	@Test(expected = NumberFormatException.class)
	public void testLongitudVaciaError(){
		Double.parseDouble("");	
	}
	
	// Localidad Valida 
	@Test
	public void testLocalidadValida() {
		PlanificadorRed planificador= new PlanificadorRed();
		Localidad Pilar = new Localidad ("Pilar", "Buenos Aires", -34.4, -58.9);
		boolean resultado = planificador.agregarLocalidad (Pilar);
		assertTrue (resultado); //devuelve true si es verdadero
	}

	@Test//verifica que no se agregue uno que ya existe, no considera mayusculas ni espacios
	public void testLocalidadInvalidaYaCreada() {
		PlanificadorRed planificador = new PlanificadorRed();
		Localidad Pilar1 = new Localidad ("Jose.c.Paz", "Buenos Aires", -34.4, -58.9);
		Localidad Pilar2 = new Localidad ("jose.c.paz", "buenosaires", -34.4, -58.9);
		
		planificador.agregarLocalidad(Pilar1);
		boolean resultado = planificador.agregarLocalidad(Pilar2);

		assertFalse(resultado);
	}
	
	/*	
	COORDENADAS LIMITE
	 LATITUD= DESDE -90.0 (Polo Sur) HASTA 90.0(Polo Norte)
	 LONGITUD= -180.0 (Oeste) HASTA EL ESTE 180.0
	 */
	@Test // MAXIMALATITUD
	public void testLatitudLimite() {
		Localidad local = new Localidad ("Polo Norte", "Tierra", 90.0,0.0);
		assertEquals(90.0, local.getLatitud(), 0.0001);
	}
	 @Test (expected = IllegalArgumentException.class)
	 public void testLatitudSuperaAlLimite1() {
		 new Localidad ("ERROR", "provincia", 90.1, 0.0);
	 }
	 @Test (expected = IllegalArgumentException.class)
	 public void testLatitudSuperaAlLimite2() {
		 new Localidad ("ERROR", "provincia", -90.1, 0.0);
	 }
	
	 ///// LONGITUD TEST
	 @Test
	 public void testLongitudLimieta() {
		 Localidad local= new Localidad ("Antimeridiano", "Tierra", 0.0, 180.0);
		 assertEquals(180.0, local.getLongitud(),0.0001);
	 }
	 
	 @Test (expected = IllegalArgumentException.class)
	 public void testLongitudSuperaLimite1() {
		 new Localidad("Error","Provincia", 0.0, 181.0);
	 }
	 @Test (expected = IllegalArgumentException.class)
	 public void testLongitudSuperaLimite2() {
		 new Localidad("Error","Provincia", 0.0, -181.0);
	 }
	 
	 
}
