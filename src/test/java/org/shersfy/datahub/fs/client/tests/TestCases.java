package org.shersfy.datahub.fs.client.tests;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

public class TestCases {
    
    @Test
    public void test01() {
        
        System.out.println(JSON.toJSONString(System.getenv()));
        
    }
    

}
