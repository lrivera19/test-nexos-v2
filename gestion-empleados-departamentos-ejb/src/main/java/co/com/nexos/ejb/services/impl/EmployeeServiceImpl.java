package co.com.nexos.ejb.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import co.com.nexos.dto.EmployeeDTO;
import co.com.nexos.entites.Department;
import co.com.nexos.entites.Employee;
import co.com.nexos.exceptions.CustomException;
import co.com.nexos.services.EmployeeService;

@Stateless
public class EmployeeServiceImpl implements EmployeeService {

    @PersistenceContext(unitName = "testPU")
    private EntityManager entityManager;

    @Override
    public void save(EmployeeDTO employeeDTO) {
        try {
            Employee employee = convertToEntity(employeeDTO);
            entityManager.persist(employee);
        } catch (Exception e) {
            throw new CustomException("Error al guardar el empleado: " + e.getMessage());
        }
    }

    @Override
    public EmployeeDTO findById(Long id) {
        try {
            Employee employee = entityManager.find(Employee.class, id);
            if (employee != null) {
                return convertToDTO(employee);
            } else {
                throw new CustomException("Empleado con ID " + id + " no encontrado.");
            }
        } catch (NoResultException e) {
            throw new CustomException("Empleado con ID " + id + " no encontrado.");
        } catch (Exception e) {
            throw new CustomException("Error al buscar el departamento: " + e.getMessage());
        }
    }

    @Override
    public void update(EmployeeDTO employeeDTO) {
        try {
            Employee employee = convertToEntity(employeeDTO);
            entityManager.merge(employee);
        } catch (Exception e) {
            throw new CustomException("Error al actualizar el empleado: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Employee employee = entityManager.find(Employee.class, id);
            if (employee == null) {
                throw new CustomException("Empleado con ID: " + id + " no encontrado.");
            }
            entityManager.remove(employee);
        } catch (Exception e) {
            throw new CustomException("Error al eliminar el empleado: " + e.getMessage());
        }
    }

    @Override
    public List<EmployeeDTO> findAll() {
        try {
            List<Employee> employees = entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
            return employees.stream()
                            .map(this::convertToDTO)
                            .collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException("Error al obtener la lista de empleados: " + e.getMessage());
        }
    }

    private Employee convertToEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setId(dto.getId());
        employee.setDocumentType(dto.getDocumentType());
        employee.setDocumentNumber(dto.getDocumentNumber());
        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());

        if (dto.getDepartmentId() != null) {
            Department department = entityManager.find(Department.class, dto.getDepartmentId());
            employee.setDepartment(department);
        } 

        employee.setCity(dto.getCity());
        employee.setAddress(dto.getAddress());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setCreatedAt(dto.getCreatedAt());
        employee.setUpdatedAt(dto.getUpdatedAt());
        return employee;
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setDocumentType(employee.getDocumentType());
        dto.setDocumentNumber(employee.getDocumentNumber());
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDepartmentId(employee.getDepartment().getId());
        dto.setCity(employee.getCity());
        dto.setAddress(employee.getAddress());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setCreatedAt(employee.getCreatedAt());
        dto.setUpdatedAt(employee.getUpdatedAt());
        return dto;
    }
}