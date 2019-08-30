package com.elephantcarpaccio.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class StateTaxTest {

    private String stateTaxJson;

    @Before
    public void setUp() {
        if (stateTaxJson == null) {
            stateTaxJson = "[{\"state\":\"Alabama\",\"tax\":\"4\"},{\"state\":\"Alaska\",\"tax\":\"0\"},{\"state\":\"Arizona\",\"tax\":\"5.60\"},{\"state\":\"Arkansas\",\"tax\":\"6.50\"},{\"state\":\"California\",\"tax\":\"7.25\"},{\"state\":\"Colorado\",\"tax\":\"2.90\"},{\"state\":\"Connecticut\",\"tax\":\"6.35\"},{\"state\":\"Delaware\",\"tax\":\"0\"},{\"state\":\"District of Columbia\",\"tax\":\"5.75\"},{\"state\":\"Florida\",\"tax\":\"6\"},{\"state\":\"Georgia\",\"tax\":\"4\"},{\"state\":\"Guam\",\"tax\":\"4\"},{\"state\":\"Hawaii\",\"tax\":\"4.17\"},{\"state\":\"Idaho\",\"tax\":\"6\"},{\"state\":\"Illinois\",\"tax\":\"6.25\"},{\"state\":\"Indiana\",\"tax\":\"7\"},{\"state\":\"lowa\",\"tax\":\"6\"},{\"state\":\"Kansas\",\"tax\":\"6.50\"},{\"state\":\"Kentucky\",\"tax\":\"6\"},{\"state\":\"Louisiana\",\"tax\":\"4.45\"},{\"state\":\"Maine\",\"tax\":\"5.50\"},{\"state\":\"Maryland\",\"tax\":\"6\"},{\"state\":\"Massachusetts\",\"tax\":\"6.25\"},{\"state\":\"Michigan\",\"tax\":\"6\"},{\"state\":\"Minnesota\",\"tax\":\"6.88\"},{\"state\":\"Mississippi\",\"tax\":\"7\"},{\"state\":\"Missouri\",\"tax\":\"4.23\"},{\"state\":\"Montana\",\"tax\":\"0\"},{\"state\":\"Nebraska\",\"tax\":\"5.50\"},{\"state\":\"Nevada\",\"tax\":\"6.85\"},{\"state\":\"New Hampshire\",\"tax\":\"0\"},{\"state\":\"New Jersey\",\"tax\":\"6.63\"},{\"state\":\"New Mexico\",\"tax\":\"5.13\"},{\"state\":\"New York\",\"tax\":\"4\"},{\"state\":\"North Carolina\",\"tax\":\"4.75\"},{\"state\":\"North Dakota\",\"tax\":\"5\"},{\"state\":\"Ohio[43]\",\"tax\":\"5.75\"},{\"state\":\"Oklahoma\",\"tax\":\"4.50\"},{\"state\":\"Oregon\",\"tax\":\"0\"},{\"state\":\"Pennsylvania\",\"tax\":\"6\"},{\"state\":\"Puerto Rico\",\"tax\":\"10.50\"},{\"state\":\"Rhode Island\",\"tax\":\"7\"},{\"state\":\"South Carolina\",\"tax\":\"6\"},{\"state\":\"South Dakota\",\"tax\":\"4\"},{\"state\":\"Tennessee\",\"tax\":\"7\"},{\"state\":\"Texas\",\"tax\":\"6.25\"},{\"state\":\"Utah\",\"tax\":\"5.95\"},{\"state\":\"Vermont\",\"tax\":\"6\"},{\"state\":\"Virginia\",\"tax\":\"5.30\"},{\"state\":\"Washington\",\"tax\":\"6.50\"},{\"state\":\"West Virginia\",\"tax\":\"6\"},{\"state\":\"Wisconsin\",\"tax\":\"5\"},{\"state\":\"Wyoming\",\"tax\":\"4\"}]";
        }
    }

    @After
    public void tearDown() {
        stateTaxJson = null;
    }

    @Test
    public void testCreateAnswer() throws Throwable {
        List<StateTax> stateTaxList = StateTax.createStateTaxList(stateTaxJson);
        assertNotNull(stateTaxList);
        assertEquals(53, stateTaxList.size());

        StateTax parent = stateTaxList.get(0);
        assertEquals("Alabama", parent.getState());
        assertEquals(4, parent.getTaxRate(), 0);

    }
}