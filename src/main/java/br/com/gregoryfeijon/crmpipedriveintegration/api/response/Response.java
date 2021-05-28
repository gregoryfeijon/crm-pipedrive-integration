package br.com.gregoryfeijon.crmpipedriveintegration.api.response;

import lombok.Data;

import java.util.List;

/**
 * 26/05/2021 às 23:31
 *
 * @author gregory.feijon
 */

@Data
public class Response<T> {

    private T data;
    private List<String> errors;
}
