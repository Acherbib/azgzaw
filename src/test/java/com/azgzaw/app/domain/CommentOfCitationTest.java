package com.azgzaw.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azgzaw.app.web.rest.TestUtil;

public class CommentOfCitationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentOfCitation.class);
        CommentOfCitation commentOfCitation1 = new CommentOfCitation();
        commentOfCitation1.setId("id1");
        CommentOfCitation commentOfCitation2 = new CommentOfCitation();
        commentOfCitation2.setId(commentOfCitation1.getId());
        assertThat(commentOfCitation1).isEqualTo(commentOfCitation2);
        commentOfCitation2.setId("id2");
        assertThat(commentOfCitation1).isNotEqualTo(commentOfCitation2);
        commentOfCitation1.setId(null);
        assertThat(commentOfCitation1).isNotEqualTo(commentOfCitation2);
    }
}
