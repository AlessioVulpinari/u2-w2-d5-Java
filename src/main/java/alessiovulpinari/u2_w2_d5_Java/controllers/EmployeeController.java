package alessiovulpinari.u2_w2_d5_Java.controllers;

import alessiovulpinari.u2_w2_d5_Java.entities.Employee;
import alessiovulpinari.u2_w2_d5_Java.exceptions.BadRequestException;
import alessiovulpinari.u2_w2_d5_Java.payloads.EmployeePayload;
import alessiovulpinari.u2_w2_d5_Java.payloads.EmployeePayloadResponse;
import alessiovulpinari.u2_w2_d5_Java.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Page<Employee> getAllEmployees(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size)
    {
        return this.employeeService.getEmployees(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeePayloadResponse saveEmployee(@RequestBody @Validated EmployeePayload body, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }

        return new EmployeePayloadResponse(this.employeeService.saveEmployee(body).getEmployeeId());
    }

    @GetMapping("/{employeeId}")
    public Employee findById(@PathVariable UUID employeeId)
    {
        return this.employeeService.findById(employeeId);
    }

    @PutMapping("/{employeeId}")
    public Employee findByIdAndUpdate(@PathVariable UUID employeeId, @RequestBody EmployeePayload body) {
        return this.employeeService.findByIdAndUpdate(employeeId, body);
    }

    @DeleteMapping("/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID employeeId) {
        this.employeeService.findByIdAndDelete(employeeId);
    }

}
