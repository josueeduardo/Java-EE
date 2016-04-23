/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.simple.batch;

import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
public class ImportBodyItemProcessor implements ItemProcessor {

    private static final Logger logger = Logger.getLogger(ImportBodyItemProcessor.class.getName());

    @Override
    public String processItem(Object rawValue) {
        try {
            Thread.sleep(5000);
            String updated = ":PROCESSED:" + rawValue;
            return updated;
        } catch (InterruptedException ex) {
            Logger.getLogger(ImportBodyItemProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ERROR";
    }
}
