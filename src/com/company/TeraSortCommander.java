package com.company;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by mialiu on 3/19/16.
 */

public class TeraSortCommander {
    private int _workersCount = 8;
    private int _maxLength;
    private TaskQueue<Job> _taskQueue = null;

    private TaskQueue<String> _fileQueue = null;

    private List<Thread> _workers = new ArrayList<Thread>();

    private String _fileName = "";

    public TeraSortCommander(String fileName, int worker, int maxLength) {
        _fileName = fileName;
        _workersCount = worker;
        _maxLength = maxLength;

        _taskQueue = new TaskQueue<Job>();
        _fileQueue = new TaskQueue<String>();
    }

    public void Start(int powNum) {
        Date start = new Date();
        System.out.println("~~~~Start: " + start.getTime());

        startWorkers();

        // create all tasks
        long linesForG = (long)Math.pow((double)10, (double)powNum);
        long numLinesForG = linesForG/_workersCount;// linesForG.divide(new BigInteger(Integer.toString(_workersCount)));
        int linesForM = 10000;
        int numLines = linesForM / _workersCount;
//        if (numLines > _maxLength) {
//            numLines = _maxLength;
//        }
        for (int i = 0; i < _workersCount; i++){
            Job aJob = new Job();
            aJob.type = 0;
            aJob.ID = i;
            aJob.begin = i*numLinesForG;// numLinesForG.multiply(new BigInteger(Integer.toString(i)));// i * numLines;
            //aJob.end = (i + 1) * numLines - 1;
            aJob.stringLength = numLinesForG;// numLines;
            aJob.fileName = _fileName;

            _taskQueue.Put(aJob);
        }

        String file2 = "";
        while(true) {
            String file1 = _fileQueue.Get();

            System.out.print("In commander, get " + file1 + ". The remaining files are ");
            Date end = new Date();
            System.out.println((end.getTime() - start.getTime()) + " ms.");
            /*for(String name : _fileQueue._list) {
                System.out.print(name + ", ");
            }
            System.out.println();*/

            if (file2.isEmpty()) {
                file2 = file1;
            } else {
                Job jobTwo = new Job();
                jobTwo.type = 1;
                jobTwo.file1 = file1;
                jobTwo.file2 = file2;

                _taskQueue.Put(jobTwo);

                //System.out.println("In commander, put one job with " + file1 + " and " + file2);

                file2 = "";
            }
        }

    }

    private void startWorkers() {
        for (int i = 0; i < _workersCount; i++) {
            TeraSortWorker st = new TeraSortWorker(_taskQueue, _fileQueue, _maxLength);
            Thread th = new Thread(st);
            th.start();

            _workers.add(th);
        }
    }

}
