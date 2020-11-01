package apap.tugas.sipes.service;
import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.repository.PenerbanganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PenerbanganServiceImpl implements PenerbanganService {

    @Autowired
    PenerbanganDb penerbanganDb;

    @Override
    public List<PenerbanganModel> getListPenerbangan() {
        return penerbanganDb.findAll();
    }

    @Override
    public PenerbanganModel getPenerbanganById(Long id) {
        return penerbanganDb.findById(id).get();
    }

    @Override
    public void addPenerbangan(PenerbanganModel penerbangan){
        penerbanganDb.save(penerbangan);
    }

    @Override
    public PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan) {
        // ambil penerbangan yang di db
        PenerbanganModel p = penerbanganDb.findById(penerbangan.getId()).get();
        // set data baru
        p.setKode_bandara_tujuan(penerbangan.getKode_bandara_tujuan());
        p.setWaktu_berangkat(penerbangan.getWaktu_berangkat());
        p.setNomor_penerbangan(penerbangan.getNomor_penerbangan());
        p.setKode_bandara_asal(penerbangan.getKode_bandara_asal());
        penerbanganDb.save(p);
        return p;
    }
    @Override
    public void deletePenerbangan(PenerbanganModel penerbangan) {
        penerbanganDb.delete(penerbangan);
    }
}
