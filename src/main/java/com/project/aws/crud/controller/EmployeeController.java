package com.project.aws.crud.controller;

import com.project.aws.crud.dto.EmployeeDTO;
import com.project.aws.crud.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Responsável por prover os endpoints de funcionários
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Endpoint responsável por realizar a criação de funcionário
     *
     * @param employee dados do funcionário a ser criado
     * @return {@code ResponseEntity<EmployeeDTO>} dados do funcionário criado
     */
    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody @Valid final EmployeeDTO employee){
        return ResponseEntity.ok(employeeService.create(employee));
    }

    /**
     * Endpoint responsável por buscar os dados de um funcionário por ID
     *
     * @param id a ser encontrado
     * @return {@code ResponseEntity<EmployeeDTO>} dados do funcionário cadastrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable final String id){
        return ResponseEntity.ok(employeeService.findById(id));
    }

    /**
     * Endpoint responsável por buscar os dados de um funcionário por documento
     *
     * @param document a ser encontrado
     * @return {@code ResponseEntity<EmployeeDTO>} dados do funcionário cadastrado
     */
    @GetMapping("/document/{document}")
    public ResponseEntity<EmployeeDTO> findByDocument(@PathVariable final String document){
        return ResponseEntity.ok(employeeService.findByDocument(document));
    }

    /**
     * Endpoint responsável por buscar dos os funcionários cadastrados
     *
     * @return {@code ResponseEntity<List<EmployeeDTO>>} dados de todos os funcionários cadastrados
     */
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> findAll(){
        return ResponseEntity.ok(employeeService.findAll());
    }

    /**
     * Enpoint responsável por realizar a alteração dos dados de um funcionário por ID
     *
     * @param id do funcionário a ser alterado
     * @param employee dados alterados do funcionário
     * @return {@code ResponseEntity<EmployeeDTO>} novos dados do funcionário
     */
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable final String id, @RequestBody @Valid final EmployeeDTO employee){
        return ResponseEntity.ok(employeeService.update(id, employee));
    }

    /**
     * Endpoint responsável por fazer a remoção de um funcionário
     *
     * @param id do funcionário a ser removido
     * @return {@code Void} retorna apenas pelo HttpStatus
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final String id){
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
