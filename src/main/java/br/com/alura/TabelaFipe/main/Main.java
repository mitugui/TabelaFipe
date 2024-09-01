package br.com.alura.TabelaFipe.main;

import br.com.alura.TabelaFipe.model.Data;
import br.com.alura.TabelaFipe.service.ApiConsumption;
import br.com.alura.TabelaFipe.service.DataConverter;

import java.util.Scanner;

public class Main {
    private Scanner reading = new Scanner(System.in);
    private ApiConsumption apiConsumption = new ApiConsumption();
    private DataConverter dataConverter = new DataConverter();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void displayMenu() {
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar: 
                """;

        System.out.print(menu);
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

        var brands = dataConverter.getListData(json, Data.class);
        brands.forEach(System.out::println);
    }
}
