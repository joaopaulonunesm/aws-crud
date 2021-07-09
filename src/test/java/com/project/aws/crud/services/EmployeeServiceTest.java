package com.project.aws.crud.services;

import com.project.aws.crud.dto.EmployeeDTO;
import com.project.aws.crud.exception.BusinessException;
import com.project.aws.crud.model.Employee;
import com.project.aws.crud.repository.EmployeeRepository;
import com.project.aws.crud.service.EmployeeService;
import com.project.aws.crud.utils.ErrorMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    public static final String DOCUMENT = "11111111111";

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void createEmployee_ShouldCreate(){

        Employee employee = Employee.builder().document(DOCUMENT).build();

        when(employeeRepository.findByDocument(employee.getDocument())).thenReturn(Optional.empty());
        when(employeeRepository.save(employee)).thenReturn(employee);

        EmployeeDTO employeeDTO = employeeService.create(employee.toDTO());

        verify(employeeRepository).findByDocument(any());
        verify(employeeRepository).save(any(Employee.class));
        verifyNoMoreInteractions(employeeRepository);

        assertEquals(employee.getDocument(), employeeDTO.getDocument());
    }

    @Test
    public void createEmployee_ShouldNotCreate(){

        Employee employee = Employee.builder().document(DOCUMENT).build();

        when(employeeRepository.findByDocument(employee.getDocument())).thenReturn(Optional.of(employee));

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeService.create(employee.toDTO()));

        verify(employeeRepository).findByDocument(any());
        verifyNoMoreInteractions(employeeRepository);

        assertEquals(ErrorMessages.EMPLOYEE_EXISTENT.getDescription(), exception.getCodeError());
    }

    @Test
    public void findById_ShouldReturnEmployee(){

        Employee employee = Employee.builder().document(DOCUMENT).build();
        when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employee));

        EmployeeDTO employeeDTO = employeeService.findById(anyString());

        verify(employeeRepository).findById(anyString());
        verifyNoMoreInteractions(employeeRepository);

        assertEquals(employee.getDocument(), employeeDTO.getDocument());
    }

    @Test
    public void findById_ShouldReturnNull(){

        when(employeeRepository.findById(anyString())).thenReturn(Optional.empty());

        EmployeeDTO employeeDTO = employeeService.findById(anyString());

        verify(employeeRepository).findById(anyString());
        verifyNoMoreInteractions(employeeRepository);

        assertNull(employeeDTO);
    }

    @Test
    public void findByDocument_ShouldReturnEmployee(){

        Employee employee = Employee.builder().document(DOCUMENT).build();
        when(employeeRepository.findByDocument(anyString())).thenReturn(Optional.of(employee));

        EmployeeDTO employeeDTO = employeeService.findByDocument(anyString());

        verify(employeeRepository).findByDocument(anyString());
        verifyNoMoreInteractions(employeeRepository);

        assertEquals(employee.getDocument(), employeeDTO.getDocument());
    }

    @Test
    public void findByDocument_ShouldReturnNull(){

        when(employeeRepository.findByDocument(anyString())).thenReturn(Optional.empty());

        EmployeeDTO employeeDTO = employeeService.findByDocument(anyString());

        verify(employeeRepository).findByDocument(anyString());
        verifyNoMoreInteractions(employeeRepository);

        assertNull(employeeDTO);
    }

    @Test
    public void findAll_ShouldReturnTwo(){

        Employee employee = Employee.builder().document(DOCUMENT + "1").build();
        Employee employee2 = Employee.builder().document(DOCUMENT + "2").build();
        List<Employee> employees = Arrays.asList(employee, employee2);
        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeDTO> employeeDTO = employeeService.findAll();

        verify(employeeRepository).findAll();
        verifyNoMoreInteractions(employeeRepository);

        assertEquals(employees.size(), employeeDTO.size());
    }

    @Test
    public void findAll_ShouldReturnEmpty(){
        List<Employee> employees = Collections.emptyList();
        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeDTO> employeeDTO = employeeService.findAll();

        verify(employeeRepository).findAll();
        verifyNoMoreInteractions(employeeRepository);

        assertEquals(employees.size(), employeeDTO.size());
    }

    @Test
    public void update_ShouldUpdateEmployee(){

        Employee employee = Employee.builder().firstName("Joao").document(DOCUMENT).build();
        when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employee));

        EmployeeDTO employeeDTOMock = EmployeeDTO.builder().document(DOCUMENT).firstName("Roberto").build();
        when(employeeRepository.save(employee)).thenReturn(employeeDTOMock.toModel());
        EmployeeDTO employeeDTO = employeeService.update(anyString(), employeeDTOMock);

        verify(employeeRepository).findById(anyString());
        verify(employeeRepository).save(employee);
        verifyNoMoreInteractions(employeeRepository);

        assertEquals(employee.getFirstName(), employeeDTO.getFirstName());
    }

    @Test
    public void update_ShouldReturnExceptionWhenNotFoundEmployee(){

        when(employeeRepository.findById(anyString())).thenReturn(Optional.empty());

        Employee employee = Employee.builder().firstName("Joao").document(DOCUMENT).build();
        BusinessException exception = assertThrows(BusinessException.class, () -> employeeService.update(anyString(), employee.toDTO()));

        verify(employeeRepository).findById(anyString());
        verifyNoMoreInteractions(employeeRepository);

        assertEquals(ErrorMessages.EMPLOYEE_NOT_FOUND_BY_ID.getDescription(), exception.getCodeError());
    }

    @Test
    public void update_ShouldReturnExceptionWhenUpdateDocument(){

        Employee employee = Employee.builder().firstName("Joao").document(DOCUMENT).build();
        when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employee));

        EmployeeDTO employeeDTOMock = EmployeeDTO.builder().document(DOCUMENT + "2").build();
        BusinessException exception = assertThrows(BusinessException.class, () -> employeeService.update(anyString(), employeeDTOMock));

        verify(employeeRepository).findById(anyString());
        verifyNoMoreInteractions(employeeRepository);

        assertEquals(ErrorMessages.EMPLOYEE_CANT_CHANGE_DOCUMENT.getDescription(), exception.getCodeError());
    }

    @Test
    public void delete_ShouldDeleteEmployee(){

        Employee employee = Employee.builder().firstName("Joao").document(DOCUMENT).build();

        when(employeeRepository.findById(anyString())).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(employee);

        employeeService.delete(anyString());

        verify(employeeRepository).findById(anyString());
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    public void delete_ShouldReturnExceptionWhenNotFoundEmployee(){

        when(employeeRepository.findById(anyString())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> employeeService.delete(anyString()));

        verify(employeeRepository).findById(anyString());
        verifyNoMoreInteractions(employeeRepository);

        assertEquals(ErrorMessages.EMPLOYEE_NOT_FOUND_BY_ID.getDescription(), exception.getCodeError());
    }
}
