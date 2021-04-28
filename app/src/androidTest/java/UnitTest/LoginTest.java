package UnitTest;

import com.example.billsplit.login;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void emailvalidator(){
        assertTrue(login.isEmailValid("abc@gmail.com"));
        assertFalse(login.isEmailValid("hjdh.vom"));

    }


    @Test
    public void paswordValidator(){
        assertTrue(login.password("bagh15523"));
        assertFalse(login.password("hf45"));

    }
}
