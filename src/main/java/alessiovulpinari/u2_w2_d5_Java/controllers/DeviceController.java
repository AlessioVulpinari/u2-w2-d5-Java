package alessiovulpinari.u2_w2_d5_Java.controllers;

import alessiovulpinari.u2_w2_d5_Java.entities.Device;
import alessiovulpinari.u2_w2_d5_Java.exceptions.BadRequestException;
import alessiovulpinari.u2_w2_d5_Java.payloads.DeviceEmployeePayload;
import alessiovulpinari.u2_w2_d5_Java.payloads.DevicePayloadResponse;
import alessiovulpinari.u2_w2_d5_Java.payloads.NewDevicePayload;
import alessiovulpinari.u2_w2_d5_Java.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public Page<Device> getAllDevices(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size)
    {
        return this.deviceService.getDevices(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DevicePayloadResponse saveDevice(@RequestBody @Validated NewDevicePayload body, BindingResult result) {
        if (result.hasErrors()) {
            throw new BadRequestException(result.getAllErrors());
        }

        return new DevicePayloadResponse(this.deviceService.saveDevice(body).getDeviceId());
    }

    @GetMapping("/{deviceId}")
    public Device findById(@PathVariable UUID deviceId)
    {
        return this.deviceService.findById(deviceId);
    }

    @PutMapping("/{deviceId}")
    public Device findByIdAndUpdate(@PathVariable UUID deviceId, @RequestBody NewDevicePayload body) {
        return this.deviceService.findByIdAndUpdate(deviceId, body);
    }

    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID deviceId) {
        this.deviceService.findByIdAndDelete(deviceId);
    }

    @PatchMapping("/{deviceId}")
    public Device updateEmployeeId(@PathVariable UUID deviceId, @RequestBody DeviceEmployeePayload body) {
        return this.deviceService.findByIdAndUpdateEmployee(deviceId, body);
    }
}
