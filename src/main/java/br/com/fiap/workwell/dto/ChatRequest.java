package br.com.fiap.workwell.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequest {

    @NotBlank(message = "A situação não pode estar em branco.")
    @Size(max = 2000, message = "A situação deve ter no máximo 2000 caracteres.")
    private String situacao;
}