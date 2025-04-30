package com.kirusanth.ems.service;

import com.kirusanth.ems.dto.EmployeeDTO;
import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();
    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long id);
    List<EmployeeDTO> getEmployeesByDepartment(Long departmentId);
    List<EmployeeDTO> getEmployeesByManager(Long managerId);
    EmployeeDTO deactivateEmployee(Long id);
    EmployeeDTO activateEmployee(Long id);
}
