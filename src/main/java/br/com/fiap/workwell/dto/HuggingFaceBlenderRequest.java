package br.com.fiap.workwell.dto;

import lombok.Data;

import java.util.Map;

@Data
public class HuggingFaceBlenderRequest {
    private String inputs;
    private Map<String, Object> parameters;
    private Map<String, Object> options;
}
