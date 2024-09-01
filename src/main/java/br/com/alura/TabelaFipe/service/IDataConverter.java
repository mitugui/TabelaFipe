package br.com.alura.TabelaFipe.service;

import java.util.List;

public interface IDataConverter {
    <T> T getData(String data, Class<T> clazz);

    <T> List<T> getListData(String data, Class<T> clazz);
}
