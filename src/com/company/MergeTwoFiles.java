package com.company;

import java.io.*;
import java.util.Scanner;

/**
 * Created by mialiu on 3/19/16.
 */
public class MergeTwoFiles {
    private String _firstFile;
    private String _secondFile;
    private String _outputFile = "";

    public MergeTwoFiles(String file1, String file2){
        _firstFile = file1;
        _secondFile = file2;
        _outputFile = file1 + ".a";
    }

    public String GetOutPutFileName(){
        return _outputFile;
    }

    public void Run() {
        FileReader stream1 = null;
        FileReader stream2 = null;
        //Scanner scanner1 = null;
        //Scanner scanner2 = null;
        FileWriter writer = null;

        try {
            stream1 = new FileReader(_firstFile);
            BufferedReader bs1 = new BufferedReader(stream1);
            //scanner1 = new Scanner(stream1);

            stream2 = new FileReader(_secondFile);
            BufferedReader bs2 = new BufferedReader(stream2);

            //scanner2 = new Scanner(stream2);

            writer = new FileWriter(_outputFile, false);
            BufferedWriter bw = new BufferedWriter(writer);

            /*
            for (;scanner1.hasNextLine() && scanner2.hasNextLine();){
                String line1 =
            }
            */

            String line1 = "";
            String line2 = "";

            while (true) {
                if (line1.isEmpty()){
                    line1 = bs1.readLine();
                    if (line1 == null) {
                        break;
                    }
                }
                if (line2.isEmpty()){
                    line2 = bs2.readLine();
                    if (line2 == null) {
                        break;
                    }
                }

                if (CompareLines(line1, line2) <= 0) {
                    bw.write(line1 + "\r\n");
                    line1 = "";
                } else {
                    bw.write(line2 + "\r\n");
                    line2 = "";
                }
            }

            if (line1 != null && !line1.isEmpty()) {
                bw.write(line1 + "\r\n");
            }

            if (line2 != null && !line2.isEmpty()) {
                bw.write(line2 + "\r\n");
            }

            while ((line1 = bs1.readLine()) != null) {
                bw.write(line1 + "\r\n");
            }

            while ((line2 = bs2.readLine()) != null) {
                bw.write(line2 + "\r\n");
            }

            bs1.close();
            bs2.close();
            bw.flush();
            bw.close();

            /*
            while (bs1.hasNextLine()){
                if (line1.isEmpty()) {
                    line1 = bs1.nextLine();
                }
                if (!scanner2.hasNextLine()) {
                    writer.write(line1 + "\r\n");
                    line1 = "";
                } else {
                    if (line2.isEmpty()) {
                        line2 = scanner2.nextLine();
                    }
                    if (CompareLines(line1, line2) <= 0) {
                        writer.write(line1 + "\r\n");
                        line1 = "";
                    } else {
                        writer.write(line2 + "\r\n");
                        line2 = "";
                    }
                }
            }

            if (!line2.isEmpty()) {
                writer.write(line2 + "\r\n");
            }

            if (!line1.isEmpty()) {
                writer.write(line1 + "\r\n");
            }

            while (scanner2.hasNextLine()) {
                String line = scanner2.nextLine();
                writer.write(line + "\r\n");
            }

            writer.close();
            stream1.close();
            stream2.close();
            scanner1.close();
            scanner2.close();
            */

            DeleteFile(_firstFile);
            DeleteFile(_secondFile);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Merging " + _firstFile + " and " + _secondFile + " into " + _outputFile + " has finished.");
    }

    protected int CompareLines(String line1, String line2){
        int keyNum = 10;
        /*
        if (line1.length()<98) {
            System.out.println("M:length: " + line1.length() + '\t' + line1);
            return -1;
        }
        if (line2.length()<98) {
            System.out.println("M:length: " + line2.length() + '\t' + line2);
            return 1;
        }
        */
        String key1 = line1.substring(0, keyNum);
        String key2 = line2.substring(0, keyNum);
        // Return: a negative integer, zero, or a positive integer
        // as this object is less than, equal to, or greater than the specified object
        return key1.compareTo(key2);
    }

    protected void DeleteFile(String fileName){
        File file = new File(fileName);
        file.delete();
    }
}
