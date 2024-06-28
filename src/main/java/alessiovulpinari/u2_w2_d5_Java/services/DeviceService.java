package alessiovulpinari.u2_w2_d5_Java.services;

import alessiovulpinari.u2_w2_d5_Java.entities.Device;
import alessiovulpinari.u2_w2_d5_Java.enums.DeviceStatus;
import alessiovulpinari.u2_w2_d5_Java.enums.DeviceType;
import alessiovulpinari.u2_w2_d5_Java.exceptions.BadRequestException;
import alessiovulpinari.u2_w2_d5_Java.exceptions.NotFoundException;
import alessiovulpinari.u2_w2_d5_Java.payloads.NewDevicePayload;
import alessiovulpinari.u2_w2_d5_Java.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private EmployeeService employeeService;

    public Page<Device> getDevices(int pageNumber, int pageSize) {
        if (pageSize <= 0) pageSize =10;
        if (pageSize >= 50) pageSize =50;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return deviceRepository.findAll(pageable);
    }

    public Device saveDevice(NewDevicePayload body) {

        Device newDevice = new Device(convertStringToDeviceStatus(body.status()), convertStringToDeviceType(body.type()));
        return deviceRepository.save(newDevice);
    }

    public Device findById(UUID deviceId) {
        return this.deviceRepository.findById(deviceId).orElseThrow(() -> new NotFoundException(deviceId));
    }

    public Device findByIdAndUpdate(UUID deviceId, Device modifiedDevice) {
        Device found = findById(deviceId);

        found.setStatus(modifiedDevice.getStatus());
        found.setType(modifiedDevice.getType());
        found.setEmployee(employeeService.findById(modifiedDevice.getEmployee().getEmployeeId()));
        return deviceRepository.save(found);
    }

    public void findByIdAndDelete(UUID deviceId) {
        Device found = findById(deviceId);
        this.deviceRepository.delete(found);
    }

    private static DeviceType convertStringToDeviceType(String type) {
        try {
            return DeviceType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Tipo dispositivo inserito non coretto! Inserire SMARTPHONE, " +
                    "TABLET, LAPTOP o COMPUTER.");
        }
    }

    private static DeviceStatus convertStringToDeviceStatus(String status) {
        try {
            return DeviceStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Stato dispositivo inserito non coretto! Inserire AVAILABLE," +
                    "ASSIGNED, UNDER_MAINTENANCE, DISMISSED");
        }
    }
}
