package com.swaksha.hospitalservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name="Ehr")

public class Ehr {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer EhrId;

    @ManyToOne
    Patient patient;

    @Column
<<<<<<< HEAD
<<<<<<< HEAD
    private LocalDate creationDate;
=======
    String data;
>>>>>>> parent of 92b7eaa... date fixes
    
=======
    String data;

>>>>>>> 89609882fdadcebc982da29b41d2e01d4c082bc8

}
