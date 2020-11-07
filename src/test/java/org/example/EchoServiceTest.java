package org.example;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EchoServiceTest {

    @Test
    public void givenValidRequestAndResponse_whenEcho_thenTrueIsResponse() throws IOException {
        //given:
        EchoService echoService = new EchoService();

        String request =  "Hello world!";

        byte []messageInBytes = new byte [] {
               'H','e', 'l','l','o', ' ', 'w','o','r','l','d','!'
        };

        OutputStream outputStream = mock(OutputStream.class);
        InputStream inputStream   = mock(InputStream.class);

        Mockito.when(inputStream.readAllBytes()).thenReturn(messageInBytes);

        //when:
        boolean response =  echoService.sendEchoMessage(request, outputStream, inputStream);

        //then:
        verify(inputStream).readAllBytes();
        verify(outputStream).write(messageInBytes);
        verifyNoMoreInteractions(inputStream, outputStream);

        assertTrue(response);
    }

    @Test
    public void givenValidRequestAndWrongResponse_whenEcho_thenFalseIsResponse() throws IOException {
        //given:
        EchoService echoService = new EchoService();

        String request =  "Hello world!";
        byte []messageRespond = new byte [] {
                'H','e', 'l','l','o', ' ', 'w','o','r','l','d','!','!'
        };
        byte []messageRequest = new byte [] {
                'H','e', 'l','l','o', ' ', 'w','o','r','l','d','!'
        };

        OutputStream outputStream = mock(OutputStream.class);
        InputStream inputStream   = mock(InputStream.class);

        when(inputStream.readAllBytes()).thenReturn(messageRespond);

        //when:


        boolean response =  echoService.sendEchoMessage(request, outputStream, inputStream);

        //then:
        verify(inputStream).readAllBytes();
        verify(outputStream).write(messageRequest);
        verifyNoMoreInteractions(inputStream, outputStream);

        assertFalse(response);
    }

}