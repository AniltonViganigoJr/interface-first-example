package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.println("***Entre com os dados do aluguel***");
		System.out.print("Modelo do carro:");
		String carModel = sc.nextLine();
		System.out.print("Retirada (DD/MM/YYYY HH:MM):");
		LocalDateTime start = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		System.out.print("Retorno (DD/MM/YYYY HH:MM):");
		LocalDateTime finish = LocalDateTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		
		CarRental carRental = new CarRental(start, finish, new Vehicle(carModel));

		System.out.print("Entre com o pre�o por hora: $");
		double pricePerHour = sc.nextDouble();
		System.out.print("Entre com o pre�o por dia: $");
		double pricePerDay = sc.nextDouble();
		
		RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());
		
		rentalService.processInvoice(carRental);
		
		System.out.println("****FATURA****");
		System.out.println("Pagamento b�sico: $" + String.format("%.2f",carRental.getInvoice().getBasicPayment()));
		System.out.println("Imposto: $" + String.format("%.2f",carRental.getInvoice().getTax()));
		System.out.println("Pagamento total: $" + String.format("%.2f",carRental.getInvoice().getTotalPayment()));
		sc.close();
	}
}