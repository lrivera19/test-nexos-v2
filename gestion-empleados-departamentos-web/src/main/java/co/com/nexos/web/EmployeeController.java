package co.com.nexos.web;

import javax.inject.Named;

import co.com.nexos.dto.DepartmentDTO;
import co.com.nexos.dto.EmployeeDTO;
import co.com.nexos.services.DepartmentService;
import co.com.nexos.services.EmployeeService;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.annotation.PostConstruct;

@Named
@ViewScoped
public class EmployeeController implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private EmployeeService employeeService;
    
    @Inject
    private DepartmentService departmentService;

    private EmployeeDTO employee;
    private List<EmployeeDTO> employees;
    private List<DepartmentDTO> departments;

    @PostConstruct
    public void init() {
        employee = new EmployeeDTO();
        employees = employeeService.findAll();
        departments = departmentService.findAll();
    }

    public void createOrUpdate() {
        try {
            if (employee.getId() == null) {
                employeeService.save(employee);
                addMessage("Éxito", "Empleado creado correctamente.");
            } else {
                employeeService.update(employee);
                addMessage("Éxito", "Empleado actualizado correctamente.");
            }
            employees = employeeService.findAll();
            resetEmployee();
        } catch (Exception e) {
            addErrorMessage("Error", "Ocurrió un error al guardar el empleado: " + e.getMessage());
        }
    }

    public void delete(EmployeeDTO emp) {
        try {
            employeeService.delete(emp.getId());
            addMessage("Éxito", "Empleado eliminado correctamente.");
            employees = employeeService.findAll();
            resetEmployee();
        } catch (Exception e) {
            addErrorMessage("Error", "Ocurrió un error al eliminar el empleado: " + e.getMessage());
        }
    }

    private void resetEmployee() {
        this.employee = new EmployeeDTO();
    }

    private void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void addErrorMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public EmployeeDTO getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDTO employee) {
        this.employee = employee;
    }

    public List<EmployeeDTO> getEmployees() {
        return employees;
    }

    public List<DepartmentDTO> getDepartments() {
        return departments;
    }
}
