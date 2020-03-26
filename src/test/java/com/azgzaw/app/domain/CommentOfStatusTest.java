package com.azgzaw.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azgzaw.app.web.rest.TestUtil;

public class CommentOfStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentOfStatus.class);
        CommentOfStatus commentOfStatus1 = new CommentOfStatus();
        commentOfStatus1.setId("id1");
        CommentOfStatus commentOfStatus2 = new CommentOfStatus();
        commentOfStatus2.setId(commentOfStatus1.getId());
        assertThat(commentOfStatus1).isEqualTo(commentOfStatus2);
        commentOfStatus2.setId("id2");
        assertThat(commentOfStatus1).isNotEqualTo(commentOfStatus2);
        commentOfStatus1.setId(null);
        assertThat(commentOfStatus1).isNotEqualTo(commentOfStatus2);
    }
}
