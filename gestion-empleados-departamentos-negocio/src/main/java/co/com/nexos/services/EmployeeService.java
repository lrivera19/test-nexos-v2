package co.com.nexos.services;

import java.util.List;

import javax.ejb.Local;

import co.com.nexos.dto.EmployeeDTO;

@Local
public interface EmployeeService {
	void save(EmployeeDTO employeeDTO);
    EmployeeDTO findById(Long id);
    void update(EmployeeDTO employeeDTO);
    void delete(Long id);
    List<EmployeeDTO> findAll();
}
