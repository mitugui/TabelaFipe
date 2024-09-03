package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Vehicle(@JsonAlias("Valor") String value,
                      @JsonAlias("Marca") String brand,
                      @JsonAlias("Modelo") String model,
                      @JsonAlias("AnoModelo") Integer year,
                      @JsonAlias("Combustivel") String fuel,
                      @JsonAlias("MesReferencia") String referenceMonth,
                      @JsonAlias("CodigoFipe") String fipeCode) {
    @Override
    public String toString() {
        return "Veículo [VALOR: " + value + ", MÊS DE REFERÊNCIA: " + referenceMonth + ", CÓDIGO FIPE: " + fipeCode + ",\n\t\t" +
                "MARCA: " + brand + ", MODELO: " + model + ",\n\t\t" +
                "ANO: "+ year + ", COMBUSTÍVEL: "+ fuel +"]";
    }
}
