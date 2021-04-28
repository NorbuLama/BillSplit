package UnitTest;

import com.example.billsplit.Register;

import org.junit.Test;
import static org.junit.Assert.*;

public class RegisterationTest {
    @Test
    public void emailvalidator(){
        assertTrue(Register.isEmailValid("abc@gmail.com"));
        assertFalse(Register.isEmailValid("hjdh.vom"));

    }



    @Test
    public void paswordValidator(){
        assertTrue(Register.password("bagh15523"));
        assertFalse(Register.password("hf45"));

    }

}
