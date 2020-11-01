package apap.tugas.sipes.controller;
import apap.tugas.sipes.model.PenerbanganModel;
import apap.tugas.sipes.service.PenerbanganService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class PenerbanganController{

    @Autowired
    private PenerbanganService penerbanganService;

    @GetMapping("/penerbangan")
    private String viewAllPenerbangan(Model model){
        // ambil semua penerbangan
        List<PenerbanganModel> listPenerbangan = penerbanganService.getListPenerbangan();
        model.addAttribute("listPenerbangan", listPenerbangan);
        return "viewall-penerbangan";
    }

    @GetMapping("/penerbangan/{id}")
    public String viewDetailPenerbangan(
        @PathVariable Long id,
        Model model
    ){
        // ambil penerbangan sesuai id
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);

        // periksa ada pesawat atau tidak
        boolean hasPesawat = penerbangan.getPesawat() !=null;
        model.addAttribute("penerbangan", penerbangan);
        model.addAttribute("hasPesawat", hasPesawat);
        return "view-detail-penerbangan";
    }

    @GetMapping("/penerbangan/tambah")
    public String addPenerbanganForm(Model model){
        // buat penerbangan baru
        PenerbanganModel penerbangan = new PenerbanganModel();
        model.addAttribute("penerbangan", penerbangan);
        return "form-add-penerbangan";
    }

    @PostMapping("/penerbangan/tambah")
    public String addPenerbanganSubmit(
        @ModelAttribute PenerbanganModel penerbangan,
        Model model
    ){
        // simpan penerbangan baru
        penerbanganService.addPenerbangan(penerbangan);
        model.addAttribute("penerbangan", penerbangan);
        return "add-penerbangan";
    }

    @GetMapping("/penerbangan/ubah/{id}")
    public String updatePenerbanganForm(
        @PathVariable Long id, Model model
    ){
        // ambil penerbangan dari idnya
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
        model.addAttribute("penerbangan", penerbangan);
        return "form-update-penerbangan";
    }

    @PostMapping("/penerbangan/ubah")
    public String updatePenerbanganSubmit(
        @ModelAttribute PenerbanganModel penerbangan,
        Model model
    ){
        // update di service
        PenerbanganModel updated = penerbanganService.updatePenerbangan(penerbangan);
        model.addAttribute("penerbangan", updated);
        return "update-penerbangan";
    }

    @GetMapping("/penerbangan/hapus/{id}")
    public String deletePenerbangan(
        @PathVariable Long id,
        Model model
    ){
        // hapus di service
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
        penerbanganService.deletePenerbangan(penerbangan);
        model.addAttribute("penerbangan", penerbangan);
        return "delete-penerbangan";
    }
}