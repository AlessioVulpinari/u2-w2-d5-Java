package alessiovulpinari.u2_w2_d5_Java.services;

import alessiovulpinari.u2_w2_d5_Java.entities.Device;
import alessiovulpinari.u2_w2_d5_Java.entities.Employee;
import alessiovulpinari.u2_w2_d5_Java.exceptions.NotFoundException;
import alessiovulpinari.u2_w2_d5_Java.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findById(UUID employeeId) {
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }
}
