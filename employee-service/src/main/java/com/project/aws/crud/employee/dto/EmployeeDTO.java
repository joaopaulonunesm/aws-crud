package com.project.aws.crud.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.aws.crud.employee.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    @NotBlank
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
