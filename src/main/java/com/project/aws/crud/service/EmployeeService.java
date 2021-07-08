package com.project.aws.crud.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.project.aws.crud.dto.EmployeeDTO;
import com.project.aws.crud.model.Employee;
import com.project.aws.crud.repository.EmployeeRepository;
import com.project.aws.crud.utils.ErrorMessages;
import com.project.aws.crud.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Responsável por fornecer os serviços relacionados a um funcionário
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final DynamoDBMapper mapper;

    /**
     * Realiza a criação do Funcionário
     *
     * @param employee dados do funcionário a ser criado
     * @return {@code EmployeeDTO} dados do funcionário criado
     */
    public EmployeeDTO create(EmployeeDTO employee){
        log.info("M=create, message=Creating employee, document={}", employee.getDocument());

        final Optional<Employee> employeeByDocument = getByDocument(employee.getDocument());

        if (employeeByDocument.isPresent()){
            throw new BusinessException(ErrorMessages.EMPLOYEE_EXISTENT, employee.getDocument());
        }

        return employeeRepository.save(employee.toModel()).toDTO();
    }

    /**
     * Busca funcionário por ID
     *
     * @param id id a ser encontrado
     * @return {@code EmployeeDTO} dados do funcionário encontrado
     */
    public EmployeeDTO findById(final String id){
        log.info("M=getById, message=Finding employee by ID, id={}", id);
        return getById(id).toDTO();
    }

    /**
     * Busca funcionário por Documento
     *
     * @param document documento a ser encontrado
     * @return {@code EmployeeDTO} dados do funcionário encontrado
     */
    public EmployeeDTO findByDocument(final String document){
        log.info("M=findByDocument, message=Finding employee by document, document={}", document);
        return getByDocument(document).orElseThrow(() -> new BusinessException(ErrorMessages.EMPLOYEE_NOT_FOUND_BY_DOCUMENT, document)).toDTO();
    }

    /**
     * Lista todos os funcionários cadastrados
     *
     * @return {@code List<EmployeeDTO>} lista de funcionários
     */
    public List<EmployeeDTO> findAll(){
        log.info("M=findAll, message=Finding all employee");
        List<EmployeeDTO> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(emp -> employees.add(emp.toDTO()));
        return employees;
    }

    /**
     * Altera os dados de um funcionário por ID
     *
     * @param id id do funcionário a ser alterado
     * @param employee dados alterados do funcionário
     * @return {@code EmployeeDTO} dados do funcionário alterado
     */
    public EmployeeDTO update(final String id, final EmployeeDTO employee){
        log.info("M=update, message=Updating employee, id={}, employee={}", id, employee);

        Employee existEmployee = getById(id);

        if(!existEmployee.getDocument().equals(employee.getDocument())){
            throw new BusinessException(ErrorMessages.EMPLOYEE_CANT_CHANGE_DOCUMENT);
        }

        existEmployee.setFirstName(employee.getFirstName());
        existEmployee.setLastName(employee.getLastName());
        existEmployee.setEmail(employee.getEmail());

        return employeeRepository.save(existEmployee).toDTO();
    }

    /**
     * Deleta funcionário por ID
     *
     * @param id ID do funcionário a ser deletado
     */
    public void delete(final String id){
        log.info("M=delete, message=Deleting employee by ID, id={}", id);
        Employee employee = getById(id);
        employeeRepository.delete(employee);
    }

    /**
     * Busca funcionário por ID no banco de dados
     *
     * @param id id a ser encontrado
     * @return {@code Employee} dados do funcionário encontrado
     */
    private Employee getById(final String id){
        log.info("M=findByDocument, message=Finding employee by ID, id={}", id);
        return employeeRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorMessages.EMPLOYEE_NOT_FOUND_BY_ID, id));
    }

    /**
     * Busca o funcionário no banco de dados pelo Documento
     *
     * @param document documento a ser encontrado
     * @return {@code Employee} dados do funcionário encontrado
     */
    private Optional<Employee> getByDocument(final String document) {
        return employeeRepository.findByDocument(document);
    }
}
