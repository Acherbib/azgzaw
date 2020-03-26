package com.azgzaw.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azgzaw.app.web.rest.TestUtil;

public class LikeOfStatusTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikeOfStatus.class);
        LikeOfStatus likeOfStatus1 = new LikeOfStatus();
        likeOfStatus1.setId("id1");
        LikeOfStatus likeOfStatus2 = new LikeOfStatus();
        likeOfStatus2.setId(likeOfStatus1.getId());
        assertThat(likeOfStatus1).isEqualTo(likeOfStatus2);
        likeOfStatus2.setId("id2");
        assertThat(likeOfStatus1).isNotEqualTo(likeOfStatus2);
        likeOfStatus1.setId(null);
        assertThat(likeOfStatus1).isNotEqualTo(likeOfStatus2);
    }
}
