@Entity
@Table(name = "vehicles")
@Getter @Setter @NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fipe_code", nullable = false, unique = true)
    private String fipeCode;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    private String observations;
    // getters, setters, etc.
}