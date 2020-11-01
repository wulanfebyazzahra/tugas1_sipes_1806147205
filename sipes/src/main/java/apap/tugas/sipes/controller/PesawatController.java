package apap.tugas.sipes.controller;

import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.model.PesawatModel;
import apap.tugas.sipes.model.TeknisiModel;
import apap.tugas.sipes.model.TipeModel;

import apap.tugas.sipes.service.PenerbanganService;
import apap.tugas.sipes.service.PesawatService;
import apap.tugas.sipes.service.TeknisiService;
import apap.tugas.sipes.service.TipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.*;

@Controller
public class PesawatController {

    @Autowired
    private PesawatService pesawatService;

    @Autowired
    private TeknisiService teknisiService;

    @Autowired
    private TipeService tipeService;

    @Autowired
    private PenerbanganService penerbanganService;

    @GetMapping("/")
    private String home() {
        return "home";
    }

    @GetMapping("/pesawat")
    private String viewAllPesawat(Model model) {
        // ambil semua pesawat
        List<PesawatModel> pesawat = pesawatService.getPesawatList();

        model.addAttribute("pesawat", pesawat);
        return "viewall-pesawat";
    }

    @GetMapping("/pesawat/{id}")
    public String viewDetailPesawat(
            @PathVariable Long id,
            Model model
    ) {
        // ambil pesawat dengan id
        PesawatModel pesawat = pesawatService.getPesawatById(id);

        // list teknisi di pesawat
        List<TeknisiModel> listTeknisi = pesawat.getListTeknisi();

        // list seluruh penerbangan
        List<PenerbanganModel> listAllPenerbangan = penerbanganService.getListPenerbangan();

        // list penerbangan di pesawat saat ini
        List<PenerbanganModel> listPenerbangan = pesawat.getListPenerbangan();

        // mengambil penerbangan yang belum di assign
        List<PenerbanganModel> pilihan = new ArrayList<>();
        for(PenerbanganModel penerbangan : listAllPenerbangan){
            if(penerbangan.getPesawat() != pesawat){
                pilihan.add(penerbangan);
            }
        }

        model.addAttribute("pesawat", pesawat);
        model.addAttribute("listTeknisi", listTeknisi);
        model.addAttribute("pilihan", pilihan);
        model.addAttribute("listPenerbangan", listPenerbangan);
        return "view-detail-pesawat";
    }

    @PostMapping("/pesawat/{id}/tambah-penerbangan")
    public String addPenerbanganToPesawat(
            @PathVariable Long id,
            @RequestParam(value="idSelected") Long idSelected,
            Model model
    ){
        // ambil pesawat dengan id
        PesawatModel pesawat = pesawatService.getPesawatById(id);

        // list teknisi di pesawat
        List<TeknisiModel> listTeknisi = pesawat.getListTeknisi();

        // list seluruh penerbangan
        List<PenerbanganModel> listAllPenerbangan = penerbanganService.getListPenerbangan();

        // list penerbangan di pesawat saat ini
        List<PenerbanganModel> listPenerbangan = pesawat.getListPenerbangan();

        // mengambil penerbangan yang belum di assign
        List<PenerbanganModel> pilihan = new ArrayList<>();
        for(PenerbanganModel penerbangan : listAllPenerbangan){
            if(penerbangan.getPesawat() != pesawat){
                pilihan.add(penerbangan);
            }
        }

        // mengambil penerbangan berdasarkan id penerbangan yang telah dipilih
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idSelected);

        // menambahkan penerbangan ke pesawat lalu update
        pesawat.getListPenerbangan().add(penerbangan);
        pesawatService.updatePesawat(pesawat);

        // menambahkan pesawat ke penerbangan lalu update
        penerbangan.setPesawat(pesawat);
        penerbanganService.updatePenerbangan(penerbangan);

        // membuat notifikasi, mengambil penerbangan terakhir
        PenerbanganModel temp = listPenerbangan.get(listPenerbangan.size()-1);
        String notifikasi = "Penerbangan dengan nomor "+temp.getNomor_penerbangan()+" berhasil ditambahkan";

