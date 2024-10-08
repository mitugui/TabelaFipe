package br.com.alura.TabelaFipe.main;

import br.com.alura.TabelaFipe.model.BrandModels;
import br.com.alura.TabelaFipe.model.Data;
import br.com.alura.TabelaFipe.model.Vehicle;
import br.com.alura.TabelaFipe.service.ApiConsumption;
import br.com.alura.TabelaFipe.service.DataConverter;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
    private final Scanner reading = new Scanner(System.in);
    private final ApiConsumption apiConsumption = new ApiConsumption();
    private final DataConverter dataConverter = new DataConverter();

    public void displayMenu() {
        final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

        System.out.print("""
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                
                *** Digite uma das opções para consultar:
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

        System.out.println("\n*** Informe o código da marca para consulta:");
        var brandCode = reading.nextLine();

        Optional<Data> searchedBrand = brands.stream()
                .filter(b -> b.code().equals(brandCode))
                .findFirst();
        address += "/" + searchedBrand.get().code() + "/modelos";

        json = apiConsumption.getData(address);

        System.out.println("** Modelos dessa marca:");
        var brandModels = dataConverter.getData(json, BrandModels.class);
        brandModels.models().stream()
                .sorted(Comparator.comparing(Data::name))
                .forEach(System.out::println);

        System.out.println("\n*** Digite um trecho do nome do carro a ser buscado:");
        var vehicleName = reading.nextLine();

        List<Data> filteredModels = brandModels.models().stream()
                .filter(m -> m.name().toUpperCase().contains(vehicleName.toUpperCase()))
                .collect(Collectors.toList());

        System.out.println("** Modelos filtrados:");
        filteredModels.forEach(System.out::println);

        System.out.println("\n*** Digite o código do modelo para consultar valores:");
        var modelCode = reading.nextLine();

        Optional<Data> searchedModel = brandModels.models().stream()
                .filter(m -> m.code().equals(modelCode))
                .findFirst();

        address += "/" + searchedModel.get().code() + "/anos";
        json = apiConsumption.getData(address);

        List<Data> searchedModelWithDifferentYears = dataConverter.getListData(json, Data.class);

        List<String> modelYears = searchedModelWithDifferentYears.stream()
                .filter(s -> s.code().length() <= 6)
                .map(Data::code)
                .toList();

        List<Vehicle> vehicles = new ArrayList<>();

        for (String year : modelYears) {
            json = apiConsumption.getData(address + "/" + year);
            Vehicle vehicle = dataConverter.getData(json, Vehicle.class);
            vehicles.add(vehicle);
        }

        System.out.print("\n** Todos os veículos encontrados com avaliações por ano:");
        for (Vehicle v : vehicles) {
            System.out.println("\n" + v);
        }
    }
}
