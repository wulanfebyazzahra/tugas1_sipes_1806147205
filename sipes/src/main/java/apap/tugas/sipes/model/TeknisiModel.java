package apap.tugas.sipes.model;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="teknisi")
public class TeknisiModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=255)
    @Column(name="nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name = "nomor_telepon", nullable = false)
    private Long nomor_telepon;

    // Hubungan dengan Pesawat
    @ManyToMany(mappedBy = "listTeknisi")
    private List<PesawatModel> listPesawat;

    // Setter & Getter
    public Long getNomor_telepon() {
        return this.nomor_telepon;
    }

    public void setNomor_telepon(Long nomor_telepon) {
        this.nomor_telepon = nomor_telepon;
    }

    public String getNama() {
        return this.nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
}
