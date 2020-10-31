package apap.tugas.sipes.service;
import java.util.List;
import apap.tugas.sipes.model.PenerbanganModel;

public interface PenerbanganService {
    List<PenerbanganModel> getListPenerbangan();

    void addPenerbangan(PenerbanganModel penerbangan);
    void deletePenerbangan(PenerbanganModel penerbangan);

    PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan);
    PenerbanganModel getPenerbanganById(Long id);

}
