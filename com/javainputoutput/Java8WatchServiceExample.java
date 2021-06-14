package com.javainputoutput;

import static java.nio.file.StandardWatchEventKinds.*;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class Java8WatchServiceExample 
{
	private final WatchService watcher;
    private final Map<WatchKey, Path> keys;

    /**
     * Parameterized constructor of the class .
     * @param dir
     * @throws IOException
     */
    Java8WatchServiceExample(Path dir) throws IOException 
    {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();
        walkAndRegisterDirectories(dir);
    }

    /**
     * Register the given directory with the WatchService. 
     */
    private void registerDirectory(Path dir) throws IOException 
    {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        keys.put(key, dir);
    }

    /**
     * This method is use to Register the given directory, and all its sub-directories, with the WatchService.
     */
    private void walkAndRegisterDirectories(final Path start) throws IOException 
    {
        /* register directory and sub-directories */
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() 
        {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException 
            {
                registerDirectory(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    /** Process all events for keys queued to the watcher */
    @SuppressWarnings("rawtypes")
    void processEvents() 
    {
        while (true) 
        {
            WatchKey key; //wait for key to be signalled 
            try 
            {
                key = watcher.take();
            } 
            catch (InterruptedException x) 
            {
                return;
            }
            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }
            for (WatchEvent<?> event : key.pollEvents()) 
            {
                WatchEvent.Kind kind = event.kind();
                Path name = (Path) event.context();
                Path child = dir.resolve(name);
                System.out.format("%s: %s\n", event.kind().name(), child); // print out event

                // if directory is created, and watching recursively, then register it and its sub-directories
                if (kind == StandardWatchEventKinds.ENTRY_CREATE) 
                {
                    try 
                    {
                        if (Files.isDirectory(child)) 
                        {
                            walkAndRegisterDirectories(child);
                        }
                    } 
                    catch (IOException x) 
                    {
                    }
                } 
                else if (kind.equals(StandardWatchEventKinds.ENTRY_DELETE)) 
                {
                    if (Files.isDirectory(child))
                        keys.remove(key);
                }
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) 
            {
                keys.remove(key);
                // all directories are inaccessible
                if (keys.isEmpty()) 
                {
                    break;
                }
            }
        }
    }
}
