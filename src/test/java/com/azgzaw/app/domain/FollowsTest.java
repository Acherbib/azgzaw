package com.azgzaw.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azgzaw.app.web.rest.TestUtil;

public class FollowsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Follows.class);
        Follows follows1 = new Follows();
        follows1.setId("id1");
        Follows follows2 = new Follows();
        follows2.setId(follows1.getId());
        assertThat(follows1).isEqualTo(follows2);
        follows2.setId("id2");
        assertThat(follows1).isNotEqualTo(follows2);
        follows1.setId(null);
        assertThat(follows1).isNotEqualTo(follows2);
    }
}
