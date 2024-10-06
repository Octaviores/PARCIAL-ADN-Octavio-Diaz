package services;


import org.springframework.stereotype.Service;
import respositories.HumanoRepository;

import java.util.List;
@Service
public interface HumanoService{
    boolean isMutant(String[] DNA);
}
