package br.com.alura.TabelaFipe.main;

import br.com.alura.TabelaFipe.model.BrandModels;
import br.com.alura.TabelaFipe.model.Data;
import br.com.alura.TabelaFipe.service.ApiConsumption;
import br.com.alura.TabelaFipe.service.DataConverter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private Scanner reading = new Scanner(System.in);
    private ApiConsumption apiConsumption = new ApiConsumption();
    private DataConverter dataConverter = new DataConverter();

    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void displayMenu() {
        System.out.print("""
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                Digite uma das opções para consultar:
                """);
        var vehicleTypeOption = reading.nextLine();

        String address = URL_BASE;
        if (vehicleTypeOption.equalsIgnoreCase("Carro")) {
            address += "carros/marcas";
        } else if (vehicleTypeOption.equalsIgnoreCase("Moto")) {
            address += "motos/marcas";
        } else if (vehicleTypeOption.equalsIgnoreCase("Caminhao")) {
            address += "caminhoes/marcas";
        }

        var json = apiConsumption.getData(address);

        List<Data> brands = dataConverter.getListData(json, Data.class);
        brands.stream()
                .sorted(Comparator.comparing(Data::name))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta:");
        var brandCode = reading.nextInt();

        Optional<Data> searchedBrand = brands.stream()
                .filter(b -> b.code().equals(brandCode))
                .findFirst();

        address += "/" + searchedBrand.get().code() + "/modelos";
        json = apiConsumption.getData(address);

        var brandModels = dataConverter.getData(json, BrandModels.class);
        brandModels.models().stream()
                .sorted(Comparator.comparing(Data::name))
                .forEach(System.out::println);
    }
}
