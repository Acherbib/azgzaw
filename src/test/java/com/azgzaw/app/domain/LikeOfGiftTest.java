package com.azgzaw.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azgzaw.app.web.rest.TestUtil;

public class LikeOfGiftTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikeOfGift.class);
        LikeOfGift likeOfGift1 = new LikeOfGift();
        likeOfGift1.setId("id1");
        LikeOfGift likeOfGift2 = new LikeOfGift();
        likeOfGift2.setId(likeOfGift1.getId());
        assertThat(likeOfGift1).isEqualTo(likeOfGift2);
        likeOfGift2.setId("id2");
        assertThat(likeOfGift1).isNotEqualTo(likeOfGift2);
        likeOfGift1.setId(null);
        assertThat(likeOfGift1).isNotEqualTo(likeOfGift2);
    }
}
