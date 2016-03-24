package com.company;

import java.math.BigInteger;

/**
 * Created by mialiu on 3/19/16.
 */
public class Job {
    // 0 for quick sort strings in one file
    // 1 for merge files
    public int type = 0;
    public int ID = 0;
    public long begin = 0;
    //public BigInteger end = BigInteger.ZERO;
    public long stringLength = 0;
    public String fileName = "";

    // f1
    public String file1 = "";
    // f2
    public String file2 = "";
}
