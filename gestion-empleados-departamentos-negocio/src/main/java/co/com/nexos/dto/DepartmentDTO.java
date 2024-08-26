package co.com.nexos.dto;

import java.time.LocalDateTime;

public class DepartmentDTO {
	private Long id;
    private String departmentCode;
    private String departmentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public DepartmentDTO(Long id, String departmentCode, String departmentName, LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.departmentCode = departmentCode;
		this.departmentName = departmentName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public DepartmentDTO() {
	}
}
