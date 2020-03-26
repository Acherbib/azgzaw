package com.azgzaw.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.azgzaw.app.web.rest.TestUtil;

public class AuctionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auction.class);
        Auction auction1 = new Auction();
        auction1.setId("id1");
        Auction auction2 = new Auction();
        auction2.setId(auction1.getId());
        assertThat(auction1).isEqualTo(auction2);
        auction2.setId("id2");
        assertThat(auction1).isNotEqualTo(auction2);
        auction1.setId(null);
        assertThat(auction1).isNotEqualTo(auction2);
    }
}
