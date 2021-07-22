package com.willpower.travel.test;

import com.willpower.travel.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @project: oldtravle;
 * @package: com.willpower.travel.test;
 * @author: Administrator;
 * @date: 2021/5/24 16:28;
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestFileInTxt {
    @Test
    public void testFileInTex() throws IOException {
        File file = new File("testfile.txt");
        System.out.println(file.exists());
        System.out.println(file.getAbsolutePath());
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("王德法");
        fileWriter.close();
    }
}
