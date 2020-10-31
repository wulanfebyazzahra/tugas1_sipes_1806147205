package apap.tugas.sipes.service;

import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TipeModel;
import apap.tugas.sipes.repository.PesawatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class PesawatServiceImpl implements PesawatService {

    @Autowired
    PesawatDb pesawatDb;

    @Autowired
    TipeService tipeService;

    @Override
    public List<PesawatModel> getPesawatList(){
        return pesawatDb.findAll();
    }

    @Override
    public void tambahPesawat(PesawatModel pesawat){
        String noSeri = nomorSeri(pesawat);
        pesawat.setNomor_seri(noSeri);
        pesawat.setTanggal_dibuat(LocalDate.now());
        pesawat.setTipe(tipeService.getTipeById(pesawat.getTipe().getId()));
        pesawatDb.save(pesawat);
    }

    @Override
    public PesawatModel getPesawatById(Long id) {
        return pesawatDb.findById(id).get();
    }

    @Override
    public String nomorSeri(PesawatModel pesawat){
        String noSeri = "";

        // Komersial 1, Militer 2
        String jenis = "";
        if (pesawat.getJenis_pesawat().equals("Komersial")){
            jenis="1";
        }else{
            jenis="2";
        }

        String tipe = "";

        // tipe harus diset dahulu baru bisa diambil namanya
        TipeModel tp = tipeService.getTipeById(pesawat.getTipe().getId());
        pesawat.setTipe(tp);

        if(pesawat.getTipe().getNama().equals("ATR")){
            tipe="AT";
        }
        else if(pesawat.getTipe().getNama().equals("Airbus")){
            tipe="AB";
        }
        else if(pesawat.getTipe().getNama().equals("Bombardier")) {
            tipe="BB";
        }
        else if(pesawat.getTipe().getNama().equals("BOEING")){
            tipe="BO";
        }

        Integer tahun = pesawat.getTanggal_dibuat().getYear();

        // reverse tahun dengan string builder
        String thndibalik = new StringBuilder(Integer.toString(tahun)).reverse().toString();

        // tahun+8
        String thntambah = Integer.toString(tahun+8);

        // mengambil 2 string caps random
        String random="";
        Random r = new Random();
        for(int i=0; i<2; i++){
            char c = (char) ('A'+ r.nextInt(26));
            random+=c;
        }
        noSeri = jenis + tipe + thndibalik + thntambah + random;
        return noSeri;
    }
    
    @Override
	public PesawatModel updatePesawat(PesawatModel pesawat) {
        String noSeri = nomorSeri(pesawat);

        PesawatModel p = pesawatDb.findById(pesawat.getId()).get();
        p.setTempat_dibuat(pesawat.getTempat_dibuat());
        p.setJenis_pesawat(pesawat.getJenis_pesawat());
        p.setNomor_seri(noSeri);
        p.setMaskapai(pesawat.getMaskapai());
        p.setTanggal_dibuat(pesawat.getTanggal_dibuat());
        p.setTipe(tipeService.getTipeById(pesawat.getTipe().getId()));
        pesawatDb.save(p);
        return p;
    }

    @Override
    public void deletePesawat(PesawatModel pesawat) {
        pesawatDb.delete(pesawat);
    }
}
