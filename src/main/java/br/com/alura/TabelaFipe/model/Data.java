package br.com.alura.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Data(@JsonAlias("codigo") Integer code,
                   @JsonAlias("nome") String name) {
    @Override
    public String toString() {
        return "Cód: " + code + ", Descrição: " + name;
    }
}
