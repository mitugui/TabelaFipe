package br.com.alura.TabelaFipe.main;

import br.com.alura.TabelaFipe.service.ApiConsumption;

import java.util.Scanner;

public class Main {
    private Scanner reading = new Scanner(System.in);
    private ApiConsumption apiConsumption = new ApiConsumption();

    private String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void displayMenu() {
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar:
                """;

        System.out.println(menu);
        var option = reading.nextLine();

        String address = URL_BASE;
        if (option.equalsIgnoreCase("Carro")) {
            address += "carros/marcas/";
        } else if (option.equalsIgnoreCase("Moto")) {
            address += "motos/marcas/";
        } else if (option.equalsIgnoreCase("Caminhao")) {
            address += "caminhoes/marcas/";
        }

        var json = apiConsumption.getData(address);
        System.out.println(json);
    }
}
