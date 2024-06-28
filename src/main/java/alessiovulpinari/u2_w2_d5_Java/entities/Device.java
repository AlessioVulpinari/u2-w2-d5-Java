package alessiovulpinari.u2_w2_d5_Java.entities;

import alessiovulpinari.u2_w2_d5_Java.enums.DeviceStatus;
import alessiovulpinari.u2_w2_d5_Java.enums.DeviceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "dispositivi")
public class Device {

    @Id
    @GeneratedValue
    @Column(name = "id_dispositivo", nullable = false)
    private UUID deviceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato", nullable = false)
    private DeviceStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipologia", nullable = false)
    private DeviceType type;

    @ManyToOne
    @JoinColumn(name = "id_dipendente")
    private Employee employee;

    public Device(DeviceStatus status, DeviceType type, Employee employee) {
        this.status = status;
        this.type = type;
        this.employee = employee;
    }
}
