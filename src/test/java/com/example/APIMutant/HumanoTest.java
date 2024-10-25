package com.example.APIMutant;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import com.example.APIMutant.services.HumanoServiceImpl;

public class HumanoTest {

    @Test
    public void testMutantDna() {
        String[] dnaMutant = {
                "AAAATG",
                "AAGTAG",
                "ATGTAG",
                "AGGTAG",
                "AGCTAG",
                "GGCCTG"
        };
        assertTrue(HumanoServiceImpl.isMutant(dnaMutant), "The DNA should be classified as mutant.");
    }

    @Test
    public void testHumanDna() {
        String[] dnaHuman = {
                "CTAGTA",
                "CTAGCG",
                "TTAGGA",
                "GCTTAC",
                "CGGTGC",
                "ATGCTA"
        };
        assertFalse(HumanoServiceImpl.isMutant(dnaHuman), "The DNA should be classified as human.");
    }
}
