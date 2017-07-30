package com.khande.idea.plugin.locateinfinder.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.diagnostic.Logger;


import java.io.IOException;

/**
 * Created by Khande on 17/7/30.
 */
public class LocateInFinderAction extends AnAction {
    private Logger logger = Logger.getInstance(this.getClass().getSimpleName());

    @Override
    public void actionPerformed(AnActionEvent event) {
        VirtualFile selectedFile = getSelectedFile(event);

        if (selectedFile != null) {
            logger.debug("Selected file = " + selectedFile.getPath());
        } else {
            logger.debug("No file selected.");
            return;
        }

//        String[] command = new String[9];
//        command[0] = "osascript";
//        command[1] = "-e";
//        command[2] = "tell application \"Path Finder\"";
//        command[3] = "-e";
//        command[4] = "activate";
//        command[5] = "-e";
//        command[6] = "reveal POSIX file \"" + selectedFile.getPath() + '"';
//        command[7] = "-e";
//        command[8] = "end tell";
//        try {
//            Runtime.getRuntime().exec(command);
//        } catch (IOException e) {
//            logger.info("exec of LocateInFinder applescript failed", e);
//        }

        boolean isFileIsDir = selectedFile.isDirectory();
        String[] command = new String[2];
        command[0] = "open";
        command[1] = isFileIsDir ? selectedFile.getPath() : selectedFile.getParent().getPath();

        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            logger.info("exec of LocateInFinder Open failed", e);
        }

    }

    @Override
    public void update(AnActionEvent event) {
        event.getPresentation().setEnabled(getSelectedFile(event) != null);
    }

    private VirtualFile getSelectedFile(AnActionEvent e) {
        return DataKeys.VIRTUAL_FILE.getData(e.getDataContext());
    }
}
