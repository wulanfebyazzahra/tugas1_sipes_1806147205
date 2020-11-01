package apap.tugas.sipes.service;
import java.util.List;
import apap.tugas.sipes.model.TeknisiModel;

public interface TeknisiService {
    List<TeknisiModel> getListTeknisi();
    TeknisiModel getTeknisiById(Long id);
}
