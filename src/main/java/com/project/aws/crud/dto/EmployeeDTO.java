package com.project.aws.crud.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.aws.crud.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * DTO responsável por receber e retornar dados do funcionário
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {

    private String id;
    @NotNull
    @Positive
    private String document;
    @NotNull
    private String firstName;
    private String lastName;
    @Email
    private String email;

    public Employee toModel(){
            return Employee.builder()
                    .document(this.document)
                    .firstName(this.firstName)
                    .lastName(this.lastName)
                    .email(this.email)
                    .build();
    }
}
