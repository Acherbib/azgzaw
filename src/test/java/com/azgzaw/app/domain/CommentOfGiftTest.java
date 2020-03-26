package com.azgzaw.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azgzaw.app.web.rest.TestUtil;

public class CommentOfGiftTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CommentOfGift.class);
        CommentOfGift commentOfGift1 = new CommentOfGift();
        commentOfGift1.setId("id1");
        CommentOfGift commentOfGift2 = new CommentOfGift();
        commentOfGift2.setId(commentOfGift1.getId());
        assertThat(commentOfGift1).isEqualTo(commentOfGift2);
        commentOfGift2.setId("id2");
        assertThat(commentOfGift1).isNotEqualTo(commentOfGift2);
        commentOfGift1.setId(null);
        assertThat(commentOfGift1).isNotEqualTo(commentOfGift2);
    }
}
