package apap.tugas.sipes.service;
import java.util.List;
import apap.tugas.sipes.model.PesawatModel;

public interface PesawatService {
    List<PesawatModel>getPesawatList();
    
    void addPesawat(PesawatModel pesawat);
    void deletePesawat(PesawatModel pesawat);

    PesawatModel getPesawatById(Long id);
    PesawatModel updatePesawat(PesawatModel pesawat);

    String nomorSeri(PesawatModel pesawat);
}