        model.addAttribute("pesawat", pesawat);
        model.addAttribute("listTeknisi", listTeknisi);
        model.addAttribute("pilihan", pilihan);
        model.addAttribute("listPenerbangan", listPenerbangan);
        model.addAttribute("notifikasi", notifikasi);
        return "view-detail-pesawat";
    }


    @GetMapping("/pesawat/tambah")
    public String addPesawatForm(
            Model model) {
        // buat pesawat dan teknisi baru
        PesawatModel pesawat = new PesawatModel();
        TeknisiModel teknisi = new TeknisiModel();

        // buat list untuk teknisi baru nantinya
        List<TeknisiModel> listTeknisi = new ArrayList<>();

        // list semua tipe
        List<TipeModel> listTipe = tipeService.getListTipe();

        // list semua teknisi
        List<TeknisiModel> listAllTeknisi = teknisiService.getListTeknisi();

        // masukin teknisi baru ke list
        listTeknisi.add(teknisi);

        // simpen ke db
        pesawat.setListTeknisi(listTeknisi);

        model.addAttribute("pesawat", pesawat);
        model.addAttribute("listTeknisi", listTeknisi);
        model.addAttribute("listTipe", listTipe);
        model.addAttribute("listAllTeknisi", listAllTeknisi);
        return "form-add-pesawat";
    }

    @PostMapping(value = "/pesawat/tambah", params = {"tambah"})
    public String addTeknisi(
            @ModelAttribute PesawatModel pesawat,
            Model model
    ) {
        TeknisiModel teknisi = new TeknisiModel();

        // nambahin teknisi baru
        pesawat.getListTeknisi().add(teknisi);

        // list semua teknisi
        List<TeknisiModel> listAllTeknisi = teknisiService.getListTeknisi();

        // list semua tipe
        List<TipeModel> listTipe = tipeService.getListTipe();

        model.addAttribute("listAllTeknisi", listAllTeknisi);
        model.addAttribute("listTipe", listTipe);
        model.addAttribute("pesawat", pesawat);
        return "form-add-pesawat";
    }

    @PostMapping("/pesawat/tambah")
    public String addPesawatSubmit(
            @ModelAttribute PesawatModel pesawat, Model model
    ) {
        // bikin list untuk teknisi
        List<TeknisiModel> listTeknisi = new ArrayList<TeknisiModel>();

        // masukin teknisi2 pesawat ke list
        for (TeknisiModel teknisi : pesawat.getListTeknisi()) {
            teknisi = teknisiService.getTeknisiById(teknisi.getId());
            listTeknisi.add(teknisi);
        }
        // simpen ke db
        pesawat.setListTeknisi(listTeknisi);

        // simpen pesawat barunya
        pesawatService.addPesawat(pesawat);

        model.addAttribute("pesawat", pesawat);
        return "add-pesawat";
    }

    @GetMapping("/pesawat/ubah/{id}")
    public String updatePesawatForm(
            @PathVariable Long id, Model model
    ) {
        // ambil pesawat dari id
        PesawatModel pesawat = pesawatService.getPesawatById(id);

        model.addAttribute("pesawat", pesawat);
        return "form-update-pesawat";
    }

    @PostMapping("/pesawat/ubah")
    public String updatePesawatSubmit(
            @ModelAttribute PesawatModel pesawat, Model model
    ) {
        // update pesawat di service
        PesawatModel updated = pesawatService.updatePesawat(pesawat);

        model.addAttribute("pesawat", updated);
        return "update-pesawat";
    }

    @GetMapping("/pesawat/hapus/{id}")
    public String deletePesawat(
            @PathVariable Long id,
            Model model
    ) {
        // ambil pesawat dari idnya
        PesawatModel pesawat = pesawatService.getPesawatById(id);

        // kalau punya penerbangan gaboleh diapus
        if (pesawat.getListPenerbangan().size() > 0){
            model.addAttribute("pesawat", pesawat);
            return "cannot-delete";
        }

        // delete pesawat di service
        pesawatService.deletePesawat(pesawat);

        model.addAttribute("pesawat", pesawat);
        return "delete-pesawat";
    }

    @GetMapping("/pesawat/pesawat-tua")
    public String findPesawatTua(Model model){
        // ammbil list pesawat
        List<PesawatModel> pesawat = pesawatService.getPesawatList();

        // bikin list buat nyimpen pesawat
        List<PesawatModel> listPesawat = new ArrayList<PesawatModel>();

        // bikin list buat nyimpen tahun pesawat
        List<Integer> listTahun = new ArrayList<Integer>();

        // masukin pesawat yang lebih dari 10 thn ke list
        for (PesawatModel pwt : pesawat) {
            // ambil tahun sekarang
            int sekarang = LocalDate.now().getYear();

            // ambil tahun pesawat dibuat
            int dibuat = pwt.getTanggal_dibuat().getYear();

            // ngurangin tahun skrg sama tahun pesawat dibuat
            int dif = sekarang - dibuat;

            // kalau lebih dari 10 tahun tambahin ke list pesawat tua
            if (dif > 10){
                listPesawat.add(pwt);
                listTahun.add(dif);
            }
        }
        model.addAttribute("listPesawat", listPesawat);
        model.addAttribute("listTahun", listTahun);
        return "view-pesawat-tua";
    }

    @GetMapping("/pesawat/bonus")
    private String bonusTotalTeknisi(Model model) {
        // ambil list pesawat
        List<PesawatModel> pesawat = pesawatService.getPesawatList();

        // list untuk total teknisi
        List<Integer> total = new ArrayList<Integer>();

        // masukin total teknisi2 ke list
        for (PesawatModel pwt : pesawat) {
            int jumlah = pwt.getListTeknisi().size();
            total.add(jumlah);
        }

        model.addAttribute("pesawat", pesawat);
        model.addAttribute("total", total);
        return "bonus-total-teknisi";
    }

    @GetMapping("/pesawat/filter")
    private String filterPesawat(
            @RequestParam(value= "idPenerbangan", defaultValue = "0") Long idPenerbangan,
            @RequestParam(value= "idTipe", defaultValue = "0") Long idTipe,
            @RequestParam(value= "idTeknisi", defaultValue = "0") Long idTeknisi,
            Model model
    ) {
        // ambil list tipe, teknisi, dan penerbangan
        List<TipeModel> listTipe = tipeService.getListTipe();
        List<TeknisiModel> listTeknisi = teknisiService.getListTeknisi();
        List<PenerbanganModel> listPenerbangan = penerbanganService.getListPenerbangan();

        // tampilin
        model.addAttribute("listTeknisi", listTeknisi);
        model.addAttribute("listTipe", listTipe);
        model.addAttribute("listPenerbangan", listPenerbangan);

        // kondisi semuanya tidak null
        Boolean test = (idPenerbangan != null || idTipe != null || idTeknisi != null);
        if (test == Boolean.TRUE) {
            // ambil list pesawat
            List<PesawatModel> listPesawat = pesawatService.getPesawatList();

            // bikin list buat nyimpen
            List<PesawatModel> simpenPesawat = new ArrayList<PesawatModel>();

            // masukin pesawat ke list
            for (PesawatModel pesawat : listPesawat) {
                simpenPesawat.add(pesawat);
            }

            for (PesawatModel pesawat : simpenPesawat) {
                // filter untuk penerbangan
                if (idPenerbangan != 0) {
                    // cari penerbangan dari idnya
                    PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
                    // ambil list penerbangan
                    List<PenerbanganModel> listPen = pesawat.getListPenerbangan();
                    // menghapus penerbangan yang tidak di-get
                    if (!listPen.contains(penerbangan)) {
                        listPesawat.remove(pesawat);
                    }
                }
                // filter untuk teknisi
                if (idTeknisi != 0) {
                    // cari teknisi dari idnya
                    TeknisiModel teknisi = teknisiService.getTeknisiById(idTeknisi);
                    // ambil list teknisi
                    List<TeknisiModel> listTek = pesawat.getListTeknisi();
                    // menghapus teknisi yang tidak di-get
                    if (!listTek.contains(teknisi)) {
                        listPesawat.remove(pesawat);
                    }
                }
                // filter untuk tipe
                if (idTipe != 0) {
                    // cari tipe dari idnya
                    TipeModel tipe = tipeService.getTipeById(idTipe);
                    // ambil tipe pesawat
                    TipeModel tipePes = pesawat.getTipe();
                    // menghapus tipe yang tidak sama dengan yang di-get
                    if (tipe.getId() != tipePes.getId()) {
                        listPesawat.remove(pesawat);
                    }
                }
            }
            int size = listPesawat.size();
            model.addAttribute("listPesawat", listPesawat);
            model.addAttribute("size", size);
        }
        return "pesawat-filter";
    }
}