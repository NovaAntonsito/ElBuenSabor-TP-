package com.example.demo.Utils;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Slf4j
public class ImagenUtils {
    public static byte[] compressImage(byte[] data){
        Deflater compresor = new Deflater();
        compresor.setLevel(Deflater.BEST_COMPRESSION);
        compresor.setInput(data);
        compresor.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] temp = new byte[4*1024];
        while (!compresor.finished()){
            int size = compresor.deflate(temp);
            outputStream.write(temp,0,size);
        }
        try {
            outputStream.close();
        }catch(Exception err){
            log.info(err.toString()+" Epa hubo un error al comprimir");
        }
        return outputStream.toByteArray();
    }
    public static byte[] decompressImage(byte[] data){
        Inflater descompresor = new Inflater();
        descompresor.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] temp = new byte[4*1024];
        try {
            while(!descompresor.finished()){
                int count = descompresor.inflate(temp);
                outputStream.write(temp,0 ,count);
            }
            outputStream.close();
        }catch (Exception err) {
            log.info(err.toString()+" Epa tambien hubo un error al descomprimir");
        }
        return outputStream.toByteArray();
    }
}
