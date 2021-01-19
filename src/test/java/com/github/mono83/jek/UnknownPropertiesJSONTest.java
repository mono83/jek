package com.github.mono83.jek;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.mono83.jek.dto.general.StatusDTO;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UnknownPropertiesJSONTest {
    @Test
    public void testUnknownProperties() throws JsonProcessingException {
        StatusDTO dto = Mapping.mapper.readValue(
                "{\"uptimeSeconds\":\"123\",\"releaseName\":\"Lemon (build 51852e)\", \"unknown\":10}",
                StatusDTO.class
        );

        Assert.assertEquals(123, dto.getUptimeSeconds());
        Assert.assertEquals("Lemon (build 51852e)", dto.getReleaseName());
    }
}
