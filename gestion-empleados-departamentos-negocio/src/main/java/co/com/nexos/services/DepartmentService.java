package co.com.nexos.services;

import java.util.List;

import javax.ejb.Local;

import co.com.nexos.dto.DepartmentDTO;

@Local
public interface DepartmentService {
	void save(DepartmentDTO departmentDTO);
	DepartmentDTO findById(Long id);
    void update(DepartmentDTO departmentDTO);
    void delete(Long id);
    List<DepartmentDTO> findAll();
}
