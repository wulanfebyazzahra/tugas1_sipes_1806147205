package apap.tugas.sipes.service;
import java.util.List;
import apap.tugas.sipes.model.TipeModel;

public interface TipeService {
    List<TipeModel> getListTipe();
    TipeModel getTipeById(Long id);
}
