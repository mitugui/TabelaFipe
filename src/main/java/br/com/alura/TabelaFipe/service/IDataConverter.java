package br.com.alura.TabelaFipe.service;

public interface IDataConverter {
    <T> T getData(String data, Class<T> clazz);
}
