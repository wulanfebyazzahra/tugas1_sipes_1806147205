package apap.tugas.sipes.service;
import java.util.List;
import apap.tugas.sipes.model.PesawatModel;

public interface PesawatService {
    List<PesawatModel>getPesawatList();
    void tambahPesawat(PesawatModel pesawat);
    PesawatModel updatePesawat(PesawatModel pesawat);
    void deletePesawat(PesawatModel pesawat);
    List<String> getListNomorSeri();
    PesawatModel getPesawatById(Long id);
    String nomorSeri(PesawatModel pesawat);
}
