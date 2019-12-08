package com.ltoscano.ia.util;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author ltosc
 */
public class FileHelper 
{
    private static String selectedFilePath;
    public static final String GENOME_FILE_EXTENSION = "dat";
    public static final String TRAIN_SET_FILE_EXTENSION = "txt";
    public static final FileFilter GENOME_FILE_FILTER = new FileNameExtensionFilter("Genome File", GENOME_FILE_EXTENSION);
    public static final FileFilter TRAIN_SET_FILE_FILTER = new FileNameExtensionFilter("Train Set File", TRAIN_SET_FILE_EXTENSION);
    public static final String WORK_DIR = System.getProperty("user.dir");
    public static final String GENOME_PATH = WORK_DIR + File.separator + "genomes";
    
    public static int showFileDialog(int dialogType, int dialogMode, String title, String currentDirectory, FileFilter fileFilter)
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogType(dialogType);
        fileChooser.setFileSelectionMode(dialogMode);
        fileChooser.setDialogTitle(title);
        fileChooser.setFileFilter(fileFilter);
        fileChooser.setCurrentDirectory(new File(currentDirectory));
        
        int diagResult = fileChooser.showOpenDialog(null);
        
        if(diagResult == JFileChooser.APPROVE_OPTION)
        {
            selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
        }
        
        return diagResult;
    }

    /**
     * @return the selectedFilePath
     */
    public static String getSelectedFilePath() 
    {
        return selectedFilePath;
    }
}
