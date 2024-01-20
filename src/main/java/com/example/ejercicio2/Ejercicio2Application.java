package com.example.ejercicio2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@SpringBootApplication
public class Ejercicio2Application {

	public static void main(String[] args) {
		SpringApplication.run(Ejercicio2Application.class, args);
	}
}


@RestController
class RateController {
	@PostMapping("/getRate")
	public TasaResponse getRate(@RequestBody TasaRequest request) {
		Calendar fechaActual = Calendar.getInstance();
		double tasa = 0; // 2%

		if (request.getMarca().equals("VISA")) {
			tasa =  fechaActual.get(Calendar.YEAR) / 12;
		}else
			if (request.getMarca().equals("NARA")){
				tasa = fechaActual.get(Calendar.DAY_OF_MONTH) * 0.5;
			}else
				if (request.getMarca().equals("AMEX")){
					tasa = (fechaActual.get(Calendar.MONTH)+1) * 0.1;
				}


		double totalAmount = request.getMonto() * (tasa);


		TasaResponse response = new TasaResponse();
		response.setMarca(request.getMarca());
		response.getMonto(request.getMonto());
		response.setTasa(tasa);
		response.setMontoTotal(totalAmount);

		return response;
	}


	@ExceptionHandler(InvalidRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<String> handleInvalidRequest(InvalidRequestException e) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	}
}

class TasaRequest {
	private String marca;
	private double monto;


	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}
}

class TasaResponse {
	private String marca;
	private double monto;
	private double tasa;
	private double montoTotal;


	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public double getMonto(double monto) {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public double getTasa() {
		return tasa;
	}

	public void setTasa(double tasa) {
		this.tasa = tasa;
	}

	public double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(double montoTotal) {
		this.montoTotal = montoTotal;
	}

}
	class InvalidRequestException extends RuntimeException {
		public InvalidRequestException(String message) {
			super(message);
		}
	}
