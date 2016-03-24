package com.company;

import java.util.Date;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int worker = 8;
        int maxsize = 2000;
        String file = "data-1m.txt";
        int powNum = 4;// 4 for 1M, 7 for 1G, 8 for 10G
        if (args.length > 4) {
            worker = Integer.parseInt(args[0]);
            maxsize = Integer.parseInt(args[1]);
            file = args[2];
            powNum = Integer.parseInt(args[3]);
        }
        TeraSortCommander teraSortCommander = new TeraSortCommander(file, worker, maxsize);
        teraSortCommander.Start(powNum);
    }
}
