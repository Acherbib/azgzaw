package com.azgzaw.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azgzaw.app.web.rest.TestUtil;

public class LikeOfCitationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikeOfCitation.class);
        LikeOfCitation likeOfCitation1 = new LikeOfCitation();
        likeOfCitation1.setId("id1");
        LikeOfCitation likeOfCitation2 = new LikeOfCitation();
        likeOfCitation2.setId(likeOfCitation1.getId());
        assertThat(likeOfCitation1).isEqualTo(likeOfCitation2);
        likeOfCitation2.setId("id2");
        assertThat(likeOfCitation1).isNotEqualTo(likeOfCitation2);
        likeOfCitation1.setId(null);
        assertThat(likeOfCitation1).isNotEqualTo(likeOfCitation2);
    }
}
