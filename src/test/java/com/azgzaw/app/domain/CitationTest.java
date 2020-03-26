package com.azgzaw.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azgzaw.app.web.rest.TestUtil;

public class CitationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Citation.class);
        Citation citation1 = new Citation();
        citation1.setId("id1");
        Citation citation2 = new Citation();
        citation2.setId(citation1.getId());
        assertThat(citation1).isEqualTo(citation2);
        citation2.setId("id2");
        assertThat(citation1).isNotEqualTo(citation2);
        citation1.setId(null);
        assertThat(citation1).isNotEqualTo(citation2);
    }
}
