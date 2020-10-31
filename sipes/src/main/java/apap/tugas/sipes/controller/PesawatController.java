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
public class PesawatController{

    @Autowired
    private PesawatService pesawatService;

    @Autowired
    private TeknisiService teknisiService;

    @Autowired
    private TipeService tipeService;

    @Autowired
    private PenerbanganService penerbanganService;

    @GetMapping("/")
    private String home(){ return "home";
    }

    @GetMapping("/pesawat")
    private String viewAllPesawat(Model model){
        // ambil semua pesawat
        List<PesawatModel> pesawat = pesawatService.getPesawatList();

        model.addAttribute("pesawat", pesawat);
        return "viewall-pesawat";
    }

    @GetMapping("/pesawat/{id}")
    public String viewDetailPesawat(
            @PathVariable Long id,
            Model model
    ){
        PesawatModel pesawat = pesawatService.getPesawatById(id);

        // list teknisi di pesawat
        List<TeknisiModel> listTeknisi = pesawat.getListTeknisi();
        model.addAttribute("pesawat", pesawat);
        model.addAttribute("listTeknisi", listTeknisi);
        return "view-detail-pesawat";
    }

    @GetMapping("/pesawat/tambah")
    public String addPesawatForm(
            Model model){
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


    @PostMapping(value="/pesawat/tambah", params={"tambah"})
    public String addTeknisi(
        @ModelAttribute PesawatModel pesawat,
        Model model
    ){
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
    ){
        // bikin list untuk teknisi
        List<TeknisiModel> listTeknisi = new ArrayList<TeknisiModel>();
        // masukin teknisi2 pesawat ke list
        for(TeknisiModel teknisi: pesawat.getListTeknisi()){
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
    ){
        // ambil pesawat dari id
        PesawatModel pesawat = pesawatService.getPesawatById(id);
        model.addAttribute("pesawat", pesawat);
        return "form-update-pesawat";
    }

    @PostMapping("/pesawat/ubah")
    public String updatePesawatSubmit(
            @ModelAttribute PesawatModel pesawat, Model model
    ){
        // update pesawat di service
        PesawatModel updated = pesawatService.updatePesawat(pesawat);
        model.addAttribute("pesawat", updated);
        return "update-pesawat";
    }

    @GetMapping("/pesawat/hapus/{id}")
    public String deletePesawat(
        @PathVariable Long id,
        Model model
    ){
        // ambil pesawat dari idnya
        PesawatModel pesawat = pesawatService.getPesawatById(id);
        // delete pesawat di service
        pesawatService.deletePesawat(pesawat);
        model.addAttribute("pesawat", pesawat);
        return "delete-pesawat";
    }