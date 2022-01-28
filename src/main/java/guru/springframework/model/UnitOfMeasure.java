package guru.springframework.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
public class UnitOfMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String description;

}
