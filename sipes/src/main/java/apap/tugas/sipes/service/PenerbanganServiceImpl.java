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

}
