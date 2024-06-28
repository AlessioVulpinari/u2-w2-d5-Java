package alessiovulpinari.u2_w2_d5_Java.services;

import alessiovulpinari.u2_w2_d5_Java.entities.Employee;
import alessiovulpinari.u2_w2_d5_Java.exceptions.BadRequestException;
import alessiovulpinari.u2_w2_d5_Java.exceptions.NotFoundException;
import alessiovulpinari.u2_w2_d5_Java.payloads.EmployeePayload;
import alessiovulpinari.u2_w2_d5_Java.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> getEmployees(int pageNumber, int pageSize) {
        if (pageSize <= 0) pageSize =10;
        if (pageSize >= 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return employeeRepository.findAll(pageable);
    }

    public Employee saveEmployee(EmployeePayload body) {
        this.employeeRepository.findByEmail(body.email()).ifPresent(employee ->
        {throw new BadRequestException("Esiste già un dipendente con questa email: " + body.email());});

        this.employeeRepository.findByUsername(body.username()).ifPresent(employee -> {
            throw new BadRequestException("Esiste già un impiegato con questo username: " + body.username());});

        Employee newEmployee = new Employee(body.username(), body.fistName(), body.LastName(), body.email());
        return employeeRepository.save(newEmployee);
    }

    public Employee findById(UUID employeeId) {
        return this.employeeRepository.findById(employeeId).orElseThrow(() -> new NotFoundException(employeeId));
    }

    public Employee findByIdAndUpdate(UUID employeeId, EmployeePayload body)
    {
        Employee found = findById(employeeId);
        found.setEmail(body.email());
        found.setUsername(body.username());
        found.setLastName(body.LastName());
        found.setFirstName(body.fistName());
        found.setAvatarUrl("https://ui-avatars.com/api/?name=" + body.fistName() + "+" + body.LastName());
        return employeeRepository.save(found);
    }

    public void findByIdAndDelete(UUID employeeId) {
        Employee found = findById(employeeId);
        this.employeeRepository.delete(found);
    }
}
