package com.computationalcluster.common.module.tasksolver;

import java.io.File;
import java.io.FileFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import org.apache.log4j.Logger;

import pl.edu.pw.mini.se2.TaskSolverCreator;

public class TaskSolverLoader {
	private static final Logger logger = Logger.getLogger(TaskSolverLoader.class);
	
	public List<TaskSolverCreator> load() {
		File loc = new File("plugins");

        File[] flist = loc.listFiles(new FileFilter() {
            public boolean accept(File file) {return file.getPath().toLowerCase().endsWith(".jar");}
        });
        URL[] urls = new URL[flist.length];
        for (int i = 0; i < flist.length; i++) {
        	logger.info("file:" + flist[i]);
        	try {
				urls[i] = flist[i].toURI().toURL();
			} catch (MalformedURLException e) {
				logger.error(e.getMessage());
			}
        }
            
        URLClassLoader ucl = new URLClassLoader(urls);

        ServiceLoader<TaskSolverCreator> sl = ServiceLoader.load(TaskSolverCreator.class, ucl);
        Iterator<TaskSolverCreator> apit = sl.iterator();
        
        List<TaskSolverCreator> taskSolverCreators = new ArrayList<TaskSolverCreator>();
        while (apit.hasNext()) {
        	TaskSolverCreator taskSolverCreator = apit.next();
        	taskSolverCreators.add(taskSolverCreator);
        	logger.info("taskSolverCreator:" + taskSolverCreator);
        }
            
		return taskSolverCreators;
	}
}
