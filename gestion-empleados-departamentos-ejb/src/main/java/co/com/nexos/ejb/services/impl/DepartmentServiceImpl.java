package co.com.nexos.ejb.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import co.com.nexos.dto.DepartmentDTO;
import co.com.nexos.entites.Department;
import co.com.nexos.exceptions.CustomException;
import co.com.nexos.services.DepartmentService;

@Stateless
public class DepartmentServiceImpl implements DepartmentService {

    @PersistenceContext(unitName = "testPU")
    private EntityManager entityManager;

    @Override
    public void save(DepartmentDTO departmentDTO) {
        try {
            Department department = converToEntity(departmentDTO);
            entityManager.persist(department);
        } catch (Exception e) {
            throw new CustomException("Error al guardar el departamento: " + e.getMessage());
        }
    }

    @Override
    public DepartmentDTO findById(Long id) {
        try {
            Department department = entityManager.find(Department.class, id);
            if (department != null) {
                return convertToDTO(department);
            } else {
                throw new CustomException("Departamento con ID " + id + " no encontrado.");
            }
        } catch (NoResultException e) {
            throw new CustomException("Departamento con ID " + id + " no encontrado.");
        } catch (Exception e) {
            throw new CustomException("Error al buscar el departamento: " + e.getMessage());
        }
    }

    @Override
    public void update(DepartmentDTO departmentDTO) {
        try {
            Department department = converToEntity(departmentDTO);
            entityManager.merge(department);
        } catch (Exception e) {
            throw new CustomException("Error al actualizar el departamento: " + e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Department department = entityManager.find(Department.class, id);
            if (department != null) {
                entityManager.remove(department);
            } else {
                throw new CustomException("Departamento con ID " + id + " no encontrado.");
            }
        } catch (Exception e) {
            throw new CustomException("Error al eliminar el departamento: " + e.getMessage());
        }
    }

    @Override
    public List<DepartmentDTO> findAll() {
        try {
            List<Department> departments = entityManager.createQuery("SELECT d FROM Department d", Department.class).getResultList();
            return departments.stream().map(this::convertToDTO).collect(Collectors.toList());
        } catch (Exception e) {
            throw new CustomException("Error al listar los departamentos: " + e.getMessage());
        }
    }

    // Métodos de conversión
    private DepartmentDTO convertToDTO(Department department) {
        if (department == null) {
            return null;
        }
        return new DepartmentDTO(
                department.getId(),
                department.getDepartmentCode(),
                department.getDepartmentName(),
                department.getCreatedAt(),
                department.getUpdatedAt()
        );
    }

    private Department converToEntity(DepartmentDTO departmentDTO) {
        if (departmentDTO == null) {
            return null;
        }
        Department department = new Department();
        department.setId(departmentDTO.getId());
        department.setDepartmentCode(departmentDTO.getDepartmentCode());
        department.setDepartmentName(departmentDTO.getDepartmentName());
        department.setCreatedAt(departmentDTO.getCreatedAt());
        department.setUpdatedAt(departmentDTO.getUpdatedAt());
        return department;
    }
}