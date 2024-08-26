package co.com.nexos.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import co.com.nexos.dto.DepartmentDTO;
import co.com.nexos.services.DepartmentService;

@Named
@javax.faces.view.ViewScoped
public class DepartmentController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private DepartmentService departmentService;

    private DepartmentDTO department;
    private List<DepartmentDTO> departments;

    @PostConstruct
    public void init() {
        department = new DepartmentDTO();
        departments = departmentService.findAll();
    }

    public void createOrUpdate() {
        try {
            if (department.getDepartmentCode() == null || department.getDepartmentName() == null) {
                addErrorMessage("Error", "El código o el nombre del departamento no pueden estar vacíos.");
                return;
            }

            if (department.getId() == null) {
                departmentService.save(department);
                addMessage("Éxito", "Departamento creado correctamente.");
            } else {
                departmentService.update(department);
                addMessage("Éxito", "Departamento actualizado correctamente.");
            }
            departments = departmentService.findAll();
            resetDepartment();
        } catch (Exception e) {
            addErrorMessage("Error", "Ocurrió un error al guardar el departamento: " + e.getMessage());
        }
    }

    public void delete(DepartmentDTO dept) {
        try {
            departmentService.delete(dept.getId());
            addMessage("Éxito", "Departamento eliminado correctamente.");
            departments = departmentService.findAll();
            resetDepartment();
        } catch (Exception e) {
            addErrorMessage("Error", "Ocurrió un error al eliminar el departamento: " + e.getMessage());
        }
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public List<DepartmentDTO> getDepartments() {
        return departments;
    }

    private void resetDepartment() {
        this.department = new DepartmentDTO();
    }

    private void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private void addErrorMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}