package ostrowski;

import org.junit.Test;

import static org.junit.Assert.*;

public class RecognitionTest {

    @Test
    public void toStringBuilder2() {
        int[] nonRepeat = {5,4,1};
        int[] repeat = {6,2,3,1};
        Recognition rec = new Recognition(nonRepeat,repeat);
        assertEquals(rec.toStringBuilder().toString(),rec.toStringBuilder2().toString());
    }
}