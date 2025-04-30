package com.kirusanth.ems.repository;

import com.kirusanth.ems.model.Employee;
import com.kirusanth.ems.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByManagerId(Long managerId);
    Optional<Employee> findByEmail(String email);
    List<Employee> findByLastNameContainingIgnoreCase(String lastName);
    List<Employee> findByActive(boolean active);
    List<Employee> findByRole(Role role);
}
