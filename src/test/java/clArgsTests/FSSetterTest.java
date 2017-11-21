package clArgsTests;

import clArgs.FSSetter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FSSetterTest {

    @Test
    void setWillReturnAnyStringThatSaysItsTrue() {
        //Given
        boolean isItALegit = true;
        String thingToSet = "This is totally a valid thing to set";
        //When
        String thingThatWasSet = FSSetter.set(isItALegit, thingToSet);
        //Then
        assertEquals(thingToSet, thingThatWasSet);
    }

    @Test
    void setThrowsAnIllegalArgumentExceptionForAnyStringThatSaysItsALiar() {
        //Given
        boolean isItALegit = false;
        String thingToSet = "This is totally not a valid thing to set";
        //Then
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            FSSetter.set(isItALegit, thingToSet);
        });
        //And
        assertEquals(FSSetter.notInFSMessage(thingToSet), exception.getMessage());
    }
}