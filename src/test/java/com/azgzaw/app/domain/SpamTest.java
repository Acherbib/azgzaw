package com.azgzaw.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azgzaw.app.web.rest.TestUtil;

public class SpamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Spam.class);
        Spam spam1 = new Spam();
        spam1.setId("id1");
        Spam spam2 = new Spam();
        spam2.setId(spam1.getId());
        assertThat(spam1).isEqualTo(spam2);
        spam2.setId("id2");
        assertThat(spam1).isNotEqualTo(spam2);
        spam1.setId(null);
        assertThat(spam1).isNotEqualTo(spam2);
    }
}
