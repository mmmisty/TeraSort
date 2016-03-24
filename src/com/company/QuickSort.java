package com.company;

import java.io.*;
import java.util.*;

//import org.apache.commons.io.FileUtils;

/**
 * Created by mialiu on 3/19/16.
 */
public class QuickSort {
    private String _fileName;
    private List<String> _tmpFile = new ArrayList<>();
    private List<String> _lines = null;
    private File _file = null;
    private Random _random = new Random();
    private RandomAccessFile raf;

    private int _maxLength = 0;

    //File _inputStream = null;
    //Scanner _scanner = null;

    public QuickSort(String fileName, int maxLength) {
        _fileName = fileName;
        _maxLength = maxLength;
        try {
            //_inputStream = new File(_fileName);
            //_scanner = new Scanner(_inputStream/*, "UTF-8"*/);
            _file = new File(_fileName);
            //_lines = FileUtils.readLines(_file);

        } catch (Exception e) {
            Close();
        }
    }

    protected boolean Close() {
        boolean result = true;
//        if (_file != null) {
//            try {
//                _file.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//                result = false;
//            }
//        }
//        if (_scanner != null) {
//            try {
//                _scanner.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//                result = false;
//            }
//        }
        if (_lines != null || !_lines.isEmpty()) {
            _lines.clear();
        }
        return result;
    }

    protected void LoadFile(long begin, long length) {
        int lineNum = 1;
        _lines = new ArrayList<>();
        try {
            RandomAccessFile raf = new RandomAccessFile(_file, "r");

            raf.seek(begin * 100);

            FileReader fr = new FileReader(raf.getFD());
            BufferedReader br = new BufferedReader(fr);

            String line = null;

            while ((line = br.readLine()) != null && lineNum <= length){
                //String line = _scanner.nextLine();
                _lines.add(line);
                //System.out.println(line);
                lineNum++;
            }
            br.close();
            //System.out.println("Load file, begin: " + begin + ", length: " + length + ", lineNum: " + lineNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(lineNum);
    }

    protected void RandomizedQuickSort(List<String> A, int p, int r) {
        if(p < r){
            int q = RandomizedPartition(A, p, r);
            RandomizedQuickSort(A, p, q - 1);
            RandomizedQuickSort(A, q + 1, r);
        }
    }

    protected int RandomizedPartition(List<String> A, int p, int r) {
        int k = _random.nextInt(r + 1 - p) + p;
        ExchangeLines(A, r, k);

        String x = A.get(r);
        int i = p - 1;
        for (int j = p; j <= r - 1; j++){
            if (CompareLines(A.get(j), x) < 0) {
                ++i;
                ExchangeLines(A, i, j);
            }
        }
        ExchangeLines(A, i + 1, r);
        return i + 1;
    }

    protected void ExchangeLines(List<String> A, int i, int j){
        String line1 = A.get(i);
        String line2 = A.get(j);
        String tmp = line1;
        A.set(j, tmp);
        tmp = line2;
        A.set(i, tmp);
    }

    protected int CompareLines(String line1, String line2){
        int keyNum = 10;
        /*
        if (line1.length()<98) {
            System.out.println("Q:length: " + line1.length() + '\t' + line1);
            return -1;
        }
        if (line2.length()<98) {
            System.out.println("Q:length: " + line2.length() + '\t' + line2);
            return 1;
        }
        */
        String key1 = line1.substring(0, keyNum);
        String key2 = line2.substring(0, keyNum);
        // Return: a negative integer, zero, or a positive integer
        // as this object is less than, equal to, or greater than the specified object
        return key1.compareTo(key2);
    }

    protected void Write(String out){
        try {
            FileWriter _writer = new FileWriter(out, false);
            BufferedWriter bw = new BufferedWriter(_writer);
            for (String line : _lines){
                //_writer.write(Integer.toString((int) line.charAt(0) ) );
                /*
                if (line.length()<98) {
                    System.out.println("Q:Write:Length: "+ line.length() + '\t' + line);
                }
                */
                bw.write(line);
                bw.write("\r\n");
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Run(long begin, long length, int tmpFileIndex){
        if (_file == null || !_file.exists()) {
            return;
        }

        double loop = Math.ceil((double)length / _maxLength);
        for (int i = 0; i < loop; i++) {
            long acLenghth = length < _maxLength ? length : _maxLength;
            LoadFile(begin + i * _maxLength, acLenghth);
            RandomizedQuickSort(_lines, 0, _lines.size() - 1);
            String out = _fileName + '.' + Integer.toString(tmpFileIndex) + '.' + Integer.toString(i);
            _tmpFile.add(out);
            Write(out);
            Close();

            length -= acLenghth;

            System.out.println("In quick sort, " + out + " has been created. begin: "
                    + (begin + i * _maxLength) + " length: " + (length < _maxLength ? length : _maxLength));
        }
    }

    public List<String> GetTmpFileName() {
        return _tmpFile;
    }
}
