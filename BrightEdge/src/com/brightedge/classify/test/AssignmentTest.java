package com.brightedge.classify.test;

/**
 * Created by Vignesh on 5/23/17.
 *
 * A simple Test which tests for different inputs
 * @author Vignesh
 */

import com.brightedge.classify.main.Assignment;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Arrays;
import java.util.List;


@RunWith(Parameterized.class)
public class AssignmentTest {

    private final String url;
    private final List<String> expectedResult;
    private Assignment appTest;

    private static final String[] answer1 = {"behind nsa leaks", "man behind", "security nine state", "politics edward snowden", "safeguard", "hong kong"};
    private static final String[] answer2 = {"vignesh ramesh", "github twitter linkedin", "blog email new", "student new york", "graduate student", "vignesh graduate"
    };

    public AssignmentTest(String url, List<String> expectedResult) {
        this.url = url;
        this.expectedResult = expectedResult;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> configs() {
        return Arrays.asList(new Object [][]{
                {"http://www.cnn.com/2013/06/10/politics/edward-snowden-profile/",
                        Arrays.asList(answer1)},
                {"http://www.vigneshrv.com", Arrays.asList(answer2)}
        });
    }

    @Before
    public void initialize() {
        appTest = new Assignment();
    }

    @Test
    public void testApp() {
        assertEquals(expectedResult, appTest.findTopics(url));
    }
}
