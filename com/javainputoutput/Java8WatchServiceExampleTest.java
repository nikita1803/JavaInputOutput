package com.javainputoutput;

import static org.junit.Assert.*;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class Java8WatchServiceExampleTest 
{

	private static String HOME = System.getProperty("user.home");
    private static String PLAY_WITH_NIO = "TempPlayGround";
    
    /**
     * This test method is use to test all the activities .
     * @throws IOException
     */
    @Test
    public void givenDirectoryWhenWatchedListsAllTheActivities() throws IOException 
    {
        Path dir = Paths.get(HOME + "/" + PLAY_WITH_NIO);
        Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
        new Java8WatchServiceExample(dir).processEvents();
    }

}
