package com.company;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mialiu on 3/19/16.
 */
public class TeraSortWorker implements Runnable {
    private TaskQueue<Job> _taskQueue;
    private TaskQueue<String> _fileQueue;

    private static int _threadIdCounter;
    private int _threadId;

    private Job _job = null;

    private int _maxLength;

    public TeraSortWorker(TaskQueue<Job> taskQueue, TaskQueue<String> fileQueue, int maxLength) {
        _threadId = _threadIdCounter;
        _threadIdCounter += 1;

        _taskQueue = taskQueue;
        _fileQueue = fileQueue;

        _maxLength = maxLength;
    }

    @Override
    public void run() {
        while (true) {
            _job = _taskQueue.Get();
            if (_job == null) {
                break;
            }
            ProcessCommand();

//            try {
//                _socket.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            System.out.println(_socket.getRemoteSocketAddress().toString());
        }
        System.out.print(_threadId);
        System.out.println(" at the end.");
    }

    protected void ProcessCommand(){
        if (_job.type == 0) {
            QuickSort sort = new QuickSort(_job.fileName, _maxLength);
            sort.Run(_job.begin, _job.stringLength, _job.ID);
            List<String> fileNames = sort.GetTmpFileName();
            for (String name : fileNames) {
                _fileQueue.Put(name);
                /*
                System.out.println("In worker, " + name + " has been put on the list.");
                System.out.print("File list: ");
                for (String file : _fileQueue._list) {
                    System.out.print(file + ", ");
                }
                System.out.println();
                */
            }
        } else { // job.type == 1
            MergeTwoFiles merge = new MergeTwoFiles(_job.file1, _job.file2);
            merge.Run();
            String outputFile = merge.GetOutPutFileName();
            _fileQueue.Put(outputFile);
        }
    }
}
