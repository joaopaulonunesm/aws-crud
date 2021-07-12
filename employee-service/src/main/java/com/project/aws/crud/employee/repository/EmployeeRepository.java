package com.project.aws.crud.employee.repository;

import com.project.aws.crud.employee.model.Employee;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Responsável por gerenciar os dados do funcionário no banco de dados
 */
@EnableScan
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, String> {

    /**
     * Busca funcionário no banco de dados pelo Documento
     *
     * @param document documento a ser encontrado o funcionário
     * @return {@code Optional<Employee>} dados do funcionário caso encontrado
    */
    Optional<Employee> findByDocument(final String document);
}
