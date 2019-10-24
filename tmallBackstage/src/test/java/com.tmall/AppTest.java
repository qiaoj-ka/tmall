package com.tmall;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void test1(){
        Format format = new SimpleDateFormat("yyyyMMddHHmmss");
        String id=format.format(new Date());
        StringBuffer stringBuffer=new StringBuffer(id);
        String tail=String.valueOf((int)(100000+Math.random()*100000));
        StringBuffer stringBuffer1=new StringBuffer(tail);
        stringBuffer.append(stringBuffer1);
        System.out.println(stringBuffer.toString());
    }
}
